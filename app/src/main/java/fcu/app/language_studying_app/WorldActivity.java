package fcu.app.language_studying_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WorldActivity extends AppCompatActivity {

  private Button btnJoinRoom;
  private ImageView ivEp1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_world);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    btnJoinRoom = findViewById(R.id.btn_join_room);
    ivEp1 = findViewById(R.id.iv_ep1);

    ivEp1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(WorldActivity.this, WorldToGameLoading.class);
        startActivity(intent);
      }
    });

    btnJoinRoom.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        joinRoom();
      }
    });
  }

  private void joinRoom() {
    Dialog dialog = new Dialog(WorldActivity.this);
    View view = getLayoutInflater().inflate(R.layout.dialog_input_room_code , null);
    dialog.setContentView(view);

    Window window = dialog.getWindow();
    if (window != null) {
      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
      layoutParams.copyFrom(window.getAttributes());
      layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.5);
      window.setAttributes(layoutParams);
    }

    Button btnJoin = view.findViewById(R.id.btn_join);
    Button btnCancel = view.findViewById(R.id.btn_cancel);

    btnJoin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // correct room code
        // or not
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });

    dialog.show();
  }
}