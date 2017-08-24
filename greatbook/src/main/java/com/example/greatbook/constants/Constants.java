package com.example.greatbook.constants;

import com.example.greatbook.App;
import com.example.greatbook.utils.FileUtils;

import java.io.File;

/**
 * Created by MDove on 2016/11/20.
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
    public static final String ADD_GROUP_TO_RECORD_SUC ="local_add_group_suc_finish";
    public static final String ADD_GROUP_TO_RECORD_ABANDON ="local_add_group_abandon";
    public static final String TO_CLIPACTIVITY="to_clipactivity";
    public static final String TO_CLIPACTIVITY_NO_CLIP="to_clipactivity_no_clip";

    public static final String PATH_TXT = PATH_DATA + "/book/";

    public static final String PATH_EPUB = PATH_DATA + "/epub";

    public static final String PATH_CHM = PATH_DATA + "/chm";
    public static final String SUFFIX_ZIP="suffix_zip";
    public static final String RETURN_CLIP_PHOTO="return_clip_photo";
    public static final int  START_ALBUM_REQUESTCODE=1;
    public static final int CROP_RESULT_CODE=2;
    public static final int CAMERA_WITH_DATA=3;
    public static final String DISCOVERY_RECORD_ITEM_CLICK="discovery_record_item_click";
    public static final String DISCOVERY_GROUP_ITEM_CLICK="discovery_group_item_click";
    public static final String RECORD_REMARKS_LIKE_TO_REFRESH ="record_remarks_like_to_refresh";
    public static final String OPEN_RECORD_ITEM_POSITION ="open_record_item_position";

}
