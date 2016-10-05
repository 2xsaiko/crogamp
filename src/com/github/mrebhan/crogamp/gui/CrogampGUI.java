package com.github.mrebhan.crogamp.gui;

import com.github.mrebhan.crogamp.Crogamp;
import com.github.mrebhan.crogamp.ICrogampInterface;
import com.github.mrebhan.crogamp.gm.GameLibrary;
import com.github.mrebhan.crogamp.gm.GameSettings;
import com.github.mrebhan.crogamp.gm.ModSettings;
import com.github.mrebhan.crogamp.settings.Settings;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class CrogampGUI extends Application implements ICrogampInterface {

	private TableView<ModSettings> modTable;
	private Button rebuildGame;
	private ComboBox<GameSettings> games;

	@Override
	public void start(Stage stage) throws Exception {
		String bgn = "-fx-background-color: #292929;";
		stage.setTitle("Crogamp " + Crogamp.VERSION);
		initElements();
		refreshGameList();
		refreshModList();

		Scene scene = new Scene(new Group(), 1366, 768);
		BorderPane grid = new BorderPane();
		grid.setStyle(bgn);

		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);

		ToolBar topBar = new ToolBar(games, spacer, rebuildGame);
		topBar.setStyle(bgn);

		grid.setTop(topBar);
		grid.setCenter(modTable);

		scene.setRoot(grid);
		scene.setFill(Paint.valueOf("#292929"));
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(e -> GameLibrary.getSettings().saveSettings());
	}

	private void initElements() {
		games = new ComboBox<>();
		games.setStyle("-fx-base: #363636;");
		games.setPrefWidth(750);
		games.valueProperty().addListener(e -> {
			@SuppressWarnings("unchecked")
			GameSettings gs = ((ObjectProperty<GameSettings>) e).get();
			rebuildGame.setDisable(gs == null);
			GameLibrary.selectGame(gs);
			refreshModList();
		});

		rebuildGame = new Button("Rebuild Game");
		rebuildGame.setStyle("-fx-base: #363636;");
		rebuildGame.setDisable(GameLibrary.getSelectedGame() == null);
		rebuildGame.setOnAction(e -> rebuildGame());

		modTable = new TableView<>();
		modTable.setStyle("-fx-base: #363636;");
	}

	private void refreshGameList() {
		ObservableList<GameSettings> items = games.getItems();
		items.clear();
		items.addAll(GameLibrary.getSettings().getValue(Settings.GAMES).values());
		games.getSelectionModel().select(GameLibrary.getSelectedGame());
	}

	@SuppressWarnings("unchecked")
	private void refreshModList() {
		ObservableList<ModSettings> mods = modTable.getItems();
		mods.clear();
		if (GameLibrary.getSelectedGame() != null) {
			mods.addAll(GameLibrary.getSelectedGame().getValue(GameSettings.MODS).values());
		}

		TableColumn<ModSettings, Integer> prioCol = new TableColumn<>();
		TableColumn<ModSettings, String> idCol = new TableColumn<>("Mod ID");
		TableColumn<ModSettings, Boolean> enabledCol = new TableColumn<>("Enabled");

		modTable.getColumns().setAll(prioCol, idCol, enabledCol);
	}

	private void rebuildGame() {
		// rebuildGame.setDisable(true);
		// Task<Void> t = new Task<Void>() {
		//
		// @Override
		// protected Void call() throws Exception {
		// return null;
		// }
		// };
		// t.setOnSucceeded(e -> rebuildGame.setDisable(false));
		// t.setOnFailed(e -> rebuildGame.setDisable(false));
		// t.setOnCancelled(e -> rebuildGame.setDisable(false));
	}

	@Override
	public int start(String[] args) {
		launch(args);
		return 0;
	}

}
