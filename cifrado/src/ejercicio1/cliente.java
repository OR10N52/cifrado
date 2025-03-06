package ejercicio1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String server = "localhost";
		int puerto = 31080;
		
		try (Socket socket = new Socket(server,puerto);
			 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter pr = new PrintWriter(socket.getOutputStream(), true)){
			
			int moving = 0;
			while(moving == 0) {
				try {
					moving = Integer.parseInt(JOptionPane.showInputDialog("Introduce el numero de desplazamientos del cifrado"));
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Por favor, introduce un valor valido", "Error", 0, null);
				}
			}
			
			String message = JOptionPane.showInputDialog("Introduce el mensaje a cifrar");
			
			pr.println(moving);
			pr.flush();
			pr.println(message);
			pr.flush();
			
			String cifrado = br.readLine();
			String descifrado = br.readLine();
			System.out.println("Mensaje original: "+message);
			System.out.println("Mensaje cifrado: "+cifrado);
			System.out.println("Mensaje descifrado: "+descifrado);
			
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
