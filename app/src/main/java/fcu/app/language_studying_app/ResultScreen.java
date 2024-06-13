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
    btnBackToMenu = findViewById(R.id.button2);
    btnRetry = findViewById(R.id.button);

    findIDInit();

    tvResTime.setText(tvResTime.getText().toString() + ":  " + resultTime);
    tvResMiss.setText(tvResMiss.getText().toString() + ":  " + resultMiss);

    //onClickListener to back to menu and retry
    View.OnClickListener btnListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        //back to menu
        if (view.getId() == R.id.button2) {
          intent.setClass(ResultScreen.this, WorldActivity.class);
        } else if (view.getId() == R.id.button) { //retry
          //todo
          //redirect screen back to the played stage
        }
        startActivity(intent);
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
  }
}