package com.roopre.simpleboard.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roopre.simpleboard.ChatMsgVO;
import com.roopre.simpleboard.Public.Se_Application;
import com.roopre.simpleboard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ChatMsgFragment extends Fragment implements View.OnClickListener {
    // 로그용 TAG
    private final String TAG = getClass().getSimpleName();

    // 채팅을 입력할 입력창, 전송 버튼
    EditText content_et;
    Button send_iv;

    // 채팅 내용을 뿌려줄 RecyclerView 와 Adapter
    RecyclerView recyclerView;
    ChatMsgAdapter adapter;

    // 채팅 방 이름
    String chatRoom = "";

    // 채팅 내용을 담을 배열
    List<ChatMsgVO> msgVOList = new ArrayList<>();

    // FirebaseDatabase 연결용 객체들
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public ChatMsgFragment() {
    }

    public static ChatMsgFragment newInstance(int columnCount) {
        ChatMsgFragment fragment = new ChatMsgFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_msg, container, false);

        content_et = view.findViewById(R.id.content_et);
        send_iv = view.findViewById(R.id.send_iv);

        recyclerView = view.findViewById(R.id.rv);
        send_iv.setOnClickListener(this);

        // ChatRoomFragment 에서 받는 채팅방 이름
        chatRoom = getArguments().getString("chatroom");
        adapter = new ChatMsgAdapter(msgVOList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // Firebase Database 초기
        myRef = database.getReference(chatRoom);

        // Firebase Database Listener 붙이기
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // 데이터 추가 시 자동 호출 되는 메서드
                Log.d(TAG, "onChild added");
                Log.d(TAG, "onChild = " + snapshot.getValue(ChatMsgVO.class).toString());

                // 데이터베이스의 정보를 ChatMsgVO 객체에 담음
                ChatMsgVO chatMsgVO = snapshot.getValue(ChatMsgVO.class);
                msgVOList.add(chatMsgVO);

                // 채팅 메세지 배열에 담고 RecyclerView 다시 그리기
                adapter = new ChatMsgAdapter(msgVOList);
                recyclerView.setAdapter(adapter);

                // RecyclerView 에서 보여주는 리스트의 가장 마지막을 보여주도록 스크롤 이동
                recyclerView.scrollToPosition(msgVOList.size() - 1);
                Log.d(TAG, msgVOList.size() + "");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG, "chatRoom = "+ chatRoom);
        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "view.getId() : " + view.getId());

        switch(view.getId()){
            case R.id.send_iv:
                if (content_et.getText().toString().trim().length() >= 1) {
                    Log.d(TAG, "입력 처리");

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Database에 저장할 객체 만들기
                    ChatMsgVO msgVO = new ChatMsgVO(Se_Application.Localdb.get_dataS("userid"), dateFormat.format(new Date()).toString(), content_et.getText().toString().trim());

                    // 해당 DB에 값 저장시키기
                    myRef.push().setValue(msgVO);

                    // 입력 필드 초기화
                    content_et.setText("");
                } else {
                    Toast.makeText(getActivity(), "메세지를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}