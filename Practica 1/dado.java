import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.awt.*;

public class dado extends JApplet implements ActionListener {
	
	JButton jugar;
	JLabel mensaje, jl1, jl2;
	ImageIcon imas[],imas2[];
	Container c;

	public dado(){}

	public void init() {
		c = getContentPane();
		//imagenes en el arreglo para el primer dado (negro)
		imas = new ImageIcon [7];
		imas[0] = new ImageIcon("uno.jpg");
		imas[1] = new ImageIcon("dos.jpg");
		imas[2] = new ImageIcon("tres.jpg");
		imas[3] = new ImageIcon("cuatro.jpg");
		imas[4] = new ImageIcon("cinco.jpg");
		imas[5] = new ImageIcon("seis.jpg");
		imas[6] = new ImageIcon("uno.jpg");
        //imagenes en el arreglo para el segundo dado (blanco)
        imas2 = new ImageIcon [7];
        imas2[0] = new ImageIcon("uno2.jpg");
        imas2[1] = new ImageIcon("dos2.jpg");
        imas2[2] = new ImageIcon("tres2.jpg");
        imas2[3] = new ImageIcon("cuatro2.jpg");
        imas2[4] = new ImageIcon("cinco2.jpg");
        imas2[5] = new ImageIcon("seis2.jpg");
        imas2[6] = new ImageIcon("uno2.jpg");
		//Imagenes de los dados
		jl1 = new JLabel(imas[0]);
    	jl2 = new JLabel(imas2[0]);
    	//Botón
		jugar = new JButton("Jugar");
		jugar.addActionListener(this);
		//Mensaje
		mensaje = new JLabel ("Bienvenido", SwingConstants.CENTER);
		c.add("West",jl1); c.add("Center", jl2);
		c.add("North", mensaje); c.add("South", jugar);
	}
    
	public void actionPerformed(ActionEvent e){
		int x = (int)(Math.random()*6);
		int y = (int)(Math.random()*6);
        System.out.println("x =" + x + " y =" + y);
        jl1.setIcon(imas[x]);
		jl2.setIcon(imas2[y]);
		if (x + y + 2 == 7)
            mensaje.setText("Ganaste!");
        else
            mensaje.setText("Perdiste :(");
    }
    
    public static void main(String s[]){
        dado d = new dado();
		d.init();
        Frame f = new Frame ("El 7 Ganador");
        f.pack();
        f.setLocationRelativeTo(null);
        f.add("Center", d);
		f.setSize(400,250);
		f.setVisible(true);
	}

}




