package ejercicio1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

	public static String cifrar(int moving, String texto){
	    String textoCodificado = "";
	    String abc = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	    texto = texto.toUpperCase();

	    char caracter;
	    for (int i = 0; i < texto.length(); i++) {
	        caracter = texto.charAt(i);

	        int pos = abc.indexOf(caracter);

	        if(pos == -1){
	            textoCodificado += caracter;
	        }else{
	            textoCodificado += abc.charAt( (pos + moving) % abc.length() );
	        }

	    }

	    return textoCodificado;
	}
	
	public static String descifrar(int moving, String texto){
	    String textoDescodificado = "";
	    String abc = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	    texto = texto.toUpperCase();

	    char caracter;
	    for (int i = 0; i < texto.length(); i++) {
	        caracter = texto.charAt(i);

	        int pos = abc.indexOf(caracter);

	        if(pos == -1){
	            textoDescodificado += caracter;
	        }else{
	            if(pos - moving < 0){
	                textoDescodificado += abc.charAt( abc.length() + (pos - moving) );
	            }else{
	                textoDescodificado += abc.charAt( (pos - moving) % abc.length() );
	            }
	        }

	    }

	    return textoDescodificado;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int puerto = 31080;
		
		try (ServerSocket server = new ServerSocket(puerto)){
			
			try (Socket socket = server.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pr = new PrintWriter(socket.getOutputStream())){
				System.out.println("Cliente conectado");
				int moving = Integer.parseInt(br.readLine());
				String message = br.readLine();
				
				String msgcifrado = cifrar(moving, message);
				String msgdescifrado = descifrar(moving, msgcifrado);
				
				pr.println(msgcifrado);
				pr.flush();
				pr.println(msgdescifrado);
				pr.flush();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Algo salió mal al recibir el mensaje.");
				e.printStackTrace();
				server.close();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something went pretty bad");
			e.printStackTrace();
			
		}
	}

}
