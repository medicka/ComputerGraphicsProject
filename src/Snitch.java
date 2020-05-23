import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class Snitch {
	
	 Group snitch;
	    double c = 0.7;
		
		
		public Group create(){
			
			ObjModelImporter importer = new ObjModelImporter();
			importer.read("models/Snitch/Snitch.obj");
			
			snitch = new Group();		
			snitch.getChildren().addAll(importer.getImport());
			snitch.getTransforms().addAll(new Scale(c, c, c), new Translate(0, -20, 0));
			
			
			return snitch;
		}

}
