/**
 * Copyright (C) 2011Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.util;

/**
 * A very poor mans imitation of Haskells Maybe Monad.
 *
 * This should allow one to avoid null pointer exceptions
 * without boilerplate and compose chains of work without
 * worrying about checking for nulls at each step.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public abstract class MaybeDo<T> {

    public Maybe<T> doIt(Maybe<T> obj) {
        if (obj.nothing()) {
            return obj;
        } else {
            return doFn(obj.just());
        }
    }

    public abstract Maybe<T> doFn(T arg);
}
