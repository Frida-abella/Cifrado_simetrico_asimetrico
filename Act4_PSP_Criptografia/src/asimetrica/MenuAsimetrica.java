package asimetrica;

import java.awt.List;

import simetrica.Coche;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


public class MenuAsimetrica {
	
	public static void main(String[] args) {
		
		try {
									
			// A continuación, creamos los objetos necesarios para el CIFRADO ASIMÉTRICO:
			// El generador del par de claves, indicándole el algoritmo que usaremos, el RSA
			KeyPairGenerator generadorAsimetricas = KeyPairGenerator.getInstance("RSA");
			
			// Obtenemos el par de claves, simétrica y asimétrica, con KeyPair
			KeyPair claves = generadorAsimetricas.generateKeyPair();
			
			// Configuramos el cifrador asimétrico:
			Cipher cifradorAsimetrico = Cipher.getInstance("RSA");
			// Lo inicializamos para que genere la clave pública con la que encriptaremos
			cifradorAsimetrico.init(Cipher.ENCRYPT_MODE, claves.getPublic());
			
			// Crearemos un objeto para descifrado asimétrico:
			Cipher descifradorAsimetrico = Cipher.getInstance("RSA");
			// Lo configuramos para que descifre con la clave privada:
			descifradorAsimetrico.init(Cipher.DECRYPT_MODE, claves.getPrivate());
			
			// Ahorraremos código usando las mismas variables, y mismo menú, para ambos cifrados (simétrico y asimétrico)
			// Creamos la variable que almacena la frase original
			String frase = null;
						
			// Creamos la variable que almacena el flujo de bytes de la frase original introducida:
			byte [] bytesFrase = null;
						
			// Creamos la variable que almacenerá en memoria el flujo de bytes con la frase cifrada 
			byte [] bytesFraseCifrada = null;
					
			// Creamos la variable tipo String donde se almacenará la frase encriptada
			String fraseCifrada = null;
					
			// Variable que almacena los bytes de la frase tras descifrarla
			byte [] bytesFraseDescifrada = null;
			
			// Declaramos el objeto tipo Coche con el que trabajaremos en el menú:
			Coche coche1;
			
			// Declaramos el objeto tipo SealedObject donde se creará o guardará el objeto (tipo coche) encriptado
			SealedObject so = null;
			
			// Creamos una variable boolean para controlar la repetición del bucle.
			boolean continuar = true;
			
			do {
				// Creamos el manú para que salga por pantalla y permita escoger una opción 
				System.out.println("ELIJA UNA OPCIÓN:\n\n"
						+ "  1. Encriptar frase \n"
						+ "  2. Mostrar frase encriptada \n"
						+ "  3. Desencriptar frase \n"
						+ "  4. Encriptar un objeto coche \n"
						+ "  5. Salir \n");
				

				// Creamos un objeto Scanner para pedir por pantalla la opcion del menú deseada
				Scanner sc = new Scanner(System.in);
				int opcion = sc.nextInt();	
				// Creamos un único Scanner para usar a lo largo del menú que lea Strings
				Scanner scanner = new Scanner(System.in);
			switch (opcion) {
			
			case 1:
				System.out.println("----ENCRIPTAR FRASE----");
				// Pedimos la frase por consola y la almacenamos en una variable de tipo String:
				System.out.println("Escriba la frase que desea encriptar:");
				frase = scanner.nextLine();
				// Convertimos el String recibido en un array o flujo de bytes para poder cifrar la frase
				bytesFrase = frase.getBytes();
				
				// Damos las dos opciones de cifrado 
				System.out.println("Escribe 1 para cifrado simétrico o 2 para cifrado asimétrico: ");

				// Guardamos en un array o flujo de bytes el mismo mensaje pero encriptado, usando el método .doFinal() del cifrador para encriptar:
				bytesFraseCifrada =	cifradorAsimetrico.doFinal(bytesFrase);
				// Lo pasamos o guardamos en variable tipo String
				fraseCifrada = new String(bytesFraseCifrada);
	
			case 2:
				System.out.println("----MOSTRAR FRASE ENCRIPTADA----");
				// Mostramos la frase original:
				System.out.println("La frase original era: " + frase);
				// Mostramos por pantalla la frase encriptada almacenada en memoria
				System.out.println("La frase encriptada es la siguiente: " + fraseCifrada);
				break;
			
			case 3:
				System.out.println("----DESCIFRAR FRASE----");
				// Guardamos en un array o flujo de bytes el mismo mensaje pero encriptado, usando el método .doFinal() del cifrador para encriptar:
				bytesFraseDescifrada =	descifradorAsimetrico.doFinal(bytesFraseCifrada);
				System.out.println("La frase desencriptada es: " + new String(bytesFraseDescifrada));
				break;
				
			case 4:
				coche1 = new Coche();
				System.out.println("Introduzca los datos del coche:");
				System.out.println("Matrícula: ");
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
						so = new SealedObject(coche1, cifradorAsimetrico);
						System.out.println("Coche original: " + coche1);
						System.out.println("Coche cifrado: " + so.toString());
						
					} catch (IOException ioe) {
						System.out.println("Error. No se ha podido cifrar el objeto");
					}
					
			case 5:
			System.out.println("----SALIR----");
			System.out.println("La aplicación ha terminado ");
			continuar = false;
			break;
			
			}	
			
			}	while (continuar); 
			
		} catch (GeneralSecurityException gse) {
			System.out.println("Un error ha ocurrido... " + gse.getMessage());
			gse.printStackTrace();
		}
		
		
	}
}
