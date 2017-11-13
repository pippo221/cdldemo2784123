package com.example.cuongducnguyenkp.cdldemo2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemFourFragment extends Fragment {
    TextView txtView, txtViewAddress, txtViewTEL, txtViewServiceType, txtViewMAC, txtViewContractDuration, txtViewExpiredIn, txtViewMess;
    EditText editTextName, editTextAddress, editTextPhone, editTextCompanyName, editTextMAC, editTextServiceType;
    private boolean viewGroupIsVisible = true;
    private View mViewGroup;
    private View mViewGroup2;
    private Button mButton;
    RelativeLayout relativeLayout1, relativeLayout2;
    SQLiteOpenHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapterDuration listAdapter;
    ListView listViewMessage;
    SQLiteDatabase sqLiteDatabaseObj;

    ArrayList<String> arrayListID;
    ArrayList<String> arrayListCharge;
    ArrayList<String> arrayListName;
    ArrayList<String> arrayListDurationStart;
    ArrayList<String> arrayListDurationEnd;
    ArrayList<String> arrayListMessage;
    ArrayList<Integer> CONTRACT_DAYS_Array;
    ArrayList<String> START_DATE;
    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    String TempHolder;

    String id, name, start, end;
    GridView gridView;
    List<String> li;
    ArrayAdapter<String> dataAdapter;

    public static ItemFourFragment newInstance() {
        ItemFourFragment fragment = new ItemFourFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String tempName2, tempDuration;
        final String tempName;
        final View rootview = inflater.inflate(R.layout.activity_item_four_fragment, container, false);
        txtView = (TextView) rootview.findViewById(R.id.textViewEditName);
        txtViewAddress = (TextView) rootview.findViewById(R.id.textViewEditAddress);
        txtViewTEL = (TextView) rootview.findViewById(R.id.textViewTEL);
        txtViewMAC = (TextView) rootview.findViewById(R.id.textViewMACAdress);
        txtViewServiceType = (TextView) rootview.findViewById(R.id.textViewEditServiceType);
        txtViewContractDuration = (TextView) rootview.findViewById(R.id.textViewEditContractDate);
        txtViewExpiredIn = (TextView) rootview.findViewById(R.id.textViewExpiredDays);
        txtViewMess = (TextView) rootview.findViewById(R.id.textViewMessage);
        relativeLayout1 = (RelativeLayout) rootview.findViewById(R.id.relativeLayoutEdit1);
        relativeLayout2 = (RelativeLayout) rootview.findViewById(R.id.relativeLayoutEdit2);

        Bundle bundle = getArguments();
        tempName2 = String.valueOf(bundle.getString("Address"));
        txtViewAddress.setText(tempName2);
        tempName2 = String.valueOf(bundle.getString("TEL"));
        txtViewTEL.setText(tempName2);
        tempName2 = String.valueOf(bundle.getString("MAC"));
        txtViewMAC.setText(tempName2);
        tempName2 = String.valueOf(bundle.getString("ServiceType"));
//        Toast.makeText(getActivity(), tempName2 + " 123", Toast.LENGTH_SHORT).show();
        txtViewServiceType.setText(tempName2);
        tempName = String.valueOf(bundle.getString("Name")); //Truyen ten tu man hinh truoc vao bien temp
        txtView.setText(tempName); //Set gia tri bien temp vao textViewName

        tempDuration = String.valueOf(bundle.getString("DurationStart")) + " ~ " + String.valueOf(bundle.getString("DurationEnd"));
        txtViewContractDuration.setText(tempDuration);

        mViewGroup = rootview.findViewById(R.id.viewsContainer);
        mViewGroup2 = rootview.findViewById(R.id.viewsContainer2);
        mButton = (Button) rootview.findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() { //Ham hide/show cac thong tin khach hang
            public void onClick(View view) {
                if (viewGroupIsVisible) {
                    mViewGroup.setVisibility(View.GONE);
                    mViewGroup2.setVisibility(View.GONE);
                    mButton.setText("Show");
                } else {
                    mViewGroup.setVisibility(View.VISIBLE);
                    mViewGroup2.setVisibility(View.VISIBLE);
                    mButton.setText("Hide");
                }

                viewGroupIsVisible = !viewGroupIsVisible;
            }
        });
        TextView textView1;
        textView1 = (TextView) rootview.findViewById(R.id.textView1);
        textView1.setFocusable(true);
        textView1.setEnabled(true);
        textView1.setClickable(true);
        textView1.setFocusableInTouchMode(true);
        relativeLayout1.setOnClickListener(new AdapterView.OnClickListener() {
                                               String st = tempName;

                                               @Override
                                               public void onClick(View v) {
                                                   LayoutInflater inflater = getActivity().getLayoutInflater();
                                                   View dialogView = inflater.inflate(R.layout.alert_edit_information1, null);
                                                  /* Alert Dialog Code Start*/
                                                   AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                                                   alert.setTitle("Alert Dialog With EditText"); //Set Alert dialog title here
                                                   alert.setMessage("Enter Your Name Here"); //Message here

                                                   // Set an EditText view to get user input
                                                   alert.setView(dialogView);
                                                   editTextName = (EditText) dialogView.findViewById(R.id.editTextInformationName);
                                                   editTextAddress = (EditText) dialogView.findViewById(R.id.editTextInformationAddress);
                                                   editTextPhone = (EditText) dialogView.findViewById(R.id.editTextInformationPhones);
                                                   editTextCompanyName = (EditText) dialogView.findViewById(R.id.editTextInformationCompanyName);
                                                   editTextMAC = (EditText) dialogView.findViewById(R.id.editTextInformationMAC);
                                                   editTextServiceType = (EditText) dialogView.findViewById(R.id.editTextInformationServiceType);

                                                   editTextName.setText(txtView.getText(), TextView.BufferType.EDITABLE);
                                                   editTextAddress.setText(txtViewAddress.getText(), TextView.BufferType.EDITABLE);
                                                   editTextPhone.setText(txtViewTEL.getText(), TextView.BufferType.EDITABLE);
                                                   editTextCompanyName.setText(editTextCompanyName.getText(), TextView.BufferType.EDITABLE);
                                                   editTextMAC.setText(txtViewMAC.getText(), TextView.BufferType.EDITABLE);
                                                   editTextServiceType.setText(txtViewServiceType.getText(), TextView.BufferType.EDITABLE);

                                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int whichButton) {
                                                           //You will get as string input data in this variable.
                                                           // here we convert the input to a string and show in a toast.
                            //                        String srt = input.getEditableText().toString();
                            //                        String str = ListViewClickItemArray.get(position).toString();
                                                    Toast.makeText(getContext(), "OK 1", Toast.LENGTH_LONG).show();
                            //
                            //                        OpenSQLiteDataBase();
                            //                        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                            //                        String strHolder = "UPDATE " + SQLiteHelper.TABLE2_NAME + " SET" +
                            //                                " message" +
                            //                                "='" +
                            //                                srt + "'" +
                            //                                "WHERE id='" + str + "';";
                            //                        sqLiteDatabaseObj.execSQL(strHolder);
                            //                        sqLiteDatabaseObj.close();

                                                       } // End of onClick(DialogInterface dialog, int whichButton)
                                                   }); //End of alert.setPositiveButton

                                                   alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int whichButton) {
                                                           // Canceled.
                                                           dialog.cancel();
                                                       }
                                                   }); //End of alert.setNegativeButton
                                                   final AlertDialog alertDialog = alert.create();
                                                   alertDialog.show();
                                                   alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           Boolean wantToCloseDialog = (editTextName.getText().toString().trim().isEmpty());
                                                           // if EditText is empty disable closing on possitive button
                                                           if (!wantToCloseDialog){
                                                               alertDialog.dismiss();}
                                                           Toast.makeText(getContext(), "OK 2", Toast.LENGTH_LONG).show();


                                                       }
                                                   });

       /* Alert Dialog Code End*/


                                               }

                                           }
        );

        ////////////////////
        listViewMessage = (ListView) rootview.findViewById(R.id.listViewDuration);
        arrayListID = new ArrayList<String>();
        arrayListName = new ArrayList<String>();
        arrayListDurationStart = new ArrayList<String>();
        arrayListDurationEnd = new ArrayList<String>();
        arrayListMessage = new ArrayList<String>();
        arrayListCharge = new ArrayList<String>();
        sqLiteHelper = new SQLiteHelper(getContext());

        /////Listview click on item action
        listViewMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            boolean click = true;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                 /* Alert Dialog Code Start*/
//                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                alert.setTitle("Alert Dialog With EditText"); //Set Alert dialog title here
//                alert.setMessage("Enter Your Name Here"); //Message here
//
//                // Set an EditText view to get user input
//                final EditText input = new EditText(getContext());
//                alert.setView(input);
//
//                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        //You will get as string input data in this variable.
//                        // here we convert the input to a string and show in a toast.
//                        String srt = input.getEditableText().toString();
//                        String str = ListViewClickItemArray.get(position).toString();
//                        Toast.makeText(getContext(), srt + " " + str, Toast.LENGTH_LONG).show();
//
//                        OpenSQLiteDataBase();
//                        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
//                        String strHolder = "UPDATE " + SQLiteHelper.TABLE2_NAME + " SET" +
//                                " message" +
//                                "='" +
//                                srt + "'" +
//                                "WHERE id='" + str + "';";
//                        sqLiteDatabaseObj.execSQL(strHolder);
//                        sqLiteDatabaseObj.close();
//
//                    } // End of onClick(DialogInterface dialog, int whichButton)
//                }); //End of alert.setPositiveButton
//                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // Canceled.
//                        dialog.cancel();
//                    }
//                }); //End of alert.setNegativeButton
//                AlertDialog alertDialog = alert.create();
//                alertDialog.show();
//       /* Alert Dialog Code End*/
                txtViewMess = (TextView) view.findViewById(R.id.textViewMessage);


                if (click) {
                    txtViewMess.setTextColor(Color.BLACK);
                    txtViewMess.setText("Paid");

                    Toast.makeText(getActivity(), txtViewMess.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    txtViewMess.setTextColor(Color.RED);
                    txtViewMess.setText("Unpaid");
                    Toast.makeText(getActivity(), txtViewMess.getText(), Toast.LENGTH_SHORT).show();

                }
                click = !click;
            }// End of onClick(View v)


        });

        return rootview;
    }

    private void OpenSQLiteDataBase() {
        sqLiteDatabaseObj = getActivity().openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void onResume() {

        try {
            ShowSQLiteDBdata();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    private void ShowSQLiteDBdata() throws ParseException {
        String tempName,tempID;

        Bundle bundle = getArguments();
        tempName = String.valueOf(bundle.getString("Name")); //Truyen ten tu man hinh truoc vao bien temp
        tempID = String.valueOf(bundle.getString("ID"));
        Toast.makeText(getContext(),tempID, Toast.LENGTH_LONG).show();
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT t2.id, t2.name, t2.customer_id" +
                        ", t2.duration_start, "
                        + "t2.duration_end, " +
                        "t2.message, t2.charge " +
//                        "t1.address" +
                        " FROM " + SQLiteHelper.TABLE2_NAME + " t2"
//                        + " LEFT JOIN " + SQLiteHelper.TABLE_NAME + " t1 ON t1." + SQLiteHelper.Table_Column_1_Name + " = " +
//                        "t2." + SQLiteHelper.Table2_Column_Name
                        + " WHERE " +
//                        "t2.name = '" + tempName + "' AND " +
                        "t2.customer_id = '"+ tempID + "'ORDER BY t2.id"
                , null)
        ;

        arrayListID.clear();
        arrayListName.clear();
        arrayListDurationStart.clear();
        arrayListDurationEnd.clear();
        arrayListMessage.clear();

        int i = 0;
        if (cursor.moveToFirst()) {
            do {

                arrayListID.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_ID)));

                //Inserting Column ID into Array to Use at ListView Click Listener Method.
                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_ID)));

                arrayListName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_Name)));

                arrayListDurationStart.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_DurationStart)));

                arrayListDurationEnd.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_DurationEnd)));

                arrayListMessage.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_Message)));

                arrayListCharge.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table2_Column_Charge)));
                Toast.makeText(getActivity(), arrayListDurationStart.get(i), Toast.LENGTH_SHORT).show();
                i++;
            } while (cursor.moveToNext());
        }
        i = 0;
        listAdapter = new ListAdapterDuration(getContext(),

                arrayListCharge,
                arrayListName,
                arrayListDurationStart,
                arrayListDurationEnd,
                arrayListMessage

        );
        Calendar today = Calendar.getInstance();
        Date tempDate = new Date();
        today.set(Calendar.HOUR_OF_DAY, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (int i2 = 0; i2 < arrayListDurationEnd.size(); i2++) {
            tempDate = formatter.parse(arrayListDurationEnd.get(i2));
            long diff1, diff2, diff3;
            diff1 = today.getTime().getTime();
            diff2 = tempDate.getTime();
            diff3 = (diff2 - diff1) / (1000 * 60 * 60 * 24);
//            Toast.makeText(getActivity(), String.valueOf(diff3), Toast.LENGTH_SHORT).show();

        }
        txtViewExpiredIn.setText("abc");
        listViewMessage.setAdapter(listAdapter);

        cursor.close();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


}

