package com.example.bsz.words.netword;

/**
 * Created by 22948 on 2017/10/19.
 * word_name':'' #所查的词
 'symbols':'' #词各种信息 下面字段都是这个字段下面的
 'word_symbol': '' #拼音
 'symbol_mp3': '' #发音
 'parts':'' #汉字的各种翻译 数组
 'net_means': '' #网络释义
 */

public class WordCh {
    private String word_name;
    private String symbols;
    private String word_symbol;
    private String symbol_mp3;
    private String parts;
    private String net_means;

    public WordCh(String word_name, String symbols, String word_symbol, String symbol_mp3, String parts, String net_means) {
        this.word_name = word_name;
        this.symbols = symbols;
        this.word_symbol = word_symbol;
        this.symbol_mp3 = symbol_mp3;
        this.parts = parts;
        this.net_means = net_means;
    }

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public String getWord_symbol() {
        return word_symbol;
    }

    public void setWord_symbol(String word_symbol) {
        this.word_symbol = word_symbol;
    }

    public String getSymbol_mp3() {
        return symbol_mp3;
    }

    public void setSymbol_mp3(String symbol_mp3) {
        this.symbol_mp3 = symbol_mp3;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getNet_means() {
        return net_means;
    }

    public void setNet_means(String net_means) {
        this.net_means = net_means;
    }
}
