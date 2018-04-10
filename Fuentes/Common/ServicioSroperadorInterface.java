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

public interface ServicioSroperadorInterface extends Remote {
	//Interface del servicio Servidor Operador. Se implementa en el repositorio
	
	public void descargar_fichero(int id_cli, String nombre_fichero, String nombre_servicioDiscocliente) throws RemoteException, NotBoundException;
		//Descarga un fichero desde el repositorio al cliente
	
	public void lista_clientes_set_cliente(int id_cli, String nombre) throws RemoteException;
		//Introduce un cliente en la lista local de clientes del repositorio
	
	public Map<Integer, String> lista_clientes_obtener_lista() throws RemoteException;
		//Devuelce la lista local de clientes del repositorio
	
	public void lista_ficheros_set_fichero(int id_cli, String nombre_fichero) throws RemoteException;
		//Introduce el nombre de un fichero en la lista local de ficheros del repositorio
	
	public void lista_ficheros_borrar_fichero(int id_cli, String nombre_fichero) throws RemoteException;
		//Borra un fichero de la lista local de ficheros del repositorio
	
	public Map<Integer, List<String>> lista_ficheros_obtener_lista() throws RemoteException;
		//Devuelve la lista local de ficheros del repositorio
	
	public List<String> lista_ficheros_obtener_lista_ficheroscli(int id_cli) throws RemoteException;
		//Devuelve una lista con los nombres de los ficheros almacenados en el repositorio del cliente id_cli
	
	public void set_id_repositorio(int id) throws RemoteException;
		//Introduce el identificador del repositorio
	
	public void crear_directorio(int id_cli) throws RemoteException;
		//Crea en el repositorio un directorio para el cliente id_cli
	
	public List<Fichero> listar_ficheros_metadatos(int id_cli) throws RemoteException;
		//Devuelve una lista con los metadatos de los ficheros del cliente id_cli, almacenados en el repositorio.
}
