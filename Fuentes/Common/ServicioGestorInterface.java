/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ServicioGestorInterface extends Remote {
	//Interface del servicio Gestor. Se implementa en el servidor
	
	public String Localizar_repositorio(int id_cli) throws RemoteException, NotBoundException;
		//Devuelve la direccion remota del repositorio asignado al cliente id_cli
	
	public void Bajar_fichero(int id_cli, String nombre_fichero, String cliente) throws RemoteException, NotBoundException;
		//Gestiona la accion de bajar fichero desde un repositorio al cliente, si hacer la descarga fisica.
	
	public List<String> listar_ficheros(int id_cli) throws RemoteException, NotBoundException;
		//Devuelve una lista de los ficheros del cliente id_cli almacenados en el repositorio
	
	public Map<Integer, String> listar_clientes() throws RemoteException, NotBoundException;
		//Devuelve una lista de clientes conectados
	
	public List<Fichero> listar_metadatos(int id_cli) throws RemoteException, NotBoundException;
		//Devuelve una lista con los metadatos de los ficheros del cliente id_clip almacenados en el repositorio.
}
