package vistas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import uniandes.cupi2.estructuras.Camino;
import uniandes.cupi2.estructuras.ListaOrdenada;
import uniandes.cupi2.estructuras.Vertice;
import mundo.Amistad;
import mundo.LlaveUsuario;
import mundo.SistemaGestionVideos;
import mundo.Usuario;
import mundo.Video;

public class VistaVerCaminoIntermedio 
{
	public VistaVerCaminoIntermedio()
	{

	}

	public void imprimirRespuesta(Usuario pUsuario, String orig, String dest, Camino<LlaveUsuario, Usuario, Amistad> camino, HttpServletResponse response) throws IOException
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

		salida.println("<div id=\"panelCentroBusqueda\">");
		salida.println("    <h2>Camino entre "+orig+" y "+dest+" pasando por "+pUsuario.darCorreoElectronico()+"</h2>");
		salida.println("    <p></p>");
		
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterVer = camino.darVertices();
		salida.println("    <ol>");
		
		while(iterVer.hasNext())
		{
			salida.println("      <li>"+iterVer.next().darId().darEmail()+"</li>");
		}
		
		
		salida.println("    </ol>");
		salida.println("    <p></p>");
		salida.println("  </div>");;
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
