package com.github.mrebhan.crogamp.gui;

import com.github.mrebhan.crogamp.Crogamp;
import com.github.mrebhan.crogamp.ICrogampInterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class CrogampGUI extends Application implements ICrogampInterface {

	private TableView<String> modTable;
	private Button rebuildGame;
	private ComboBox<String> games;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Crogamp " + Crogamp.VERSION);
		initElements();

		Scene scene = new Scene(new Group(), 450, 250);
		GridPane grid = new GridPane();

		grid.setVgap(5);
		grid.setHgap(5);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		grid.add(games, 0, 0, 10, 1);
		grid.add(rebuildGame,20, 0);

		Group root = (Group) scene.getRoot();
		root.getChildren().add(grid);
		scene.setFill(Paint.valueOf("#292929"));
		stage.setScene(scene);
		stage.show();
	}

	private void initElements() {
		games = new ComboBox<>();
		games.setPrefWidth(200);
		rebuildGame = new Button("Rebuild Game");
	}

	@Override
	public int start(String[] args) {
		launch(args);
		return 0;
	}

}
