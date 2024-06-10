package fcu.app.language_studying_app;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GameStage extends AppCompatActivity {

    private ArrayList<String> ansSen;

    private float dx, dy, initx, inity;
    private TextView tvCh1;
    private TextView tvCh2;
    private TextView tvCh3;
    private TextView tvCh4;
    private TextView tvCh5;
    private TextView tvCh6;
    private TextView tvCh7;
    private TextView tvCh8;
    private TextView tvAns1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_stage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findIDInit();

        moveChoice(tvCh1, tvAns1);
        moveChoice(tvCh2, tvAns1);
        moveChoice(tvCh3, tvAns1);
        moveChoice(tvCh4, tvAns1);
        moveChoice(tvCh5, tvAns1);
        moveChoice(tvCh6, tvAns1);
        moveChoice(tvCh7, tvAns1);
        moveChoice(tvCh8, tvAns1);
    }

    private void findIDInit() {
        tvCh1 = findViewById(R.id.tv_ch1);
        tvCh2 = findViewById(R.id.tv_ch2);
        tvCh3 = findViewById(R.id.tv_ch3);
        tvCh4 = findViewById(R.id.tv_ch4);
        tvCh5 = findViewById(R.id.tv_ch5);
        tvCh6 = findViewById(R.id.tv_ch6);
        tvCh7 = findViewById(R.id.tv_ch7);
        tvCh8 = findViewById(R.id.tv_ch8);
        tvAns1 = findViewById(R.id.tv_ans1);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void moveChoice(TextView ch, TextView ans) {
        ch.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initx = v.getX();
                        inity = v.getY();
                        dx = v.getX() - event.getRawX();
                        dy = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                            .x(event.getRawX() + dx)
                            .y(event.getRawY() + dy)
                            .setDuration(0)
                            .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        Rect rect1 = new Rect();
                        Rect rect2 = new Rect();
                        v.getHitRect(rect1);
                        ans.getHitRect(rect2);
                        boolean isColliding = Rect.intersects(rect1, rect2);
                        if (isColliding) {
                            ans.setText(ans.getText().toString() + " " + ch.getText().toString());
                            ch.setText(" ");
                            v.setX(initx);
                            v.setY(inity);
                        } else {
                            v.setX(initx);
                            v.setY(inity);
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
}