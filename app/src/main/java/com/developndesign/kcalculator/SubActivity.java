package com.developndesign.kcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.developndesign.firebaseautomlvisionedge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class SubActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.statuspage);
        Button btn1 = (Button) findViewById(R.id.logout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        btn1.setOnClickListener(this);
        if (user == null) {
            myStartActivity(SignUpActivity.class);
        } else {
            for (UserInfo profile : user.getProviderData()) {
                String name = profile.getDisplayName();
                Log.e("이름:", "이름:" + name);
                if (name != null) {
                    if (name.length() == 0) {
                        myStartActivity(MemberIntActivity.class);
                    }
                }
            }
        }
    }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    //파이어베이스와 연동된 로그인정보 로그아웃.
                    myStartActivity(SignUpActivity.class);
                    break;
            }
        }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
