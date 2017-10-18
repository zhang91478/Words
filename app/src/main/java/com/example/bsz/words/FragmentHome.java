package com.example.bsz.words;


import android.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentHome extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("FragmentHome","onCreate");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        ListView listView = view.findViewById(R.id.fragment_home_list_item);
        SearchView searchView = view.findViewById(R.id.fragment_home_search);
        searchView.setFocusable(false);
        List<Map<String,Object>> list=getData();
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                list,R.layout.news_item,new String []{"news_title","news_info","news_Synopsis"},
                new int []{R.id.news_title,R.id.news_info,R.id.news_Synopsis});
        listView.setAdapter(simpleAdapter);

        Button button1= view.findViewById(R.id.fragment_home_button1);
        Button button2= view.findViewById(R.id.fragment_home_button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("FragmentHome","Create a new thread");
                        DBManger dbManger= new DBManger(getActivity(),"wordbook.db",null,30);
                        LoadingWordsHelper lh = new LoadingWordsHelper();
                        lh.LoadingWords(dbManger,"English");
                    }
                });
                thread.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDBHelper = new DBHelper(getActivity(),"wordbook.db",null,30);
                SQLiteDatabase sqLiteDatabase = myDBHelper.getWritableDatabase();
                myDBHelper.onUpgrade(sqLiteDatabase,sqLiteDatabase.getVersion(),sqLiteDatabase.getVersion());
                Toast.makeText(getActivity(),"已经更新词库版本",Toast.LENGTH_LONG).show();
            }
        });

        Log.v("FragmentHome","onCreateView");

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i=0;i<10;++i){
            Map<String, Object> map = new HashMap<>();
            map.put("news_title","一次表演");
            map.put("news_info","2012");
            map.put("news_Synopsis","egriuweruyweirwoeirhweuirgwe");
            list.add(map);
        }
        return list;
    }


}


