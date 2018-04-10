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
import java.util.Map;

public interface ServicioDatosInterface extends Remote {
	//Interface del servicio de Datos. Se implementa en el servidor
	

	public void lista_clientes_set_cliente(int id, String nombre) throws RemoteException;
		//Introduce un cliente en la lista de clientes
	
	public int lista_clientes_get_idcliente(String nombre) throws RemoteException;
		//Devuelve el id de cliente de la lista de clientes
	
	public String lista_clientes_get_nombrecliente(int id) throws RemoteException;
		//Devuelve el nombre del cliente id de la lista de clientes
	
	public void lista_clientes_borrar_cliente(int id) throws RemoteException;
		//Borra al cliente id de la lista de clientes.
	
	public int lista_clientes_get_numclientes() throws RemoteException;
		//Devuelve el numero de clientes registrados en la lista de clientes.

	public Map<Integer, String> lista_clientes_obtener_lista() throws RemoteException;
		//Devuelve la lista de clientes

	
	public void lista_repositorios_set_repositorio(int id, String nombre) throws RemoteException;
		//Introduce un repositorio en la lista de repositorios.
	
	public int lista_repositorios_get_idrepositorio(String nombre) throws RemoteException;
		//Devuelve el identificador de repositorio de un repositorio de la lista de repositorios
	
	public String lista_repositorios_get_nombrerepositorio(int id) throws RemoteException;
		//Devuelve el nombre del repositorio id de la lista de repositorios
	
	public void lista_repositorios_borrar_repositorio(int id) throws RemoteException;
		//Borra al repositorio id de la lista de repositorios
	
	public int lista_repositorios_get_numrepositorios() throws RemoteException;
		//Devuelve el numero de repositorios registrados en la lista de repositorios
	
	public Map<Integer, String> lista_repositorios_obtener_lista() throws RemoteException;
		//Devuelve la lista de repositorios
	

	public void lista_clirep_set_clirep(int idcli, int idrep) throws RemoteException;
		//Introduce una relacion de cliente repositorio en la lista de clientes repositorios
	
	public int lista_clirep_get_idrepositorio(int idcli) throws RemoteException;
		//Devuelve el identificador del repositorio asignado al cliente idcli
	
	public int lista_clirep_get_idcliente(int idrep) throws RemoteException;
		//devuelve un identificador de cliente que este asociado al repositorio idrep

	public void lista_clirep_borrar_clirep(int idcli) throws RemoteException;
		//Borra de la lista clientes repositorio las asociaciones del cliente idcli
	
	public int lista_clirep_get_numclirep() throws RemoteException;
		//Devuelve el numero de asociaciones cliente repositorio
	
	public Map<Integer, Integer> lista_clirep_obtener_lista() throws RemoteException;
		//Devuelve la lista de asociaciones cliente repositorio
	
	public int repositorio_menos_cargado() throws RemoteException;
		//Devuelve el identificador del repositorio menos cargado

	
}

 