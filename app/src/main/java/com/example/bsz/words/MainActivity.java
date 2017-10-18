package com.example.bsz.words;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String showingFragmentTag ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("MainActivity","onCreate");
        FragmentManager fragmentManager = this.getFragmentManager();

        FragmentHome fragmentHome = new FragmentHome();
        FragmentTrans fragmentTrans = new FragmentTrans();
        FragmentBook fragmentBook = new FragmentBook();
        FragmentMe fragmentMe = new FragmentMe();

        fragmentManager.beginTransaction().add(R.id.main_fragment,fragmentHome,"Home").commit();

        showingFragmentTag = fragmentHome.getTag();
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button_home);
        Button button2 = (Button)findViewById(R.id.button_trans);
        Button button3 = (Button)findViewById(R.id.button_book);
        Button button4 = (Button)findViewById(R.id.button_me);



        buttonAction(button1,fragmentHome,"Home");
        buttonAction(button2,fragmentTrans,"Trans");
        buttonAction(button3,fragmentBook,"Book");
        buttonAction(button4,fragmentMe,"Me");

    }
    private void buttonAction(Button bt, final Fragment newFragment, final String tag){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Log.v("buttonAction","now Fragment is "+ showingFragmentTag);
                Fragment fragment = fm.findFragmentByTag(showingFragmentTag);
                if(!newFragment.isAdded()){
                    fm.beginTransaction()
                            .hide(fragment)
                            .add(R.id.main_fragment,newFragment,tag)
                            .commit();
                    showingFragmentTag = tag;
                    Log.v("buttonAction","Fragment is not added,"+
                            fragment.getTag()+" will be hide");
                }
                else if(!newFragment.getTag().equals(showingFragmentTag)){
                    fm.beginTransaction()
                            .hide(fragment)
                            .show(newFragment)
                            .commit();
                    showingFragmentTag = newFragment.getTag();
                    Log.v("buttonAction","Fragment is added,"
                            +fragment.getTag()+" will be hide");
                }
            }
        });

    }
}
