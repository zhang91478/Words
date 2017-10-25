package com.example.bsz.words.netword;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22948 on 2017/10/19.
 */

public class GetSentenceEveryDay {

    public Sentence getSentence(String date){
        Sentence sentence = new Sentence();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024*3];
        int len = 0;
        String urlPath ="http://open.iciba.com/dsapi/?date="+date;
        Log.v("GetSentenceEveryDay","urlPath :"+urlPath);
        URL url = null;
        try {
            url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            Log.v("GetSentenceEveryDay","json:"+ new String(data));
            readJson(new String(data),sentence);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GetSentenceEveryDay","get Json :"+e.toString());
        }
        return sentence;
    }

    private void readJson(String json,Sentence sentence) {
        String temp = "";
        for (int i = 0; i < json.length()-4; i++) {
            if((json.charAt(i)=='c'
                    &&json.charAt(i+1)=='o'
                    &&json.charAt(i+2)=='n'
                    &&json.charAt(i+3)=='t')){

                int j = i+10;
                while (json.charAt(j)!='"'){
                    temp = temp + json.charAt(j);
                    j++;
                }
                i = j;
                sentence.setContent(temp);
                Log.v("readJson","翻译："+temp);
                Log.v("readJson","句子："+sentence.getContent());
            }
            temp = "";
            if((json.charAt(i)=='n'
                    &&json.charAt(i+1)=='o'
                    &&json.charAt(i+2)=='t'
                    &&json.charAt(i+3)=='e')){

                int j = i+7;
                while (json.charAt(j)!='"'){
                    temp = temp + json.charAt(j);
                    j++;
                }

                Log.v("readJson","翻译："+temp);
                sentence.setNote(decodeUnicode(temp));
                Log.v("readJson","翻译："+sentence.getNote());
                break;
            }
        }
    }

    private String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(Character.valueOf(letter).toString());
            start = end;
        }
        return buffer.toString();
    }


}

