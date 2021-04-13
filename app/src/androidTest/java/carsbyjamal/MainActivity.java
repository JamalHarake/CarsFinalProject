package carsbyjamal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

        public void setContentView(int LayoutResID) {

        View baseLayout = this.getLayoutInflater().inflate(R.layout.activity_base_with_drawer, (ViewGroup)null);
        FrameLayout subclassLayout = (FrameLayout)baseLayout.findViewById(R.id.Frframe);
        this.getLayoutInflater().inflate(LayoutResID, (ViewGroup)subclassLayout, true);
        super.setContentView(baseLayout);
        Toolbar tb = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(tb);
        View var10000 = this.findViewById(R.id.drawer_layout);
        final DrawerLayout drawer = (DrawerLayout)var10000;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity)this, drawer, tb, R.string.open, R.string.close);
        drawer.addDrawerListener((DrawerLayout.DrawerListener)toggle);
        toggle.syncState();
        ((NavigationView)this.findViewById(R.id.navigation_view)).setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)(new NavigationView.OnNavigationItemSelectedListener() {
            public final boolean onNavigationItemSelected(@NotNull MenuItem it) {
                switch(it.getItemId()) {
                    case R.id.nav_item_database:
                        MainActivity.this.goToCarDatabase();
                    default:
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                }
            }
        }));
    }

    public boolean onCreateOptionsMenu(@Nullable Menu menu) {
        MenuInflater var10000 = this.getMenuInflater();
        MenuInflater inflater = var10000;
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.nav_item_database:
                this.goToCarDatabase();
            default:
                return true;
        }
    }

    private final void goToCarDatabase() {
        Intent goToCarDatabase = new Intent((Context)this, HomePage.class);
        this.startActivity(goToCarDatabase);
    }
}