/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.tcl.update.compat;

import android.os.Build;

/**
 * Various utilities shared amongst the Launcher's classes.
 */
public final class Utilities {


    // public static boolean isNycMR1OrAbove() {
    // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    // }
    //
    // public static boolean isNycOrAbove() {
    // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    // }

    public static final boolean ATLEAST_MARSHMALLOW = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

    public static final boolean ATLEAST_LOLLIPOP_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;

    public static final boolean ATLEAST_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    public static final boolean ATLEAST_KITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public static final boolean ATLEAST_JB_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;

    public static final boolean ATLEAST_JB_MR2 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;



}
