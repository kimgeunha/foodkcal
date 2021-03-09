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

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.goToLoginButton).setOnClickListener(onClickListener);
    }

    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }//로그아웃 후 뒤로가기 클릭시 다시 메인으로 가는 현상을 잡아주는 코드.


    View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signUpButton:
                    signUp();
                    break;
                case R.id.goToLoginButton:
                    myStartActivity(LogInActivity.class);
                    break;

            }
        }
    };

    private void signUp() {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passeordEditText)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.passwordCheckEditText)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {

            if (password.equals(passwordCheck)) {


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startTost("회원가입에 성공했습니다.");
                                    myStartActivity(MainActivity.class);
                                } else {
                                    if (task.getException() != null) {
                                        startTost(task.getException().toString());
                                    }
                                    startTost(task.getException().toString());

                                }
                            }
                        });
            } else {
                startTost("비밀번호가 일치하지 않습니다.");
                //통해 경고문 출력

            }
        } else {
            startTost("이메일 또는 비밀번호를 입력해 주세요.");
            //통해 경고문 출력
        }
    }

    private void startTost(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }     //tost창을 통해 오류 안내메세지를 나오게 함.

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
