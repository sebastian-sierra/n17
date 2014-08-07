package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mundo.LlaveUsuario;
import mundo.SistemaGestionVideos;
import mundo.Usuario;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;

import vistas.VistaVerUsuario;

public class ServletGuardarImagen extends HttpServlet
{
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/dataQueVideo/queVideo.data";
	public final static String RUTA_CARPETA_FOTOS = "/dataQueVideo/fotosUsuarios/";



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
		//procesarSolicitud( request, response );
	}

	/**
	 * Maneja un pedido POST de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String rutaFoto = guardarFoto( request, response );
		procesarSolicitud( request, response, rutaFoto );
	}

	private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response, String rutaFoto) throws IOException 
	{
		String emailUsuario = request.getParameter("usuario");
		Usuario usuarioBuscado = SistemaGestionVideos.getInstance().darGrafoUsuarios().darVertice(new LlaveUsuario(emailUsuario)).darInfo();;
		usuarioBuscado.cambiarRutaFoto(rutaFoto);

		VistaVerUsuario vista = new VistaVerUsuario();
		vista.imprimirRespuesta(usuarioBuscado, response);
	}

	/**
	 * Procesa el pedido de igual manera para todos
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 * @throws IOException Excepcion de error al escribir la respuesta
	 */
	private String guardarFoto( HttpServletRequest request, HttpServletResponse response )  
	{	
		System.out.println("ENTRO A PROCESAR");
		File file ;
		int maxFileSize = 10000 * 1024;
		int maxMemSize = 10000 * 1024;

		ServerConfig config = ServerConfigLocator.locate( );
		System.out.println(ServerConfigLocator.locate().getServerHomeURL());
		File dataDir = config.getServerDataDir();
		String filePath = dataDir+RUTA_CARPETA_FOTOS;

		// Verify the content type
		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") >= 0)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File("/tmp"));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum file size to be uploaded.
			upload.setSizeMax( maxFileSize );
			try{ 
				// Parse the request to get file items.
				List fileItems = upload.parseRequest(request);

				// Process the uploaded file items
				Iterator i = fileItems.iterator();


				while ( i.hasNext () ) 
				{
					FileItem fi = (FileItem)i.next();
					if ( !fi.isFormField () )	
					{
						// Get the uploaded file parameters
						String fieldName = fi.getFieldName();
						String fileName = fi.getName();
						boolean isInMemory = fi.isInMemory();
						long sizeInBytes = fi.getSize();
						// Write the file
						if( fileName.lastIndexOf("\\") >= 0 ){
							file = new File( filePath + 
									fileName.substring( fileName.lastIndexOf("\\"))) ;
							file.getParentFile().mkdirs();
							System.out.println("La ruta de la foto es: "+file.getAbsolutePath());
						}else{
							file = new File( filePath + 
									fileName.substring(fileName.lastIndexOf("\\")+1)) ;
							file.getParentFile().mkdirs();
							System.out.println("La ruta de la foto es: "+file.getAbsolutePath());
						}

						fi.write( file ) ;
						return file.getName();
					}
				}

			}catch(Exception ex) {
				System.out.println(ex);
			}
		}

		return "";

	}


}
