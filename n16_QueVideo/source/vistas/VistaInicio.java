package vistas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import mundo.SistemaGestionVideos;
import mundo.Usuario;
import mundo.Video;

public class VistaInicio 
{
	public VistaInicio()
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
		salida.println("");
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
		salida.println("<div id=\"panelUsuariosBusqueda\">");
		salida.println("    <h2>Descubre Videos y Usuarios Conocidos</h2>");
		salida.println("    <p></p>");
		salida.println("    <fieldset><legend> Usuarios Sugeridos</legend>");
		
		Iterator<Usuario> iterUsuariosSug = SistemaGestionVideos.getInstance().darSugerenciasDeUsuarios(pUsuario.darCorreoElectronico()).iterator();
		while(iterUsuariosSug.hasNext())
		{
			Usuario usuarioActual = iterUsuariosSug.next();
			salida.println("      <a href=\"visitarperfil.html?usuario="+pUsuario.darCorreoElectronico()+"&usuarioAVisitar="+usuarioActual.darCorreoElectronico()+"\">");
			String fotoActual = usuarioActual.darRutaFoto().darDato();

			if (!fotoActual.equals("") && !usuarioActual.darRutaFoto().esPrivado()) 
			{
				salida.println("      <figure class=\"floatleft\"><img src=\"/quevideo/imagen.html?nombrefoto="+ fotoActual + "\">");
			}
			else
			{
				salida.println("      <figure class=\"floatleft\"><img src=\"imagenes/nophoto.png\">");
			}
			salida.println("        <figcaption><font size=\"-1\">"+usuarioActual.darNombreUsuario()+"</font> </figcaption>");
			salida.println("      </figure>");
			salida.println("      </a>");
		}
		
		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		salida.println("        <fieldset><legend> Videos Sugeridos</legend>");
		salida.println("    <ul>");
		
		Iterator<Video> iterVideosSug = SistemaGestionVideos.getInstance().darSugerenciasDeVideos(pUsuario.darCorreoElectronico()).iterator();
		while(iterVideosSug.hasNext())
		{
			Video vidActual = iterVideosSug.next();
			salida.println("      <a href=\"vervideo.html?idvideo="+vidActual.darId()+"&usuario="+pUsuario.darCorreoElectronico()+"\"><li>"+vidActual.darNombre()+"</li></a>");
		}
		
		salida.println("    </ul>");
		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		salida.println("    ");
		salida.println("     <form id=\"formaBuscarUsuario\" method=\"POST\" action=\"buscarusuario.html?usuario="+pUsuario.darCorreoElectronico()+"\">");
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
		salida.println("    <p></p>");
		salida.println("    <form id=\"formaDarCaminoIntermedio\" method=\"POST\" action=\"darCaminoIntermedio.html?usuario="+pUsuario.darCorreoElectronico()+"\">");
		salida.println("      <fieldset>");
		salida.println("        <legend>Dar Camino Intermedio</legend>");
		salida.println("        ID Usuario Origen:");
		salida.println("        <input type=\"text\" name=\"idOrig\">");
		salida.println("        <br>");
		salida.println("        ID Usuario Destino:");
		salida.println("        <input type=\"text\" name=\"idDest\">");
		salida.println("        <br>");
		salida.println("        <input type=\"submit\" value=\"Buscar Camino\">");
		salida.println("      </fieldset>");
		salida.println("    </form>");
		salida.println("    <p></p>");
		salida.println("    <form id=\"formaAgregarUsuariosDeNivel\" method=\"POST\" action=\"agregarUsuariosDeNivel.html?usuario="+pUsuario.darCorreoElectronico()+"\">");
		salida.println("      <fieldset>");
		salida.println("        <legend>Agregar seguidores</legend>");
		salida.println("        Nivel:");
		salida.println("        <input type=\"text\" name=\"nivel\">");
		salida.println("        <br>");
		salida.println("        <input type=\"submit\" value=\"Agregar\">");
		salida.println("      </fieldset>");
		salida.println("    </form>");
		salida.println("    <p></p>");
		salida.println("</div>");
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
	
	public void imprimirRespuesta( HttpServletResponse response) throws IOException
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
		salida.println("        <li><a href=\"inicio.html\">QueVideo.com</a></li>");
		salida.println("        <li><a href=\"iniciarsesioncrearcuenta.html\">Mi cuenta (Iniciar Sesi&oacute;n)</a></li>");
		salida.println("        <li>");
		salida.println("          <form method=\"POST\" id=\"formabusqueda\" action=\"buscarvideos.html\">");
		salida.println("            <input name=\"buscar\" type=\"search\" id=\"buscar\">");
		salida.println("            <input name=\"aceptar\" type=\"submit\" id=\"aceptar\" value=\"Buscar\">");
		salida.println("          </form>");
		salida.println("        </li>");
		salida.println("      </ul>");
		salida.println("    </nav>");
		salida.println("  </header>");
		salida.println("  <article id=\"maintext\">");
		salida.println("    <h2>Bienvenido a QueVideo.com </h2>");
		salida.println("    <h2>Aqu&iacute; podr&aacute;:</h2>");
		salida.println("    <ul>");
		salida.println("      <li>Descubrir</li>");
		salida.println("      <li>Compartir</li>");
		salida.println("      <li>Socializar</li>");
		salida.println("    </ul>");
		salida.println("  </article>");
		salida.println("  <footer><p>Copyright 2013 Que Video  </p></footer>");
		salida.println("</div>");
		salida.println("</body>");
		salida.println("</html>");
		salida.println("");

	}
}
