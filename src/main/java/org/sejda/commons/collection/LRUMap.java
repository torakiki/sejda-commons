/*
 * Copyright 2022 Sober Lemur S.a.s. di Vacondio Andrea and Sejda BV
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

    private final int maxCapacity;

    public LRUMap(int maxCapacity) {
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.maxCapacity;
    }
}
