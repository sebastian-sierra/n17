package mundo;

import java.io.Serializable;



public class Comentario implements Serializable
{

	/**
	 * El autor del comentario
	 */
	private String autor;
	
	/**
	 * El comentario
	 */
	private String comentario;
	
	/**
	 * El constructor del comentario
	 * @param nComentario El comentario
	 * @param nAutor El autor del comentario
	 */
	public Comentario(String nComentario, String nAutor)
	{
		autor=nAutor;
		comentario= nComentario;
	}
	
	/**
	 * Devuelve el comentario
	 * @return El comentario
	 */
	public String darComentario()
	{
		return comentario;
	}
	
	/**
	 * Devuelve el autor
	 * @return El autor
	 */
	public String darAutor()
	{
		return autor;
	}
}
