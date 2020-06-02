import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Topos extends JFrame implements ActionListener{
	JButton botones[];
	JLabel punt;
	double r;
	int m[];
	int cta=0;
	public Topos(){
		super("Topos");
		botones=new JButton[20];
		m=new int[20];
		punt=new JLabel("Puntuacion: 0");
		setLayout(new GridLayout(5,5));
		for(int i=0; i<botones.length; i++){
			r=Math.random();
			if(r<0.5){
				m[i]=1;
				add(botones[i]=new JButton(new ImageIcon("Tapa.jpg")));
				botones[i].addActionListener(this);
				botones[i].setEnabled(true);
			}
			if(r>=0.5){
				m[i]=0;
				add(botones[i]=new JButton(new ImageIcon("topo.jpg")));
				botones[i].addActionListener(this);
				botones[i].setEnabled(true);
			}	
		}
		add(punt);
		hilo TOPO = new hilo();
		TOPO.start();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}
	public class hilo extends Thread{
    	public void run(){		
			while(true){
				try {
					Thread.sleep(3000);
					for(int i=0; i<botones.length; i++){
					r=Math.random();
					if(r<0.5){
						m[i]=1;
						botones[i].setIcon(new ImageIcon("Tapa.jpg"));
						botones[i].setEnabled(true);
					}
					if(r>=0.5){
						m[i]=0;
						botones[i].setIcon(new ImageIcon("Topo.jpg"));
						botones[i].setEnabled(true);
					}	
		  		}
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}	
				
			}
		}
	}
  	public void actionPerformed(ActionEvent e){
		JButton btn=(JButton)e.getSource();
		for(int j=0; j<botones.length; j++){
			if(btn==botones[j]){
				if(m[j]==0){
					botones[j].setIcon(new ImageIcon("pegatopo.jpg"));
					botones[j].setEnabled(false);
					cta ++;
					punt.setText("Puntuacion: "+cta);
				}
			}
		}
	}
	public static void main(String s[]){
			new Topos();
	}
}
