package com.dh.demo;

import android.support.v4.app.Fragment;

import com.dh.baseactivity.BaseFragmentActivity;

import java.io.IOException;
import java.io.PushbackInputStream;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void initializeStartingFragment() {
        try {
            Fragment fragment = new MainFragment();
            loadFragment(fragment, null);

            // readLine(new PushbackInputStream(getResources().getAssets().open("1.txt")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readLine(PushbackInputStream in) throws IOException {
        char buf[] = new char[128];
        int room = buf.length;
        int offset = 0;
        int c;
        loop: while (true) {
            switch (c = in.read()) {
                case -1:
                case '\n':
                    break loop;
                case '\r':
                    int c2 = in.read();
                    if ((c2 != '\n') && (c2 != -1)) in.unread(c2);
                    break loop;
                default:
                    if (--room < 0) {
                        char[] lineBuffer = buf;
                        buf = new char[offset + 128];
                        room = buf.length - offset - 1;
                        System.arraycopy(lineBuffer, 0, buf, 0, offset);

                    }
                    buf[offset++] = (char) c;
                    break;
            }
        }
        if ((c == -1) && (offset == 0)) return null;
        return String.copyValueOf(buf, 0, offset);
    }
}
