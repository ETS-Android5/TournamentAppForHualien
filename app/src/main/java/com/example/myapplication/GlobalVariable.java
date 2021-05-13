package com.example.myapplication;
import android.app.Application;

public class GlobalVariable extends Application {
    private int nowLevel = 0;         // Orientation - Level

    //修改 變數値
    public void setLevel(int nLe){
        this.nowLevel = nLe;
    }

    //取得 變數值
    public int getLevel() {
        return nowLevel;
    }
}