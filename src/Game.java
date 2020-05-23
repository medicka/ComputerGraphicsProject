
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Game extends Application{
	
	Group potter;
	PerspectiveCamera camera;
	Text text2;
	Text text5;
	Scene scene;
	double x;
	AudioClip song;
	AudioClip win;
	AudioClip lose1;
	AudioClip lose2;
	
	double potterScale = 0.3;
	double velocity = 60;
	int position = 0;
    int distance = 40;
    int points = 0;
    int counter = 0;
    double pow = 1.55;
    boolean colectedPoint = false;
    boolean won = false;
    
	Matrica m = new Matrica();
	Matrica.Polje [][] matrix = m.create();
	
	double timeStart = Double.NaN;
	
	
	AnimationTimer animationTimerPlayer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			double timeNow = now * 1e-9;   
			
			if (Double.isNaN(timeStart)) {   
				timeStart = timeNow;
			}
			
		    double y = 0;
		    double z = 0;
			double time = timeNow - timeStart;
			potter.setRotationAxis(Rotate.Y_AXIS);
			potter.setRotate(180);
			
			Point3D a = new Point3D(x, y, z); 
		   
		    z = a.getZ()+(velocity * Math.pow(time, pow));
		    position = (int)((z-a.getZ()) / distance);
		   
		    if (position > Matrica.LENGTH-5){
				if(x == -1){
					y = -1;
					song.stop();
					won = true;
					win.play();
					}
				else{
					smash();
					lose1.play();
				}
			}
		    
		    Point3D b = new Point3D(x*(30), y*(30), z); 
		    Point3D c = new Point3D(0, 0, z);
		   
		    potter.getTransforms().setAll(new Translate(b.getX(), b.getY(), -b.getZ())//, new Rotate(40*time, Rotate.Y_AXIS)
		    		);
			potter.getTransforms().addAll(new Scale(potterScale, potterScale, potterScale), new Translate(-20, 25, 10) );
			
			camera.getTransforms().setAll(new Translate(c.getX(), c.getY(), c.getZ()));
			camera.getTransforms().add(new Translate (0, -120, -150));
			camera.getTransforms().add(new Rotate(-20, Rotate.X_AXIS));
	
			text2.setText(Integer.toString(points));
			text5.setText(Integer.toString(counter));
			
			
			if (position == Matrica.LENGTH-1 || matrix[Math.abs((int)x)][position].full){
				if ( matrix[Math.abs((int)x)][position].snitch){
					matrix[Math.abs((int)x)][position].content.getChildren().clear();
					matrix[Math.abs((int)x)][position].snitch = false;
					matrix[Math.abs((int)x)][position].full = false;
					points +=150;
					counter++;
					
				}
				else{
					smash();
					if (!won){
						lose2.play();
					}	
				}
			}	
		 	
		}
	};
	
	private void moveRight(){
    	 x -= 1;
    	 if (Math.abs(x)>2){
    		 x += 1;
    	 }
    }
	private void moveLeft(){
		x += 1;
		if (x > 0){
			x -= 1;
   	    }
	}
	
	private void smash (){
		animationTimerPlayer.stop();
		song.stop();
		lose1.stop();
		
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		/* ====== Skybox ========================================================================== */
		
		SkyBox sb = new SkyBox();
		Group skybox = sb.create();
		
		Group root3D = new Group(skybox);
		
		/* ====== Igrac ========================================================================== */
		
		Potter p = new Potter();
		potter = p.create();
		
		root3D.getChildren().add(potter);
		
		/* ====== Staza ========================================================================== */
	
		for (int j = 0; j < Matrica.LENGTH; j++){
			   for (int i = 0; i < Matrica.WIDTH ; i++){
				   root3D.getChildren().addAll(matrix[i][j].map, matrix[i][j].content);
				   
			   }
		    }
		HagridsHut hh = new HagridsHut();
		Group hut = hh.create();
		int hutZ = (int)matrix[1][Matrica.LENGTH-1].point.getZ();
		hut.setTranslateX(matrix[1][Matrica.LENGTH-1].point.getX());
		hut.setTranslateZ(hutZ + 35);
		
		root3D.getChildren().add(hut);
		
		/* ====== Svetla ========================================================================== */
		
		AmbientLight lightA = new AmbientLight(Color.hsb(0, 0, 0.15));
		PointLight lightP1 = new PointLight(Color.hsb(45, 1, 0.7));
		PointLight lightP2 = new PointLight(Color.hsb(25, 1, 0.8));
		PointLight lightP3 = new PointLight(Color.hsb(240, 0.5, 0.25));
		PointLight lightP4 = new PointLight(Color.hsb(240, 0.75, 1));
		PointLight lightP5 = new PointLight(Color.hsb(240, 0.75, 0.2));
		PointLight lightP6 = new PointLight(Color.hsb(240, 0.75, 1));
		lightP1.getTransforms().add(new Translate(200, -200, -100));
		lightP2.getTransforms().add(new Translate(200, -200, 600));
		lightP3.getTransforms().add(new Translate(100, -200, 5900));
		lightP4.getTransforms().add(new Translate(100, -200, 1100));
		lightP5.getTransforms().add(new Translate(-100, -200, 32500));
		lightP6.getTransforms().add(new Translate(-100, -200, 4000));
		
		root3D.getChildren().addAll(lightA, lightP1, lightP2, lightP3, lightP4, lightP5, lightP6);
		
		root3D.setTranslateX(-30);
		
		/* ====== HUD ========================================================================== */
		
		Group rootHud = new Group();
		
		Font font = new Font(30);
		Text text1 = new Text("Points:");
		text2 = new Text("");
		Text text3 = new Text("Sniches:");
		Text text4 = new Text(" from " + Matrica.numbSniches);
		text5 = new Text("");
		
		text1.setFont(font);
		text2.setFont(font);
		text3.setFont(font);
		text4.setFont(font);
		text5.setFont(font);
		
		text1.setFill(Color.WHITE);
		text2.setFill(Color.CORAL);
		text3.setFill(Color.WHITE);
		text4.setFill(Color.CORAL);
		text5.setFill(Color.CORAL);
		
		text1.getTransforms().add(new Translate (0, 40, 0));
		text2.getTransforms().add(new Translate (20, 70, 0));
		text3.getTransforms().add(new Translate (0, 100, 0));
		text4.getTransforms().add(new Translate (50, 130, 0));
		text5.getTransforms().add(new Translate (20, 130, 0));
		
		rootHud.getChildren().addAll(text1, text2, text3, text4, text5);
		
		/* ====== Camera ========================================================================== */

		camera = new PerspectiveCamera(true);
		camera.setFieldOfView(45);
		camera.setFarClip(20000);
		
		/* ====== Sound ========================================================================== */
		
		song = new AudioClip(this.getClass().getResource("song.mp3").toExternalForm());
		song.play();
		
		win = new AudioClip(this.getClass().getResource("win.mp3").toExternalForm());
		lose1 = new AudioClip(this.getClass().getResource("YouSuck.mp3").toExternalForm());
		lose2 = new AudioClip(this.getClass().getResource("GameOverSound.mp3").toExternalForm());
		/* ====== SubScene ======================================================================== */
		
		SubScene scene3D = new SubScene (root3D, 1600, 1000, true, SceneAntialiasing.BALANCED);
		scene3D.setFill(Color.hsb(0, 0, 0.1));
		scene3D.setCamera(camera);
		
		SubScene sceneHud = new SubScene (rootHud, 1600, 1000, false, SceneAntialiasing.BALANCED);
		
		/* ====== Scene ========================================================================== */
		
		Group root = new Group(scene3D, sceneHud);
		scene = new Scene(root);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode().toString() == "RIGHT"){
					moveRight();
					
				}
				else if (event.getCode().toString() == "LEFT"){
					moveLeft();
				}
				else{
					return;
				}
			}
	    	
	    });
		scene.setCursor(Cursor.NONE);
		

		/* ====== Stage ========================================================================== */

		stage.setScene(scene);
		stage.setTitle("Potter Run");
		stage.show();

		animationTimerPlayer.start();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch();

	}
	

}
