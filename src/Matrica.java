
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class Matrica extends Group{
	
	final static int WIDTH = 3;
	final static int LENGTH = 165;
	
	final static int numbSniches = 40;
	final static int numbEnemies = 45;
	
	class Polje{
		
		int x, z;
		Point3D point;
		Group map;
		Group content;
		boolean full;
		boolean around;
		boolean snitch;
		
		Polje (Point3D point, Group map, Group content, int x, int z){
		   this.x = x;
		   this.z = z;
	       this.point = point;
	       this.map = map;	
	       this.content = content;
	       full = false;
	       around = false;
	       snitch = false;
	       }
	}
	
	Polje [][] matrix;
	Group obsticle;
	
	
	public Polje [][] create(){
	    matrix = new Polje [WIDTH][LENGTH];
	    for (int j = 0; j < LENGTH; j++){
			   for (int i = 0; i < WIDTH; i++){
				   int x =i;
				   int z = j;
				   Point3D p = new Point3D(i*30, 0, j*40);
				   Box box = box();
				   box.getTransforms().add(new Translate(i*30, 0, j*40));
				   Group map = new Group(box);
				   Group con = new Group();
				   matrix[i][j] = new Polje (p, map, con, x, z);
			   }
		    }
	    fillMatrix();
	    return matrix;
	    
	}
	
	private Box box(){
		
		Box box = new Box(30, 2, 40);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.DARKGRAY);
		box.setMaterial(mat);
		
		return box;
	}
	
	public void fillMatrix (){
		
		for (int b = 0; b < numbEnemies; b++){
			int i = 0;
			int j = 0;
			while (j ==0 || j == 1 || matrix[i][j].around){
			   i = (int)(WIDTH * Math.random());
			   j = (int)((LENGTH-5) * Math.random());
			}
			
			int enemy = (int) Math.floor(2*Math.random());
			switch(enemy){
			case 0:
				FireCrab fc = new FireCrab();
				obsticle= fc.create();
				break;
			default:
				Aragog a = new Aragog();
				obsticle = a.create();
				break;
			}
			
			obsticle.setTranslateX(matrix[i][j].point.getX());
			obsticle.setTranslateY(matrix[i][j].point.getY());
			obsticle.setTranslateZ(matrix[i][j].point.getZ());
			matrix[i][j].content.getChildren().add(obsticle);
			matrix[i][j].full = true;
			for (int k = 0; k < WIDTH; k++){
				matrix[k][j-1].around = true;
				matrix[k][j].around = true;
				matrix[k][j+1].around = true;
			}
			
		}
		for (int f = 0; f < numbSniches; f++){
			int i = 0;
			int j = 0;
			while (j ==0 || j == 1 || matrix[i][j].full){
			   i = (int)(WIDTH * Math.random());
			   j = (int)((LENGTH-4) * Math.random());
			}
			Snitch sn = new Snitch();
			obsticle = sn.create();
			matrix[i][j].snitch = true;
			obsticle.setTranslateX(matrix[i][j].point.getX());
			obsticle.setTranslateY(matrix[i][j].point.getY());
			obsticle.setTranslateZ(matrix[i][j].point.getZ());
			matrix[i][j].content.getChildren().add(obsticle);
			matrix[i][j].full = true;
		}
		
	}

}
