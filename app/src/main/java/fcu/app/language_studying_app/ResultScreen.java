package fcu.app.language_studying_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultScreen extends AppCompatActivity {

  private int resultTime;
  private int resultMiss;

  private TextView tvResTime;
  private TextView tvResMiss;
  private ImageView ivResScore1;
  private ImageView ivResScore2;
  private ImageView ivResScore3;
  private Button btnBackToMenu;
  private Button btnRetry;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_result_screen);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    });
    SharedPreferences sp = getSharedPreferences(MainActivity.SHARE_PREF_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    String newScore = sp.getString(MainActivity.STAGE_SCORE, "00000");

    int StageNum = sp.getInt(MainActivity.CURRENT_STAGE, 1);
    Bundle bundle = this.getIntent().getExtras();
    resultTime = bundle.getInt("TIME");
    resultMiss = bundle.getInt("MISS");


    findIDInit();

    tvResTime.setText(tvResTime.getText().toString() + ":  " + resultTime);
    tvResMiss.setText(tvResMiss.getText().toString() + ":  " + resultMiss);

    if(resultMiss < 12 && resultTime < 200)
    {
      ivResScore1.setImageResource(android.R.drawable.btn_star_big_on);
      newScore = newScore.substring(0, StageNum-1) + '1' + newScore.substring(StageNum);
    }
    if(resultMiss < 7 && resultTime < 150)
    {
      ivResScore2.setImageResource(android.R.drawable.btn_star_big_on);
      newScore = newScore.substring(0, StageNum-1) + '2' + newScore.substring(StageNum);
    }

    if(resultMiss < 3 && resultTime < 120)
    {
      ivResScore3.setImageResource(android.R.drawable.btn_star_big_on);
      newScore = newScore.substring(0, StageNum-1) + '3' + newScore.substring(StageNum);
    }
    editor.putString(MainActivity.STAGE_SCORE, newScore);
    editor.commit();



    //onClickListener to back to menu and retry
    View.OnClickListener btnListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //back to menu
        if (view.getId() == R.id.btn_result_return) {
          WorldToGameLoading.returnHome = true;
          startActivity(new Intent().setClass(ResultScreen.this, WorldActivity.class));
          finish();
        } else if (view.getId() == R.id.btn_result_restart) { //retry
          //redirect screen back to the played stage
          WorldToGameLoading.restart = true;
          startActivity(new Intent().setClass(ResultScreen.this, WorldToGameLoading.class));
          finish();
        }
      }
    };

    btnBackToMenu.setOnClickListener(btnListener);
    btnRetry.setOnClickListener(btnListener);
  }

  private void findIDInit() {
    tvResTime = findViewById(R.id.tv_result_time);
    tvResMiss = findViewById(R.id.tv_result_miss);
    ivResScore1 = findViewById(R.id.iv_result_score1);
    ivResScore2 = findViewById(R.id.iv_result_score2);
    ivResScore3 = findViewById(R.id.iv_result_score3);
    btnBackToMenu = findViewById(R.id.btn_result_return);
    btnRetry = findViewById(R.id.btn_result_restart);
  }
}