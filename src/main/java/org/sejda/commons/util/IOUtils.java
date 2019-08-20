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

import static java.util.Objects.nonNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.sejda.commons.FastByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param closeable
     *            to be closed
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
     * @param closeable
     *            to be closed
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (nonNull(closeable)) {
                closeable.close();
            }
        } catch (IOException ioe) {
            LOG.warn("An error occured while closing a Closeable resource", ioe);
        }
    }

    /**
     * @param input
     * @return the content of the input stream as a byte[]
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        try (FastByteArrayOutputStream output = new FastByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer, 0, 8192)) >= 0) {
                output.write(buffer, 0, read);
            }
            // java9
            // input.transferTo(output);
            return output.toByteArray();
        }
    }

}
