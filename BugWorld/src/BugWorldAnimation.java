import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BugWorldAnimation extends Application {

	int width = 600, height = 600;
	int enlargementFactor = 20;

	@Override
	public void start(Stage primaryStage) throws Exception {


		World world1 = new World(width / enlargementFactor, height / enlargementFactor);

		// get object lists from World
		List<Bug> bugs = world1.getBugs();
		List<Plant> plants = world1.getPlants();
		List<Obstacle> obstacles = world1.getObstacles();


		// make objects squares (different colour for each type of object)
		List<ImageView> images = new ArrayList<ImageView>();

		for (Bug b: bugs) {
			//Circle c = new Circle(b.getX() * enlargementFactor, b.getY() * enlargementFactor, enlargementFactor / 2, Color.RED);
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream("bug_image.png")));
			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(b.getX() * enlargementFactor);
			i.setY(b.getY() * enlargementFactor);

			b.setImage(i);
			images.add(i);
		}

		for (Plant p: plants) {
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream("plant_image.png")));
			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(p.getX() * enlargementFactor);
			i.setY(p.getY() * enlargementFactor);
			images.add(i);
		}

		for (Obstacle o: obstacles) {
			ImageView i = new ImageView(new Image(getClass().getResourceAsStream("wall_image.png")));
			i.setFitWidth(enlargementFactor);
			i.setFitHeight(enlargementFactor);
			i.setX(o.getX() * enlargementFactor);
			i.setY(o.getY() * enlargementFactor);
			images.add(i);
		}

		// add all objects to group
		Group root = new Group();
		root.getChildren().addAll(images);

		Scene scene = new Scene(root, width + enlargementFactor, height + enlargementFactor);
		KeyFrame frame = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				world1.updateWorld();

				for (Bug bug: bugs) {
					ImageView image = bug.getImage();

					int curX = bug.getX() * enlargementFactor;
					int curY = bug.getY() * enlargementFactor;

					 //circle.setTranslateX(circle.getTranslateX() + (curX - prevX));
					 //circle.setTranslateY(circle.getTranslateY() + (curY - prevY));
					image.setX(curX);
					image.setY(curY);

					//circle.setCenterX(curX);
					//circle.setCenterY(curY);

					//System.out.println("" + prevX + " " + curX + ", " + prevY + " " + curY + " ");
					//System.out.println("" + (circle.getTranslateX() + (curX - prevX)));

				}


			}
		});

		TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();

		primaryStage.setTitle("Bug World");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void updateObjects() {

	}

	public static void main(String[] args) {
		launch(args);

	}

}
