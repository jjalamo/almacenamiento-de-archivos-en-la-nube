/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.servidor;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cloud.common.Fichero;
import cloud.common.ServicioDatosInterface;
import cloud.common.ServicioGestorInterface;
import cloud.common.ServicioSroperadorInterface;

//---------------------------------------------
public class ServicioGestorImpl implements ServicioGestorInterface{
	//implementacion de la interface ServicioGestorInterface
	
	private static ServicioDatosInterface servidorDatos; //servicio de Datos
	private static ServicioSroperadorInterface servidorSroperador; //servicio Sroperador
	
//------------------------------------------
	public String Localizar_repositorio(int id_cli) throws RemoteException, NotBoundException {
		//devuelve el nombre del servicio Cloperador del repositorio asignado
		//al cliente id_cli
		
		int id_rep=0; //identificador de repositorio
		String nombre_servicioCloperador=null; //nombre del servicio Cloperador
		
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		
		//llama al servicio de datos para obtener el identificador del repositorio
		//asignado al cliente id_cli
		id_rep=servidorDatos.lista_clirep_get_idrepositorio(id_cli);
		//obtiene el nombre del servicio Cloperador del repositorio y lo devuelve
		nombre_servicioCloperador="ServidorCloperador".concat(Integer.toString(id_rep));
		
		return nombre_servicioCloperador;
	}
//------------------------------------------

//------------------------------------------
	public void Bajar_fichero(int id_cli, String nombre_fichero, String cliente) throws RemoteException, NotBoundException {
		//Gestiona las acciones implicadas en la descarga de archivos desde un repositorio
		//al cliente id_cli
		
		int id_rep=0; //identificador de repositorio
		String nombre_servicioSroperador=null; //nombre del servicio Sroperador
		
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		
		//llama al servicio de Datos para obtener el identificador del repositorio
		//asignado al cliente id_cli
		id_rep=servidorDatos.lista_clirep_get_idrepositorio(id_cli);
		//obtiene el nombre del servicio Sroperador del repositorio asignado
		//al cliente id_cli
		nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(id_rep));
		//busca el servicio Sroperador del repositorio asignado al cliente id_cli
		Registry registrySroperador = LocateRegistry.getRegistry();
		servidorSroperador = (ServicioSroperadorInterface) registrySroperador.lookup(nombre_servicioSroperador);
		//llama al servicio Sroperador del repositorio asignado al cliente id_cli
		//y le indica el fichero a descargar y el cliente que lo solicita
		servidorSroperador.descargar_fichero(id_cli, nombre_fichero, cliente);
	}
//------------------------------------------

//------------------------------------------
	public List<String> listar_ficheros(int id_cli)	throws RemoteException, NotBoundException {
		//devuelve una lista de los ficheros del cliente id_cli, almacenados en el 
		//repositorio que tiene asignado
		
		List<String> lista_fich = new ArrayList<String>();//lista de ficheros del cliente
		int id_rep=0; //identificador de repositorio
		String nombre_servicioSroperador=null; //nombre del servicio Sroperador
		
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		//llama al servicio de datos para obtener el identificador del repositorio
		//asignado al clietne id_cli
		id_rep=servidorDatos.lista_clirep_get_idrepositorio(id_cli);
		//obtiene el nombre del servicio Sroperador del repositorio asignado
		//al cliente id_cli
		nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(id_rep));
		//busca el servicio Sroperador del repositorio asignado al cliente id_cli
		Registry registrySroperador = LocateRegistry.getRegistry();
		servidorSroperador = (ServicioSroperadorInterface) registrySroperador.lookup(nombre_servicioSroperador);
		//llama al servicio Sroperador del repositorio asignado al cliente id_cli
		//para obtener una lista de ficheros que tiene almacenado el cliente id_cli en 
		//el repositorio y devuelve la lista.
		lista_fich=servidorSroperador.lista_ficheros_obtener_lista_ficheroscli(id_cli);

		return lista_fich;
	}
//------------------------------------------

//------------------------------------------
	public Map<Integer, String> listar_clientes() throws RemoteException, NotBoundException {
		//Devuelve una lista de clientes conectados al sistema
		
		Map<Integer,String> lista_clientes=null;//lista de clientes a devolver
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		//llama al servicio de Datos para obtener una lista de clientes 
		//conectados al sistema y la devuelve
		lista_clientes=servidorDatos.lista_clientes_obtener_lista();
		
		return lista_clientes;
	}
//------------------------------------------

//------------------------------------------
	public List<Fichero> listar_metadatos(int id_cli) throws RemoteException, NotBoundException {
		//devuelve una lista con los metadatos de los ficheros del cliente id_cli
		//almacenados en el repositorio asignado al cliente id_cli
		
		List<Fichero> lista_fich = new ArrayList<Fichero>(); //lista de metadatos de ficheros, a devolver
		int id_rep=0; //identificador de repositorio
		String nombre_servicioSroperador=null; //nombre del servicio Sroperador
		
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		//llama al servicio de Datos para obtener el identificador del repositorio
		//asignado al cliente id_cli
		id_rep=servidorDatos.lista_clirep_get_idrepositorio(id_cli);
		//obtiene el nombre del servicio Sroperador del repositorio asignado al cliente id_cli
		nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(id_rep));
		//busca el servicio Sroperador del repositorio asignado al cliente id_cli
		Registry registrySroperador = LocateRegistry.getRegistry();
		servidorSroperador = (ServicioSroperadorInterface) registrySroperador.lookup(nombre_servicioSroperador);
		//llama al servicio Sroperador para obtener una lista de los metadatos de los ficheros
		//alamcenados en el repositorio asignado al cliente id_cli y devuelve la lista
		lista_fich=servidorSroperador.listar_ficheros_metadatos(id_cli);
		return lista_fich;
	}
//------------------------------------------
}
