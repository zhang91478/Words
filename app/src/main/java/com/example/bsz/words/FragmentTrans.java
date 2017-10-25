package com.example.bsz.words;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class FragmentTrans extends Fragment {

    private String className = "FragmentTrans";
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(className,"onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(className,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_fragment_trans, container, false);
        webView= view.findViewById(R.id.fragment_trans_web);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webView.loadUrl("http://wap.chinadaily.com.cn/");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl("http://europe.chinadaily.com.cn/");
        //设置Web视图


        return view;
    }

    @Override
    public void onDetach() {
        Log.v(className,"onDetach");
        super.onDetach();
    }
}
