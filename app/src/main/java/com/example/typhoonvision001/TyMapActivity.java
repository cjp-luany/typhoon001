package com.example.typhoonvision001;

import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.example.typhoonvision001.DataDB.TyMapDB;
import com.example.typhoonvision001.DataDB.TyMapData;

import java.util.ArrayList;
import java.util.List;
//    private double latitude;
//    private double longitude;
/**
 * Created by 乱不得静 on 2017/3/23.
 */
public class TyMapActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean f=true;
    private ImageView button_iv2,iv_add,iv_sub,iv_mode;
    private Context ctxt =this;
    private MapView mapView;
    private BaiduMap TyBDMap;
    private List<TyMapData> tyMapData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.typhoon_map);

        mapView = (MapView) findViewById(R.id.map_view2);
        TyBDMap = mapView.getMap();

        button_iv2=(ImageView) findViewById(R.id.button_iv2);
        button_iv2.setOnClickListener(this);

        iv_mode=(ImageView) findViewById(R.id.iv_mode);
        iv_mode.setOnClickListener(this);

        iv_add=(ImageView) findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);

        iv_sub=(ImageView) findViewById(R.id.iv_sub);
        iv_sub.setOnClickListener(this);



        TyMapinit(ctxt);
        mapView.showZoomControls(false);

        addOverlay();


    }

    private void TyMapinit(Context ctxt) {
        tyMapData = new ArrayList<>();
        TyMapDB dbHelper;
        dbHelper = new TyMapDB(ctxt,"info",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("info", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name=cursor.getString(cursor.getColumnIndex("名字"));
                double clat = cursor.getDouble(cursor.getColumnIndex("纬度"));
                double clng = cursor.getDouble(cursor.getColumnIndex("经度"));
                String ctime = cursor.getString(cursor.getColumnIndex("日期"));
                String movedirection=cursor.getString(cursor.getColumnIndex("移动方向"));
                int movespeed = cursor.getInt(cursor.getColumnIndex("移动速度"));
                int power = cursor.getInt(cursor.getColumnIndex("风力"));
                int pressure=cursor.getInt(cursor.getColumnIndex("中心气压"));
                int radius10 =cursor.getInt(cursor.getColumnIndex("十级半径"));
                int radius7=cursor.getInt(cursor.getColumnIndex("七级半径"));
                int speed =cursor.getInt(cursor.getColumnIndex("速度"));
                String strong=cursor.getString(cursor.getColumnIndex("等级"));
                tyMapData.add(new TyMapData(name,ctime, clat, clng,movedirection,movespeed,power,pressure,radius10,radius7,speed,strong));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

//        LocationClientOption location = new LocationClientOption();
//        //就是这个方法设置为true，才能获取当前的位置信息
//        location.setIsNeedAddress(true);
//        location.setOpenGps(true);
//        location.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        location.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系

        ;
//        TyBDMap.setMyLocationEnabled(true);
//        MyLocationData locData = new MyLocationData.Builder().direction(100).latitude(TyBDMap.getLocationData().latitude)
//                .longitude(TyBDMap.getLocationData().longitude).build();
//        TyBDMap.setMyLocationData(locData);
//
//        LatLng loc = new LatLng(locData.latitude, locData.longitude);
//        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(loc);
//        TyBDMap.setMapStatus(status);

    }



    private void addOverlay() {
        List<LatLng> pointss = new ArrayList<LatLng>();
        Marker marker = null;
        LatLng point = null;
        MarkerOptions option = null;
        PolylineOptions option2= null;
        BitmapDescriptor bitmap =BitmapDescriptorFactory.fromResource(R.drawable.hupoint);

        for (TyMapData data : tyMapData) {
            point = new LatLng(data.getLatitude(), data.getLongitude());
            option = new MarkerOptions().position(point).icon(bitmap).zIndex(5).draggable(true);
            marker = (Marker) TyBDMap.addOverlay(option);
            pointss.add(point);
            //option2=new PolylineOptions().points()

            Bundle bundle = new Bundle();
            bundle.putSerializable("info", data.getName()+"\n"+data.getTime()+"\n"+"纬度："+data.getLatitude()+"   经度："+data.getLongitude()
                    +"\n"+"向"+data.getMovedirection()+"移动   速度"+data.getMovespeed()+"公里/小时"
                    +"\n"+"风力"+data.getPower()+"级"
                    +"\n"+"中心气压"+data.getPressure()+"百帕"
                    +"\n"+"十级半径"+data.getRadius10()+"  公里"
                    +"\n"+"七级半径"+data.getRadius7()+"公里"
                    +"\n"+"速度"+data.getSpeed()+"米/秒"
                    +"\n"+data.getStrong()
            );
            bundle.putInt("Radius7",data.getRadius7());
            bundle.putInt("Radius10",data.getRadius10());
//            if(data.getRadius10()==0){
//                bundle.putInt("Radius",data.getRadius7());
//            }else{
//                bundle.putInt("Radius",data.getRadius10());
//            }

            marker.setExtraInfo(bundle);
        }

        if(point!=null){
            option2=new PolylineOptions().width(5).color(0xAAFF0000).points(pointss);
            TyBDMap.addOverlay(option2);
        }

        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
        TyBDMap.setMapStatus(status);

            BaiduMap.OnMarkerClickListener listener =new BaiduMap.OnMarkerClickListener(){
                @Override
                public boolean onMarkerClick(Marker marker){
                    BitmapDescriptor A =BitmapDescriptorFactory.fromResource(R.drawable.hu16px);
                    BitmapDescriptor B =BitmapDescriptorFactory.fromResource(R.drawable.hu24px);
                    BitmapDescriptor C =BitmapDescriptorFactory.fromResource(R.drawable.hu32px);
                    LatLng po = marker.getPosition();
                    ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
                    giflist.add(A);
                    giflist.add(B);
                    giflist.add(C);
                    MarkerOptions ooD = new MarkerOptions().position(po).icons(giflist).zIndex(5).period(10);
                    Marker  MarkerE = (Marker) (TyBDMap.addOverlay(ooD));

            try{

                    final String i = (String) marker.getExtraInfo().get("info");
                    final int r7 = (int) marker.getExtraInfo().get("Radius7");
                    final int r10 = (int) marker.getExtraInfo().get("Radius10");
                    final Button itxt = new Button(getApplicationContext());

                    int radius7=r7*1000;int radius10=r10*1000;
                    final InfoWindow window;

                    itxt.setBackgroundResource(R.drawable.locate_popup);
                    itxt.setTextColor(Color.BLACK);
                    itxt.setPadding(10, 10, 10, 10);
                    itxt.setTextSize(12);
                    itxt.setText(i);

                    Point p = TyBDMap.getProjection().toScreenLocation(marker.getPosition());p.y -= 90;
                    LatLng L = TyBDMap.getProjection().fromScreenLocation(p);

                    window = new InfoWindow(itxt, L, 0);
                    TyBDMap.showInfoWindow(window);

                    itxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TyMapActivity.this, "你点击了"+i, Toast.LENGTH_SHORT).show();
                            TyBDMap.hideInfoWindow();
                        }
                    });


                    CircleOptions option3=new CircleOptions().center(po).radius(radius7).stroke(new Stroke(5, 0xAA00FF00)).fillColor(0x22FFFFFF);
                    TyBDMap.addOverlay(option3);

                    CircleOptions option4=new CircleOptions().center(po).radius(radius10).stroke(new Stroke(5, 0xAAFF0000)).fillColor(0x55FF7F24);
                     TyBDMap.addOverlay(option4);

                    return true;
            }catch (Exception e){
                Toast.makeText(TyMapActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                TyBDMap.hideInfoWindow();
                mapView.refreshDrawableState();
                ///  TyBDMap.clear();
////            addOverlay();
                return false;
            }
                };
            };

            TyBDMap.setOnMarkerClickListener(listener);

//        BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener(){
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//
//
//                BitmapDescriptor A =BitmapDescriptorFactory.fromResource(R.drawable.hu16px);
//                BitmapDescriptor B =BitmapDescriptorFactory.fromResource(R.drawable.hu24px);
//                BitmapDescriptor C =BitmapDescriptorFactory.fromResource(R.drawable.hu32px);
//                LatLng po = marker.getPosition();
//                ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//                giflist.add(A);
//                giflist.add(B);
//                giflist.add(C);
//                MarkerOptions ooD = new MarkerOptions().position(po).icons(giflist).zIndex(5).period(10);
//                Marker  MarkerE = (Marker) (TyBDMap.addOverlay(ooD));
//
//
////                if(marker.getExtraInfo().get("info")==null){
////                    Toast.makeText(TyMapActivity.this, "info无", Toast.LENGTH_SHORT).show();
////                    mapView.refreshDrawableState();
////                }tr
//                try{
//
//                    final String i = (String) marker.getExtraInfo().get("info");
//                    final int r7 = (int) marker.getExtraInfo().get("Radius");
//                    final Button itxt = new Button(getApplicationContext());
//
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("info",i);
////                bundle.putInt("Radius",r7);
////                marker.setExtraInfo(bundle);
//
//                    int radius=r7*1000;
//                    final InfoWindow window;
//
//                    itxt.setBackgroundResource(R.drawable.locate_popup);
//                    itxt.setTextColor(Color.BLACK);
//                    itxt.setPadding(10, 10, 10, 10);
//                    itxt.setTextSize(12);
//                    itxt.setText(i);
//
//                    Point p = TyBDMap.getProjection().toScreenLocation(marker.getPosition());p.y -= 90;
//                    LatLng L = TyBDMap.getProjection().fromScreenLocation(p);
//
//                    window = new InfoWindow(itxt, L, 0);
//                    TyBDMap.showInfoWindow(window);
//
//                    itxt.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(TyMapActivity.this, "你点击了"+i, Toast.LENGTH_SHORT).show();
//                            TyBDMap.hideInfoWindow();
//                        }
//                    });
//
//
//                    CircleOptions option3=new CircleOptions().center(po).radius(radius).stroke(new Stroke(5, 0xAA00FF00)).fillColor(0x22FFFFFF);
//                    TyBDMap.addOverlay(option3);
//
//                    return true;
//
//                }catch (Exception e){
//                  //  Toast.makeText(TyMapActivity.this, "出错了", Toast.LENGTH_SHORT).show();
//                    TyBDMap.hideInfoWindow();
////                    TyBDMap.clear();
////                    addOverlay();
//                    mapView.refreshDrawableState();
//                    return false;
//                }
//
////                try {
////
////                }catch (Exception e){
////                    Toast.makeText(TyMapActivity.this, "出错了", Toast.LENGTH_SHORT).show();
////         //           TyBDMap.showInfoWindow(null);
//////                    TyBDMap.removeMarkerClickListener(BaiduMap.OnMarkerClickListener ){
//////                        if(this.onMarkerClick(Marker marker).contains(MarkerE)) {
//////                            this.onMarkerClick(Marker marker).remove(MarkerE);
//////                        }
//////                    };
////                    TyBDMap.hideInfoWindow();
////
////                    return false;
////                }
//            }
//        };


//        }catch (Exception e){
//            TyBDMap.removeMarkerClickListener(listener);
//            mapView.refreshDrawableState();
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_iv2:
                Intent intent1 = new Intent(TyMapActivity.this, ListActivity.class);
                startActivity(intent1);
                break;

            case R.id.iv_add:
                TyBDMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.iv_sub:
                TyBDMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;

            case R.id.iv_mode:
                if(f){
                    f = false;
                    TyBDMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

                }else{
                    f = true;
                    TyBDMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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

//    private class listening implements BaiduMap.OnMarkerClickListener{
//        @Override
//        public boolean onMarkerClick(Marker var1){
//            BitmapDescriptor A =BitmapDescriptorFactory.fromResource(R.drawable.hu16px);
//            BitmapDescriptor B =BitmapDescriptorFactory.fromResource(R.drawable.hu24px);
//            BitmapDescriptor C =BitmapDescriptorFactory.fromResource(R.drawable.hu32px);
//            LatLng po = var1.getPosition();
//            ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//            giflist.add(A);
//            giflist.add(B);
//            giflist.add(C);
//            MarkerOptions ooD = new MarkerOptions().position(po).icons(giflist).zIndex(5).period(10);
//            Marker  MarkerE = (Marker) (TyBDMap.addOverlay(ooD));
//
////            try{
//
//            final String i = (String) var1.getExtraInfo().get("info");
//            final int r7 = (int) var1.getExtraInfo().get("Radius");
//            final Button itxt = new Button(getApplicationContext());
//
//            int radius=r7*1000;
//            final InfoWindow window;
//
//            itxt.setBackgroundResource(R.drawable.locate_popup);
//            itxt.setTextColor(Color.BLACK);
//            itxt.setPadding(10, 10, 10, 10);
//            itxt.setTextSize(12);
//            itxt.setText(i);
//
//            Point p = TyBDMap.getProjection().toScreenLocation(var1.getPosition());p.y -= 90;
//            LatLng L = TyBDMap.getProjection().fromScreenLocation(p);
//
//            window = new InfoWindow(itxt, L, 0);
//            TyBDMap.showInfoWindow(window);
//
//            itxt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(TyMapActivity.this, "你点击了"+i, Toast.LENGTH_SHORT).show();
//                    TyBDMap.hideInfoWindow();
//                }
//            });
//
//
//            CircleOptions option3=new CircleOptions().center(po).radius(radius).stroke(new Stroke(5, 0xAA00FF00)).fillColor(0x22FFFFFF);
//            TyBDMap.addOverlay(option3);
//
//            return true;
//
////            }catch (Exception e){
////                //  Toast.makeText(TyMapActivity.this, "出错了", Toast.LENGTH_SHORT).show();
////                TyBDMap.hideInfoWindow();
////                mapView.refreshDrawableState();
////                return false;
////            }
//        };
//    }
}
