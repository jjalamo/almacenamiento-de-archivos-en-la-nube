/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.repositorio;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import cloud.common.Fichero;
import cloud.common.ServicioDiscoclienteInterface;
import cloud.common.ServicioSroperadorInterface;

//------------------------------------------------
public class ServicioSroperadorImpl implements ServicioSroperadorInterface {
	//implementacion de la interface ServicioSroperadorInterface
	
	private static ServicioDiscoclienteInterface servidorDiscocliente; //servicio disco cliente
 	private static Map<Integer, String> lista_clientes = new HashMap<Integer, String>(); //lista local de clientes asociados al repositorio
 	private static Map<Integer, List<String>> lista_ficheros = new HashMap<Integer, List<String>>(); //lista local de ficheros almacenados en el repositorio
 	@SuppressWarnings("unused")
	private static int id_repositorio; //identificador del repositorio 
 	
//------------------------------------------------
 	public void descargar_fichero(int id_cli, String nombre_fichero, String nombre_servicioDiscocliente) throws RemoteException, NotBoundException {
 		//descarga un fichero del repositorio al clieente id_cli
 		
		String ruta=null; //ruta donde se encuentra almacenado el fichero
		String nombreCliente=null; //nombre del cliente
		List<String> lista_fich = new ArrayList<String>(); //lista de ficheros del cliente
		Fichero mi_fichero; //fichero a descargar
		
		//obtiene acceso al servicio disco cliente del cliente al que se le va a mandar el fichero
		Registry registryDiscocliente = LocateRegistry.getRegistry();
		servidorDiscocliente = (ServicioDiscoclienteInterface) registryDiscocliente.lookup(nombre_servicioDiscocliente);
		
		//obtiene una lista de los ficheros del cliente id_cli almacenados en el repositorio
		//y localiza el fichero a descagar
		lista_fich=lista_ficheros.get(id_cli);
		if(lista_fich.contains(nombre_fichero)) {
			//obtiene la ruta donde esta almacenado el fichero a descargar
			ruta=Integer.toString(id_cli) + "//" + nombre_fichero;
			//obtiene el nombre del cliente propietario del fichero
			nombreCliente=lista_clientes.get(id_cli);
			//obtiene el fichero a descargar
			mi_fichero = new Fichero(ruta, nombreCliente);
			//descarga el fichero accediendo al servicio disco cliente del cliente que va a recibir el fichero
			if(servidorDiscocliente.descargar_fichero(nombre_fichero, mi_fichero)==false) {
				System.out.println("Error, el fichero no se ha descargado.");
			}
		}
 	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public void lista_clientes_set_cliente(int id_cli, String nombre) throws RemoteException {
		//introduce un cliente en la lista local de clientes del repositorio
		lista_clientes.put(id_cli, nombre);
	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public Map<Integer, String> lista_clientes_obtener_lista() throws RemoteException {
		//devuelve la lista local de clientes asociados al repositorio
		return lista_clientes;
	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public void lista_ficheros_borrar_fichero(int id_cli, String nombre_fichero) throws RemoteException {
		//borra un fichero del cliente id_cli, almacenado en el repositorio, de la lista local de ficheros
		
		List<String> lista= new ArrayList<String>(); //lista de ficheros del cliente almacenados en el repositorio

		//comprueba si id_cli esta asociado al repositorio
		if(lista_ficheros.containsKey(id_cli)) {
			//obtiene la lista de ficheros del cliente id_cli almacenados en el repositorio
			lista=lista_ficheros.get(id_cli);
			//Comprueba si el fichero a borrar esta almacenado en el repositorio
			if(lista.contains(nombre_fichero)) {
				//borra el fichero de la lista de ficheros almacenados en el repositorio
				lista.remove(nombre_fichero);
				lista_ficheros.put(id_cli, lista);
			}
		}
	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public void lista_ficheros_set_fichero(int id_cli, String nombre_fichero) throws RemoteException {
		//añade un fichero a la lista local de ficheros almacenados en el repositorio
		
		List<String> lista= new ArrayList<String>(); //lista de ficheros del cliente id_cli almacenados en el repositorio
		
		//comprueba si el cliente tiene ficheros almacenados en el repositorio
		if(!lista_ficheros.containsKey(id_cli)) {
			//añade el fichero a la lista local del repositorio
			lista.add(nombre_fichero);
			lista_ficheros.put(id_cli, lista);
		}else{
			//si el cliente ya tiene ficheros almacenados en el repositorio,
			//obtiene la lista de ficheros del cliente, almacenados en el repositorio
			lista=lista_ficheros.get(id_cli);
			//Comprueba si el fichero existe en la lista
			if(!lista.contains(nombre_fichero)) {
				//añade el fichero a la lista local del repositorio
				lista.add(nombre_fichero);
				lista_ficheros.put(id_cli, lista);
			}
		}
	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public Map<Integer, List<String>> lista_ficheros_obtener_lista() throws RemoteException {
		//devuelve la lista local de ficheros almacenados en el repositorio
		return lista_ficheros;
	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public List<String> lista_ficheros_obtener_lista_ficheroscli(int id_cli) throws RemoteException {
		//devuelve una lista de los ficheros almacenados en el repositorio que pertenecen al cliente id_cli
		return lista_ficheros.get(id_cli);
	}
//------------------------------------------------------------- 	

//------------------------------------------------------------- 	
	public void set_id_repositorio(int id) throws RemoteException {
		//almacena el identificador del repositorio
		id_repositorio=id;
	}
//------------------------------------------------------------- 	
	
//------------------------------------------------------------- 	
	public void crear_directorio(int id_cli) throws RemoteException {
		//crea un directorio en el repositorio con el nombre del identificador del cliente
		
		File directorio; //directorio a crear
		String nombre=null; //nombre del directorio
		
		try {
			//crea el directorio en disco
			nombre=Integer.toString(id_cli);
			directorio= new File(nombre);
			directorio.mkdir();
		}catch (Exception e) {
			System.out.println("Error al crear directorio.");
		}
	}
//------------------------------------------------------------- 

//------------------------------------------------------------- 
	public List<Fichero> listar_ficheros_metadatos(int id_cli) throws RemoteException {
		//devuelve una lista con los metadatos de los ficheros almacenados en el repositorio
		//que son propiedad del cliente id_cli
		
		List<String> lista = new ArrayList<String>(); //lista de ficheros del cliente id_cli
		List<Fichero> metadatos = new ArrayList<Fichero>(); //lista de metadatos de los ficheros del cliente id_cli
		String ruta=null; //ruta donde se almacenan los ficheros del cliente id_cli
		String nombre_fichero=null; //nombre de fichero
		String nombre_cli=null; //nombre del cliente
		int num_ele=0; //numero de elementos en la lista
		int i=0; //contador de bucle
		Fichero fichero; //Fichero
		
		//obtiene la lista de ficheros del cliente id_cli almacenados en el repositorio
		lista=lista_ficheros.get(id_cli);
		//obtiene el numero de ficheros del cliente id_cli, el numero de elementos de la lista
		num_ele=lista.size();
		//obtiene la ruta donde se almacenan los ficheros del cliente
		ruta=Integer.toString(id_cli);
		//obtiene el nombre del cliente
		nombre_cli=lista_clientes.get(id_cli);
		
		//para cada fichero del cliente....
		for(i=0;i<num_ele;i++) {
			//obtiene el nombre del fichero
			nombre_fichero=lista.get(i);
			//obtiene los metadatos del fichero y los añade a la lista metadatos
			fichero= new Fichero(ruta, nombre_fichero, nombre_cli);
			metadatos.add(fichero);
		}
		return metadatos;
	}
//------------------------------------------------------------- 
	
}
