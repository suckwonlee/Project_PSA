package kr.ac.kopo.termproject.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 모든 룬(스탯 및 패시브)의 정의와 효과 조회를 제공하는 클래스
 */
public class RuneProvider {

    /**
     * 스탯 룬 정의 (레벨 1~5)
     */
    public enum StatProvider {
        STRIKE("강격의 룬", "레벨만큼 공격력 증가", new int[]{1, 2, 3, 4, 5});

        private final String displayName;
        private final String flavorText;
        private final int[] values;

        StatProvider(String displayName, String flavorText, int[] values) {
            this.displayName = displayName;
            this.flavorText = flavorText;
            this.values = values;
        }

        /** 화면에 표시할 이름 */
        public String getDisplayName() {
            return displayName;
        }

        /** 룬 설명(플레이버 텍스트) */
        public String getFlavorText() {
            return flavorText;
        }

        /**
         * 룬 레벨에 따른 효과 수치를 반환합니다.
         * @param level 1~5
         * @return 해당 레벨의 보너스 값
         */
        public int getValue(int level) {
            if (level < 1 || level > values.length) {
                throw new IllegalArgumentException("Invalid stat rune level: " + level);
            }
            return values[level - 1];
        }

        /** 지원하는 최대 레벨을 반환합니다. */
        public int getMaxLevel() {
            return values.length;
        }
    }

    /**
     * 패시브 룬 정의 (룬 레벨 1~3)
     */
    public enum PassiveProvider {
        IGNITION     ("진화(불지름)",       "어릴적부터 불장난을 많이 치던 아이였지. 그 불 장난이 전장에서 유용할 수준까지 가는건 상상도 못했군",            5,  8,  BonusType.ATTACK,     1,  Trigger.BATTLE_START),
        HEAVY_ARMOR  ("중갑",              "북대륙에서 살아남은 전사들은 갑옷이 필요하지 않다네. 그들의 육신이 어떤 갑옷보다도 더 단단했지.",         1,  3,  BonusType.DEFENSE,    2,  Trigger.BATTLE_START),
        DETERMINATION("결의",             "사흘 밤낮으로 싸우고도 포기하지 않던 사내라네. 기어코 지원군이 도착해서 사람들이 안전해진걸 확인한 뒤에야 기절하고 말더군.", 2,  5,  BonusType.MAX_WILL,  2,  Trigger.BATTLE_START),
        VENOM        ("침독",             "뱀같은 사내라는 말을 아는가? 그는 입이 아니라 무기에 독이 들었네",            1,  2,  BonusType.CRIT_RATE,  3,  Trigger.ON_HIT_POISON),
        FIGHTING_SPIRIT("투쟁심",         "상대가 크면 그에 비례해서 용기가 샘솟고, 상대가 강하다면 오히려 투지가 오르더군. 정말 공격적인 녀석이었지",               1,  2,  BonusType.ATTACK,     2,  Trigger.BATTLE_START),
        LEECH        ("흡혈",             "인간답지 않다? 바로 보았군. 그는 흡혈귀 혼혈이라네",  3,  5,  BonusType.LEECH,      1,  Trigger.ON_HIT_LEECH),
        SURVIVAL     ("생존",             "강한자가 살아남는게 아니다. 살아남는자가 강한것이다",                     4,  8,  BonusType.MAX_HP,     10, Trigger.EVERY_THREE_TURNS),
        ENGAGEMENT   ("교전",             "정말 태생이 싸움꾼이었지. 다 죽어가던 녀석이 새 싸움이라니까 병상에서 바로 일어나더군",                   10, 20, BonusType.INITIAL_HP, 30, Trigger.BATTLE_START);

        private final String displayName;
        private final String flavorText;
        private final int baseEffect;
        private final int secondaryEffect;
        private final BonusType bonusType;
        private final int bonusValue;
        private final Trigger trigger;

        PassiveProvider(String displayName,
                        String flavorText,
                        int baseEffect,
                        int secondaryEffect,
                        BonusType bonusType,
                        int bonusValue,
                        Trigger trigger) {
            this.displayName      = displayName;
            this.flavorText       = flavorText;
            this.baseEffect       = baseEffect;
            this.secondaryEffect  = secondaryEffect;
            this.bonusType        = bonusType;
            this.bonusValue       = bonusValue;
            this.trigger          = trigger;
        }

        /** 화면에 표시할 이름 */
        public String getDisplayName() {
            return displayName;
        }

        /** 룬 설명(플레이버 텍스트) */
        public String getFlavorText() {
            return flavorText;
        }

        /** 발동 시점 */
        public Trigger getTrigger() {
            return trigger;
        }

        /** 보너스 타입 */
        public BonusType getBonusType() {
            return bonusType;
        }

        /**
         * 룬 레벨에 따른 패시브 효과량
         * @param runeLevel 1~3
         * @return 기본 또는 상위 효과량
         */
        public int getPassiveEffect(int runeLevel) {
            if (runeLevel < 1 || runeLevel > 3) {
                throw new IllegalArgumentException("Invalid rune level: " + runeLevel);
            }
            return runeLevel <= 2 ? baseEffect : secondaryEffect;
        }

        /**
         * 룬 레벨에 따른 추가 보너스 수치
         * @param runeLevel 2~3일 때만 보너스 적용
         * @return 보너스 값 또는 0
         */
        public int getBonusValue(int runeLevel) {
            if (runeLevel < 2 || runeLevel > 3) {
                return 0;
            }
            return bonusValue;
        }

        /** 보너스 종류 정의 */
        public enum BonusType {
            ATTACK,     // 공격력
            DEFENSE,    // 방어력
            MAX_WILL,   // 의지 최대치
            CRIT_RATE,  // 치명타율
            LEECH,      // 흡혈량%
            MAX_HP,     // 최대 HP
            INITIAL_HP  // 초기 HP
        }

        /** 발동 시점 정의 */
        public enum Trigger {
            BATTLE_START,       // 전투 시작 시
            ON_HIT_POISON,      // 공격 명중 시 독 부여
            ON_HIT_LEECH,       // 공격 명중 시 흡혈
            EVERY_THREE_TURNS   // 3턴마다 효과 발동
        }
    }

    /**
     * 모든 룬의 이름 목록 조회 (Stat + Passive)
     */
    public static List<String> getAllRuneNames() {
        List<String> list = new ArrayList<>();
        for (StatProvider s : StatProvider.values()) list.add(s.getDisplayName());
        for (PassiveProvider p : PassiveProvider.values()) list.add(p.getDisplayName());
        return list;
    }

    /**
     * 스탯 룬만 이름 목록 조회
     */
    public static List<String> getStatRuneNames() {
        List<String> list = new ArrayList<>();
        for (StatProvider s : StatProvider.values()) list.add(s.getDisplayName());
        return list;
    }

    /**
     * 패시브 룬만 이름 목록 조회
     */
    public static List<String> getPassiveRuneNames() {
        List<String> list = new ArrayList<>();
        for (PassiveProvider p : PassiveProvider.values()) list.add(p.getDisplayName());
        return list;
    }
    public static List<String> getStatRunes() {
        List<String> names = new ArrayList<>();
        for (StatProvider sp : StatProvider.values()) {
            names.add(sp.getDisplayName());
        }
        return names;
    }
}
