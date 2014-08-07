package vistas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import mundo.Comentario;
import mundo.Usuario;
import mundo.Video;

public class VistaVerVideo 
{
	public VistaVerVideo()
	{
		
	}

	public void imprimirRespuesta(Video videoBuscado, Usuario usuarioActual, HttpServletResponse response) throws IOException 
	{
		// TODO Auto-generated method stub
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
		salida.println("        <li><a href=\"inicio.html?usuario="+usuarioActual.darCorreoElectronico()+"&idvideo="+videoBuscado.darId()+"\">Descubrir</a></li>");
		salida.println("        <li><a href=\"verperfil.html?usuario="+usuarioActual.darCorreoElectronico()+"&idvideo="+videoBuscado.darId()+"\">Mi cuenta ("+usuarioActual.darNombreUsuario()+")</a></li>");
		salida.println("        <li>");
		salida.println("          <form method=\"POST\" id=\"formabusqueda\" action=\"buscarvideos.html?usuario="+usuarioActual.darCorreoElectronico()+"&idvideo="+videoBuscado.darId()+"\">");
		salida.println("            <input name=\"buscar\" type=\"search\" id=\"buscar\">");
		salida.println("            <input name=\"aceptar\" type=\"submit\" id=\"aceptar\" value=\"Buscar\">");
		salida.println("          </form>");
		salida.println("        </li>");
		salida.println("      </ul>");
		salida.println("    </nav>");
		salida.println("  </header>");
		
		salida.println("  <div id=\"panelCentro\">");
		salida.println("      <p>    ");
		salida.println("      <h2 id=\"tituloVideo\">"+videoBuscado.darNombre()+"</h2>");
		salida.println("      <p></p>");
		salida.println("      <p></p>");
		salida.println("      <div id=\"video\">");
		salida.println("        <iframe src=\"http://player.vimeo.com/video/"+videoBuscado.darId()+"?title=0&amp;byline=0\" width=\"680\" height=\"383\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>");
		salida.println("      </div>");
		salida.println("      <p></p>");
		salida.println("    <fieldset>");
		salida.println("    <legend>Informaci&oacute;n</legend>");
		salida.println("    <ul id=\"listaPieDeVideo\">");
		salida.println("        <li>Likes: "+videoBuscado.darLikes()+"</li>");
		salida.println("        <li>Reproducciones: "+videoBuscado.darNumeroVistas()+"</li>");
		salida.println("      </ul>");
		salida.println("    <p></p>");
		salida.println("    <form id=\"formadarlike\" name=\"formadarlike\" method=\"POST\" action=\"darlike.html?usuario="+usuarioActual.darCorreoElectronico()+"&idvideo="+videoBuscado.darId()+"\">");
		salida.println("    <input type=\"submit\" value=\"Me gusta\">");
		salida.println("    </form>");
		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		salida.println("  <form id=\"formaComentar\" method=\"POST\" action=\"comentarvideo.html?usuario="+usuarioActual.darCorreoElectronico()+"&idvideo="+videoBuscado.darId()+"\">");
		salida.println("        <fieldset>");
		salida.println("          <legend>Comentar</legend>");
		salida.println("          <textarea name=\"comentario\"></textarea>");
		salida.println("          <p>");
		salida.println("            <input type=\"submit\" value=\"Aceptar\">");
		salida.println("          </p>");
		salida.println("        </fieldset>");
		salida.println("      </form>");
		
		salida.println("    <article id=\"panelComentarios\">");
		salida.println("        <p> </p>     ");
		
		Iterator<Comentario> iterComentarios = videoBuscado.darComentarios().iterator();
		while(iterComentarios.hasNext())
		{
			Comentario comentarioActual = iterComentarios.next();
			salida.println("      <h3>"+comentarioActual.darAutor()+"</h3>");
			salida.println("        <p></p>");
			salida.println("        <p>"+comentarioActual.darComentario()+"</p>");
			salida.println("        <p></p>");
		}
		
		salida.println("      </article>");
		salida.println("  </div>");
		
		salida.println("    <aside id=\"panelIzq\">");
		
		Iterator<String> iterIndices = videoBuscado.darIndices().iterator();
		if(!iterIndices.hasNext())
		{
			salida.println("      <h2>El video no se encuentra en ning&uacute;n &iacute;ndice</h2>");
		}
		else
		{
			salida.println("      <article id=\"textoIndicesVideo\"><p><h2>El video se encuentra en los siguientes &iacute;ndices:</h2></p>");
			salida.println("        <ul>");
			while(iterIndices.hasNext())
			{
				salida.println("          <li>"+iterIndices.next()+"</li>");

			}
			salida.println("        </ul>");
			salida.println("      </article>");
			
		}
		
		salida.println("<p></p>");
		
		Iterator<Usuario> iterUsuariosLike = videoBuscado.darUsuariosLikes().iterator();
		if(!iterUsuariosLike.hasNext())
		{
			salida.println("      <h2>A ning&uacute;n usuario le gusta este video</h2>");
		}
		else
		{
			salida.println("      <article id=\"textoIndicesVideo\"><p><h2>A estos usuarios les gusta este video:</h2></p>");
			salida.println("        <ul>");
			while(iterUsuariosLike.hasNext())
			{
				salida.println("          <li>"+iterUsuariosLike.next().darNombreUsuario()+"</li>");

			}
			salida.println("        </ul>");
			salida.println("      </article>");
		}	
		
		salida.println("<p></p>");
		salida.println("    </aside>");
		
		salida.println("<footer>");
		salida.println("    <div>");
		salida.println("      <p>Copyright 2013 Que Video </p>");
		salida.println("    </div>");
		salida.println("    <aside>");
		salida.println("      <p><a href=\"cerrarsesion.html?usuario="+usuarioActual.darCorreoElectronico()+"\">Cerrar Sesi&oacute;n</a></p>");
		salida.println("    </aside>");
		salida.println("  </footer>");
		salida.println("  ");
		salida.println("  ");
		salida.println("  </body>");
		salida.println("  </html>");
		
	}

	public void imprimirRespuesta(Video videoBuscado, HttpServletResponse response) throws IOException 
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
		salida.println("        <li><a href=\"inicio.html?idvideo="+videoBuscado.darId()+"\">QueVideo.com</a></li>");
		salida.println("        <li><a href=\"iniciarsesioncrearcuenta.html?idvideo="+videoBuscado.darId()+"\">Mi cuenta (Iniciar Sesi&oacute;n)</a></li>");
		salida.println("        <li>");
		salida.println("          <form method=\"POST\" id=\"formabusqueda\" action=\"buscarvideos.html?idvideo="+videoBuscado.darId()+"\">");
		salida.println("            <input name=\"buscar\" type=\"search\" id=\"buscar\">");
		salida.println("            <input name=\"aceptar\" type=\"submit\" id=\"aceptar\" value=\"Buscar\">");
		salida.println("          </form>");
		salida.println("        </li>");
		salida.println("      </ul>");
		salida.println("    </nav>");
		salida.println("  </header>");
		
		salida.println("  <div id=\"panelCentro\">");
		salida.println("      <p>    ");
		salida.println("      <h2 id=\"tituloVideo\">"+videoBuscado.darNombre()+"</h2>");
		salida.println("      <p></p>");
		salida.println("      <p></p>");
		salida.println("      <div id=\"video\">");
		salida.println("        <iframe src=\"http://player.vimeo.com/video/"+videoBuscado.darId()+"?title=0&amp;byline=0\" width=\"680\" height=\"383\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>");
		salida.println("      </div>");
		salida.println("      <p></p>");
		salida.println("    <fieldset>");
		salida.println("    <legend>Informaci&oacute;n</legend>");
		salida.println("    <ul id=\"listaPieDeVideo\">");
		salida.println("        <li>Likes: "+videoBuscado.darLikes()+"</li>");
		salida.println("        <li>Reproducciones: "+videoBuscado.darNumeroVistas()+"</li>");
		salida.println("      </ul>");
		salida.println("    <p></p>");
		
		salida.println("    </fieldset>");
		salida.println("    <p></p>");
		
		salida.println("    <article id=\"panelComentarios\">");
		salida.println("        <p> </p>     ");
		
		Iterator<Comentario> iterComentarios = videoBuscado.darComentarios().iterator();
		while(iterComentarios.hasNext())
		{
			Comentario comentarioActual = iterComentarios.next();
			salida.println("      <h3>"+comentarioActual.darAutor()+"</h3>");
			salida.println("        <p></p>");
			salida.println("        <p>"+comentarioActual.darComentario()+"</p>");
			salida.println("        <p></p>");
		}
		
		salida.println("      </article>");
		salida.println("  </div>");
		
		salida.println("    <aside id=\"panelIzq\">");
		
		Iterator<String> iterIndices = videoBuscado.darIndices().iterator();
		if(!iterIndices.hasNext())
		{
			salida.println("      <h2>El video no se encuentra en ning&uacute;n &iacute;ndice</h2>");
		}
		else
		{
			salida.println("      <article id=\"textoIndicesVideo\"><p><h2>El video se encuentra en los siguientes &iacute;ndices:</h2></p>");
			salida.println("        <ul>");
			while(iterIndices.hasNext())
			{
				salida.println("          <li>"+iterIndices.next()+"</li>");

			}
			salida.println("        </ul>");
			salida.println("      </article>");
			
		}
		
		salida.println("    </aside>");
		
		salida.println("  <footer><p>Copyright 2013 Que Video  </p></footer>");
		salida.println("  ");
		salida.println("  ");
		salida.println("  </body>");
		salida.println("  </html>");	
	}
	
	
}
