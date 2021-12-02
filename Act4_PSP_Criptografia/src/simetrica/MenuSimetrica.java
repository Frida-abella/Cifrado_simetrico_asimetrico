package simetrica;

import java.awt.List;

import simetrica.Coche;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


public class MenuSimetrica {
	
	public static void main(String[] args) {
		
		try {
			// Primero creamos todos los objetos necesarios para el cifrado sim�trico:
			// Creamos el generador de claves, elegimos el algoritmo AES
			KeyGenerator generadorSimetrica = KeyGenerator.getInstance("AES");
			
			// Creamos la clave sim�trica con la que vamos a encriptar:
			SecretKey claveSecreta = generadorSimetrica.generateKey();
			
			// Creamos el objeto que va a cifrar y descifrar las frases y objetos con la clave, 
			// Le pasamos el algoritmo que usa para cifrar, que ser� el AES
			Cipher cifradorSimetrico = Cipher.getInstance("AES");
			// Lo iniciamos o configuramos para encriptar:
			cifradorSimetrico.init(Cipher.ENCRYPT_MODE, claveSecreta);
			
			// Creamos un objeto Cipher para desencriptado sim�trico:
			Cipher descifradorSimetrico = Cipher.getInstance("AES");
			descifradorSimetrico.init(Cipher.DECRYPT_MODE, claveSecreta);
			
			// Creamos la variable que almacena la frase original
			String frase = null;
						
			// Creamos la variable que almacena el flujo de bytes de la frase original introducida:
			byte [] bytesFrase = null;
						
			// Creamos la variable que almacener� en memoria el flujo de bytes con la frase cifrada 
			byte [] bytesFraseCifrada = null;
					
			// Creamos la variable tipo String donde se almacenar� la frase encriptada
			String fraseCifrada = null;
					
			// Variable que almacena los bytes de la frase tras descifrarla
			byte [] bytesFraseDescifrada = null;
			
			// Declaramos el objeto tipo Coche con el que trabajaremos en el men�:
			Coche coche1;
			
			// Declaramos el objeto tipo SealedObject donde se crear� o guardar� el objeto (tipo coche) encriptado
			SealedObject so = null;
			
			// Creamos una variable boolean para controlar la repetici�n del bucle.
			boolean continuar = true;
			
			do {
				// Creamos el man� para que salga por pantalla y permita escoger una opci�n 
				System.out.println("ELIJA UNA OPCI�N:\n\n"
						+ "  1. Encriptar frase \n"
						+ "  2. Mostrar frase encriptada \n"
						+ "  3. Desencriptar frase \n"
						+ "  4. Encriptar un objeto coche \n"
						+ "  5. Salir \n");
				

				// Creamos un objeto Scanner para pedir por pantalla la opcion del men� deseada
				Scanner sc = new Scanner(System.in);
				int opcion = sc.nextInt();	
				// Creamos un �nico Scanner para usar a lo largo del men� que lea Strings
				Scanner scanner = new Scanner(System.in);
			switch (opcion) {
			
			case 1:
				System.out.println("----ENCRIPTAR FRASE----");
				// Pedimos la frase por consola y la almacenamos en una variable de tipo String:
				System.out.println("Escriba la frase que desea encriptar:");
				frase = scanner.nextLine();
				// Convertimos el String recibido en un array o flujo de bytes para poder cifrar la frase
				bytesFrase = frase.getBytes();
				// Guardamos en un array o flujo de bytes el mismo mensaje pero encriptado, usando el m�todo .doFinal() del cifrador para encriptar:
				bytesFraseCifrada = cifradorSimetrico.doFinal(bytesFrase);
				// Guardamos en la variable tipo String fraseCifrada el valor del flujo de bytes de la frase cifrada pasado a String
				fraseCifrada = new String(bytesFraseCifrada);	
		break;
				
			case 2:
				System.out.println("----MOSTRAR FRASE ENCRIPTADA----");
				// Mostramos la frase original:
				System.out.println("La frase original era: " + frase);
				// Mostramos por pantalla la frase encriptada almacenada en memoria
				System.out.println("La frase encriptada es la siguiente: " + fraseCifrada);
				break;
			
			case 3:
				System.out.println("----DESCIFRAR FRASE----");
				// Guardamos en un flujo de bytes la frase desencriptada:
				bytesFraseDescifrada = descifradorSimetrico.doFinal(bytesFraseCifrada);
				// Mostramos la frase descifrada por pantalla, pas�ndola a String
				System.out.println("La frase desencriptada es: " + new String(bytesFraseDescifrada));
				break;
				
			case 4:
				coche1 = new Coche();
				System.out.println("Introduzca los datos del coche:");
				System.out.println("Matr�cula: ");
				coche1.setMatricula(scanner.nextLine());
				System.out.println("Marca: ");
				coche1.setMarca(scanner.nextLine());
				System.out.println("Modelo: ");
				coche1.setModelo(scanner.nextLine());
				System.out.println("Precio: ");
				Scanner precio = new Scanner(System.in);
				coche1.setPrecio(precio.nextInt());
				
					try {
						// Usamos un objeto de tipo SealedObject para cifrar el objeto Coche:
						so = new SealedObject(coche1, cifradorSimetrico);
						System.out.println("Coche original: " + coche1);
						System.out.println("Coche cifrado: " + so.toString());
						
					} catch (IOException ioe) {
						System.out.println("Error. No se ha podido cifrar el objeto");
					}
			break;
			case 5:
			System.out.println("----SALIR----");
			System.out.println("La aplicaci�n ha terminado ");
			continuar = false;
			break;
			
			}	
			
			}	while (continuar); 
			
		} catch (Exception e) {
			System.out.println("Un error ha ocurrido... " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}
