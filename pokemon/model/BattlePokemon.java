package pokemon.model;

import java.util.Objects;

public class BattlePokemon {

    private final Pokemon basePokemon;
    private final Attack firstAttack;
    private final Attack secondAttack;
    private int currentHp;

    public BattlePokemon(Pokemon basePokemon, Attack firstAttack, Attack secondAttack) {
        this.basePokemon = Objects.requireNonNull(basePokemon, "basePokemon must not be null");
        this.firstAttack = Objects.requireNonNull(firstAttack, "firstAttack must not be null");
        this.secondAttack = Objects.requireNonNull(secondAttack, "secondAttack must not be null");

        if (firstAttack.getId() == secondAttack.getId()) {
            throw new IllegalArgumentException("BattlePokemon must have two different attacks");
        }

        this.currentHp = basePokemon.getHp();
    }

    public Pokemon getBasePokemon() {
        return basePokemon;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public Attack getFirstAttack() {
        return firstAttack;
    }

    public Attack getSecondAttack() {
        return secondAttack;
    }

    public Attack getAttackByChoice(int choice) {
        if (choice == 1) {
            return firstAttack;
        }
        if (choice == 2) {
            return secondAttack;
        }

        throw new IllegalArgumentException("choice must be 1 or 2");
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("damage must be >= 0");
        }

        currentHp = Math.max(0, currentHp - damage);
    }

    public boolean isFainted() {
        return currentHp == 0;
    }

    public String getHpDisplay() {
        return currentHp + "/" + basePokemon.getHp();
    }

    @Override
    public String toString() {
        return basePokemon.getName()
                + " [HP: " + getHpDisplay()
                + ", Attacks: "
                + firstAttack.getName()
                + ", "
                + secondAttack.getName()
                + "]";
    }
}