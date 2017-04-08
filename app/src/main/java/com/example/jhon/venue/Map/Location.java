package com.example.jhon.venue.Map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.jhon.venue.Bean.LocationData;
import com.example.jhon.venue.R;

/**
 * Created by John on 2017/3/6.
 */

public class Location implements LocationSource,AMapLocationListener {

    private  Context context;

    private AMap aMap;
    private MapView mapView;

    private LatLng latLng;

    private OnLocationChangedListener mListener = null;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
//    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private boolean isFirst=true;

    private Location(Context context,MapView mapView){
        this.context=context;
        this.mapView=mapView;

        init();
    }
    public static Location newInstance(Context context, MapView mapView) {

        Location location = new Location(context,mapView);

        return location;
    }

    private void init(){
        if (aMap==null){
            aMap=mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        UiSettings settings=aMap.getUiSettings();
        // 设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮

        settings.setLogoPosition(0);
        settings.setScrollGesturesEnabled(true); //这个方法设置了地图是否允许通过手势来移动。
        settings.setTiltGesturesEnabled(false);//这个方法设置了地图是否允许通过手势来倾斜。
        settings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        settings.setZoomControlsEnabled(false);//这个方法设置了地图是否允许显示缩放按钮。
        settings.setRotateGesturesEnabled(false);//这个方法设置了地图是否允许通过手势来旋转。

        //设置指南针方向
//        settings.setCompassEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);

        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        myLocationStyle.radiusFillColor(android.R.color.transparent);
//        myLocationStyle.strokeColor(android.R.color.transparent);
//        myLocationStyle.strokeWidth(0);//设置边框宽度
//        aMap.setMyLocationStyle(myLocationStyle);

    }

    private void initLoc(){
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(context);
            //设置定位回调监听
            mLocationClient.setLocationListener(this);//因为该类已经继承了此里面的方法，用this
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//             mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
//            //单次定位
//            //获取一次定位结果：
//            // 该方法默认为false。
//            mLocationOption.setOnceLocation(true);
//            //获取最近3s内精度最高的一次定位结果：
//            // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//            mLocationOption.setOnceLocationLatest(true);

            //自定义连续定位
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            mLocationOption.setInterval(1000);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否强制刷新WIFI，默认为true，强制刷新。
            mLocationOption.setWifiActiveScan(false);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);

            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

            // 给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();//启动定位
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        initLoc();
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(final AMapLocation amapLocation) {
        if (mListener != null&&amapLocation != null) {
            if (amapLocation != null
                    &&amapLocation.getErrorCode() == 0) {
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getAccuracy();//获取精度信息
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息
                amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                amapLocation.getFloor();//获取当前室内定位的楼层
                LocationData.latitude=amapLocation.getLatitude();
                LocationData.longitude=amapLocation.getLongitude();
//获取定位时间
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//api 24
//            Date date = new Date(amapLocation.getTime());
//            df.format(date);//api 24
                if (isFirst){
                    latLng=new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                    isFirst = false;

//                    aMap.addMarker(getMarkerOptions(amapLocation));//在定位处显示marker

//                    new MapDownload().downloadMap(context);//下载地图
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }

        }
    }

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() +  "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        options.title(buffer.toString());
        //子标题
        options.snippet("这里好火");
        //设置多少帧刷新一次图片资源
        options.period(60);

        return options;

    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void startLocation(){
        mLocationClient.startLocation();//启动定位
        isFirst=true;
    }

    public void stopLocation(){
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    public void onDestroy() {
        if(null != mLocationClient){
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

}
