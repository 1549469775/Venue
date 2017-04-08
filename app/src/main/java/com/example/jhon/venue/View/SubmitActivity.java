package com.example.jhon.venue.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.LocationData;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Http.HttpFile;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Modle.SubmitModle;
import com.example.jhon.venue.Preference.SubmitAction;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/3/27.
 */

public class SubmitActivity extends BaseActivity implements SubmitAction.SubmitListener {
    private static int RESULT_LOAD_IMAGE=1;

    @BindView(R.id.et_submit_title)
    EditText etSubmitTitle;
    @BindView(R.id.img_submit_booktop)
    ImageView imgSubmitBooktop;
    @BindView(R.id.et_submit_assent)
    EditText etSubmitAssent;
    @BindView(R.id.tv_submit_location)
    TextView tvSubmitLocation;
    @BindView(R.id.tv_submit_location_data)
    TextView tvSubmitLocationData;
    @BindView(R.id.switch_private)
    Switch switchPrivate;
    @BindView(R.id.btn_submit_submit)
    Button btnSubmitSubmit;

    private Story story=null;
    private String imgPath="";

    private SubmitAction action=new SubmitAction(this,this);

    @Override
    public void initView() {
        setContentView(R.layout.submit_activity);
        setupToolbar(true, true);
        getToolbar().setTitle("文章编辑");
        ButterKnife.bind(this);
    }

    @Override
    public void initOperation() {

    }

    @OnClick({R.id.img_submit_booktop, R.id.switch_private, R.id.btn_submit_submit})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.img_submit_booktop:
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
                break;
            case R.id.switch_private:
                if (switchPrivate.isChecked()){
                    HttpFile.getPermission(this, new JudgeListener() {
                        @Override
                        public void onSuccess() {
                            ShowUtil.showSnack(view,"PermissionOnSuccess");
                        }

                        @Override
                        public void onError(Exception e) {
                            ShowUtil.showSnack(view,e.getMessage());
                        }
                    });
                }else {

                }

//                if (switchPrivate.isChecked()){
//                    //TODO 私密信息，隐藏用户
//                    SubmitModle.createTimeLine(this, "sss", new JudgeListener() {
//                        @Override
//                        public void onSuccess() {
//                            ShowUtil.showToast(SubmitActivity.this,"成功了哦");
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            ShowUtil.showToast(SubmitActivity.this,e.getMessage());
//                        }
//                    });
//                }
                break;
            case R.id.btn_submit_submit:
                showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_IMAGE&&resultCode==RESULT_OK&&null!=data){
            Uri selectImage=data.getData();
            String[] filePathColumn={ MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectImage,filePathColumn,null,null,null);

            cursor.moveToFirst();
            int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
            String picturePath=cursor.getString(columnIndex);
            cursor.close();
            imgPath=picturePath;
            Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
            imgSubmitBooktop.setImageBitmap(bitmap);
            imgSubmitBooktop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,bitmap.getHeight()));
        }
    }

    public void showDialog(){
        if (BeanUtil.getTimeLine()!=null){
            final String[] title={"故事","人物","地点"};
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("请选择投递的类型");
            builder.setSingleChoiceItems(title, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String tit=title[which];
                    ShowUtil.showToast(getApplicationContext(),tit);
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    action.upLoad(imgPath, new ParseListener() {
                        @Override
                        public void error(Exception e) {

                        }

                        @Override
                        public void parseJson(String response) {
                            ShowUtil.showSnack(tvSubmitLocation,"上传成功了哦");
//                        ShowUtil.showToast(SubmitActivity.this,"上传成功了哦");
                            //TODO 提交
                            if (story==null){
//                            "0c01405277c009de7b7f9913df9c83a5bc57328b657ccfc5615d944e3a225be2.jpeg"
                                story=new Story();
                                story.setTitle(etSubmitTitle.getText().toString());
                                story.setCover(JsonUtil.getString("storedName",response));
                                story.setTimelineId(BeanUtil.getTimeLine().getId());
//                            ShowUtil.showLog("getFileName",HttpFile.getFileName(imgPath));
                                story.setContent(etSubmitAssent.getText().toString());
//                    story.setTimelineId();
                                story.setLatitude(LocationData.latitude);
                                story.setLongitude(LocationData.longitude);
                            }
                            action.submit(new JudgeListener() {
                                @Override
                                public void onSuccess() {
                                    story=null;
                                    ShowUtil.showSnack(tvSubmitLocation,"发表成功了哦");
//                                ShowUtil.showToast(SubmitActivity.this,"发表成功了哦");
                                }

                                @Override
                                public void onError(Exception e) {
                                    ShowUtil.showToast(SubmitActivity.this,e.getMessage());
                                }
                            });
                            dialog.dismiss();
                        }
                    });


                }
            });
            builder.setNegativeButton("取消",null);
            builder.show();
        }else {
            ShowUtil.showSnack(tvSubmitLocation,"请先选择一个时间轴");
            startActivity(new Intent(SubmitActivity.this, TimeLineActivity.class));
        }
    }


    @Override
    public Story getStory() {
        return story;
    }
}
