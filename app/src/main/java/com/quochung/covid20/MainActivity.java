package com.quochung.covid20;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.quochung.covid20.cacmuc.tintuc;
import com.quochung.covid20.cacmuc.trangchu;
import com.quochung.covid20.cacmuc.vietnam;

public class MainActivity extends AppCompatActivity {




    BottomNavigationView thanhdieuhuong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        khoitaoview();
        caidatbottom();





    }

    private void caidatbottom(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        thanhdieuhuong.getMenu().getItem(0).setChecked(false);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        thanhdieuhuong.setItemIconTintList(null);
        Fragment fragtrangchu = new trangchu();
        fragmentTransaction.replace(R.id.chinhgiua, fragtrangchu).commit();
        thanhdieuhuong.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                if (thanhdieuhuong.isPressed())
                    thanhdieuhuong.setPressed(false);
                else
                    thanhdieuhuong.setPressed(true);

                switch (item.getItemId()) {

                    case R.id.trangchu:
                        Fragment fragtrangchu = new trangchu();
                        fragmentTransaction.replace(R.id.chinhgiua, fragtrangchu).commit();
                        break;

                    case R.id.vietnam:
                        Fragment fragvietnam = new vietnam();
                        fragmentTransaction.replace(R.id.chinhgiua, fragvietnam).commit();
                        break;
                    case R.id.tintuc:
                        Fragment fragtintuc = new tintuc();
                        Toast.makeText(MainActivity.this, "Đang tải...", Toast.LENGTH_LONG).show();
                        fragmentTransaction.replace(R.id.chinhgiua, fragtintuc).commit();
                        break;
                }
                return true;
            }
        });
    }


    private void khoitaoview() {
        thanhdieuhuong= findViewById(R.id.bottom_menu);
    }
}