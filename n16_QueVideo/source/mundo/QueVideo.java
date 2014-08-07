package mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class QueVideo 
{	
	/**
	 * Servicio de autenticación
	 */
	private OAuthService service;
	
	/**
	 * Token de acceso
	 */
	private Token accessToken;
	
	/**
	 * Ruta de archivo de serialización
	 */
	private final static String RUTA_ARCHIVO_SERIALIZACION = "data/quevideo.data";


	/**
	 * Constructor de la clase QueVideo
	 * @throws Exception Bota excepcion si hay algun error leyendo el archivo
	 */
	public QueVideo() throws Exception
	{
		File archivo = new File(RUTA_ARCHIVO_SERIALIZACION);
		if(archivo.exists())
		{
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream(archivo));
			//service = (OAuthService)ois.readObject();
			service = new ServiceBuilder().provider(VimeoApi.class).apiKey("b4cab8d828b0eaa53c493091a31de50d7bcdf6e9").apiSecret("01486d2568039f60bf21c86e7aaee4551d9417ec").build();
			accessToken = (Token)ois.readObject();
			ois.close();
		}
		else 
		{
			service = new ServiceBuilder().provider(VimeoApi.class).apiKey("b4cab8d828b0eaa53c493091a31de50d7bcdf6e9").apiSecret("01486d2568039f60bf21c86e7aaee4551d9417ec").build();
			Token requestToken = service.getRequestToken();
			String authUrl = service.getAuthorizationUrl(requestToken);
			System.out.println(authUrl);
			String  verif = JOptionPane.showInputDialog("Ingrese el codigo");
			Verifier v = new Verifier(verif);
			accessToken = service.getAccessToken(requestToken, v);
		}
	}


	public void guardar() throws Exception
	{
		File f= new File(RUTA_ARCHIVO_SERIALIZACION);
		f.createNewFile();
		ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(accessToken);
		oos.close();

	}

	public static void main( String[] args)
	{	


		try {
			QueVideo queVideo = new QueVideo();

			OAuthRequest request = new OAuthRequest(Verb.GET, "http://vimeo.com/api/rest/v2?format=json&method=vimeo.videos.search&page=1&per_page=50&query=microdancing&sort=relevant");
			queVideo.service.signRequest(queVideo.accessToken, request); // the access token from step 4
			Response response = request.send();
			System.out.println(response.getBody());

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
				System.out.println(nombreActual);
			}
			
			queVideo.guardar();
			//System.out.println(busqueda);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
