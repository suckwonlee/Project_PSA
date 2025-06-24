package kr.ac.kopo.termproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

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

        arrowLeft.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + continents.length) % continents.length;
            updateContent(nameView, descView);
        });
        arrowRight.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % continents.length;
            updateContent(nameView, descView);
        });

        updateContent(nameView, descView);
    }

    private void updateContent(TextView nameView, TextView descView) {
        nameView.setText(continents[currentIndex]);
        descView.setText("설명: " + continents[currentIndex] + " 의 세계관 설명이 들어갑니다.");
    }
}
