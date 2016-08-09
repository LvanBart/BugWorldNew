import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BugWorldAnimation extends Application {

	int width = 600, height = 600;
	int enlargementFactor = 30;
	List<BugWorldObject> allObjects;

	@Override
	public void start(Stage primaryStage) throws Exception {

		World world1 = new World(width / enlargementFactor, height / enlargementFactor);

		// get object lists from World
		allObjects = world1.getAllObjects();

		// List<ImageView> images = new ArrayList<ImageView>();
		Map<ImageView, BugWorldObject> images = new HashMap<ImageView, BugWorldObject>();

		for (BugWorldObject object : allObjects) {
			images.put(makeImageView(object), object);
		}

		// add all objects to group
		Group root = new Group();

		for (ImageView i : images.keySet()) {
			root.getChildren().add(i);
		}

		Scene scene = new Scene(root, width + enlargementFactor, height + enlargementFactor + 60);
		KeyFrame frame = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				world1.updateWorld();

				// update object list
				allObjects = world1.getAllObjects();

				// find which objects are no longer in bug world, and which
				// objects are new
				List<ImageView> toRemove = new ArrayList<ImageView>();
				
				// if there is an object which has been added to bug world, add it to graphical bug world
				for (BugWorldObject object: allObjects) {
					if (!images.containsValue(object)) {
						ImageView iv = makeImageView(object);
						images.put(iv, object);
						root.getChildren().add(iv);
					}
				}

				for (ImageView i : images.keySet()) {
					// if object not in bug world, add to roRemove list
					if (!allObjects.contains(images.get(i))) {
						toRemove.add(i);
					}
				}

				for (ImageView i : toRemove) {
					images.remove(i);
					root.getChildren().remove(i);
				}

				for (ImageView i : images.keySet()) {

					int newX = images.get(i).getX();
					int newY = images.get(i).getY();

					i.setX(newX * enlargementFactor);
					i.setY(newY * enlargementFactor);
				}

			}
		});
		
		// make menu
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		
		Menu menu1 = new Menu("1");
		
		MenuItem menuItem1 = new MenuItem("a");
		MenuItem menuItem2 = new MenuItem("b");
		MenuItem menuItem3 = new MenuItem("c");
		menu1.getItems().addAll(menuItem1, menuItem2, menuItem3);
		
		
		Menu menu2 = new Menu("2");
		Menu menu3 = new Menu("3");
		
		menuBar.getMenus().addAll(menu1, menu2, menu3);
		root.getChildren().add(menuBar);

		Timeline tl = new Timeline(frame);
		tl.setCycleCount(javafx.animation.Animation.INDEFINITE);
		tl.play();

		// add buttons
		Button playBtn = new Button("Play");
		playBtn.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		playBtn.setTextFill(Color.WHITE);
		playBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tl.play();
			}
		});

		Button pauseBtn = new Button("Pause");
		pauseBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		pauseBtn.setTextFill(Color.WHITE);
		pauseBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tl.pause();
			}
		});

		Button stopBtn = new Button("Stop");
		stopBtn.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		stopBtn.setTextFill(Color.WHITE);
		stopBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tl.stop();
			}
		});

		HBox buttons = new HBox();
		buttons.getChildren().add(playBtn);
		buttons.getChildren().add(pauseBtn);
		buttons.getChildren().add(stopBtn);

		buttons.setLayoutY(height + enlargementFactor + 25);
		buttons.setLayoutX(10);
		buttons.setSpacing(10);
		root.getChildren().add(buttons);

		primaryStage.setTitle("Bug World");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public ImageView makeImageView(BugWorldObject object) {
		String fileName = "";

		if (object.getSymbol() == 'C') {
			fileName = "crawling_bug_image.png";
		} else if (object.getSymbol() == 'J') {
			fileName = "jumping_bug_image.png";
		} else if (object.getSymbol() == 'F') {
			fileName = "flying_bug_image.png";
		} else if (object.getSymbol() == 'B') {
			fileName = "bug_image.png";
		} else if (object.getSymbol() == 'P') {
			fileName = "plant_image.png";
		} else if (object.getSymbol() == 'O') {
			fileName = "wall_image.png";
		} else if (object.getSymbol() == 'X') {
			fileName = "tombstone_image.png";
		}

		if (!fileName.equals("")) {
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream(fileName)));

			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(object.getX() * enlargementFactor);
			i.setY(object.getY() * enlargementFactor);

			return i;
		}

		return null;

	}

	public static void main(String[] args) {
		launch(args);

	}

}
