package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DesignStageActivity extends AppCompatActivity {
    private Button btnReturn;
    private EditText etTopic;
    private Button btnAddWord;


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
        etTopic = findViewById(R.id.et_topic);
        btnAddWord = findViewById(R.id.btn_add_word);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DesignStageActivity.this, DesignRecordActivity.class);
                DesignStageActivity.this.startActivity(intent);
            }
        };
        btnReturn.setOnClickListener(listener);
        btnAddWord.setOnClickListener(listener);
    }
}