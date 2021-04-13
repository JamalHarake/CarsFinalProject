package com.cst2335.teamproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EmptyActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_empty);
        Intent var10000 = this.getIntent();
        Bundle dataToPass = var10000.getExtras();
        CarOptions dFrag = new CarOptions();
        dFrag.setArguments(dataToPass);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, (Fragment)dFrag).commit();
    }

    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        this.getMenuInflater().inflate(R.menu.helpmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        boolean var10000;
        switch(item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent((Context)this, MenuItems.class);
                intent.putExtra("help", false);
                this.startActivity(intent);
                var10000 = true;
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Context)this);
                alertDialog.setTitle((CharSequence)"Help Section ").setMessage((CharSequence)"TWelcome to Help section !\n" +
                        "        You can put the model you want to search in the\n" +
                        "        search bar. You can now click on the details to\n" +
                        "        have more informations about the car. You can also\n" +
                        "        add the car to review it later ! And if you liked it,\n" +
                        "        you can buy it by simply click the Buy button, which\n" +
                        "        gonna direct you to Auto Trader link.").setPositiveButton((CharSequence)"More", (DialogInterface.OnClickListener)(new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface $noName_0, int $noName_1) {
                        Intent intent = new Intent((Context) com.cst2335.teamproject.EmptyActivity.this, MenuItems.class);
                        intent.putExtra("help", true);
                        com.cst2335.teamproject.EmptyActivity.this.startActivity(intent);
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
}