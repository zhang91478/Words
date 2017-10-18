package com.example.bsz.words;

class WordOfAll {
    private String word;
    private String tag;
    private String phone;
    private String trans;



    WordOfAll() {
        word = "";
        tag = "";
        trans = "";
        phone = "";
    }



    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    String getTag() {
        return tag;
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    String getTrans() {
        return trans;
    }

    void setTrans(String trans) {
        this.trans = trans;
    }

}
