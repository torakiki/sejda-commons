/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 08/apr/2012
 * Copyright 2017 by Sober Lemur S.a.s. di Vacondio Andrea (info@pdfsam.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sejda.commons.collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author Andrea Vacondio
 * 
 */
public class LRUMapTest {

    @Test
    public void testPut() {
        Map<String, String> victim = new LRUMap<>(2);
        victim.put("1", "A");
        victim.put("2", "B");
        assertTrue(victim.size() == 2);
        victim.put("3", "C");
        assertTrue(victim.size() == 2);
        assertFalse(victim.containsKey("1"));
    }
}
