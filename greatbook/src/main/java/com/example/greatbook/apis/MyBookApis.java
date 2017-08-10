package com.example.greatbook.apis;

import com.example.greatbook.model.BookSectionBean;
import com.example.greatbook.model.BookSectionContentBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MBENBEN on 2017/8/9.
 */

public interface MyBookApis {
    //http://www.ohonor.xyz/MyBook/querySectionNameByBookName?bookName=短篇鬼故事
    //http://www.ohonor.xyz/MyBook/queryBookContentByHref?bookSectionId=/gushihui/100geshijiejingdianbeihoudegushi/429564.html
    String HOST="http://www.ohonor.xyz/MyBook/";

    @GET("queryBookContentByHref")
    Observable<BookSectionContentBean> queryBookContentByHref(@Query("bookSectionId") String bookSectionId);

    @GET("querySectionNameByBookName")
    Observable<List<BookSectionBean>> querySectionNameByBookName(@Query("bookName") String bookName);
}
