/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioCloperadorInterface extends Remote {
	//Interface del servicio Cliente operador. Se implemeta en el repositorio.
	
	public boolean Subir_fichero(int id_cli, String nombre_fichero, Fichero FICHERO) throws RemoteException, NotBoundException, FileNotFoundException, IOException;
		//Sube al repositorio desde el cliente id, un fichero.
	
	public void borrar_fichero(int id_cli, String nombre_fichero) throws RemoteException, NotBoundException;
		//Borra del repositorio un fichero del cliente id.
	
	public void set_id_repositorio(int id) throws RemoteException;
		//Almacena el identificador del repositorio
}
