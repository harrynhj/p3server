import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class JavaFXTemplate extends Application {

	Label welcomeTitleL;
	Label portL;
	TextField portTF;
	Button setPortButton;
	Button startServerButton;
	Label welcomeErrorL;
	GridPane welcomePane;
	HBox welcomeButtonsHB;
	Scene welcomeScene;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		welcomeTitleL = new Label("Connect Four Server");
		portL = new Label("Enter Port Number");
		portTF = new TextField();
		portTF.setAlignment(Pos.CENTER);
		portTF.setPromptText("Enter a valid port number");
		setPortButton = new Button("Set Port");
		startServerButton = new Button("Start Server");
		welcomeErrorL = new Label();
		welcomeButtonsHB = new HBox(setPortButton,startServerButton);
		welcomeButtonsHB.setSpacing(40);
		welcomePane = new GridPane();
		welcomePane.setVgap(5);
		welcomePane.setHgap(5);
		welcomeTitleL.setStyle("-fx-font-size: 36; -fx-alignment: CENTER;-fx-font-family: 'Times New Roman'");
		portL.setStyle("-fx-font-size: 20; -fx-alignment: CENTER;-fx-font-family: 'Times New Roman'");
		welcomeButtonsHB.setStyle("-fx-font-size: 20; -fx-alignment: CENTER");
		welcomeErrorL.setStyle("-fx-font-size: 14; -fx-alignment: CENTER");
		GridPane.setHalignment(portL,HPos.CENTER);
		GridPane.setHalignment(welcomeTitleL,HPos.CENTER);
		GridPane.setHalignment(portTF,HPos.CENTER);
		GridPane.setHalignment(welcomeButtonsHB,HPos.CENTER);
		GridPane.setHalignment(welcomeErrorL,HPos.CENTER);
		welcomePane.add(welcomeTitleL, 42,20);
		welcomePane.add(portL,42,30);
		welcomePane.add(portTF,42,40);
		welcomePane.add(welcomeButtonsHB,42,70);
		welcomePane.add(welcomeErrorL,42,90);

	    welcomeScene = new Scene(welcomePane, 700,700);
		primaryStage.setScene(welcomeScene);
		primaryStage.setResizable(false);
		primaryStage.show();


		// action event to avoid user input
		portTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
				if (!newValue.matches("\\d*")) {
					portTF.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		setPortButton.setOnAction(actionEvent -> {
			if (Objects.equals(portTF.getText(), "")) {
				welcomeErrorL.setText("Please Enter A Port!");
				welcomeErrorL.setVisible(true);
				welcomeErrorL.setTextFill(Color.color(1,0,0));
			} else {
				int temp = Integer.parseInt(portTF.getText());
				if (temp > 65535 || temp < 1) {
					welcomeErrorL.setText("Invalid Port Number!");
					welcomeErrorL.setVisible(true);
					welcomeErrorL.setTextFill(Color.color(1,0,0));
				} else {
					// TODO try if port is valid
					welcomeErrorL.setVisible(false);
					portTF.setDisable(true);
					setPortButton.setDisable(true);
				}
			}
		});
	}

}
