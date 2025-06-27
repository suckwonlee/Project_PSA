package kr.ac.kopo.termproject.data;

public enum PassiveProvider {
    FIGHTING_SPIRIT("투쟁심", new int[]{1, 2, 5, 8, 12}, BonusType.ATTACK, 2, Trigger.BATTLE_START),
    LEECH("흡혈", new int[]{3, 5, 10, 15, 25}, BonusType.LEECH, 1, Trigger.ON_HIT_LEECH),
    HEAVY_ARMOR("중갑", new int[]{1, 3, 6, 10, 15}, BonusType.DEFENSE, 2, Trigger.BATTLE_START);

    private final String displayName;
    private final int[] values;
    private final BonusType bonusType;
    private final int bonusValue;
    private final Trigger trigger;

    PassiveProvider(String displayName, int[] values, BonusType bonusType, int bonusValue, Trigger trigger) {
        this.displayName = displayName;
        this.values = values;
        this.bonusType = bonusType;
        this.bonusValue = bonusValue;
        this.trigger = trigger;
    }

    public String getDisplayName() { return displayName; }

    public int getPassiveEffect(int level) {
        if (level < 1 || level > values.length) {
            throw new IllegalArgumentException("Invalid rune level: " + level);
        }
        return values[level - 1];
    }

    public int getBonusValue(int level) {
        return level >= 2 ? bonusValue : 0;
    }

    public BonusType getBonusType() { return bonusType; }
    public Trigger getTrigger() { return trigger; }
    public int getMaxLevel() { return values.length; }

    public static int getMaxLevelStatic() {
        return values()[0].getMaxLevel();
    }

    public enum BonusType {
        ATTACK,     // 공격력
        DEFENSE,    // 방어력
        MAX_WILL,   // 의지 최대치
        CRIT_RATE,  // 치명타율
        LEECH,      // 흡혈량%
        MAX_HP,     // 최대 HP
        INITIAL_HP  // 초기 HP
    }

    public enum Trigger {
        BATTLE_START,              // 전투 시작 시
        ON_HIT_POISON,             // 공격 명중 시 독 부여
        ON_HIT_LEECH,              // 공격 명중 시 흡혈
        EVERY_THREE_TURNS,         // 3턴마다 효과 발동
        EACH_TURN,                 // 매 턴마다 효과 발동
        EVERY_TWO_TURNS,           // 2턴마다 효과 발동
        RESIST_FLAME_POISON,       // 화염 및 독 피해 저항 적용
        APPLY_AFTER_THREE_TURNS,   // 피해 또는 효과 지연 적용
        BATTLE_START_MAX_HP        // 전투 시작 시 최대/현재 체력 설정
    }
}
