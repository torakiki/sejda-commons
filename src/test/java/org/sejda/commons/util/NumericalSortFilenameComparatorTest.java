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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

public class NumericalSortFilenameComparatorTest {

    @Test
    public void nulls() {
        assertEquals(0, new NumericalSortFilenameComparator().compare(null, new File("bla")));
        assertEquals(0, new NumericalSortFilenameComparator().compare(null, null));
        assertEquals(0, new NumericalSortFilenameComparator().compare(new File("bla"), null));
    }

    @Test
    public void nonDigit() {
        assertEquals(0, new NumericalSortFilenameComparator().compare(new File("123.pdf"), new File("bla.pdf")));
        assertEquals(0, new NumericalSortFilenameComparator().compare(new File("bla"), new File("123.pdf")));
    }

    @Test
    public void digits() {
        assertEquals(1, new NumericalSortFilenameComparator().compare(new File("123.pdf"), new File("1bla.pdf")));
        assertEquals(1, new NumericalSortFilenameComparator().compare(new File("123bla.pdf"), new File("1bla.pdf")));
        assertEquals(0, new NumericalSortFilenameComparator().compare(new File("1.pdf"), new File("001bla.pdf")));
        assertEquals(0, new NumericalSortFilenameComparator().compare(new File("1bla.pdf"), new File("001bla.pdf")));
        assertEquals(-1, new NumericalSortFilenameComparator().compare(new File("005bla.pdf"), new File("500.pdf")));
        assertEquals(-1, new NumericalSortFilenameComparator().compare(new File("005bla.pdf"), new File("500bla.pdf")));
    }
}
