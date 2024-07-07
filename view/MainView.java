package citywars.view;

import citywars.Main;

public abstract class MainView {
    public abstract void display();

    public String getInput(String prompt) {
        System.out.print(prompt);
        if (Main.scanner.hasNextLine()) {
            return Main.scanner.nextLine();
        }
        return "";
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
