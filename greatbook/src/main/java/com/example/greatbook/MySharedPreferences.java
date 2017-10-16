package com.example.greatbook;

import android.content.Context;

/**
 * Created by MDove on 2016/4/1.
 */
public class MySharedPreferences {
    private static String CUR_WORDS_KEY = "cur_words_key";
    private static String SYNC_WORDS_KEY = "sync_words_key";
    private static String CUR_LEVEL_KEY = "cur_level_key";
    private static String IS_LOGIN_KEY = "is_login_key";

    public static boolean isLogin() {
        Context context = App.getInstance().getContext();
        boolean isLogin = context.getSharedPreferences(IS_LOGIN_KEY, context.MODE_PRIVATE)
                .getBoolean(IS_LOGIN_KEY, false);
        return isLogin;
    }

    public static void setLogin(boolean isLogin) {
        Context context = App.getInstance().getContext();

        context.getSharedPreferences(IS_LOGIN_KEY, context.MODE_PRIVATE)
                .edit().putBoolean(IS_LOGIN_KEY, isLogin)
                .commit();
    }

    public static int getSyncWords() {
        Context context = App.getInstance().getContext();
        int curWords = context.getSharedPreferences(SYNC_WORDS_KEY, context.MODE_PRIVATE)
                .getInt(SYNC_WORDS_KEY, 0);
        return curWords;
    }

    public static void putSyncWords(int addWords) {
        Context context = App.getInstance().getContext();

        int curSyncWords = getSyncWords();

        context.getSharedPreferences(SYNC_WORDS_KEY, context.MODE_PRIVATE)
                .edit().putInt(SYNC_WORDS_KEY, curSyncWords + addWords).commit();
    }

    public static void resetSyncWords() {
        Context context = App.getInstance().getContext();

        context.getSharedPreferences(SYNC_WORDS_KEY, context.MODE_PRIVATE)
                .edit().putInt(SYNC_WORDS_KEY, 0).commit();
    }


    public static int getCurWords() {
        Context context = App.getInstance().getContext();
        int curWords = context.getSharedPreferences(CUR_WORDS_KEY, context.MODE_PRIVATE)
                .getInt(CUR_WORDS_KEY, 0);
        return curWords;
    }

    public static void resetCurWords(int words) {
        Context context = App.getInstance().getContext();

        putCurLevel(1);
        int upNeedWords = context.getResources().getInteger(R.integer.up_level_need);

        putCurLevel(words / upNeedWords);

        putCurWords(0);
        putCurWords(words);
    }

    public static void putCurWords(int addWords) {
        Context context = App.getInstance().getContext();

        int curLevel = getCurLevel();
        int curWords = getCurWords();
        int upNeedWords = context.getResources().getInteger(R.integer.up_level_need);
        if (curWords - (curLevel * upNeedWords) + addWords >= upNeedWords) {
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
}
