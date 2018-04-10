/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;

import cloud.common.ServicioAutenticacionInterface;
import cloud.common.ServicioDatosInterface;
import cloud.common.ServicioSroperadorInterface;

//-------------------------------------------
public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {
	//implementacion de la interface ServicioAutenticacionInterface
	
	private static int numero_sesion=100; //identificadores unicos para asignar a clientes y repositorios
	private static int numero_puerto=8000; //numeros de puerto para asignar a los servicios de los clientes y repositorios
	private static ServicioDatosInterface servidorDatos; //servicio de datos
	private static ServicioSroperadorInterface servidorSroperador;//servicio Sroperador
	
//------------------------------------	
	public int Autenticar_cliente(String nombre) throws RemoteException, NotBoundException {
		//Autentica un cliente en el sistema y devuelve al cliente un identificador unico
		
		int num_rep=0; //numero de repositorios activos en el sistema
		int id_cli=0; //identificador del cliente
		int id_rep=0; //identificador del repositorio a asignar al cliente
		String nombre_servicioSroperador=null; //nombre del servicio Sroperador
		
		//busca el servicio de Datos
		Registry registry = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registry.lookup("ServidorDatos");
		
		//obtiene el numero de repositorios conectados
		num_rep=servidorDatos.lista_repositorios_get_numrepositorios();

		//comprueba si hay repositorios activos
		if(num_rep>0) {
			//obtiene el identificador para el cliente
			id_cli=getSesion();
			//obtiene el identificador del repositorio menos cargado
			id_rep=servidorDatos.repositorio_menos_cargado();
			//llama al servidor de Datos para añadir el cliente a la lista de clientes en el servidor de datos
			servidorDatos.lista_clientes_set_cliente(id_cli, nombre);
			//llama al servidor de Datos para añadir la asociacion de cliente repositorio
			servidorDatos.lista_clirep_set_clirep(id_cli, id_rep);
			//obtiene el nombre del servicio Sroperador del repositorio asignado al cliente
			nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(id_rep));
			
			//busca el servicio Sroperador del repositorio asignado al cliente
			Registry registry2 = LocateRegistry.getRegistry();
			servidorSroperador = (ServicioSroperadorInterface) registry2.lookup(nombre_servicioSroperador);
			
			//llama al servicio Sroperador del repositorio asignado al cliente para
			//añadir el cliente a la lista local de clientes, del repositorio aisgnado,
			//e inicia la lista local de ficheros del cliente en el repositorio asignado
			servidorSroperador.lista_clientes_set_cliente(id_cli, nombre);
			servidorSroperador.lista_ficheros_set_fichero(id_cli, nombre);
			servidorSroperador.lista_ficheros_borrar_fichero(id_cli, nombre);

			//Llama al servicio Sroperador del repositorio asignado al cliente para
			//crear en el repositorio el directorio para el cliente
			servidorSroperador.crear_directorio(id_cli);
		}
		return id_cli;
	}
//-------------------------------------------	
	
//------------------------------------------
	public void Desconectar_cliente(int id) throws RemoteException, NotBoundException {
		//desconecta al cliente id del sistema
		
		//busca el servicio de Datos
		Registry registry = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registry.lookup("ServidorDatos");

		//borra al cliente id de las listas del servicio de datos
		servidorDatos.lista_clientes_borrar_cliente(id);
		servidorDatos.lista_clirep_borrar_clirep(id);
	}
//-------------------------------------------	
		
//------------------------------------	
	public int Autenticar_repositorio() throws RemoteException, NotBoundException {
		//Autentica un repositorio y le devuelve un identificador unico
		
		int id=0; //identificador que se asignara al repositorio
		String nombre; //nombre del repositorio
		String identificador; //identificador del repositorio en modo String
		
		//busca el servicio de Datos del servidor
		Registry registry = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registry.lookup("ServidorDatos");
			
		//obtiene un identificador unico y lo pasa a cadena
		id=getSesion();
		identificador=Integer.toString(id);
		
		//obtiene el nombre del repositorio
		nombre="repositorio".concat(identificador);
		
		//llama al servicio de Datos y añade el repositorio a la lista de repositorios
		servidorDatos.lista_repositorios_set_repositorio(id, nombre);
		
		return id;
	}
//-------------------------------------------	
		
//------------------------------------------
	public void Desconectar_repositorio(int id) throws RemoteException, NotBoundException {
		//desconecta el repositorio del sistema
		
		int id_cli=0; //identificador de cliente
		int aux=0; //numero de asociaciones cliente repositorio registradas en el sistema
		int i=0;//contador de bicle
		
		//busca el servicio de datos
		Registry registry = LocateRegistry.getRegistry();
		servidorDatos = (ServicioDatosInterface) registry.lookup("ServidorDatos");

		//llama al servicio de datos y borra el repositorio de la lsita de repositorios
		servidorDatos.lista_repositorios_borrar_repositorio(id);
		//obtiene el numero de clientes asociados al repositorio
		aux=servidorDatos.lista_clirep_get_numclirep();
		
		//para cada cliente asociado al repositorio....
		for (i=0;i<aux;i++) {
			//obtiene el identificador de un cliente asociado al repositorio, mediante el
			//servicio de Datos
			id_cli=servidorDatos.lista_clirep_get_idcliente(id);
			//llama al servicio de Datos y borra el cliente obtenido de la lista
			//de asociaciones de cliente repositorio
			servidorDatos.lista_clirep_borrar_clirep(id_cli);
		}
	}
//-------------------------------------------	
	
//-------------------------------------------
	public int getPuerto() {
		//devuelve un numero de puerto
		return ++numero_puerto;
	}
//---------------------------------------------

//-------------------------------------------
	private static int getSesion() {
		//devuelce un identificador unico
		return ++numero_sesion;
	}
//-------------------------------------------
}
