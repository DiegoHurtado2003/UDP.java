import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        while (true) {
            try {
                // 1 - Crear socket de tipo servidor y le indicamos el puerto
                ServerSocket servidor = new ServerSocket(49200);

                // 2 - Queda a la espera de peticiones y las acepta cuando las recibe
                System.out.println("Servidor se encuentra a la escucha...");
                Socket peticion = servidor.accept();

                // 3 - Abrir flujos de lectura y escritura de datos
                System.out.println("Habrimos flujos de lectura y escritura");
                InputStream is = peticion.getInputStream();
                OutputStream os = peticion.getOutputStream();

                // 4 - Intercambiar datos con el cliente
                // Leer mensaje enviado por el cliente e imprimirlo por consola
                System.out.println("Leyendo la ruta");
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String rutaArchivo = br.readLine();
                System.out.println("Mensaje enviado por el cliente(ruta): " + rutaArchivo);


                //Buscamos el archivo especificado por el cliente, lo leemos y le enviamos su contenido
                File archivo = new File(rutaArchivo);
                StringBuilder stringBuilder = new StringBuilder();
                String textoFinal;
                FileReader fr = null;
                BufferedReader brArchivo = null;

                try {
                    if (archivo.exists()) {
                        fr = new FileReader(archivo);
                        brArchivo = new BufferedReader(fr);

                        // Lectura del fichero
                        String linea;
                        while ((linea = brArchivo.readLine()) != null) {
                            stringBuilder.append(linea);
                        }
                        textoFinal = stringBuilder.toString();
                    } else {
                        textoFinal = "El archivo no se encontró";
                    }
                } catch (IOException e) {
                    textoFinal = "Ha habido un error en la lectura del archivo, ¿Ha escrito bien la ruta?";
                } finally {
                    try {
                        if (null != fr) {
                            fr.close();
                        }
                    } catch (IOException e) {
                        System.out.println("El fichero no se pudo cerrar correctamente");
                    }
                }


                // Enviarle mensaje al cliente
                System.out.println("Servidor envía al cliente un mensaje");
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(textoFinal);
                bw.newLine();
                bw.flush();

                // 5 - Cerrar flujos de lectura y escritura
                brArchivo.close();
                isr.close();
                is.close();
                bw.close();
                osw.close();
                os.close();

                // 6 - Cerra la conexión
                System.out.println("Cierre de conexión del servidor");
                peticion.close();
                servidor.close();

            } catch (IOException e) {
                System.err.println("Ha habido algún error en la creación del Socket Servidor");
                e.printStackTrace();
            }
        }
    }
}
