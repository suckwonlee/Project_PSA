package kr.ac.kopo.termproject.data;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.ac.kopo.termproject.R;
import kr.ac.kopo.termproject.model.Character;

public class GameDataProvider {

    public static List<Character> loadAllCharacters(Context context) {
        List<Character> list = new ArrayList<>();

        list.add(new Character(
                "hero_candidate",
                "용사후보",
                R.drawable.placeholder_char1,
                170,
                13,
                14,
                7,
                0,
                "철제 중갑(중갑+2)",
                "중갑(받는 피해 감소)",
                "추상적이었기에 수많았던 용사 후보중 한명. 자신이 용사라 믿고 세상의 구원을 위해 일어섰다"
        ));

        return list;
    }

    public static List<String> getAttackSkills() {
        return Arrays.asList(
                "공격",
                "방패술",
                "신념의 검"
        );
    }

    public static List<String> getAttackDescriptions() {
        return Arrays.asList(
                "ATK의 100%만큼 물리 피해를 입힙니다 (등급당 +20%).",
                "DEF의 95%만큼 물리 피해를 입힙니다 (등급당 +20%).",
                "ATK의 25%만큼 물리 및 화염 피해를 입힙니다 (등급당 +5%)."
        );
    }

    public static List<String> getDefenseSkills() {
        return Arrays.asList(
                "방어",
                "무기방어",
                "철벽의 의지"
        );
    }

    public static List<String> getDefenseDescriptions() {
        return Arrays.asList(
                "DEF의 250%만큼 받는 피해를 감소시킵니다 (등급당 +50%).",
                "ATK의 200%만큼 받는 피해를 감소시킵니다 (등급당 +40%).",
                "의지 1 부여, 저지불가(모든 즉발형 피해 절반), 중갑 +2 (챕터당 15회, 등급당 +3회)"
        );
    }

    public static List<String> getPassiveRuneNames() {
        return Arrays.asList(
                "화염의 룬",
                "혹한의 룬",
                "수호자의 룬",
                "독사의 룬",
                "투지의 룬",
                "혈귀의 룬",
                "필생의 룬",
                "돌격의 룬"
        );
    }

    public static List<String> getPassiveRunePassivesLevel1() {
        return Arrays.asList(
                "방화",    // 화염의 룬
                "중갑",    // 혹한의 룬
                "의지",    // 수호자의 룬
                "침독",    // 독사의 룬
                "투쟁심",  // 투지의 룬
                "흡혈",    // 혈귀의 룬
                "생존",    // 필생의 룬
                "출격"     // 돌격의 룬
        );
    }

    public static List<String> getPassiveRuneFlavorTexts() {
        return Arrays.asList(
                "어릴적부터 불장난을 많이 치던 아이였지. 그 불 장난이 전장에서 유용할 수준까지 가는건 상상도 못했군",
                "북대륙에서 살아남은 전사들은 갑옷이 필요하지 않다네. 그들의 육신이 어떤 갑옷보다도 더 단단했지.",
                "사흘 밤낮으로 싸우고도 포기하지 않던 사내라네. 기어코 지원군이 도착해서 사람들이 안전해진걸 확인한 뒤에야 기절하고 말더군.",
                "뱀같은 사내라는 말을 아는가? 그는 입이 아니라 무기에 독이 들었네",
                "상대가 크면 그에 비례해서 용기가 샘솟고, 상대가 강하다면 오히려 투지가 오르더군. 정말 공격적인 녀석이었지",
                "인간답지 않다? 바로 보았군. 그는 흡혈귀 혼혈이라네",
                "강한자가 살아남는게 아니다. 살아남는자가 강한것이다",
                "정말 태생이 싸움꾼이었지. 다 죽어가던 녀석이 새 싸움이라니까 병상에서 바로 일어나더군"
        );
    }

    public static List<String> getStatRuneNames() {
        return Arrays.asList(
                "강격의 룬"
        );
    }
    public static List<String> getStatRuneFlavorTexts() {
        return Arrays.asList(
                "강격의 룬: 등급이 오를수록 공격력이 커집니다."
        );
    }


    public static int getStatRuneAttackBonus(int level) {
        switch (level) {
            case 1: return 1;
            case 2: return 2;
            case 3: return 3;
            case 4: return 4;
            case 5: return 5;
            default: return 0;
        }
    }
}
