package fcu.app.language_studying_app;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WorldActivity extends AppCompatActivity {

  private String stage;
  private String[] stages = {"stage1.csv", "stage2.csv", "stage3.csv", "stage4.csv", "stage5.csv"} ;

  private Button btnJoinRoom;
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

    btnJoinRoom.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        joinRoom();
      }
    });
  }

  private void findIDInit() {
    btnJoinRoom = findViewById(R.id.btn_join_room);
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

    btnJoin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // correct room code
        // or not
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });

    dialog.show();
  }
}