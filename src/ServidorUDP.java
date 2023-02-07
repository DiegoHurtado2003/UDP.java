import java.io.*;
import java.net.*;

public class ServidorUDP {


    public static void main(String[] args) {
        try {
            File file = new File("src\\resultado.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            System.out.println("Creación del socket");
            DatagramSocket socket = new DatagramSocket(49900);

                boolean booleanoSalida = false;
                BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));

                while (!booleanoSalida) {
                    String mensaje = null;
                    byte[] bufferEntrada = new byte[64];

                    DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                    socket.receive(packetEntrada);

                    mensaje = new String(packetEntrada.getData()).trim();

                    try {
                        bw.write(mensaje);
                        bw.newLine();
                    } catch (IOException e) {
                        System.err.println("Error en la escritura del fichero");
                    }
                    if (mensaje.equals("FIN")) {
                        booleanoSalida = true;
                    }
                }
                bw.close();
            System.out.println("Adiós =(");

        } catch (SocketException e) {
            System.err.println("Error en la creación del socket");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error general");
            throw new RuntimeException(e);
        }
    }




}
