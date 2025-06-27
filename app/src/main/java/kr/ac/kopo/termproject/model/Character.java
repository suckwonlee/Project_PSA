package kr.ac.kopo.termproject.model;

import android.os.Parcel;
import android.os.Parcelable;
import kr.ac.kopo.termproject.data.PassiveProvider;
import kr.ac.kopo.termproject.data.StatProvider;

public class Character implements Parcelable {
    private String id;             // 고유 식별자
    private String name;           // 캐릭터 이름
    private int imageResId;        // 프로필 이미지 리소스 ID
    private int hp;                // 최대 체력
    private int attack;            // 공격력
    private int defense;           // 방어력
    private int critRate;          // 치명타율
    private int evasion;           // 회피율
    private String equipment;      // 기본 장비 설명
    private String passiveSkill;   // 패시브 스킬 설명
    private String description;    // 캐릭터 설명

    // 룬 슬롯
    private PassiveProvider passiveRune;
    private int passiveRuneLevel;
    private StatProvider statRune;
    private int statRuneLevel;

    public Character(String id,
                     String name,
                     int imageResId,
                     int hp,
                     int attack,
                     int defense,
                     int critRate,
                     int evasion,
                     String equipment,
                     String passiveSkill,
                     String description) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.critRate = critRate;
        this.evasion = evasion;
        this.equipment = equipment;
        this.passiveSkill = passiveSkill;
        this.description = description;
    }

    public Character(String id, String name, int imageResId) {
        this(
                id,
                name,
                imageResId,
                100,
                10,
                5,
                0,
                0,
                "",
                "",
                ""
        );
    }


    protected Character(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageResId = in.readInt();
        hp = in.readInt();
        attack = in.readInt();
        defense = in.readInt();
        critRate = in.readInt();
        evasion = in.readInt();
        equipment = in.readString();
        passiveSkill = in.readString();
        description = in.readString();
        passiveRune = PassiveProvider.valueOf(in.readString());
        passiveRuneLevel = in.readInt();
        statRune = StatProvider.valueOf(in.readString());
        statRuneLevel = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(imageResId);
        dest.writeInt(hp);
        dest.writeInt(attack);
        dest.writeInt(defense);
        dest.writeInt(critRate);
        dest.writeInt(evasion);
        dest.writeString(equipment);
        dest.writeString(passiveSkill);
        dest.writeString(description);
        dest.writeString(passiveRune != null ? passiveRune.name() : "");
        dest.writeInt(passiveRuneLevel);
        dest.writeString(statRune != null ? statRune.name() : "");
        dest.writeInt(statRuneLevel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    // getter/setter
    public String getId() { return id; }
    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    public int getCritRate() { return critRate; }
    public void setCritRate(int critRate) { this.critRate = critRate; }
    public int getEvasion() { return evasion; }
    public void setEvasion(int evasion) { this.evasion = evasion; }
    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }
    public String getPassiveSkill() { return passiveSkill; }
    public void setPassiveSkill(String passiveSkill) { this.passiveSkill = passiveSkill; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PassiveProvider getPassiveRune() { return passiveRune; }
    public int getPassiveRuneLevel() { return passiveRuneLevel; }
    public void setPassiveRune(PassiveProvider rune, int level) {
        this.passiveRune = rune;
        this.passiveRuneLevel = level;
    }

    public StatProvider getStatRune() { return statRune; }
    public int getStatRuneLevel() { return statRuneLevel; }
    public void setStatRune(StatProvider rune, int level) {
        this.statRune = rune;
        this.statRuneLevel = level;
    }
}
