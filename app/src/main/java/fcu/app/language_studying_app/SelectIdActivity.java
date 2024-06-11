package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectIdActivity extends AppCompatActivity {
    private Button btnLearn;
    private Button btnDesign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLearn = findViewById(R.id.btn_learn);
        btnDesign = findViewById(R.id.btn_design);

        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (view.getId() == R.id.btn_learn) {
                    intent.setClass(SelectIdActivity.this, WorldActivity.class);
                } else if (view.getId() == R.id.btn_design) {
                    intent.setClass(SelectIdActivity.this, DesignRecordActivity.class);
                }

                startActivity(intent);
            }
        };

        btnLearn.setOnClickListener(btnListener);
        btnDesign.setOnClickListener(btnListener);
    }
}