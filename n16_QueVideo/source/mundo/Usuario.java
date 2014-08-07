package mundo;

import java.io.Serializable;



import uniandes.cupi2.estructuras.IInfoVertice;
import uniandes.cupi2.estructuras.Lista;
import uniandes.cupi2.estructuras.TablaHashing;

public class Usuario implements Comparable<Usuario>, Serializable, IInfoVertice<LlaveUsuario>
{

	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------
	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------
	/**
	 * La llave unica
	 */
	private LlaveUsuario llaveUsuario;

	/**
	 * El nombre del usuario
	 */
	private String nombreUsuario;

	/**
	 * La contrase単a del usuario
	 */
	private String contrasenia;

	/**
	 * La ruta de la foto del usuario
	 */
	private DatoUsuario<String> rutaFoto;

	/**
	 * La tabla de los videos con likes de este usuario
	 */
	private DatoUsuario<TablaHashing<Video, LlaveVideo>> videosConLikes;

	/**
	 * Lista que contiene las solicitudes de seguimiento sin responder por parte de otros usuarios
	 */
	private Lista<LlaveUsuario> solicitudesEnEspera;

	/**
	 * Indica si el usuario es seguido libremente o no
	 */
	private boolean seguidoLibremente;

	/**
	 * Lista que contiene las etiquetas de los videos que le gustan al usuario
	 */
	private Lista<String> etiquetasDeUsuario;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Metodo constructor del usuario
	 * @param pNombre El nombre del usuario
	 * @param pEmail El correo del usuario
	 * @param pContrasenia La contrase単a del usuario
	 */
	public Usuario( String pNombre, String pEmail, String pContrasenia, boolean likesPrivados, boolean fotoPrivada, boolean pSeguido )
	{
		llaveUsuario = new LlaveUsuario(pEmail);
		nombreUsuario = pNombre;
		contrasenia = pContrasenia;
		rutaFoto = new DatoUsuario<String>("", fotoPrivada);
		videosConLikes = new DatoUsuario<TablaHashing<Video, LlaveVideo>>(new TablaHashing<Video, LlaveVideo>(100), likesPrivados);
		seguidoLibremente = pSeguido;
		solicitudesEnEspera = new Lista<LlaveUsuario>();
		etiquetasDeUsuario = new Lista<String>();
	}

	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------
	/**
	 * Devuelve el correo electronico del usuario
	 * @return El correo electronico del usuario
	 */
	public String darCorreoElectronico()
	{
		return llaveUsuario.darEmail();
	}

	/**
	 * Devuelve el nombre de usuario
	 * @return el nombre de usuario
	 */
	public String darNombreUsuario()
	{
		return nombreUsuario;
	}

	/**
	 * Devuelve la llave del usuario
	 * @return La llave del usuario
	 */
	public LlaveUsuario darId()
	{
		return llaveUsuario;
	}

	public String darContrasena(){
		return contrasenia;
	}

	/**
	 * Devuelve la ruta de la foto de este usuario
	 * @return La ruta de la foto del usuario
	 */
	public DatoUsuario<String> darRutaFoto()
	{
		return rutaFoto;
	}

	/**
	 * Cambia la ruta de la foto actual por la que entra por parametro
	 * @param pRuta La ruta de la nueva foto
	 */
	public void cambiarRutaFoto( String pRuta)
	{
		rutaFoto.cambiarDato(pRuta);;
	}

	/**
	 * Devuelve la tabla de los videos que este usuario ha likeado
	 * @return La tabla de los videos likeados
	 */
	public DatoUsuario<TablaHashing<Video, LlaveVideo>> darTablaVideosLikes()
	{
		return videosConLikes;
	}
	
	public Lista<LlaveUsuario> darSolicitudesEnEspera()
	{
		return solicitudesEnEspera;
	}

	/**
	 * Verifica que la contrasenia que llega por parametro es la del usuario
	 * @param pContrasenia La contrasenia con la que se va a comparar
	 * @return true en caso de que este correcta, false en caso contrario
	 */
	public boolean verificarContresenia( String pContrasenia)
	{
		return contrasenia.equals(pContrasenia);
	}

	/**
	 * Indica si el usuario es seguido libremente 
	 * @return true en caso de que lo sea, false en caso contrario
	 */
	public boolean esSeguidoLibremente()
	{
		return seguidoLibremente;
	}

	/**
	 * Compara el usuario con otro que llega por parametro por sus llaves
	 */
	public int compareTo(Usuario o) 
	{
		return llaveUsuario.compareTo(o.llaveUsuario);
	}

	/**
	 * Agrega un la llave de un usuario a la lista de solicitudes en espera
	 * @param llaveUsuario2 La llave que se desea agregar
	 */
	public void agregarAListaDeEspera(LlaveUsuario llaveUsuario2) 
	{	
		if(solicitudesEnEspera.buscar(llaveUsuario2)==null)
		{
			solicitudesEnEspera.agregar(llaveUsuario2);
		}
	}

	/**
	 * Elimina una llave de usuario que llega por parametro de las solicitudes en espera
	 * @param llaveUsuario2 La llave que se desea eliminar
	 */
	public void eliminarSolicitud(LlaveUsuario llaveUsuario2) 
	{
		solicitudesEnEspera.eliminar(llaveUsuario2);
	}
	
	/**
	 * Agrega una etiquea a las etiquetas del usuario
	 * @param etiqueta La etiqueta que se desea agregar
	 */
	public void agregarEtiqueta(String etiqueta)
	{
		if(etiquetasDeUsuario.buscar(etiqueta)==null)
		{
			etiquetasDeUsuario.agregar(etiqueta);
		}
	}

	/**
	 * Indica si el usuario tiene la etiqueta que llega por parametro en su lista de etiquetas
	 * @param pIndice La etiqueta
	 * @return true si la tiene false en caso contrario
	 */
	public boolean tieneIndice(String pIndice) 
	{
		return etiquetasDeUsuario.buscar(pIndice)!=null;
	}
	
	
}