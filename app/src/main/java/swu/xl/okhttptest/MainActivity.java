package swu.xl.okhttptest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {
    //TAG
    public static final String TAG = MainActivity.class.getSimpleName();

    //OkHttpClient
    private final OkHttpClient client = new OkHttpClient();

    //显示下载的图片
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //显示下载的图片
        imageView = findViewById(R.id.img);

        //同步Get请求
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                syncRequestByGet();
            }
        }).start();*/

        //异步Get请求
        //asyncRequestByGet();

        //POST请求
        //requestByPost();

        //POST上传文本
        //uploadTextToServer();

        //POST上传图片
        //uploadPictureToServer();

        //POST上传视频
        //uploadMovieToServer();

        //GET请求下载图片
        downloadPicture();
    }

    /**
     * 同步的Get请求
     */
    private void syncRequestByGet(){
        //请求
        Request.Builder builder = new Request.Builder();
        builder.url("http://192.168.43.43/login/loginGet?name=xl&password=0000");
        Request request = builder.build();

        try {
            //请求的响应
            Response response = client.newCall(request).execute();

            //成功的情况下
            if (response.isSuccessful()) {
                //读取响应头
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG,headers.name(i)+":"+headers.value(i));
                }

                //读取响应体
                ResponseBody body = response.body();
                String jsonString = body.string();
                //InputStream inputStream = body.byteStream();
                //Reader reader = body.charStream();

                //转化为Student对象
                Gson gson = new Gson();
                Student student = gson.fromJson(jsonString, Student.class);

                Log.d(TAG,student.toString());
            }else {

                Log.d(TAG,"响应失败");
            }
        } catch (IOException e) {
            e.printStackTrace();

            Log.d(TAG,"IO异常："+e.getMessage());
        }
    }

    /**
     * 异步的Get请求
     */
    private void asyncRequestByGet(){
        //请求
        Request.Builder builder = new Request.Builder();
        builder.url("http://192.168.43.43/login/loginGet?name=xl&password=0000");
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"响应失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //成功的情况下
                if (response.isSuccessful()) {
                    //读取响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG,headers.name(i)+":"+headers.value(i));
                    }

                    //读取响应体
                    ResponseBody body = response.body();
                    String jsonString = body.string();
                    //InputStream inputStream = body.byteStream();
                    //Reader reader = body.charStream();

                    //转化为Student对象
                    Gson gson = new Gson();
                    Student student = gson.fromJson(jsonString, Student.class);

                    Log.d(TAG,student.toString());
                }else {

                    Log.d(TAG,"响应失败");
                }
            }
        });
    }

    /**
     * POST请求获取数据
     */
    private void requestByPost(){
        //Request.Builder
        Request.Builder builder = new Request.Builder();

        //构建参数键值对
        FormBody formBody = new FormBody.Builder()
                .add("name", "xl")
                .add("password", "0000")
                .build();

        //构建Request
        builder.url("http://192.168.43.43/login/loginPost");
        builder.post(formBody);
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"响应失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //成功的情况下
                if (response.isSuccessful()) {
                    //读取响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG,headers.name(i)+":"+headers.value(i));
                    }

                    //读取响应体
                    ResponseBody body = response.body();
                    String jsonString = body.string();
                    //InputStream inputStream = body.byteStream();
                    //Reader reader = body.charStream();

                    //转化为Student对象
                    Gson gson = new Gson();
                    Student student = gson.fromJson(jsonString, Student.class);

                    Log.d(TAG,student.toString());
                }else {

                    Log.d(TAG,"响应失败");
                }
            }
        });
    }

    /**
     * POST上传文本数据
     */
    private void uploadTextToServer(){
        //文件类型
        final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=utf-8");

        //获取文本数据流
        final InputStream is = getResources().openRawResource(R.raw.test);

        //请求体
        RequestBody text_request = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE;
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(getBytesByInputStream(is));
            }
        };

        //构建MultipartBody
        MultipartBody.Builder multi_builder = new MultipartBody.Builder();
        multi_builder.setType(MultipartBody.FORM);
        multi_builder.addFormDataPart("file","test.txt",text_request);
        RequestBody requestBody = multi_builder.build();

        //构建Request
        Request.Builder builder = new Request.Builder();
        builder.url("http://192.168.43.43/upLoadFile");
        builder.post(requestBody);
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"响应失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //成功的情况下
                if (response.isSuccessful()) {
                    //读取响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG,headers.name(i)+":"+headers.value(i));
                    }

                    //读取响应体
                    ResponseBody body = response.body();
                    String jsonString = body.string();
                    //InputStream inputStream = body.byteStream();
                    //Reader reader = body.charStream();

                    Log.d(TAG,jsonString);
                }else {

                    Log.d(TAG,"响应失败");
                }
            }
        });
    }

    /**
     * POST上传图片数据
     */
    private void uploadPictureToServer(){
        //文件类型
        final MediaType MEDIA_TYPE = MediaType.parse("image/png");

        //获取文本数据流
        final InputStream is = getResources().openRawResource(R.raw.test_picture);

        //请求体
        RequestBody picture_request = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE;
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(getBytesByInputStream(is));
            }
        };

        //构建MultipartBody
        MultipartBody.Builder multi_builder = new MultipartBody.Builder();
        multi_builder.setType(MultipartBody.FORM);
        multi_builder.addFormDataPart("file","test.png",picture_request);
        RequestBody requestBody = multi_builder.build();

        //构建Request
        Request.Builder builder = new Request.Builder();
        builder.url("http://192.168.43.43/upLoadFile");
        builder.post(requestBody);
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"响应失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //成功的情况下
                if (response.isSuccessful()) {
                    //读取响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG,headers.name(i)+":"+headers.value(i));
                    }

                    //读取响应体
                    ResponseBody body = response.body();
                    String jsonString = body.string();
                    //InputStream inputStream = body.byteStream();
                    //Reader reader = body.charStream();

                    Log.d(TAG,jsonString);
                }else {

                    Log.d(TAG,"响应失败");
                }
            }
        });
    }

    /**
     * POST上传视频数据
     */
    private void uploadMovieToServer(){
        //文件类型
        final MediaType MEDIA_TYPE = MediaType.parse("video/mp4; charset=utf-8");

        //获取文本数据流
        final InputStream is = getResources().openRawResource(R.raw.test_movie);

        //请求体
        RequestBody movie_request = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE;
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(getBytesByInputStream(is));
            }
        };

        //构建MultipartBody
        MultipartBody.Builder multi_builder = new MultipartBody.Builder();
        multi_builder.setType(MultipartBody.FORM);
        multi_builder.addFormDataPart("file","test.mp4",movie_request);
        RequestBody requestBody = multi_builder.build();

        //构建Request
        Request.Builder builder = new Request.Builder();
        builder.url("http://192.168.43.43/upLoadFile");
        builder.post(requestBody);
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"响应失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //成功的情况下
                if (response.isSuccessful()) {
                    //读取响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG,headers.name(i)+":"+headers.value(i));
                    }

                    //读取响应体
                    ResponseBody body = response.body();
                    String jsonString = body.string();
                    //InputStream inputStream = body.byteStream();
                    //Reader reader = body.charStream();

                    Log.d(TAG,jsonString);
                }else {

                    Log.d(TAG,"响应失败");
                }
            }
        });
    }

    /**
     * GET请求下载图片
     */
    private void downloadPicture(){
        //请求
        Request.Builder builder = new Request.Builder();
        builder.url("http://192.168.43.43/upLoad/img/test.png");
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"响应失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //成功的情况下
                if (response.isSuccessful()) {
                    //读取响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        Log.d(TAG,headers.name(i)+":"+headers.value(i));
                    }

                    //读取响应体
                    ResponseBody body = response.body();
                    //String jsonString = body.string();
                    InputStream is = body.byteStream();
                    //Reader reader = body.charStream();

                    //获的图片
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);

                    //主线程更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });

                    //关闭输入流
                    is.close();

                }else {

                    Log.d(TAG,"响应失败");
                }
            }
        });
    }

    /**
     * 将输入流->字符串
     * @param is
     * @return
     */
    private byte[] getBytesByInputStream(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] data = new byte[1024];
        int len;
        try {
            //一直读取
            while ((len = is.read(data)) != -1){
                bos.write(data,0,len);
            }

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

}

