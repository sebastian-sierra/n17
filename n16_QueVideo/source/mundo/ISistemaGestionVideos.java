package mundo;

import java.util.Iterator;

import org.json.simple.parser.ParseException;

import uniandes.cupi2.estructuras.ListaOrdenada;



public interface ISistemaGestionVideos {

	public Usuario registrarUsuario(String nNombre, String nCorreo, String nContrasena, String dobleCon) throws Exception;
	
	/**
	 *Valida un usuario en el sistema y lo retorna con toda su informacion
	 * @param email El email del usuario
	 * @param contrasenaU La contrasenia del usuario
	 * @return El usuario con toda su informacion o null si no se pudo verificar porque no existe
	 * 		   o porque la contrasenia es incorrecta
	 */
	public Usuario validarUsuario(String email, String contrasenaU);
	
	/**
	 * Busca videos por palabra clave
	 * @param nPalabra La palabra clace
	 * @return Una lista ordenada de videos que contienen la palabra clave
	 * @throws ParseException Bota excepcion si ocurre un error parseando.
	 */
	public ListaOrdenada<Video> buscarVideosPalabraClave(String nPalabra) throws ParseException;
	
	/**
	 * Guarda la busqueda de la palabra buscada
	 * @param palabraClave la busqueda
	 */
	public void guardarBusqueda(String  palabraClave);
	
	/**
	 * Busca el video y le hace like al video, agrega este video a la lista de videos likeados del usuario
	 * @param usuarioLike El usuario que hace like
	 * @param videoLike El video likeado
	 */
	public void likearUnVideo(Usuario usuarioLike,Video videoLike);
	
	/**
	 * Comenta un video
	 * @param comentario comentario del usuario
	 * @param aComentar Video a comentar
	 * @param autor El usuario que hace el comentario
	 */
	public void comentarUnVideo(String comentario, Video aComentar, Usuario autor); 
	
}
