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
import kr.ac.kopo.termproject.R;

/**
 * CharacterSelectionActivity
 * Handles Rune1 and Rune2 selection via passive runes,
 * with unlock, apply (no level dialog), and JSON save/load support.
 * Only "강격의 룬" is supported; other selections use provided placeholders.
 */
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

        btnRune1      = findViewById(R.id.btn_rune1);
        btnRune2      = findViewById(R.id.btn_rune2);
        ivPortrait    = findViewById(R.id.portrait);
        tvPrompt      = findViewById(R.id.detail_prompt);
        tvDescription = findViewById(R.id.detail_placeholder);

        saveManager = new SaveManager(this);

        // Apply saved runes for both slots
        applySavedRune(1);
        applySavedRune(2);

        btnRune1.setOnClickListener(v -> handleRuneSlot(1));
        btnRune2.setOnClickListener(v -> handleRuneSlot(2));
    }

    private void handleRuneSlot(int slot) {
        List<String> runeNames = new ArrayList<>();
        if (slot == 1) {
            // Slot1: Passive runes from enum values
            for (PassiveProvider p : PassiveProvider.values()) {
                // If enum has a display name method, use that; otherwise use name()
                runeNames.add(p.getDisplayName());
            }
        } else {
            // Slot2: Stat runes
            runeNames = RuneProvider.getStatRunes();
        }
        String[] names = runeNames.toArray(new String[0]);

        new AlertDialog.Builder(this)
                .setTitle("룬" + slot + " 선택")
                .setItems(names, (dialog, which) -> {
                    String chosen = names[which];
                    Set<String> unlocked = saveManager.getUnlockedRunes();
                    if (unlocked.contains(chosen)) {
                        applyRuneUI(slot, chosen, getSavedLevel(slot));
                    } else {
                        showUnlockDialog(slot, chosen);
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showUnlockDialog(int slot, String runeName) {
        new AlertDialog.Builder(this)
                .setTitle("룬 해금")
                .setMessage(runeName + "을(를) 해금하시겠습니까?\n(해금 비용: 100골드)")
                .setPositiveButton("예", (d, w) -> {
                    int gold = saveManager.getRemainingGold();
                    if (gold < 100) {
                        Toast.makeText(this, "골드가 부족합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    saveManager.setRemainingGold(gold - 100);
                    saveManager.unlockRuneType(runeName);
                    setSavedRune(slot, runeName, 1);
                    applyRuneUI(slot, runeName, 1);
                    Toast.makeText(this, runeName + " 해금 완료! (Lv1)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("아니요", null)
                .show();
    }

    private void applySavedRune(int slot) {
        String name = (slot == 1)
                ? saveManager.getRune1Name()
                : saveManager.getRune2Name();
        int level = (slot == 1)
                ? saveManager.getRune1Level()
                : saveManager.getRune2Level();
        if (!name.isEmpty() && level > 0) {
            applyRuneUI(slot, name, level);
        }
    }

    private void setSavedRune(int slot, String name, int level) {
        if (slot == 1) saveManager.setRune1(name, level);
        else           saveManager.setRune2(name, level);
    }

    private int getSavedLevel(int slot) {
        return (slot == 1)
                ? saveManager.getRune1Level()
                : saveManager.getRune2Level();
    }

    private void applyRuneUI(int slot, String runeName, int level) {
        int iconRes;
        if ("강격의 룬".equals(runeName)) {
            iconRes = R.drawable.rune_strike;
        } else {
            iconRes = (slot == 1)
                    ? R.drawable.placeholder_rune1
                    : R.drawable.placeholder_rune2;
        }
        if (slot == 1) btnRune1.setImageResource(iconRes);
        else           btnRune2.setImageResource(iconRes);

        ivPortrait.setImageResource(iconRes);
        tvPrompt.setText(runeName + " Lv " + level);
        tvDescription.setText(getRuneDescription(runeName, level));
    }

    private String getRuneDescription(String runeName, int level) {
        if ("강격의 룬".equals(runeName)) {
            return "공격력 +" + level;
        }
        return "";
    }
}
