/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Utilis {
	//Clase de utilidades para iniciar rmi registry y manejas PATH
	public static final String CODEBASE="java.rmi.server.codebase";
	public static void SetCodeBase(Class<?> c){
		String ruta = c.getProtectionDomain().getCodeSource().getLocation().toString();
		String path = System.getProperty(CODEBASE);
		if(path != null && !path.isEmpty()){
			ruta = path + " " + ruta;
		}
		System.setProperty(CODEBASE, ruta);
	}
	
	public static void startRegistry(int RMIPortNum) throws RemoteException
	{
		try{
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list(); //This call throws a exception if the registry does not already exist
			
		}catch (RemoteException ex){
			//No valid registry at that port.
			System.out.println("RMI registry no ha podido ser localizado en el puerto: " + RMIPortNum);
			@SuppressWarnings("unused")
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
			System.out.println("RMI registry se ha creado en el puerto: " +RMIPortNum);
			System.out.println();
			
		}
	}
	
}
