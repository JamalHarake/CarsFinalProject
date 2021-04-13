package com.cst2335.teamproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class SavedCars extends AppCompatActivity {
    @NotNull
    ArrayList element = new ArrayList<Cars>();
    private final DataBase db = new DataBase((Context)this);
    private com.cst2335.teamproject.SavedCars.MyAdapter adapter;
    private Boolean isTabl;
    private CarOptions dFrag = new CarOptions();

    @NotNull
    public final ArrayList getElements() {
        return this.element;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_saved_cars);
        FrameLayout frame = (FrameLayout)this.findViewById(R.id.frame);
        this.isTabl = frame != null;
        this.loadDataFromDatabase();
        View var10000 = this.findViewById(R.id.carList);
        ListView list = (ListView)var10000;
        Context var10004 = list.getContext();
        this.adapter = new com.cst2335.teamproject.SavedCars.MyAdapter(var10004);
        list.setAdapter((ListAdapter)this.adapter);
        if (this.element.isEmpty()) {
            Toast.makeText(this.getApplicationContext(), (CharSequence)"No Favourites Added", Toast.LENGTH_LONG).show();
        }

        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout)this.findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                com.cst2335.teamproject.SavedCars.this.getElements().clear();
                com.cst2335.teamproject.SavedCars.this.loadDataFromDatabase();
                com.cst2335.teamproject.SavedCars.MyAdapter var10000 = com.cst2335.teamproject.SavedCars.this.adapter;
                if (var10000 != null) {
                    var10000.notifyDataSetChanged();
                }

                SwipeRefreshLayout var1 = pullToRefresh;
                var1.setRefreshing(false);
            }
        }));
    }

    private final void loadDataFromDatabase() {
        String[] columns = new String[]{DataBase.getCars.getCOL_MAKEID(), DataBase.getCars.getCOL_MAKE(), DataBase.getCars.getCOL_MODELID(), DataBase.getCars.getCOL_MODEL()};
        Cursor var10000 = this.db.getWritableDatabase().query(false, DataBase.getCars.getTABLENAME(), columns, (String)null, (String[])null, (String)null, (String)null, (String)null, (String)null);
        Cursor results = var10000;
        int makeID = results.getColumnIndex(DataBase.getCars.getCOL_MAKEID());
        int make = results.getColumnIndex(DataBase.getCars.getCOL_MAKE());
        int modelID = results.getColumnIndex(DataBase.getCars.getCOL_MODELID());
        int model = results.getColumnIndex(DataBase.getCars.getCOL_MODEL());
        results.moveToPosition(-1);

        while(results.moveToNext()) {
            String mkID = results.getString(makeID);
            String mk = results.getString(make);
            String mdlID = results.getString(modelID);
            String mdl = results.getString(model);

            Cars cars = new Cars(mkID, mk, mdlID, mdl);
            this.element.add(cars);
        }

    }

    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        this.getMenuInflater().inflate(R.menu.helpmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        boolean var10000;
        switch(item.getItemId()) {
            case 1000217:
                Intent intent = new Intent((Context)this, com.cst2335.teamproject.MenuItems.class);
                intent.putExtra("help", false);
                this.startActivity(intent);
                var10000 = true;
                break;
            case 1000327:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Context)this);
                alertDialog.setTitle((CharSequence)"Enter Car Manufacturer's Name and click go ").setMessage((CharSequence)"To view your saved cars, click on View Saved Cars. \nFor More help click More").setPositiveButton((CharSequence)"More", (DialogInterface.OnClickListener)(new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface $noName_0, int $noName_1) {
                        Intent intent = new Intent((Context) com.cst2335.teamproject.SavedCars.this, com.cst2335.teamproject.MenuItems.class);
                        intent.putExtra("help", true);
                        com.cst2335.teamproject.SavedCars.this.startActivity(intent);
                    }
                })).setNeutralButton("Close Help", (click, arg) -> {

            });
                alertDialog.show();
                var10000 = true;
                break;
            default:
                var10000 = super.onOptionsItemSelected(item);
        }

        return var10000;
    }

    protected void onResume() {
        super.onResume();
        this.element.clear();
        this.loadDataFromDatabase();
        com.cst2335.teamproject.SavedCars.MyAdapter var10000 = this.adapter;
        if (var10000 != null) {
            var10000.notifyDataSetChanged();
        }

        if (this.element.isEmpty()) {
            Toast.makeText(this.getApplicationContext(), (CharSequence)"No Favourites Added", Toast.LENGTH_LONG).show();
        }

    }

    public static final void access$setTabl$p(com.cst2335.teamproject.SavedCars $this, Boolean var1) {
        $this.isTabl = var1;
    }

    public static final void access$setAdapter$p(com.cst2335.teamproject.SavedCars $this, com.cst2335.teamproject.SavedCars.MyAdapter var1) {
        $this.adapter = var1;
    }

    public final class MyAdapter extends BaseAdapter {
        private final LayoutInflater inflator;

        public int getCount() {
            return com.cst2335.teamproject.SavedCars.this.getElements().size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return element.get(position).toString();
        }


        public long getItemId(int position) {
            return (long)position;
        }

        @Nullable
        public View getView(final int position, @Nullable View convertView, @Nullable ViewGroup parent) {
            View view = (View)null;
            view = this.inflator.inflate(R.layout.caritem, parent, false);
            View var10000 = view.findViewById(R.id.modelName);
            TextView model = (TextView)var10000;
            var10000 = view.findViewById(R.id.companyName);
            TextView make = (TextView)var10000;
            var10000 = view.findViewById(R.id.modelID);
            TextView modelID = (TextView)var10000;
            var10000 = view.findViewById(R.id.makeID);
            TextView makeID = (TextView)var10000;
            if (view != null) {
                view.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                    public final void onClick(View it) {
                        Bundle dataToPass = new Bundle();
                        Cars var10002 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
                        dataToPass.putString("MakeID", var10002 != null ? var10002.returnMakeID() : null);
                        var10002 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
                        dataToPass.putString("ModelID", var10002 != null ? var10002.returnModelID() : null);
                        var10002 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
                        dataToPass.putString("MakeName", var10002 != null ? var10002.returnMake() : null);
                        var10002 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
                        dataToPass.putString("ModelName", var10002 != null ? var10002.returnModel() : null);
                        dataToPass.putBoolean("Saved", true);
                        if (isTabl == true) {
                            com.cst2335.teamproject.SavedCars.this.dFrag = new CarOptions();
                            com.cst2335.teamproject.SavedCars.this.dFrag.setArguments(dataToPass);
                            com.cst2335.teamproject.SavedCars.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, (Fragment) com.cst2335.teamproject.SavedCars.this.dFrag).commit();
                        } else {
                            Intent phoneIntent = new Intent(it.getContext(), com.cst2335.teamproject.EmptyActivity.class);
                            phoneIntent.putExtras(dataToPass);
                            com.cst2335.teamproject.SavedCars.this.startActivity(phoneIntent);
                        }

                    }
                }));
            }

            Cars var10001 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
            make.setText((CharSequence)(var10001 != null ? var10001.returnMake() : null));
            var10001 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
            model.setText((CharSequence)(var10001 != null ? var10001.returnModel() : null));
            var10001 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
            modelID.setText((CharSequence)(var10001 != null ? var10001.returnModelID() : null));
            var10001 = (Cars) com.cst2335.teamproject.SavedCars.this.getElements().get(position);
            makeID.setText((CharSequence)(var10001 != null ? var10001.returnMakeID() : null));
            return view;
        }

        public MyAdapter(@NotNull Context context) {
            super();
            LayoutInflater var10001 = LayoutInflater.from(context);
            this.inflator = var10001;
        }
    }
}