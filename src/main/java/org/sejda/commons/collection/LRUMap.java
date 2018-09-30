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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fixed size {@link Map} implementation removing the least recently used element when {@link Map#put(Object, Object)} is called.
 * 
 * @author Andrea Vacondio
 * @param <K>
 *            key of the map
 * @param <V>
 *            value
 */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {

    private int maxCapacity;

    public LRUMap(int maxCapacity) {
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.maxCapacity;
    }
}
