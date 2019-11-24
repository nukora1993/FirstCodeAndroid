package com.example.wmm.networktest;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest=(Button)findViewById(R.id.send_request);
        responseText=(TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.send_request){
//            sendRequestWithHttpURLConnection();
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override

            public void run() {
                try{
                    //使用OKhttp好处是能将返回内容给解析好
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
//                            .url("https://www.baidu.com")
//                            .url("https://raw.githubusercontent.com/ZhuXiaoran07/ALG-Semantics/2c62536d8e150ac75f94d0b419d9e41b58b3e2f4/config.xml")
                            .url("https://raw.githubusercontent.com/cheche062/MutantBox/47151e0496327f64e367a1718e490cb40c8886a4/battle_space/h5/GameResource.json")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
//                    showResponse(responseData);

//                    parseXMLWithPull(responseData);
//                    parseXMLWithSAX(responseData);
//                    parseJSONWithJSONObject(responseData);
                    parseJSONWithGSON(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //使用GSON，优点是可以实现json到javabean的映射
    private void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
        List<App> appList=gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for(App app:appList){
            Log.d("GsonTest",app.getId()+app.getName()+app.getVersion());
        }
    }

    //使用原生的JSONObject解析，优点简单，缺点不能实现直接到javabean的映射
    private void parseJSONWithJSONObject(String jsonData){
        try{
            //只有是一个JSONArray的时候才能够使用JSONArray，否则需要使用JSONObject
//            JSONArray jsonArray=new JSONArray(jsonData);
//            for (int i = 0; i <jsonArray.length() ; i++) {
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                Log.d("JSONObjectTest",jsonObject.toString());
//            }
            JSONObject jsonObject=new JSONObject(jsonData);
            Log.d("JSONObjectTest",jsonObject.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //使用SAX解析，优点逻辑清晰，面向对象，不需要while处理，缺点代码量稍大，需要编写Handler
    private void parseXMLWithSAX(String xmlData){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            ContentHandler handler=new ContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //使用XMLPull的方式解析xml,优点简单直接，缺点逻辑不清晰
    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            //获得当前的解析eventType，包括文档开始，文档结束，tag开始，tag结束
            //判断当前node是什么，并且nextText解析出node里的值
            int eventType=xmlPullParser.getEventType();
            String id="";
            String name="";
            String version="";
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        if("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("app".equals(nodeName)){
                            Log.d("xml",id+name+version);
                        }
                        break;
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    //注意高版本的android不允许直接发送http请求，如果需要这样做，需要在manifest进行相应配置
                    URL url=new URL("https://www.baidu.com");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try{
                            //注意流的关闭是先打开的后关闭，如果关闭的是处理流，会自动关闭与之关联的节点流
                            reader.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }

                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        //android 不允许在子线程更新ui
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
