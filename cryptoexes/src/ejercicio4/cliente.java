package ejercicio4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class cliente {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		String server = "localhost";
		int puerto = 8843;
		
		SSLSocketFactory factous = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket) factous.createSocket(server, puerto);
		
		PrintWriter finneas = new PrintWriter(socket.getOutputStream(), true);
		finneas.println("It's only a lifetime. It's only a while");
		
		socket.close();
	}

}
