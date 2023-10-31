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

import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.Collator;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Andrea Vacondio
 */
public class NumericalSortFilenameComparatorTest {
    @Test
    public void nulls() {
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator();
        assertTrue(victim.compare(null, new File("bla")) > 0);
        assertTrue(victim.compare(null, null) == 0);
        assertTrue(victim.compare(new File("bla"), null) < 0);
    }

    @Test
    public void onlyDigits() {
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator();
        assertTrue(victim.compare(new File("123.pdf"), new File("1.pdf")) > 0);
        assertTrue(victim.compare(new File("0023.pdf"), new File("230.pdf")) < 0);
        assertTrue(victim.compare(new File("1"), new File("001.pdf")) > 0);
        assertTrue(victim.compare(new File("005.pdf"), new File("500.pdf")) < 0);
    }

    @Test
    public void nonDigit() {
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator();
        assertTrue(victim.compare(new File("123.pdf"), new File("bla.pdf")) < 0);
        assertTrue(victim.compare(new File("bla.pdf"), new File("123.pdf")) > 0);
        assertTrue(victim.compare(new File("bla.pdf"), new File("abc.pdf")) > 0);
        assertTrue(victim.compare(new File("bla.pdf"), new File("bla.pdf")) == 0);
        assertTrue(victim.compare(new File("bla.pdf"), new File("bla123.pdf")) < 0);
        assertTrue(victim.compare(new File("123bla.pdf"), new File("123.pdf")) > 0);
    }

    @Test
    public void leadingDigits() {
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator();
        assertTrue(victim.compare(new File("123.pdf"), new File("1bla.pdf")) > 0);
        assertTrue(victim.compare(new File("123bla.pdf"), new File("1bla.pdf")) > 0);
        assertTrue(victim.compare(new File("1.pdf"), new File("001bla.pdf")) > 0);
        assertTrue(victim.compare(new File("1bla.pdf"), new File("001bla.pdf")) > 0);
        assertTrue(victim.compare(new File("005bla.pdf"), new File("500.pdf")) < 0);
    }

    @Test
    public void trailingDigits() {
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator();
        assertTrue(victim.compare(new File("123.pdf"), new File("bla1.pdf")) < 0);
        assertTrue(victim.compare(new File("bla 10.pdf"), new File("bla 1.pdf")) > 0);
        assertTrue(victim.compare(new File("1.pdf"), new File("bla001.pdf")) < 0);
        assertTrue(victim.compare(new File("bla1.pdf"), new File("bla001.pdf")) > 0);
        assertTrue(victim.compare(new File("bla005.pdf"), new File("500.pdf")) > 0);
    }

    @Test
    public void mixed() {
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator();
        assertTrue(victim.compare(new File("bla 1.pdf"), new File("vol 10.pdf")) < 0);
        assertTrue(victim.compare(new File("20 bla 1.pdf"), new File("03 vol 10.pdf")) > 0);
        assertTrue(victim.compare(new File("20 bla 1.pdf"), new File("20 bla 5.pdf")) < 0);
        assertTrue(victim.compare(new File("banana.pdf"), new File("avocado.pdf")) > 0);
        assertTrue(victim.compare(new File("chuck.pdf"), new File("chuck.abc")) > 0);
        assertTrue(victim.compare(new File("1234file.pdf"), new File("chuck.abc")) < 0);
        assertTrue(victim.compare(new File("bla003abc.pdf"), new File("chuck.pdf")) < 0);
    }

    @Test
    public void mixedLocaleAwareCollatorAsFallback() {
        var collator = Collator.getInstance(Locale.GERMAN);
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator(collator::compare);
        assertTrue(victim.compare(new File("0001.pdf"), new File("002.abc")) < 0);
        assertTrue(victim.compare(new File("002.pdf"), new File("03anders.abc")) < 0);
        assertTrue(victim.compare(new File("03anders.pdf"), new File("003ängstlich.abc")) < 0);
        assertTrue(victim.compare(new File("003ängstlich.pdf"), new File("Adele.abc")) < 0);
        assertTrue(victim.compare(new File("Adele.pdf"), new File("andere.abc")) < 0);
        assertTrue(victim.compare(new File("andere.pdf"), new File("Ändern.abc")) < 0);
        assertTrue(victim.compare(new File("Ändern.abc"), new File("Ändern.pdf")) < 0);
        assertTrue(victim.compare(new File("Ändern.pdf"), new File("anders.pdf")) < 0);
        assertTrue(victim.compare(new File("anders.pdf"), new File("ängstlich.pdf")) < 0);
        assertTrue(victim.compare(new File("ängstlich.pdf"), new File("zippy.pdf")) < 0);
    }

    @Test
    public void mixedNorwegianLocaleAwareCollatorAsFallback() {
        var collator = Collator.getInstance(Locale.forLanguageTag("no"));
        NumericalSortFilenameComparator victim = new NumericalSortFilenameComparator(collator::compare);
        assertTrue(victim.compare(new File("Adele.pdf"), new File("Banana.abc")) < 0);
        assertTrue(victim.compare(new File("Banana.pdf"), new File("Ændern.abc")) < 0);
        assertTrue(victim.compare(new File("Ændern.pdf"), new File("Ønders.abc")) < 0);
        assertTrue(victim.compare(new File("Ønders.pdf"), new File("Åndere.abc")) < 0);
    }
}
