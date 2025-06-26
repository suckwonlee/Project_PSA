package kr.ac.kopo.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContinentSelectionActivity extends AppCompatActivity {
    private final String[] continents = {"남대륙", "서대륙", "동대륙", "북대륙", "중앙대륙"};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_selection);

        ImageButton arrowLeft = findViewById(R.id.arrow_left);
        ImageButton arrowRight = findViewById(R.id.arrow_right);
        TextView nameView = findViewById(R.id.continent_name);
        TextView descView = findViewById(R.id.description_text);
        Button btnNext = findViewById(R.id.btn_next_center);

        arrowLeft.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + continents.length) % continents.length;
            updateContent(nameView, descView);
        });
        arrowRight.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % continents.length;
            updateContent(nameView, descView);
        });

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ContinentSelectionActivity.this,
                    CharacterSelectionActivity.class
            );
            // 필요 시 선택한 대륙 정보 전달
            intent.putExtra("selectedContinent", continents[currentIndex]);
            startActivity(intent);
        });

        updateContent(nameView, descView);
    }

    private void updateContent(TextView nameView, TextView descView) {
        nameView.setText(continents[currentIndex]);
        descView.setText("설명: " + continents[currentIndex] + " 의 세계관 설명이 들어갑니다.");
    }
}
