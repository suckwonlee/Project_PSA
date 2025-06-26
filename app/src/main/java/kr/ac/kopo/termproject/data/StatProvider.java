// File: app/src/main/java/kr/ac/kopo/termproject/data/StatProvider.java
package kr.ac.kopo.termproject.data;

/**
 * 스탯 룬 효과를 정의하는 enum 클래스
 * 현재 테스트 용도로 강격의 룬만 포함합니다.
 * 레벨 1~5까지 지원합니다.
 */
public enum StatProvider {
    STRIKE(new int[]{1, 2, 3, 4, 5});         // 강격의 룬 (레벨만큼 공격력 증가)

    private final int[] values;

    StatProvider(int[] values) {
        this.values = values;
    }

    /**
     * 룬 레벨에 따른 효과 수치를 반환합니다.
     * @param level 1~5
     * @return 해당 레벨의 보너스 값
     */
    public int getValue(int level) {
        if (level < 1 || level > values.length) {
            throw new IllegalArgumentException("Invalid level for " + this.name());
        }
        return values[level - 1];
    }

    /**
     * 지원하는 최대 레벨을 반환합니다.
     * @return values.length
     */
    public int getMaxLevel() {
        return values.length;
    }
}
