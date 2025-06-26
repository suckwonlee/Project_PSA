// File: app/src/main/java/kr/ac/kopo/termproject/rune/PassiveProvide.java
package kr.ac.kopo.termproject.data;

/**
 * 패시브 룬 효과를 정의하는 enum 클래스
 */
public enum PassiveProvider {
    // 기본 패시브 룬
    IGNITION(new int[]{5, 8, 15, 20, 30}, Trigger.BATTLE_START),       // 진화(불지름)
    HEAVY_ARMOR(new int[]{1, 3, 6, 10, 15}, Trigger.BATTLE_START),      // 중갑
    DETERMINATION(new int[]{2, 5, 8, 12, 20}, Trigger.BATTLE_START),   // 결의
    VENOM(new int[]{1, 2, 3, 4, 5}, Trigger.ON_HIT_POISON),             // 침독
    FIGHTING_SPIRIT(new int[]{1, 2, 5, 8, 12}, Trigger.BATTLE_START),  // 투쟁심
    LEECH(new int[]{3, 5, 10, 15, 25}, Trigger.ON_HIT_LEECH),           // 흡혈
    SURVIVAL(new int[]{4, 8, 20, 30, 50}, Trigger.EVERY_THREE_TURNS),   // 생존
    ENGAGEMENT(new int[]{10, 20, 40, 60, 100}, Trigger.BATTLE_START),   // 교전

    // 이계등급 패시브 룬
    ARSON(new int[]{3}, Trigger.EACH_TURN),                             // 방화 (매 턴 적에게 화염 피해)
    FORTRESS(new int[]{50}, Trigger.BATTLE_START),                      // 요새 (전투 시작 시 중갑)
    RESISTANCE(new int[]{50}, Trigger.RESIST_FLAME_POISON),             // 결의 (화염/중독 피해 저항)
    POISON_STORM(new int[]{5}, Trigger.EVERY_TWO_TURNS),                // 독무 (2턴마다 독 부여)
    BATTLE_FRENZY(new int[]{7}, Trigger.EVERY_TWO_TURNS),               // 전의 (2턴마다 공격력 증가)
    BLOOD_SHADOW(new int[]{0}, Trigger.APPLY_AFTER_THREE_TURNS),        // 혈잠 (피해 3턴 후 적용)
    REGENERATION(new int[]{20}, Trigger.EACH_TURN),                     // 재생 (매 턴 체력 회복)
    GIANTIZE(new int[]{100}, Trigger.BATTLE_START_MAX_HP);              // 거인화 (전투 시작 시 최대/현재 체력 증가)

    private final int[] values;
    private final Trigger trigger;

    PassiveProvider(int[] values, Trigger trigger) {
        this.values = values;
        this.trigger = trigger;
    }

    /**
     * 레벨(1~values.length)에 해당하는 효과 수치를 반환합니다.
     * 이계등급 룬은 values.length=1이며, 항상 level=1로 호출됩니다.
     */
    public int getValue(int level) {
        if (level < 1 || level > values.length) {
            throw new IllegalArgumentException("Invalid level for " + this.name());
        }
        return values[level - 1];
    }

    public Trigger getTrigger() {
        return trigger;
    }
    public int getMaxLevel() {
        return values.length;
    }

    /**
     * 전체 패시브 룬이 지원하는 최대 레벨을 반환합니다.
     */
    /**
     * 전체 패시브 룬이 지원하는 최대 레벨을 반환합니다.
     */
    public static int getMaxLevelStatic() {
        // 모든 패시브 룬은 동일한 값 배열 길이를 가짐
        return values()[0].getMaxLevel();
    }
    /**
     * 룬 효과 발동 시점을 정의하는 트리거
     */
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
