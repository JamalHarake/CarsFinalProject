package com.cst2335.teamproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class CarsList extends AppCompatActivity {


        private Boolean isTabl;
        private String carModels = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/";
        private CarOptions dFrag = new CarOptions();
        ArrayList element = new ArrayList<Cars>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);
        FrameLayout frame = findViewById(R.id.frame);
        isTabl = frame != null;
        com.cst2335.teamproject.CarsList.carsQuery s = new com.cst2335.teamproject.CarsList.carsQuery((Context)this);
        ProgressBar progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        s.execute(new String[]{this.carModels});



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.helpmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean var10000;
        switch(item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent((Context)this, com.cst2335.teamproject.MenuItems.class);
                intent.putExtra("help", false);
                this.startActivity(intent);
                var10000 = true;
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Context)this);
                alertDialog.setTitle((CharSequence)"Enter Car Manufacturer's Name and click go ").setMessage((CharSequence)"To view your saved cars, click on View Saved Cars. \nFor More help click More").setPositiveButton((CharSequence)"More", (DialogInterface.OnClickListener)(new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface $noName_0, int $noName_1) {
                        Intent intent = new Intent((Context) com.cst2335.teamproject.CarsList.this, com.cst2335.teamproject.MenuItems.class);
                        intent.putExtra("help", true);
                        com.cst2335.teamproject.CarsList.this.startActivity(intent);
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
    public final class carsQuery extends AsyncTask <String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                String model = (String) null;
                String make = (String) null;
                String modelID = (String) null;
                String makeID = (String) null;


               HttpURLConnection modelConnection = (HttpURLConnection) new URL(new URL(strings[0]), com.cst2335.teamproject.CarsList.this.getIntent().getStringExtra("search")).openConnection();
                InputStream modelTemp = modelConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(modelTemp, "UTF-8");

                for (int etype = xpp.getEventType(); etype != 1; etype = xpp.next()) {
                    if (etype == 2 && xpp.getName().equals("ModelsForMake")) {
                        xpp.nextTag();
                        makeID = xpp.nextText();
                        this.publishProgress(new Integer[]{25});
                        xpp.nextTag();
                        make = xpp.nextText();
                        this.publishProgress(new Integer[]{50});
                        xpp.nextTag();
                        modelID = xpp.nextText();
                        this.publishProgress(new Integer[]{75});
                        xpp.nextTag();
                        model = xpp.nextText();
                        Cars carItems = new Cars(makeID, make, modelID, model);
                        element.add(carItems);
                        this.publishProgress(new Integer[]{100});
                    }
                }
            } catch (Exception var12) {
                var12.printStackTrace();
            }

            return "Done";
        }

        protected void onProgressUpdate(@NotNull Integer... values) {
            super.onProgressUpdate((Integer[]) Arrays.copyOf(values, values.length));
            ProgressBar progressBar = findViewById(R.id.ProgressBar);
            progressBar.setProgress(values[0]);

        }

        @SuppressLint("WrongConstant")
        protected void onPostExecute(@Nullable String result) {
            super.onPostExecute(result);
            ListView list = findViewById(R.id.CarList);
            ProgressBar progressBar = findViewById(R.id.ProgressBar);
            progressBar.setVisibility(4);
            Toast.makeText(com.cst2335.teamproject.CarsList.this.getApplicationContext(), element.size() + "results found", Toast.LENGTH_LONG).show();

            com.cst2335.teamproject.CarsList var10002 = com.cst2335.teamproject.CarsList.this;
            Context var10003 = list.getContext();
            com.cst2335.teamproject.CarsList.MyAdapter adapter = var10002.new MyAdapter(var10003);
            list.setAdapter((ListAdapter) adapter);
        }
        public carsQuery(@NotNull Context context) {
            super();
        }
    }
        public final class MyAdapter extends BaseAdapter {
            private final LayoutInflater inflator;

            public int getCount() {
                return element.size();
            }

            @Nullable
            public String getItem(int position) {
                return element.get(position).toString();
            }


            public long getItemId(int position) {
                return (long)position;
            }

            @SuppressLint("ViewHolder")
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
                            Cars var10002 = (Cars)element.get(position);
                            dataToPass.putString("MakeID", var10002 != null ? var10002.returnMakeID() : null);
                            var10002 = (Cars)element.get(position);
                            dataToPass.putString("ModelID", var10002 != null ? var10002.returnModelID() : null);
                            var10002 = (Cars)element.get(position);
                            dataToPass.putString("MakeName", var10002 != null ? var10002.returnMake() : null);
                            var10002 = (Cars)element.get(position);
                            dataToPass.putString("ModelName", var10002 != null ? var10002.returnModel() : null);
                            dataToPass.putBoolean("Saved", false);


                            if (isTabl) {
                                com.cst2335.teamproject.CarsList.this.dFrag = new CarOptions();
                                com.cst2335.teamproject.CarsList.this.dFrag.setArguments(dataToPass);
                                com.cst2335.teamproject.CarsList.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, dFrag).commit();
                            } else {
                                Intent phoneIntent = new Intent(it.getContext(), com.cst2335.teamproject.EmptyActivity.class);
                                phoneIntent.putExtras(dataToPass);
                                com.cst2335.teamproject.CarsList.this.startActivity(phoneIntent);
                            }

                        }
                    }));
                }
               // make.text = elements[position]?.returnMake()
                Cars var10001 = (Cars)element.get(position);
                make.setText((CharSequence)(var10001 != null ? var10001.returnMake() : null));
                var10001 = (Cars)element.get(position);
                model.setText((CharSequence)(var10001 != null ? var10001.returnModel() : null));
                var10001 = (Cars)element.get(position);
                modelID.setText((CharSequence)(var10001 != null ? var10001.returnModelID() : null));
                var10001 = (Cars)element.get(position);
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

