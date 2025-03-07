package ejercicio4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.List;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class server {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int puerto = 8843;
		
		SSLServerSocketFactory factos = (SSLServerSocketFactory)
		SSLServerSocketFactory.getDefault();

		
		try (SSLServerSocket server = (SSLServerSocket) factos.createServerSocket(8443);
				 ){
			
			System.out.println("Servidor esperando conexión...");
			try (SSLSocket socket = (SSLSocket) server.accept()){
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = reader.readLine();

				File msgfile = new File("mensajes_seguro.txt");

				
				try (FileOutputStream fia = new FileOutputStream(msgfile, true)) { 
				    fia.write(message.getBytes()); 
				    fia.write(System.lineSeparator().getBytes());
				}
				
				System.out.println("Mensaje guardado: " + message);
				
				verificarACL("mensajes_seguro.txt");
				socket.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error en la conexión: "+ e.getMessage());
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void verificarACL(String filename) {
		
		Path file = Paths.get(filename);
		
		AclFileAttributeView aclView = Files.getFileAttributeView(file,
				AclFileAttributeView.class);
		
		try {
			System.out.println("Permisos de acceso: " + aclView.getAcl());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
