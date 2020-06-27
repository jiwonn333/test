package com.roopre.simpleboard.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.service.autofill.LuhnChecksumValidator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.roopre.simpleboard.Activity.MainActivity;
import com.roopre.simpleboard.Public.Se_Application;
import com.roopre.simpleboard.R;

import java.io.DataOutputStream;
import java.io.IOException;

import androidx.fragment.app.Fragment;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    View rootView;

    EditText userid_et, passwd_et;
    CheckBox auto_chk;
    Button login_btn, join_btn;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Se_Application.Localdb.get_dataB("auto_chk")){
            auto_chk.setChecked(true);
        }else
        {
            auto_chk.setChecked(false);
        }

        userid_et.setText(Se_Application.Localdb.get_dataS("userid"));
        passwd_et.setText(Se_Application.Localdb.get_dataS("passwd"));

        if(Se_Application.Localdb.get_dataB("auto_chk") && Se_Application.Localdb.get_dataB("login")){
            login_btn.performClick();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);;

        userid_et = rootView.findViewById(R.id.userid_et);
        passwd_et = rootView.findViewById(R.id.passwd_et);

        auto_chk = rootView.findViewById(R.id.auto_chk);
        auto_chk.setOnCheckedChangeListener(this);

        login_btn= rootView.findViewById(R.id.login_btn);
        join_btn = rootView.findViewById(R.id.join_btn);

        login_btn.setOnClickListener(this);
        join_btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.auto_chk:
                if(isChecked){
                    Se_Application.Localdb.set_dataB("auto_chk", true);
                }else
                {
                    Se_Application.Localdb.set_dataB("auto_chk", false);
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_btn:
                if(CheckValid()){
                    // 입력이 정상적이면 로그인 확인하고 MainFragment 로 이동
                    new LoginTask().execute();
                }
                break;
            case R.id.join_btn:
                JoinFragment joinFragment = new JoinFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.mainFragment, joinFragment, "JOIN")
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    private boolean CheckValid(){
        if(userid_et.getText().toString().trim().length() < 5){
            Toast.makeText(getActivity(), "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if(passwd_et.getText().toString().trim().length() < 4){
            Toast.makeText(getActivity(), "비밀번호를 4자 이상 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }



    class LoginTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            String result = null;

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host(Se_Application.Server_URL)
                    .addPathSegment("user_login.php")
                    .addQueryParameter("userid", userid_et.getText().toString().trim())
                    .addQueryParameter("passwd", passwd_et.getText().toString().trim())
                    .build();
            try {
                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();
                Response response = client.newCall(request).execute();
                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, s);
            if(s.contains("success")){
                // 이동할 Fragment 선언
                Se_Application.Localdb.set_dataS("userid", userid_et.getText().toString().trim());
                Se_Application.Localdb.set_dataS("passwd", passwd_et.getText().toString().trim());
                Se_Application.Localdb.set_dataB("login", true);

                MainFragment mainFragment = new MainFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.mainFragment, mainFragment, "MAIN")
                        .commit();
            }
        }
    }
}
