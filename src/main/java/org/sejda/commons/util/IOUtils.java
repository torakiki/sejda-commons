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

import org.sejda.commons.FastByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.sejda.commons.util.RequireUtils.requireNotNullArg;

/**
 * Utility class with I/O related static methods
 */
public final class IOUtils {
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
        // hide
    }

    /**
     * Null safe close of the given {@link Closeable}.
     *
     * @param closeable to be closed
     * @throws IOException
     */
    public static void close(Closeable closeable) throws IOException {
        if (nonNull(closeable)) {
            closeable.close();
        }
    }

    /**
     * Null safe close of the given {@link Closeable} suppressing any exception.
     *
     * @param closeable to be closed
     */
    public static void closeQuietly(Closeable closeable) {
        closeQuietly(closeable, null);
    }

    /**
     * Null safe close of the given {@link Closeable} handing the potential exception to the given consumer
     *
     * @param closeable to be closed
     * @param consumer  consumer for the potential {@link Exception}. If null the exception is logged as a warning.
     */
    public static void closeQuietly(Closeable closeable, Consumer<IOException> consumer) {
        try {
            close(closeable);
        } catch (IOException ioe) {
            ofNullable(consumer).ifPresentOrElse(c -> c.accept(ioe),
                    () -> LOG.warn("An error occurred while closing a Closeable resource", ioe));
        }
    }

    /**
     * @param input
     * @return the content of the input stream as a byte[]
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        try (FastByteArrayOutputStream output = new FastByteArrayOutputStream()) {
            input.transferTo(output);
            return output.toByteArray();
        }
    }

    /**
     * Copy the input stream data to the output stream
     *
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        requireNotNullArg(input, "Cannot copy a null input");
        requireNotNullArg(output, "Cannot copy to a null output");
        input.transferTo(output);
    }
}
