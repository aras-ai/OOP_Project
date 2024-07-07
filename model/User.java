package citywars.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class User {
    private static UserDatabase userDatabase;

    static {
        userDatabase = new UserDatabase();
    }

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    private int level;
    private int maxHP;
    private int xp;
    private int coins;
    private List<String> cards;
    private List<GameRecord> gameHistory;
    private Character character;

    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.level = 1;
        this.maxHP = 100;
        this.xp = 0;
        this.coins = 0;
        this.cards = new ArrayList<>();
        this.gameHistory = new ArrayList<>();
        this.character = null;
    }

    public static UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public static boolean isValidUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9_]+$", username);
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                Pattern.matches(".*[a-z].*", password) &&
                Pattern.matches(".*[A-Z].*", password) &&
                Pattern.matches(".*[^a-zA-Z0-9].*", password);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[^@]+@[^@]+\\.com$", email);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setStarterPack(List<String> starterPack) {
        this.cards.addAll(starterPack);
    }

    public List<GameRecord> getGameHistory() {
        return gameHistory;
    }

    public void addGameRecord(GameRecord gameRecord) {
        this.gameHistory.add(gameRecord);
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(String characterName) {
        this.character = Character.getCharacter(characterName);
        this.maxHP += character.getHealthBonus();
    }
}
