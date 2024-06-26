package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
  private Button btnStart;
  public static final String SHARE_PREF_NAME = "app_pref";
  public static final String STAGE_SCORE = "stage_score";
  public static final String CURRENT_STAGE = "current_stage";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    SharedPreferences sp = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();

    String score = sp.getString(STAGE_SCORE, "000000");
    int curStage = sp.getInt(CURRENT_STAGE, 0);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    btnStart = findViewById(R.id.btn_start);

    View.OnClickListener btnListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view.getId() == R.id.btn_start) {
          Intent intent = new Intent();
          intent.setClass(MainActivity.this, SelectIdActivity.class);
          startActivity(intent);
        }
      }
    };

    btnStart.setOnClickListener(btnListener);
  }
}