/*
 * Copyright 2018 Sober Lemur S.a.s. di Vacondio Andrea and Sejda BV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sejda.commons.util;

import java.io.File;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.sejda.commons.util.StringUtils.isEmpty;
import static org.sejda.commons.util.StringUtils.isNotEmpty;

/**
 * Comparator for filenames that performs a numerical sort if the file names start or end with
 * digits. It allows to specify a fallback comparator to use in case the numerical sort fails. The
 * goal is to behave as close as possible to file managers sorting results.
 *
 * @author Andrea Vacondio
 */
public class NumericalSortFilenameComparator implements Comparator<File> {

    private static final Pattern DIGITS_PATTERN = Pattern.compile("^(\\d*)(.*)(\\d*)$");

    private static final Comparator<String> BIG_INT_COMPARATOR = (a, b) -> {
        var bigA = toBigInteger(a);
        var bigB = toBigInteger(b);
        if (nonNull(bigA) && nonNull(bigB)) {
            return bigA.compareTo(bigB);
        }
        return 0;
    };

    private final Comparator<Matcher> digitsPatternMatcherComparator = comparing((Matcher m) -> m.group(1),
            BIG_INT_COMPARATOR).thenComparing(m -> m.group(2), this::stringCompare)
            .thenComparing(m -> m.group(3), BIG_INT_COMPARATOR);

    private final Comparator<String> fallback;

    /**
     * @param fallback the comparator to use when numerical sorting fails. Default is file name
     *                 case-insensitive compare
     */
    public NumericalSortFilenameComparator(Comparator<String> fallback) {
        this.fallback = ofNullable(fallback).orElse(nullsLast(String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Comparator performing numerical sort with fallback to file name case-insensitive compare in
     * case numerical sort fails
     */
    public NumericalSortFilenameComparator() {
        this(null);
    }

    @Override
    public int compare(File a, File b) {
        Matcher m1 = ofNullable(a).map(NumericalSortFilenameComparator::basename).map(DIGITS_PATTERN::matcher)
                .filter(Matcher::matches).orElse(null);
        Matcher m2 = ofNullable(b).map(NumericalSortFilenameComparator::basename).map(DIGITS_PATTERN::matcher)
                .filter(Matcher::matches).orElse(null);

        if (nonNull(m1) && nonNull(m2) && (isEmpty(m1.group(1)) ^ isEmpty(m2.group(1)))) {
            // one start with digits the other doesn't, we can just go with the fallback
            return fallback.compare(name(a), name(b));
        }

        int retVal = nullsLast(digitsPatternMatcherComparator).compare(m1, m2);
        if (retVal == 0) {
            // from the numerical sort of point of view they are equivalent (ex. 001banana.pdf and 1banana.pdf)
            return fallback.compare(name(a), name(b));
        }
        return retVal;
    }

    private int stringCompare(String a, String b) {
        if (isNotEmpty(a) && isNotEmpty(b)) {
            return this.fallback.compare(a, b);
        }
        return 0;
    }

    private static BigInteger toBigInteger(String value) {
        if (isNotEmpty(value)) {
            return new BigInteger(value);
        }
        return null;
    }

    private static String basename(File file) {
        if (nonNull(file)) {
            String filename = file.getName();
            int index = filename.lastIndexOf('.');
            if (index > 0) {
                return filename.substring(0, index);
            }
            if (StringUtils.isNotEmpty(filename)) {
                return filename;
            }
        }
        return null;
    }

    private static String name(File file) {
        return ofNullable(file).map(File::getName).orElse(null);
    }
}
