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

import static org.sejda.commons.util.RequireUtils.requireNotNullArg;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Component that allows to set up a lookup relation between items of the same type.
 * 
 * @author Andrea Vacondio
 * @param <I>
 *            type of the items
 */
public class LookupTable<I> {
    private Map<I, I> oldToNew = new LinkedHashMap<>();

    /**
     * Adds a lookup entry.
     * 
     * @param keyItem
     * @param valueItem
     */
    public void addLookupEntry(I keyItem, I valueItem) {
        requireNotNullArg(keyItem, "Cannot map a null item");
        requireNotNullArg(valueItem, "Cannot map a null item");
        oldToNew.put(keyItem, valueItem);

    }

    public void clear() {
        oldToNew.clear();
    }

    /**
     * @return true if the table is empty
     */
    public boolean isEmpty() {
        return oldToNew.isEmpty();
    }

    /**
     * Looks up the item that correspond to the given one
     * 
     * @param item
     * @return the item associated to the given input one or null if no mapping is present
     */
    public I lookup(I item) {
        return oldToNew.get(item);
    }

    /**
     * @param item
     * @return true if the table contains a lookup for the given item
     */
    public boolean hasLookupFor(I item) {
        return oldToNew.containsKey(item);
    }

    /**
     * @return a collection containing values of the table
     */
    public Collection<I> values() {
        return oldToNew.values();
    }

    /**
     * @return the first item or null if the table is empty.
     */
    public I first() {
        if (!isEmpty()) {
            return oldToNew.values().iterator().next();
        }
        return null;
    }

    /**
     * @return the keys of the table
     */
    public Set<I> keys() {
        return oldToNew.keySet();
    }
}
