package mundo;

import java.io.Serializable;

public class LlaveVideo implements Comparable<LlaveVideo>, Serializable
{
	//-------------------------------------------------------------
	// Constantes
	//-------------------------------------------------------------

	/**
	 * La constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	/**
	 * El id del video
	 */
	private String idVideo;

	// -------------------------------------------------------------
	// Constructores
	// -------------------------------------------------------------

	/**
	 * Construye una llave del video con el id que llega por parametro
	 * @param nIdVideo El id del video
	 */
	public LlaveVideo(String nIdVideo)
	{	
		idVideo = nIdVideo;
	}

	// -------------------------------------------------------------
	// Metodos
	// -------------------------------------------------------------

	/**
	 * Devuelve el id del video
	 * @return El id del video
	 */
	public String darIdVideo()
	{
		return idVideo;
	}

	/**
	 * Genera el hashCode() a partir del id
	 */
	public int hashCode()
	{
		return idVideo.hashCode();
	}

	/**
	 * Compara la llave con el que llega por parametro teniendo en cuenta el id
	 */
	public int compareTo(LlaveVideo o) 
	{
		return idVideo.compareToIgnoreCase(o.idVideo);
	}
	
}
