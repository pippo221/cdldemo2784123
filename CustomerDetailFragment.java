package com.example.cuongducnguyenkp.cdldemo2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomerDetailFragment extends Fragment {
    TextView txtView;
    TextView txtViewAddress;
    TextView txtViewTEL;
    TextView txtViewServiceType;
    TextView txtViewMAC;
    TextView txtViewContractDuration;
    TextView txtViewExpiredIn;
    TextView txtViewMess;
    TextView txtStatus;
    EditText editTextName;
    EditText editTextAddress;
    EditText editTextPhone;
    EditText editTextCompanyName;
    EditText editTextMAC;
    EditText editTextServiceType;
    View mViewGroup;
    View mViewGroup2;
    Button mButton;
    private boolean viewGroupIsVisible = true;

    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
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
    ArrayList<String> ListViewClickItemArray;

    {
        ListViewClickItemArray = new ArrayList<String>();
    }

    String tempID;
    String id;

    public static CustomerDetailFragment newInstance() {
        CustomerDetailFragment fragment = new CustomerDetailFragment();
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
        final View rootview = inflater.inflate(R.layout.activity_customer_detail_fragment, container, false);
        txtView = (TextView) rootview.findViewById(R.id.textViewEditName);
        txtViewAddress = (TextView) rootview.findViewById(R.id.textViewEditAddress);
        txtViewTEL = (TextView) rootview.findViewById(R.id.textViewTEL);
        txtViewMAC = (TextView) rootview.findViewById(R.id.textViewMACAdress);
        txtViewServiceType = (TextView) rootview.findViewById(R.id.textViewEditServiceType);
        txtViewContractDuration = (TextView) rootview.findViewById(R.id.textViewEditContractDate);
        txtViewExpiredIn = (TextView) rootview.findViewById(R.id.textViewExpiredDays);
        txtViewMess = (TextView) rootview.findViewById(R.id.textViewMessage);
        txtStatus = (TextView) rootview.findViewById(R.id.textViewEditStatus);
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
        txtViewServiceType.setText(tempName2);
        tempName = String.valueOf(bundle.getString("Name")); //Truyen ten tu man hinh truoc vao bien temp
        txtView.setText(tempName); //Set gia tri bien temp vao textViewName
        tempID = String.valueOf(bundle.getString("ID"));

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
                                                   final View dialogView = inflater.inflate(R.layout.alert_edit_information1, null);
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
                                                           String str1 = editTextName.getEditableText().toString();
                                                           String str2 = editTextAddress.getEditableText().toString();
                                                           String str3 = editTextPhone.getEditableText().toString();
                                                           String str4 = editTextMAC.getEditableText().toString();
                                                           String str5 = editTextServiceType.getEditableText().toString();

                                                           OpenSQLiteDataBase();
                                                           sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                                                           String strHolder = "UPDATE " + SQLiteHelper.TABLE_NAME + "  SET" +
                                                                   " name='" + str1 + "'," +
                                                                   " address='" + str2 + "'," +
                                                                   " phone_number='" + str3 + "'," +
                                                                   " MAC_address='" + str4 + "'," +
                                                                   " service_type='" + str5 + "'" +
                                                                   " WHERE id='" + tempID + "'";
                                                                   String strHolder2 = "UPDATE "+ SQLiteHelper.TABLE2_NAME + " SET" +
                                                                   " name='" + str1 + "'" +" where customer_id = '" + tempID + "'";
                                                                   ;
                                                           sqLiteDatabaseObj.execSQL(strHolder);
                                                           sqLiteDatabaseObj.execSQL(strHolder2);
                                                           sqLiteDatabaseObj.close();
                                                           Toast.makeText(getContext(), "Update customer information successfully", Toast.LENGTH_LONG).show();
                                                       } // End of onClick(DialogInterface dialog, int whichButton)
                                                   }); //End of alert.setPositiveButton

                                                   alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int whichButton) {
                                                           // Canceled.
                                                           dialog.cancel();
                                                       }
                                                   }); //End of alert.setNegativeButton
                                                   final int editTextLength = editTextName.getText().toString().trim().length();
                                                   final AlertDialog alertDialog = alert.create();
                                                   editTextName.addTextChangedListener(new TextWatcher() {
                                                       private void handleText() {
                                                           final Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                                           if (editTextName.getText().length() == 0) {
                                                               okButton.setEnabled(false);
                                                           } else {
                                                               okButton.setEnabled(true);
                                                           }
                                                       }

                                                       @Override
                                                       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                       }

                                                       @Override
                                                       public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                       }

                                                       @Override
                                                       public void afterTextChanged(Editable s) {
                                                           handleText();
                                                       }
                                                   });
                                                   alertDialog.show();
//                                                   alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
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
        txtViewMess = (TextView) rootview.findViewById(R.id.textViewMessage);

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
//                txtViewMess = (TextView) view.findViewById(R.id.textViewMessage);
//
//                if (click) {
//                    txtViewMess.setTextColor(Color.BLACK);
//                    txtViewMess.setText("Paid");
//                    Toast.makeText(getActivity(), String.valueOf(position) +" position turned to Black", Toast.LENGTH_SHORT).show();
//                    click = !click;
//                } else {
//                    txtViewMess.setTextColor(Color.RED);
//                    txtViewMess.setText("Unpaid");
//                    Toast.makeText(getActivity(), String.valueOf(position)+" position turned to Red", Toast.LENGTH_SHORT).show();
//                    click = !click;
//                }
                String str = ListViewClickItemArray.get(position).toString();
                OpenSQLiteDataBase();
                sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                String strHolder = "UPDATE " + SQLiteHelper.TABLE2_NAME + " SET" +
                        " message " +
                        "=' PAID '" +
                        "WHERE id='" + str + "';";

                sqLiteDatabaseObj.execSQL(strHolder);
                sqLiteDatabaseObj.close();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(CustomerDetailFragment.this).attach(CustomerDetailFragment.this).commit();
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
        String tempName;

        Bundle bundle = getArguments();
        tempName = String.valueOf(bundle.getString("Name")); //Truyen ten tu man hinh truoc vao bien temp

//        Toast.makeText(getContext(), tempID, Toast.LENGTH_LONG).show();
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
                        "t2.customer_id = '" + tempID + "'ORDER BY t2.id"
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
//                Toast.makeText(getActivity(), arrayListDurationStart.get(i), Toast.LENGTH_SHORT).show();
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
        ArrayList<Long> tempMinus = new ArrayList<>(), tempPlus = new ArrayList<>();
        ArrayList<String> tempMinusDate = new ArrayList<>(), tempPlusDate = new ArrayList<>();
        for (int i2 = 0; i2 < arrayListDurationEnd.size(); i2++) {
            tempDate = formatter.parse(arrayListDurationEnd.get(i2));
            long diff1, diff2, diff3;
            diff1 = today.getTime().getTime();
            diff2 = tempDate.getTime();
            diff3 = ((diff2 - diff1) / (1000 * 60 * 60 * 24)) + 1;
//            Toast.makeText(getActivity(), String.valueOf(diff3), Toast.LENGTH_SHORT).show();
            if (diff3 < 0) {
                tempMinus.add(diff3);
                tempMinusDate.add(arrayListDurationEnd.get(i2));
//                Toast.makeText(getActivity(), "Add to Minus value " + String.valueOf(diff3), Toast.LENGTH_SHORT).show();
            } else {
                tempPlus.add(diff3);
//                Toast.makeText(getActivity(), "Add to Plus value " + String.valueOf(diff3), Toast.LENGTH_SHORT).show();
                tempPlusDate.add(arrayListDurationEnd.get(i2));
            }

        }
        long tempExpiredInValue = 0;
        String tempExpiredInDate = "";
        int i4 = 0;
        if (tempPlus.isEmpty() == false) {
            do {
                if (tempPlus.get(i4) < 30) {
                    tempExpiredInValue = tempPlus.get(i4);
                    tempExpiredInDate = tempPlusDate.get(i4);
                }
                i4++;
            } while (i4 < tempPlus.size());
        }
        Calendar c = new GregorianCalendar();
        String temp = "";
        Date dateLastDate = new Date();
        Date todayFormatted = new Date();

        String temp5 = arrayListDurationEnd.get(arrayListDurationEnd.size() - 1);
//        Toast.makeText(getActivity(), temp5 + " " + String.valueOf(today), Toast.LENGTH_SHORT).show();
        dateLastDate = formatter.parse(temp5);
        today.set(Calendar.HOUR_OF_DAY, 0);
        todayFormatted = today.getTime();
        if (todayFormatted.before(dateLastDate) == true) {
            txtStatus.setText("On Going");
        } else {
            txtStatus.setText("Finished");
        }
        txtViewExpiredIn.setText(String.valueOf(tempExpiredInValue) + " days on " + tempExpiredInDate);
        listViewMessage.setAdapter(listAdapter);

        cursor.close();
    }

}

