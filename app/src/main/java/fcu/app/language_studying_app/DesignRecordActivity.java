package fcu.app.language_studying_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
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
    }

    private void getStages(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("stages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Map<String, String>> stageList = new ArrayList<>();

                for(DataSnapshot ds : snapshot.getChildren()){
                    Stage stage = ds.getValue(Stage.class);
                    Map<String, String> map = new HashMap<>();
                    map.put("Name", stage.getName());
                    map.put("Code", String.valueOf(stage.getCode()));
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