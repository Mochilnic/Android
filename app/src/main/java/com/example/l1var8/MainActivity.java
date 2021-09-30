package com.example.l1var8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText first;
    private TextInputEditText second;
    private RadioButton plus;
    private RadioButton minus;
    private RadioButton multiply;
    private RadioButton divide;
    private Button calc;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        plus=findViewById(R.id.radioButton1);
        minus=findViewById(R.id.radioButton2);
        multiply=findViewById(R.id.radioButton3);
        divide=findViewById(R.id.radioButton4);
        calc=findViewById(R.id.calc);
        result=findViewById(R.id.result);

        plus.setChecked(true);


        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first.getText().toString().trim().equals("") || second.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.errtext, Toast.LENGTH_LONG).show();
                } else {
                    int a = Integer.parseInt(Objects.requireNonNull(first.getText()).toString().trim());
                    int b = Integer.parseInt(Objects.requireNonNull(second.getText()).toString().trim());
                    int res = 0;
                    if (plus.isChecked()) {
                        res = a + b;
                    }
                    if (minus.isChecked()) {
                        res = a - b;
                    }
                    if (multiply.isChecked()) {
                        res = a * b;
                    }
                    if (divide.isChecked()) {
                        res = a / b;
                    }
                    result.setText(String.valueOf(res));
                }
            }
        });
    }
}