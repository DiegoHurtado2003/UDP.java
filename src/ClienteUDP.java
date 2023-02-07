import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.net.UnknownHostException;


public class ClienteUDP {

        public static void main(String[] args) {
            try {

                InetAddress direccion = InetAddress.getLocalHost();
                int port = 49900;

                System.out.println("Creación del socket");
                DatagramSocket socket = new DatagramSocket();

                for (int i = 0; i < 10000; i++) {
                    enviarMensaje(socket, "Mensaje: " + i, direccion, port);
                }
                enviarMensaje(socket, "FIN", direccion, port);

                System.out.println("Cierro conexiónn");
                socket.close();
            } catch (UnknownHostException e) {
                System.err.println("No se ecnontró el servidor");
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.err.println("Error al enviar el paquete");
                throw new RuntimeException(e);
            }
        }

        private static void enviarMensaje(DatagramSocket socket, String mensaje, InetAddress address, int port) {
            byte[] bufferSalida = mensaje.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bufferSalida, bufferSalida.length, address, port);
            try {
                socket.send(datagramPacket);
            } catch (IOException e) {
                System.err.println("Error al enviar el mensaje");            }
        }
}


