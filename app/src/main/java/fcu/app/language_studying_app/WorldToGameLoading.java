package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class WorldToGameLoading extends AppCompatActivity {

  private String stage;
  private int mode = 0;

  private ArrayList<String> chQuestion = new ArrayList<String>();
  private ArrayList<String> enQuestion = new ArrayList<String>();
  private ArrayList<String> answer = new ArrayList<String>();
  private int numOfStage;
  public static int curStage = 0;
  public static int time = 1;
  public static int miss = 0;

  public static boolean restart = false;
  public static boolean returnHome = false;

  private TextView tvT;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_world_to_game_loading);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    Bundle bundle = this.getIntent().getExtras();
    if (bundle != null) {
      mode = bundle.getInt("MODE");
    }

    if (mode == 0) {
      stage = bundle.getString("STAGE");

      try {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getAssets().open(stage)));
        int i = 0;
        String[] line = reader.readNext();
        numOfStage = Integer.parseInt(line[0]);

        while ((line = reader.readNext()) != null) {
          if (line[0].contains("q")) {
            enQuestion.add(line[1]);
            chQuestion.add(line[2]);
          } else if (line[0].contains("a")) {
            answer.add(line[1]);
          }
          i++;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (mode == 1) {
      numOfStage = 1;
      chQuestion.add(bundle.getString("chQuestion"));
      enQuestion.add(bundle.getString("enQuestion"));
      answer.add(bundle.getString("answer"));
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (restart) {
      time = 0;
      miss = 0;
      curStage = 0;
      restart = false;
    }

    if (returnHome) {
      time = 0;
      miss = 0;
      curStage = 0;
      returnHome = false;
      startActivity(new Intent().setClass(WorldToGameLoading.this, WorldActivity.class));
      //onDestroy();

    } else if (curStage == numOfStage) {
      Bundle bundle = new Bundle();
      bundle.putInt("TIME", time);
      bundle.putInt("MISS", miss);

      Intent intentToResult = new Intent();
      intentToResult.putExtras(bundle);

      intentToResult.setClass(WorldToGameLoading.this, ResultScreen.class);
      startActivity(intentToResult);

    } else {
      Bundle bundle = new Bundle();
      bundle.putString("chQuestion", chQuestion.get(curStage));
      bundle.putString("enQuestion", enQuestion.get(curStage));
      bundle.putString("answer", answer.get(curStage));

      Intent intentToGame = new Intent();
      intentToGame.putExtras(bundle);

      intentToGame.setClass(WorldToGameLoading.this, GameStage.class);
      startActivity(intentToGame);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (returnHome) {
      onDestroy();
    }
  }
}