package com.roadpia.imagephp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  RetrofitImpl retrofit = RetrofitImpl.getInstance();
        retrofit.uploadFile(this, Environment.getExternalStorageDirectory()+"/1.jpg",Environment.getExternalStorageDirectory()+"/1temp.png");*/
        /*File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", "screenshot2.png");
        BitmapUtils.loadImageFromUri(this,Uri.fromFile(file));*/
        takeScreenshot();



    }
    //화면 캡쳐하기
    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다

        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환

        String filename = "screenshot.png";
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);  //Pictures폴더 screenshot.png 파일
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        view.setDrawingCacheEnabled(false);
        return file;
    }



    private void screenshot(Bitmap bm) {
        try {
            File path = new File(Environment.getExternalStorageDirectory()+"/screenshot1.jpg");

            /*if(! path.isDirectory()) {
                path.mkdirs();
            }*/

            String temp = Environment.getExternalStorageDirectory()+"/";
            temp = temp + "screenshot1";
            temp = temp + ".jpg";

            FileOutputStream out = new FileOutputStream(temp);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException:", e.getMessage());
        }
    }

    private void takeScreenshot() {

        try {
            // image naming and path  to include sd card  appending name you choose for file
            // 저장할 주소 + 이름
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + "screenshot" + ".jpg";

            // create bitmap screen capture
            // 화면 이미지 만들기
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            // 이미지 파일 생성
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }





}
