package com.example.greatbook.apis;

import com.example.greatbook.model.BookSectionContent;
import com.example.greatbook.model.GrammarKindIndex;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MBENBEN on 2017/8/9.
 */

public interface MyBookApis {
    //http://www.ohonor.xyz/MyBook/querySectionNameByBookName?bookName=鬼故事
    //http://www.ohonor.xyz/MyBook/queryBookContentByHref?bookSectionId=/gushihui/100geshijiejingdianbeihoudegushi/429564.html
    String HOST="http://www.sbkk8.cn/MyBook/";

    @GET("queryBookContentByHref")
    Observable<BookSectionContent> queryBookContentByHref(@Query("bookSectionId") String bookSectionId);

    @GET("querySectionNameByBookName")
    Observable<BookSectionContent> querySectionNameByBookName(@Query("bookName") String bookName);
}
