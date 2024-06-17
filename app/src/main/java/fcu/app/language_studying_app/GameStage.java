package fcu.app.language_studying_app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;

public class GameStage extends AppCompatActivity {

  private Handler h = new Handler();
  private Runnable r;

  private String chQuestion;
  private String[] enQuestion;
  private String[] ansSen;
  private int cnt = 0;
  private boolean runStop = false;
  private boolean initPositionSet = true;

  private float dx, dy, initx, inity;

  private TextView tvChQ;
  private TextView tvCh1;
  private TextView tvCh2;
  private TextView tvCh3;
  private TextView tvCh4;
  private TextView tvCh5;
  private TextView tvCh6;
  private TextView tvCh7;
  private TextView tvCh8;
  private TextView tvAns1;
  private TextView tvTime;
  private Button btnNext;
  private Button btnStop;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_game_stage);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null) {
      chQuestion = bundle.getString("chQuestion");
      enQuestion = bundle.getString("enQuestion").split(" ");
      ansSen = bundle.getString("answer").split(" ");
    }

    findIDInit();
    stageInit();

    moveChoice(tvCh1, tvAns1);
    moveChoice(tvCh2, tvAns1);
    moveChoice(tvCh3, tvAns1);
    moveChoice(tvCh4, tvAns1);
    moveChoice(tvCh5, tvAns1);
    moveChoice(tvCh6, tvAns1);
    moveChoice(tvCh7, tvAns1);
    moveChoice(tvCh8, tvAns1);

    btnNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        WorldToGameLoading.curStage++;
        finish();
      }
    });

    btnStop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(GameStage.this, PauseScreen.class);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (WorldToGameLoading.returnHome) {
      finish();
      //startActivity(new Intent().setClass(GameStage.this, WorldActivity.class));
      //onDestroy();

    } else if (WorldToGameLoading.restart) {
      finish();
      //startActivity(new Intent().setClass(GameStage.this, WorldToGameLoading.class));
      //onDestroy();

    } else {
      // continue after pause
      runStop = false;
      r = new Runnable() {
        @Override
        public void run() {
          if(runStop)
            return;
          tvTime.setText(String.format("%d", WorldToGameLoading.time++));
          h.postDelayed(this, 1000);
        }
      };
      h.postDelayed(r, 1000);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    runStop = true;
  }

  private void findIDInit() {
    tvCh1 = findViewById(R.id.tv_ch1);
    tvCh2 = findViewById(R.id.tv_ch2);
    tvCh3 = findViewById(R.id.tv_ch3);
    tvCh4 = findViewById(R.id.tv_ch4);
    tvCh5 = findViewById(R.id.tv_ch5);
    tvCh6 = findViewById(R.id.tv_ch6);
    tvCh7 = findViewById(R.id.tv_ch7);
    tvCh8 = findViewById(R.id.tv_ch8);
    tvAns1 = findViewById(R.id.tv_ans1);
    tvChQ = findViewById(R.id.tv_ch_q);
    tvTime = findViewById(R.id.tv_time);
    btnNext = findViewById(R.id.btn_next_stage);
    btnStop = findViewById(R.id.btn_game_stop);
  }

  private void stageInit() {
    tvCh1.setText(ansSen[0]);
    tvCh2.setText(ansSen[1]);
    tvCh3.setText(ansSen[2]);
    tvCh4.setText(ansSen[3]);
    tvCh5.setText(ansSen[4]);
    tvCh6.setText(ansSen[5]);
    tvCh7.setText(ansSen[6]);
    tvCh8.setText(ansSen[7]);
    tvChQ.setText(chQuestion);

    btnNext.setEnabled(false);
    btnNext.setAlpha(0);
  }

  @SuppressLint("ClickableViewAccessibility")
  private void moveChoice(TextView ch, TextView ans) {
    ch.setOnTouchListener(new View.OnTouchListener() {
      @SuppressLint("ClickableViewAccessibility")
      @Override
      public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            if (initPositionSet) {
              initx = v.getX();
              inity = v.getY();
              initPositionSet = false;
            }
            dx = v.getX() - event.getRawX();
            dy = v.getY() - event.getRawY();
            break;
          case MotionEvent.ACTION_MOVE:
            initPositionSet = true;
            v.animate()
                .x(event.getRawX() + dx)
                .y(event.getRawY() + dy)
                .setDuration(0)
                .start();
            break;
          case MotionEvent.ACTION_UP:

            Rect rect1 = new Rect();
            Rect rect2 = new Rect();
            v.getHitRect(rect1);
            ans.getHitRect(rect2);
            boolean isColliding = Rect.intersects(rect1, rect2);
            if (isColliding && ch.getText().toString().equals(enQuestion[cnt])) {
              ans.setText(ans.getText().toString() + "  " + ch.getText().toString());
              ch.setText(" ");
              cnt++;

              v.setX(initx);
              v.setY(inity);
              v.setAlpha(0);
              if (cnt == enQuestion.length) {
                btnNext.setEnabled(true);
                btnNext.setAlpha(1);
              }
            } else {
              WorldToGameLoading.miss++;

              v.setX(initx);
              v.setY(inity);
            }
            break;
          default:
            return false;
        }
        return true;
      }
    });
  }
}