package kr.ac.kopo.termproject;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kr.ac.kopo.termproject.data.PassiveProvider;
import kr.ac.kopo.termproject.data.RuneProvider;
import kr.ac.kopo.termproject.data.SaveManager;

public class CharacterSelectionActivity extends AppCompatActivity {
    private ImageButton btnRune1;
    private ImageButton btnRune2;
    private ImageView ivPortrait;
    private TextView tvPrompt;
    private TextView tvDescription;

    private SaveManager saveManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        btnRune1 = findViewById(R.id.btn_rune1);
        btnRune2 = findViewById(R.id.btn_rune2);
        ivPortrait = findViewById(R.id.portrait);
        tvPrompt = findViewById(R.id.detail_prompt);
        tvDescription = findViewById(R.id.detail_placeholder);

        saveManager = new SaveManager(this);
        saveManager.load();

        int gold = saveManager.getRemainingGold();
        Toast.makeText(this, "보유 골드: " + gold, Toast.LENGTH_SHORT).show();

        applySavedRune(1);
        applySavedRune(2);

        btnRune1.setOnClickListener(v -> handleRuneSlot(1));
        btnRune2.setOnClickListener(v -> handleRuneSlot(2));
    }

    private void handleRuneSlot(int slot) {
        List<String> runeNames = new ArrayList<>();
        if (slot == 1) {
            for (PassiveProvider p : PassiveProvider.values()) {
                runeNames.add(p.getDisplayName());
            }
        } else {
            runeNames = RuneProvider.getStatRuneNames();
        }
        String[] names = runeNames.toArray(new String[0]);

        new AlertDialog.Builder(this)
                .setTitle("룬" + slot + " 선택")
                .setItems(names, (dialog, which) -> {
                    String chosen = names[which];
                    Set<String> unlocked = saveManager.getUnlockedRunes();
                    if (unlocked.contains(chosen)) {
                        applyRune(slot, chosen, getSavedLevel(slot));
                    } else {
                        showUnlockDialog(slot, chosen);
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void applyRune(int slot, String runeName, int level) {
        if (slot == 1) applyPassiveRuneUI(runeName, level);
        else           applyStatRuneUI(runeName, level);
    }

    private void applyPassiveRuneUI(String runeName, int level) {
        int iconRes = getResources().getIdentifier("passive_" + runeName, "drawable", getPackageName());
        if (iconRes == 0) iconRes = R.drawable.placeholder_rune1;

        btnRune1.setImageResource(iconRes);
        ivPortrait.setImageResource(iconRes);
        tvPrompt.setText(runeName + " Lv " + level);
        tvDescription.setText(getPassiveRuneDescription(runeName, level));
    }

    private void applyStatRuneUI(String runeName, int level) {
        int iconRes = getResources().getIdentifier("stat_" + runeName, "drawable", getPackageName());
        if (iconRes == 0) iconRes = R.drawable.placeholder_rune2;

        btnRune2.setImageResource(iconRes);
        ivPortrait.setImageResource(iconRes);
        tvPrompt.setText(runeName + " Lv " + level);
        tvDescription.setText(getStatRuneDescription(runeName, level));
    }

    private void applySavedRune(int slot) {
        String name = (slot == 1) ? saveManager.getRune1Name() : saveManager.getRune2Name();
        int level = (slot == 1) ? saveManager.getRune1Level() : saveManager.getRune2Level();
        if (!name.isEmpty() && level > 0) {
            applyRune(slot, name, level);
        }
    }

    private void setSavedRune(int slot, String name, int level) {
        if (slot == 1) saveManager.setRune1(name, level);
        else           saveManager.setRune2(name, level);
    }

    private int getSavedLevel(int slot) {
        return (slot == 1) ? saveManager.getRune1Level() : saveManager.getRune2Level();
    }

    private void showUnlockDialog(int slot, String runeName) {
        new AlertDialog.Builder(this)
                .setTitle("룬 해금")
                .setMessage(runeName + "을(를) 해금하시겠습니까? (100골드)")
                .setPositiveButton("예", (d, w) -> {
                    int gold = saveManager.getRemainingGold();
                    if (gold < 100) {
                        Toast.makeText(this, "골드가 부족합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    saveManager.setRemainingGold(gold - 100);
                    saveManager.unlockRuneType(runeName);
                    setSavedRune(slot, runeName, 1);
                    applyRune(slot, runeName, 1);
                    Toast.makeText(this, runeName + " 해금 완료! (Lv1)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("아니요", null)
                .show();
    }

    private String getPassiveRuneDescription(String runeName, int level) {
        for (PassiveProvider p : PassiveProvider.values()) {
            if (p.getDisplayName().equals(runeName)) {
                int effect = p.getPassiveEffect(level);
                int bonus = p.getBonusValue(level);
                StringBuilder sb = new StringBuilder("기본 효과: ").append(effect);
                if (bonus > 0) {
                    sb.append(" / 추가 스탯: +").append(bonus).append(" (");
                    switch (p.getBonusType()) {
                        case ATTACK: sb.append("공격력"); break;
                        case DEFENSE: sb.append("방어력"); break;
                        case MAX_WILL: sb.append("의지 최대치"); break;
                        case CRIT_RATE: sb.append("치명타율"); break;
                        case LEECH: sb.append("흡혈량"); break;
                        case MAX_HP: sb.append("최대 HP"); break;
                        case INITIAL_HP: sb.append("초기 HP"); break;
                    }
                    sb.append(")");
                }
                return sb.toString();
            }
        }
        return "알 수 없는 룬";
    }

    private String getStatRuneDescription(String runeName, int level) {
        return runeName + ": 공격력 +" + level;
    }
}
