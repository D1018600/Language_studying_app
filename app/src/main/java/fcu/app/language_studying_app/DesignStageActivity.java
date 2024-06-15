package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ThreadLocalRandom;

public class DesignStageActivity extends AppCompatActivity {
    private Button btnReturn;
    private EditText etSentence;
    private Button btnPublish;
    private EditText etName;
    private EditText[] etWords;
    private int wordCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_design_stage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnReturn = findViewById(R.id.btn_return_design_stage);
        etSentence = findViewById(R.id.et_sentence);
        btnPublish = findViewById(R.id.btn_publish);
        etName = findViewById(R.id.et_stage_name);
        etWords = new EditText[]{
                findViewById(R.id.et_1),
                findViewById(R.id.et_2),
                findViewById(R.id.et_3),
                findViewById(R.id.et_4),
                findViewById(R.id.et_5),
                findViewById(R.id.et_6)
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(view.getId() == R.id.btn_return_design_stage){
                    intent.setClass(DesignStageActivity.this, DesignRecordActivity.class);
                }


                if(view.getId() == R.id.btn_publish){
                    String name = etName.getText().toString();
                    String sentence = etSentence.getText().toString();
                    String word1 = etWords[0].getText().toString();
                    String word2 = etWords[1].getText().toString();
                    String word3 = etWords[2].getText().toString();
                    String word4 = etWords[3].getText().toString();
                    String word5 = etWords[4].getText().toString();
                    String word6 = etWords[5].getText().toString();
                    int code = ThreadLocalRandom.current().nextInt(1, 99999);
                    addStage(code, name, sentence, word1, word2, word3, word4, word5, word6);
                    intent.setClass(DesignStageActivity.this, DesignRecordActivity.class);
                }
                DesignStageActivity.this.startActivity(intent);
            }
        };
        btnReturn.setOnClickListener(listener);
        btnPublish.setOnClickListener(listener);
    }

    private void addStage(int code, String name, String sentence, String word1, String word2, String word3, String word4, String word5, String word6) {

        Stage stage = new Stage(code, name, sentence, word1, word2, word3, word4, word5, word6);
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("stages");
        reference.push().setValue(stage);
        Toast.makeText(this, "stage add success", Toast.LENGTH_SHORT).show();
    }
}