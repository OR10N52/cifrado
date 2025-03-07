package ejercicio2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class cifrado {

	public static void main(String[] args) {
		
		String archivo = JOptionPane.showInputDialog("Introduce la ruta del archivo a cifrar");
			try {
				BufferedReader br = new BufferedReader(new FileReader(archivo));
				String message = br.readLine();
				
				try {
				
				KeyGenerator keymachine = KeyGenerator.getInstance("AES");
				keymachine.init(128);
				SecretKey clave = keymachine.generateKey();
				File archivoClave = new File("clave.key");
				Cipher bill = Cipher.getInstance("AES");
				
				try (FileOutputStream fia = new FileOutputStream(archivoClave)){
					fia.write(clave.getEncoded());
					System.out.println("Clave guardada correctamente");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Something went pretty wrong");
				}
				
					
				bill.init(Cipher.ENCRYPT_MODE, clave);
				byte[] encrypted = bill.doFinal(message.getBytes());
					
				bill.init(Cipher.DECRYPT_MODE, clave);
				byte[] decrypted = bill.doFinal(encrypted);
				
				
				System.out.println("Mensaje original: "+message);
				System.out.println("Mensaje cifrado: "+ new String(encrypted));
				System.out.println("Mensaje descifrado: "+ new String(decrypted));
				
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("No se encontró dicho archivo");
			}
	}
}
