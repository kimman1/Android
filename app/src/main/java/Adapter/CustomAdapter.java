package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.KimManUserManager.Model.User;
import com.example.KimManUserManager.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;
    private ArrayList<User> arrUser;
    public CustomAdapter(Context context, int resource , ArrayList<User> objects)
    {
        super(context,resource,objects);
        this.context = context;
        this.resource= resource;
        this.arrUser = objects;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView  = LayoutInflater.from(context).inflate(this.resource,parent,false);
        TextView tv_Id = convertView.findViewById(R.id.tv_Id);
        TextView tv_User = convertView.findViewById(R.id.tv_User);
        TextView tv_Address = convertView.findViewById(R.id.tv_Address);
        TextView tv_Phone = convertView.findViewById(R.id.tv_Phone);
        User user = arrUser.get(position);
        tv_Id.setText("ID: "+user.getmId());
        tv_User.setText("User: "+user.getmUsername());
        tv_Address.setText("Address: "+user.getmAddress());
        tv_Phone.setText("Phone: "+user.getmPhone());
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You choose user has ID: " + arrUser.get(position).getmId(), Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }
}
