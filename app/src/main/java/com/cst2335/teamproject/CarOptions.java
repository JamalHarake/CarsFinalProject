package com.cst2335.teamproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class CarOptions extends Fragment {
    private AppCompatActivity parentActivity;

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle data = this.getArguments();
        Boolean isSaved = data != null ? data.getBoolean("Saved") : null;
        View var10000 = inflater.inflate(R.layout.fragment_cars, container, false);
        final View result = var10000;
        Context var10002 = result.getContext();
        final com.cst2335.teamproject.DataBase db = new com.cst2335.teamproject.DataBase(var10002);
        var10000 = result.findViewById(R.id.modelName);
        TextView model = (TextView)var10000;
        var10000 = result.findViewById(R.id.modelID);
        TextView modelID = (TextView)var10000;
        var10000 = result.findViewById(R.id.companyName);
        TextView make = (TextView)var10000;
        var10000 = result.findViewById(R.id.makeID);
        TextView makeID = (TextView)var10000;
        var10000 = result.findViewById(R.id.buyB);
        Button buyB = (Button)var10000;
        var10000 = result.findViewById(R.id.detailsB);
        Button viewD = (Button)var10000;
        var10000 = result.findViewById(R.id.saveB);
        final Button saveB = (Button)var10000;
        model.setText((CharSequence)String.valueOf(data != null ? data.getString("ModelName") : null));
        make.setText((CharSequence)String.valueOf(data != null ? data.getString("MakeName") : null));
        modelID.setText((CharSequence)String.valueOf(data != null ? data.getString("ModelID") : null));
        makeID.setText((CharSequence)String.valueOf(data != null ? data.getString("MakeID") : null));
        viewD.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                StringBuilder var10003 = (new StringBuilder()).append("http://www.google.com/search?q=");
                Bundle var10004 = data;
                var10003 = var10003.append(String.valueOf(var10004 != null ? var10004.getString("MakeName") : null)).append("+");
                var10004 = data;
                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(var10003.append(String.valueOf(var10004 != null ? var10004.getString("ModelName") : null)).toString()));
                com.cst2335.teamproject.CarOptions.this.startActivity(browserIntent);
            }
        }));
        buyB.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(it.getContext());
                alertDialog.setTitle((CharSequence)"Open AutoTrader?").setMessage((CharSequence)"You'll be taken to the AutoTrader website.").setNeutralButton((CharSequence)"Yes", (DialogInterface.OnClickListener)(new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface $noName_0, int $noName_1) {
                        StringBuilder var10003 = (new StringBuilder()).append("https://www.autotrader.ca/cars/?mdl=");
                        Bundle var10004 = data;
                        var10003 = var10003.append(String.valueOf(var10004 != null ? var10004.getString("ModelName") : null)).append("&make=");
                        var10004 = data;
                        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(var10003.append(String.valueOf(var10004 != null ? var10004.getString("MakeName") : null)).append("&loc=K2G1V8").toString()));
                        com.cst2335.teamproject.CarOptions.this.startActivity(browserIntent);
                    }
                })).setNegativeButton((CharSequence)"No", (click, arg) -> {

                })
                .create().show();
            }
        }));
        if (isSaved) {
            saveB.setText((CharSequence)"Remove From DataBase");
            saveB.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Bundle var10001 = data;
                    db.deleteData(var10001 != null ? var10001.getString("ModelID") : null);
                    @SuppressLint("WrongConstant") Snackbar var10000 = Snackbar.make(it, (CharSequence)"Car Removed From Database", Toast.LENGTH_SHORT);
                    Snackbar snackbar = var10000;
                    AppCompatActivity var3 = com.cst2335.teamproject.CarOptions.this.parentActivity;
                    if (var3 != null) {
                        FragmentManager var4 = var3.getSupportFragmentManager();
                        if (var4 != null) {
                            FragmentTransaction var5 = var4.beginTransaction();
                            if (var5 != null) {
                                var5 = var5.remove((Fragment) com.cst2335.teamproject.CarOptions.this);
                                if (var5 != null) {
                                    var5.commit();
                                }
                            }
                        }
                    }

                    snackbar.show();
                }
            }));
        } else {
            saveB.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    com.cst2335.teamproject.CarOptions var10000 = com.cst2335.teamproject.CarOptions.this;
                    Context var10001 = result.getContext();
                    ArrayList model = var10000.getModels(var10001);
                    Bundle var3 = data;
                    if (!model.contains(String.valueOf(var3 != null ? var3.getString("ModelID") : null))) {
                        var3 = data;
                        String var4 = var3 != null ? var3.getString("ModelName") : null;
                        Bundle var10002 = data;
                        String var5 = var10002 != null ? var10002.getString("ModelID") : null;
                        Bundle var10003 = data;
                        String var6 = var10003 != null ? var10003.getString("MakeName") : null;
                        Bundle var10004 = data;
                        db.insertData(var4, var5, var6, var10004 != null ? var10004.getString("MakeID") : null);
                    } else {
                        Toast.makeText(result.getContext(), (CharSequence)"Car already Saved", Toast.LENGTH_SHORT).show();
                    }

                    saveB.setText((CharSequence)"Saved");
                    saveB.setBackgroundColor(Color.parseColor("#97d992"));
                }
            }));
        }

        return result;
    }

    private final ArrayList getModels(Context context) {
        com.cst2335.teamproject.DataBase db = new com.cst2335.teamproject.DataBase(context);
        ArrayList model = new ArrayList();
        String[] columns = new String[]{com.cst2335.teamproject.DataBase.getCars.getCOL_MODELID()};
        Cursor var10000 = db.getWritableDatabase().query(false, com.cst2335.teamproject.DataBase.getCars.getTABLENAME(), columns, (String)null, (String[])null, (String)null, (String)null, (String)null, (String)null);
        Cursor results = var10000;
        int modelIndex = results.getColumnIndex(com.cst2335.teamproject.DataBase.getCars.getCOL_MODELID());

        while(results.moveToNext()) {
            model.add(results.getString(modelIndex));
        }

        results.close();
        return model;
    }

    public void onAttach(@NotNull Context context) {

        super.onAttach(context);
        this.parentActivity = (AppCompatActivity)context;
    }

    public static final void access$setParentActivity$p(com.cst2335.teamproject.CarOptions $this, AppCompatActivity var1) {
        $this.parentActivity = var1;
    }
}
