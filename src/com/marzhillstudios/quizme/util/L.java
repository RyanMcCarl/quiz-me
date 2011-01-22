/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme.util;

import android.util.Log;

/**
 * Utility logging class.
 *
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */

public class L {

    public static void i(String tag, String msg, Object... args) {
        L.i(tag, msg, args);
    }

    public static void d(String tag, String msg, Object... args) {
        L.d(tag, msg, args);
    }

    public static void w(String tag, String msg, Object... args) {
        L.w(tag, msg, args);
    }

    public static void v(String tag, String msg, Object... args) {
        L.v(tag, msg, args);
    }
}

