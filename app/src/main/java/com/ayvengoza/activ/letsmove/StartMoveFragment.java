package com.ayvengoza.activ.letsmove;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ang on 21.11.17.
 */

public class StartMoveFragment extends Fragment {

    public static StartMoveFragment newInstance(){
        Bundle args = new Bundle();
        StartMoveFragment fragment = new StartMoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_move, container, false);
        return view;
    }
}
