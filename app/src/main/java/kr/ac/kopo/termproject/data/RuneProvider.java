package kr.ac.kopo.termproject.data;
import kr.ac.kopo.termproject.data.PassiveProvider;
import java.util.ArrayList;
import java.util.List;

public class RuneProvider {

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

        public String getDisplayName() {
            return displayName;
        }

        public String getFlavorText() {
            return flavorText;
        }

        public int getValue(int level) {
            if (level < 1 || level > values.length) {
                throw new IllegalArgumentException("Invalid stat rune level: " + level);
            }
            return values[level - 1];
        }

        public int getMaxLevel() {
            return values.length;
        }
    }

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

        public String getDisplayName() {
            return displayName;
        }

        public String getFlavorText() {
            return flavorText;
        }

        public Trigger getTrigger() {
            return trigger;
        }

        public BonusType getBonusType() {
            return bonusType;
        }



        public int getPassiveEffect(int runeLevel) {
            if (runeLevel < 1 || runeLevel > 3) {
                throw new IllegalArgumentException("Invalid rune level: " + runeLevel);
            }
            return runeLevel <= 2 ? baseEffect : secondaryEffect;
        }


        public int getBonusValue(int runeLevel) {
            if (runeLevel < 2 || runeLevel > 3) {
                return 0;
            }
            return bonusValue;
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
            BATTLE_START,       // 전투 시작 시
            ON_HIT_POISON,      // 공격 명중 시 독 부여
            ON_HIT_LEECH,       // 공격 명중 시 흡혈
            EVERY_THREE_TURNS   // 3턴마다 효과 발동
        }
    }


    public static List<String> getAllRuneNames() {
        List<String> list = new ArrayList<>();
        for (StatProvider s : StatProvider.values()) list.add(s.getDisplayName());
        for (PassiveProvider p : PassiveProvider.values()) list.add(p.getDisplayName());
        return list;
    }


    public static List<String> getStatRuneNames() {
        List<String> list = new ArrayList<>();
        for (StatProvider s : StatProvider.values()) list.add(s.getDisplayName());
        return list;
    }


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
    public static String getRuneName(PassiveProvider passive) {
        switch (passive) {
            case FIGHTING_SPIRIT: return "투지의 룬";
            case LEECH: return "혈귀의 룬";
            case HEAVY_ARMOR: return "혹한의 룬";
            case IGNITION: return "화염의 룬";
            case DETERMINATION: return "수호자의 룬";
            case VENOM: return "독사의 룬";
            case SURVIVAL: return "필생의 룬";
            case ENGAGEMENT: return "돌격의 룬";
            default: return "???";
        }
    }
    public static kr.ac.kopo.termproject.data.PassiveProvider getPassiveProviderByName(String name) {
        for (kr.ac.kopo.termproject.data.PassiveProvider p : kr.ac.kopo.termproject.data.PassiveProvider.values()) {
            if (p.getDisplayName().equals(name)) {
                return p;
            }
        }
        return null;
    }
}
