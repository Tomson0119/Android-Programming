package kr.ac.tukorea.gamekim2016180014.SampleProject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * <p>extends : 클래스 상속.</p>
 * <p>MainActivity : 프로그램 시작점.</p>
 * <p>manifests : application의 기본 설정들을 모아놓은 파일.</p>
 * <p>activity들을 여러 개 넣을 수 있으며, icon, theme 등을 설정할 수 있다.</p>
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] IMAGE_IDS = new int[] {
            R.mipmap.cat1,
            R.mipmap.cat2,
            R.mipmap.cat3,
            R.mipmap.cat4,
            R.mipmap.cat5
    };

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPage(1);
    }

    public void onButtonPrev(View view) {
        setPage(page-1);
        Log.d(TAG, "Button prev pushed");
    }

    public void onButtonNext(View view) {
        setPage(page+1);
        Log.d(TAG, "Button next pushed.");
    }

    private void setPage(int newPage) {
        if(newPage <= 0) {
            newPage = IMAGE_IDS.length;
        }
        else if(newPage > IMAGE_IDS.length) {
            newPage = 1;
        }
        page = newPage;

        String text = page + " / " + IMAGE_IDS.length;
        TextView view = findViewById(R.id.pageText);
        view.setText(text);

        ImageView img = findViewById(R.id.contentImageView);
        img.setImageResource(IMAGE_IDS[page-1]);
    }
}