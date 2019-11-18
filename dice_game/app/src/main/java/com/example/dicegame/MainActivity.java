package com.example.dicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.zip.InflaterOutputStream;

import static java.lang.String.*;

public class MainActivity extends AppCompatActivity {
    int user_overall = 0;
    int user_turn = 0;
    int computer_overall = 0;
    int computer_turn = 0;
    int computer_now = 0;
    Random rand = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.textView);
        tv.setText(format("Your Score: %d  Computer Score: %d  Your turn score: %d ", user_overall, computer_overall, user_turn));
        Button btn = findViewById(R.id.button); //  roll button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Roll
                int score = rand.nextInt(5)+1;
                ImageView iv= findViewById(R.id.imageView);
                String dice = format("dice%d", score);
                int id = getResources().getIdentifier(dice, "drawable", getPackageName());
                iv.setImageResource(id);
                if(score == 1){
                    user_turn = 0;
                    user_overall+=user_turn;
                    TextView tv = findViewById(R.id.textView);
                    tv.setText(format("Your Score: %d  Computer Score: %d  Your turn score: %d ", user_overall, computer_overall, user_turn));
                    Button btn = findViewById(R.id.button);
                    btn.setActivated(false);
                    btn = findViewById(R.id.button2);
                    btn.setActivated(false);
                    computer_turn_helper();
                }else{
                    user_turn+=score;
                    TextView tv = findViewById(R.id.textView);
                    tv.setText(format("Your Score: %d  Computer Score: %d  Your turn score: %d ", user_overall, computer_overall, user_turn));
                }
            }
        });
        btn = findViewById(R.id.button2); //hold
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_overall+=user_turn;
                TextView tv = findViewById(R.id.textView);
                tv.setText(format("Your Score: %d Computer Score: %d  Your turn score: %d ", user_overall, computer_overall, user_turn));
                user_turn = 0;
                Button btn = findViewById(R.id.button);
                btn.setActivated(false);
                btn = findViewById(R.id.button2);
                btn.setActivated(false);
                computer_turn_helper();
            }
        });
        btn = findViewById(R.id.button3); //reset
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_overall = 0;
                user_turn = 0;
                computer_overall = 0;
                computer_turn = 0;
                TextView tv = findViewById(R.id.textView);
                tv.setText(format("Your Score: %d Computer Score: %d  Your turn score: %d ", user_overall, computer_overall, user_turn));
            }
        });
    }
    private int Computer_turn(){
        int score = rand.nextInt(5)+1;
        ImageView iv= findViewById(R.id.imageView);
        String dice = format("dice%d", score);
        int id = getResources().getIdentifier(dice, "drawable", getPackageName());
        iv.setImageResource(id);
        return score;
    }

    private Handler _handler;

    private void computer_turn_helper() {
        _handler = new Handler();
        Runnable r = new Runnable() {
            private final Handler h0 = _handler;
            @Override
            public void run() {
                if(_handler != h0) return;
                int score = Computer_turn();
                if(score == 1) {
                    computer_turn = 0;
                    TextView tv = findViewById(R.id.textView);
                    tv.setText(format("Your Score: %d Computer Score: %d  Computer hold", user_overall, computer_overall));
                    Button btn = findViewById(R.id.button);
                    btn.setActivated(true);
                    btn = findViewById(R.id.button2);
                    btn.setActivated(true);
                    _handler = null;
                }
                else {
                    computer_turn += score;
                    if (computer_turn >= 20) {
                        computer_overall += computer_turn;
                        TextView tv = findViewById(R.id.textView);
                        tv.setText(format("Your Score: %d Computer Score: %d  Computer hold", user_overall, computer_overall));
                        Button btn = findViewById(R.id.button);
                        btn.setActivated(true);
                        btn = findViewById(R.id.button2);
                        btn.setActivated(true);
                        computer_turn = 0;
                        _handler = null;
                    } else {
                        TextView tv = findViewById(R.id.textView);
                        tv.setText(format("Your Score: %d Computer Score: %d  Computer rolled a %d", user_overall, computer_overall, score));
                        _handler.postDelayed(this, 500);
                    }
                }
            }
        };
        r.run();
    }
}
