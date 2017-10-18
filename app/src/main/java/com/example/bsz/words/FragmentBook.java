package com.example.bsz.words;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.SimpleExpandableListAdapter;
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
    ExpandableListView expandableListView = null;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(className,"onSaveInstanceState");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(className,"onCreate");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.v(className,"onHiddenChanged");
    }

    @Override
    public void onAttach(Context context) {
        Log.v(className,"onAttach");
        super.onAttach(context);
        DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
        sql = "SELECT * FROM allWords";
        listOfAllWord = dbManger.getWordOfAllFromDateBase(sql);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(className,"onCreateView");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_book, container, false);
                view.findViewById(R.id.fragment_book_list_item);

        expandableListView = view.findViewById(R.id.fragment_book_list_item);
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        });


        SearchView searchView = view.findViewById(R.id.fragment_book_search);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                DBManger dbManger = new DBManger(getActivity(),"wordbook.db",null,30);
                sql = "SELECT * FROM allWords WHERE word  LIKE '%"+s+"%' OR trans LIKE '%"+s+"%'";
                listOfAllWord = dbManger.getWordOfAllFromDateBase(sql);
                updateListViewItem(expandableListView);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });{

        }



        updateListViewItem(expandableListView);
        Log.v("FragmentBook","onCreate");
        return view;
    }

    @Override
    public void onDetach() {
        Log.v(className,"onDetach");
        super.onDetach();
    }

    private List<Map<String,Object>>getGroupData(){
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < listOfAllWord.size(); i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("allWord", listOfAllWord.get(i).getWord());
            list.add(map);
        }


        return list;
    }
    private List<List<Map<String,Object>>>getChildData(){
        List<List<Map<String,Object>>> list = new ArrayList<>();
        for (int i = 0; i < listOfAllWord.size(); i++) {
            List<Map<String,Object>> arrayList = new ArrayList<>();
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("tags", listOfAllWord.get(i).getTag());
            map.put("trans", listOfAllWord.get(i).getTrans());
            map.put("phone", listOfAllWord.get(i).getPhone());
            arrayList.add(map);
            list.add(arrayList);
        }
        return list;
    }


    private void updateListViewItem(ExpandableListView listView){
        SimpleExpandableListAdapter adapter =
                new SimpleExpandableListAdapter(
                        getActivity(),
                        getGroupData(),
                        R.layout.book_item_super,
                        new String[]{"allWord"},
                        new int[]{R.id.allWord},
                        getChildData(),
                        R.layout.book_item_child,
                        new String[]{"tags","trans","phone"},
                        new int[]{R.id.tags,R.id.trans,R.id.phone});
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
