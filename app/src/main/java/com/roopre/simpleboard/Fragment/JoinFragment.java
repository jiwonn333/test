package com.roopre.simpleboard.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.roopre.simpleboard.Activity.MainActivity;
import com.roopre.simpleboard.Public.Se_Application;
import com.roopre.simpleboard.R;

import java.io.IOException;
import java.lang.reflect.Type;

import androidx.fragment.app.Fragment;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JoinFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    View rootView;

    ImageView profile_iv;
    EditText userid_et, passwd_et, repasswd_et, nickname_et;
    Button join_btn;

    public JoinFragment() {
        // Required empty public constructor
    }

    public static JoinFragment newInstance(String param1, String param2) {
        JoinFragment fragment = new JoinFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_join, container, false);;

        profile_iv = rootView.findViewById(R.id.profile_iv);

        userid_et = rootView.findViewById(R.id.userid_et);
        passwd_et = rootView.findViewById(R.id.passwd_et);
        repasswd_et = rootView.findViewById(R.id.repasswd_et);
        nickname_et = rootView.findViewById(R.id.nickname_et);

        join_btn = rootView.findViewById(R.id.join_btn);
        join_btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.join_btn:
                if(CheckValid()){
                    // 입력이 정상적이면 로그인 확인하고 MainFragment 로 이동
                    new JoinTask().execute();

                }
                break;
        }
    }

    class JoinTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            String result = null;

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host(Se_Application.Server_URL)
                    .addPathSegment("user_join.php")
                    .addQueryParameter("userid", userid_et.getText().toString().trim())
                    .addQueryParameter("passwd", passwd_et.getText().toString().trim())
                    .addQueryParameter("nickname", passwd_et.getText().toString().trim())
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
                Se_Application.Localdb.set_dataS("userid", userid_et.getText().toString().trim());
                Se_Application.Localdb.set_dataS("passwd", passwd_et.getText().toString().trim());

                ((MainActivity)getActivity()).onBackStackChanged();
            }
        }
    }

    private boolean CheckValid(){
        if(userid_et.getText().toString().trim().length() < 5){
            Toast.makeText(getActivity(), "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!Se_Application.emailValidator(userid_et.getText().toString().trim())){
            Toast.makeText(getActivity(), "이메일을 정확하게 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(passwd_et.getText().toString().trim().length() < 6){
            Toast.makeText(getActivity(), "비밀번호를 6자리 이 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!passwd_et.getText().toString().trim().equals(repasswd_et.getText().toString().trim())){
            Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(nickname_et.getText().toString().trim().length() < 1){
            Toast.makeText(getActivity(), "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}
