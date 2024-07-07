package citywars.controller;

public class SecurityQuestion {
    public static String[] questions = {
        "What is your father’s name?",
        "What is your favourite color?",
        "What was the name of your first pet?"
    };

    public static String getQuestion(int index) {
        if (index >= 1 && index <= questions.length) {
            return questions[index - 1];
        }
        return null;
    }
}
