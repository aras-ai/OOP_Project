package citywars.model;

import java.util.HashMap;

public class Character {
    private static HashMap<String, Character> characters;

    static {
        characters = new HashMap<>();
        characters.put("Saber", new Character("Saber", "Knight", 20, 15, 0));
        characters.put("Archer", new Character("Archer", "Archer", 0, 10, 0.5));
    }

    private String name;
    private String type;
    private int healthBonus;
    private int damageBonus;
    private double buffChance;

    public Character(String name, String type, int healthBonus, int damageBonus, double buffChance) {
        this.name = name;
        this.type = type;
        this.healthBonus = healthBonus;
        this.damageBonus = damageBonus;
        this.buffChance = buffChance;
    }

    public static Character getCharacter(String name) {
        return characters.get(name);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public double getBuffChance() {
        return buffChance;
    }

    @Override
    public String toString() {
        return String.format("Character{name='%s', type='%s', healthBonus=%d, damageBonus=%d, buffChance=%.2f}",
                name, type, healthBonus, damageBonus, buffChance);
    }
}
