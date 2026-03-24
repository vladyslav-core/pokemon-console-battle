# Pokemon Console Battle

A small Java console game that simulates simple Pokémon battles.

This project was built with a clean layered structure and separates:
- model
- repository
- service
- ui

The goal of the project is not only to create a playable console game, but also to demonstrate clean code organization and separation of responsibilities.

## Features

- Pokémon and attacks stored in repositories
- Random enemy generation
- Battle-specific Pokémon wrapper with current HP
- Simple turn-based battle flow
- Console-based user interaction
- Clear separation between UI, business logic, and data layer

## Project Structure

```text
pokemon/
├── Main.java
├── model/
│   ├── Pokemon.java
│   ├── Attack.java
│   └── BattlePokemon.java
├── repository/
│   ├── PokemonRepository.java
│   └── AttackRepository.java
├── service/
│   └── BattleService.java
└── ui/
    └── ConsoleUI.java
```text

    Technologies
Java
Object-Oriented Programming
Layered architecture
Console I/O
How to Run
Open the project in IntelliJ IDEA or another Java IDE
Run Main.java
Follow the instructions in the console
Architecture Notes
Model

Contains the core domain objects:

Pokemon
Attack
BattlePokemon
Repository

Responsible for storing and retrieving Pokémon and attacks.

Service

Contains the battle logic and connects repositories with the UI.

UI

Handles all console input and output.

Why This Project

This project was created as a practice project to improve:

Java fundamentals
OOP design
code organization
repository/service separation
console application structure
Possible Future Improvements
attack accuracy checks
type effectiveness system
power points (PP)
status effects
unit tests
file or database persistence
better battle balancing
Author

Created as a Java learning and portfolio project.

## Technologies

- Java
- Object-Oriented Programming (OOP)
- Layered Architecture
- Console I/O

## How to Run

1. Open the project in IntelliJ IDEA or any other Java IDE
2. Run `Main.java`
3. Follow the instructions in the console

## Architecture Notes

### Model
Contains the core domain objects:

- `Pokemon`
- `Attack`
- `BattlePokemon`

### Repository
Responsible for storing and retrieving Pokémon and attacks.

### Service
Contains the battle logic and connects the repositories with the UI.

### UI
Handles all console input and output.

## Why This Project

This project was created as a practice project to improve:

- Java fundamentals
- OOP design
- code organization
- separation of responsibilities
- console application structure

## Possible Future Improvements

- Add attack accuracy checks
- Add type effectiveness system
- Add power points (PP)
- Add status effects
- Add unit tests
- Add file or database persistence
- Improve battle balancing

## Author

Created as a Java learning and portfolio project.
