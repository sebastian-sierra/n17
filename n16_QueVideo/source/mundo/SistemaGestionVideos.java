package mundo;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import uniandes.cupi2.estructuras.Camino;
import uniandes.cupi2.estructuras.GrafoDirigido;
import uniandes.cupi2.estructuras.Lista;
import uniandes.cupi2.estructuras.ListaOrdenada;
import uniandes.cupi2.estructuras.TablaHashing;
import uniandes.cupi2.estructuras.Trie;
import uniandes.cupi2.estructuras.Vertice;



public class SistemaGestionVideos implements Serializable//, ISistemaGestionVideos
{

	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------
	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constante para representar una busqueda por correo
	 */
	public static final String POR_NOMBRE= "pornombre";
	
	/**
	 * Constante para representar una busqueda por correo
	 */
	public static final String POR_EMAIL= "poremail";
	
	/**
	 * Ruta de archivo serializado
	 */
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/dataQueVideo/queVideo.data";

	/**
	 * clave API
	 */
	private static final String API_KEY = "b4cab8d828b0eaa53c493091a31de50d7bcdf6e9";

	/**
	 * clave API secreta
	 */
	private static final String API_SECRET = "01486d2568039f60bf21c86e7aaee4551d9417ec";

	/**
	 * Token de acceso
	 */
	private static final String VIMEO_ACCESS_TOKEN = "e814f26da75b542b3d885d8c5e9577b8";

	/**
	 * Accesos secreto
	 */
	private static final String VIMEO_ACCESS_SECRET = "725ff29220807ada8ab59827dbf6a66d87252084";

	/**
	 * 
	 */
	private static final String CALLBACK = "oob";

	/**
	 * pagina base
	 */
	private final static String VIMEO_BASE = "http://vimeo.com/api/rest/v2?method=%s";

	/**
	 * metodo vimeo
	 */
	private final static String VIMEO_METHOD = "vimeo.test.login";

	/**
	 * Version
	 */
	public static final String O_AUTH_VERSION = "1.0";

	/**
	 * 
	 */
	public static final String SIGNATURE_METHOD = "HMAC-SHA1";

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * El grafo de usuarios
	 */
	private GrafoDirigido<LlaveUsuario, Usuario, Amistad> usuarios;

	/**
	 * la tabla de videos en el sistema
	 */
	private TablaHashing<Video, LlaveVideo> videosEnElSistema;

	/**
	 * la lista de videos de la busqueda actual
	 */
	private ListaOrdenada<Video> listaBusquedaActual;

	/**
	 * la tabla de usuarios conectados
	 */
	private TablaHashing<Usuario, LlaveUsuario> usuariosConectados;

	/**
	 * El trie de los videos
	 */
	private Trie<Video> videosEnTrie;

	/**
	 * Usuario administrador
	 */
	private Usuario administrador;

	/**
	 * token de acceso
	 */
	private Token accessToken;

	/**
	 * Singleton
	 */
	private static SistemaGestionVideos instancia = null;
	
	// -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
	/**
	 * Constructor de la clase
	 */
	public SistemaGestionVideos()
	{
		usuarios = new GrafoDirigido<LlaveUsuario, Usuario, Amistad>(100);
		listaBusquedaActual = new ListaOrdenada<Video>();
		videosEnElSistema = new TablaHashing<Video, LlaveVideo>(1000);
		usuariosConectados = new TablaHashing<Usuario, LlaveUsuario>(100);
		administrador = new Usuario("Administrador", "admin@gmail.com", "123", true, true, false);
		videosEnTrie = new Trie<Video>();
		usuarios.agregarVertice(administrador);

	}

	/**
	 * Utiliza el patron singleton, solo se permite una instancia, este metodo la devuelve
	 * @return Devuelve la unica instancia de la clase
	 */
	public static SistemaGestionVideos getInstance()
	{
		System.out.println("getInstance");
		if( instancia == null )
		{
			System.out.println("Creando instancia");
			try
			{
				ServerConfig config = ServerConfigLocator.locate( );
				File dataDir = config.getServerDataDir();
				File tmp = new File( dataDir + RUTA_ARCHIVO_SERIALIZADO );

				System.out.println("Nombre=" + tmp.getName( ));
				System.out.println("Path=" + tmp.getPath( ));
				System.out.println("Abs. Path=" + tmp.getAbsolutePath( ));
				if ( tmp.exists( ) )
				{
					FileInputStream fis = new FileInputStream( tmp );
					ObjectInputStream ois = new ObjectInputStream( fis );
					instancia = (SistemaGestionVideos) ois.readObject( );
					ois.close();
					fis.close();
					System.out.println("QueVideo Deserializado");
				}
				else
				{
					instancia = new SistemaGestionVideos( );                    
					System.out.println("QueVideo Nuevo");
				}

			}
			catch( Exception e )
			{
				instancia = new SistemaGestionVideos( );
				System.out.println("QueVideo Nuevo");
			}
		}
		return instancia;
	}
	
	// -----------------------------------------------------------------
    // Metodos
    // -----------------------------------------------------------------
	/**
	 * Devuelve la tabla de usuarios
	 * @return La tabla de usuarios.
	 */
	public Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> darUsuarios()
	{
		return usuarios.recorridoPlano();
	}
	
	public GrafoDirigido<LlaveUsuario, Usuario, Amistad> darGrafoUsuarios()
	{
		return usuarios;
	}

	/**
	 * Devuelve la tabla de usuarios conectados
	 * @return La tabla de usuarios conectados
	 */
	public TablaHashing<Usuario, LlaveUsuario> darUsuariosConectados()
	{
		return usuariosConectados;
	}

	/**
	 * Devuelve el administrador
	 * @return el administrador.
	 */
	public Usuario darAdministrador()
	{
		return administrador;
	}

	/**
	 * Verifica si un usuario dado su mail y contrasena es administrador
	 * @param email El mail del usuario a verificar
	 * @param contrasenha La contrasena del ususario a verificar
	 * @return Un indicador diciendo si es o no administrador
	 */
	public boolean esAdministrador(String email, String contrasenha)
	{
		return administrador.darCorreoElectronico().equals(email) && administrador.verificarContresenia(contrasenha);
	}

	/**
	 * Devuelve la busqueda actual
	 * @return La busqueda actual
	 */
	public ListaOrdenada<Video> darBusquedaActual()
	{
		return listaBusquedaActual;
	}

	/**
	 * Autentica con Vimeo
	 * @return Retorna la autenticación
	 */
	public OAuthService autentificarConVimeo()
	{
		OAuthService service = new ServiceBuilder()
		.provider(VimeoApi.class)
		.apiKey(API_KEY) // replace with your API key
		.apiSecret(API_SECRET) // replace with your API secret
		.callback(CALLBACK)
		.signatureType(SignatureType.QueryString)
		.build();

		System.out.println("Obteniendo access Token");
		accessToken = new Token(VIMEO_ACCESS_TOKEN, VIMEO_ACCESS_SECRET);              
		System.out.println("Access Token Conseguido");

		// Now let's go and ask for a protected resource!
		System.out.println("Intentado loggearse");
		String protectedResourceURL = String.format(VIMEO_BASE, VIMEO_METHOD);
		OAuthRequest orequest = new OAuthRequest(Verb.GET, protectedResourceURL);
		service.signRequest(accessToken, orequest);
		Response oresponse = orequest.send();
		System.out.println("Loggeado");
		System.out.println(oresponse.getCode());
		System.out.println(oresponse.getBody());

		System.out.println();
		System.out.println("LIsto para hacer nuevos request");

		return service;
	}


	/**
	 * Registra usuario
	 * @param nNombre. El nombre del usuario
	 * @param nCorreo. El correo del usuario
	 * @param nContrasena. La contraseña del usuario
	 * @param dobleCon. La doble contraseña del usuario
	 * @throws Exceotion Bota excepción si el usuario no ha llenado todos los campos, si las contraseñas no son iguales, si ya existe un usuario con ese correo o si la direccion de correo electronico no es valida
	 */
	public Usuario registrarUsuario(String nNombre, String nCorreo, String nContrasena, String dobleCon, boolean likesPrivados, boolean fotoPrivada, boolean seguidoFacil) throws Exception
	{
		if(nNombre.trim().equals("") || nCorreo.trim().equals("") || nContrasena.trim().equals("") || dobleCon.trim().equals("") || nNombre==null || nCorreo==null || nContrasena==null || dobleCon==null)
		{
			throw new Exception("Por favor llene todos los campos");
		}

		if(dobleCon.equals(nContrasena)==false)
		{
			throw new Exception("No son iguales las dos contrase&ntilde;as");
		}

		if(!nCorreo.contains("@"))
		{
			throw new Exception("La direcci&oacute;n de correo electr&oacute;nico no es v&aacute;lida");
		}

		if(usuarios.darVertice(new LlaveUsuario(nCorreo))!=null)
		{
			throw new Exception("Ya existe un usuario con ese correo");

		}

		Usuario aAgregar = new Usuario(nNombre,nCorreo, nContrasena, likesPrivados, fotoPrivada, seguidoFacil);
		usuarios.agregarVertice(aAgregar);

		if (usuariosConectados.buscarElemento(aAgregar.darId())==null) {
			usuariosConectados.agregarElemento(aAgregar, aAgregar.darId());
		}
		return aAgregar;
	}




	/*
	 * (non-Javadoc)
	 * @see mundo.ISistemaGestionVideos#validarUsuario(java.lang.String, java.lang.String)
	 */
	/**
	 * Valida un usuario
	 * @param email, El email del usuario a validar
	 * @param contrasenaU, La contraseña del usuario a validar
	 */
	public Usuario validarUsuario(String email, String contrasenaU) 
	{
		LlaveUsuario llaveEjemplo = new LlaveUsuario (email );
		Vertice<LlaveUsuario, Usuario, Amistad> usuarioBuscado = usuarios.darVertice(llaveEjemplo);

		if( usuarioBuscado==null )
		{
			return null;
		}

		if( !usuarioBuscado.darInfo().verificarContresenia(contrasenaU) )
		{
			return null;
		}

		System.out.print("Email encontrado: "+usuarioBuscado.darId().darEmail()+" Email actual: "+email);
		if (usuariosConectados.buscarElemento(usuarioBuscado.darId())==null) {
			usuariosConectados.agregarElemento(usuarioBuscado.darInfo(), usuarioBuscado.darId());
		}
		return usuarioBuscado.darInfo();
	}

	/**
	 * Desconecta un usuario dado su email
	 * @param email El mail del usuario a desconectar
	 */
	public void desconectarUsuario( String email )
	{
		usuariosConectados.eliminarElementoPorLlave( new LlaveUsuario(email) );
	}

	
	public ListaOrdenada<Video> buscarVideosPalabraClave(String nPalabra) throws ParseException
	{
		ListaOrdenada<Video> busquedaEnTrie = videosEnTrie.buscar(nPalabra);

		if(busquedaEnTrie.darLongitud()>0)
		{
			return busquedaEnTrie;
		}

		return buscarVideosPalabraClaveVimeo(nPalabra);
	}


	/**
	 * Busca videos por palabra clave
	 * @param nPalabra La palabra clace
	 * @return Una lista ordenada de videos que contienen la palabra clave
	 * @throws ParseException Bota excepcion si ocurre un error parseando.
	 */
	public ListaOrdenada<Video> buscarVideosPalabraClaveVimeo(String nPalabra) throws ParseException 
	{
		listaBusquedaActual = new ListaOrdenada<Video>();

		OAuthService service = autentificarConVimeo();
		String nPalabraBus = nPalabra.replace(" ", "+");
		String protectedResourceURL = String.format(VIMEO_BASE, "vimeo.videos.search&query="+nPalabraBus+"&format=json&page=1&per_page=50&sort=relevant");
		System.out.println(protectedResourceURL);
		OAuthRequest myrequest = new OAuthRequest(Verb.GET, protectedResourceURL);

		service.signRequest(accessToken, myrequest);
		Response response = myrequest.send();

		JSONParser parser = new JSONParser();


		Object obj = parser.parse(response.getBody());
		JSONObject jsonObject = (JSONObject) obj;
		JSONObject videos = (JSONObject) jsonObject.get("videos");
		JSONArray arrayVideos = (JSONArray)videos.get("video");
		Iterator<JSONObject> iterVideos = arrayVideos.iterator();
		while(iterVideos.hasNext())
		{
			JSONObject actual = iterVideos.next();
			String nombreActual = (String)actual.get("title");
			String idActual = (String)actual.get("id");
			if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(nombreActual, nPalabra)) 
			{
				Video vidActual = new Video(nombreActual, idActual);
				Video vidAntiguo = videosEnElSistema.buscarElemento( vidActual.darLlaveVideo() );
				if( vidAntiguo!=null )
				{
					vidActual = vidAntiguo;
				}
				else
				{
					videosEnElSistema.agregarElemento(vidActual, vidActual.darLlaveVideo());
				}

				listaBusquedaActual.agregar(vidActual);
				System.out.println(nombreActual);
			}

		}

		return listaBusquedaActual;
	}


	/**
	 * Busca un vdeo dado del id del video
	 * @param idVideo El id del video
	 * @return El video buscado
	 */
	public Video buscarVideo(String idVideo)
	{
		Video resp = videosEnElSistema.buscarElemento(new LlaveVideo(idVideo));
		System.out.println(resp);
		return resp;	
	}

	/*
	 * (non-Javadoc)
	 * @see mundo.ISistemaGestionVideos#guardarBusqueda(java.lang.String)
	 */
	public void guardarBusqueda(String  palabraClave) 
	{
		Iterator<Video> iterVidBusActual = listaBusquedaActual.iterator();
		videosEnTrie.agregar(palabraClave, iterVidBusActual);
		iterVidBusActual = listaBusquedaActual.iterator();
		while(iterVidBusActual.hasNext())
		{
			System.out.println("Entro a guardar ind");
			Video vidActual = iterVidBusActual.next();
			vidActual.agregarIndice(palabraClave);
		}

		listaBusquedaActual = new ListaOrdenada<Video>();
	}

	/*
	 * (non-Javadoc)
	 * @see mundo.ISistemaGestionVideos#likearUnVideo(mundo.Usuario, mundo.Video)
	 */
	public void likearUnVideo(Usuario usuarioLike, Video videoLike) 
	{
		if(usuarioLike.darTablaVideosLikes().darDato().buscarElemento(videoLike.darLlaveVideo())==null)
		{
			videoLike.registrarLike(usuarioLike);
			usuarioLike.darTablaVideosLikes().darDato().agregarElemento(videoLike, videoLike.darLlaveVideo());
			
			Iterator<String> etiquetasVideo = videoLike.darIndices().iterator();
			while(etiquetasVideo.hasNext())
			{
				String etActual = etiquetasVideo.next();
				System.out.println("La etiqueta actual es: "+etActual);
				usuarioLike.agregarEtiqueta(etActual);
			}
		}

	}

	/**
	 * Registra una reproduccion en el video
	 * @param llaveVideo La llave del video a dar la reproduccion
	 */
	public void registrarReproduccion( String llaveVideo )
	{
		Video videoRep = videosEnElSistema.buscarElemento( new LlaveVideo(llaveVideo) );
		videoRep.registrarReproduccion();
	}

	/**
	 * Devuelve una lista ordenadad de los 10 videos con mas likes
	 * @return Una lista ordenada de videos con los 10 videos con mas likes
	 */
	public ListaOrdenada<Video> darVideosMasLikes()
	{
		ListaOrdenada<Video> resp = new ListaOrdenada<Video>();

		Iterator<Video> iterVideos = videosEnElSistema.iterator();
		while(iterVideos.hasNext())
		{
			ComparadorVideosPorLike comp = new ComparadorVideosPorLike();
			Video vidActual = iterVideos.next();
			resp.agregarPorCriterio(vidActual, comp);
		}

		return resp;
	}

	/**
	 * Devuelve una lista ordenada de los 20 videos mas vistos
	 * @return lista ordenada de los 20 videos mas vistos
	 */
	public ListaOrdenada<Video> darVideosMasVistos()
	{
		ListaOrdenada<Video> resp = new ListaOrdenada<Video>();

		Iterator<Video> iterVideos = videosEnElSistema.iterator();
		while(iterVideos.hasNext())
		{
			ComparadorVideosPorVistas comp = new ComparadorVideosPorVistas();
			Video vidActual = iterVideos.next();
			resp.agregarPorCriterio(vidActual, comp);
		}

		return resp;
	}

	/*
	 * (non-Javadoc)
	 * @see mundo.ISistemaGestionVideos#comentarUnVideo(java.lang.String, mundo.Video, mundo.Usuario)
	 */
	public void comentarUnVideo(String comentario, Video aComentar, Usuario autor) 
	{
		Comentario nuevoCom = new Comentario(comentario, autor.darNombreUsuario());
		aComentar.darComentarios().encolar(nuevoCom);
	}
	
	/**
	 * Busca todos los usuarios que contengan la palabra clave en su correo o en su nombre, dependiendo de lo que 
	 * escogio el usuario
	 * @param palabraClave La palabra por la que se desea buscar
	 * @param porCorreo true si se desea buscar por correo, false si se desea buscar por nombre
	 * @return Una lista con todos los usuarios que contienen la palabra clave
	 */
	public Lista<Usuario> buscarUsuariosPorPalabraClave(String palabraClave, String criterio)
	{
		Lista<Usuario> resp = new Lista<Usuario>();
		
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterVertices = usuarios.recorridoPlano();
		while(iterVertices.hasNext())
		{
			Usuario usuarioActual = iterVertices.next().darInfo();
			if(criterio.equals(POR_EMAIL) )
			{
				if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(usuarioActual.darCorreoElectronico(), palabraClave))
				{
					resp.agregar(usuarioActual);
				}
			}
			
			else
			{
				if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(usuarioActual.darNombreUsuario(), palabraClave))
				{
					resp.agregar(usuarioActual);
				}
			}
		}
		
		return resp;
	}
	
	/**
	 * Devuelve un camino en cualquier sentido entre dos usuarios
	 * @param emailX El usuario que esta realizando conectado
	 * @param emailY El usuario que esta siendo visitado por el conectado
	 * @return
	 */
	public Camino<LlaveUsuario, Usuario, Amistad> darCaminoEntreUsuarios(String emailX, String emailY)
	{
		Camino<LlaveUsuario, Usuario, Amistad> resp = usuarios.darCaminoSimpleMasCorto(new LlaveUsuario(emailX), new LlaveUsuario(emailY));
		
		if(resp==null)
		{
			usuarios.darCaminoSimpleMasCorto(new LlaveUsuario(emailY), new LlaveUsuario(emailX));
		}
		
		return resp;
	}
	
	/**
	 * Adiciona un videoContaco a un usuario. Si el usuario destino no es seguido libremente, se adiciona el usuario 
	 * origen a la lista de espera
	 * @param emailOrigen El usuario que quiere seguir
	 * @param emailDestino El usuario a ser seguido
	 */
	public void adicionarVideoContadoAUsuario(String emailOrigen, String emailDestino)
	{
		Usuario usuarioDestino = usuarios.darVertice(new LlaveUsuario(emailDestino)).darInfo();
		if(usuarioDestino.esSeguidoLibremente())
		{
			usuarios.agregarArco(new LlaveUsuario(emailOrigen), new LlaveUsuario(emailDestino), new Amistad());
		}
		
		else
		{
			usuarioDestino.agregarAListaDeEspera(new LlaveUsuario(emailOrigen));
		}
	}
	
	/**
	 * Acepta una solicitud que estaba en la lista de pendiente del usuario destino
	 * @param emailOrigen El usuario que quiere seguir
	 * @param emailDestino El usuario seguido
	 */
	public void aceptarVideoContacto(String emailUsuario, String emailUsuarioAceptar)
	{
		Usuario usuario = usuarios.darVertice(new LlaveUsuario(emailUsuario)).darInfo();
		usuario.eliminarSolicitud(new LlaveUsuario(emailUsuarioAceptar));
		usuarios.agregarArco(new LlaveUsuario(emailUsuarioAceptar), new LlaveUsuario(emailUsuario), new Amistad());
	}
	
	public void rechazarVideoContaco(String emailUsuario, String emailUsuarioRechazar)
	{
		Usuario usuario = usuarios.darVertice(new LlaveUsuario(emailUsuario)).darInfo();
		usuario.eliminarSolicitud(new LlaveUsuario(emailUsuarioRechazar));
	}
	
	/**
	 * Le suguiere a un usuario los 10 usuarios mas cercanos a el que el no sigue
	 * @param llaveUsuarioOrigen La llave del usuario al que se le quieren sugerir usuarios
	 * @return Una lista con los usuarios sugeridos
	 */
	public Lista<Usuario> darSugerenciasDeUsuarios(String llaveUsuarioOrigen)
	{
		Lista<Usuario> listaSugerencias = new Lista<Usuario>();
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterBFS = usuarios.recorridoXNiveles(new LlaveUsuario(llaveUsuarioOrigen)).iterator();
		
		int i = 0;
		while(iterBFS.hasNext() && i<10)
		{
			Vertice<LlaveUsuario, Usuario, Amistad> verActual = iterBFS.next();
			if(verActual.darDistancia()>1.0)
			{
				listaSugerencias.agregar(verActual.darInfo());
				i++;
			}
		}
		
		return listaSugerencias;
		
 	}
	
	/**
	 * Le suguiere a un usuario 10 videos que esten cercanos a el por sus videos contactos en donde cada video o
	 * tiene mas de 5 rep o tiene un tag que le gusta al usuario.
	 * @param llaveUsuarioOrigen Llave del usuario al que se le quieren sugerir los videos
	 * @return Una lista con los videos sugeridos
	 */
	public Lista<Video> darSugerenciasDeVideos(String llaveUsuarioOrigen)
	{
		Lista<Video> listaSugerencias = new Lista<Video>();
		Usuario usuarioOrigen = usuarios.darVertice(new LlaveUsuario(llaveUsuarioOrigen)).darInfo();
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> iterBFS = usuarios.recorridoXNiveles(new LlaveUsuario(llaveUsuarioOrigen)).iterator();
		
		int i = 0;
		while(iterBFS.hasNext() && i<10)
		{
			Vertice<LlaveUsuario, Usuario, Amistad> verActual = iterBFS.next();
			if(verActual.darDistancia()>0)
			{
				Iterator<Video> videosActual = verActual.darInfo().darTablaVideosLikes().darDato().iterator();
				while(videosActual.hasNext())
				{
					Video vidActual = videosActual.next();
					if(usuarioOrigen.darTablaVideosLikes().darDato().buscarElemento(vidActual.darLlaveVideo())==null)
					{
						if(vidActual.darNumeroVistas()>5)
						{
							listaSugerencias.agregar(vidActual);
							i++;
						}
						else
						{
							Iterator<String> iterIndicesVideoActual = vidActual.darIndices().iterator();
							while(iterIndicesVideoActual.hasNext())
							{
								if(usuarioOrigen.tieneIndice(iterIndicesVideoActual.next()))
								{
									listaSugerencias.agregar(vidActual);
									i++;
								}
							}
						}
					}
				}
			}
		}
		
		return listaSugerencias;
	}
	
	/**
	 * Devuelve los usuarios que no siguen a nadie pero son seguidos
	 * @return Una lista con los usuarios influencer
	 */
	public Lista<Vertice<LlaveUsuario, Usuario, Amistad>> darUsuariosInfluencer()
	{
		return usuarios.darSumideros();
	}
	
	/**
	 * Devuelve los usuarios que no son seguidos pero siguen a alguien
	 * @return Una lista con los usuarios stalker
	 */
	public Lista<Vertice<LlaveUsuario, Usuario, Amistad>> darUsuariosStalker()
	{
		return usuarios.darFuentes();
	}
	
	/**
	 * Devuelve los usuarios que no siguen y nadie los sigue
	 * @return Una lista con los usuarios forever alone
	 */
	public Lista<Vertice<LlaveUsuario, Usuario, Amistad>> darUsuariosForeverAlone()
	{
		return usuarios.darVerticesAislados();
	}
	
	/**
	 * Devuelve el camino mas largo de toda la red
	 * @return El camino mas largo
	 */
	public Camino<LlaveUsuario, Usuario, Amistad> darCaminoMasLargoEntreUsuarios()
	{
		return usuarios.darCaminoSimpleMasLargoDelGrafo();
	}
	
	/**
	 * Devuelve el usuario mas popular
	 * @return El usuario con mas seguidores y usuarios que lo siguen
	 */
	public Usuario darUsuarioMasPopular()
	{
		usuarios.eliminarVertice(administrador.darId());
		Usuario resp = null;
		if (usuarios.darVerticeConMasArcos()!=null) 
		{
			resp = usuarios.darVerticeConMasArcos().darInfo();
		}
		else
		{
			resp = null;
		}
		return resp;
	}
	
	/**
	 * Devuelve al usuario con el que se puede acceder a todos los otros sin repetir vertices
	 * @return El usuario hamilton
	 */
	public Usuario darUsuarioHamilton()
	{
		usuarios.eliminarVertice(administrador.darId());
		Usuario resp = null;
		if (usuarios.darCaminoHamilton()!=null) 
		{
			resp = usuarios.darCaminoHamilton().darVertices().next().darInfo();
		}
		else
		{
			resp= null;
		}
		usuarios.agregarVertice(administrador);
		return resp;
	}
	
	/**
	 * Desconecta a todos los usuarios
	 */
	public void desconectarTodosLosUsuario() 
	{
		usuariosConectados = new TablaHashing<Usuario, LlaveUsuario>(100);
	}
	
	/**
	 * Devuelve el camino entre dos usuarios con un usuario intermedio especifico
	 * @param usuarioOrigen El id del usuario de origen
	 * @param usuarioDestino El id del usuario destino
	 * @param usuarioIntermedio El id del usuario intermedio
	 * @return
	 */
	public Camino<LlaveUsuario, Usuario, Amistad> darCaminoConUsuarioIntermedio(LlaveUsuario usuarioOrigen, LlaveUsuario usuarioDestino, LlaveUsuario usuarioIntermedio)
	{
		return usuarios.darCaminoConVerticeIntermedio(usuarioOrigen, usuarioIntermedio, usuarioDestino);
	}
	
	public Lista<Usuario> agregarAutomaticamenteSeguidoresDeNivel( int nNivel, LlaveUsuario fuente )
	{
		Lista<Usuario> resp = new Lista<Usuario>();
		
		Iterator<Vertice<LlaveUsuario, Usuario, Amistad>> bfsAlReves = usuarios.bfsAlReves(fuente).iterator();
		
		while(bfsAlReves.hasNext())
		{
			Vertice<LlaveUsuario, Usuario, Amistad> verActual = bfsAlReves.next();
			System.out.println("-----Ver Actual: "+verActual.darId().darEmail()+" dis: "+verActual.darDistancia());
			if(verActual.darDistancia()==nNivel)
			{
				
				resp.agregar(verActual.darInfo());
				usuarios.agregarArco(verActual.darId(), fuente, new Amistad());
			}
		}
		
		return resp;
	}

	public static void main( String[] args)
	{	



		SistemaGestionVideos sis = new SistemaGestionVideos();
		Usuario nuevoUsuario = new Usuario("Sebastian Sierra Cuervo", "sebastian.sierra1993@gmail.com", "123456", false, false, true);
		sis.usuarios.agregarVertice(nuevoUsuario);
		System.out.println(nuevoUsuario.darId().hashCode());
		Usuario usuarioResultado = sis.validarUsuario("sebastian.sierra1993@gmail.com", "123456");

		try {
			sis.buscarVideosPalabraClave("millonarios");
			System.out.println("Busco videos");
		} catch (ParseException e) {

			e.printStackTrace();
		}

	}


}
