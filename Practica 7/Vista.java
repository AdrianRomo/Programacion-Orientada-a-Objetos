
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.table.*;
import java.sql.*;
//import javax.swing.table.DefaultTableCellRenderer;
/***********************************************************************************
Applet que:
  -Muestra cualquier base de datos y tabla (SELECT) que se le indique en el panel de formulario
  -Actualiza la vista (vuelve a ejecutar la sentencia)

En el panel de formulario (pnlFormulario) el usuario introduce el host, base de datos, etc.
Al pulsar "Conexion" se muestra el panel del JTable (pnlConsulta).

Se aplica el patron modelo-vista. El modelo es responsable de tratar los datos y
la vista del control de interfaz y eventos.

SI no compila ejecutar
CLASSPATH=$CLASSPATH:/usr/share/java/mysql.jar export CLASSPATH

************************************************************************************/
public class Vista extends JApplet implements java.awt.event.ActionListener {
    JPanel pnlRaiz;                // Panel principal
    JTabbedPane pnlTab;            // Panel contenedor de pestaias

    //// Panel de formulario
    JPanel pnlFormulario;
    JComboBox cbbDriver;
    JComboBox cbbHost;
    
    JTextField txtBasedatos;
    JTextField txtTabla;
    JTextField txtLogin;
    JTextField txtPwd;
    JButton btnConexion;
    String BOTON_CONEXION = "conexion";

    //// Panel de consulta de datos
    JPanel pnlConsulta;
    JPanel pnlTablas; 
    JPanel pnlista;
    JTable tabla;
    JButton btnActualizar;
    String BOTON_ACTUALIZAR = "actualizar"; 
	
	//Panel de Tablas
	JComboBox cbbTablas;
	JPanel pnlFila1;
	JLabel lblTablas;
    
    static Frame f;
    modelo mod;
    private Connection con;        
                             // modelo de datos
    JTableHeader header;
	ImageIcon imageIcon;
    public void init() {
      f.addWindowListener(new WindowHandler());
	try {

	    //// Obtengo y configuro layout de panel raiz
	    pnlRaiz = (JPanel)this.getContentPane();
	    pnlRaiz.setLayout(new BorderLayout());

	    //// Le asigno un TABBEDPANE
	    pnlTab = new JTabbedPane();
	    pnlRaiz.add(pnlTab, BorderLayout.CENTER);

	    //// Crea un modelo vacio (no hay conexion a la base de datos todavia)
	    mod = new modelo();

            //// Creo el panel de formulario y lo asigno al Tabbed
	    pnlFormulario = crearPanelFormulario();
	    pnlTab.addTab("Conexion", pnlFormulario );

	    //// Creo el panel de consulta y lo asigno al Tabbed
	    pnlConsulta = crearPanelConsulta( mod );
	    pnlTab.addTab( "Consulta", pnlConsulta );
	    ///////fggfgfgfgfgfgfgfgfgfgfgfgfg
	    pnlista = crearPanelTablas();
	    pnlTab.addTab("LTablas", pnlista);
	   
	    
	}
	catch ( Exception e ) {
	    e.printStackTrace();
	}
    }

    private  JPanel crearPanelFormulario() {
	JPanel pnlF = new JPanel();
	pnlF.setLayout( new BorderLayout() );

	JPanel pnlSuperior = new JPanel();
	pnlSuperior.setLayout( new BoxLayout( pnlSuperior, BoxLayout.PAGE_AXIS));

	JPanel pnlFila1 = new JPanel( new FlowLayout( FlowLayout.LEFT));
	JLabel lblDriver = new JLabel( "Driver:" );
	Vector drivers = new Vector();
	drivers.add("com.mysql.jdbc.Driver");     // Local
	drivers.add("org.gjt.mm.mysql.Driver");   // Remoto
	cbbDriver = new JComboBox( drivers );
	pnlFila1.add( lblDriver );
	pnlFila1.add( cbbDriver );
	pnlSuperior.add( pnlFila1 );

	JPanel pnlFila2 = new JPanel(new FlowLayout( FlowLayout.LEFT));
	JLabel lblHost = new JLabel( "Host:" );
	Vector hosts = new Vector();
        hosts.add("jdbc:mysql://localhost:3306/");     // Local
        hosts.add("jdbc:mysql:///");     // Local
		hosts.add("jdbc:mysql://proactiva-calidad.com:3306/");   // Remoto
	cbbHost = new JComboBox( hosts );
	pnlFila2.add( lblHost );
	pnlFila2.add( cbbHost );
	pnlSuperior.add( pnlFila2 );

	JPanel pnlFila3 = new JPanel(new FlowLayout( FlowLayout.LEFT));
JLabel lblBasedatos = new JLabel( "Base de datos:" );
	txtBasedatos = new JTextField("classicmodels");
	pnlFila3.add( lblBasedatos );
	pnlFila3.add( txtBasedatos );
	pnlSuperior.add( pnlFila3 );

	JPanel pnlFila4 = new JPanel(new FlowLayout( FlowLayout.LEFT));
	JLabel lblTabla = new JLabel( "Tabla:" );
	txtTabla = new JTextField(10);
	txtTabla.setText("customers");
	pnlFila4.add( lblTabla );
	pnlFila4.add( txtTabla );
	pnlSuperior.add( pnlFila4 );

	JPanel pnlFila5 = new JPanel(new FlowLayout( FlowLayout.LEFT));
	JLabel lblLogin = new JLabel( "Login:" );
	txtLogin = new JTextField("root");
	pnlFila5.add( lblLogin );
	pnlFila5.add( txtLogin );
	pnlSuperior.add( pnlFila5 );

	JPanel pnlFila6 = new JPanel(new FlowLayout( FlowLayout.LEFT));
	JLabel lblPwd = new JLabel( "Password:" );
	txtPwd = new JTextField("n0m3l0");


	pnlFila6.add( lblPwd );
	pnlFila6.add( txtPwd );
	pnlSuperior.add( pnlFila6 );

	btnConexion = new JButton( "Pulse aqui para la conexion" );
	btnConexion.setActionCommand( BOTON_CONEXION );
	btnConexion.addActionListener( this );

	pnlF.add( pnlSuperior, BorderLayout.CENTER );
	pnlF.add( btnConexion, BorderLayout.SOUTH );
	return pnlF;
    }

    /************************************************************************
     * Crea el panel de consulta, que contiene un JTable
     ***********************************************************************/
    private JPanel crearPanelConsulta( modelo mod) {
	JPanel pnlCon = new JPanel();
	pnlCon.setLayout( new BorderLayout() );

	tabla = new JTable(mod);
	JScrollPane pnlScroll = new JScrollPane( tabla );
	btnActualizar = new JButton( "Pulse aqui para actualizar la vista");
	btnActualizar.setActionCommand( BOTON_ACTUALIZAR );
	btnActualizar.addActionListener( this );

	pnlCon.add( pnlScroll, BorderLayout.CENTER);
	pnlCon.add( btnActualizar, BorderLayout.SOUTH);
	return pnlCon;
    }

    private JPanel crearPanelTablas() {
	 pnlTablas = new JPanel();
	 pnlTablas.setLayout( new BorderLayout() );
	 pnlFila1 = new JPanel( new FlowLayout( FlowLayout.LEFT));
	 lblTablas = new JLabel( "Tablas:" );
	
	
	pnlFila1.add( lblTablas );
	cbbTablas = new JComboBox();
		pnlFila1.add( cbbTablas );
		
        pnlTablas.add( pnlFila1, BorderLayout.CENTER );
	return pnlTablas;
    }


    /************************************************************************
     * Recepcion de evento de pulsacion de boton
     ***********************************************************************/
    public void actionPerformed(ActionEvent e) {

	//// Boton de conexion
	if ( e.getActionCommand().compareTo( BOTON_CONEXION ) == 0 ) {
	    String hostYbase = (String)cbbHost.getSelectedItem() + txtBasedatos.getText();
	    if ( !mod.conexion( (String)cbbDriver.getSelectedItem(), hostYbase, txtTabla.getText(),txtLogin.getText(), txtPwd.getText() ) ) 			{
			JOptionPane.showMessageDialog( null, mod.obt_mensaje_error(),"Error", JOptionPane.ERROR_MESSAGE);
	   	 }
	    //// Si la conexion ok, cambio a panel de consulta
	    else {
	    	cbbTablas.removeAllItems();
		mod.fireTableStructureChanged();
		pnlTab.setSelectedComponent( pnlConsulta );
                tabla.setRowHeight(40);
                
    //tabla.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
              
		header = tabla.getTableHeader();
		header.setBackground(Color.yellow);                
		mod.listarTablas(cbbTablas);
		
	    }
	}
	//// Boton de actualizar
	if ( e.getActionCommand().compareTo( BOTON_ACTUALIZAR ) == 0 ) {
	    if ( !mod.actualizar( txtTabla.getText() ) )
		JOptionPane.showMessageDialog( null, mod.obt_mensaje_error(),"Error", JOptionPane.ERROR_MESSAGE);
	}
    }
    public class WindowHandler extends WindowAdapter {
  		public void windowClosing(WindowEvent e) {
   		      System.exit(0);
  		}
    }
public static void main(String args[]){
	f=new Frame("JDBC TABLE EXAMPLE");
	Vista v=new Vista();
	v.init();
	v.start();	
	f.add(v, BorderLayout.CENTER);
	f.setSize(700, 500);
	f.setVisible(true);
}

/*
class ImageRenderer extends DefaultTableCellRenderer {
  JLabel lbl = new JLabel();

  ImageIcon icon = new ImageIcon(getClass().getResource("rings.jpg"));

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    lbl.setText((String) value);
    lbl.setIcon(icon);
    return lbl;
  }
*/
public class iconRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object obj,boolean isSelected, boolean hasFocus, int row, int column) {
		txtIcon i = (txtIcon)obj;
            	setIcon(i.imageIcon); setText(i.txt);
            	setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            	setHorizontalAlignment(JLabel.CENTER);
            	return this;
        }
    }
public class txtIcon {
	String txt;
	ImageIcon imageIcon;
	txtIcon(String text, ImageIcon icon) {
		txt = text;
            	imageIcon = icon;
        }
  }


public Connection getConexion(){
           return con;
    }
}
