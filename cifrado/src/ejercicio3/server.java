package ejercicio3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class server {
    public static void main(String[] args) {
        int puerto = 34221;

        try (ServerSocket server = new ServerSocket(puerto)) {
            System.out.println("Servidor esperando conexiones...");

            // Generar claves RSA
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            KeyPair key = generador.generateKeyPair();

            PublicKey publick = key.getPublic();
            PrivateKey privatek = key.getPrivate();

            // Guardar la clave pública en un archivo
            saveKeyToFile("publicKey.txt", publick.getEncoded());

            try (Socket socket = server.accept();
                 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 ) {

                System.out.println("Cliente conectado exitosamente");

                // Leer mensaje
                String mensajeCifrado = br.readLine();
                System.out.println("Mensaje cifrado recibido: " + mensajeCifrado);

                // Descifrar mensaje
                String mensajeDescifrado = desencriptar(mensajeCifrado, privatek);
                System.out.println("Mensaje descifrado: " + mensajeDescifrado);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveKeyToFile(String fileName, byte[] keyBytes) throws IOException {
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(encodedKey);
        }
    }

    private static String desencriptar(String mensajeCifrado, PrivateKey privateKey) throws Exception {
        byte[] bytesCifrados = Base64.getDecoder().decode(mensajeCifrado);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] mensajeDescifrado = cipher.doFinal(bytesCifrados);
        return new String(mensajeDescifrado);
    }
}
