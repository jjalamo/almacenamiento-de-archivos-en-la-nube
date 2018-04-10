/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.cliente;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import cloud.common.Fichero;
import cloud.common.ServicioDiscoclienteInterface;

//----------------------------------------------
public class ServicioDiscoclienteImpl implements ServicioDiscoclienteInterface{
	//Implementacion de la interface disco cliente ServicioDiscoclienteInterface

//----------------------------------------------
	public boolean descargar_fichero(String nombre_fichero, Fichero fichero) throws RemoteException {
		//descarga al cliente un fichero desde el repositorio en el que esta almacenado
		
		String ruta=null; //ruta del fichero en disco
		OutputStream os; //Stream para descargar el fichero
		
		try {
			//descarga el fichero en la ruta indicada del cliente
			ruta=nombre_fichero;
			
			os= new FileOutputStream(ruta);
			if(fichero.escribirEn(os)==false) {
				os.close();
				return false;
			}
			os.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("Error al bajar el fichero.");
		} catch(IOException e) {
			System.out.println("Error al bajar el fichero.");
		}
		return true;
	}
//---------------------------------------------------
}
