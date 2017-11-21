package com.ayvengoza.activ.letsmove;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartMoveActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return StartMoveFragment.newInstance();
    }
}
