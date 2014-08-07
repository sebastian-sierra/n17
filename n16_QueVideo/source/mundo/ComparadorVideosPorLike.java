package mundo;

import java.util.Comparator;

public class ComparadorVideosPorLike implements Comparator<Video>
{

	/**
	 * Compara dos videos por like
	 * @return un indicador de la resta entre sus likes
	 */
	public int compare(Video v1, Video v2) 
	{
		return v2.darLikes()-v1.darLikes();
	}
	
}
