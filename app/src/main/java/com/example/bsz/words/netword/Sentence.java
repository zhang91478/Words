package com.example.bsz.words.netword;

/**
 * Created by 22948 on 2017/10/19.
 */

public class Sentence {
    private String content;//
    private String note;
    private String tts;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public Sentence(String content, String note, String tts) {
        this.content = content;
        this.note = note;
        this.tts = tts;
    }

    public Sentence() {
    }
}
