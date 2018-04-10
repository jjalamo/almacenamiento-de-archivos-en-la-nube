/********************************************
*		Jacinto Álamo Galdón.				*
*		26224967-E.							*
*		jalamo25@alumno.uned.es.			*
*		jalamo.uned@gmail.com.				*
*		665 009 467.						*
*		Centro Asociado Jaén - Úbeda.		*
********************************************/

package cloud.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Gui {
	//Clase de interface de usuario en modo texto

//-------------------------------------------------------------	
	public static int MostrarMenu(String name, String[] entradas) {
		//Muestra en pantalla un menu de usuario y permite elegir una opcion del menu.
		//Devuelve la opcion de menu seleccionada
		
		int opcion=0;
		System.out.println(" MENU " + name);
		System.out.println("******************************");
		for(int i=0; i<entradas.length; i++) {
			System.out.println((i+1) + ".-" + entradas[i]);
		}
		try {
			do {
				@SuppressWarnings("resource")
				Scanner entrada = new Scanner(System.in);
				System.out.print("Seleccione una opcion [1-" + entradas.length + "]: ");
				opcion = entrada.nextInt();
				System.out.println();
				System.out.println();	
			}while(opcion<0 && opcion>entradas.length);
		}
		catch(Exception e){
			System.out.println();
			System.out.println("ERROR EN LA OPCION ELEGIDA");
		}
		return opcion;
	}
//--------------------------------------------------------
	
//--------------------------------------------------------	
	public static void LimpiarPantalla() throws IOException {
		//Borra la pantalla
		
		System.out.println("Presione una tecla para continuar...");
		System.in.read();
		for(int i=0; i<30; i++)
			System.out.println();
	}
//--------------------------------------------------------------
	
//----------------------------------------------------------	
	public static String IntroducirString(String texto) throws IOException {
		//Captura desde teclado una cadena de texto
		
		String cadena=null;
		System.out.print(texto + ": ");
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
			cadena = entrada.readLine();
		}
		catch (Exception e) {
			System.out.println("ERROR EN LA ENTRADA DE DATOS");
		}
		return cadena;
	}
//-----------------------------------------------------------------------
}
