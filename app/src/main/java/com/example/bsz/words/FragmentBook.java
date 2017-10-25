package com.example.bsz.words;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FragmentBook extends Fragment {
    private String className = "FragmentBook";
    private List<WordOfAll> listOfAllWord;
    private String sql ="";

    private ExpandableListView expandableListView;
    private SimpleExpandableListAdapter adapter;
    private List<Map<String,Object>> groupList;
    private List<List<Map<String,Object>>> childList;
    private ListView landListView;

    private SimpleAdapter landAdapter;

    private Boolean isLand;//是否横屏


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(className,"onCreate");

        isLand = getActivity()
                .getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;

        groupList = new ArrayList<>();
        childList = new ArrayList<>();

            DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
            sql = "SELECT * FROM allWords";
            listOfAllWord = dbManger.getWordOfAllFromDateBase(sql);

            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> groupMap = new HashMap<>();
                groupMap.put("allWord", listOfAllWord.get(i).getWord());
                groupList.add(groupMap);

                List<Map<String,Object>> arrayList = new ArrayList<>();
                HashMap<String,Object> childMap = new HashMap<>();
                childMap.put("tags", listOfAllWord.get(i).getTag());
                childMap.put("trans", listOfAllWord.get(i).getTrans());
                childMap.put("phone", listOfAllWord.get(i).getPhone());
                arrayList.add(childMap);
                childList.add(arrayList);
            }

            if(isLand){
                landAdapter = new SimpleAdapter(
                        getActivity(),
                        groupList,
                        R.layout.book_item_super,
                        new String[]{"allWord"},
                        new int[]{R.id.allWord}
                );
            }else {
                adapter = new SimpleExpandableListAdapter(
                        getActivity(),
                        groupList,
                        R.layout.book_item_super,
                        new String[]{"allWord"},
                        new int[]{R.id.allWord},
                        childList,
                        R.layout.book_item_child,
                        new String[]{"tags","trans","phone"},
                        new int[]{R.id.tags,R.id.trans,R.id.phone});
            }
        }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.v(className,"onHiddenChanged");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_book, container, false);

        final AdapterView.OnItemLongClickListener longClickListener =
                new AdapterView.OnItemLongClickListener() {//长按事件
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final WordOfMe wordOfMe =new WordOfMe();
                        final EditText editText = new EditText(getActivity());
                        editText.setTextSize(33);
                        wordOfMe.setWord(listOfAllWord.get(i).getWord());
                        wordOfMe.setPhone(listOfAllWord.get(i).getPhone());
                        wordOfMe.setTags(listOfAllWord.get(i).getTag());
                        wordOfMe.setTrans(listOfAllWord.get(i).getTrans());
                        new AlertDialog.Builder(getActivity())
                                .setTitle("保存单词"+wordOfMe.getWord())
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        wordOfMe.setNote(editText.getText().toString());
                                        Date date = new Date();
                                        SimpleDateFormat DateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss",Locale.CHINA);
                                        String time = DateFormat.format(date);
                                        wordOfMe.setTime(time);
                                        DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
                                        dbManger.Insert(wordOfMe);
                                        Toast.makeText(getActivity(),
                                                wordOfMe.getWord()+
                                                        "已成功保存",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                        return true;
                    }
                };

        SearchView searchView = view.findViewById(R.id.fragment_book_search);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
                sql = "SELECT * FROM allWords WHERE word  LIKE '%"+s+"%' OR trans LIKE '%"+s+"%'";
                listOfAllWord = dbManger.getWordOfAllFromDateBase(sql);
                if(isLand){
                    updateListViewItem(landAdapter);
                }
                else {
                    updateListViewItem(adapter);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        if(isLand){
            landListView = view.findViewById(R.id.fragment_book_list_item_land);
            landListView.setAdapter(landAdapter);
            landListView.setOnItemLongClickListener(longClickListener);

            final TextView textView1 = view.findViewById(R.id.tags_land);
            final TextView textView2 = view.findViewById(R.id.phone_land);
            final TextView textView3 = view.findViewById(R.id.trans_land);
            landListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    textView1.setText(listOfAllWord.get(i).getTag());
                    textView2.setText(listOfAllWord.get(i).getPhone());
                    textView3.setText(listOfAllWord.get(i).getTrans());
                }
            });
        }
        else {
            expandableListView = view.findViewById(R.id.fragment_book_list_item);
            expandableListView.setOnItemLongClickListener(longClickListener);
            expandableListView.setAdapter(adapter);
            Log.v("FragmentBook","onCreate");
        }
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v("FragmentBook","onConfigurationChanged");

    }

    private void updateListViewItem(SimpleAdapter simpleAdapter){
        groupList.clear();
        for (int i = 0; i < listOfAllWord.size(); i++) {
            HashMap<String, Object> groupMap = new HashMap<>();
            groupMap.put("allWord", listOfAllWord.get(i).getWord());
            groupList.add(groupMap);
        }
        simpleAdapter.notifyDataSetChanged();
    }

    private void updateListViewItem(SimpleExpandableListAdapter simpleExpandableListAdapter){


            groupList.clear();
            childList.clear();
            for (int i = 0; i < listOfAllWord.size(); i++) {
                HashMap<String,Object> groupMap = new HashMap<>();
                groupMap.put("allWord", listOfAllWord.get(i).getWord());
                groupList.add(groupMap);

                List<Map<String,Object>> arrayList = new ArrayList<>();
                HashMap<String,Object> childMap = new HashMap<String,Object>();
                childMap.put("tags", listOfAllWord.get(i).getTag());
                childMap.put("trans", listOfAllWord.get(i).getTrans());
                childMap.put("phone", listOfAllWord.get(i).getPhone());
                arrayList.add(childMap);
                childList.add(arrayList);
            }
        simpleExpandableListAdapter.notifyDataSetChanged();
    }
}


