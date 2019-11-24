package com.example.wmm.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//AsyncTask是android为了方便隔离子线程和ui操作逻辑而设计的类
//第一个泛型参数是传入任务的参数，这里应该是url
//第二个泛型参数表示进度是整形
//第三个泛型参数是结果用整形
public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAILED=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELED=3;

    private DownloadListener listener;

    private boolean isCanceld=false;
    private boolean isPaused=false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener){
        this.listener=listener;
    }

    //开启子线程执行的函数
    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is=null;
        RandomAccessFile savedFile=null;
        File file=null;
        try{
            //已下载字节数
            long downloadedLength=0;
            //下载地址
            String downloadUrl=strings[0];
            //文件名
            String fileName=downloadUrl.substring(downloadUrl.lastIndexOf('/'));
            //下载存放目录,一般就是/存储器/Download,这里我是/storage/emulated/0/Download
            String directory=Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
            ).getPath();
            file=new File(directory+fileName);
            //如果文件存在，说明之前已经下载过，那么读取已经下载的长度
            if(file.exists()){
                downloadedLength=file.length();
            }
            //获取待下载文件的字节数
            long contentLength=getContentLength(downloadUrl);
            if(contentLength==0){
                return TYPE_FAILED;
            }else if(contentLength==downloadedLength){
                return TYPE_SUCCESS;
            }

            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .addHeader("RANGE","bytes="+downloadedLength+"-"+contentLength)
                    .url(downloadUrl)
                    .build();

            Response response=client.newCall(request).execute();
            if(response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");
                //跳过已下载的字节数
                savedFile.seek(downloadedLength);
                byte[] b=new byte[1024];
                int total=0;
                int len;
                while((len=is.read(b))!=-1){
                    if(isCanceld){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else{
                        total+=len;
                        //否则写入文件
                        savedFile.write(b,0,len);
                        //计算百分比
                        int progress=(int)((total+downloadedLength)*100/contentLength);
                        //在子线程中调用该函数以修改ui
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;

            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(is!=null){
                    is.close();
                }
                if(savedFile!=null){
                    savedFile.close();
                }
                if(isCanceld&&file!=null){
                    file.delete();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    //当publish时被调用，然后在里面又调用了listener里的onProgress
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress=values[0];
        if(progress>lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    //当子线程任务执行完毕时被调用，根据子线程的返回值决定逻辑
    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;


        }
    }

    public void pauseDownload(){
        isPaused=true;
    }

    public void cancelDownload(){
        isCanceld=true;
    }

    private long getContentLength(String downloadUrl)throws IOException{
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(downloadUrl)
                .build();

        Response response=client.newCall(request).execute();
        if(response!=null&&response.isSuccessful()){
            long contentLength=response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
