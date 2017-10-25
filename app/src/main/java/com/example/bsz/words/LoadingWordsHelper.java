package com.example.bsz.words;



import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class LoadingWordsHelper {
    private String user;
    private String password;
    private String url;
    private Connection connection;
    LoadingWordsHelper() {
        this.user = "root";
        this.password = "Zhangshubo";
        this.url = "jdbc:mysql://123.206.43.242:3336/Words?autoReconnect=true";
    }


    void LoadingWords(final DBManger dbManger, String tableName){
        connection = null;
        final String sql = "SELECT * FROM "+tableName;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(url,user,password);
                }
                catch (ClassNotFoundException e){
                    Log.e("LoadingWordsHelper","jdbc加载失败"+e.toString());
                }
                catch(SQLException e){
                    Log.e("LoadingWordsHelper","SQL连接错误"+e.toString());
                }
                try {
                    if(null != connection){
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery(sql);
                        while (rs.next()){
                            WordOfAll word = new WordOfAll();
                            word.setWord(rs.getString("word"));
                            word.setTag(rs.getString("tags"));
                            word.setPhone(rs.getString("phone"));
                            word.setTrans(rs.getString("trans"));
                            dbManger.Insert(word);
                        }
                    }
                } catch (SQLException e) {
                    Log.e("LoadingWordHelper",e.toString());
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
