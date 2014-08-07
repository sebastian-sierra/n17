package vistas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.estructuras.ListaOrdenada;
import mundo.SistemaGestionVideos;
import mundo.Usuario;
import mundo.Video;

public class VistaVerBusqueda 
{
	public VistaVerBusqueda()
	{

	}

	public void imprimirRespuesta(ListaOrdenada<Video> videos, Usuario pUsuario, String palabraClave, HttpServletResponse response) throws IOException
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

		salida.println("  <div id=\"panelCentroBusqueda\">");
		salida.println("  <h2>Videos con la palabra \""+palabraClave+"\" en su t&iacute;tulo</h2>");
		salida.println("  <p></p>");
		salida.println("  <ul>");

		Iterator<Video> iterVid = videos.iterator();
		while(iterVid.hasNext())
		{
			Video vidActual = iterVid.next();
			salida.println("    <a href=\"vervideo.html?idvideo="+vidActual.darId()+"&usuario="+pUsuario.darCorreoElectronico()+"\"><li>"+vidActual.darNombre()+"</li></a>");

		}

		salida.println("  </ul>");
		salida.println("<p></p>");
		salida.println("<form action=\"guardarbusqueda.html?palabraclave="+palabraClave+"&usuario="+pUsuario.darCorreoElectronico()+"\" method=\"POST\">");
		salida.println("	<input type=\"submit\" value=\"Guardar Busqueda\">");
		salida.println("</form>");
		salida.println("<p></p>");
		salida.println("  </div>");
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

	public void imprimirRespuesta(ListaOrdenada<Video> videos, String palabraClave, HttpServletResponse response) throws IOException
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

		salida.println("  <div id=\"panelCentroBusqueda\">");
		salida.println("  <h2>Videos con la palabra \""+palabraClave+"\" en su t&iacute;tulo</h2>");
		salida.println("  <p></p>");
		salida.println("  <ul>");

		Iterator<Video> iterVid = videos.iterator();
		while(iterVid.hasNext())
		{
			Video vidActual = iterVid.next();
			salida.println("    <a href=\"vervideo.html?idvideo="+vidActual.darId()+"\"><li>"+vidActual.darNombre()+"</li></a>");

		}

		salida.println("  </ul>");
		salida.println("<p></p>");
		salida.println("<form action=\"guardarbusqueda.html?palabraclave="+palabraClave+"\" method=\"POST\">");
		salida.println("	<input type=\"submit\" value=\"Guardar Busqueda\">");
		salida.println("</form>");
		salida.println("<p></p>");
		salida.println("  </div>");
		salida.println("<footer><p> Copyright 2013 Que Video  </p></footer>");
		salida.println("</div>");
		salida.println("</body>");
		salida.println("</html>");
		salida.println("");

	}


}
