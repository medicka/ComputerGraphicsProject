import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class FireCrab {
	
	Group fcrab;
	double c = 0.3;
	
	public Group create(){
		
		ObjModelImporter importer = new ObjModelImporter();
		importer.read("models/firecrab/firecrab.obj");
		
		fcrab = new Group();
		fcrab.getChildren().addAll(importer.getImport());
		fcrab.getTransforms().addAll(new Rotate(180, Rotate.Y_AXIS), new Scale(c, c, c), new Translate(0, -10, 20));
		
		return fcrab;
	}

}
