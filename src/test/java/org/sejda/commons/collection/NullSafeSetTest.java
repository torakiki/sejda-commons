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
package org.sejda.commons.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Andrea Vacondio
 * 
 */
public class NullSafeSetTest {

    @Test
    public void testAdd() {
        Set<String> victim = new NullSafeSet<>();
        assertFalse(victim.add(null));
        assertTrue(victim.isEmpty());
        assertTrue(victim.add("test"));
        assertEquals(1, victim.size());
    }

    @Test
    public void testAddWithDelegate() {
        Set<String> victim = new NullSafeSet<>(new HashSet<>());
        assertFalse(victim.add(null));
        assertTrue(victim.isEmpty());
        assertTrue(victim.add("test"));
        assertEquals(1, victim.size());
    }
}
