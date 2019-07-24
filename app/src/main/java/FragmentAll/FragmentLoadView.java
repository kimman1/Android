package FragmentAll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.KimManUserManager.Activity_ItemChange;
import com.example.KimManUserManager.Activity_reloadListView;
import com.example.KimManUserManager.MainActivity;
import com.example.KimManUserManager.Model.User;
import com.example.KimManUserManager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Adapter.CustomAdapter;

public class FragmentLoadView  extends Fragment {
      /*  String url = "jdbc:mysql://192.168.1.164:3306/repair?useUnicode=true&characterEncoding=utf-8";
        String username = "test";
        String password = "test";*/
      String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12289053?useUnicode=true&characterEncoding=utf-8";
    String username = "sql12289053";
    String password="RIn2XtkGUX";
    RelativeLayout rLoadView;
    private ListView lvUser;
    ArrayList<User> arrayList =  new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.item_user,container,false);
        lvUser = view.findViewById(R.id.lsUser);
        rLoadView = view.findViewById(R.id.rlayoutLoadView);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connectDB();
        CustomAdapter customAdapter = new CustomAdapter(getActivity(),R.layout.row_item_user,arrayList);
        lvUser.setAdapter(customAdapter);
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertSignOutDialog();
                return true;
            }
        });
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(), Activity_ItemChange.class);
                intent.putExtra("idIntent", arrayList.get(position).getmId());
                intent.putExtra("usernameIntent", arrayList.get(position).getmUsername());
                intent.putExtra("addressIntent" , arrayList.get(position).getmAddress());
                intent.putExtra("phoneIntent",arrayList.get(position).getmPhone());
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                getActivity().finish();
            }
        });

        return view;
    }
    public void alertSignOutDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you want sign out?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Cancled",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent1);
                getActivity().finish();
            }
        });
        AlertDialog alertdialog = alertDialog.create();
        alertdialog.show();
    }
    public void connectDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            while (rs.next())
            {
                User userdb = new User();
                userdb.setmId(rs.getInt(1));
                userdb.setmUsername(rs.getString(2));
                userdb.setmAddress(rs.getString(3));
                userdb.setmPhone(rs.getString(4));
                arrayList.add(userdb);
            }
            Toast.makeText(getActivity(),"Load Success", Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) { ;
            Log.i("IllegalAccessException", e.toString());
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            Toast.makeText(getActivity(),"Load Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}
