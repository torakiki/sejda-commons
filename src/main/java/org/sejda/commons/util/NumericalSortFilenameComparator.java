/*
 * Copyright 2020 Sober Lemur S.a.s. di Vacondio Andrea and Sejda BV
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

import static java.util.Objects.nonNull;

import java.io.File;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Comparator for filenames that performs a numerical sort if the file names start with digits, returns 0 in any other case.
 * 
 * @author Andrea Vacondio
 *
 */
public class NumericalSortFilenameComparator implements Comparator<File> {

    public static Logger LOG = LoggerFactory.getLogger(NumericalSortFilenameComparator.class);
    private Pattern pattern = Pattern.compile("^(\\d*)(.*)$");

    private Function<File, BigInteger> leadingDigits = (f) -> {
        if (nonNull(f)) {
            Matcher matcher = pattern.matcher(f.getName().toLowerCase());
            if (matcher.matches()) {
                return Optional.of(matcher.group(1)).filter(StringUtils::isNotEmpty).map(BigInteger::new).orElse(null);
            }
        }
        return null;
    };

    @Override
    public int compare(File a, File b) {
        try {
            BigInteger bigA = leadingDigits.apply(a);
            BigInteger bigB = leadingDigits.apply(b);
            if (nonNull(bigA) && nonNull(bigB)) {
                return bigA.compareTo(bigB);
            }
        } catch (NumberFormatException | IllegalStateException e) {
            LOG.warn("Unexpected conversion issue", e);
        }
        return 0;
    }
}
