package mundo;

import java.io.Serializable;

public class DatoUsuario<T> implements Serializable
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
	 * Representa el dato del usuario
	 */
	private T dato;
	
	/**
	 * Representa si es privado o no
	 */
	private boolean privado;
	
	// -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	/**
	 * Construye el dato con la informacion que llega por parametro
	 * @param pDato El dato
	 * @param pPrivado True si es privado false si es publico
	 */
	public DatoUsuario(T pDato, boolean pPrivado) 
	{
		dato = pDato;
		privado = pPrivado;	
	}
	
	// -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------
	
	/**
	 * Devuelve el dato
	 * @return El dato
	 */
	public T darDato()
	{
		return dato;
	}
	
	/**
	 * Devuelve si es privado o no
	 * @return true si es privado false si es publico
	 */
	public boolean esPrivado()
	{
		return privado;
	}
	
	/**
	 * Asigna a privado con lo que llega por parametro
	 * @param pPrivado El nuevo estado deseado
	 */
	public void cambiarPrivado( boolean pPrivado )
	{
		privado = pPrivado;
	}
	
	/**
	 * Cambia el dato por el que llega por parametro
	 * @param pDato El nuevo dato
	 */
	public void cambiarDato( T pDato )
	{
		dato = pDato;
	}
	
}
