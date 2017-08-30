package com.roadpia.imagephp;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BlackboxService {

    @GET("cgi-bin/Config.cgi")
    Call<ResponseBody> getMediaList(@Query("action") String action, @Query("property") String property, @Query("format") String format, @Query("count") String count, @Query("from") String from);


    @Multipart
    @POST("fileupload2.php")
    Call<ResponseBody> upload(@Part MultipartBody.Part file,@Part MultipartBody.Part file2);
}