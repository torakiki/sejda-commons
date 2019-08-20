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
package org.sejda.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Andrea Vacondio
 *
 */
public class LookupTableTest {
    private LookupTable<String> victim;

    @BeforeEach
    public void setUp() {
        victim = new LookupTable<>();
    }

    @Test
    public void cleanIsEmpty() {
        assertTrue(victim.isEmpty());
        victim.addLookupEntry("this", "that");
        assertFalse(victim.isEmpty());
        victim.clear();
        assertTrue(victim.isEmpty());
    }

    @Test
    public void nullFirst() {
        assertThrows(IllegalArgumentException.class, () -> {
            victim.addLookupEntry(null, "not null");
        });
    }

    @Test
    public void nullSecond() {
        assertThrows(IllegalArgumentException.class, () -> {
            victim.addLookupEntry("not null", null);
        });
    }

    @Test
    public void lookup() {
        victim.addLookupEntry("this", "that");
        victim.addLookupEntry("this1", "that1");
        victim.addLookupEntry("this2", "that2");
        assertEquals("that2", victim.lookup("this2"));
    }

    @Test
    public void first() {
        victim.addLookupEntry("this", "that");
        victim.addLookupEntry("this1", "that1");
        victim.addLookupEntry("this2", "that2");
        assertEquals("that", victim.first());
    }

    @Test
    public void firstNull() {
        assertNull(victim.first());
    }

    @Test
    public void values() {
        victim.addLookupEntry("this", "that");
        victim.addLookupEntry("this1", "that1");
        victim.addLookupEntry("this2", "that2");
        assertEquals(3, victim.values().size());
    }

    @Test
    public void keys() {
        victim.addLookupEntry("this", "that");
        victim.addLookupEntry("this1", "that1");
        victim.addLookupEntry("this", "that2");
        assertEquals(2, victim.values().size());
    }
}
