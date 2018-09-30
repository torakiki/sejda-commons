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

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple object pool implementation. It requires a {@link Supplier} used to create instances to pool and when an object is requested but the pool is exhausted.
 * 
 * @author Andrea Vacondio
 */
public class Pool<T> {
    private static final Logger LOG = LoggerFactory.getLogger(Pool.class);

    private final ArrayBlockingQueue<T> pool;
    private Supplier<T> supplier;

    private Optional<Consumer<T>> applyOnGive = Optional.empty();

    public Pool(Supplier<T> creator, int poolsize) {
        requireNotNullArg(creator, "Pool objects creator cannot be null");
        this.pool = new ArrayBlockingQueue<>(poolsize);
        this.supplier = creator;
    }

    /**
     * @return the instance borrowed from the pool
     */
    public T borrow() {
        return Optional.ofNullable(this.pool.poll()).orElseGet(supplier);
    }

    /**
     * Returns the object to the pool
     * 
     * @param object
     */
    public void give(T object) {
        applyOnGive.ifPresent(c -> c.accept(object));
        if (!this.pool.offer(object)) {
            LOG.info("Poll is already full, cannot return borrowed instance");
        }
    }

    /**
     * Configure the pool to apply the given consumer to returned objects. This might be useful to restore initial status on returned objects.
     * 
     * @param applyOnGive
     * @return the pool
     */
    public Pool<T> onGive(Consumer<T> applyOnGive) {
        this.applyOnGive = Optional.ofNullable(applyOnGive);
        return this;
    }
}
