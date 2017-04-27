package com.larry.light.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class LightLoader<D> extends AsyncTaskLoader<D> {

    public LightLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(D d) {
        if (!isReset()) {
            super.deliverResult(d);
        }
    }

    @Override
    public D loadInBackground() {
        return null;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }
}
