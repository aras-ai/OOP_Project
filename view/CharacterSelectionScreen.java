package citywars.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import citywars.model.Character;
import citywars.model.User;

public class CharacterSelectionScreen {
    private User firstPlayer;
    private User secondPlayer;
    private boolean firstPlayerSelected = false;
    private VBox layout;

    public CharacterSelectionScreen(Stage stage) {
        layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        Label instructionLabel = new Label("Select your character:");
        VBox charactersBox = new VBox(10);
        charactersBox.setPadding(new Insets(10));

        for (Character character : Character.getAllCharacters()) {
            Button characterButton = new Button(character.getName());
            characterButton.setOnAction(e -> {
                if (!firstPlayerSelected) {
                    firstPlayer.setCharacter(character.getName());
                    firstPlayerSelected = true;
                    instructionLabel.setText("Player 2, select your character:");
                } else {
                    secondPlayer.setCharacter(character.getName());
                    GameScreen gameScreen = new GameScreen(stage, firstPlayer, secondPlayer);
                    stage.setScene(new Scene(gameScreen.getView(), 600, 800));
                }
            });
            charactersBox.getChildren().add(characterButton);
        }

        layout.getChildren().addAll(instructionLabel, charactersBox);
    }

    public void setPlayers(User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public VBox getView() {
        return layout;
    }
}
