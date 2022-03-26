package kr.ac.tukorea.gamekim2016180014.morecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText editText;
    private TextView outputText;
    private TextWatcher editWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            outputText.setText(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.nameEdit);
        editText.addTextChangedListener(editWatcher);
        outputText = findViewById(R.id.outputText);
    }

    public void onBtnDoIt(View view) {
        outputText.setText(editText.getText().toString());
    }

    public void onCheckBox(View view) {
        CheckBox box = (CheckBox)view;
        Log.d(TAG, "Checked: " + box.isChecked());
    }
}