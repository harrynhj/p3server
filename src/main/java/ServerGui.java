import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class ServerGui extends Application {

	Label welcomeTitleL;
	Label backendTitleL;
	Label portL;
	Label clientCounterL;
	ListView<String> logLV;
	TextField portTF;
	Button setPortButton;
	Button startServerButton;
	Label welcomeErrorL;
	GridPane welcomePane;
	GridPane backendPane;
	HBox welcomeButtonsHB;
	Scene welcomeScene;
	Scene backendScene;
	int port;
	Server serverConnection;


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
		startServerButton.setDisable(true);
		welcomeErrorL = new Label();
		welcomeButtonsHB = new HBox(setPortButton,startServerButton);
		welcomeButtonsHB.setSpacing(40);

		backendTitleL = new Label("Server Status");
		clientCounterL = new Label("Online Clients: ");
		logLV = new ListView<>();


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


		backendTitleL.setStyle("-fx-font-size: 32; -fx-font-family: 'Times New Roman'");
		GridPane.setHalignment(backendTitleL,HPos.CENTER);
		GridPane.setValignment(clientCounterL,VPos.TOP);
		logLV.setPrefWidth(350);
		backendPane = new GridPane();
		backendPane.setVgap(5);
		backendPane.setHgap(5);
		backendPane.add(backendTitleL,5,10);
		backendPane.add(logLV,5,30);
		backendPane.add(clientCounterL,20,30);

	    welcomeScene = new Scene(welcomePane, 700,700);
		backendScene = new Scene(backendPane,700,700);
		primaryStage.setScene(welcomeScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Connect Four Server");
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
					portTF.clear();
				} else {
					welcomeErrorL.setVisible(false);
					portTF.setDisable(true);
					setPortButton.setDisable(true);
					startServerButton.setDisable(false);
					port = temp;
				}
			}
		});

		startServerButton.setOnAction(actionEvent -> {
			// TODO: test port
			serverConnection = new Server( data -> {
				Platform.runLater(() -> {

					if (Objects.equals(data.toString(), "Invalid port number")) {
						welcomeErrorL.setText("Port Already In Use!");
						welcomeErrorL.setVisible(true);
						welcomeErrorL.setTextFill(Color.color(1,0,0));
						setPortButton.setDisable(false);
						portTF.clear();
						portTF.setDisable(false);
					} else {
						primaryStage.setScene(backendScene);
						logLV.getItems().add(data.toString());
					}
				});
			},port);
		});
	}

}
