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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Andrea Vacondio
 *
 */
public class EitherTest {

    @Test
    public void fromLeft() {
        Either<String, Integer> victim = Either.left("Chuck Norris");
        assertFalse(victim.fromRight().isPresent());
        assertTrue(victim.fromLeft().isPresent());
        assertEquals("Chuck Norris", victim.fromLeft().get());
    }

    @Test
    public void fromRight() {
        Either<String, Integer> victim = Either.right(12);
        assertTrue(victim.fromRight().isPresent());
        assertFalse(victim.fromLeft().isPresent());
        assertEquals(12, (int) victim.fromRight().get());
    }

    @Test
    public void either() {
        Either<String, Integer> victim = Either.left("12");
        assertEquals(15, (int) victim.either(s -> Integer.parseInt(s) + 3, i -> i + 3));
        victim = Either.right(12);
        assertEquals(15, (int) victim.either(s -> Integer.parseInt(s) + 3, i -> i + 3));
    }
}
