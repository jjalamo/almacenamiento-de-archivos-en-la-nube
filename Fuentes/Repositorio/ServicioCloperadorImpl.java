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
import cloud.common.Fichero;
import cloud.common.ServicioCloperadorInterface;
import cloud.common.ServicioSroperadorInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

//--------------------------------------------------------------
public class ServicioCloperadorImpl implements ServicioCloperadorInterface {
	//Implementa la inferface ServicioCloperadorInterface
	
	private static ServicioSroperadorInterface servidorSroperador; //servicio Sroperador
	private static int id_repositorio=0; //identificador del repositorio

//-----------------------------------------------
	public boolean Subir_fichero(int id_cli, String nombre_fichero, Fichero fichero) throws NotBoundException, IOException {
		//Sube un fichero desde el cliente id_cli al repositorio
		
		String nombre_servicioSroperador=null; //Nombre del servicio Sroperador
		String ruta=null; //Ruta donde se guardara el fichero
		OutputStream os; //Stream para subir el fichero
		
		try {
			nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(id_repositorio));

			Registry registrySroperador = LocateRegistry.getRegistry();
			servidorSroperador = (ServicioSroperadorInterface) registrySroperador.lookup(nombre_servicioSroperador);
		
			//Introduce el fichero en la lista de ficheros en el servicio Sroperador
			servidorSroperador.lista_ficheros_set_fichero(id_cli, nombre_fichero);
			
			//genera la ruta en disco para el fichero
			ruta=Integer.toString(id_cli) + "//" + fichero.obtenerNombre();
			
			//Guarda en disco el fichero 
			os= new FileOutputStream(ruta);
			if(fichero.escribirEn(os)==false) {
				os.close();
				return false;
			}
			os.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("Error al subir fichero.");
		} catch(IOException e) {
			System.out.println("Error al subir el fichero");
		}
		return true;
	}
//-----------------------------------------------

//-----------------------------------------------
	public void borrar_fichero(int id_cli, String nombre_fichero) throws RemoteException, NotBoundException {
		// borra el fichero de id_cli del repositorio
		
		String nombre_servicioSroperador=null; //nombre del servicio Sroperador
		File fich; //fichero a borrar
		String directorio; //nombre del directorio donde se almacena el fichero
		
		//obtiene el nombre del servicio Sroperador
		nombre_servicioSroperador="ServidorSroperador".concat(Integer.toString(id_repositorio));

		Registry registrySroperador = LocateRegistry.getRegistry();
		servidorSroperador = (ServicioSroperadorInterface) registrySroperador.lookup(nombre_servicioSroperador);
		
		//obtiene el direcotrio donde se almacena el fichero
		directorio=Integer.toString(id_cli);
		
		//comprueba si existe el fichero y lo borra
		fich = new File(directorio,nombre_fichero);
		if(fich.exists()) {
			fich.delete();
			servidorSroperador.lista_ficheros_borrar_fichero(id_cli, nombre_fichero);
		}
	}
//-----------------------------------------------

//-----------------------------------------------
	public void set_id_repositorio(int id) throws RemoteException {
		//devuelve el identificador del repositorio
		id_repositorio=id;
	}
//-----------------------------------------------
}
