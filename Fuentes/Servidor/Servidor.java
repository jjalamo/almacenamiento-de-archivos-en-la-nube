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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Map.Entry;
import cloud.common.Gui;
import cloud.common.ServicioGestorInterface;
import cloud.common.Utilis;
import cloud.common.ServicioAutenticacionInterface;
import cloud.common.ServicioDatosInterface;

public class Servidor {
	private static ServicioDatosInterface servidorDatos;

//--------------------------------------------------------
	public static void main(String[] args) throws Exception {
		int opcion=0; //opcion de menu
		String port = "1099"; //numero de puerto donde iniciar rmi registry
		
		try {
			//inicia rmi registry en el puerto port
			Utilis.startRegistry(Integer.parseInt(port));

			//inicia el servicio de autenticacion
			Utilis.SetCodeBase(ServicioAutenticacionInterface.class);
			ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
			ServicioAutenticacionInterface remoteAutenticacion = (ServicioAutenticacionInterface) UnicastRemoteObject.exportObject(servicioAutenticacion, 7777);
			Registry registryAutenticacion = LocateRegistry.getRegistry();
			registryAutenticacion.rebind("ServidorAutenticar", remoteAutenticacion);
			System.out.println("Se ha iniciado el SERVICIO DE AUTENTICACION");
			//inicia el servicio de Datos
			Utilis.SetCodeBase(ServicioDatosInterface.class);
			ServicioDatosImpl servicioDatos = new ServicioDatosImpl();
			ServicioDatosInterface remoteDatos = (ServicioDatosInterface) UnicastRemoteObject.exportObject(servicioDatos, 7778);
			Registry registryDatos = LocateRegistry.getRegistry();
			registryDatos.rebind("ServidorDatos", remoteDatos);
			System.out.println("Se ha iniciado el SERVICIO DE DATOS");
			//inicia el servicio Gestor
			Utilis.SetCodeBase(ServicioGestorInterface.class);
			ServicioGestorImpl servicioGestor = new ServicioGestorImpl();
			ServicioGestorInterface remoteGestor = (ServicioGestorInterface) UnicastRemoteObject.exportObject(servicioGestor, 7779);
			Registry registryGestor = LocateRegistry.getRegistry();
			registryGestor.rebind("ServidorGestor", remoteGestor);
			System.out.println("Se ha iniciado el SERVICIO GESTOR.");
			System.out.println();
			
			do {
				//muestra en pantalla el menu servidor y selecciona una opcion
				Gui.LimpiarPantalla();
				opcion=Gui.MostrarMenu("SERVIDOR", new String[]{"LISTAR CLIENTES.","LISTAR REPOSITORIOS","LISTAR REPOSITORIO-CLIENTE","SALIR"});
				switch (opcion) {
				case 1: listar_clientes(); break;
				case 2: listar_repositorios(); break; 
				case 3: listar_clirep(); break; 
				}
			}while (opcion!=4);
			//desconecta los servicios de Autenticacion, Datos y Gestor y cierra el servidor
			System.out.println("Cerrando SERVIDOR...");
			registryAutenticacion.unbind("ServidorAutenticar");
			UnicastRemoteObject.unexportObject((Remote) servicioAutenticacion, true);
			registryDatos.unbind("ServidorDatos");
			UnicastRemoteObject.unexportObject((Remote) servicioDatos, true);
			registryGestor.unbind("ServidorGestor");
			UnicastRemoteObject.unexportObject((Remote) servicioGestor, true);
			System.out.println("SERVIDOR cerrado.");
		}catch (Exception e) {
			System.out.println("Error en el SERVIDOR");
			System.exit(2);
		}
	}
//--------------------------------------------

//-------------------------------------------	
	private static void listar_clientes() throws RemoteException, NotBoundException {
		//implenta la opcion listar clientes del menu servidor
		//muestra en pantalla una lista de los clientes conectados al sistema
		
		Map<Integer, String> l_cli=null; //lista de clientes
		
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		//llama al servicio de Datos para obtener una lista de los clientes conectados
		l_cli=servidorDatos.lista_clientes_obtener_lista();
		//muestra en pantalla la lista de clientes conectados
 		System.out.println("LISTA DE CLIENTES");
 		System.out.println("-----------------");
 		System.out.println();
	 		
 		if (l_cli.size()==0) {
 			System.out.println("No hay CLIENTES conectados");
 		} else {
 			System.out.println("Hay " + l_cli.size() + " CLIENTES conectados");
 			System.out.println();
 			for(Entry<Integer, String>entry : l_cli.entrySet()) {
 				System.out.println("Id: " + entry.getKey() + "\t Nombre: " + entry.getValue());
 			}
 		}
 		System.out.println();
	}
//--------------------------------------------
	
//--------------------------------------------
	private static void listar_repositorios() throws RemoteException, NotBoundException {
		//Implementa la opcion listar repositorios del menu servidor
		//muestra en pantalla una lista de los repositorios conectados al sistema
		
		Map<Integer, String> l_rep=null; //lista de repositorios
			
		//busca el servicio de datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		//llama al servicio de Datos para obtener una lista de los repositorios conectados
		l_rep=servidorDatos.lista_repositorios_obtener_lista();
		//muestra en pantalla la lista de repositorios conectados
 		System.out.println("LISTA DE REPOSITORIOS");
 		System.out.println("---------------------");
 		System.out.println();
	 		
 		if (l_rep.size()==0) {
 			System.out.println("No hay REPOSITORIOS conectados");
 		} else {
 			System.out.println("Hay " + l_rep.size() + " REPOSITORIOS conectados");
 			System.out.println();
 			for(Entry<Integer, String>entry : l_rep.entrySet()) {
 				System.out.println("Id: " + entry.getKey() + "\t Nombre: " + entry.getValue());
 			}
 		}
 		System.out.println();
	}
//--------------------------------------------
	
//--------------------------------------------
	private static void listar_clirep() throws RemoteException, NotBoundException {
		//implementa la opcion clientes repositorios del menu servidor
		//muestra en pantalla una lista de los clientes conectados al sistema junto
		//con su repositorio asignado
		
		int id_cli=0; //identificador de cliente
		int id_rep=0; //identificador de repositorio
		String nombre_cli=null; //nombre del cliente
		String nombre_rep=null; //nombre del repositorio
		Map<Integer, Integer> l_clirep=null; //lista de asociaciones de clientes repositorios
				
		//busca el servicio de Datos
		Registry registryDatos = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registryDatos.lookup("ServidorDatos");
		//llama al servicio de datos para obtener una lista de las asociaciones entre clientes
		//y repositorios asignados
		l_clirep=servidorDatos.lista_clirep_obtener_lista();
		//muestra en pantalla la lista de asociaciones entre clientes y repositorios asignados
 		System.out.println("LISTA DE CLIENTES - REPOSITORIOS");
 		System.out.println("--------------------------------");
 		System.out.println();
		 		
 		if (l_clirep.size()==0) {
 			System.out.println("No hay CLIENTES - REPOSITORIOS asociados");
 		} else {
 			System.out.println("Hay " + l_clirep.size() + " CLIENTES - REPOSITORIOS asociados");
			System.out.println();
 			for(Entry<Integer, Integer>entry : l_clirep.entrySet()) {
 				id_cli=entry.getKey();
 				id_rep=entry.getValue();
 				nombre_cli=servidorDatos.lista_clientes_get_nombrecliente(id_cli);
 				nombre_rep=servidorDatos.lista_repositorios_get_nombrerepositorio(id_rep);
 				System.out.println("CLIENTE - ID: "+id_cli+" \t NOMBRE: "+nombre_cli+" \t REPOSITORIO - ID: "+id_rep+" \t NOMBRE: "+nombre_rep);
 			}
 		}
 		System.out.println();
	}
//--------------------------------------------
}
