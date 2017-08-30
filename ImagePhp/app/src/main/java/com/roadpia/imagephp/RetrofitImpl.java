package com.roadpia.imagephp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sw on 2017-02-16.
 */

public class RetrofitImpl {




    private Retrofit retrofit;
    BlackboxService blackboxService;

    private static RetrofitImpl instance;



    private RetrofitImpl() {

    }





    public static RetrofitImpl getInstance() {
        if (instance == null) {
            instance = new RetrofitImpl();
        }
        return instance;
    }
    //파일 전송
    public void uploadFile(final Context context,String filePath,String filePath2) {
        String url = "http://chasw12.dothome.co.kr";
        retrofit = new Retrofit.Builder().baseUrl(url).build();
        blackboxService = retrofit.create(BlackboxService.class);



        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        final File file = new File(filePath);
        File file2 = new File(filePath2);


        // create RequestBody instance from file


        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestFile2 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file2);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("myfile", file.getName(), requestFile);
        MultipartBody.Part body2 =
                MultipartBody.Part.createFormData("myfile2", file2.getName(), requestFile2);

        StringBuilder disposition = new StringBuilder("form-data; name=");
        appendQuotedString(disposition, "myfile");

        if (file.getName() != null) {
            disposition.append("; myfile=");
            appendQuotedString(disposition, file.getName());
        }
        if(file2.getName()!=null){
            appendQuotedString(disposition, "&myfile2");
            disposition.append("; myfile2=");
            appendQuotedString(disposition, file2.getName());
        }
        System.out.println("disposition"+disposition.toString());

        // add another part within the multipart request
        String descriptionString = "";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        // finally, execute the request
        Call<ResponseBody> call = blackboxService.upload(body,body2);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                try {
                    Log.d("Upload", "success" +  " " + file.getName()+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(context, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static StringBuilder appendQuotedString(StringBuilder target, String key) {
        target.append('"');
        for (int i = 0, len = key.length(); i < len; i++) {
            char ch = key.charAt(i);
            switch (ch) {
                case '\n':
                    target.append("%0A");
                    break;
                case '\r':
                    target.append("%0D");
                    break;
                case '"':
                    target.append("%22");
                    break;
                default:
                    target.append(ch);
                    break;
            }
        }
        target.append('"');
        return target;
    }




}
