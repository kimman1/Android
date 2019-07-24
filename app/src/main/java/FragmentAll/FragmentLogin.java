package FragmentAll;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.KimManUserManager.R;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FragmentLogin extends Fragment {
   /*String url = "jdbc:mysql://192.168.1.164:3306/repair";
    String username = "test";
    String password = "test";*/
   String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12289053?useUnicode=true&characterEncoding=utf-8";
   String username = "sql12289053";
   String password="RIn2XtkGUX";
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtResult;
    private CheckBox checkBoxShowHide;
    private CheckBox checkboxRemeberPassword;
    String lbUsername,usernamers;
    String lbPassword,passwordrs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.activity_login,container,false);

        btnLogin = view.findViewById(R.id.btnLogin);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtResult = view.findViewById(R.id.txtResult);
        checkBoxShowHide = view.findViewById(R.id.checkBoxShowHide);
        checkboxRemeberPassword = view.findViewById(R.id.checkBoxPasswordRemember);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginremember",Context.MODE_PRIVATE);
        String preferenceUsername = sharedPreferences.getString("username","");
        String preferencePassword = sharedPreferences.getString("password", "");
        int prefereceRememberState = sharedPreferences.getInt("rememberState", 0);
        txtUsername.setText(preferenceUsername);
        txtPassword.setText(preferencePassword);
        if(prefereceRememberState == 0)
        {
            checkboxRemeberPassword.setChecked(false);
        }
        else
        {
            checkboxRemeberPassword.setChecked(true);
        }

        checkBoxShowHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lbUsername = txtUsername.getText().toString();
                lbPassword = txtPassword.getText().toString();
                if(lbUsername.trim().equalsIgnoreCase(""))
                {
                    txtUsername.setError("Please type Username");
                }
                else if(lbPassword.trim().equalsIgnoreCase(""))
                {
                    txtPassword.setError("Please type Password");
                }
                else {
                    ConnectMysql connectMysql = new ConnectMysql();
                    connectMysql.execute("");
                }
            }
        });
        return view;
    }


    private class ConnectMysql extends AsyncTask<String,Void,String> {
        String res="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(),"Please Wait..",Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(String... params){
            try {
                // Async -> OK
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url,username,password);
                String result = "Database connect successfull\n";

                String sql = "select * from login where username = ? and password = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,lbUsername);
                pst.setString(2,lbPassword);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    result += rs.getString(1).toString() + "\n";
                    usernamers = rs.getString(2).toString();
                    passwordrs = rs.getString(3).toString();
                }
                con.close();
                res = result;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                res = e.toString();
            }

            return  res;
        }
        protected void onPostExecute(String result) {
            // Await
            if(lbUsername.equals(usernamers) && lbPassword.equals(passwordrs))
            {
                if(checkboxRemeberPassword.isChecked())
                {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginremember",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", txtUsername.getText().toString());
                    editor.putString("password", txtPassword.getText().toString());
                    editor.putInt("rememberState", 1);
                    editor.commit();
                }
                else
                {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginremember",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                }
                Toast.makeText(getActivity(),"Login Success",Toast.LENGTH_SHORT).show();
                //getActivity().overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                loadFragment(new FragmentLoadView());
            }
            else
            {
                Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameMain, fragment).setCustomAnimations(R.anim.enter_from_right_fragment,R.anim.exit_to_left_fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
