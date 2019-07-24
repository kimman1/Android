package com.example.KimManUserManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Activity_ItemChange extends AppCompatActivity {
    private EditText editID;
    private EditText editUsername;
    private EditText editAddress;
    private EditText editPhone;
    private Button btnCancle;
    private Button btnAdd;
    private Button btnEditItem;
    private Button btnDeleteItem;
    private EditText txtDialUserName;
    private EditText txtDialAddress;
    private EditText txtDialPhone;
    int idintent;
    String usernameintent;
    String addressintent;
    String phoneintent;
    String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12289053?useUnicode=true&characterEncoding=utf-8";
    String username = "sql12289053";
    String password="RIn2XtkGUX";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_user_change);
        editID = findViewById(R.id.editID);
        editUsername = findViewById(R.id.editUserName);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        btnCancle = findViewById(R.id.btnCancleItem);
        btnAdd = findViewById(R.id.btnAddItem);
        btnEditItem = findViewById(R.id.btnEditItem);
        btnDeleteItem = findViewById(R.id.btnDeleteItem);
        Intent intent = getIntent();
        idintent = intent.getIntExtra("idIntent",0);
        usernameintent = intent.getStringExtra("usernameIntent");
         addressintent = intent.getStringExtra("addressIntent");
         phoneintent = intent.getStringExtra("phoneIntent");
        editID.setText(String.valueOf(idintent));
        editUsername.setText(usernameintent);
        editAddress.setText(addressintent);
        editPhone.setText(phoneintent);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerDialog();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Activity_ItemChange.this, Activity_reloadListView.class);
                Activity_ItemChange.this.startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                finish();
            }
        });
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showwaningDeletealertdialog();

            }
        });
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                editID = findViewById(R.id.editID);
                editUsername = findViewById(R.id.editUserName);
                editAddress = findViewById(R.id.editAddress);
                editPhone = findViewById(R.id.editPhone);
                if(usernameintent.equals(editUsername.getText().toString()) && addressintent.equals(editAddress.getText().toString()) && phoneintent.equals(editPhone.getText().toString()))
                {
                   Toast.makeText(Activity_ItemChange.this,"Nothing to edit", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    editDatabase(editUsername.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString(), idintent);
                    Intent intent1 = new Intent(Activity_ItemChange.this, Activity_reloadListView.class);
                    Activity_ItemChange.this.startActivity(intent1);
                    finish();
                }
            }
        });

    }

    public void showAlerDialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_user,null);
        txtDialUserName = mView.findViewById(R.id.txtDialUserName);
        txtDialAddress = mView.findViewById(R.id.txtDialAddress);
        txtDialPhone = mView.findViewById(R.id.txtDialPhone);
        btnAdd = mView.findViewById(R.id.btnDialAdd);
        btnCancle = mView.findViewById(R.id.btnDialCancle);

        mBuilder.setView(mView);
         AlertDialog dialog = mBuilder.create();
        dialog.show();
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    if(txtDialUserName.getText().toString().trim().equalsIgnoreCase(""))
                    {
                        txtDialUserName.setError("Please type Username");
                    }
                    else if(txtDialAddress.getText().toString().trim().equalsIgnoreCase(""))
                    {
                        txtDialAddress.setError("Please type Address");
                    }
                    else if(txtDialPhone.getText().toString().trim().equalsIgnoreCase(""))
                    {
                        txtDialPhone.setError("Please type Phone Number");
                    }
                    else {
                        showwarningAddalertdialog();
                        dialog.dismiss();
                    }
                }
            });
        btnCancle.setOnClickListener(e->{
            dialog.dismiss();

        });


    }
    public void editDatabase(String usernamedb, String addressddb, String phonedb,int id)
    {

        String sql = "update user set username = ?, address = ?, phone = ? where iduser = ?";
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,usernamedb);
            pst.setString(2,addressddb);
            pst.setString(3,phonedb);
            pst.setInt(4,id);
            pst.executeUpdate();
        }
        catch (IllegalAccessException | SQLException | InstantiationException |ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Edit User Failed. Please contact Kim Man !",Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteDatabase(int id)
    {

        String sql = "delete from user where iduser = ?";
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate();
            Toast.makeText(this,"Delete User Successfully",Toast.LENGTH_SHORT).show();
        }
        catch (IllegalAccessException | SQLException | InstantiationException |ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Delete User Failed, Contact to Kim Man !",Toast.LENGTH_SHORT).show();
        }
    }
    public void addDatabase()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            String sql = "insert into user (username, address, phone) VALUES (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,txtDialUserName.getText().toString());
            pst.setString(2,txtDialAddress.getText().toString());
            pst.setString(3,txtDialPhone.getText().toString());
            pst.executeUpdate();
            Toast.makeText(this,"Add User Successfully !",Toast.LENGTH_SHORT).show();
            con.close();
        } catch (IllegalAccessException | SQLException | InstantiationException |ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Add User Failed, check your Internet Connection !",Toast.LENGTH_SHORT).show();
        }
    }
    public void showwarningAddalertdialog()
    {
       AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

       alertDialog.setTitle("Warning");
       alertDialog.setMessage("Do you want to add this user to Database?");
       alertDialog.setCancelable(false);
       alertDialog.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               Toast.makeText(Activity_ItemChange.this,"Cancled",Toast.LENGTH_SHORT).show();
               dialog.dismiss();
           }
       });
       alertDialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               addDatabase();
               dialog.dismiss();
               Intent intent1 = new Intent(Activity_ItemChange.this, Activity_reloadListView.class);
               Activity_ItemChange.this.startActivity(intent1);
               finish();
           }
       });
       AlertDialog alertdialog = alertDialog.create();
       alertdialog.show();
    }
    public void showwaningDeletealertdialog()
    {
       AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you want to delete this user to Database?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Activity_ItemChange.this,"Cancled",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDatabase(idintent);
                dialog.dismiss();
                Intent intent1 = new Intent(Activity_ItemChange.this, Activity_reloadListView.class);
                Activity_ItemChange.this.startActivity(intent1);
                finish();
            }
        });
        AlertDialog alertdialog = alertDialog.create();
        alertdialog.show();
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameMain, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void test(){
        List<String> lstResult = new ArrayList<>();
        lstResult.add("1");
        lstResult.add("2");
        lstResult.add("3");

        for(String s : lstResult){
            System.out.println(s);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // java 9
            lstResult.forEach(s -> {
                System.out.println(s);
            });
        }
    }
}
