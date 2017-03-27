package com.example.jhon.venue.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapStatus;

import java.io.File;

/**
 * Created by John on 2017/3/14.
 */

public class MapDownload implements OfflineMapManager.OfflineMapDownloadListener {

    private AlertDialog alertDialog;
    private AlertDialog alertDownload;
    private ProgressBar horizontalprogressBar;

    private OfflineMapManager mapManager;

    private boolean isHas=false;

    public void check(Context context){

    }

    public void downloadMap(final Context context){
        //检查地图‘’‘’‘
        check(context);
        mapManager=new OfflineMapManager(context,MapDownload.this);
//        if (mapManager.getDownloadOfflineMapCityList()!=null){
//            Log.d("xyx",mapManager.getDownloadOfflineMapCityList().get(0).getCity()+"sda");
//        }else {
//            Log.d("xyx","asdada"+"sda");
//        }
        if (!isHas){
            alertDownload=new AlertDialog.Builder(context)
                    .setTitle("您将下载离线地图")
//                    .setMessage(""+LocationData.cityname)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showProgressBarDialog(context);
//                                mapManager.downloadByCityCode(LocationData.citycode);//默认路径amap\data\vmap
                            // mapManager.downloadByCityName();

                        }
                    })
                    .setNegativeButton("取消",null)
                    .create();
            alertDownload.show();
            isHas=true;
        }else {
            Toast.makeText(context,"朋友，你有了",Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBarDialog( Context context){
//        View view= LayoutInflater.from(context).inflate(R.layout.horizonprograssbar,null,false);
//        horizontalprogressBar= (ProgressBar) view.findViewById(R.id.horizontalprogressBar);
        horizontalprogressBar.setMax(100);
        alertDialog=new AlertDialog.Builder(context)
                .setTitle("进度")
//                .setView(view)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onDownload(int i, int i1, String s) {
        Log.d("xyx","name");
        horizontalprogressBar.setProgress(i1);
        if (i==OfflineMapStatus.SUCCESS){
            alertDownload.dismiss();
            alertDialog.dismiss();
            Log.d("xyx",s);

        }
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }
}
