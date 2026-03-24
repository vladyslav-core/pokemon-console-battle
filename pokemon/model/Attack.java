package pokemon.model;

import java.util.Objects;

public class Attack {

    private final int id;
    private final String name;
    private final String effect;
    private final String type;
    private final String kind;
    private final int power;
    private final int accuracy;
    private final int powerPoints;

    public Attack(int id,
                  String name,
                  String effect,
                  String type,
                  String kind,
                  int power,
                  int accuracy,
                  int powerPoints) {

        this.id = requirePositive(id, "id");
        this.name = requireText(name, "name");
        this.effect = requireText(effect, "effect");
        this.type = requireText(type, "type");
        this.kind = requireText(kind, "kind");
        this.power = requireNonNegative(power, "power");
        this.accuracy = requirePercentage(accuracy, "accuracy");
        this.powerPoints = requireNonNegative(powerPoints, "powerPoints");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getPowerPoints() {
        return powerPoints;
    }

    public String getAccuracyDisplay() {
        return accuracy + "%";
    }

    @Override
    public String toString() {
        return "#" + id + " " + name +
                " [" + type + ", " + kind + "]" +
                " | Power: " + power +
                ", Accuracy: " + accuracy + "%" +
                ", PP: " + powerPoints;
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

    private static int requirePercentage(int value, String fieldName) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(fieldName + " must be between 0 and 100");
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
}