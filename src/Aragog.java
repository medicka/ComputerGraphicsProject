import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class Aragog {
	
	Group aragog;
	double c = 0.08;
	
	public Group create(){
		
		ObjModelImporter importer = new ObjModelImporter();
		importer.read("models/skAragogMesh/skAragogMesh.obj");
		
		aragog = new Group();
		aragog.getChildren().addAll(importer.getImport());
		
		aragog.getTransforms().addAll(new Rotate(-90, Rotate.Y_AXIS), new Translate (0, -3, 0));
		aragog.getTransforms().add(new Scale(c, c, c));
		
		return aragog;
	}

}
