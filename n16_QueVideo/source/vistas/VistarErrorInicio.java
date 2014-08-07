package vistas;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class VistarErrorInicio 
{
	public VistarErrorInicio()
	{
		
	}
	
	public void imprimirRespuesta(String error, HttpServletResponse response) throws IOException
	{
		PrintWriter salida = response.getWriter();
		
		salida.println("<!doctype html>");
		salida.println("<html>");
		salida.println("<head>");
		salida.println("<meta charset=\"UTF-8\">");
		salida.println("<title>QueVideo</title>");
		salida.println("<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->");
		salida.println("<script>var __adobewebfontsappname__=\"dreamweaver\"</script>");
		salida.println("<script src=\"http://use.edgefonts.net/source-sans-pro:n6,n2:default.js\" type=\"text/javascript\"></script>");
		salida.println("");
		salida.println("<link href=\"main.css\" rel=\"stylesheet\" type=\"text/css\">");
		salida.println("</head>");
		salida.println("");
		salida.println("<body>");
		salida.println("<div id=\"wrapper\">");
		salida.println("  <header id=\"top\">");
		salida.println("    <h1>QueVideo</h1>");
		salida.println("    <nav id=\"mainnav\">");
		salida.println("      <ul>");
		salida.println("        <li><a href=\"index.html\">QueVideo.com</a></li>");
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
		salida.println("  <article id=\"artMensajeError\">Hubo un error ingresando al sistema: "+error+"</article>");
		salida.println("  <form id=\"formacrearcuenta\" name=\"formacrearcuenta\" method=\"post\" action=\"crearcuentausuario.html\">");
		salida.println("    <fieldset>");
		salida.println("      <legend>Crear Cuenta</legend>");
		salida.println("      <p>");
		salida.println("        <label id=\"lbnombre\">Nombre: </label>");
		salida.println("        <input name=\"nombre\" type=\"text\" id=\"camponombre\">");
		salida.println("      </p>");
		salida.println("      <p>");
		salida.println("        <label id=\"lbapellido\">Apellido: </label>");
		salida.println("        <input name=\"apellido\" type=\"text\" id=\"campoapellido\">");
		salida.println("      </p>");
		salida.println("      <p>");
		salida.println("        <label id=\"lbemail\">Email:</label>");
		salida.println("        <input name=\"email\" type=\"text\" id=\"campoemail\">");
		salida.println("      </p>");
		salida.println("      <p>");
		salida.println("        <label id=\"lbcontrasenia\">Contrase&ntilde;a:</label>");
		salida.println("        <input name=\"contrasenia\" type=\"password\" id=\"campocontrasenia\">");
		salida.println("      </p>");
		salida.println("      <p>");
		salida.println("        <label id=\"lbrepetircontrasenia\">Repita su contrase&ntilde;a:</label>");
		salida.println("        <input name=\"contrasenia2\" type=\"password\" id=\"campocontrasenia2\">");
		salida.println("      </p>");
		salida.println("      <p>");
		salida.println("        <input type=\"checkbox\" id=\"fotoprivada\" name=\"cbfotoprivada\" >");
		salida.println("        <label for=\"fotoprivada\" > Quiero que mi foto sea privada</label>");
		salida.println("      </p>");
		salida.println("      <p>");
		salida.println("        <input type=\"checkbox\" id=\"likesprivados\" name=\"cblikesprivados\" >");
		salida.println("        <label for=\"likesprivados\" > Quiero que mis likes sean privados</label>");
		salida.println("      </p>");
		salida.println("      <input type=\"checkbox\" id=\"seguidolibremente\"  name=\"cbseguidolibremente\">");
		salida.println("      <label for=\"seguidolibremente\" > No quiero ser seguido libremente</label>");
		salida.println("      <p>");
		salida.println("        <input name=\"btnAceptarCrearCuenta\" type=\"submit\" id=\"btnAceptarCrearCuenta\" value=\"Aceptar\">");
		salida.println("      </p>");
		salida.println("    </fieldset>");
		salida.println("  </form>");
		salida.println("  <aside id=\"panelderecho\">");
		salida.println("  <form method=\"POST\" id=\"formainiciarsecion\" action=\"iniciarsesiondeusuario.html\">");
		salida.println("    <fieldset>");
		salida.println("      <legend>Iniciar Sesi&oacute;n</legend>");
		salida.println("      <p id=\"campoemailinsec\">");
		salida.println("        <label>Email:</label>");
		salida.println("        <input type=\"text\" name=\"email\">");
		salida.println("      </p>");
		salida.println("      <p id=\"campocontrasenaincsec\">");
		salida.println("        <label>Contrase&ntilde;a:</label>");
		salida.println("        <input type=\"password\" name=\"contrasenia\"> ");
		salida.println("      </p>");
		salida.println("      <p><input name=\"aceptar\" type=\"submit\" id=\"btnAceptarInSes\" value=\"Aceptar\"></p>");
		salida.println("    </fieldset>");
		salida.println("  </form>");
		salida.println("  </aside>");
		salida.println("  <footer><p>Copyright 2013 Que Video  </p></footer>");
		salida.println("</div>");
		salida.println("</body>");
		salida.println("</html>");


	}
}
