package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mundo.SistemaGestionVideos;
import mundo.Usuario;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;

import vistas.VistaVerUsuario;
import vistas.VistarErrorInicio;

public class ServletCrearCuentaUsuario extends HttpServlet
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
        String nombreUsuario = request.getParameter("nombre");
        String apellidoUsuario = request.getParameter("apellido");
        String email = request.getParameter("email");
        String contra1 = request.getParameter("contrasenia");
        String contra2 = request.getParameter("contrasenia2");
        String stgfotoPrivada = request.getParameter("cbfotoprivada");
        String stglikesPrivados = request.getParameter("cblikesprivados");
        String stgseguidoLib = request.getParameter("cbseguidolibremente");
        boolean fotoPrivada = stgfotoPrivada!=null;
        boolean likesPrivados = stglikesPrivados!=null;
        boolean seguidoLibremente = stgseguidoLib==null;
        
        try {
			//TODO Cambiar esto para que no sea false false true, sino lo que mando el usuario
        	Usuario nUsuario = SistemaGestionVideos.getInstance().registrarUsuario(nombreUsuario+" "+apellidoUsuario, email, contra1, contra2, likesPrivados, fotoPrivada, seguidoLibremente);
			VistaVerUsuario vista = new VistaVerUsuario();
			vista.imprimirRespuesta(nUsuario, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			VistarErrorInicio vista = new VistarErrorInicio();
			vista.imprimirRespuesta( e.getMessage(), response);
		}
    }
	
}
