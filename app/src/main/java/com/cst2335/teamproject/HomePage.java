package com.cst2335.teamproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button ChatButton = findViewById(R.id.Chatbutton);
        Intent Npage = new Intent(this, ChatActivity.class);
        ChatButton.setOnClickListener(bt -> startActivity(Npage));

        EditText editText = findViewById(R.id.search);
        Button ButtonSearch = findViewById(R.id.SearchButton);
        Button favorite = findViewById(R.id.SavedButton);
        SharedPreferences sharedPref = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        editText.setText(sharedPref.getString("SEARCH", ""));
        favorite.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context) com.cst2335.teamproject.HomePage.this, SavedCars.class);
                com.cst2335.teamproject.HomePage.this.startActivity(intent);
            }
        }));

        ButtonSearch.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                String text = editText.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(com.cst2335.teamproject.HomePage.this, "Nothing to search", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent((Context) com.cst2335.teamproject.HomePage.this, CarsList.class);
                    intent.putExtra("search", text);
                    com.cst2335.teamproject.HomePage.this.startActivity(intent);
                }
            }
        }));
    }

    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        this.getMenuInflater().inflate(R.menu.helpmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        boolean v1;
        switch(item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent((Context)this, MenuItems.class);
                intent.putExtra("help", false);
                this.startActivity(intent);
                v1 = true;
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Context)this);
                alertDialog.setTitle((CharSequence)"Help Section ").setMessage((CharSequence)"Welcome to Help section !\n" +
                        "        You can put the model you want to search in the\n" +
                        "        search bar. You click the model you want. You can now click on the details to\n" +
                        "        have more informations about the car. You can also\n" +
                        "        add the car to review it later ! And if you liked it,\n" +
                        "        you can buy it by simply click the Buy button, which\n" +
                        "        gonna direct you to Auto Trader link.");
                alertDialog.show();
                v1 = true;
                break;
            default:
                v1 = super.onOptionsItemSelected(item);
        }

        return v1;
    }

    protected void onPause() {
        super.onPause();
        EditText searchT = findViewById(R.id.search);
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SEARCH", searchT.getText().toString()).apply();
    }
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);

        EditText editText = findViewById(R.id.search);
        editText.setText(sharedPreferences.getString("SEARCH", ""));
    }
    }
