package com.example.bsz.words;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentTrans extends Fragment {

    private String className = "FragmentTrans";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(className,"onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(className,"onCreateView");
        return inflater.inflate(R.layout.fragment_fragment_trans, container, false);
    }

    @Override
    public void onDetach() {
        Log.v(className,"onDetach");
        super.onDetach();
    }
}
