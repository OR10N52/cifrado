package ejercicio3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 34221;

        try (Socket socket = new Socket(host, puerto);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pr = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Conectado al servidor");

            // Leer la clave pública del archivo
            PublicKey publicKey = leerClavePublica("publicKey.txt");

            // Mensaje a enviar
            String mensaje = "Hola, Servidor!";
            String mensajeCifrado = encriptar(mensaje, publicKey);

            System.out.println("Mensaje cifrado enviado: " + mensajeCifrado);
            pr.println(mensajeCifrado); // Enviar mensaje cifrado al servidor

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PublicKey leerClavePublica(String fileName) throws Exception {
        byte[] claveBytes = Base64.getDecoder().decode(Files.readString(Paths.get(fileName)));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(claveBytes));
    }

    private static String encriptar(String mensaje, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] mensajeCifrado = cipher.doFinal(mensaje.getBytes());
        return Base64.getEncoder().encodeToString(mensajeCifrado);
    }
}
