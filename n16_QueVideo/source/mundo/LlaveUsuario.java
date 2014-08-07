package mundo;

import java.io.Serializable;

public class LlaveUsuario implements Comparable<LlaveUsuario>, Serializable
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
	 * El email del usuario
	 */
	private String email;

	// -------------------------------------------------------------
	// Constructores
	// -------------------------------------------------------------

	/**
	 * Construye una llave de pais con el codigo que llega por parametro
	 * @param nCodigo El codigo del pais
	 */
	public LlaveUsuario(String nEmail)
	{	
		email = nEmail;
	}

	// -------------------------------------------------------------
	// Metodos
	// -------------------------------------------------------------

	/**
	 * Devuelve el codigo del pais
	 * @return El codigo del pais
	 */
	public String darEmail()
	{
		return email;
	}

	/**
	 * Genera el hashCode() a partir del email
	 */
	public int hashCode()
	{
		return email.hashCode();
	}

	/**
	 * Compara la llave con el que llega por parametro teniendo en cuenta el correo electronico
	 */
	public int compareTo(LlaveUsuario o) 
	{
		return email.compareToIgnoreCase(o.email);
	}
	
}
