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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Andrea Vacondio
 */
public class IOUtilsTest {

    @Test
    public void copyNullInput() {
        assertThrows(IllegalArgumentException.class, () -> IOUtils.copy(null, new ByteArrayOutputStream()),
                "Cannot copy a null input");
    }

    @Test
    public void copyNullOutput() {
        assertThrows(IllegalArgumentException.class, () -> IOUtils.copy(new ByteArrayInputStream(new byte[1]), null),
                "Cannot copy to a null output");
    }

    @Test
    public void copy() throws IOException {
        byte[] data = { '3', '5' };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(new ByteArrayInputStream(data), out);
        assertArrayEquals(data, out.toByteArray());
    }

    @Test
    public void toByteArray() throws IOException {
        byte[] data = { '3', '5' };
        assertArrayEquals(data, IOUtils.toByteArray(new ByteArrayInputStream(data)));
    }

    @Test
    void closeCloseable() throws IOException {
        Closeable closeable = mock(Closeable.class);
        IOUtils.close(closeable);
        verify(closeable).close();
    }

    @Test
    @DisplayName("Closing null doesn't throw")
    void closeQuietlyNull() {
        IOUtils.closeQuietly(null);
    }

    @Test
    void closeQuietly() throws IOException {
        Closeable closeable = mock(Closeable.class);
        IOUtils.closeQuietly(closeable);
        verify(closeable).close();
    }

    @Test
    @DisplayName("Closing exception doesn't rethrow")
    void closeQuietlyException() throws IOException {
        Closeable closeable = mock(Closeable.class);
        doThrow(new IOException()).when(closeable).close();
        IOUtils.closeQuietly(closeable);
        verify(closeable).close();
    }

    @Test
    void closeQuietlyConsumer() throws IOException {
        Closeable closeable = mock(Closeable.class);
        doThrow(new IOException()).when(closeable).close();
        Consumer<IOException> consumer = mock(Consumer.class);
        IOUtils.closeQuietly(closeable, consumer);
        verify(closeable).close();
        verify(consumer).accept(any());
    }
}
