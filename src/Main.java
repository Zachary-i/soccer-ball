import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}
	int shouldMove =0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane root = new GridPane();
		primaryStage.setTitle("Soccer Ball");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
		
		Pane group = makeSquareBallGroup(100,7);
		
		root.add(group, 0, 0);
		group.setTranslateX(300);
		group.setTranslateY(300);
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED,(KeyEvent event)->{

                if (event.getCode() == KeyCode.A) {
                    animateSquareBallMovement(group,0);
                }
                if (event.getCode() == KeyCode.W) {
                    animateSquareBallMovement(group,1);
                }
                if (event.getCode() == KeyCode.D) {
                    animateSquareBallMovement(group,2);
                }
                if (event.getCode() == KeyCode.S) {
                    animateSquareBallMovement(group,3);
                }
            
        });
		
		
	}
	
	public void animateSquareBallMovement(Pane ball, int direction) {
		AnimationTimer animator = new AnimationTimer(){
			  @Override
			  public void handle(long now) {
				  if(shouldMove!=125) {
			    moveSquareBallGroup(ball,direction);
			    shouldMove++;
				  } else {
					  shouldMove=0;
					  this.stop();
				  }
			    }
			};
			animator.start();
	}
	
	public void moveSquareBallGroup(Pane ball, int direction) {
		final float ROT_SPEED =.004f;
		for(Node p: ball.getChildren()) {
			float[] data= (float[])p.getUserData();
			switch(direction) {
			case 0:
				data[1]+=ROT_SPEED;
				data[3]-=ROT_SPEED;
				if(data[1]-data[3]>data[5]/2) {data[1]-=data[5];}
				break;
			case 1:
				data[0]+=ROT_SPEED;
				data[4]-=ROT_SPEED;
				if(data[0]-data[4]>data[5]/2) {data[0]-=data[5];}
				break;
			case 2:
				data[1]-=ROT_SPEED;
				data[3]+=ROT_SPEED;
				if(data[3]-data[1]>data[5]/2) {data[1]+=data[5];}
				break;
			case 3:
				data[0]-=ROT_SPEED;
				data[4]+=ROT_SPEED;
				if(data[4]-data[0]>data[5]/2) {data[0]+=data[5];}
				break;
			
			}
			float[] data1=data.clone(); data1[1]++;
			float[] data2=data.clone(); data2[0]++; data2[1]++;
			float[] data3=data.clone(); data3[0]++;
			((Polygon) p).getPoints().setAll(new Double[]{
					(double) calculateBallPointPosition(data)[0], (double) calculateBallPointPosition(data)[1],
					(double) calculateBallPointPosition(data1)[0], (double) calculateBallPointPosition(data1)[1],
					(double) calculateBallPointPosition(data2)[0], (double) calculateBallPointPosition(data2)[1],
					(double) calculateBallPointPosition(data3)[0], (double) calculateBallPointPosition(data3)[1]});;
			
		}
	}
	
	public Pane makeSquareBallGroup(float sideLengthScaler, int gridSize) {
		Pane ball = new Pane();
		float[] origin = new float[] {gridSize/2f,gridSize/2f};
		float[][][] points = new float[gridSize+1][gridSize+1][2];
		for(int i=0; i<=gridSize;i++) {
			for(int j=0; j<=gridSize;j++) {
				float[] pos = calculateBallPointPosition(i,j,sideLengthScaler,origin[0],origin[1]);
				points[i][j][0]=pos[0];
				points[i][j][1]=pos[1];
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
				quad.setUserData(new float[] {i,j, sideLengthScaler, origin[0], origin[1], gridSize});
			}
		}
		
		return ball;
	}
	
	public float[] calculateBallPointPosition(float i, float j, float sideLengthScaler, float originX, float originY) {
		float[] pos = new float[2];
		final float number =1.13f;
		float dist=(j-originX)*(j-originX)+(i-originY)*(i-originY);
		dist = (float) Math.sqrt(dist);
		pos[0]=(float) (originX+((j-originX)*sideLengthScaler*(1/Math.pow(number, Math.pow(dist, 1.3)))));
		pos[1]=(float) (originY+((i-originY)*sideLengthScaler*(1/Math.pow(number, Math.pow(dist, 1.3)))));
		return pos;
	}
	
	public float[] calculateBallPointPosition(float[] data) {
		return calculateBallPointPosition(data[0],data[1],data[2],data[3],data[4]);
	}

}
