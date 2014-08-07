package mundo;

import java.io.Serializable;

public class Administrador implements Serializable
{

	/**
	 * Constante de serialización
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * El sistema gestion videos para poder hacer las tareas del administrador
	 */
	private SistemaGestionVideos sistema;
	
	/**
	 * El login del admin
	 */
	private String login;
	
	/**
	 * La contraseña de este
	 */
	private String password;
	
	/**
	 * Constructor del administrados
	 * @param loginAdmin El login del admin
	 * @param passwAdmin La contrasela del admin
	 */
	public Administrador(String loginAdmin, String passwAdmin) {

		login=loginAdmin;
		password=passwAdmin;
	}
	
	/**
	 * Devuelve el login del administrador
	 * @return Un String con el login del administrador
	 */
	public String darLogin()
	{
		return login;
	}
	
	public String darPassword(){
		return password;
	}
	
	/**
	 * Indica si un login con una contraseña corresponden a los de un administrador
	 * @param pLogin El login a comprobar
	 * @param pPass La contraseña a comprobar
	 * @return Un indicador que muestra si son o no los mismos que los del administrador
	 */
	public boolean esAdministrador(String pLogin, String pPass)
	{
		return login.equals(pLogin) && password.equals(pPass);
	}

}
