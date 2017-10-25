package com.example.bsz.words.netword;

/**
 * Created by 22948 on 2017/10/19.
 *
 * 'word_name':'' #单词
 'exchange': '' #单词的各种时态
 'symbols':'' #单词各种信息 下面字段都是这个字段下面的
 'ph_en': '' #英式音标
 'ph_am': '' #美式音标
 'ph_en_mp3':'' #英式发音
 'ph_am_mp3': '' #美式发音
 'ph_tts_mp3': '' #TTS发音
 'parts':'' #词的各种意思
 */

public class WordEn {
    private String word_name;
    private String exchange;
    private String ph_en;
    private String ph_am;
    private String ph_en_mp3;
    private String ph_am_mp3;
    private String ph_tts_mp3;
    private String parts;

    public WordEn(String word_name,
                  String exchange,
                  String ph_en,
                  String ph_am,
                  String ph_en_mp3,
                  String ph_am_mp3,
                  String ph_tts_mp3,
                  String parts) {
        this.word_name = word_name;
        this.exchange = exchange;
        this.ph_en = ph_en;
        this.ph_am = ph_am;
        this.ph_en_mp3 = ph_en_mp3;
        this.ph_am_mp3 = ph_am_mp3;
        this.ph_tts_mp3 = ph_tts_mp3;
        this.parts = parts;
    }

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPh_en() {
        return ph_en;
    }

    public void setPh_en(String ph_en) {
        this.ph_en = ph_en;
    }

    public String getPh_am() {
        return ph_am;
    }

    public void setPh_am(String ph_am) {
        this.ph_am = ph_am;
    }

    public String getPh_en_mp3() {
        return ph_en_mp3;
    }

    public void setPh_en_mp3(String ph_en_mp3) {
        this.ph_en_mp3 = ph_en_mp3;
    }

    public String getPh_am_mp3() {
        return ph_am_mp3;
    }

    public void setPh_am_mp3(String ph_am_mp3) {
        this.ph_am_mp3 = ph_am_mp3;
    }

    public String getPh_tts_mp3() {
        return ph_tts_mp3;
    }

    public void setPh_tts_mp3(String ph_tts_mp3) {
        this.ph_tts_mp3 = ph_tts_mp3;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }
}
