package kr.ac.tukorea.gamekim2016180014.cardsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = new int[] {
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33,
    };
    private int[] resIds = new int[] {
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_kd,
            R.mipmap.card_4h, R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_kd,
            R.mipmap.card_4h, R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh
    };

    private ImageButton prevCardButton = null;
    private int cardsRemain = 0;
    private int flips = 0;
    private TextView scoreTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.restartBtn);
        btn.setText(R.string.restart);
        scoreTextView = findViewById(R.id.scoreTextView);
        startGame();
    }

    private void startGame() {
        cardsRemain = BUTTON_IDS.length;

        Random random = new Random();
        for(int i = 0; i < resIds.length; i++) {
            int t = random.nextInt(resIds.length);

            int temp = resIds[t];
            resIds[t] = resIds[i];
            resIds[i] = temp;
        }

        for(int i = 0; i < BUTTON_IDS.length; i++) {
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            btn.setVisibility(View.VISIBLE);
            btn.setImageResource(R.mipmap.card_blue_back);
            int resId = resIds[i];
            btn.setTag(resId);
        }
        setScore(0);
        prevCardButton = null;
    }

    public void onBtnCard(View view) {
        if(!(view instanceof ImageButton)) {
            Log.e(TAG, "View is not ImageButton: " + view);
            return;
        }

        ImageButton btn = (ImageButton)view;

        int cardIndex = findButtonIndex(view.getId());
        if (cardIndex < 0) {
            Log.e(TAG, "Cannot find button index!");
            return;
        }

        if(prevCardButton == btn) {
            Log.d(TAG, "Same card");
            Toast.makeText(this, R.string.same_card, Toast.LENGTH_SHORT).show();
            return;
        }

        int resId = (Integer)btn.getTag();
        if (prevCardButton != null) {
            int prevResId = (Integer)prevCardButton.getTag();
            if (prevResId == resId) {
                prevCardButton.setVisibility(View.INVISIBLE);
                btn.setVisibility(View.INVISIBLE);
                prevCardButton = null;

                cardsRemain -= 2;
                if(cardsRemain == 0)
                    askRetry();
            } else {
                btn.setImageResource(resId);
                prevCardButton.setImageResource(R.mipmap.card_blue_back);
                prevCardButton = btn;
                setScore(flips + 1);
            }
        } else {
            setScore(flips + 1);
            btn.setImageResource(resId);
            prevCardButton = btn;
        }
    }

    private void setScore(int score) {
        flips = score;
        String fmt = getString(R.string.flip_fmt);
        String s = String.format(fmt, flips);
        scoreTextView.setText(s);
    }

    private int findButtonIndex(int id) {
        for(int i = 0; i < BUTTON_IDS.length; i++) {
            if (BUTTON_IDS[i] == id) return i;
        }
        return -1;
    }

    public void onBtnRestart(View view) {
        askRetry();
    }

    private void askRetry() {
        AlertDialog alert = new AlertDialog.Builder(this)
            .setTitle(R.string.restart)
            .setMessage(R.string.restart_message)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startGame();
                }
            })
            .setNegativeButton(R.string.no, null)
            .create();
        alert.show();
    }
}