package citywars.model;

public class Card {
    private static CardDatabase cardDatabase;

    static {
        cardDatabase = new CardDatabase();
    }

    private String name;
    private String type;
    private int attackDefense;
    private int duration;
    private int damage;
    private int upgradeLevel;
    private int upgradeCost;

    public Card(String name, String type, int attackDefense, int duration, int damage, int upgradeLevel, int upgradeCost) {
        this.name = name;
        this.type = type;
        this.attackDefense = attackDefense;
        this.duration = duration;
        this.damage = damage;
        this.upgradeLevel = upgradeLevel;
        this.upgradeCost = upgradeCost;
    }

    public static CardDatabase getCardDatabase() {
        return cardDatabase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAttackDefense() {
        return attackDefense;
    }

    public void setAttackDefense(int attackDefense) {
        this.attackDefense = attackDefense;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public void upgrade() {
        this.attackDefense += 10;
        this.damage += 5;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Attack/Defense: %d, Duration: %d, Damage: %d, Upgrade Level: %d, Upgrade Cost: %d",
                name, attackDefense, duration, damage, upgradeLevel, upgradeCost);
    }
}
