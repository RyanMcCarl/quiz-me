/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.util;

/**
 * Maybe Class somewhat reminiscent of the Maybe Class in Haskell.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public class Maybe<T> {

    private T item;
    private final boolean nothing;

    public Maybe() {
        nothing = true;
    }

    public Maybe(T obj) {
       nothing = false;
       item = obj; 
    }

    public boolean nothing() {
        return nothing;
    }

    public T just() {
        return item;
    }
}
