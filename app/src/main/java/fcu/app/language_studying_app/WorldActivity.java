package fcu.app.language_studying_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldActivity extends AppCompatActivity {

  private String stage;
  private String[] stages = {"stage1.csv", "stage2.csv", "stage3.csv", "stage4.csv", "stage5.csv"};

  private Button btnJoinRoom;
  private Button btnBack;
  private ImageView ivEp1;
  private ImageView ivEp2;
  private ImageView ivEp3;
  private ImageView ivEp4;
  private ImageView ivEp5;
  private RatingBar rbEp1;
  private RatingBar rbEp2;
  private RatingBar rbEp3;
  private RatingBar rbEp4;
  private RatingBar rbEp5;
  private Bundle bundle;
  private EditText etRoomCode;
  private String chQuestion;
  private String enQuestion;
  private String answer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_world);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    SharedPreferences sp = getSharedPreferences(MainActivity.SHARE_PREF_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    String score = sp.getString(MainActivity.STAGE_SCORE, "000000");

    findIDInit();
    rbEp1.setRating(score.charAt(0) - '0');
    rbEp2.setRating(score.charAt(1) - '0');
    rbEp3.setRating(score.charAt(2) - '0');
    rbEp4.setRating(score.charAt(3) - '0');
    rbEp5.setRating(score.charAt(4) - '0');
    View.OnClickListener stageListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (v.getId() == R.id.iv_ep1) {
          stage = stages[0];
          editor.putInt(MainActivity.CURRENT_STAGE, 1);
        } else if (v.getId() == R.id.iv_ep2) {
          stage = stages[1];
          editor.putInt(MainActivity.CURRENT_STAGE, 2);
        } else if (v.getId() == R.id.iv_ep3) {
          stage = stages[2];
          editor.putInt(MainActivity.CURRENT_STAGE, 3);
        } else if (v.getId() == R.id.iv_ep4) {
          stage = stages[3];
          editor.putInt(MainActivity.CURRENT_STAGE, 4);
        } else if (v.getId() == R.id.iv_ep5) {
          stage = stages[4];
          editor.putInt(MainActivity.CURRENT_STAGE, 5);
        }
        editor.commit();
        bundle = new Bundle();
        bundle.putInt("MODE", 0);
        bundle.putString("STAGE", stage);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        intent.setClass(WorldActivity.this, WorldToGameLoading.class);
        startActivity(intent);

        finish();
      }
    };

    ivEp1.setOnClickListener(stageListener);
    ivEp2.setOnClickListener(stageListener);
    ivEp3.setOnClickListener(stageListener);
    ivEp4.setOnClickListener(stageListener);
    ivEp5.setOnClickListener(stageListener);

    View.OnClickListener backListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
          Intent intent = new Intent();
          intent.setClass(WorldActivity.this, SelectIdActivity.class);
          startActivity(intent);
        }
      }
    };
    btnBack.setOnClickListener(backListener);


    btnJoinRoom.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        joinRoom();
      }
    });
  }

  private void findIDInit() {
    btnJoinRoom = findViewById(R.id.btn_join_room);
    btnBack = findViewById(R.id.btn_back);
    ivEp1 = findViewById(R.id.iv_ep1);
    ivEp2 = findViewById(R.id.iv_ep2);
    ivEp3 = findViewById(R.id.iv_ep3);
    ivEp4 = findViewById(R.id.iv_ep4);
    ivEp5 = findViewById(R.id.iv_ep5);
    rbEp1 = findViewById(R.id.rb_ep1);
    rbEp2 = findViewById(R.id.rb_ep2);
    rbEp3 = findViewById(R.id.rb_ep3);
    rbEp4 = findViewById(R.id.rb_ep4);
    rbEp5 = findViewById(R.id.rb_ep5);
    etRoomCode = findViewById(R.id.et_room_code);
  }

  private void joinRoom() {
    Dialog dialog = new Dialog(WorldActivity.this);
    View view = getLayoutInflater().inflate(R.layout.dialog_input_room_code , null);
    dialog.setContentView(view);

    Window window = dialog.getWindow();
    if (window != null) {
      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
      layoutParams.copyFrom(window.getAttributes());
      layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.5);
      window.setAttributes(layoutParams);
    }

    Button btnJoin = view.findViewById(R.id.btn_join);
    Button btnCancel = view.findViewById(R.id.btn_cancel);
    EditText etRoomCode = view.findViewById(R.id.et_room_code);

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });

    btnJoin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(v.getId() == R.id.btn_join) {
          String inputCode = etRoomCode.getText().toString();
          if (TextUtils.isEmpty(inputCode)) {
            Toast.makeText(WorldActivity.this, "enter a room code", Toast.LENGTH_SHORT).show();
            return;
          }
          FirebaseDatabase database = FirebaseDatabase.getInstance();
          DatabaseReference reference = database.getReference("stages");
          Query query = reference.orderByChild("code").equalTo(Integer.parseInt(inputCode));
          query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                  Stage stage = ds.getValue(Stage.class);
                  // get data from firebase
                  if (stage != null) {
                    String stringBuilder = stage.getWord1() + " " +
                            stage.getWord2() + " " +
                            stage.getWord3() + " " +
                            stage.getWord4() + " " +
                            stage.getWord5() + " " +
                            stage.getWord6() + " " +
                            stage.getWord7() + " " +
                            stage.getWord8();

                    String name = stage.getName();
                    String chinese = stage.getChinese();
                    String sentence = stage.getSentence();
                    String csvString = "1" + "q1," + sentence + "," + chinese + "a1," + stringBuilder;

                    Bundle bundle = new Bundle();
                    bundle.putInt("MODE", 1);
                    bundle.putString("chQuestion", stage.getChinese());
                    bundle.putString("enQuestion", stage.getSentence());
                    bundle.putString("answer", stringBuilder);

                    Intent intentToGame = new Intent();
                    intentToGame.putExtras(bundle);

                    intentToGame.setClass(WorldActivity.this, WorldToGameLoading.class);
                    startActivity(intentToGame);
                  }
                }
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(WorldActivity.this, "Wrong code, please retry again.", Toast.LENGTH_SHORT).show();
            }
          });
        }
      }
    });

    dialog.show();
  }
}