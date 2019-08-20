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
package org.sejda.commons.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.sejda.commons.util.RequireUtils.requireArg;
import static org.sejda.commons.util.RequireUtils.requireIOCondition;
import static org.sejda.commons.util.RequireUtils.requireNotBlank;
import static org.sejda.commons.util.RequireUtils.requireNotNullArg;
import static org.sejda.commons.util.RequireUtils.requireState;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * @author Andrea Vacondio
 *
 */
public class RequireUtilsTest {

    @Test
    public void nullArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            requireNotNullArg(null, "message");
        }, "message");

    }

    @Test
    public void falseConditionArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            requireArg(false, "message");
        }, "message");

    }

    @Test
    public void nullArgNotBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            requireNotBlank(null, "message");
        }, "message");

    }

    @Test
    public void emptyArgNotBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            requireNotBlank("", "message");
        }, "message");

    }

    @Test
    public void blankArgNotBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            requireNotBlank(" ", "message");
        }, "message");

    }

    @Test
    public void faseConditionIO() {
        assertThrows(IOException.class, () -> {
            requireIOCondition(false, "message");
        }, "message");

    }

    @Test
    public void falseState() {
        assertThrows(IllegalStateException.class, () -> {
            requireState(false, "message");
        }, "message");
    }

    @Test
    public void positiveArg() throws IOException {
        requireArg(true, "message");
        requireNotNullArg(new Object(), "message");
        requireNotBlank("ChuckNorris", "message");
        requireIOCondition(true, "message");
        requireState(true, "message");
    }
}
