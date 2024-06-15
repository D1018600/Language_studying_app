package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class EditStageActivity extends AppCompatActivity {
    private Button btnReturn;
    private Button btnSubmit;
    private EditText etName;
    private EditText etSentence;
    private EditText etWords[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_stage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // set id
        btnReturn = findViewById(R.id.btn_return_edit_stage);
        btnSubmit = findViewById(R.id.btn_submit_edit_stage);
        etName = findViewById(R.id.et_name_edit_stage);
        etSentence = findViewById(R.id.et_sentence_edit_stage);
        etWords = new EditText[]{
                findViewById(R.id.et_1_edit_stage),
                findViewById(R.id.et_2_edit_stage),
                findViewById(R.id.et_3_edit_stage),
                findViewById(R.id.et_4_edit_stage),
                findViewById(R.id.et_5_edit_stage),
                findViewById(R.id.et_6_edit_stage)
        };

        // fetch content from DesignRecordActivity
        final Intent intentFetch = getIntent();
        final String id = intentFetch.getStringExtra("id");
        String name = intentFetch.getStringExtra("name");
        String sentence = intentFetch.getStringExtra("sentence");
        String et1 = intentFetch.getStringExtra("word1");
        String et2 = intentFetch.getStringExtra("word2");
        String et3 = intentFetch.getStringExtra("word3");
        String et4 = intentFetch.getStringExtra("word4");
        String et5 = intentFetch.getStringExtra("word5");
        String et6 = intentFetch.getStringExtra("word6");

        // put content on editText
        etName.setText(name);
        etSentence.setText(sentence);
        etWords[0].setText(et1);
        etWords[1].setText(et2);
        etWords[2].setText(et3);
        etWords[3].setText(et4);
        etWords[4].setText(et5);
        etWords[5].setText(et6);

        // submit
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("stages");

                // store the edited content
                String newName = etName.getText().toString();
                String newSentence = etSentence.getText().toString();
                String newWord1 = etWords[0].getText().toString();
                String newWord2 = etWords[1].getText().toString();
                String newWord3 = etWords[2].getText().toString();
                String newWord4 = etWords[3].getText().toString();
                String newWord5 = etWords[4].getText().toString();
                String newWord6 = etWords[5].getText().toString();

                Map<String, Object> updates = new HashMap<>();
                updates.put("name", newName);
                updates.put("sentence", newSentence);
                updates.put("word1", newWord1);
                updates.put("word2", newWord2);
                updates.put("word3", newWord3);
                updates.put("word4", newWord4);
                updates.put("word5", newWord5);
                updates.put("word6", newWord6);

                // push updated content
                reference.child(id).updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditStageActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditStageActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intent = new Intent(EditStageActivity.this, DesignRecordActivity.class);
                startActivity(intent);
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStageActivity.this, DesignRecordActivity.class);
                startActivity(intent);
            }
        });
    }


}