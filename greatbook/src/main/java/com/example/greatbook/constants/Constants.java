package com.example.greatbook.constants;

import com.example.greatbook.App;

import java.io.File;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class Constants {
    public static final String PATH_DATA = App.getInstance().getContext().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/GreatBookCache";

    public static String LEANCLOUD_APP_KEY ="0PyRefuGHTaRDe97hpFJQ4MK";
    public static String LEANCLOUD_APP_ID ="QXRP1vo8DnS6uGs7lsj43Mqz-gzGzoHsz";

    public static String TIANXING_APP_KEY="6ab0e95b0a7a25623e097b14a7c983b8";
    public static final String SET_GROUP_FINISH="set_group_finish";

    public static final int RLV_PAGE_COUNT = 6;


    public static final String JOK_APP_KEY="92e45e290c194f53665fa891af8a2c05";

    public static final String LOCAL_ADD_SUC_FINISH="local_add_suc_finish";
    public static final String LOCAL_SET_RECORD_SUC_FINISH="local_set_record_suc_finish";
    public static final String LOCAL_ADD_GROUP_SUC_FINISH="local_add_group_suc_finish";
}
