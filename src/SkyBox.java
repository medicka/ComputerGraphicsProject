import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class SkyBox {
	
	Group skybox;
	private String url = "sky3.png";
	
	public Group create(){
		Image image = new Image(url);
		int d = (int) image.getWidth() / 4;
		
		int n = 6;
		Point3D y = Rotate.Y_AXIS;
		Point3D x = Rotate.X_AXIS;
		//                 front,   back,   left,  right,    top, bottom
		int     px  [] = {     1,      3,      0,      2,      1,      1};
		int     py  [] = {     1,      1,      1,      1,      0,      2};
		int     rot [] = {     0,      2,      3,      1,      1,      3};
		Point3D axis[] = {     y,      y,      y,      y,      x,      x}; 
		
		skybox = new Group();
		for (int i = 0; i < n; i++) {
			WritableImage wi = new WritableImage(image.getPixelReader(), px[i]*d, py[i]*d, d, d);
			ImageView iv = new ImageView(wi);
			iv.setFitWidth(1);
			iv.setFitHeight(1);
			iv.getTransforms().setAll(
					new Rotate(90 * rot[i], axis[i]),
					new Translate(-0.5, -0.5, 0.5)
					);
			skybox.getChildren().add(iv);
		}		

		double c = 10000;
		skybox.getTransforms().add(new Scale(c, c, c));
		skybox.getTransforms().add(new Rotate(30, Rotate.Y_AXIS));
		skybox.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));
		
		return skybox;
	}

}
