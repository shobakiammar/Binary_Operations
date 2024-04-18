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

public class Summation extends AppCompatActivity {
    Button clear ;
    Button Back;
    Button Sum;
    EditText first;
    EditText second;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
    }
    private void setupViews() {
        clear = findViewById(R.id.btnClear);
        Sum = findViewById(R.id.btnSum);
        Back = findViewById(R.id.Back);
        first = findViewById(R.id.edtFirstNum);
        second = findViewById(R.id.edtSecondNum);
        result = findViewById(R.id.tvResult);
    }

    public void SumBtn(View view) {
        if(!first.getText().toString().isEmpty()&&!second.getText().toString().isEmpty()){
            if(isBinary(first.getText().toString()) && isBinary(second.getText().toString())){
                result.setText("The Result :\n"+first.getText().toString()+"\n"+"+"+second.getText().toString()+"\n -----------\n"+ sumBinary(first.getText().toString(),second.getText().toString()));
            }else{
                Toast.makeText(getApplicationContext(), "Not binary numbers", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Empty Edit Texts", Toast.LENGTH_SHORT).show();
        }

    }

    public void ClearBtn(View view) {
        first.setText("");
        second.setText("");
        result.setText("");
    }

    private Boolean isBinary(String binary){
        for(int i =0;i<binary.length();i++){
            char c= binary.charAt(i);
            if(c!='0'&& c!='1'){
                return false;
            }
        }
        return true;
    }
private String sumBinary(String firstBinary, String secondBinary) {
    int maxLength = Math.max(firstBinary.length(), secondBinary.length());
    firstBinary = padWithZeros(firstBinary, maxLength);
    secondBinary = padWithZeros(secondBinary, maxLength);
    StringBuilder result = new StringBuilder();
    int carry = 0;

    for (int i = maxLength - 1; i >= 0; i--) {
        int digit1 = Integer.parseInt(String.valueOf(firstBinary.charAt(i))) ;
        int digit2 = Integer.parseInt(String.valueOf(secondBinary.charAt(i))) ;
        int sum = digit1 + digit2 + carry;
        result.insert(0, sum % 2);
        carry = sum / 2;
    }

    if (carry > 0) {
        result.insert(0, carry);
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

    public void BackBtn(View view) {
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
