package citywars.controller;

import java.util.Random;

public class CaptchaGenerator {
    private static CaptchaGenerator instance;

    static {
        instance = new CaptchaGenerator();
    }

    private Random random;

    private CaptchaGenerator() {
        random = new Random();
        random.setSeed(7);
    }

    public static CaptchaGenerator getInstance() {
        return instance;
    }

    public String generateCaptcha() {
        int a = random.nextInt(10);
        int b = random.nextInt(10);
        String operator = random.nextBoolean() ? "PLUS" : "MINUS";
        return String.format("%d %s %d = ", a, operator, b);
    }

    public boolean validateCaptcha(String captcha, int userResult) {
        String[] parts = captcha.split(" ");
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[2]);
        String operator = parts[1];
        int result = operator.equals("PLUS") ? a + b : a - b;
        return result == userResult;
    }
}
