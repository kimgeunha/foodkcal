package com.developndesign.kcalculator;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developndesign.firebaseautomlvisionedge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MemberIntActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_int);

        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    profileUpdate();
                    break;

            }
        }
    };

    private void profileUpdate() {
        String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        if (name.length() > 0) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            if (user !=null) {

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startTost("회원정보 등록에 성공하였습니다.");
                                    finish();
                                }
                            }
                        });//회원정보 업데이트
            }
        }else {
            startTost("회원정보를 입력해 주세요.");
            //통해 경고문 출력
        }
    }

    private void startTost(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }     //tost창을 통해 오류 안내메세지를 나오게 함.

}
