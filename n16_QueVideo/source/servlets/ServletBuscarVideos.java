package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mundo.LlaveUsuario;
import mundo.SistemaGestionVideos;
import mundo.Usuario;
import mundo.Video;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;
import org.json.simple.parser.ParseException;

import uniandes.cupi2.estructuras.ListaOrdenada;
import vistas.VistaVerBusqueda;
import vistas.VistaVerUsuario;
import vistas.VistarErrorInicio;

public class ServletBuscarVideos extends HttpServlet
{
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/dataQueVideo/queVideo.data";


	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------

	/**
	 * Inicializacion del Servlet
	 */
	public void init( ) throws ServletException
	{
	}

    public void destroy( )
    {
        System.out.println("Destruyendo instancia");
        try
        {
            if ( SistemaGestionVideos.getInstance( ) != null )
            {
                ServerConfig config = ServerConfigLocator.locate( );
                File dataDir = config.getServerDataDir();
                File tmp = new File( dataDir + RUTA_ARCHIVO_SERIALIZADO );
                tmp.getParentFile().mkdirs();
                tmp.createNewFile();
                
                System.out.println("Nombre=" + tmp.getName( ));
                System.out.println("Path=" + tmp.getPath( ));
                System.out.println("Abs. Path=" + tmp.getAbsolutePath( ));
                
                SistemaGestionVideos.getInstance().desconectarTodosLosUsuario();
                
                FileOutputStream fos = new FileOutputStream( tmp );
                ObjectOutputStream oos = new ObjectOutputStream( fos );
                oos.writeObject( SistemaGestionVideos.getInstance( ) );
                oos.close();
                fos.close();
                System.out.println("QueVideo Serializado");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        	System.out.println("Error de persistencia en Servidor de QueVideo: "+e.getMessage());
        }
    }

	/**
	 * Maneja un pedido GET de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}

	/**
	 * Maneja un pedido POST de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}

	/**
	 * Procesa el pedido de igual manera para todos
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 * @throws IOException Excepcion de error al escribir la respuesta
	 */
	private void procesarSolicitud( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		String palabraClave = request.getParameter("buscar");
		String emailUsuario = request.getParameter("usuario");
		System.out.println("emailusuario: "+emailUsuario);
		String idVideo = request.getParameter("idvideo");

		if(idVideo!=null)
		{
			SistemaGestionVideos.getInstance().registrarReproduccion(idVideo);
		}

		try {
			ListaOrdenada<Video> videosBusqueda = SistemaGestionVideos.getInstance().buscarVideosPalabraClave(palabraClave);
			VistaVerBusqueda vista = new VistaVerBusqueda();
			if (emailUsuario!=null) 
			{	
				Usuario usuarioActual = SistemaGestionVideos.getInstance().darGrafoUsuarios().darVertice(new LlaveUsuario(emailUsuario)).darInfo();
				vista.imprimirRespuesta(videosBusqueda, usuarioActual, palabraClave, response);
			}
			else
			{	
				vista.imprimirRespuesta(videosBusqueda, palabraClave, response);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
