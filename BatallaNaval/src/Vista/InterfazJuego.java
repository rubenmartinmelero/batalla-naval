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
	
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	private String mensaje = "";
	private String servidorChat;
	private Socket cliente;

	private String host ;
	
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
		//setUndecorated(true);
		setSize(800,700);
		}
	
	public void establecerHost(String host)
		{
		this.host = host ;
		}
	
	
	public void ejecutarCliente()
		{
		servidorChat = host; // establecer el servidor al que se va a conectar este cliente
		try 
			{
			conectarAServidor();
			obtenerFlujos();
			}
		catch ( EOFException excepcionEOF ) {
	         System.err.println( "El cliente termino la conexión" );
	      }
	 
	      // procesar los problemas que pueden ocurrir al comunicarse con el servidor
	      catch ( IOException excepcionES ) {
	         excepcionES.printStackTrace();
	      }
	 
	      finally {
	      //   cerrarConexion(); // Paso 4: cerrar la conexión
	      }
		}
	
	private void conectarAServidor() throws IOException
	   {
	      System.out.println( "Intentando realizar conexión\n" );
	 
	      // crear Socket para realizar la conexión con el servidor
	      cliente = new Socket( InetAddress.getByName( servidorChat ), 12345 );
	 
	      // mostrar la información de la conexión
	      System.out.println( "Conectado a: " +
	         cliente.getInetAddress().getHostName() );
	   }
	 
	   // obtener flujos para enviar y recibir datos
	   private void obtenerFlujos() throws IOException
	   {
	      // establecer flujo de salida para los objetos
	      salida = new ObjectOutputStream( cliente.getOutputStream() );
	      salida.flush(); // vacíar búfer de salida para enviar información de encabezado
	 
	      // establecer flujo de entrada para los objetos
	      entrada = new ObjectInputStream( cliente.getInputStream() );
	 
	      System.out.println( "\nSe recibieron los flujos de E/S\n" );
	   }
	 
	   // procesar la conexión con el servidor
	   private void procesarConexion() throws IOException
	   {
	      // habilitar campoIntroducir para que el usuario del cliente pueda enviar mensajes

	 
	      do { // procesar mensajes enviados del servidor
	 
	         // leer mensaje y mostrarlo en pantalla
	         try {
	            mensaje = ( String ) entrada.readObject();
	            System.out.println( "\n" + mensaje );
	         }
	 
	         // atrapar los problemas que pueden ocurrir al leer del servidor
	         catch ( ClassNotFoundException excepcionClaseNoEncontrada ) {
	            System.out.println( "\nSe recibió un objeto de tipo desconocido" );
	         }
	 
	      } while ( !mensaje.equals( "SERVIDOR>>> TERMINAR" ) );
	 
	   } // fin del método procesarConexion
	
	  
	private class EventoConexion implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			host = JOptionPane.showInputDialog("Ingrese el numero de la direccion ip a la cual desea conectarse") ;	
			
			establecerHost(host);
			ejecutarCliente();
			
			}		
		}
	
	public static void main(String args[])
		{
		InterfazJuego interfaz = new InterfazJuego() ;
		interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}