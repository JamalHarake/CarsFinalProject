package com.cst2335.teamproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public final class MenuItems extends AppCompatActivity {
    @SuppressLint({"SetTextI18n"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.menu_options);
        View textview1 = this.findViewById(R.id.titleoption);
        TextView t1 = (TextView)textview1;
        textview1 = this.findViewById(R.id.description);
        TextView t2 = (TextView)textview1;
    }
}