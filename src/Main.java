import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane root = new GridPane();
		primaryStage.setTitle("Soccer Ball");
		primaryStage.setScene(new Scene(root, 400, 300));
		primaryStage.show();
		
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
		    0.0, 0.0,
		    0.0, 50.0,
		    50.0, 50.0,
		    50.0, 0.0});
		polygon.setFill(new Color(.5,1,.5,1));
		polygon.setStroke(Color.BLACK);
		root.add(polygon, 5, 5);
		polygon.setTranslateX(150);
		polygon.setTranslateY(150);
		
		
	}
	
	public Pane makeSquareBallPane(float sideLength, int gridSize) {
		Pane ballPane = new Pane();
		float[] origin = new float[] {gridSize/2f,gridSize/2f};
		float[][][] points = new float[gridSize][gridSize][2];
		for(int i=0; i<=gridSize;i++) {
			for(int j=0; j<=gridSize;j++) {
				float dist=(j-origin[0])*(j-origin[0])+(i-origin[1]*(i-origin[1]));
				points[i][j][0]=5;
			}
		}
		
		return ballPane;
	}

}
