/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

//-----------------------------------------------------
public class Fichero implements Serializable{
	//Clase Fichero serializable proporcionada por el ED.

	private static final long serialVersionUID = -701591859841871541L;
	private String propietario;		//Identificador del propietario original del fichero
	private String nombre;			//Nombre del fichero
	private long peso;				//Peso del fichero en bytes
	private long checksum;			//Suma de chequeo de los bytes del fichero
	private byte[] data;			//Contenido del fichero
	
//-----------------------------------------------
	public Fichero (String nombre, String propietario) {	//constructor
		this.nombre=nombre;
		this.propietario=propietario;
		
		CheckedInputStream c = null;
		peso = 0;
		
		try{
			c = new CheckedInputStream(new FileInputStream(nombre), new CRC32());
			peso = new File(nombre).length();
			data=new byte[(int) this.peso];
			while(c.read(data) >= 0) {
		    }
			c.close();
			 
		}catch (FileNotFoundException ef)
		{
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {

			System.err.println("Error leyendo fichero" + e.toString());
		}
		checksum = c.getChecksum().getValue();
	}
//--------------------------------------------
	
//--------------------------------------------	
	public Fichero (String ruta, String nombre, String propietario)	{ //constructor
		this.nombre=nombre;
		this.propietario=propietario;
		
		CheckedInputStream c = null;
		peso = 0;
		
		try{
			c = new CheckedInputStream(new FileInputStream(ruta+"//"+nombre), new CRC32());
			peso = new File("./"+ruta+"//"+nombre).length();
			data=new byte[(int) this.peso];
			while(c.read(data) >= 0) {
		    }
			c.close();
			 
		}catch (FileNotFoundException ef)
		{
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {

			System.err.println("Error leyendo fichero" + e.toString());
		}
		checksum = c.getChecksum().getValue();
	}
//---------------------------------
	
//------------------------------------	
	public boolean escribirEn (java.io.OutputStream os)
	{
		//Escribe un fichero por Stream
		long CheckSum;
		CheckedOutputStream cs= new CheckedOutputStream(os,new CRC32());
		try{
			cs.write(data);
			CheckSum=cs.getChecksum().getValue();
			cs.close();
			if (CheckSum != this.checksum)
			{
				return (false);	//Falla el checksum, debería mandarse de nuevo
			}			
		}catch(Exception e){
			System.err.println("Error escribiendo fichero" + e.toString());
		}
		return (true); //Fichero mandado Ok
	}
//-------------------------------------
	
//-----------------------------------	
	public String obtenerPropietario()
	{
		//Obtiene el propietario del fichero
		return (propietario);
	}
//---------------------------------------
	
//------------------------------------------	
	public String obtenerNombre()
	{
		//obtiene el nombre del fichero
		return (nombre);
	}
//----------------------------------------
	
//----------------------------------------	
	public long obtenerPeso()
	{
		//Obtiene el tamaño (peso) del fichero
		return (peso);
	}
//--------------------------------------------
	
//_-----------------------------------------	
	public long obtenerChecksum()
	{
		//Obtiene el checksum del fichero
		return (checksum);
	}
//-----------------------------------------
}
