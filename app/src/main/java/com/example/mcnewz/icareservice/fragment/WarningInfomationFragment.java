package com.example.mcnewz.icareservice.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.dao.RegencyInfoItemDao;
import com.example.mcnewz.icareservice.dao.SendDataList;
import com.example.mcnewz.icareservice.dao.WarningInfoItemDao;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class WarningInfomationFragment extends Fragment {
    private final String TAG = this.getClass().getName();

    private String lat;
    private String lng;
    private ProgressDialog pDialog;

    WarningInfoItemDao itemInfo;


    // Camera
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    private EditText editTo;
    private EditText editSubject;
    private EditText editTime;
    private EditText editDetail;
    private ImageView ivShowImg;
    private ImageButton btnImg;

    // Dialog
    Dialog dialog;


    final int CAMERA_REQUEST = 1234;
    final  int GALLERY_REQUEST = 5678;

    // setNameImage
    String millis ="";

    // parametorALl Receive
    String idUser;

    String typeAc;
    String typeName;
    String checkValue ="";
    String departSelect = "";
    String finishSendData = "";
    private String timeSubmit;
    private String currentDateand;

    String selectedPhoto="";
    private RadioGroup rdoGroup;
    private RadioButton rdo1;
    private RadioButton rdo2;
    private RadioButton rdo3;
    private RadioButton rdo4;
    private RadioButton rdo5;
    private RadioButton rdo6;
    private String typeWarning;
    private EditText edtRdo;


    public WarningInfomationFragment() {
        super();
    }
    public static WarningInfomationFragment newInstance() {
        WarningInfomationFragment fragment = new WarningInfomationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public static WarningInfomationFragment newInstance(String idUser,
                                                        String lat,
                                                        String lng) {

        WarningInfomationFragment fragment = new WarningInfomationFragment();
        Bundle args = new Bundle();


        args.putString("lat", lat);
        args.putString("lng", lng);
        args.putString("idUser", idUser);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        lat = getArguments().getString("lat");
        lng = getArguments().getString("lng");
        idUser = getArguments().getString("idUser");
        Toast.makeText(getContext(), ""+idUser, Toast.LENGTH_SHORT).show();

        setTimeSubmit();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_warning_infomation, container, false);
        initInstances(rootView);

        checkPermissionForCamera();
        requestPermissionForExternalStorage();
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        cameraPhoto = new CameraPhoto(Contextor.getInstance().getContext());
        galleryPhoto = new GalleryPhoto(Contextor.getInstance().getContext());

        editTime = (EditText) rootView.findViewById(R.id.editTime);
        editDetail = (EditText) rootView.findViewById(R.id.editDetail);
        ivShowImg = (ImageView) rootView.findViewById(R.id.ivShowImg);
        btnImg = (ImageButton) rootView.findViewById(R.id.btnImg);

        //rdoGroup = (RadioGroup) rootView.findViewById(R.id.rdoGroup);


        setOnClicke( rootView);
        setTextShow();
    }

    private void setTextShow() {
        editTime.setText(timeSubmit);

    }

    private void setTimeSubmit() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

        timeSubmit = df.format(c.getTime());
        currentDateand = dfDate.format(new Date());

    }

    private void setOnClicke(View rootView) {
        btnImg.setOnClickListener(ChooseImageDialogListener);

        //int selectedId = rdoGroup.getCheckedRadioButtonId();
        rdo1 = (RadioButton) rootView.findViewById(R.id.rdo1);
        rdo2 = (RadioButton) rootView.findViewById(R.id.rdo2);
        rdo3 = (RadioButton) rootView.findViewById(R.id.rdo3);
        rdo4 = (RadioButton) rootView.findViewById(R.id.rdo4);
        rdo5 = (RadioButton) rootView.findViewById(R.id.rdo5);
        edtRdo = (EditText) rootView.findViewById(R.id.edtRdo);


        rdo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdoSetFalse();
                typeWarning = "ไฟฟ้าขัดข้อง";
                rdo1.setChecked(true);
            }
        });

        rdo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rdoSetFalse();
                typeWarning = "ท่อน้ำชำรุด";
                rdo2.setChecked(true);

            }
        });



        rdo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdoSetFalse();
                typeWarning = "จุดเสี่ยง";
                rdo3.setChecked(true);

            }
        });

        rdo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdoSetFalse();
                typeWarning = "ถนนชำรุด";
                rdo4.setChecked(true);

            }
        });

        rdo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdoSetFalse();
                edtRdo.setEnabled(true);
                typeWarning = String.valueOf(edtRdo.getText());
                rdo5.setChecked(true);
            }
        });


    }

    private void rdoSetFalse() {
        rdo1.setChecked(false);
        rdo2.setChecked(false);
        rdo3.setChecked(false);
        rdo4.setChecked(false);
        rdo5.setChecked(false);
        edtRdo.setEnabled(false);

    }


    private void setRegencyInfo() {
        showpDialog();
        itemInfo = new WarningInfoItemDao();

        setItemInfoInsertToDatabase();

        Call<WarningInfoItemDao> call = HttpManager.getInstance().getService().setWarningInfoList(
                itemInfo.getDetail(),
                itemInfo.getLat(),
                itemInfo.getLng(),
                itemInfo.getMembers(),
                itemInfo.getImageName(),
                itemInfo.getSubject(),
                itemInfo.getTime_submit(),
                itemInfo.getCreate_date()
        );

        call.enqueue(new Callback<WarningInfoItemDao>() {
            @Override
            public void onResponse(Call<WarningInfoItemDao> call, Response<WarningInfoItemDao> response) {

                Toast.makeText(Contextor.getInstance().getContext(), "Complete", Toast.LENGTH_SHORT).show();
                hidepDialog();
            }

            @Override
            public void onFailure(Call<WarningInfoItemDao> call, Throwable t) {
                hidepDialog();
                //Toast.makeText(Contextor.getInstance().getContext(), "Not Complete "+ t.toString(), Toast.LENGTH_SHORT).show();

                Log.d("onFailureInfo", t.toString());
            }
        });

        uploadImage();

    }

    private void setItemInfoInsertToDatabase() {

        // TODO: HERE SetInfo
        itemInfo.setSubject(typeWarning);
        itemInfo.setDetail(editDetail.getText().toString());
        itemInfo.setLat(lat);
        itemInfo.setLng(lng);
        itemInfo.setMembers(Integer.parseInt(idUser));
        itemInfo.setImageName(String.valueOf(millis));
        itemInfo.setTime_submit(editTime.getText().toString());
        itemInfo.setCreate_date(java.sql.Date.valueOf(currentDateand));

    }


    int selectImage = 0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                selectImage = 1;

                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    //ivShowImg.setImageBitmap(getRotateBitmap(bitmap, 90));
                    ivShowImg.setImageBitmap(bitmap);
                    ivShowImg.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(), "Something wrong while taking photos ", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath =  galleryPhoto.getPath();
                selectedPhoto = photoPath;

                selectImage = 1;

                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    ivShowImg.setImageBitmap(bitmap);
                    ivShowImg.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(), "Something wrong while choosing photos ", Toast.LENGTH_SHORT).show();
                }

            }
        }//end if resultCode
    }

    private void uploadImage() {

        if(selectedPhoto == null || selectedPhoto.equals("")){
            Toast.makeText(Contextor.getInstance().getContext(), "No Image Selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO : Cannot Check Not Image Null  after NSC Do it
        try {
            Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 253).getBitmap();

            String encondImage= ImageBase64.encode(bitmap);
            Log.d(TAG, encondImage);
            HashMap<String, String> postData = new HashMap<String, String>( );
            postData.put("image", encondImage);

            PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.contains("uploaded_success")){
                        Toast.makeText(Contextor.getInstance().getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        WarningInfomationFragment.FragmentListener listener = (WarningInfomationFragment.FragmentListener) getActivity();
                        listener.onSendClickRegencyInfo(finishSendData, "a");

                    }else {
                        Toast.makeText(Contextor.getInstance().getContext(), "Image Uploaded Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // todo : get Web
//            task.execute("http://icareuserver.comscisau.com/icare/icareservice/upload.php");
            task.execute("http://icareuserver.comscisau.com/icare/icareservice/insertWarningInfo.php?name="+millis);
            task.setEachExceptionsHandler(new EachExceptionsHandler() {
                @Override
                public void handleIOException(IOException e) {
                    Toast.makeText(Contextor.getInstance().getContext(), "Connot Connect to server.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {
                    Toast.makeText(Contextor.getInstance().getContext(), "URL Error", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleProtocolException(ProtocolException e) {
                    Toast.makeText(Contextor.getInstance().getContext(), "Protocal Error", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                    Toast.makeText(Contextor.getInstance().getContext(), "Encoding Error", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (FileNotFoundException e) {
            Toast.makeText(Contextor.getInstance().getContext(), "Something wrong while encoding photos ", Toast.LENGTH_SHORT).show();
        }
    }

    // ratateBitmap
    private Bitmap getRotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return bitmap1;

    }



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }

    }








    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send2, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public interface FragmentListener{
        void onSendClickRegencyInfo(String finishSendData, String tab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_send:
                millis = String.valueOf(Calendar.getInstance().getTimeInMillis());
                setRegencyInfo();


                if(selectedPhoto == null || selectedPhoto.equals("")){
                    WarningInfomationFragment.FragmentListener listener = (WarningInfomationFragment.FragmentListener) getActivity();
                    listener.onSendClickRegencyInfo(finishSendData, "w");
                }
                break;
        }
        return true;
    }


    View.OnClickListener ChooseImageDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // custom dialog
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.item_cam_dialog);
            dialog.setTitle("Title...");


            TextView tvCamera = (TextView) dialog.findViewById(R.id.tvCamera);
            TextView tvGallery = (TextView) dialog.findViewById(R.id.tvGallery);
            TextView tvcancel = (TextView) dialog.findViewById(R.id.tvCancel);
            tvcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            tvCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Something wrong while taking photos ", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }
            });

            tvGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                    dialog.dismiss();

                }
            });

            dialog.show();
        }
    };

    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;

    public void requestPermissionForExternalStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(getContext(), "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }
}
