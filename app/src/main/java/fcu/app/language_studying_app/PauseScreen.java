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

public class PauseScreen extends AppCompatActivity {

  private Button btnRetry;
  private Button btnCont;
  private Button btnHome;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_pause_screen);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    findIDInit();;

    View.OnClickListener btnListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (v.getId() == R.id.btn_pause_retry) {
          WorldToGameLoading.restart = true;
          startActivity(new Intent().setClass(PauseScreen.this, WorldToGameLoading.class));
          finish();
        } else if (v.getId() == R.id.btn_pause_continue) {
          finish();
        } else if (v.getId() == R.id.btn_pause_home) {
          WorldToGameLoading.returnHome = true;
          startActivity(new Intent().setClass(PauseScreen.this, WorldActivity.class));
          finish();
        }
      }
    };

    btnRetry.setOnClickListener(btnListener);
    btnCont.setOnClickListener(btnListener);
    btnHome.setOnClickListener(btnListener);
  }

  private void findIDInit() {
    btnRetry = findViewById(R.id.btn_pause_retry);
    btnCont = findViewById(R.id.btn_pause_continue);
    btnHome = findViewById(R.id.btn_pause_home);
  }
}