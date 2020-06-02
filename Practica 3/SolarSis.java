import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class SolarSis {	
	public SolarSis(){	 
	//creamos el Grupo
		BranchGroup group = new BranchGroup();
	//crear Apariencias
		Appearance appsol = new Appearance();
		Appearance appearth = new Appearance();
		Appearance appMarte = new Appearance();
		Appearance appJupiter = new Appearance();
		Appearance appSaturno = new Appearance();
		Appearance appUrano = new Appearance();
	//Cargar una textura a partir del archivo de una imagen y poner la textura en la aparienica
		TextureLoader tex=new TextureLoader("TIERRA.JPG", null);
			appearth.setTexture(tex.getTexture());
		tex=new TextureLoader("SOL.JPG", null);
			appsol.setTexture(tex.getTexture());
		tex=new TextureLoader("MARTE.JPG", null);
			appMarte.setTexture(tex.getTexture());
		tex=new TextureLoader("JUPITER.JPG", null);
			appJupiter.setTexture(tex.getTexture());
		tex=new TextureLoader("Saturno.JPG", null);
			appSaturno.setTexture(tex.getTexture());
		tex=new TextureLoader("Urano.JPG", null);
			appUrano.setTexture(tex.getTexture());
	//crear una esfera con el radio y la apariencia correspondiente al planeta
		Sphere earth = new Sphere(0.045f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 32, appearth);
		Sphere sol = new Sphere(0.35f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 32, appsol);
		Sphere Marte = new Sphere(0.023f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 32, appMarte);
		Sphere Jupiter = new Sphere(0.18f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 32, appJupiter);
		Sphere Saturno = new Sphere(0.20f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 32, appSaturno);
		Sphere Urano = new Sphere(0.22f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 32, appUrano);
		
	//rotar la esfera sobre su propio eje a la velocidad  correspondiente al planeta (duración del día) 
		TransformGroup earthRotXformGroup = Posi.rotate(earth, new Alpha(-1, 1250));
		TransformGroup MarteRotXformGroup = Posi.rotate(Marte, new Alpha(-1, 1292));
		TransformGroup JupiterRotXformGroup = Posi.rotate(Jupiter, new Alpha(-1, 507));
		TransformGroup solRotXformGroup = Posi.rotate(sol, new Alpha(-1, 1250));
		TransformGroup SaturnoRotXformGroup = Posi.rotate(Saturno, new Alpha(-1, 1250));
		TransformGroup UranoRotXformGroup = Posi.rotate(Urano, new Alpha(-1, 708));
	//	alejar la esfera del sol (la posición del sol es el origen)
		TransformGroup earthTransXformGroup = Posi.translate(earthRotXformGroup, new Vector3f(0.0f, 0.0f, 0.7f));
		TransformGroup MarteTransXformGroup = Posi.translate(MarteRotXformGroup, new Vector3f(0.0f, 0.0f, 1.06f));
		TransformGroup JupiterTransXformGroup = Posi.translate(JupiterRotXformGroup, new Vector3f(0.0f, 0.0f, 3.64f));
		TransformGroup SaturnoTransXformGroup = Posi.translate(SaturnoRotXformGroup, new Vector3f(0.0f, 0.0f, 4.00f));
		TransformGroup UranoTransXformGroup = Posi.translate(UranoRotXformGroup, new Vector3f(0.0f, 0.0f, 4.64f));
	// rotar la esfera alrededor del sol a la velocidad  correspondiente al planeta (duración del año).
		TransformGroup earthRotGroupXformGroup = Posi.rotate(earthTransXformGroup, new Alpha(-1, 2500));
		TransformGroup MarteRotGroupXformGroup = Posi.rotate(MarteTransXformGroup, new Alpha(-1, 4705));
		TransformGroup JupiterRotGroupXformGroup = Posi.rotate(JupiterTransXformGroup, new Alpha(-1, 6125));
		TransformGroup SaturnoRotGroupXformGroup = Posi.rotate(SaturnoTransXformGroup, new Alpha(-1, 7500));
		TransformGroup UranoRotGroupXformGroup = Posi.rotate(UranoTransXformGroup, new Alpha(-1, 8200));
	//agregarla al BranchGroup
	    	group.addChild(earthRotGroupXformGroup);
	    	group.addChild(MarteRotGroupXformGroup);
	    	group.addChild(JupiterRotGroupXformGroup);
	    	group.addChild(solRotXformGroup);
			group.addChild(SaturnoRotGroupXformGroup);
			group.addChild(UranoRotGroupXformGroup);

	    	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	    	Canvas3D canvas = new Canvas3D(config); 
	    	canvas.setSize(400, 400);
	    	SimpleUniverse universe = new SimpleUniverse(canvas);
	    	
	    	universe.getViewingPlatform().setNominalViewingTransform();
	    	universe.addBranchGraph(group);  
	    	
	    	JFrame f = new JFrame("Planetario, Sistema Solar");
	    	
	    	f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	    	f.add(canvas); 
	    	f.pack(); 
	    	f.setVisible(true); 
    	}
	public static void main(String a[]) { 
		new SolarSis();
	}
}                                                                         
