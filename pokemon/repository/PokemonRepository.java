package pokemon.repository;

import pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PokemonRepository {

    private final Map<Integer, Pokemon> pokemonsById;
    private final Map<String, Pokemon> pokemonsByName;
    private final List<Pokemon> pokemons;
    private final Random random;

    public PokemonRepository() {
        this.pokemonsById = new HashMap<>();
        this.pokemonsByName = new HashMap<>();
        this.pokemons = new ArrayList<>();
        this.random = new Random();

        loadPokemons();
    }

    public Pokemon findById(int id) {
        if (id <= 0) {
            return null;
        }

        return pokemonsById.get(id);
    }

    public Pokemon findByName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return pokemonsByName.get(normalizeName(name));
    }

    public Pokemon getRandomPokemon() {
        if (pokemons.isEmpty()) {
            throw new IllegalStateException("No pokemons loaded");
        }

        int randomIndex = random.nextInt(pokemons.size());
        return pokemons.get(randomIndex);
    }

    private void loadPokemons() {
        String[] data = {
                "1;Bulbasaur;Grass;Poison;318;45;49;49;65;65;45",
                "2;Ivysaur;Grass;Poison;405;60;62;63;80;80;60",
                "3;Venusaur;Grass;Poison;525;80;82;83;100;100;80",
                "4;Charmander;Fire;;309;39;52;43;60;50;65",
                "5;Charmeleon;Fire;;405;58;64;58;80;65;80",
                "6;Charizard;Fire;Flying;534;78;84;78;109;85;100",
                "7;Squirtle;Water;;314;44;48;65;50;64;43",
                "8;Wartortle;Water;;405;59;63;80;65;80;58",
                "9;Blastoise;Water;;530;79;83;100;85;105;78",
                "10;Caterpie;Bug;;195;45;30;35;20;20;45"
        };

        for (String line : data) {
            Pokemon pokemon = parsePokemon(line);

            pokemonsById.put(pokemon.getId(), pokemon);
            pokemonsByName.put(normalizeName(pokemon.getName()), pokemon);
            pokemons.add(pokemon);
        }
    }

    private Pokemon parsePokemon(String line) {
        String[] parts = line.split(";");

        if (parts.length != 11) {
            throw new IllegalArgumentException("Invalid pokemon data line: " + line);
        }

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String type1 = parts[2].trim();
        String type2 = parts[3].trim();
        int total = Integer.parseInt(parts[4].trim());
        int hp = Integer.parseInt(parts[5].trim());
        int attack = Integer.parseInt(parts[6].trim());
        int defense = Integer.parseInt(parts[7].trim());
        int specialAttack = Integer.parseInt(parts[8].trim());
        int specialDefense = Integer.parseInt(parts[9].trim());
        int speed = Integer.parseInt(parts[10].trim());

        return new Pokemon(
                id,
                name,
                type1,
                type2,
                total,
                hp,
                attack,
                defense,
                specialAttack,
                specialDefense,
                speed
        );
    }

    private String normalizeName(String name) {
        return name.trim().toLowerCase();
    }
}