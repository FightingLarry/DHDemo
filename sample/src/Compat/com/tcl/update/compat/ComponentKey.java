package com.tcl.update.compat;


import android.content.ComponentName;

import java.util.Arrays;

public class ComponentKey {

    public final ComponentName componentName;
    public final UserHandleCompat user;

    private final int mHashCode;

    public ComponentKey(ComponentName componentName, UserHandleCompat user) {
        this.componentName = componentName;
        this.user = user;
        mHashCode = Arrays.hashCode(new Object[] {componentName, user});

    }


    @Override
    public int hashCode() {
        return mHashCode;
    }

    @Override
    public boolean equals(Object o) {
        ComponentKey other = (ComponentKey) o;
        return other.componentName.equals(componentName) && other.user.equals(user);
    }

    /**
     * Encodes a component key as a string of the form [flattenedComponentString#userId].
     */
    @Override
    public String toString() {
        return componentName.flattenToString() + "#" + user;
    }
}
