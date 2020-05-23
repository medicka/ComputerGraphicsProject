import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;

public class Potter  {
	
	private Group harry;
	
	public Group create(){
		
		ObjModelImporter importer = new ObjModelImporter();
		importer.read("models/harryPotter/HarryPotter(onabroomstick).obj");
		
		harry = new Group();		
		harry.getChildren().addAll(importer.getImport());
		
		return harry;
	}

}
