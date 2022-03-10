package kr.ac.tukorea.gamekim2016180014.SampleProject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * <p>extends : 클래스 상속.</p>
 * <p>MainActivity : 프로그램 시작점.</p>
 * <p>manifests : application의 기본 설정들을 모아놓은 파일.</p>
 * <p>activity들을 여러 개 넣을 수 있으며, icon, theme 등을 설정할 수 있다.</p>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtView = findViewById(R.id.nameText);
        txtView.setText("Kim Ji Sung");
    }

    private void onButtonPushed() {
        TextView txtView = findViewById(R.id.nameText);
        txtView.setText("Button has pushed");
    }
}