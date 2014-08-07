package mundo;

import java.util.Comparator;

public class ComparadorVideosPorVistas implements Comparator<Video>
{
	/**
	 * Compara dos videos por numero de vistas
	 * @return el resultado de la resta entre las vistas de estos
	 */
	public int compare(Video v1, Video v2) 
	{
		return v2.darNumeroVistas()-v1.darNumeroVistas();
	}
}
