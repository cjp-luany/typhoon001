package com.example.typhoonvision001.hide;
import com.baidu.mapapi.SDKInitializer;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.typhoonvision001.DataDB.TyMapData;
import com.example.typhoonvision001.R;

import java.util.ArrayList;
import java.util.List;
//param为文档的主要路径
//    public static Intent getWordFileIntent(String param) {
//                Intent intent = null;
//                try {
//                        intent = new Intent("android.intent.action.VIEW");
//                        intent.addCategory("android.intent.category.DEFAULT");
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        Uri uri = Uri.fromFile(new File(param));
//                        intent.setDataAndType(uri, "application/msword");
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                return intent;
//            }

//获取意图后，判断该是否存在，防止崩溃：
/**
 * 判断Intent 是否存在 防止崩溃
 *
 * @param context
 * @param intent
 * @return
 */
//    private boolean isIntentAvailable(Context context, Intent intent) {
//        final PackageManager packageManager = context.getPackageManager();
//        List<resolveinfo> list = packageManager.queryIntentActivities(intent,
//                PackageManager.GET_ACTIVITIES);
//        return list.size() > 0;
//    }</resolveinfo>



//    如果list.size小于o，提示是否已经安装了office：
//            Tools.showToast(NoticeDetailActivity.this,"请安装office");

/**
 * Created by 乱不得静 on 2017/3/23.
 */
public class WordActivity extends AppCompatActivity implements View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient = null;
    public BDLocationListener mBDListener = new MyLocationListener();

    private List<TyMapData> bdMapClientList;
    private double latitude;
    private double longitude;
    private boolean isFirstLocate = true;

    private Button mButtonAdd;
    private Button mButtonSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.z_typhoon_word);

        /**
         * 缩放地图的按钮
         */
        mButtonAdd = (Button) findViewById(R.id.button_add);
        mButtonSub = (Button) findViewById(R.id.button_sub);
        mButtonAdd.setOnClickListener(this);
        mButtonSub.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.mapView_client_raise);
        mMapView.showZoomControls(false);//让百度地图默认的地图缩放控件不显示

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);//使能百度地图的定位功能
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mBDListener);//注册地图的监听器
        initLocation();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final String info = (String) marker.getExtraInfo().get("info");
                InfoWindow infoWindow;
                //动态生成一个Button对象，用户在地图中显示InfoWindow
                final Button textInfo = new Button(getApplicationContext());
                textInfo.setBackgroundResource(R.drawable.locate_popup);
                textInfo.setPadding(10, 10, 10, 10);
                textInfo.setTextColor(Color.BLACK);
                textInfo.setTextSize(12);
                textInfo.setText(info);
                //得到点击的覆盖物的经纬度
                LatLng ll = marker.getPosition();
                textInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(WordActivity.this, "你点击了"+info, Toast.LENGTH_SHORT).show();
                    }
                });
                //将marker所在的经纬度的信息转化成屏幕上的坐标
                Point p = mBaiduMap.getProjection().toScreenLocation(ll);
                p.y -= 90;
                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
                //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
                infoWindow = new InfoWindow(textInfo, llInfo, 0);
                mBaiduMap.showInfoWindow(infoWindow);//显示此infoWindow
                //让地图以备点击的覆盖物为中心
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.setMapStatus(status);
                return true;

            }
        });
        //开始定位
        mLocationClient.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                //放大地图
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.button_sub:
                //缩小地图
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
            default:
                break;
        }
    }

    /**
     * 自定义一个百度地图的定位监听器，可监听定位类型，位置经纬度变化等一系列状态
     */
    private class MyLocationListener implements BDLocationListener {

        public void onConnectHotSpotMessage(String var1, int var2){};
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            latitude = bdLocation.getLatitude();//得到纬度
            longitude = bdLocation.getLongitude();//得到经度
            boolean isLocateFailed = false;//定位是否成功
            if (isFirstLocate) {
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    ToastUtils.showToast(getApplicationContext(), "GPS定位");
                    Toast.makeText(WordActivity.this, "GPS定位", Toast.LENGTH_SHORT).show();
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    ToastUtils.showToast(getApplicationContext(), "网络定位");
                    Toast.makeText(WordActivity.this, "网络定位", Toast.LENGTH_SHORT).show();
                } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    ToastUtils.showToast(getApplicationContext(), "离线定位");
                    Toast.makeText(WordActivity.this, "离线定位", Toast.LENGTH_SHORT).show();
                } else if (bdLocation.getLocType() == BDLocation.TypeServerError
                        || bdLocation.getLocType() == BDLocation.TypeNetWorkException
                        || bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    isLocateFailed = true;
                }
                /**
                 * 如果定位不成功，显示定位失败的dialog
                 */
                if (isLocateFailed) {
                    simpleDialog();
                }
                initMapDataList();
                addOverlay();
                isFirstLocate = false;
            }

        }
    }

    /**
     * 添加覆盖物的方法
     */
    private void addOverlay() {
        Marker marker = null;
        LatLng point = null;
        MarkerOptions option = null;
        BitmapDescriptor bitmap =BitmapDescriptorFactory.fromResource(R.drawable.customer_location);;
        for (TyMapData data : bdMapClientList) {
            point = new LatLng(data.getLatitude(), data.getLongitude());
            option = new MarkerOptions().position(point).icon(bitmap);
            marker = (Marker) mBaiduMap.addOverlay(option);
            //Bundle用于通信
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", data.getTime()+"\n"+"纬度："+data.getLatitude()+"   经度："+data.getLongitude());
            marker.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它
        }
        //将地图移动到最后一个标志点
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(status);

    }

    /**
     * BaiduAPI上的例程，初始化定位
     */
    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //初始化每个覆盖物对应的信息
    private void initMapDataList() {
        bdMapClientList = new ArrayList<>();
        //让所有覆盖物的经纬度与你自己的经纬度相近，以便打开地图就能看到那些覆盖物
//        bdMapClientList.add(new TyMapData("中兴创维", latitude - 0.0656, longitude - 0.00354));
//        bdMapClientList.add(new TyMapData("领卓科技", latitude + 0.024, longitude - 0.0105));
//        bdMapClientList.add(new TyMapData("蓝翔驾校", latitude - 0.00052, longitude - 0.01086));
//        bdMapClientList.add(new TyMapData("优衣库折扣店", latitude + 0.0124, longitude + 0.00184));
    }

    /**
     * 定位失败时显示的dialog的初始化方法
     */
    public void simpleDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WordActivity.this);
        builder.setMessage("当前网络不可用，请检查网络设置")
                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


}
