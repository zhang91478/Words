package com.example.bsz.words;

class WordOfMe {
    private String word;
    private String time;
    private String note;
    private String trans;
    private String phone;
    private String tags;


    String getTrans() {
        return trans;
    }

    void setTrans(String trans) {
        this.trans = trans;
    }

    String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    String getTags() {
        return tags;
    }

    void setTags(String tags) {
        this.tags = tags;
    }

    WordOfMe() {
        word = "";
        time = "";
        note = "";
    }

    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    String getTime() {
        return time;
    }

    void setTime(String time) {
        this.time = time;
    }

    String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }


}

