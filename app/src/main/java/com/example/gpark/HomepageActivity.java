package com.example.gpark;

import static com.google.android.gms.common.util.CollectionUtils.setOf;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean faculty=false, admin=false;
        if(admin){
            setContentView(R.layout.activity_homepage);
        }else if(faculty){
            setContentView(R.layout.activity_homepage_faculty);
        }else{
            setContentView(R.layout.activity_homepage_student);
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();




    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this,"Click on list", Toast.LENGTH_SHORT);

        switch(item.getItemId()){

            case R.id.map:
                Toast.makeText(this,"map", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new MapFragment()).commit();
                break;
            case R.id.history:
                Toast.makeText(this,"history", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new HistoryFragment()).commit();
                break;
            case R.id.vip:
                Toast.makeText(this,"vip", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new VipFragment()).commit();
                break;
            case R.id.users:
                Toast.makeText(this,"users", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new UsersFragment()).commit();
                break;
            case R.id.detail:
                Toast.makeText(this,"detail", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new DetailFragment()).commit();
                break;

            case R.id.booking:
                Toast.makeText(this,"booking", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new BookingFragment()).commit();
                break;
            case R.id.mapSlotForm:
                Toast.makeText(this,"mapSlotForm", Toast.LENGTH_SHORT);
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment,new MapSlotFormFragment()).commit();
                break;
            default: break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
