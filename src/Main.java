import javafx.application.Application;
import javafx.scene.Group;
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
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
		
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
		    0.0, 0.0,
		    0.0, 50.0,
		    50.0, 50.0,
		    50.0, 0.0});
		polygon.setFill(new Color(.5,1,.5,1));
		polygon.setStroke(Color.BLACK);
		//root.add(polygon, 5, 5);
		polygon.setTranslateX(150);
		polygon.setTranslateY(150);
		Pane group = makeSquareBallGroup(100,7);
		
		root.add(group, 0, 0);
		group.setTranslateX(300);
		group.setTranslateY(300);
		
	}
	
	public Pane makeSquareBallGroup(float sideLengthScaler, int gridSize) {
		Pane ball = new Pane();
		final float number =1.092f;
		float[] origin = new float[] {gridSize/2f,gridSize/2f};
		float[][][] points = new float[gridSize+1][gridSize+1][2];
		for(int i=0; i<=gridSize;i++) {
			for(int j=0; j<=gridSize;j++) {
				float dist=(j-origin[0])*(j-origin[0])+(i-origin[1])*(i-origin[1]);
				dist = (float) Math.sqrt(dist);
				points[i][j][0]=(float) (origin[0]+((j-origin[0])*sideLengthScaler*(1/Math.pow(number, Math.pow(dist, 1.4)))));
				points[i][j][1]=(float) (origin[1]+((i-origin[1])*sideLengthScaler*(1/Math.pow(number, Math.pow(dist, 1.4)))));
				System.out.print(i+" "+j+" | "+points[i][j][0]+ ", "+points[i][j][1]+" "+dist+ "\n");
			}
		}
		for(int i=0; i<gridSize;i++) {
			for(int j=0; j<gridSize;j++) {
				Polygon quad = new Polygon();
				quad.getPoints().addAll(new Double[]{
						(double) points[i][j][0], (double) points[i][j][1],
						(double) points[i][j+1][0], (double) points[i][j+1][1],
						(double) points[i+1][j+1][0], (double) points[i+1][j+1][1],
						(double) points[i+1][j][0], (double) points[i+1][j][1],});
				quad.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
				ball.getChildren().add(quad);
			}
		}
		
		return ball;
	}

}
