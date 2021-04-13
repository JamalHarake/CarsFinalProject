package com.cst2335.teamproject;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivityWithDrawer extends AppCompatActivity {
    public void setContentView(int layoutResID) {
      /*  View baseLayout = this.getLayoutInflater().inflate(R.layout.activity_base_with_drawer, (ViewGroup)null);
        FrameLayout subclassLayout = (FrameLayout)baseLayout.findViewById(R.id.base_frame);
        this.getLayoutInflater().inflate(layoutResID, (ViewGroup)subclassLayout, true);
        super.setContentView(baseLayout);
        Toolbar tb = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(tb);
        View var10000 = this.findViewById(R.id.drawer_layout);
        final DrawerLayout drawer = (DrawerLayout)var10000;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity)this, drawer, tb, R.string.open, R.string.close);
        drawer.addDrawerListener((DrawerListener)toggle);
        toggle.syncState();
        ((NavigationView)this.findViewById(R.id.nav_view)).setNavigationItemSelectedListener((OnNavigationItemSelectedListener)(new OnNavigationItemSelectedListener() {
            public final boolean onNavigationItemSelected(@NotNull MenuItem it) {
                switch(it.getItemId()) {
                    case R.id.nav_item_database:
                        BaseActivityWithDrawer.this.goToCarDatabase();
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
    }*/
}}