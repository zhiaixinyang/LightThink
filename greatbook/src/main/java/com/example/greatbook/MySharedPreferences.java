package com.example.greatbook;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.greatbook.utils.LogUtils;

/**
 * Created by MDove on 2016/4/1.
 */
public class MySharedPreferences {
    public static SharedPreferences sharedPreferences = null;

    public static SharedPreferences getFristActivityInstance() {
        if (sharedPreferences == null) {
            synchronized (MySharedPreferences.class) {
                if (sharedPreferences == null) {
                    sharedPreferences = App.getInstance().getContext().getSharedPreferences("count", App.getInstance().getContext().MODE_PRIVATE);
                }
            }
        }
        return sharedPreferences;
    }

    private static String CUR_WORDS_KEY = "cur_words_key";
    private static String CUR_LEVEL_KEY = "cur_level_key";

    public static int getCurWords() {
        Context context = App.getInstance().getContext();
        int curWords = context.getSharedPreferences(CUR_WORDS_KEY, context.MODE_PRIVATE)
                .getInt(CUR_WORDS_KEY, 0);
        return curWords;
    }

    public static void putCurWords(int addWords) {
        Context context = App.getInstance().getContext();

        int curLevel = getCurLevel();
        int curWords = getCurWords();
        int upNeedWords = context.getResources().getInteger(R.integer.up_level_need);
        if (getCurUseWords(curWords, curLevel) + addWords >= curLevel * upNeedWords) {
            putCurLevel(curLevel + 1);
        }

        context.getSharedPreferences(CUR_WORDS_KEY, context.MODE_PRIVATE)
                .edit().putInt(CUR_WORDS_KEY, curWords + addWords).commit();
    }

    public static int getCurLevel() {
        Context context = App.getInstance().getContext();
        int curWords = context.getSharedPreferences(CUR_LEVEL_KEY, context.MODE_PRIVATE)
                .getInt(CUR_LEVEL_KEY, 1);
        return curWords;
    }

    public static void putCurLevel(int level) {
        Context context = App.getInstance().getContext();
        context.getSharedPreferences(CUR_LEVEL_KEY, context.MODE_PRIVATE)
                .edit().putInt(CUR_LEVEL_KEY, level).commit();
    }

    //使用递归的方式计算当前等级的下字数
    private static int getCurUseWords(int curWords, int curLevel) {
        if (curLevel == 2) {
            return curWords - App.getInstance().getContext().getResources().getInteger(R.integer.up_level_need);
        }
        if (curLevel == 1) {
            return curLevel;
        }
        return curWords - getCurUseWords(curWords, curLevel);
    }
}
