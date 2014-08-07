package mundo;

import java.io.Serializable;
import java.util.Date;

import uniandes.cupi2.estructuras.Cola;
import uniandes.cupi2.estructuras.Lista;




public class Video implements Comparable<Video>, Serializable {

	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	/**
	 * Constante de serialización
	 */
	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------
	/**
	 * Cantidad de likes
	 */
	private int likes;

	/**
	 * Nombre del video
	 */
	private String nombre;

	/**
	 * Cantidad de vistas
	 */
	private int numeroVistas;

	/**
	 * Cola de comentarios
	 */
	private Cola<Comentario> comentarios;

	/**
	 * Cola de indices
	 */
	private Cola<String> indices;

	/**
	 * id del video
	 */
	private String id;

	/**
	 * Llave unica del video
	 */
	private LlaveVideo llaveVideo;

	private Lista<Usuario> usuariosQueHanDadoLike;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------
	/**
	 * Constructor del video
	 * @param nNombre Nombre del video
	 * @param nId Id del video
	 */
	public Video(String nNombre, String nId)
	{
		likes=0;
		numeroVistas=0;
		comentarios= new Cola<Comentario>();
		indices = new Cola<String>();
		id = nId;
		nombre=nNombre;
		llaveVideo = new LlaveVideo(id);
		usuariosQueHanDadoLike = new Lista<Usuario>();
	}

	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------
	/**
	 * Devuelve los comentarios del video
	 * @return Una cola con los comentarios del video
	 */
	public Cola<Comentario> darComentarios()
	{
		return comentarios;
	}

	/**
	 * Devuelve los indices del video
	 * @return Una cola con los indices del video
	 */
	public Cola<String> darIndices()
	{
		return indices;
	}

	@Override
	public int compareTo(Video o) {
		return id.compareTo(o.darId());
	}

	/**
	 * Devuelve el numero de vistas del video
	 * @return El numero de vistas del video
	 */
	public int darNumeroVistas(){
		return numeroVistas;
	}

	/**
	 * Devuelve el numero de likes del video
	 * @return el numero de likes
	 */
	public int darLikes(){
		return likes;
	}

	/**
	 * Devuelve el nombre del video
	 * @return El nombre del video
	 */
	public String darNombre(){
		return nombre;
	}

	/**
	 * Devuelve el id del video
	 * @return el id del video
	 */
	public String darId()
	{
		return id;
	}

	/**
	 * Devuelve la llave del video
	 * @return La llave del video
	 */
	public LlaveVideo darLlaveVideo()
	{
		return llaveVideo;
	}

	/**
	 * Registra un like a este video
	 */
	public void registrarLike(Usuario usuarioLike) 
	{
		usuariosQueHanDadoLike.agregar(usuarioLike);
		likes++;
	}

	/**
	 * Registra una reproduccion a este video
	 */
	public void registrarReproduccion()
	{
		numeroVistas++;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return nombre;
	}

	/**
	 * Agrega un indice al videp
	 * @param palabraClave El indice a agregar
	 */
	public void agregarIndice(String palabraClave) 
	{

		indices.encolar(palabraClave);
		System.out.println("Se encolo: "+palabraClave+" en: "+nombre);
	}
	
	/**
	 * Devuelve los usuarios que le han dado like
	 * @return Los usuarios que le han dado like
	 */
	public Lista<Usuario> darUsuariosLikes()
	{
		return usuariosQueHanDadoLike;
	}

}
