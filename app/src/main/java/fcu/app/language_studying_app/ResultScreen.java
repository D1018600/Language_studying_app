package fcu.app.language_studying_app;

import android.content.Intent;
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

    Bundle bundle = this.getIntent().getExtras();
    resultTime = bundle.getInt("TIME");
    resultMiss = bundle.getInt("MISS");

    findIDInit();

    tvResTime.setText(tvResTime.getText().toString() + ":  " + resultTime);
    tvResMiss.setText(tvResMiss.getText().toString() + ":  " + resultMiss);

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
          //todo
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