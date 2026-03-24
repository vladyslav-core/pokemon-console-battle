package pokemon.repository;

import pokemon.model.Attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AttackRepository {

    private final Map<Integer, Attack> attacksById;
    private final List<Attack> attacks;
    private final Random random;

    public AttackRepository() {
        this.attacksById = new HashMap<>();
        this.attacks = new ArrayList<>();
        this.random = new Random();

        loadAttacks();
    }

    public Attack findById(int id) {
        if (id <= 0) {
            return null;
        }
        return attacksById.get(id);
    }

    public Attack getRandomAttack() {
        if (attacks.isEmpty()) {
            throw new IllegalStateException("No attacks loaded");
        }

        int randomIndex = random.nextInt(attacks.size());
        return attacks.get(randomIndex);
    }

    private void loadAttacks() {
        String[] data = {
                "1;Pound;Deals damage with no additional effect.;Normal;Physical;40;100%;35",
                "2;Karate Chop;Has a high critical hit ratio.;Fighting;Physical;50;100%;25",
                "3;DoubleSlap;Attacks 2-5 times in one turn.;Normal;Physical;15;85%;10",
                "4;Comet Punch;Attacks 2-5 times in one turn.;Normal;Physical;18;85%;15",
                "5;Mega Punch;Deals damage with no additional effect.;Normal;Physical;80;85%;20",
                "6;Pay Day;The player picks up extra money after in-game battles.;Normal;Physical;40;100%;20",
                "7;Fire Punch;Has a 10% chance to burn the target.;Fire;Physical;75;100%;15",
                "8;Ice Punch;Has a 10% chance to freeze the target.;Ice;Physical;75;100%;15",
                "9;ThunderPunch;Has a 10% chance to paralyze the target.;Electric;Physical;75;100%;15",
                "10;Scratch;Deals damage with no additional effect.;Normal;Physical;40;100%;35"
        };

        for (String line : data) {
            Attack attack = parseAttack(line);
            attacksById.put(attack.getId(), attack);
            attacks.add(attack);
        }
    }

    private Attack parseAttack(String line) {
        String[] parts = line.split(";");

        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid attack data line: " + line);
        }

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String effect = parts[2].trim();
        String type = parts[3].trim();
        String kind = parts[4].trim();
        int power = Integer.parseInt(parts[5].trim());
        int accuracy = Integer.parseInt(parts[6].replace("%", "").trim());
        int powerPoints = Integer.parseInt(parts[7].trim());

        return new Attack(id, name, effect, type, kind, power, accuracy, powerPoints);
    }
}