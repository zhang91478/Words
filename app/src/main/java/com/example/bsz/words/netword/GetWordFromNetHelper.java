package com.example.bsz.words.netword;


import android.os.Build;
import android.util.Log;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetWordFromNetHelper {
private String jsonString="";
    public String getWord(String word) throws IOException {

        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        final String urlPath = "http://dict-co.iciba.com/api/dictionary.php?"
                +"type=json&"+
                "key=6E6C5B2046BB4F9C6713593CCE4B27CD&w=" + word;

        Log.v("GetWordFromNetHelper","urlPath:"+urlPath);

        new Thread(){
            @Override
            public void run() {
                super.run();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int len = 0;
                HttpURLConnection connection = null;

                InputStream inStream = null;
                try {
                    URL url = new URL(urlPath);
                    connection = (HttpURLConnection)url.openConnection();
                    inStream = connection.getInputStream();
                    while ((len = inStream.read())!=-1){
                        byteArrayOutputStream.write(data,0,len);
                        inStream.close();
                    }
                    jsonString = new String(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();



     //   jsonString = new String(data);
        Log.v("GetWordFromNetHelper","jsonString:"+jsonString);
        Matcher matcher = pat.matcher(word);
        if (matcher.find()) {
            Log.v("GetWordFromNetHelper",word+"含有中文");

        }
        else {
            Log.v("GetWordFromNetHelper",word+"不含中文");
        }
        return jsonString;

    }
    private static List<WordEn> parseJsonToWordEn(String jsonString)throws Exception{
        List<WordEn> list = new ArrayList<>();
        JSONArray jsonArray = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            jsonArray = new JSONArray(jsonString);
        }
        assert jsonArray != null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            WordEn word = new WordEn(
                    jsonObject.getString("word_name"),
                    jsonObject.getString("exchange"),
                    jsonObject.getString("ph_en"),
                    jsonObject.getString("ph_am"),
                    jsonObject.getString("ph_en_mp3"),
                    jsonObject.getString("ph_am_mp3"),
                    jsonObject.getString("ph_tts_mp3"),
                    jsonObject.getString("parts")
            );
            list.add(word);
        }
        return list;
    }

    private static List<WordCh> parseJsonToWordCh(String jsonString)throws Exception{
        List<WordCh> list = new ArrayList<>();
        JSONArray jsonArray = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            jsonArray = new JSONArray(jsonString);
        }
        assert jsonArray != null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            WordCh word = new WordCh(
                    jsonObject.getString("word_name"),
                    jsonObject.getString("symbols"),
                    jsonObject.getString("word_symbol"),
                    jsonObject.getString("symbol_mp3"),
                    jsonObject.getString("parts"),
                    jsonObject.getString("net_means")
            );
            list.add(word);
        }
        return list;
    }
}
