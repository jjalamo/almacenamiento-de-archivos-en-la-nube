/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioDiscoclienteInterface extends Remote{
	//Interface del servicio Disco cliente. Se implementa en el cliente
	public boolean descargar_fichero(String nombre_fichero, Fichero fichero) throws RemoteException;
		//Descarga un fichero desde el repositorio al cliente
}
