import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
	
		
		// make objects circles (different colour for each type of object)
		List<Circle> objects = new ArrayList<Circle>();
		
		for (Bug b: bugs) {
			Circle c = new Circle(b.getX() * enlargementFactor, b.getY() * enlargementFactor, enlargementFactor / 2, Color.RED);
			b.setCircle(c);
			objects.add(c);
		}
		
		for (Plant p: plants) {
			Circle c = new Circle(p.getX() * enlargementFactor, p.getY() * enlargementFactor, enlargementFactor / 2, Color.GREEN);
			objects.add(c);
		}
		
		for (Obstacle o: obstacles) {
			Circle c = new Circle(o.getX() * enlargementFactor, o.getY() * enlargementFactor, enlargementFactor / 2, Color.BLACK);
			objects.add(c);
		}
		
		// add all objects to group
		Group root = new Group();
		root.getChildren().addAll(objects);
		
		for (Bug bug: bugs) {
			Circle circle = bug.getCircle();
			circle.setTranslateX(bug.getX() * enlargementFactor);
			circle.setTranslateY(bug.getY() * enlargementFactor);
			
		}
		
		Scene scene = new Scene(root, width + enlargementFactor/2, height + enlargementFactor/2);
		KeyFrame frame = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent t) {
				world1.updateWorld();
				
				for (Bug bug: bugs) {
					Circle circle = bug.getCircle();
					
					int prevX = bug.getPrevX() * enlargementFactor;
					int curX = bug.getX() * enlargementFactor;
					int prevY = bug.getPrevY() * enlargementFactor;
					int curY = bug.getY() * enlargementFactor;
					
					// circle.setTranslateX(curX - prevX);
					// circle.setTranslateY(curY - prevY);
					
					//System.out.println("" + prevX + " " + curX + ", " + prevY + " " + curY + " ");
					//System.out.println("" + (curX - prevX));

				}
				
					
			}
		});
		
		TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();
	
		primaryStage.setTitle("Hello Animation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void updateObjects() {
		
	}

	public static void main(String[] args) {
		launch(args);

	}

}
