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

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andrea Vacondio
 *
 */
public class StringUtils {

    private StringUtils() {
        // hide
    }

    /**
     * @return a string where control characters like \n, \t or \r are removed and whitespaces (including Unicode 'non-breaking-space') are replaced with plain regular 'space'
     */
    public static String normalizeWhitespace(String in) {
        // removes control characters like \n, \r or \t
        // replaces all whitespace (eg: &nbsp;) with ' ' (space)
        String result = in.replaceAll("[\\n\\t\\r]", "").replaceAll("\\p{Z}", " ").replaceAll("\\s", " ");
        result = result.replace((char) 160, ' ');
        return result;
    }

    /**
     * @returns a list of characters that exist in s1 but not in s2
     */
    public static Set<Character> difference(String s1, String s2) {
        return s1.codePoints().filter(c -> s2.indexOf(c) < 0).mapToObj(c -> (char) c).collect(Collectors.toSet());
    }
}
