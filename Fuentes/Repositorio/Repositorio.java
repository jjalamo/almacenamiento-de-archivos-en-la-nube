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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cloud.common.Fichero;
import cloud.common.Gui;
import cloud.common.ServicioAutenticacionInterface;
import cloud.common.ServicioCloperadorInterface;
import cloud.common.ServicioSroperadorInterface;
import cloud.common.Utilis;

public class Repositorio {
	private static int miSesion=0; //identificador del repositorio
	private static ServicioAutenticacionInterface servidorAutenticar; //Servicio de autenticacion
	private static ServicioSroperadorInterface servidorSroperador; //Servicio Sroperador
	private static ServicioCloperadorInterface servidorCloperador; //Servicio Cloperador
	private static String nombre_servicioCloperador=null; //nomnre del servicio Cloperador
	private static String nombre_servicioSroperador=null; //nombre del servicio Sroperador

//---------------------------------------	
	public static void main(String[] args) throws Exception {
		int opcion=0; //opcion de menu
		int puerto1=0; //puerto para el servicio Sroperador
		int puerto2=0; //puerto para el servicio Cloperador
//		String port = "1099";
		
		try {
//			Utilis.startRegistry(Integer.parseInt(port));

			
			Utilis.SetCodeBase(ServicioCloperadorInterface.class);
			ServicioCloperadorImpl servicioCloperador = new ServicioCloperadorImpl();
			
			Utilis.SetCodeBase(ServicioSroperadorInterface.class);
			ServicioSroperadorImpl servicioSroperador = new ServicioSroperadorImpl();
			
			//Acede al servicio de autenticacion del servidor
			Registry registryAutenticar = LocateRegistry.getRegistry();
			servidorAutenticar = (ServicioAutenticacionInterface) registryAutenticar.lookup("ServidorAutenticar");
			System.out.println("Acceso al SERVICIO DE AUTENTICACION.");
			
			//Autentica el repositorio y le devuelve un identificador unico
			miSesion = servidorAutenticar.Autenticar_repositorio();
			
			//obtiene un puerto y nombre para el servicio Cloperador 
			puerto1=servidorAutenticar.getPuerto();
			ServicioCloperadorInterface remoteCloperador = (ServicioCloperadorInterface) UnicastRemoteObject.exportObject(servicioCloperador,puerto1);
			nombre_servicioCloperador="ServidorCloperador".concat(Integer.toString(miSesion));

			//obtiene un puerto y nombre para el servicio Sroperador y lo registra
			puerto2=servidorAutenticar.getPuerto();
			ServicioSroperadorInterface remoteSroperador = (ServicioSroperadorInterface) UnicastRemoteObject.exportObject(servicioSroperador,puerto2);
			nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(miSesion));

			//registra el servicio Cloperador
			Registry registryCloperador = LocateRegistry.getRegistry();
			registryCloperador.rebind(nombre_servicioCloperador, remoteCloperador);
			
			//Registra el servicio Sroperador
			Registry registrySroperador = LocateRegistry.getRegistry();
			registrySroperador.rebind(nombre_servicioSroperador, remoteSroperador);
			
			//inicia el identificador de repositorio en Sroperador y Cloperador
			Registry registryCloperador2 = LocateRegistry.getRegistry();
			servidorCloperador = (ServicioCloperadorInterface) registryCloperador2.lookup(nombre_servicioCloperador);
			
			servidorCloperador.set_id_repositorio(miSesion);
			
			Registry registrySroperador2 = LocateRegistry.getRegistry();
			servidorSroperador = (ServicioSroperadorInterface) registrySroperador2.lookup(nombre_servicioSroperador);
			
			servidorSroperador.set_id_repositorio(miSesion);
			
			if (miSesion!=0) {
				System.out.println("Repositorio autenticado. Id: " + miSesion);
				System.out.println();
				System.out.println("Se ha iniciado el servicio CLIENTE OPERADOR.  Puerto: " + puerto1);
				System.out.println("Se ha iniciado el servicio SERVIDOR OPERADOR. Puerto: " + puerto2);
				System.out.println();
			} else {
				System.out.println();
				System.out.println("Error en el SERVIDOR o REPOSITORIO.");
				System.exit(2);
			}

			do {
				//Muestra en pantalla el menu de repositorio
				Gui.LimpiarPantalla();
				
				opcion=Gui.MostrarMenu("REPOSITORIO", new String[]{"LISTAR CLIENTES.","LISTAR FICHEROS DE CLIENTE.","SALIR"});
				switch (opcion){
					case 1: listar_clientes(); break; 
					case 2: listar_ficheros_clientes(); break; 
				}
			}while (opcion!=3);
			
			//Desconecta el repositorio
			System.out.println("Cerrando REPOSITORIO...");
			servidorAutenticar.Desconectar_repositorio(miSesion);

			//da de baja el servicio Cloperador y Sroperador
			registryCloperador.unbind(nombre_servicioCloperador);
			UnicastRemoteObject.unexportObject((Remote) servicioCloperador, true);
			
			registrySroperador.unbind(nombre_servicioSroperador);
			UnicastRemoteObject.unexportObject((Remote) servicioSroperador, true);

			System.out.println("REPOSITORIO cerrado.");
			
		}catch (Exception e) {
			System.out.println("Error en el REPOSITORIO.");
			servidorAutenticar.Desconectar_repositorio(miSesion);
		}
	}
//-------------------------------------------

//-------------------------------------------
	private static void listar_clientes() throws RemoteException, NotBoundException {
		//muestra en pantalla los clientes asociados al repositorio
		
		Map<Integer,String> lista; //lista de clientes
		
		//obtiene la lista de clientes asociados al repositorio
		lista=servidorSroperador.lista_clientes_obtener_lista();
		
		//muestra en pantalla la lista de clientes asociados al repositorio
		System.out.println();
		System.out.println("Lista de CLIENTES asociados al REPOSITORIO "+miSesion+".");
		System.out.println("------------------------------------------------");
		System.out.println();
		
		if(lista.size()==0) {
			System.out.println("No hay CLIENTES asociados al REPOSITRORIO.");
			System.out.println();
		}else{
			for(Entry<Integer,String>entry : lista.entrySet()) {
				System.out.println("ID: "+entry.getKey()+"\t NOMBRE: "+entry.getValue());
			}
		}
	}
//-------------------------------------------
	
//-------------------------------------------
	private static void listar_ficheros_clientes() throws RemoteException, NotBoundException {
		//muestra en pantalla la lista de clientes y sus ficheros que hay en el repositorio
		
		int cont=0; //variable contador
		int id_cli=0; //identificador del cliente
		int num_fich=0; //numero de ficheros
		int i=0; //Contador de bucle
		String nombre_cli=null; //nombre del cliente
		String nombre_fich=null; //nombre del fichero
		Map<Integer,String> lista_cli; //lista de clientes en el repositorio
		List<Fichero> lista_metadatos; //lista de metadatos de los ficheros del repositorio
		Fichero fichero; //fichero
		long tama=0; //Tamaños del fichero
		
		//obtiene la lista de clientes asociados al repositorio
		lista_cli=servidorSroperador.lista_clientes_obtener_lista();
		
		System.out.println();
		System.out.println("Lista de FICHEROS de CLIENTES.");
		System.out.println("------------------------------");
		System.out.println();
		
		//para cada cliente asociado al repositorio...
		for(Entry<Integer,String>entry : lista_cli.entrySet()) {
			//obtiene el identificador del cliente
			id_cli=entry.getKey(); 
			//obtiene el nombre del cliente
			nombre_cli=lista_cli.get(id_cli);
			//obtiene la lista de metadatos de los ficheros del cliente id_cli, almacenados en el repositorio
			lista_metadatos=servidorSroperador.listar_ficheros_metadatos(id_cli);
			//obtiene el numero de ficheros del cliente id_cli
			num_fich=lista_metadatos.size();
			//para cada fichero del cliente id_cli, almacenado en el repositorio....
			for(i=0;i<num_fich;i++) {
				fichero=lista_metadatos.get(i);
				//obtiene el nombre del fichero
				nombre_fich=fichero.obtenerNombre();
				//obtiene el tamaño del fichero
				tama=fichero.obtenerPeso();
				//muestra en pantalla la informacion del cliente y fichero
				System.out.println("CLIENTE - ID: "+id_cli+"\t NOMBRE: "+nombre_cli+"\t FICHERO: "+nombre_fich+"\t "+tama+" bytes.");
				//cuenta el numero de ficheros mostrados en pantalla
				cont=cont+1;
			}
		}
		System.out.println();
		if(cont==0) {
			System.out.println("No hay FICHEROS en el REPOSITORIO.");
		}else {
			System.out.println();
			System.out.println("Hay un total de "+cont+" Ficheros.");
		}
		System.out.println();
	}	
//-------------------------------------------
}


