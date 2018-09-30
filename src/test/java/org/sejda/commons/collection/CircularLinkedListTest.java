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

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Andrea Vacondio
 *
 */
public class CircularLinkedListTest {

    @Test(expected = IllegalArgumentException.class)
    public void wrongCapacity() {
        new CircularLinkedList<>(0);
    }

    @Test
    public void add() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        assertTrue(victim.add(1));
        assertTrue(victim.add(2));
        assertTrue(victim.add(3));
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(1));
    }

    @Test
    public void offer() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        assertTrue(victim.offer(1));
        assertTrue(victim.offer(2));
        assertTrue(victim.offer(3));
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(1));
    }

    @Test
    public void addLast() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        victim.addLast(1);
        victim.addLast(2);
        victim.addLast(3);
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(1));
    }

    @Test
    public void offerLast() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        victim.offerLast(1);
        victim.offerLast(2);
        victim.offerLast(3);
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(1));
    }

    @Test
    public void addFirst() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        victim.addFirst(1);
        victim.addFirst(2);
        victim.addFirst(3);
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(2));
    }

    @Test
    public void offerFirst() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        victim.offerFirst(1);
        victim.offerFirst(2);
        victim.offerFirst(3);
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(2));
    }

    @Test
    public void push() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        victim.push(1);
        victim.push(2);
        victim.push(3);
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(2));
    }

    @Test
    public void addAll() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(2);
        victim.addAll(Arrays.asList(1, 2, 3));
        assertEquals(2, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(1));
    }

    @Test
    public void addAllIndex() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(3);
        victim.add(100);
        victim.add(101);
        victim.add(102);
        victim.addAll(1, Arrays.asList(1, 2, 3));
        assertEquals(3, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(100));
        assertFalse(victim.contains(1));
        assertFalse(victim.contains(2));
        assertEquals(Integer.valueOf(3), victim.get(0));
        assertEquals(Integer.valueOf(101), victim.get(1));
        assertEquals(Integer.valueOf(102), victim.get(2));
    }

    @Test
    public void addIndex() {
        CircularLinkedList<Integer> victim = new CircularLinkedList<>(3);
        victim.add(1);
        victim.add(2);
        victim.add(3);
        victim.add(1, 100);
        assertEquals(3, victim.size());
        assertTrue(victim.isFull());
        assertFalse(victim.contains(1));
        assertEquals(Integer.valueOf(100), victim.get(0));
        assertEquals(Integer.valueOf(2), victim.get(1));
        assertEquals(Integer.valueOf(3), victim.get(2));
    }
}
