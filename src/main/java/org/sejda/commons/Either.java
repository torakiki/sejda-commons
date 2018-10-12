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

import java.util.Optional;
import java.util.function.Function;

/**
 * 
 * @author Andrea Vacondio
 * @see https://stackoverflow.com/questions/48143268/java-tagged-union-sum-types
 */
public abstract class Either<A, B> {

    private Either() {
        // hide
    }

    public abstract <C> C either(Function<? super A, ? extends C> left, Function<? super B, ? extends C> right);

    public static <A, B> Either<A, B> left(A value) {
        return new Either<>() {
            @Override
            public <C> C either(Function<? super A, ? extends C> left, Function<? super B, ? extends C> right) {
                return left.apply(value);
            }
        };
    }

    public static <A, B> Either<A, B> right(B value) {
        return new Either<>() {
            @Override
            public <C> C either(Function<? super A, ? extends C> left, Function<? super B, ? extends C> right) {
                return right.apply(value);
            }
        };
    }

    public Optional<A> fromLeft() {
        return this.either(Optional::of, value -> Optional.empty());
    }

    public Optional<B> fromRight() {
        return this.either(value -> Optional.empty(), Optional::of);
    }
}
