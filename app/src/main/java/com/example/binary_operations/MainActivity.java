package com.example.binary_operations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    TextView Welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
        createListView();
        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        Welcome.setText("Welcome "+name +"\n"+"your age in binary is : "+convertToBinary(age));

    }
private String convertToBinary(String age){
    int decimal = Integer.parseInt(age);
    StringBuilder binary = new StringBuilder();
    while (decimal > 0) {
        int remainder = decimal % 2;
        binary.insert(0, remainder);
        decimal = decimal / 2;
    }
    return binary.toString();
}


    private void setupViews() {
        Welcome=findViewById(R.id.textView);
        listview=findViewById(R.id.ListView);

    }
    private void createListView(){
        List<String> list= new ArrayList<>();
        list.add("Summation of binary numbers ");
        list.add("Subtraction of binary numbers");
        list.add("Summary");
        list.add("Back");
        ArrayAdapter <String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(new Intent(MainActivity.this,Summation.class));
                } else if (position==1) {
                    startActivity(new Intent(MainActivity.this,Subtraction.class));
                } else if (position==2) {
                    startActivity(new Intent(MainActivity.this, Summary.class));
                }else if (position==3) {
                    finish();
                }
            }
        });
    }
}