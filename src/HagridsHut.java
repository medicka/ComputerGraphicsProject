import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class HagridsHut {
	
	    Group hut;
	    double c = 15;
		
		public Group create(){
			
			ObjModelImporter importer = new ObjModelImporter();
			importer.read("models/ARCHITECTURE_Hagrids Hut/ARCHITECTURE_Hagrids Hut.obj");
			
			hut = new Group();		
			hut.getChildren().addAll(importer.getImport());
			hut.getTransforms().addAll(new Scale(c, c, c), new Translate(0, -1, 0));
			
			return hut;
		}


}
