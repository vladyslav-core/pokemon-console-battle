package pokemon;

import pokemon.repository.AttackRepository;
import pokemon.repository.PokemonRepository;
import pokemon.service.BattleService;
import pokemon.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        PokemonRepository pokemonRepository = new PokemonRepository();
        AttackRepository attackRepository = new AttackRepository();

        BattleService battleService = new BattleService(pokemonRepository, attackRepository);
        ConsoleUI consoleUI = new ConsoleUI(pokemonRepository, battleService);

        consoleUI.start();
    }
}