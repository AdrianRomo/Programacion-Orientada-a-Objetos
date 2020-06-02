import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
public class Login extends JPanel implements LeeRed, ActionListener {	
	JButton bconecta;
	JTextField tf;
	Red r;
	JLabel lblEscribe;
	
	public Login(){
		JPanel p1, p2;
		p1=new JPanel(); p2=new JPanel();
		p1.setLayout(new GridLayout(4,1));
		p2.setLayout(new BorderLayout());	

		bconecta=new JButton("Conecta");
		bconecta.addActionListener(this);  
		
		lblEscribe=new JLabel("Escribe tu nombre de Usuario");
		
		tf=new JTextField(15);    

		p1.add(lblEscribe);
		p1.add(tf);
		p1.add(bconecta);

		add(p1, BorderLayout.NORTH);
	} 
	
	public static void main(String args[]){
		JFrame f=new JFrame("Login");
		f.add("Center", new Login());
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
    f.setSize(250, 250); f.setVisible(true);	
	}      
	
	public void leeRed(Object obj){
		if(obj!=null){
			UserSession u=(UserSession)obj;
    			System.out.println("Usuario: "+u.getUser()+" " + u.getLoginTime());  
		}
      else System.out.println("No hay usuarios registrados");              	
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton btn=(JButton)e.getSource();
    		if(btn==bconecta) {
			r=new Red(this);
			UserSession c=new UserSession();
			c.setUser(tf.getText());
   			c.setLoginTime(new Date());                     
			r.escribeRed(c);
			tf.setEnabled(false);
		}
	}
	
}
