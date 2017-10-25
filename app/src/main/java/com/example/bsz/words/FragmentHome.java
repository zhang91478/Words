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


import com.example.bsz.words.netword.GetSentenceEveryDay;
import com.example.bsz.words.netword.Sentence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class FragmentHome extends Fragment {

    private static Sentence sentence= new Sentence();
    private static String defaultEndDate = "";
    private List<Map<String, Object>> list;
    private SimpleAdapter simpleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("FragmentHome","onCreate");
        list = new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Date dNow = new Date();   //当前时间
                    Date dBefore = new Date();
                    for (int i=0;i<6;++i){
                    Calendar calendar = Calendar.getInstance(); //得到日历
                    calendar.setTime(dNow);//把当前时间赋给日历
                    calendar.add(Calendar.DAY_OF_MONTH, -i);  //设置为前一天
                    dBefore = calendar.getTime();   //得到前一天的时间

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); //设置时间格式

                    defaultEndDate = sdf.format(dBefore); //格式化当前时间

                    Log.v("FragmentHome","get Sentence"+defaultEndDate);
                    sentence = new GetSentenceEveryDay().getSentence(defaultEndDate);

                    Map<String, Object> map = new HashMap<>();
                    map.put("news_title",defaultEndDate);
                    map.put("news_info",sentence.getContent());
                    map.put("news_Synopsis",sentence.getNote());
                    list.add(map);
                    }

                }
            }).start();

        simpleAdapter = new SimpleAdapter(getActivity(),
                list,R.layout.news_item,new String []{"news_title","news_info","news_Synopsis"},
                new int []{R.id.news_title,R.id.news_info,R.id.news_Synopsis});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        ListView listView = view.findViewById(R.id.fragment_home_list_item);
        SearchView searchView = view.findViewById(R.id.fragment_home_search);
        searchView.setFocusable(false);


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

        for (int i=0;i<6;++i){
            Date dNow = new Date();   //当前时间
            Date dBefore = new Date();

            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -i);  //设置为前一天
            dBefore = calendar.getTime();   //得到前一天的时间

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); //设置时间格式

            defaultEndDate = sdf.format(dBefore); //格式化当前时间

            new Thread(new Runnable() {
                @Override
                public void run() {
                    sentence = new GetSentenceEveryDay().getSentence(defaultEndDate);
                }
            }).start();

            Map<String, Object> map = new HashMap<>();
            map.put("news_title",defaultEndDate);
            map.put("news_info",sentence.getContent());
            map.put("news_Synopsis",sentence.getNote());
            list.add(map);
        }
        return list;
    }


}


