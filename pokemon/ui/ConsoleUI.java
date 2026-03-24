package pokemon.ui;

import pokemon.model.Attack;
import pokemon.model.BattlePokemon;
import pokemon.model.Pokemon;
import pokemon.repository.PokemonRepository;
import pokemon.service.BattleService;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleUI {

    private final PokemonRepository pokemonRepository;
    private final BattleService battleService;
    private final Scanner scanner;

    public ConsoleUI(PokemonRepository pokemonRepository, BattleService battleService) {
        this.pokemonRepository = Objects.requireNonNull(pokemonRepository, "pokemonRepository must not be null");
        this.battleService = Objects.requireNonNull(battleService, "battleService must not be null");
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        Pokemon selectedPlayerPokemon = null;

        printWelcome();

        while (running) {
            if (selectedPlayerPokemon == null) {
                selectedPlayerPokemon = choosePlayerPokemon();
            }

            BattlePokemon player = battleService.createBattlePokemon(selectedPlayerPokemon);
            BattlePokemon enemy = battleService.createRandomEnemy();

            printBattleStart(player, enemy);

            while (!battleService.isBattleOver(player, enemy)) {
                printBattleStatus(player, enemy);
                int choice = askAttackChoice(player);

                System.out.println();
                String turnResult = battleService.executeTurn(player, enemy, choice);
                System.out.println(turnResult);
            }

            System.out.println(battleService.getWinnerMessage(player, enemy));
            System.out.println();

            int nextAction = askAfterBattleAction();

            if (nextAction == 1) {
                // same pokemon again
            } else if (nextAction == 2) {
                selectedPlayerPokemon = null;
            } else {
                running = false;
            }
        }

        System.out.println("Game ended.");
    }

    private void printWelcome() {
        System.out.println("=== POKEMON GAME ===");
        System.out.println();
    }

    private Pokemon choosePlayerPokemon() {
        while (true) {
            System.out.println("Choose your Pokemon by ID or name:");
            String input = scanner.nextLine().trim();

            Pokemon pokemon = findPokemon(input);

            if (pokemon != null) {
                System.out.println();
                System.out.println("You chose:");
                printPokemonDetails(pokemon);
                System.out.println();
                return pokemon;
            }

            System.out.println("Pokemon not found. Please enter a valid ID or name.");
            System.out.println();
        }
    }

    private Pokemon findPokemon(String input) {
        if (isNumber(input)) {
            return pokemonRepository.findById(Integer.parseInt(input));
        }

        return pokemonRepository.findByName(input);
    }

    private void printBattleStart(BattlePokemon player, BattlePokemon enemy) {
        System.out.println("=== BATTLE START ===");
        System.out.println();

        System.out.println("Your Pokemon:");
        printBattlePokemonDetails(player);
        System.out.println();

        System.out.println("Enemy Pokemon:");
        printBattlePokemonDetails(enemy);
        System.out.println();
    }

    private void printPokemonDetails(Pokemon pokemon) {
        System.out.println("#" + pokemon.getId() + " " + pokemon.getName());
        System.out.println("Types: " + pokemon.getTypesDisplay());
        System.out.println("HP: " + pokemon.getHp());
        System.out.println("ATK: " + pokemon.getAttack());
        System.out.println("DEF: " + pokemon.getDefense());
        System.out.println("SP. ATK: " + pokemon.getSpecialAttack());
        System.out.println("SP. DEF: " + pokemon.getSpecialDefense());
        System.out.println("SPD: " + pokemon.getSpeed());
        System.out.println("TOTAL: " + pokemon.getTotal());
    }

    private void printBattlePokemonDetails(BattlePokemon battlePokemon) {
        printPokemonDetails(battlePokemon.getBasePokemon());
        System.out.println("Current HP: " + battlePokemon.getHpDisplay());
        System.out.println("Attack 1: " + formatAttack(battlePokemon.getFirstAttack()));
        System.out.println("Attack 2: " + formatAttack(battlePokemon.getSecondAttack()));
    }

    private void printBattleStatus(BattlePokemon player, BattlePokemon enemy) {
        System.out.println("=== CURRENT STATUS ===");
        System.out.println(player.getBasePokemon().getName() + " HP: " + player.getHpDisplay());
        System.out.println(enemy.getBasePokemon().getName() + " HP: " + enemy.getHpDisplay());
        System.out.println();
    }

    private int askAttackChoice(BattlePokemon player) {
        while (true) {
            System.out.println("Choose attack:");
            System.out.println("1 - " + formatAttack(player.getFirstAttack()));
            System.out.println("2 - " + formatAttack(player.getSecondAttack()));

            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equals("2")) {
                return Integer.parseInt(input);
            }

            System.out.println("Invalid choice. Please enter 1 or 2.");
            System.out.println();
        }
    }

    private int askAfterBattleAction() {
        while (true) {
            System.out.println("What do you want to do next?");
            System.out.println("1 - Start a new battle with the same Pokemon");
            System.out.println("2 - Choose another Pokemon");
            System.out.println("3 - Exit game");

            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return Integer.parseInt(input);
            }

            System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            System.out.println();
        }
    }

    private String formatAttack(Attack attack) {
        return attack.getName()
                + " [" + attack.getType() + ", " + attack.getKind() + "]"
                + " Power: " + attack.getPower()
                + ", Accuracy: " + attack.getAccuracyDisplay()
                + ", PP: " + attack.getPowerPoints();
    }

    private boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}