package com.example.wmm.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    private TextView positionText;
    private MapView mapView;
    //用于控制mapView的显示，比如缩放和定位到某一个位置
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        //初始化操作需要在setContentView之前就做好，否则会出错（不知道为啥，按道理先加载layout再加载bmap不也是很合理么）
        //报错显示，在setContentView的时候，mapView必须先要传入一个Context
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView=(MapView)findViewById(R.id.bmap_view);
        baiduMap=mapView.getMap();
        //用于在map上显示自己的一个点
        baiduMap.setMyLocationEnabled(true);

        positionText=(TextView)findViewById(R.id.position_text_view);
        List<String> permissionList=new ArrayList<>();
        //注意fine location和coarse location属于同一个权限组，只要申请其中之一即可，但是在manifest中必须全部写出来
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);

        }else{
            requestLocation();
        }

    }

    private void nabigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            //只有第一次定位时需要一段动画，否则直接显示在地图上，不需要动画（因为正常移动范围不可能太大，不过这个在实际情况时应当移动了一定的距离之后就播放动画）
            isFirstLocate=false;
        }
        MyLocationData.Builder builder=new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData locationData=builder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void requestLocation(){
        //start默认只会定位一次,可以通着设置以改变定位频率
        initLocation();
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        //定位频率，5s
        option.setScanSpan(5000);
        //设置定位方式，这里只使用GPS方式
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //设置需要获取详细地址信息
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                //注意申请了多个权限，每个权限都可能成功或者失败
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You must permision all!",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"unknown error",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                nabigateTo(bdLocation);
            }
            StringBuilder currentPosition=new StringBuilder();
            currentPosition.append("lng:").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("lat:").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("location type:");
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
                currentPosition.append("GPS");
            }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                currentPosition.append("Network");
            }

            currentPosition.append("country:").append(bdLocation.getCountry()).append("\n");
            currentPosition.append("province:").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("city:").append(bdLocation.getCity()).append("\n");
            currentPosition.append("district:").append(bdLocation.getDistrict()).append("\n");
            currentPosition.append("street:").append(bdLocation.getStreet()).append("\n");

            positionText.setText(currentPosition);
        }


    }


}
