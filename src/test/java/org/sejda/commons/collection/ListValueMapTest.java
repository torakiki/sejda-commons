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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Andrea Vacondio
 * 
 */
public class ListValueMapTest {

    @Test
    public void testMultiMap() {
        String value1 = "val1";
        String value2 = "val2";
        ListValueMap<Integer, String> victim = new ListValueMap<>();
        assertEquals(0, victim.size());
        victim.put(1, value1);
        assertEquals(1, victim.size());
        assertNotNull(victim.get(1));
        victim.put(1, value2);
        assertEquals(2, victim.get(1).size());
        assertTrue(victim.remove(1, value2));
        assertFalse(victim.remove(2, value2));
        assertEquals(1, victim.size());
        victim.clear();
        assertEquals(0, victim.size());
    }
}
