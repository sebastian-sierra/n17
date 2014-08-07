package vistas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.estructuras.Camino;
import uniandes.cupi2.estructuras.Vertice;
import mundo.Amistad;
import mundo.DatoUsuario;
import mundo.LlaveUsuario;
import mundo.SistemaGestionVideos;
import mundo.Usuario;
import mundo.Video;

public class VistaVerUsuario 
{
	public VistaVerUsuario()
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
		salida.println("  <div id=\"panelCentroVerCuenta\">");
		salida.println("  <p><h2>"+pUsuario.darNombreUsuario()+"</h2></p>");
		if (!pUsuario.darRutaFoto().darDato().equals("")) {
			//TODO Poner la foto del usuario
			salida.println("  <img src=\"/quevideo/imagen.html?nombrefoto="+ pUsuario.darRutaFoto().darDato()+ "\" alt=\"\" id=\"fotoPerfil\"/> ");
		}
		else 
		{
			salida.println("  <img src=\"imagenes/nophoto.png\" alt=\"\"  id=\"fotoPerfil\"/> ");
		}
		salida.println("  <p></p>");
		salida.println("	<form method=\"POST\" action=\"guardarimagen.html?usuario="+pUsuario.darCorreoElectronico()+"\" enctype=\"multipart/form-data\">");
		salida.println("    <fieldset><legend>Cambiar Foto</legend>");
		salida.println("    <input type=\"file\" name=\"archivo\" >");
		salida.println("    <input type=\"submit\" name=\"aceptar\" value=\"Aceptar\"></fieldset>");
		salida.println("    </form>");
		salida.println("  <p></p>");
		salida.println("  <fieldset><legend>Mis Likes</legend>");

		salida.println("    <ul>");

		Iterator<Video> likesUsuario = pUsuario.darTablaVideosLikes().darDato().iterator();
		while(likesUsuario.hasNext())
		{
			Video vidActual = likesUsuario.next();
			salida.println("      <a href=\"vervideo.html?idvideo="+vidActual.darId()+"&usuario="+pUsuario.darCorreoElectronico()+"\"><li>"+vidActual.darNombre()+"</li></a>");
		}
		salida.println("    </ul>");
		salida.println("  </fieldset>");
		salida.println("  <p></p>");
		salida.println("  </div>");
		salida.println("  ");
		salida.println("  <aside id=\"panelIzqVerCuenta\">");
		
		if(pUsuario.darSolicitudesEnEspera().darLongitud()>0)
		{
			salida.println("<fieldset>");
			salida.println("      <legend>Solicitudes de video-amistad</legend>");
			salida.println("      <ul>");
			
			Iterator<LlaveUsuario> iterSolEnEspera = pUsuario.darSolicitudesEnEspera().iterator();
			
			while(iterSolEnEspera.hasNext())
			{
				Usuario usuarioActual = SistemaGestionVideos.getInstance().darGrafoUsuarios().darVertice(iterSolEnEspera.next()).darInfo();
				salida.println("    <form method=\"POST\">");
				salida.println("        <li>"+usuarioActual.darNombreUsuario());
				salida.println("          <input type=\"submit\" style=\"float:right;\" value=\"Rechazar\" formaction=\"rechazarUsuario.html?usuario="+pUsuario.darCorreoElectronico()+"&usuarioRechazar="+usuarioActual.darCorreoElectronico()+"\">");
				salida.println("          <input type=\"submit\" style=\"float:right;\" value=\"Aceptar\" formaction=\"aceptarUsuario.html?usuario="+pUsuario.darCorreoElectronico()+"&usuarioAceptar="+usuarioActual.darCorreoElectronico()+"\">");
				salida.println("        </li>");
				salida.println("    </form>");
				salida.println("        <br>");
			}
			
			salida.println("      </ul>");
			salida.println("</fieldset>");
			salida.println("<p></p>");

		}
		

		salida.println("  <fieldset><legend>Usuarios de QueVideo</legend>");
		//TODO Poner las imagenes de todos los usuarios

		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterUsuarios = SistemaGestionVideos.getInstance().darUsuarios();
		while(iterUsuarios.hasNext())
		{
			Usuario usuarioActual = iterUsuarios.next().darInfo();
			DatoUsuario<String> fotoActual = usuarioActual.darRutaFoto();

			if (!fotoActual.darDato().equals("") && !fotoActual.esPrivado()) 
			{
				salida.println("    <img src=\"/quevideo/imagen.html?nombrefoto="+ fotoActual.darDato() + "\" alt=\"\"/>");
			}
			else
			{
				salida.println("  <img src=\"imagenes/nophoto.png\" alt=\"\"/>");
			}
		}

		salida.println("  </fieldset>");
		salida.println("  <p></p>");
		salida.println(" <form id=\"formaBuscarUsuario\" method=\"POST\" action=\"buscarusuario.html?usuario="+pUsuario.darCorreoElectronico()+"\">");
		salida.println("      <fieldset>");
		salida.println("        <legend>Buscar Usuario</legend>");
		salida.println("        <input type=\"search\" name=\"busquedausuario\">");
		salida.println("        <select name=\"criteriodebusqueda\">");
		salida.println("          <option value=\"pornombre\">Por nombre</option>");
		salida.println("          <option value=\"poremail\">Por e-mail</option>");
		salida.println("        </select>");
		salida.println("        <input type=\"submit\" value=\"Buscar\">");
		salida.println("      </fieldset>");
		salida.println("    </form>");
		salida.println("  <p></p>");
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

	public void imprimirRespuesta(Usuario pUsuario, Usuario usuarioBuscado, HttpServletResponse response) throws IOException
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
		salida.println("  <div id=\"panelCentroVerCuenta\">");
		if (usuarioBuscado!=null) 
		{
			salida.println("  <p><h2>" + usuarioBuscado.darNombreUsuario()+ "</h2></p>");

			if (!usuarioBuscado.darRutaFoto().darDato().equals("") && !usuarioBuscado.darRutaFoto().esPrivado()) {
				//TODO Poner la foto del usuario
				salida.println("  <img src=\"/quevideo/imagen.html?nombrefoto="+ usuarioBuscado.darRutaFoto().darDato()+ "\" alt=\"\" id=\"fotoPerfil\"/> ");
			}
			else 
			{
				salida.println("  <img src=\"imagenes/nophoto.png\" alt=\"\"  id=\"fotoPerfil\"/> ");
			}
		}
		else 
		{
			salida.println("  <p><h2>No se encontr&oacute; ning&uacute;n usuario con ese correo</h2></p>");
		}
		salida.println("<p></p>");
		if (SistemaGestionVideos.getInstance().darGrafoUsuarios().darArco(pUsuario.darId(), usuarioBuscado.darId())==null && (pUsuario.compareTo(usuarioBuscado)!=0)) 
		{
			salida.println("    <a href=\"seguirAVideoContaco.html?usuario="
					+ pUsuario.darCorreoElectronico() + "&usuarioASeguir="
					+ usuarioBuscado.darCorreoElectronico() + "\">Seguir a "
					+ usuarioBuscado.darNombreUsuario() + "</a>");
		}

		salida.println("<p></p>");
		salida.println("<fieldset >");
		salida.println("      <legend>Tipo de relaci&oacute;n con este usuario</legend>");
		salida.println("<p></p>");

		Camino<LlaveUsuario, Usuario, Amistad> camino = SistemaGestionVideos.getInstance().darCaminoEntreUsuarios(pUsuario.darCorreoElectronico(), usuarioBuscado.darCorreoElectronico());


		if(camino !=null && camino.darLongitud()==1 )
		{
			salida.println("      Usted sigue a "+usuarioBuscado.darNombreUsuario());
			salida.println("<p></p>");
		}
		else if(camino !=null && camino.darLongitud()>1)
		{
			salida.println("      Usted sigue indirectamente a "+usuarioBuscado.darNombreUsuario());
			salida.println("<p></p>");
			salida.println("      El camino m&aacute;s corto de usted a "+usuarioBuscado.darNombreUsuario()+":");

			Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterVer = camino.darVertices();
			salida.println("      <ul>");
			while(iterVer.hasNext())
			{
				salida.println("      <li>"+iterVer.next().darId().darEmail()+"</li>");
			}
			salida.println("      </ul>");

		}

		camino = SistemaGestionVideos.getInstance().darCaminoEntreUsuarios(usuarioBuscado.darCorreoElectronico(), pUsuario.darCorreoElectronico());

		if(camino !=null && camino.darLongitud()==1)
		{
			salida.println("      "+usuarioBuscado.darNombreUsuario()+" lo sigue a usted");
			salida.println("<p></p>");
		}
		else if(camino !=null && camino.darLongitud()>1)
		{
			salida.println("      "+usuarioBuscado.darNombreUsuario()+" lo sigue indirectamente a usted");
			salida.println("<p></p>");
			salida.println("      El camino m&aacute;s corto de "+usuarioBuscado.darNombreUsuario()+" a usted es:");

			Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterVer = camino.darVertices();
			salida.println("      <ul>");
			while(iterVer.hasNext())
			{
				salida.println("      <li>"+iterVer.next().darId().darEmail()+"</li>");
			}
			salida.println("      </ul>");

		}

		salida.println("</fieldset>");
		if (usuarioBuscado!=null) 
		{
			salida.println("  <fieldset><legend>Sus Likes</legend>");
			//TODO Poner los likes del usuario HECHO
			salida.println("    <ul>");

			if (!usuarioBuscado.darTablaVideosLikes().esPrivado()) {
				Iterator<Video> likesUsuario = usuarioBuscado.darTablaVideosLikes().darDato().iterator();
				while (likesUsuario.hasNext()) {
					Video vidActual = likesUsuario.next();
					salida.println("      <li>" + vidActual.darNombre()+ "</li>");
				}
			}
			else
			{
				salida.println("      <li>Los likes de este usuario son privados</li>");
			}
			salida.println("    </ul>");
			salida.println("  </fieldset>");
		}

		salida.println("  <p></p>");
		salida.println("      ");
		salida.println("  ");
		salida.println("  </div>");
		salida.println("  ");
		salida.println("  <aside id=\"panelIzqVerCuenta\">");

		salida.println("  <fieldset><legend>Usuarios de QueVideo</legend>");
		//TODO Poner las imagenes de todos los usuarios

		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterUsuarios = SistemaGestionVideos.getInstance().darUsuarios();
		while(iterUsuarios.hasNext())
		{
			DatoUsuario<String> fotoActual = iterUsuarios.next().darInfo().darRutaFoto();
			if (!fotoActual.darDato().equals("") && !fotoActual.esPrivado()) 
			{
				salida.println("    <img src=\"/quevideo/imagen.html?nombrefoto="+ fotoActual.darDato() + "\" alt=\"\"/>");
			}
			else
			{
				salida.println("  <img src=\"imagenes/nophoto.png\" alt=\"\"/>");
			}
		}

		salida.println("  </fieldset>");
		salida.println("  <p></p>");
		salida.println(" <form id=\"formaBuscarUsuario\" method=\"POST\" action=\"buscarusuario.html?usuario="+pUsuario.darCorreoElectronico()+"\">");
		salida.println("      <fieldset>");
		salida.println("        <legend>Buscar Usuario</legend>");
		salida.println("        <input type=\"search\" name=\"busquedausuario\">");
		salida.println("        <select name=\"criteriodebusqueda\">");
		salida.println("          <option value=\"pornombre\">Por nombre</option>");
		salida.println("          <option value=\"poremail\">Por e-mail</option>");
		salida.println("        </select>");
		salida.println("        <input type=\"submit\" value=\"Buscar\">");
		salida.println("      </fieldset>");
		salida.println("    </form>");
		salida.println("  <p></p>");
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
