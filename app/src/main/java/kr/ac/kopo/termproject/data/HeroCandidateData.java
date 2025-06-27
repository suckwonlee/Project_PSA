package kr.ac.kopo.termproject.data;

import java.util.HashMap;
import java.util.Map;

import kr.ac.kopo.termproject.R;


public class HeroCandidateData {

    private final String name = "용사후보";
    private final String description = "추상적이었기에 수많았던 용사 후보 중 한 명. 자신이 용사라 믿고 세상의 구원을 위해 일어섰다.";
    private int hp = 170;
    private int atk = 13;
    private int def = 14;
    private int crit = 7;

    private PassiveProvider passive;

    private final Map<String, String> attackFlavors = new HashMap<>();
    private final Map<String, String> defenseFlavors = new HashMap<>();
    private final Map<String, String> attackDescriptions = new HashMap<>();
    private final Map<String, String> defenseDescriptions = new HashMap<>();
    private final Map<String, int[]> attackGrowths = new HashMap<>();
    private final Map<String, int[]> defenseGrowths = new HashMap<>();
    private final Map<String, Integer> attackIcons = new HashMap<>();
    private final Map<String, Integer> defenseIcons = new HashMap<>();

    public HeroCandidateData() {
        putAttack("공격", "ATK의 100%만큼 물리 피해를 입힙니다.",
                "어릴적부터 훈련이 습관이던 녀석이지. 괭이질하는게 검 내려베기 같더라고.",
                new int[] {0, 20, 40, 60, 80},
                R.drawable.icon_attack_basic);

        putDefense("무기방어", "ATK의 200%만큼 받는 피해를 감소시킵니다.",
                "전장에서 가장 많은 적을 죽이는 자는 가장 공격적인 존재가 아닌, 가장 오래 살아남은자다.",
                new int[] {0, 40, 80, 120, 160},
                R.drawable.icon_defense_weaponblock);
    }

    private void putAttack(String name, String desc, String flavor, int[] growths, int iconResId) {
        attackDescriptions.put(name, desc);
        attackFlavors.put(name, flavor);
        attackGrowths.put(name, growths);
        attackIcons.put(name, iconResId);
    }

    private void putDefense(String name, String desc, String flavor, int[] growths, int iconResId) {
        defenseDescriptions.put(name, desc);
        defenseFlavors.put(name, flavor);
        defenseGrowths.put(name, growths);
        defenseIcons.put(name, iconResId);
    }

    public void setPassive(PassiveProvider passive) {
        this.passive = passive;
    }

    public PassiveProvider getPassive() {
        return passive;
    }

    public void addAtk(int value) {
        this.atk += value;
    }

    public void addHp(int value) {
        this.hp += value;
    }

    public void addDef(int value) {
        this.def += value;
    }

    public void addCrit(int value) {
        this.crit += value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getCrit() {
        return crit;
    }

    public String getAttackSkillFlavor(String skill) {
        return attackFlavors.getOrDefault(skill, "");
    }

    public String getDefenseSkillFlavor(String skill) {
        return defenseFlavors.getOrDefault(skill, "");
    }

    public String getAttackSkillDescription(String skill) {
        return attackDescriptions.getOrDefault(skill, "");
    }

    public String getDefenseSkillDescription(String skill) {
        return defenseDescriptions.getOrDefault(skill, "");
    }

    public int[] getAttackSkillGrowth(String skill) {
        return attackGrowths.getOrDefault(skill, new int[] {0, 0, 0, 0, 0});
    }

    public int[] getDefenseSkillGrowth(String skill) {
        return defenseGrowths.getOrDefault(skill, new int[] {0, 0, 0, 0, 0});
    }

    public int getAttackSkillIcon(String skill) {
        return attackIcons.getOrDefault(skill, R.drawable.placeholder_attack);
    }

    public int getDefenseSkillIcon(String skill) {
        return defenseIcons.getOrDefault(skill, R.drawable.placeholder_defense);
    }
}
