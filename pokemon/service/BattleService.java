package pokemon.service;

import pokemon.model.Attack;
import pokemon.model.BattlePokemon;
import pokemon.model.Pokemon;
import pokemon.repository.AttackRepository;
import pokemon.repository.PokemonRepository;

import java.util.Objects;
import java.util.Random;

public class BattleService {

    private final PokemonRepository pokemonRepository;
    private final AttackRepository attackRepository;
    private final Random random;

    public BattleService(PokemonRepository pokemonRepository, AttackRepository attackRepository) {
        this.pokemonRepository = Objects.requireNonNull(pokemonRepository, "pokemonRepository must not be null");
        this.attackRepository = Objects.requireNonNull(attackRepository, "attackRepository must not be null");
        this.random = new Random();
    }

    public BattlePokemon createBattlePokemon(Pokemon basePokemon) {
        Objects.requireNonNull(basePokemon, "basePokemon must not be null");

        Attack firstAttack = attackRepository.getRandomAttack();
        Attack secondAttack = attackRepository.getRandomAttack();

        while (firstAttack.getId() == secondAttack.getId()) {
            secondAttack = attackRepository.getRandomAttack();
        }

        return new BattlePokemon(basePokemon, firstAttack, secondAttack);
    }

    public BattlePokemon createRandomEnemy() {
        Pokemon enemyBasePokemon = pokemonRepository.getRandomPokemon();
        return createBattlePokemon(enemyBasePokemon);
    }

    public int calculateDamage(BattlePokemon attacker, BattlePokemon defender, Attack attack) {
        Objects.requireNonNull(attacker, "attacker must not be null");
        Objects.requireNonNull(defender, "defender must not be null");
        Objects.requireNonNull(attack, "attack must not be null");

        double attackPower = attack.getPower();
        double attackerAttack = attacker.getBasePokemon().getAttack();
        double defenderDefense = defender.getBasePokemon().getDefense();

        double damage = attackPower * (attackerAttack / defenderDefense) * (1.0 / 25.0);
        int finalDamage = (int) damage;

        return Math.max(1, finalDamage);
    }

    public int performAttack(BattlePokemon attacker, BattlePokemon defender, Attack attack) {
        int damage = calculateDamage(attacker, defender, attack);
        defender.takeDamage(damage);
        return damage;
    }

    public Attack getRandomEnemyAttack(BattlePokemon enemy) {
        int randomChoice = random.nextInt(2) + 1;
        return enemy.getAttackByChoice(randomChoice);
    }

    public boolean isPlayerFaster(BattlePokemon player, BattlePokemon enemy) {
        return player.getBasePokemon().getSpeed() >= enemy.getBasePokemon().getSpeed();
    }

    public String executeTurn(BattlePokemon player, BattlePokemon enemy, int playerAttackChoice) {
        Objects.requireNonNull(player, "player must not be null");
        Objects.requireNonNull(enemy, "enemy must not be null");

        Attack playerAttack = player.getAttackByChoice(playerAttackChoice);
        Attack enemyAttack = getRandomEnemyAttack(enemy);

        StringBuilder result = new StringBuilder();

        if (isPlayerFaster(player, enemy)) {
            appendAttackResult(result, player, enemy, playerAttack);

            if (enemy.isFainted()) {
                appendFaintedMessage(result, enemy);
                return appendHpSummary(result, player, enemy);
            }

            appendAttackResult(result, enemy, player, enemyAttack);

            if (player.isFainted()) {
                appendFaintedMessage(result, player);
            }
        } else {
            appendAttackResult(result, enemy, player, enemyAttack);

            if (player.isFainted()) {
                appendFaintedMessage(result, player);
                return appendHpSummary(result, player, enemy);
            }

            appendAttackResult(result, player, enemy, playerAttack);

            if (enemy.isFainted()) {
                appendFaintedMessage(result, enemy);
            }
        }

        return appendHpSummary(result, player, enemy);
    }

    public boolean isBattleOver(BattlePokemon player, BattlePokemon enemy) {
        return player.isFainted() || enemy.isFainted();
    }

    public String getWinnerMessage(BattlePokemon player, BattlePokemon enemy) {
        if (player.isFainted() && enemy.isFainted()) {
            return "Both Pokémon fainted.";
        }
        if (enemy.isFainted()) {
            return "Player wins!";
        }
        if (player.isFainted()) {
            return "Computer wins!";
        }
        return "Battle is not over yet.";
    }

    private void appendAttackResult(StringBuilder result,
                                    BattlePokemon attacker,
                                    BattlePokemon defender,
                                    Attack attack) {
        int damage = performAttack(attacker, defender, attack);

        result.append(attacker.getBasePokemon().getName())
                .append(" used ")
                .append(attack.getName())
                .append(" and dealt ")
                .append(damage)
                .append(" damage.\n");
    }

    private void appendFaintedMessage(StringBuilder result, BattlePokemon pokemon) {
        result.append(pokemon.getBasePokemon().getName())
                .append(" fainted.\n");
    }

    private String appendHpSummary(StringBuilder result, BattlePokemon player, BattlePokemon enemy) {
        result.append(player.getBasePokemon().getName())
                .append(" HP: ")
                .append(player.getCurrentHp())
                .append("\n");

        result.append(enemy.getBasePokemon().getName())
                .append(" HP: ")
                .append(enemy.getCurrentHp())
                .append("\n");

        return result.toString();
    }
}