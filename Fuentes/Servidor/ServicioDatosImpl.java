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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cloud.common.ServicioDatosInterface;

//--------------------------------------------------
public class ServicioDatosImpl implements ServicioDatosInterface{
	//Implementacion de la interface ServicioDatosInterface
	
 	private static Map<Integer, String> lista_clientes = new HashMap<Integer, String>(); //lista de clientes conectados
 	private static Map<Integer, String> lista_repositorios = new HashMap<Integer, String>(); //lista de repositorios conectados
 	private static Map<Integer, Integer> lista_clirep = new HashMap<Integer, Integer>(); //lista de clientes asociados a los repositorios
	
//---------------------------------------------
 	public void lista_clientes_set_cliente(int id, String nombre) throws RemoteException {
 		//añade al cliente id, a la lista de clientes conectados
		lista_clientes.put(id, nombre);
		
	}
//---------------------------------------------
 	
//---------------------------------------------
 	public int lista_clientes_get_idcliente(String nombre) throws RemoteException {
 		//Devuelve el identificador de cliente asociado a un nombre de cliente
 		
		int idcliente=0; //identificador de cliente
		
		//busca en la lista de clientes, el cliente buscado y devuelve su identificador
		for(Entry<Integer, String>entry : lista_clientes.entrySet()){
			if (entry.getValue()==nombre){
				idcliente=entry.getKey();
			}
		}
		return idcliente;
	}
//---------------------------------------------
	
//---------------------------------------------
 	public String lista_clientes_get_nombrecliente(int id) throws RemoteException {
 		//devuelve el nombre de cliente asociado al identificador de cliente id
 		
		String nombre=null; //nombre del cliente
		
		//busca el identificador del cliente en la lista de clientes y devuelve su nombre
		for(Entry<Integer, String>entry : lista_clientes.entrySet()){
			if (entry.getKey()==id){
				nombre=entry.getValue();
			}
		}
		return nombre;
	}
//---------------------------------------------
 	
//---------------------------------------------
	public void lista_clientes_borrar_cliente(int id) throws RemoteException {
		//borra al cliente con identificador id de la lista de clientes
		lista_clientes.remove(id);
	}
//-------------------------------------
	
//-------------------------------------
	public int lista_clientes_get_numclientes() throws RemoteException {
		//devuelve el numero de clientes conectados
		return lista_clientes.size();
	}
//-----------------------------------------
	
//-------------------------------------------
	public Map<Integer, String> lista_clientes_obtener_lista() throws RemoteException {
		//devuelve la lista de clientes conectados
		return lista_clientes;
	}
//---------------------------------------------	

//*****************************************************
//*****************************************************
	
//---------------------------------------------
 	public void lista_repositorios_set_repositorio(int id, String nombre) throws RemoteException {
 		//añade un repositorio a la lista de repositorios
		lista_repositorios.put(id, nombre);
	}
//---------------------------------------------

//---------------------------------------------
 	public int lista_repositorios_get_idrepositorio(String nombre) throws RemoteException {
 		//devuelve el identificador de repositorio asignado a un nombre de repositorio
 		int id=0;
		
 		//busca en la lista de repositorios el nombre de repositorio y devuelve su idntificador
		for(Entry<Integer, String>entry : lista_repositorios.entrySet()){
			if (entry.getValue()==nombre){
				id=entry.getKey();
			}
		}
		return id;
	}
//---------------------------------------------
		
//---------------------------------------------
 	public String lista_repositorios_get_nombrerepositorio(int id) throws RemoteException {
 		//devuelve el nombre de repositorio asignado a un identificador de repositorio
		String nombre=null;
		
		//busca en la lista de repositorios el id de repositorio y devuelve su nombre
		for(Entry<Integer, String>entry : lista_repositorios.entrySet()){
			if (entry.getKey()==id){
				nombre=entry.getValue();
			}
		}
		return nombre;
	}
//---------------------------------------------
	 	
//-----------------------------------------------
	public void lista_repositorios_borrar_repositorio(int id) throws RemoteException {
		//borra el repositorio id de la lista de repositorios
		lista_repositorios.remove(id);
	}
//-------------------------------------
	
//-------------------------------------
	public int lista_repositorios_get_numrepositorios() throws RemoteException {
		//devuelve el numero de repositorios conectados al sistema
		return lista_repositorios.size();
	}
//-----------------------------------------

//-------------------------------------------
	public Map<Integer, String> lista_repositorios_obtener_lista() throws RemoteException {
		//devuelve la lista de repositorios conectados
		return lista_repositorios;
	}
//---------------------------------------------	

//********************************************
//********************************************
	
//---------------------------------------------
	public void lista_clirep_set_clirep(int idcli, int idrep) throws RemoteException {
		//añade a la lista de clientes repositorios, la asociacion de un cliente a un repositorio
		lista_clirep.put(idcli, idrep);
	}
//-----------------------------------------------


//------------------------------------------------	
	public int lista_clirep_get_idrepositorio(int idcli) throws RemoteException {
		//devuelve el identificador del repositorio al que se ha asignado el cliente idcli
 		int id=0;
		
 		//busca en la lista de clientes repositorios el identificador del cliente
 		//y devuelve el identificador del repositorio asignado
		for(Entry<Integer, Integer>entry : lista_clirep.entrySet()){
			if (entry.getKey()==idcli){
				id=entry.getValue();
			}
		}
		return id;
	}
//-------------------------------------------------
	
//-------------------------------------------------
	public int lista_clirep_get_idcliente(int idrep) throws RemoteException {
		//devuelve el identificador de un cliente que este asociado al repositorio idrep

 		int id=0; //identificador de cliente
		
 		//recorre la lista de asociaciones cliente repositorio
		for(Entry<Integer, Integer>entry : lista_clirep.entrySet()){
			//busca en la lista de asociaciones cliente repositorio
			//el identificador de repositorio idrep
			if (entry.getValue()==idrep){
				//cuando encuentra en la lista un identificador de repositorio igual a idrep
				//almacena en id el identificador del cliente asociado, y lo devuelve
				id=entry.getKey();
			}
		}
		return id;
	}
//-------------------------------------------------

//---------------------------------------------	
	public void lista_clirep_borrar_clirep(int idcli) throws RemoteException {
		//borra de la lista de clientes repositorios la asociacion del cliente idcli con su repositorio
		lista_clirep.remove(idcli);		
	}
//---------------------------------------------
	
//---------------------------------------------	
	public int lista_clirep_get_numclirep() throws RemoteException {
		//devuelve el numero de asociaciones clientes repositorios que hay en el sistema
		return lista_clirep.size();		
	}
//---------------------------------------------
	
//----------------------------------------------	
	public Map<Integer, Integer> lista_clirep_obtener_lista() throws RemoteException {
		//devuelve la lista de asociaciones de clientes repositorios
		return lista_clirep;
	}
//-------------------------------------------------

//-------------------------------------------------
	public int repositorio_menos_cargado() throws RemoteException {
		//devuelve el identificador del repositorio menos cargado, el identificador
		//del repositorio que tenga menos clientes asignados
		
		int idrepositorio=0; //identificador de repositorio (a devolver)
		int id_rep=0; //identificador de repositorio (auxiliar)
		int num_rep=0; //numero de repositorios
		int aux=0; //variable auxiliar
		int carga=-1;//numero de clientes asignados a un repositorio
		
		//obtiene el numero de repositorios del sistema
		num_rep=lista_repositorios_get_numrepositorios();
		if (num_rep>0) {
			//para cada repositorio del sistema...
 			for(Entry<Integer, String>entry : lista_repositorios.entrySet()) {
 				//obtiene el identificador del repositorio
 				id_rep=entry.getKey();

 				aux=0;
 				//cuenta el numero de clientes asociados al repositorio y lo almacena en aux
 				for(Entry<Integer, Integer>entry2 : lista_clirep.entrySet()) {
 					if(id_rep==entry2.getValue()) {
 						aux=aux+1;
 					}
 				}
 				//compara la carga del repositorio(aux) con la carga del repositorio
 				//anterior (carga)
 				if (carga<0) {
 					carga=aux;
 					idrepositorio=id_rep;
 				} else {
 					if(aux<carga) {
 						//si la carga del repositorio es menor que la del repositorio anterior
 						//almacena su identificador en idrepositorio
 						carga=aux;
 						idrepositorio=id_rep;
 					}
 				}
 			}
		}
		//devuelve idrepositorio, repositorio con menos carga
		return idrepositorio;
	}
//-------------------------------------------------
}
