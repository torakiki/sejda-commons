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

import static org.sejda.commons.util.RequireUtils.requireArg;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A {@link LinkedList} with size constraints. When at maxCapacity and an element is added, the eldest element is removed.
 * 
 * @author Andrea Vacondio
 *
 */
public class CircularLinkedList<E> extends LinkedList<E> {

    private int maxCapacity;

    public CircularLinkedList(int maxCapacity) {
        setMaxCapacity(maxCapacity);
    }

    public void setMaxCapacity(int maxCapacity) {
        requireArg(maxCapacity > 0, "Max capacity must be a positive value");
        this.maxCapacity = maxCapacity;
        houseKeep();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isFull() {
        return size() >= maxCapacity;
    }

    @Override
    public void addFirst(E e) {
        makeRoom();
        super.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        makeRoom();
        super.addLast(e);
    }

    @Override
    public boolean add(E e) {
        makeRoom();
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean ret = super.addAll(c);
        houseKeep();
        return ret;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean ret = super.addAll(index, c);
        houseKeep();
        return ret;
    }

    @Override
    public void add(int i, E e) {
        super.add(i, e);
        houseKeep();
    }

    /**
     * Makes a space available if the list is already full. Calling this prior the insertion avoids that the list exceeds its limits.
     */
    private void makeRoom() {
        while (isFull()) {
            pollFirst();
        }
    }

    /**
     * Makes the list fit its limits by removing last items in cases where the list might have exceeded its limits.
     */
    private void houseKeep() {
        while (size() > maxCapacity) {
            pollFirst();
        }
    }
}
