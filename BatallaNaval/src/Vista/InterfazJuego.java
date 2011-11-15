package Vista;


import Logica.Servidor;

import java.net.*;
import java.io.*;
import java.awt.* ;
import javax.swing.* ;
import java.awt.event.* ;

public class InterfazJuego extends JFrame
	{
	private GridBagConstraints configuracionCapa = new GridBagConstraints();
	private Image img;
	private JMenu menuArchivo,menuAyuda ;
	private JMenuBar barra ;
	private JMenuItem itemConectar;
	private final int JUGADOR_X = 0, JUGADOR_O = 1;
	private ServerSocket servidor;
	private JTextArea areaSalida;
	
	private EventoConexion eventoConexion = new EventoConexion() ;
	
	static GraphicsDevice grafica = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	public InterfazJuego()
		{
		super(" Panel Principal ") ;
		Container contenedor = getContentPane();
		GridLayout areaInterfaz = new GridLayout(2,1,1,1);
		contenedor.setLayout(areaInterfaz);
		
		TableroJuego tableroJuego = new TableroJuego() ;
		TableroAtaques tableroAtaques = new TableroAtaques() ;
		
		contenedor.add(tableroAtaques);		
		contenedor.add(tableroJuego);
		
		JMenu menuArchivo = new JMenu( "Archivo" );
		menuArchivo.setMnemonic( 'A' );
  
		JMenuBar barra = new JMenuBar();
		setJMenuBar( barra );
		barra.add( menuArchivo ); 
		
		itemConectar= new JMenuItem( "Conectar con servidor");
		itemConectar.setMnemonic( 'C' );
		itemConectar.addActionListener(eventoConexion);
		
	    menuArchivo.add( itemConectar );
	    
		//grafica.setFullScreenWindow(this);
		setVisible(true);
		setUndecorated(true);
		setSize(800,700);
		}
	
	  
	private class EventoConexion implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			JOptionPane.showInputDialog("Ingrese el numero de la direccion ip a la cual desea conectarse") ;	
			}		
		}
	
	public static void main(String args[])
		{
		InterfazJuego interfaz = new InterfazJuego() ;
		interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}