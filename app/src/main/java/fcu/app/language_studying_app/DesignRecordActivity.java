package fcu.app.language_studying_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DesignRecordActivity extends AppCompatActivity {
    private Button btnReturn;
    private Button btnAddStage;
    private ListView lvStageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_design_record);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnReturn = findViewById(R.id.btn_return_design_record);
        btnAddStage = findViewById(R.id.btn_add_stage);
        lvStageContainer = findViewById(R.id.lv_stage_container);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(view.getId() == R.id.btn_return_design_record){
                    intent.setClass(DesignRecordActivity.this, SelectIdActivity.class);
                }
                if(view.getId() == R.id.btn_add_stage){
                    intent.setClass(DesignRecordActivity.this, DesignStageActivity.class);

                }
                DesignRecordActivity.this.startActivity(intent);
            }
        };
        getStages();
        btnReturn.setOnClickListener(listener);
        btnAddStage.setOnClickListener(listener);

        lvStageContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
                String identifier = item.get("ID"); // Get the identifier
                String name = item.get("Name");
                String code = item.get("Code");
                String sentence = item.get("sentence");
                String word1 = item.get("word1");
                String word2 = item.get("word2");
                String word3 = item.get("word3");
                String word4 = item.get("word4");
                String word5 = item.get("word5");
                String word6 = item.get("word6");

                Intent intent = new Intent(DesignRecordActivity.this, EditStageActivity.class);
                intent.putExtra("id", identifier); // Pass the identifier
                intent.putExtra("name", name);
                intent.putExtra("code", code);
                intent.putExtra("sentence", sentence);
                intent.putExtra("word1", word1);
                intent.putExtra("word2", word2);
                intent.putExtra("word3", word3);
                intent.putExtra("word4", word4);
                intent.putExtra("word5", word5);
                intent.putExtra("word6", word6);

                startActivity(intent);
            }
        });


    }

    private void getStages(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("stages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Map<String, String>> stageList = new ArrayList<>();

                for(DataSnapshot ds : snapshot.getChildren()){
                    String id = ds.getKey();
                    Stage stage = ds.getValue(Stage.class);

                    Map<String, String> map = new HashMap<>();
                    map.put("ID", id);
                    map.put("Name", stage.getName());
                    map.put("Code", String.valueOf(stage.getCode()));
                    map.put("sentence", stage.getSentence());
                    map.put("word1", stage.getWord1());
                    map.put("word2", stage.getWord2());
                    map.put("word3", stage.getWord3());
                    map.put("word4", stage.getWord4());
                    map.put("word5", stage.getWord5());
                    map.put("word6", stage.getWord6());
                    stageList.add(map);
                }

                int[] format = {android.R.id.text1, android.R.id.text2};
                String keys[] =  {"Name", "Code"};
                SimpleAdapter adapter = new SimpleAdapter(DesignRecordActivity.this, stageList, android.R.layout.simple_list_item_2, keys, format);
                lvStageContainer.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DesignRecordActivity.this, "data fetch failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}