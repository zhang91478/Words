package com.example.bsz.words;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.SimpleExpandableListAdapter;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentMe extends Fragment {

    private List<WordOfMe> listOfMyWord;
    private String sql = "";
    private ExpandableListView expandableListView;
    private SimpleExpandableListAdapter adapter;
    private List<Map<String,Object>> GroupList;
    private List<List<Map<String,Object>>> ChildList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GroupList = new ArrayList<>();
        ChildList = new ArrayList<>();

        DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
        String sql = "SELECT * FROM myWords";
        listOfMyWord = dbManger.getWordOfMeFromDateBase(sql);

        for (int i = 0; i < listOfMyWord.size(); i++) {
            HashMap<String,Object> groupMap = new HashMap<>();
            groupMap.put("myWord", listOfMyWord.get(i).getWord());
            groupMap.put("time", listOfMyWord.get(i).getTime());
            GroupList.add(groupMap);

            List<Map<String,Object>> arrayList = new ArrayList<>();
            HashMap<String,Object> childMap = new HashMap<>();
            childMap.put("tags", listOfMyWord.get(i).getTags());
            childMap.put("phone", listOfMyWord.get(i).getPhone());
            childMap.put("trans", listOfMyWord.get(i).getTrans());
            childMap.put("note", listOfMyWord.get(i).getNote());
            arrayList.add(childMap);
            ChildList.add(arrayList);
        }
        Log.v("FragmentBook","getChildData()");



        adapter = new SimpleExpandableListAdapter(
                        getActivity(),
                        GroupList,
                        R.layout.me_item_super,
                        new String[]{"myWord","time"},
                        new int[]{R.id.myWord,R.id.myTime},
                        ChildList,
                        R.layout.me_item_child,
                        new String[]{"phone","tags","trans","note"},
                        new int[]{R.id.myPhone,R.id.myTags,R.id.myTrans,R.id.myNote});

        Log.v("FragmentMe","载入单词数"+listOfMyWord.size());
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
        String sql = "SELECT * FROM myWords";
        listOfMyWord = dbManger.getWordOfMeFromDateBase(sql);
        updateListViewItem(expandableListView);
        Log.v("FragmentMe","载入单词数"+listOfMyWord.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_fragment_me, container, false);
        Log.v("FragmentMe","final ExpandableListView listView ");
        expandableListView = view.findViewById(R.id.fragment_me_list_item);
        final ExpandableListView listView = expandableListView;
        Log.v("FragmentMe","final ExpandableListView listView "+ (listView == null));
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final String word = listOfMyWord.get(i).getWord();
                new AlertDialog.Builder(getActivity())
                        .setTitle("删除"+word)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
                                dbManger.deleteByWord(word);
                                listOfMyWord = dbManger.getWordOfMeFromDateBase("SELECT * FROM myWords");
                                updateListViewItem(listView);
                                Log.v("FragmentMe","final ExpandableListView listView "+ (listView == null));
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

                return true;
            }
        });



        SearchView searchView = view.findViewById(R.id.fragment_me_search);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
                sql = "SELECT * FROM myWords WHERE word  LIKE '%" +s
                        +"%' OR note LIKE '%"+s+"%'"
                        +" OR time LIKE '%"+s+"%'";
                listOfMyWord = dbManger.getWordOfMeFromDateBase(sql);
                Log.v("FragmentMe","载入单词数"+listOfMyWord.size());
                expandableListView = view.findViewById(R.id.fragment_me_list_item);
                updateListViewItem(expandableListView);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });{

        }

        expandableListView = view.findViewById(R.id.fragment_me_list_item);
        updateListViewItem(expandableListView);
        return view;
    }


    private void updateListViewItem(ExpandableListView ListView){

        GroupList.clear();
        ChildList.clear();
        for (int i = 0; i < listOfMyWord.size(); i++) {
            HashMap<String,Object> groupMap = new HashMap<>();
            groupMap.put("myWord", listOfMyWord.get(i).getWord());
            groupMap.put("time", listOfMyWord.get(i).getTime());
            GroupList.add(groupMap);

            List<Map<String,Object>> arrayList = new ArrayList<>();
            HashMap<String,Object> childMap = new HashMap<>();
            childMap.put("tags", listOfMyWord.get(i).getTags());
            childMap.put("phone", listOfMyWord.get(i).getPhone());
            childMap.put("trans", listOfMyWord.get(i).getTrans());
            childMap.put("note", listOfMyWord.get(i).getNote());
            arrayList.add(childMap);
            ChildList.add(arrayList);
        }
        Log.v("FragmentBook","getChildData()");
        ListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
