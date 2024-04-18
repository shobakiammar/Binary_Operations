package com.example.binary_operations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Subtraction extends AppCompatActivity {
    Button clear;
    Button Sub;
    Button back;
    EditText first;
    EditText second;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subtraction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
        clearSharePref();
    }

    private void setupViews() {
        clear = findViewById(R.id.btnClearSub);
        Sub = findViewById(R.id.btnSub);
        back = findViewById(R.id.BackSub);
        first = findViewById(R.id.edtFirstNumSub);
        second = findViewById(R.id.edtSecondNumSub);
        result = findViewById(R.id.tvResultSub);
    }

    public void SubBtn(View view) {
        if (!first.getText().toString().isEmpty() && !second.getText().toString().isEmpty()) {
            if (isBinary(first.getText().toString()) && isBinary(second.getText().toString())) {
                result.setText("The Result :\n" + first.getText().toString() + "\n -" + second.getText().toString() + "\n -----------\n" + (SubBinary(first.getText().toString(), second.getText().toString())));
            } else {
                Toast.makeText(getApplicationContext(), "Not binary numbers", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Empty Edit Texts", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean isBinary(String binary) {
        for (int i = 0; i < binary.length(); i++) {
            char c = binary.charAt(i);
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    private String SubBinary(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), second.length());
        firstBinary = padWithZeros(firstBinary, maxLength);
        secondBinary = padWithZeros(secondBinary, maxLength);
        StringBuilder result = new StringBuilder();
        int borrow = 0;

        for (int i = maxLength - 1; i >= 0; i--) {
            int digit1 = Integer.parseInt(String.valueOf(firstBinary.charAt(i)));
            int digit2 = Integer.parseInt(String.valueOf(secondBinary.charAt(i)));
            digit1 -= borrow;
            if (digit1 < digit2) {
                digit1 += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.insert(0, digit1 - digit2);
        }

        return result.toString();
    }

    private String padWithZeros(String binaryString, int length) {
        StringBuilder paddedString = new StringBuilder(binaryString);
        while (paddedString.length() < length) {
            paddedString.insert(0, '0');
        }
        return paddedString.toString();
    }

    public void ClearBtnSub(View view) {
        first.setText("");
        second.setText("");
        result.setText("");
    }

    public void BackBtnSub(View view) {
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPref();
    }

    private void SharedPref(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstBinary", first.getText().toString());
        editor.putString("secondBinary", second.getText().toString());
        editor.putString("result", result.getText().toString());
        editor.apply();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getFromShared();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getFromShared();
    }
    private void getFromShared(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstB = sharedPreferences.getString("firstBinary", "");
        String secondB = sharedPreferences.getString("secondBinary", "");
        String resultB = sharedPreferences.getString("result", "");
        first.setText(firstB);
        second.setText(secondB);
        result.setText(resultB);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearSharePref();
    }
    private void clearSharePref(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

