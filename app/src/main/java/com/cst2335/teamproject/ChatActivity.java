package com.cst2335.teamproject;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    MyListAdapter myAdapter;
    private static int ACTIVITY_VIEW_MESSAGE = 33;
    SQLiteDatabase db;
    int position;
    ArrayList<SendReceiveMessage> elements = new ArrayList<>();
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";



    public class SendReceiveMessage {
        public String message;
        public boolean isSend;
        protected long id;

        public SendReceiveMessage(String m, boolean s, long i) {
            message = m;
            isSend = s;
            id = i;
        }
        public SendReceiveMessage(String m, long i) {
            message = m;
            id = i;
        }

        public void update(String m, boolean s) {
            message = m;
            isSend = s;
        }

        public SendReceiveMessage(String m, boolean s) {
            this(m, s, 0);
        }

        public String getMessage() {
            return message;
        }

        public long getId() {
            return id;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        LoadDataFromDatabase();
        EditText et = findViewById(R.id.Message);
        ListView myList = (ListView) findViewById(R.id.ListView);
        myList.setAdapter(myAdapter = new MyListAdapter());

        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded

        myList.setAdapter(myAdapter);
        myList.setOnItemClickListener((list, item, position, id) -> {
            //Create a bundle to pass data to the new fragment
            Bundle dataToPass = new Bundle();
            // dataToPass.putString(ITEM_SELECTED, String.valueOf(elements.get(position)));
            dataToPass.putInt(ITEM_POSITION, position);
            dataToPass.putLong(ITEM_ID, id);

            if(isTablet)
            {
                DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Do you want to delete this?")


                        .setMessage("The selected row is " + position)
                        .setPositiveButton("Yes", (click, arg) -> {
                            getSupportFragmentManager().beginTransaction().remove(dFragment).commit();
                            DeleteDatabase(id);
                            elements.remove(position);
                            myAdapter.notifyDataSetChanged();

                        })
                        .setNegativeButton("No", (click, arg) -> {

                        })
                        .create().show();


            }
            else //isPhone
            {
                Intent nextActivity = new Intent(ChatActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Do you want to delete this?")


                        .setMessage("The selected row is " + position)
                        .setPositiveButton("Yes", (click, arg) -> {
                            DeleteDatabase(id);
                            elements.remove(position);
                            myAdapter.notifyDataSetChanged();

                        })
                        .setNegativeButton("No", (click, arg) -> {

                        })
                        .create().show();
            }


        });


        Button SendButton = findViewById(R.id.SendButton);
        SendButton.setOnClickListener(click -> {
            String m = et.getText().toString();
            ContentValues newMessage = new ContentValues();
            newMessage.put(MyOpener.COL_MESSAGE, m);
            long newID = db.insert(MyOpener.TABLE_NAME, null, newMessage);
            SendReceiveMessage smss = new SendReceiveMessage(et.getText().toString(), true, newID);
            elements.add(smss);
            myAdapter.notifyDataSetChanged();
            et.setText("");
        });

        Button ReceiveButton = findViewById(R.id.ReceiveButton);
        ReceiveButton.setOnClickListener(click -> {
            String m = et.getText().toString();
            ContentValues newMessage = new ContentValues();
            newMessage.put(MyOpener.COL_MESSAGE, m);
            long newID = db.insert(MyOpener.TABLE_NAME, null, newMessage);
            SendReceiveMessage smss = new SendReceiveMessage(et.getText().toString(), false, newID);
            elements.add(smss);
            myAdapter.notifyDataSetChanged();
            et.setText("");
        });
    }




    private int DeleteDatabase( long id) {
        String whereArgs[] = {""+id};
        int count = db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", whereArgs);
        return count;

    }

    private void LoadDataFromDatabase() {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE};
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        int MessageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);


        while (results.moveToNext()) {
            String msg = results.getString(MessageColumnIndex);
            long id = results.getLong(idColIndex);


            elements.add(new SendReceiveMessage(msg, id));
        }
    }






    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int row) {

            return elements.get(row);

        }


        @Override
        public View getView(int row, View convertView, ViewGroup parent) {
            SendReceiveMessage thisMessage = elements.get(row);
            View thisRow;
            LayoutInflater inflater = getLayoutInflater();
            if (thisMessage.isSend) {
                thisRow = inflater.inflate(R.layout.test_layout, parent, false);
            } else {
                thisRow = inflater.inflate(R.layout.receiver_layout, parent, false);
            }
            TextView thisRowText = thisRow.findViewById(R.id.TextGoesHere);
            thisRowText.setText(thisMessage.message);
            return thisRow;
        }

        @Override
        public long getItemId(int id) {
            return ((SendReceiveMessage)getItem(id)).getId();
        }
    }
}

