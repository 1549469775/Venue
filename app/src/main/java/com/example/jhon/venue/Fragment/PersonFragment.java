package com.example.jhon.venue.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jhon.venue.Operation.AppLogin;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.CircleImageView;
import com.example.jhon.venue.Util.TransitionHelper;
import com.example.jhon.venue.View.EditorActivity;
import com.example.jhon.venue.View.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import static android.app.Activity.RESULT_OK;


public class PersonFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;

    @BindView(R.id.person_message)
    TextView personMessage;
    @BindView(R.id.person_collect)
    TextView personCollect;
    @BindView(R.id.person_setting)
    TextView personSetting;
    @BindView(R.id.person_about)
    TextView personAbout;
    @BindView(R.id.card_person)
    CardView cardPerson;
    @BindView(R.id.img_person)
    CircleImageView imgPerson;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.person_timeline)
    TextView personTimeline;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.person_fragment, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(R.drawable.tesst).centerCrop().into(imgPerson);
        return view;
    }

    public static PersonFragment newInstance() {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        return fragment;
    }

    @OnLongClick(R.id.img_person)
    boolean onLongClick() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
        return true;
    }

    @OnClick(R.id.img_person)
    public void onClick() {
        if (AppLogin.isLogin()){
            startActivity(new Intent(getContext(), EditorActivity.class));
        }else {
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
            startActivity(new Intent(getContext(), LoginActivity.class), transitionActivityOptions.toBundle());
        }
    }

    @OnClick({R.id.person_message, R.id.person_collect, R.id.person_setting,
            R.id.tv_name,R.id.person_about, R.id.card_person, R.id.person_timeline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                if (AppLogin.isLogin()){
                    startActivity(new Intent(getContext(), EditorActivity.class));
                }else {
                    Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
                    ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                    startActivity(new Intent(getContext(), LoginActivity.class), transitionActivityOptions.toBundle());
                }
                break;
            case R.id.person_message:
                //TODO 消息
                Snackbar.make(view, "" + view.getId(), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.person_collect:
                Snackbar.make(view, "" + view.getId(), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.person_timeline:
                Snackbar.make(view, "" + view.getId(), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.person_setting:
                Snackbar.make(view, "" + view.getId(), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.person_about:
                Snackbar.make(view, "" + view.getId(), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.card_person:
                if (!AppLogin.isLogin()) {
                    Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
                    ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                    startActivity(new Intent(getContext(), LoginActivity.class), transitionActivityOptions.toBundle());
                } else {
                    Snackbar.make(view, "WELCOME JOIN US!!!", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectImage, filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            cursor.close();

//            Bitmap bitmap= toRoundBitmap(BitmapFactory.decodeFile(picturePath));
            Glide.with(getContext()).load(picturePath)
                    .centerCrop()
                    .into(imgPerson);
            Snackbar.make(view, picturePath, Snackbar.LENGTH_SHORT).show();
//            img_person.setImageBitmap(bitmap);
        }
    }
}