package vistas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.estructuras.Lista;
import uniandes.cupi2.estructuras.ListaOrdenada;
import uniandes.cupi2.estructuras.Vertice;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.sun.org.apache.bcel.internal.generic.ISUB;

import mundo.Amistad;
import mundo.DatoUsuario;
import mundo.LlaveUsuario;
import mundo.SistemaGestionVideos;
import mundo.Usuario;
import mundo.Video;

public class VistaVerAdministrador 
{
	public VistaVerAdministrador()
	{

	}

	public void imprimirRespuesta(Usuario pUsuario, HttpServletResponse response) throws IOException
	{
		PrintWriter salida = response.getWriter();

		salida.println("<!doctype html>");
		salida.println("<html>");
		salida.println("<head>");
		salida.println("<meta charset=\"UTF-8\">");
		salida.println("<title>QueVideo</title>");
		salida.println("<link href=\"main.css\" rel=\"stylesheet\" type=\"text/css\">");
		salida.println("<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->");
		salida.println("<script>var __adobewebfontsappname__=\"dreamweaver\"</script>");
		salida.println("<script src=\"http://use.edgefonts.net/source-sans-pro:n6,n2:default.js\" type=\"text/javascript\"></script>");
		salida.println("");
		salida.println("</head>");
		salida.println("<body>");
		salida.println("<div id=\"wrapper\">");
		salida.println("  <header id=\"top\">");
		salida.println("    <h1>QueVideo</h1>");
		salida.println("    <nav id=\"mainnav\">");
		salida.println("      <ul>");
		salida.println("        <li><a href=\"inicio.html?usuario="+pUsuario.darCorreoElectronico()+"\">Descubrir</a></li>");
		salida.println("        <li><a href=\"verperfil.html?usuario="+pUsuario.darCorreoElectronico()+"\">Mi cuenta ("+pUsuario.darNombreUsuario()+")</a></li>");
		salida.println("        <li>");
		salida.println("          <form method=\"POST\" id=\"formabusqueda\" action=\"buscarvideos.html?usuario="+pUsuario.darCorreoElectronico()+"\">");
		salida.println("            <input name=\"buscar\" type=\"search\" id=\"buscar\">");
		salida.println("            <input name=\"aceptar\" type=\"submit\" id=\"aceptar\" value=\"Buscar\">");
		salida.println("          </form>");
		salida.println("        </li>");
		salida.println("      </ul>");
		salida.println("    </nav>");
		salida.println("  </header>");

		salida.println("  <div id=\"panelCentroAdministrador\">");
		salida.println("    <fieldset>");
		salida.println("      <legend>Top 10 (Likes)</legend>");
		salida.println("      <ol>");
		Iterator<Video> iterVid10Likes = SistemaGestionVideos.getInstance().darVideosMasLikes().iterator();
		for( int i = 1; iterVid10Likes.hasNext() && i<=10; i++)
		{
			Video vidActual = iterVid10Likes.next();
			salida.println("        <a href=\"vervideo.html?idvideo="+vidActual.darId()+"&usuario="+pUsuario.darCorreoElectronico()+"\"><li>"+vidActual.darNombre()+" ("+vidActual.darLikes()+" like(s))</li></a>");

		}

		salida.println("      </ol>");
		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		salida.println("    <fieldset>");
		salida.println("      <legend>Top 20 (Reproducciones)</legend>");
		salida.println("      <ol>");

		Iterator<Video> iterVid20rep = SistemaGestionVideos.getInstance().darVideosMasVistos().iterator();
		for( int i = 1; iterVid20rep.hasNext() && i<=20; i++)
		{
			Video vidActual = iterVid20rep.next();
			salida.println("        <a href=\"vervideo.html?idvideo="+vidActual.darId()+"&usuario="+pUsuario.darCorreoElectronico()+"\"><li>"+vidActual.darNombre()+" ("+vidActual.darNumeroVistas()+" reproduccion(es))</li></a>");

		}

		salida.println("      </ol>");
		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		salida.println("  </div>");
		salida.println("  <aside id=\"panelIzqAdmin\">");
		salida.println("    <fieldset>");
		salida.println("      <legend>Usuarios Registrados</legend>");
		//TODO Poner usuario registrados
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterUsuarios = SistemaGestionVideos.getInstance().darUsuarios();
		while(iterUsuarios.hasNext())
		{	
			Usuario usuarioActual = iterUsuarios.next().darInfo();
			DatoUsuario<String> fotoActual = usuarioActual.darRutaFoto();

			if (!fotoActual.darDato().equals("") && !fotoActual.esPrivado()) 
			{
				salida.println("      <figure class=\"floatleft\"><img src=\"/quevideo/imagen.html?nombrefoto="+ fotoActual.darDato() + "\" alt=\"\"/>");
			}
			else
			{
				salida.println("      <figure class=\"floatleft\"><img src=\"imagenes/nophoto.png\" alt=\"\"/>");
			}

			salida.println("        <figcaption><font size=\"-1\">"+usuarioActual.darNombreUsuario()+"</font></figcaption>");
			salida.println("      </figure>");
		}

		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		salida.println("    <fieldset>");
		salida.println("      <legend>Usuarios En Linea</legend>");
		//TODO Poner usuarios en linea
		Iterator<Usuario> iterUsuariosCon = SistemaGestionVideos.getInstance().darUsuariosConectados().iterator();
		while(iterUsuariosCon.hasNext())
		{	
			Usuario usuarioActual = iterUsuariosCon.next();
			DatoUsuario<String> fotoActual = usuarioActual.darRutaFoto();
			
			if (!fotoActual.darDato().equals("") && !fotoActual.esPrivado()) 
			{
				salida.println("      <figure class=\"floatleft\"><img src=\"/quevideo/imagen.html?nombrefoto="+ fotoActual.darDato() + "\" alt=\"\"/>");
			}
			else
			{
				salida.println("      <figure class=\"floatleft\"><img src=\"imagenes/nophoto.png\" alt=\"\"/>");
			}

			salida.println("        <figcaption><font size=\"-1\">"+usuarioActual.darNombreUsuario()+"</font></figcaption>");
			salida.println("      </figure>");
		}

		salida.println("    </fieldset>");

		salida.println("<p></p>");
		salida.println("<fieldset>");
		salida.println("	<legend>Estad&iacute;sticas de los usuarios</legend>");
		salida.println("	<article>");
		salida.println("		<h3>");
		salida.println("			<font size=\"+1\">Usuarios Influencer:</font>");
		salida.println("		</h3>");

		salida.println("		<ul>");
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> usuariosInfluencer = SistemaGestionVideos.getInstance().darUsuariosInfluencer().iterator();

		if(usuariosInfluencer.hasNext())
		{
			while(usuariosInfluencer.hasNext())
			{
				salida.println("			<li>"+usuariosInfluencer.next().darInfo().darNombreUsuario()+"</li>");
			}
		}
		else
		{
			salida.println("			<li>No hay usuarios Influencer</li>");
		}
		salida.println("		</ul>");
		salida.println("		<h3>");
		salida.println("			<font size=\"+1\">Usuarios Stalker:</font>");
		salida.println("		</h3>");
		salida.println("		<ul>");
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> usuariosStalker = SistemaGestionVideos.getInstance().darUsuariosStalker().iterator();

		if(usuariosStalker.hasNext())
		{
			while(usuariosStalker.hasNext())
			{
				salida.println("			<li>"+usuariosStalker.next().darInfo().darNombreUsuario()+"</li>");
			}
		}
		else
		{
			salida.println("			<li>No hay usuarios Stalker</li>");
		}

		salida.println("		</ul>");
		salida.println("		<h3>");
		salida.println("			<font size=\"+1\">Usuarios Forever Alone:</font>");
		salida.println("		</h3>");
		salida.println("		<ul>");
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> usuariosForeverAlone = SistemaGestionVideos.getInstance().darUsuariosForeverAlone().iterator();

		if(usuariosForeverAlone.hasNext())
		{
			while(usuariosForeverAlone.hasNext())
			{
				salida.println("			<li>"+usuariosForeverAlone.next().darInfo().darNombreUsuario()+"</li>");
			}
		}
		else
		{
			salida.println("			<li>No hay usuarios Forever Alone</li>");
		}

		salida.println("		</ul>");
		salida.println("		<h3>");
		salida.println("			<font size=\"+1\">Usuario con m&aacute;s contactos:</font>");
		salida.println("		</h3>");
		salida.println("		<ul>");
		if (SistemaGestionVideos.getInstance().darUsuarioMasPopular()!=null) 
		{
			salida.println("			<li>"
					+ SistemaGestionVideos.getInstance().darUsuarioMasPopular()
							.darNombreUsuario() + "</li>");
		}
		else
		{
			salida.println("			<li>No hay ning&uacute;n usuario siguiendo a alguien</li>");
		}
		salida.println("		</ul>");
		salida.println("		<h3>");
		salida.println("			<font size=\"+1\">Usuario Hamilton:</font>");
		salida.println("		</h3>");
		salida.println("		<ul>");
		if (SistemaGestionVideos.getInstance().darUsuarioHamilton()!=null) 
		{
			salida.println("			<li>"+ SistemaGestionVideos.getInstance().darUsuarioHamilton().darNombreUsuario() + "</li>");
		}
		else
		{
			salida.println("			<li>No hay ning&uacute;n usuario hamilton</li>");
		}
		salida.println("		</ul>");
		salida.println("		<h3>");
		salida.println("			<font size=\"+1\">Camino con m&aacute;s usuarios:</font>");
		salida.println("		</h3>");
		salida.println("		<ol>");

		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> usuariosCaminoMasLargo = SistemaGestionVideos.getInstance().darCaminoMasLargoEntreUsuarios().darVertices();

		while(usuariosCaminoMasLargo.hasNext())
		{
			salida.println("			<li>"+usuariosCaminoMasLargo.next().darInfo().darNombreUsuario()+"</li>");
		}

		salida.println("		</ol>");
		salida.println("	</article>");
		salida.println("</fieldset>");
		salida.println("<p></p>");
		salida.println("  </aside>");
		salida.println("<footer>");
		salida.println("    <div>");
		salida.println("      <p>Copyright 2013 Que Video </p>");
		salida.println("    </div>");
		salida.println("    <aside>");
		salida.println("      <p><a href=\"cerrarsesion.html?usuario="+pUsuario.darCorreoElectronico()+"\">Cerrar Sesi&oacute;n</a></p>");
		salida.println("    </aside>");
		salida.println("  </footer>");
		salida.println("</div>");
		salida.println("</body>");
		salida.println("</html>");
		salida.println("");

	}

}
