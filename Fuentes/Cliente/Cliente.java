/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.cliente;

import java.io.File;
import java.io.IOException;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cloud.common.Fichero;
import cloud.common.Gui;
import cloud.common.ServicioAutenticacionInterface;
import cloud.common.ServicioCloperadorInterface;
import cloud.common.ServicioDiscoclienteInterface;
import cloud.common.ServicioGestorInterface;
import cloud.common.Utilis;

//----------------------------------------------------
public class Cliente {
	private static int miSesion=0;  //identificador unico del cliente
	private static String nombreCliente; //nombre del cliente
	private static String nombre_servicioDiscocliente=null; //nombre del servicio disco cliente
	private static ServicioAutenticacionInterface servidorAutenticar; //servidor Autenticar
	private static ServicioGestorInterface servidorGestor; //servidor Gestor
	private static ServicioCloperadorInterface servidorCloperador; //servidor Cloperador
	
//---------------------------------------	
	public static void main(String[] args) throws Exception {
		int opcion=0; //opcion de menu
		int puerto=0; //puerto al que conecta el servicio disco cliente
		String port = "1099"; // puerto rmi registry
		
		try {
			//inicia remi registry en el puerto 1099
			Utilis.startRegistry(Integer.parseInt(port));
			
			//crea servicio disco cliente
			Utilis.SetCodeBase(ServicioDiscoclienteInterface.class);
			ServicioDiscoclienteImpl servicioDiscocliente = new ServicioDiscoclienteImpl();
			
			//obtiene acceso al servicio de Autenticacion
			Registry registryAutenticar = LocateRegistry.getRegistry();
			servidorAutenticar = (ServicioAutenticacionInterface) registryAutenticar.lookup("ServidorAutenticar");
			System.out.println("Acceso al SERVICIO DE AUTENTICACION.");
			
			//obtiene acceso al servicio de Gestion
			Registry registryGestor = LocateRegistry.getRegistry();
			servidorGestor = (ServicioGestorInterface) registryGestor.lookup("ServidorGestor");
			System.out.println("Acceso al SERVICIO DE GESTION.");
			System.out.println();

			//Autentica el client y le devuelve un identificador unico
			nombreCliente=Gui.IntroducirString("Introduzca nombre de CLIENTE: ");
			miSesion = servidorAutenticar.Autenticar_cliente(nombreCliente);
			
			//obtiene un numero de puerto para el servicio disco cliente
			puerto=servidorAutenticar.getPuerto();
			ServicioDiscoclienteInterface remoteDiscocliente = (ServicioDiscoclienteInterface) UnicastRemoteObject.exportObject(servicioDiscocliente,puerto);
			nombre_servicioDiscocliente="ServidorDiscocliente".concat(Integer.toString(miSesion));

			//Inicia el servicio disco cliente
			Registry registryDiscocliente = LocateRegistry.getRegistry();
			registryDiscocliente.rebind(nombre_servicioDiscocliente, remoteDiscocliente);
			System.out.println();
			System.out.println("Se ha iniciado el servicio DISCO CLIENTE. Puerto: "+ puerto);
			
			if (miSesion!=0) {
				System.out.println();
				System.out.println("Cliente autenticado. Id: " + miSesion);
				System.out.println();
			} else {
				System.out.println();
				System.out.println("Error en el SERVIDOR o REPOSITORIO.");
				System.exit(2);
			}
			
			do {
				//Muestra en pantalla el menu del cliente
				Gui.LimpiarPantalla();
				opcion=Gui.MostrarMenu("CLIENTE", new String[]{"SUBIR FICHERO.","BAJAR FICHERO","BORRAR FICHERO","LISTAR FICHEROS","LISTAR CLIENTES","SALIR"});
				switch (opcion){
					case 1: subir_fichero(); break; 
					case 2: bajar_fichero(); break; 
					case 3: borrar_fichero(); break;
					case 4: listar_ficheros(); break; 
					case 5: listar_clientes(); break; 
				}
			}while (opcion!=6);
			
			//Desconecta al cliente
			System.out.println("Cerrando CLIENTE...");
			servidorAutenticar.Desconectar_cliente(miSesion);

			//Cierra el servicio disco cliente
			registryDiscocliente.unbind(nombre_servicioDiscocliente);
			UnicastRemoteObject.unexportObject((Remote) servicioDiscocliente, true);

			System.out.println("CLIENTE cerrado.");

		}catch (Exception e) {
			System.out.println("Error en el CLIENTE.");
			servidorAutenticar.Desconectar_cliente(miSesion);
			System.exit(2);
		}
	}
//-------------------------------------------

//-------------------------------------------
	private static void subir_fichero() throws IOException, NotBoundException {
		//Implementa la opcion subir fichero del menu cliente
		String nombre_fichero=null; //nombre del fichero a subir
		String repositorio=null; //repositorio donde se alojara el fichero
		Fichero mi_fichero; //fichero a subir en stream
		File fich; //variable auxiliar para comprobar si existe el fichero
		
		nombre_fichero=Gui.IntroducirString("Introduzca FICHERO ");
		
		fich=new File(nombre_fichero);
		
		//Comprueba si existe el fichero a subir
		if(fich.exists()) {
			//obtiene la informacion del fichero a subir
			mi_fichero = new Fichero(nombre_fichero, nombreCliente);
		
			//localiza el repositorio donde se alojara el fichero
			repositorio=servidorGestor.Localizar_repositorio(miSesion);
		
			//busca el servicio Sloperador del repositorio donde se alojara el fichero
			Registry registryCloperador = LocateRegistry.getRegistry();
			servidorCloperador = (ServicioCloperadorInterface) registryCloperador.lookup(repositorio);

			//llama al servicio Sloperador para subir el fichero al repositorio
			if(servidorCloperador.Subir_fichero(miSesion, nombre_fichero, mi_fichero)==false) {
				System.out.println("Error, el fichero no se ha enviado.");
			}else{
				System.out.println();
				System.out.println("Fichero " + nombre_fichero + " subido.");
				System.out.println();
			}
		}else{
			System.out.println();
			System.out.println("El fichero " + nombre_fichero + " no existe.");
			System.out.println();
		}
	}
//-------------------------------------------

//-------------------------------------------
	private static void bajar_fichero() throws IOException, NotBoundException {
		//implementa la opcion bajar fichero del menu cliente
		String nombre_fichero=null; //nombre del fichero a bajar
		String discocliente=null; //servicio disco cliente del cliente que bajara el fichero
		
		//llama al servicio Gestor del servidor para bajar el fichero, pasandole el identificador
		//del cliente que quiere descargar el fichero, el nombre del fichero a bajar
		//y el nombre del servicio disco cliente del cliente que bajara el fichero
		nombre_fichero=Gui.IntroducirString("Introduzca FICHERO ");
		discocliente="ServidorDiscocliente".concat(Integer.toString(miSesion));
		servidorGestor.Bajar_fichero(miSesion, nombre_fichero, discocliente);
	}
//-------------------------------------------

//-------------------------------------------
	private static void borrar_fichero() throws IOException, NotBoundException {
		//implementa la opcion borrar del menu cliente
		
		String nombre_fichero=null; //nombre del fichero a borrar
		String repositorio=null; //repositorio donde se encuentra el fichero a borrar
		
		nombre_fichero=Gui.IntroducirString("Introduzca FICHERO: ");
		//llama al servicio Gestor del servidor, para localiza el repositorio donde
		//se encuentra alojado el fichero a borrar
		repositorio=servidorGestor.Localizar_repositorio(miSesion);
		
		//localiza el servicio Cloperador del repositorio que aloja el fichero a borrar
		Registry registryCloperador = LocateRegistry.getRegistry();
		servidorCloperador = (ServicioCloperadorInterface) registryCloperador.lookup(repositorio);

		//llama al servicio Cloperador del repositorio que almacena el fichero a borrar, para
		//borrar el fichero
		servidorCloperador.borrar_fichero(miSesion, nombre_fichero);
	}
//-------------------------------------------

//-------------------------------------------
	private static void listar_ficheros() throws IOException, NotBoundException {
		//implementa la opcion listar ficheros del menu cliente. Muestra un listado en
		//pantalla de los ficheros que el cliente tiene almacenados en el repositorio
		
		int num_fich=0; //numero de ficheros
		int i=0;//contador de bucle
		long tama=0;//tamaño del fichero
		String nombre_fichero=null; //nombre del fichero
		List<Fichero> lista_fich = new ArrayList<Fichero>(); //lista de ficheros almacenados
		Fichero fichero; //Fichero almacenado en el repositorio

		//llama al servicio Gestor del servidor pidiendo una lista de ficheros que el
		//cliente tiene almacenados en el repositorio
		lista_fich=servidorGestor.listar_metadatos(miSesion);

		//muestra en panlla la lista de ficheros almacenados en el repositorio
		System.out.println();
		System.out.println("Listado de FICHEROS remotos.");
		System.out.println("----------------------------");
		System.out.println();
		
		if(lista_fich!=null){
			num_fich=lista_fich.size();
			if(num_fich==0) {
				System.out.println();
				System.out.println("No hay FICHEROS remotos.");
				System.out.println();
			}else{
				for(i=0;i<num_fich;i++) {
					fichero=lista_fich.get(i);
					nombre_fichero=fichero.obtenerNombre();
					tama=fichero.obtenerPeso();
					
					System.out.println(nombre_fichero + "\t \t" + tama + " bytes.");
				}
				System.out.println();
				System.out.println("Hay un total de "+num_fich+" FICHEROS remotos.");
				System.out.println();
			}
		}else {
			System.out.println();
			System.out.println("No hay FICHEROS remotos.");
			System.out.println();
		}
	}
//-------------------------------------------

//-------------------------------------------
	private static void listar_clientes() throws IOException, NotBoundException {
		//implementa la opcion listar clientes del menu cliente.
		//Muestra en pantalla una lista de los clientes conectados al sistema.
		
		int num_cli=0; //numero de clientes conectados.
		int id_cli=0; //identificador de cliente
		String nombre_cli=null; //nombre de cliente
		Map<Integer,String> lista_clientes=null; //lista de clientes conectados
		
		//Llama al servicio Gestor del servidor y pide una lista de clientes conectados
		lista_clientes=servidorGestor.listar_clientes();
		
		//muestra en pantalla la lista de clientes conectados obtenida del servicio Gestor
		num_cli=lista_clientes.size();
		System.out.println();
		System.out.println("Listado de CLIENTES conectados al sistema.");
		System.out.println("------------------------------------------");
		System.out.println();
		
		if(num_cli>0 && lista_clientes!=null) {
			for(Entry<Integer,String>entry : lista_clientes.entrySet()) {
				id_cli=entry.getKey();
				nombre_cli=entry.getValue();
				System.out.println("ID: " + id_cli + "\t NOMBRE: " + nombre_cli);
			}
			System.out.println();
			System.out.println("Hay un total de " + num_cli + " CLIENTES conectados al sistema.");
			System.out.println();
		}else{
			System.out.println();
			System.out.println("No hay CLIENTES conectados al sistema.");
		}
	}
//-------------------------------------------
}
