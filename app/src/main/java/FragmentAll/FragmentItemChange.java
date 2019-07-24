package FragmentAll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.KimManUserManager.R;

public class FragmentItemChange extends Fragment {
    private EditText editID;
    private EditText editUserName;
    private EditText editAdress;
    private EditText editPhone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.item_user_change,container,false);
        editID = view.findViewById(R.id.editID);
        editUserName = view.findViewById(R.id.editUserName);
        editAdress = view.findViewById(R.id.editAddress);
        editPhone = view.findViewById(R.id.editPhone);
       /* int idbundle = getArguments().getInt("idbundle");
        String usernamebundle = getArguments().getString("usernamebundle");
        String addressbundle = getArguments().getString("addressbundle");
        String phonebundle = getArguments().getString("phonebundle");
        editID.setText(idbundle);
        editUserName.setText(usernamebundle);
        editAdress.setText(addressbundle);
        editPhone.setText(phonebundle);*/
        //editUserName.setText(intent.getStringExtra(FragmentLoadView.usernameItent));
        return view;
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameMain, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
