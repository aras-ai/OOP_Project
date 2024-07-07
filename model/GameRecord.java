package citywars.model;

import java.util.Date;

public class GameRecord {
    private Date date;
    private String result;
    private String opponentName;
    private int opponentLevel;
    private String rewardOrPunishment;

    public GameRecord(Date date, String result, String opponentName, int opponentLevel, String rewardOrPunishment) {
        this.date = date;
        this.result = result;
        this.opponentName = opponentName;
        this.opponentLevel = opponentLevel;
        this.rewardOrPunishment = rewardOrPunishment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public int getOpponentLevel() {
        return opponentLevel;
    }

    public void setOpponentLevel(int opponentLevel) {
        this.opponentLevel = opponentLevel;
    }

    public String getRewardOrPunishment() {
        return rewardOrPunishment;
    }

    public void setRewardOrPunishment(String rewardOrPunishment) {
        this.rewardOrPunishment = rewardOrPunishment;
    }

    @Override
    public String toString() {
        return String.format("{%s} {result: %s} {opponent: %s (level %d)} {reward/punishment: %s}",
                date, result, opponentName, opponentLevel, rewardOrPunishment);
    }
}
