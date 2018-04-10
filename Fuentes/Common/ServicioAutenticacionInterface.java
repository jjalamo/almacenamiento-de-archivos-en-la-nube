/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {
	//Interface del servicio de Autenticacion. Se implementa en el servidor.
	
	public int Autenticar_cliente(String nombre) throws RemoteException, NotBoundException;
		//Autentica un cliente devolviendole un identificador unico.
	
	public void Desconectar_cliente(int id) throws RemoteException, NotBoundException;
		//Desconecta al cliente id.
	
	public int Autenticar_repositorio() throws RemoteException, NotBoundException;
		//Autentica un repositorio devolviendole un identificador unico.
	
	public void Desconectar_repositorio(int id) throws RemoteException, NotBoundException;
		//Desconecta al repositorio id
	
	public int getPuerto() throws RemoteException;
		//devuelve un numero de puerto
}
