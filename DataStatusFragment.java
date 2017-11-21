package com.example.cuongducnguyenkp.cdldemo2;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

public class DataStatusFragment extends Fragment {
    ArrayList<String> arrayListID, arrayListName;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabaseObj;
    ListAdapterCollect listAdapter;
    ListView listViewMessage;
    TextView textViewCountAllCustomer;
    int countAllCustomer;
    Cursor cursor;

    public static DataStatusFragment newInstance() {
        DataStatusFragment fragment = new DataStatusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.activity_item_three_fragment, container, false);
        arrayListID = new ArrayList<String>();
        arrayListName = new ArrayList<String>();
        sqLiteHelper = new SQLiteHelper(getContext());
        listViewMessage = (ListView) rootview.findViewById(R.id.listViewMonthly);
        textViewCountAllCustomer = (TextView) rootview.findViewById(R.id.textViewCountAllCustomer);

        OpenSQLiteDataBase();



        return rootview;
    }

    public void onResume() {

        try {
            ShowSQLiteDBdata();
            super.onResume();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void ShowSQLiteDBdata() throws ParseException {

        arrayListID.clear();
        arrayListName.clear();
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT t2.service_type, COUNT(1)" +
                        " FROM " + SQLiteHelper.TABLE_NAME + " t2"
                        + " GROUP BY " +
                        "t2.service_type having COUNT(1) >= 1"
                , null)
        ;
        int i = 0;
        if (cursor.moveToFirst()) {
            do {

                arrayListID.add(cursor.getString(0));
                //Inserting Column ID into Array to Use at ListView Click Listener Method.
                arrayListName.add(cursor.getString(1));
//                Toast.makeText(getActivity(), arrayListID.get(i) + " = " + arrayListName.get(i), Toast.LENGTH_SHORT).show();
                i++;
            } while (cursor.moveToNext());
        }
        Toast.makeText(getActivity(), String.valueOf(arrayListID.size()), Toast.LENGTH_SHORT).show();
        listAdapter = new ListAdapterCollect(getContext(),
                arrayListID,
                arrayListName
        );
        listViewMessage.setAdapter(listAdapter);

        cursor = sqLiteDatabase.rawQuery("SELECT COUNT(id) FROM " + SQLiteHelper.TABLE_NAME + "", null);
        if (cursor.moveToFirst()) {
            do {
                countAllCustomer = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        textViewCountAllCustomer.setText("Customer = "+String.valueOf(countAllCustomer));
        cursor.close();
    }

    private void OpenSQLiteDataBase() {
        sqLiteDatabaseObj = getActivity().openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
}