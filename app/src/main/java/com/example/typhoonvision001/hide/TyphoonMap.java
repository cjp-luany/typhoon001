package com.example.typhoonvision001.hide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.TextureMapView;
//import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.typhoonvision001.R;
//import com.zwinsoft.mybaidumap.entity.MarkerInfoUtil;
//import com.zwinsoft.mybaidumap.tools.MyOrientationListener;
//import com.zwinsoft.mybaidumap.tools.MyOrientationListener.OnOrientationListener;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by 乱不得静 on 2017/3/23.
 */
public class TyphoonMap  extends AppCompatActivity implements OnClickListener {
//    private BMapManager manager;
//    private MapView mapview;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(getApplicationContext());
//        setContentView(R.layout.z_typhoon_map);
//        mapview = (MapView) findViewById(R.id.map_view);
////        baiduMap = mapview.getMap();
////        msu = MapStatusUpdateFactory.newLatLng(new LatLng(30.5715920000,104.2077620000));
////        baiduMap.setMapStatus(msu);
//        mapview.showZoomControls(true);
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//        mapview.onDestroy();
////        unregisterReceiver(broadcastReceiver);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mapview.onResume();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
//        mapview.onPause();
//    }


    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private ImageButton ib_large,ib_small,ib_mode,ib_loc,ib_traffic;
    private Button ib_marker;
    //模式切换，正常模式
    private boolean modeFlag = true;
    //当前地图缩放级别
    private float zoomLevel;
    //定位相关
    private LocationClient mLocationClient = null;
    private MyLocationListener mLocationListener;
    //是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
    private boolean isFirstLocation = true;
    //创建自己的箭头定位
    private BitmapDescriptor bitmapDescriptor;
    //经纬度
    double mLatitude;
    double mLongitude;
    //方向传感器监听
//    private MyOrientationListener myOrientationListener;
    private float mLastX;
    private List<MarkerInfoUtil> infos;
    //显示marker
    private boolean showMarker = false;
    private RelativeLayout rl_marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.z_typhoon_map);
        //初始化控件
        initView();
        //初始化地图
        initMap();
        //定位
        initLocation();
        //创建自己的定位图标，结合方向传感器，定位的时候显示自己的方向
        initMyLoc();
        //创建marker信息
        setMarkerInfo();
    }
    private void setMarkerInfo() {
        infos = new ArrayList<MarkerInfoUtil>();
        infos.add(new MarkerInfoUtil(117.216624,39.142693,"天津站",R.drawable.pic001,"天津站，俗称天津东站，隶属北京铁路局管辖"));
        infos.add(new MarkerInfoUtil(117.176955,39.111345,"南开大学",R.drawable.pic001,"正式成立于1919年，是由严修、张伯苓秉承教育救国理念创办的综合性大学。"));
        infos.add(new MarkerInfoUtil(117.174081,39.094994,"天津水上公园",R.drawable.pic001,"天津水上公园原称青龙潭，1951年7月1日正式对游客开放，有北方的小西子之称。"));
    }
    private void initMyLoc() {
        //初始化图标
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.arrow);
        //方向传感器监听
//        myOrientationListener = new MyOrientationListener(this);
//        myOrientationListener.setOnOrientationListener(new OnOrientationListener() {
//
//            @Override
//            public void onOrientationChanged(float x) {
//                mLastX = x;
//            }
//        });
    }
    private void initMap() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map_view);
        // 不显示缩放比例尺
        mMapView.showZoomControls(true);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        //百度地图
        mBaiduMap = mMapView.getMap();
        // 改变地图状态
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        //设置地图状态改变监听器
        mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
            }
            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
            }
            @Override
            public void onMapStatusChange(MapStatus arg0) {
                //当地图状态改变的时候，获取放大级别
                zoomLevel = arg0.zoom;
            }
        });
        //地图点击事件
        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }
            @Override
            public void onMapClick(LatLng arg0) {
                rl_marker.setVisibility(View.GONE);
            }
        });
    }
    private void initLocation() {
        //定位客户端的设置
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        //注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//打开Gps
        option.setScanSpan(1000);//1000毫秒定位一次
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);

    }
    private void initView() {
        //地图控制按钮
//        ib_large = (ImageButton)findViewById(R.id.ib_large);
//        ib_large.setOnClickListener(this);
//        ib_small = (ImageButton)findViewById(R.id.ib_small);
//        ib_small.setOnClickListener(this);
      //  ib_mode = (ImageButton)findViewById(R.id.ib_mode);
      //  ib_mode.setOnClickListener(this);
//        ib_loc = (ImageButton)findViewById(R.id.ib_loc);
//        ib_loc.setOnClickListener(this);
//        ib_traffic = (ImageButton)findViewById(R.id.ib_traffic);
//        ib_traffic.setOnClickListener(this);
        ib_marker = (Button)findViewById(R.id.ib_marker);
        ib_marker.setOnClickListener(this);
        rl_marker = (RelativeLayout)findViewById(R.id.rl_marker);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ib_large:
//                if (zoomLevel < 18) {
//                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
//                    ib_small.setEnabled(true);
//                } else {
//                    showInfo("已经放至最大，可继续滑动操作");
//                    ib_large.setEnabled(false);
//                }
//                break;
//            case R.id.ib_small:
//                if (zoomLevel > 6) {
//                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
//                    ib_large.setEnabled(true);
//                } else {
//                    ib_small.setEnabled(false);
//                    showInfo("已经缩至最小，可继续滑动操作");
//                }
//                break;
//            case R.id.ib_mode://卫星模式和普通模式
//                if(modeFlag){
//                    modeFlag = false;
//                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//                    showInfo("开启卫星模式");
//                }else{
//                    modeFlag = true;
//                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//                    showInfo("开启普通模式");
//                }
//                break;
//            case R.id.ib_loc:
//                isFirstLocation = true;
//                showInfo("返回自己位置");
//                break;
//            case R.id.ib_traffic://是否开启交通图
//                if(mBaiduMap.isTrafficEnabled()){
//                    mBaiduMap.setTrafficEnabled(false);
//                    ib_traffic.setBackgroundResource(R.drawable.offtraffic);
//                    showInfo("关闭实时交通图");
//                }else{
//                    mBaiduMap.setTrafficEnabled(true);
//                    ib_traffic.setBackgroundResource(R.drawable.ontraffic);
//                    showInfo("开启实时交通图");
//                }
//                break;
            case R.id.ib_marker:
                if(!showMarker){
                    //显示marker
                    showInfo("显示覆盖物");
                    addOverlay(infos);
                    showMarker = false;
//                    ib_marker.setBackgroundResource(R.drawable.ditu4);
                }else{
                    //关闭显示marker
                    showInfo("关闭覆盖物");
                    mBaiduMap.clear();
                    showMarker = false;
//                    ib_marker.setBackgroundResource(R.drawable.ditu3);
                }
                break;
            default:
                break;
        }
    }
    //显示marker
    private void addOverlay(List<MarkerInfoUtil> infos) {
        //清空地图
//        mBaiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ditu1);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for(MarkerInfoUtil info:infos){
            //获取经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
                    .draggable(true); // 设置手势拖拽;
            //添加marker
            marker = (Marker) mBaiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        //将地图显示在最后一个marker的位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
        //添加marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                MarkerInfoUtil infoUtil = (MarkerInfoUtil) bundle.getSerializable("info");
                //将信息显示在界面上
                ImageView iv_img = (ImageView)rl_marker.findViewById(R.id.iv_img);
                iv_img.setBackgroundResource(infoUtil.getImgId());
                TextView tv_name = (TextView)rl_marker.findViewById(R.id.tv_name);
                tv_name.setText(infoUtil.getName());
                TextView tv_description = (TextView)rl_marker.findViewById(R.id.tv_description);
                tv_description.setText(infoUtil.getDescription());
                //将布局显示出来
                rl_marker.setVisibility(View.VISIBLE);

                //infowindow中的布局
                TextView tv = new TextView(TyphoonMap.this);
//                tv.setBackgroundResource(R.drawable.infowindow);
                tv.setPadding(20, 10, 20, 20);
                tv.setTextColor(android.graphics.Color.WHITE);
                tv.setText(infoUtil.getName());
                tv.setGravity(Gravity.CENTER);
                bitmapDescriptor = BitmapDescriptorFactory.fromView(tv);
                //infowindow位置
                LatLng latLng = new LatLng(infoUtil.getLatitude(), infoUtil.getLongitude());
                //infowindow点击事件
                OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        //隐藏infowindow
                        mBaiduMap.hideInfoWindow();
                    }
                };
                //显示infowindow
                InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -47, listener);
                mBaiduMap.showInfoWindow(infoWindow);
                return true;
            }
        });
    }
    //自定义的定位监听
    private class MyLocationListener implements BDLocationListener{
        public void  onConnectHotSpotMessage(String var1, int var2){
        };
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mLastX)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(data);
            //更新经纬度
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            //配置定位图层显示方式，使用自己的定位图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(LocationMode.NORMAL, true, bitmapDescriptor);
            mBaiduMap.setMyLocationConfigeration(configuration);
            if(isFirstLocation){
                //获取经纬度
                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                //mBaiduMap.setMapStatus(status);//直接到中间
                mBaiduMap.animateMapStatus(status);//动画的方式到中间
                isFirstLocation = false;
                showInfo("位置：" + location.getAddrStr());
            }
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
        //开启方向传感器
//        myOrientationListener.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }
        //关闭方向传感器
//        myOrientationListener.stop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    //显示消息
    private void showInfo(String str){
        Toast.makeText(TyphoonMap.this, str, Toast.LENGTH_SHORT).show();
    }
}
