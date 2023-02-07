import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClienteTCP {
    public static void main(String[] args) {
        // 1 - Crear un socket de tipo cliente indicando IP y puerto del servidor
        InetAddress direccion = null;
        Socket cliente = null;

        InputStream is = null;
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            System.out.println("Estableciendo conexión con el servidor");
            direccion = InetAddress.getLocalHost();
            cliente = new Socket(direccion, 49200);
        } catch (UnknownHostException e) {
            System.err.println("No se encontró el servidor");
        } catch (IOException ex) {
            System.err.println("Excepción general el conectarse con el servidor");
        }


        for (int i = 0; i < 1000; i++) {
            try {

                // 2 - Abrir flujos de lectura y escritura
                is = cliente.getInputStream();
                os = cliente.getOutputStream();

                // 3 - Intercambiar datos con el servidor
                // Envío de mensaje de texto al servidor
                osw = new OutputStreamWriter(os, "UTF-8");
                bw = new BufferedWriter(osw);

                bw.write("Mensaje: " + i);
                bw.newLine();
                bw.flush();

                // Leo mensajes que me envía el servidor
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
                System.out.println("El servidor me envía el siguiente mensaje: " + br.readLine());


            } catch (IOException e) {
                System.err.println("Se ha producido un error en la conexión con el servidor.");
                e.printStackTrace();
            } finally {

                cerrarFlujosConexion(cliente, is, os, osw, bw, isr, br);
            }
        }


        try {

            // 2 - Abrir flujos de lectura y escritura
            is = cliente.getInputStream();
            os = cliente.getOutputStream();

            // 3 - Intercambiar datos con el servidor
            // Envío de mensaje de texto al servidor
            osw = new OutputStreamWriter(os, "UTF-8");
            bw = new BufferedWriter(osw);

            bw.write("FIN");
            bw.newLine();
            bw.flush();

            // Leo mensajes que me envía el servidor
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            System.out.println("El servidor me envía el siguiente mensaje: " + br.readLine());

        } catch (IOException e) {
            System.err.println("Se ha producido un error en la conexión con el servidor.");
            e.printStackTrace();
        } finally {
            cerrarFlujosConexion(cliente, is, os, osw, bw, isr, br);
        }
    }


    private static void cerrarFlujosConexion(Socket cliente, InputStream is, OutputStream os, OutputStreamWriter osw, BufferedWriter bw, InputStreamReader isr, BufferedReader br) {
        try {
            // 4 - Cerrar flujos de lectura y escritura
            br.close();
            isr.close();
            is.close();
            bw.close();
            osw.close();
            os.close();

            // 5 - Cerrar la conexión
            System.out.println("Se cierra la conexión del cliente");
            cliente.close();
        } catch (IOException e) {
            System.err.println("Error cerrando flujos y conexión");
        }
    }
}


