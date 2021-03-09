package com.developndesign.kcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.developndesign.firebaseautomlvisionedge.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });
    }
    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);//메뉴 선택하면 화면 이동
        if (fragment == null) {
            if (id == R.id.navigation_1) {
                fragment = new FragmentPage1();
            } else if (id == R.id.navigation_2) {
                fragment = new FragmentPage2();
            } else if (id == R.id.navigation_3) {
                fragment = new FragmentPage3();
            } else if (id == R.id.navigation_4) {
                fragment = new FragmentPage4();
            } else {
                fragment = new FragmentPage5();
            }
            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){//액션바에 매뉴 넣어줌
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
// <!--액티비티 추가시 <application> 안에 추가해줘야함--> androidManifest에 넣어줘야함 꼭 모든 파일을
            case R.id.Notifications:
                Toast.makeText(this,"알림 화면", Toast.LENGTH_SHORT).show();
                Intent alarmIntent = new Intent(this, alarmPage.class);
                startActivity(alarmIntent);
                return true;

            case R.id.StatusMenu:
                Toast.makeText(this,"상태 메뉴", Toast.LENGTH_SHORT).show();
                Intent statusIntent = new Intent(this, SubActivity.class);
                startActivity(statusIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}