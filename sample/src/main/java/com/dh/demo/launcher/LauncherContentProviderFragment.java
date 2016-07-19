package com.dh.demo.launcher;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;
import com.dh.demo.launcher.contentprovider.DatabaseOperate;
import com.dh.demo.launcher.contentprovider.launcher.LauncherSettings;
import com.dh.demo.launcher.reflect.LauncherContentProviderManager;

/**
 * Created by yancai.liu on 2016/7/19.
 */

public class LauncherContentProviderFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_launcher_content_provider;
    }

    @Override
    public int getTitle() {
        return R.string.title_launcher_content_provider;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRootView().findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOperate.backup(getActivity());
                // query();
            }
        });


        getRootView().findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOperate.restore(getActivity());
            }
        });
    }


    private void query() {
        final ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = null;
        try {
            cursor = cr.query(LauncherSettings.Favorites.CONTENT_URI,
                    new String[] {LauncherSettings.Favorites.INTENT, LauncherSettings.Favorites.PROFILE_ID},
                    LauncherSettings.Favorites.ITEM_TYPE + "=?",
                    new String[] {LauncherSettings.Favorites.ITEM_TYPE_APPLICATION + ""}, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String intentString =
                            cursor.getString(cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.INTENT));
                    long serialNumber =
                            cursor.getInt(cursor.getColumnIndexOrThrow(LauncherSettings.Favorites.PROFILE_ID));
                    Log.d("yckk", intentString);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
