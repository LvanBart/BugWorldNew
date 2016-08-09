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

	@Override
	public void start(Stage primaryStage) throws Exception {


		World world1 = new World(width / enlargementFactor, height / enlargementFactor);

		// get object lists from World
		List<Bug> bugs = world1.getBugs();
		List<Plant> plants = world1.getPlants();
		List<Obstacle> obstacles = world1.getObstacles();


		// List<ImageView> images = new ArrayList<ImageView>();
		Map<ImageView, BugWorldObject> images = new HashMap<ImageView, BugWorldObject>();
		

		for (Bug b: bugs) {
			// for general bug
			String fileName = "bug_image.png";
			
			// if crawlingBug
			if (b.getSymbol() == 'C') {
				fileName = "crawling_bug_image.png";
			} else if (b.getSymbol() == 'J') {
				fileName = "jumping_bug_image.png";
			} else if (b.getSymbol() == 'F') {
				fileName = "flying_bug_image.png";
			}
			
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream(fileName)));
			
			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(b.getX() * enlargementFactor);
			i.setY(b.getY() * enlargementFactor);

			images.put(i, b);
		}

		for (Plant p: plants) {
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream("plant_image.png")));
			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(p.getX() * enlargementFactor);
			i.setY(p.getY() * enlargementFactor);
			
			images.put(i, p);
		}

		for (Obstacle o: obstacles) {
			String fileName = "wall_image.png";
			
			if (o.getSymbol() == 'X') {
				fileName = "tombstone_image";
			}
			
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream(fileName)));
			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(o.getX() * enlargementFactor);
			i.setY(o.getY() * enlargementFactor);
			
			images.put(i, o);
		}


		// add all objects to group
		Group root = new Group();
		
		for (ImageView i: images.keySet()) {
			root.getChildren().add(i);
		}

		Scene scene = new Scene(root, width + enlargementFactor, height + enlargementFactor + 60);
		KeyFrame frame = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				world1.updateWorld();
				
				
				for (ImageView i: images.keySet()) {
					int newX = images.get(i).getX();
					int newY = images.get(i).getY();
					
					i.setX(newX * enlargementFactor);
					i.setY(newY * enlargementFactor);
				}


			}
		});

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


	public static void main(String[] args) {
		launch(args);

	}

}
