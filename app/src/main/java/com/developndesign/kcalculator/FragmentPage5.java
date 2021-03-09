package com.developndesign.kcalculator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developndesign.firebaseautomlvisionedge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentPage5 extends androidx.fragment.app.Fragment implements View.OnClickListener {//프래그먼트기능과 클릭리스너를 불러오기위해 선언
    FirebaseVisionImageLabeler labeler; //라벨러
    ProgressDialog progressDialog;
    FirebaseVisionImage image;
    private FirebaseAutoMLLocalModel localModel;//앱안에 넣어둔 학습모델의 데이터
    ImageView imageView;
    TextView textView;
    private Uri filePath; //실시간 데이터 베이스는 이미지를 경로(Uri)로 저장

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_page_5, container, false);
        //프래그먼트에서 사용하기 위해 id선언
        Button b1 = (Button) v.findViewById(R.id.btn1);
        Button b2 = (Button) v.findViewById(R.id.btn2);
        textView  = (TextView)v.findViewById(R.id.text);
        imageView  =  v.findViewById(R.id.imageView3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1: {// btn1을 클릭하면 이미지를 식별할수있다.
                CropImage.activity().start(getContext(), this);
                break;
            }
            case R.id.btn2:{//btn2를 클릭하면 업로드한 이미지를 데이터베이스의 저장할수있다.
                saveFile();
                break;
            }
        }

    }
    private void setLabelerFromLocalModel(Uri uri) {//앱안에 저장해둔 학습모델데이터를 불러와서
        localModel = new FirebaseAutoMLLocalModel.Builder().setAssetFilePath("model/manifest.json").build();
        try {
            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions
                    .Builder(localModel).setConfidenceThreshold(0.6f).build();//데이터의 식별수준이 60%
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
            image = FirebaseVisionImage.fromFilePath(getContext(), uri);//이미지를 uri형태로 변경하여 저장
            processImageLabeler(labeler, image);
        } catch (FirebaseMLException | IOException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    filePath = result.getUri(); //기기안에 저장되어있는 사진 uri로 전환
                    imageView.setImageURI(filePath); //이미지뷰의 uri를 통해 이미지 변경
                    textView.setText(""); //텍스트뷰의 텍스트변경
                    setLabelerFromLocalModel(filePath);
                } else
                    progressDialog.cancel();
            } else
                progressDialog.cancel();
        }
    }

    private void processImageLabeler(FirebaseVisionImageLabeler labeler, FirebaseVisionImage image) {
        labeler.processImage(image).addOnCompleteListener(new OnCompleteListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onComplete(@NonNull Task<List<FirebaseVisionImageLabel>> task) {//chicken,soondagoog,gimbab,gimchi,jokbal,tangsuyug,ddugbbogi,cake,dosirock,jjajangmun
                for (FirebaseVisionImageLabel label : task.getResult()) {
                    String eachlabel = label.getText();
                    float confidence = label.getConfidence();
                    if(eachlabel.equals("tangsuyug") ){
                        textView.append("탕수육"+"-"+"칼로리: 500kcal"+"\n\n"
                        +"단백질: 100g"+" "+"탄수화물:16g"+" "+"지방:77g");
                    }
                    else if(eachlabel.equals("cake")){
                        textView.append("케이크"+"-"+"칼로리: 700kcal"+"\n\n"
                                +"단백질: 70g"+" "+"탄수화물:105g"+" "+"지방:90g");
                    }
                    else if(eachlabel.equals("gimbab")) {
                        textView.append("김밥" + "-" + "칼로리: 451kcal" + "\n\n"
                                + "단백질: 72g" +" "+ "탄수화물:88g" +" "+ "지방:50g");
                    }
                    else if(eachlabel.equals("ddugbbogi")) {
                        textView.append("떡볶이" + "-" + "칼로리: 451kcal" + "\n\n"
                                + "단백질: 72g" +" "+ "탄수화물:88g" +" "+ "지방:50g");
                    }
                    else
                        textView.append(eachlabel + " - " + ("" + confidence * 100).subSequence(0, 4) + "%" + "\n\n");

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("OnFail", "" + e);
                Toast.makeText(getContext(), "Something went wrong! " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://kcalculator-4210d.appspot.com").child("images/" + filename);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

}
