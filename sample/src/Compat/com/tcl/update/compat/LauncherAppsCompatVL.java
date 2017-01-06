/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LauncherAppsCompatVL extends LauncherAppsCompatV16 {

    protected LauncherApps mLauncherApps;


    LauncherAppsCompatVL(Context context) {
        super(context);
        mLauncherApps = (LauncherApps) context.getSystemService("launcherapps");
    }

    public List<LauncherActivityInfoCompat> getActivityList(String packageName, UserHandleCompat user) {
        List<LauncherActivityInfo> list = mLauncherApps.getActivityList(packageName, user.getUser());
        if (list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList<LauncherActivityInfoCompat> compatList = new ArrayList<LauncherActivityInfoCompat>(list.size());
        for (LauncherActivityInfo info : list) {
            compatList.add(new LauncherActivityInfoCompatVL(info));
        }
        return compatList;
    }
}

