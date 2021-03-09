package com.developndesign.kcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developndesign.firebaseautomlvisionedge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoPasswordRestartButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginButton:
                    login();
                    break;
                case R.id.gotoPasswordRestartButton:
                    myStartActivity(PasswordResetActivity.class);
                    break;

            }
        }
    };

    private void login() {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passeordEditText)).getText().toString();
        if (email.length() > 0 && password.length() > 0) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startTost("로그인에 성공했습니다.");
                                myStartActivity(MainActivity.class);
                            } else {

                                if (task.getException() != null) {
                                    startTost(task.getException().toString());
                                }
                            }
                        }
                    });
        }else {
            startTost("이메일 또는 비밀번호를 입력해 주세요.");
            //통해 경고문 출력
        }
    }

    private void startTost(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }     //tost창을 통해 오류 안내메세지를 나오게 함.

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }//  로그인 로그인 성공시 메인 액티비티로 이동시켜줌



}
