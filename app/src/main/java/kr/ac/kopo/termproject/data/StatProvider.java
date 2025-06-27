package kr.ac.kopo.termproject.data;

/**
 * 스탯 룬 효과를 정의하는 enum 클래스
 * 레벨 1~5까지 공격력 증가량을 정의함
 */
public enum StatProvider {
    STRIKE("강격의 룬", new int[]{1, 2, 3, 4, 5});

    private final String displayName;
    private final int[] values;

    StatProvider(String displayName, int[] values) {
        this.displayName = displayName;
        this.values = values;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue(int level) {
        if (level < 1 || level > values.length) {
            throw new IllegalArgumentException("Invalid rune level: " + level);
        }
        return values[level - 1];
    }

    public int getMaxLevel() {
        return values.length;
    }
}
