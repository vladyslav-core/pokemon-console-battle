package pokemon.model;

import java.util.Objects;

public class Pokemon {

    private final int id;
    private final String name;
    private final String type1;
    private final String type2;
    private final int total;
    private final int hp;
    private final int attack;
    private final int defense;
    private final int specialAttack;
    private final int specialDefense;
    private final int speed;

    public Pokemon(int id,
                   String name,
                   String type1,
                   String type2,
                   int total,
                   int hp,
                   int attack,
                   int defense,
                   int specialAttack,
                   int specialDefense,
                   int speed) {

        this.id = requirePositive(id, "id");
        this.name = requireText(name, "name");
        this.type1 = requireText(type1, "type1");
        this.type2 = normalizeOptionalText(type2);

        this.total = requireNonNegative(total, "total");
        this.hp = requireNonNegative(hp, "hp");
        this.attack = requireNonNegative(attack, "attack");
        this.defense = requireNonNegative(defense, "defense");
        this.specialAttack = requireNonNegative(specialAttack, "specialAttack");
        this.specialDefense = requireNonNegative(specialDefense, "specialDefense");
        this.speed = requireNonNegative(speed, "speed");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public int getTotal() {
        return total;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean hasSecondType() {
        return type2 != null;
    }

    public String getTypesDisplay() {
        return hasSecondType() ? type1 + "/" + type2 : type1;
    }

    @Override
    public String toString() {
        return "#" + id + " " + name +
                " [" + getTypesDisplay() + "]" +
                " | HP: " + hp +
                ", ATK: " + attack +
                ", DEF: " + defense +
                ", SP.ATK: " + specialAttack +
                ", SP.DEF: " + specialDefense +
                ", SPD: " + speed;
    }

    private static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be > 0");
        }
        return value;
    }

    private static int requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must be >= 0");
        }
        return value;
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return trimmedValue;
    }

    private static String normalizeOptionalText(String value) {
        if (value == null) {
            return null;
        }

        String trimmedValue = value.trim();
        return trimmedValue.isEmpty() ? null : trimmedValue;
    }
}