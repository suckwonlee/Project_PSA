package kr.ac.kopo.termproject.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * SaveManager handles loading and saving game state in JSON format
 * into a private file (save.json) under Internal Storage.
 * Manages remaining gold, unlocked runes, and rune slot selections.
 */
public class SaveManager {
    private static final String TAG = "SaveManager";
    private static final String FILE_NAME = "save.json";

    private final Context context;
    private final File saveFile;
    private JSONObject cache = null;
    public SaveManager(Context context) {
        this.context = context;
        this.saveFile = new File(context.getFilesDir(), FILE_NAME);
        ensureFileExists();
    }

    // SaveManager.java 내
    public void load() {
        cache = loadJson();
    }

    /** Ensure save.json exists; if not, copy default from assets */
    private void ensureFileExists() {
        if (!saveFile.exists()) {
            try (InputStream in = context.getAssets().open(FILE_NAME);
                 FileOutputStream out = new FileOutputStream(saveFile)) {
                byte[] buf = new byte[4096];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to copy initial save.json", e);
            }
        }
    }

    /** Load entire JSON; return default structure on error */
    private JSONObject loadJson() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Failed to load or parse save.json", e);
        }
        // Build default JSON
        try {
            JSONObject root = new JSONObject();
            root.put("remainingGold", 0);
            root.put("unlockedRunes", new JSONArray());
            JSONObject slots = new JSONObject();
            slots.put("rune1", new JSONObject().put("name", "").put("level", 0));
            slots.put("rune2", new JSONObject().put("name", "").put("level", 0));
            root.put("runeSlots", slots);
            return root;
        } catch (JSONException ex) {
            // should not occur
            return new JSONObject();
        }
    }

    /** Save JSON root back to file */
    private void saveJson(JSONObject root) {
        try (FileWriter writer = new FileWriter(saveFile, false)) {
            writer.write(root.toString());
        } catch (IOException e) {
            Log.e(TAG, "Failed to save save.json", e);
        }
    }

    // Remaining Gold
    public int getRemainingGold() {
        return loadJson().optInt("remainingGold", 0);
    }

    public void setRemainingGold(int gold) {
        JSONObject root = loadJson();
        try {
            root.put("remainingGold", gold);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to set remainingGold", e);
        }
        saveJson(root);
    }

    // Unlocked Runes
    public Set<String> getUnlockedRunes() {
        JSONObject root = loadJson();
        Set<String> set = new HashSet<>();
        JSONArray arr = root.optJSONArray("unlockedRunes");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                set.add(arr.optString(i));
            }
        }
        return set;
    }

    public void unlockRuneType(String runeName) {
        JSONObject root = loadJson();
        try {
            JSONArray arr = root.optJSONArray("unlockedRunes");
            if (arr == null) arr = new JSONArray();
            Set<String> seen = new HashSet<>();
            JSONArray newArr = new JSONArray();
            for (int i = 0; i < arr.length(); i++) {
                String v = arr.optString(i);
                if (seen.add(v)) newArr.put(v);
            }
            if (seen.add(runeName)) newArr.put(runeName);
            root.put("unlockedRunes", newArr);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to unlock rune type", e);
        }
        saveJson(root);
    }

    // Rune Slot 1
    public String getRune1Name() {
        JSONObject slots = loadJson().optJSONObject("runeSlots");
        if (slots != null) {
            return slots.optJSONObject("rune1").optString("name", "");
        }
        return "";
    }

    public int getRune1Level() {
        JSONObject slots = loadJson().optJSONObject("runeSlots");
        if (slots != null) {
            return slots.optJSONObject("rune1").optInt("level", 0);
        }
        return 0;
    }

    public void setRune1(String name, int level) {
        JSONObject root = loadJson();
        try {
            JSONObject slots = root.optJSONObject("runeSlots");
            if (slots == null) {
                slots = new JSONObject();
                root.put("runeSlots", slots);
            }
            slots.put("rune1", new JSONObject().put("name", name).put("level", level));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to set rune1", e);
        }
        saveJson(root);
    }

    // Rune Slot 2 (for future use)
    public String getRune2Name() {
        JSONObject slots = loadJson().optJSONObject("runeSlots");
        if (slots != null) {
            return slots.optJSONObject("rune2").optString("name", "");
        }
        return "";
    }

    public int getRune2Level() {
        JSONObject slots = loadJson().optJSONObject("runeSlots");
        if (slots != null) {
            return slots.optJSONObject("rune2").optInt("level", 0);
        }
        return 0;
    }

    public void setRune2(String name, int level) {
        JSONObject root = loadJson();
        try {
            JSONObject slots = root.optJSONObject("runeSlots");
            if (slots == null) {
                slots = new JSONObject();
                root.put("runeSlots", slots);
            }
            slots.put("rune2", new JSONObject().put("name", name).put("level", level));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to set rune2", e);
        }
        saveJson(root);
    }

}
