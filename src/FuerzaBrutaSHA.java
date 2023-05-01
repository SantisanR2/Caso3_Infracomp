import java.lang.*;

public class FuerzaBrutaSHA {

    public static String caracteres = "abcdefghijklmnopqrstuvwxyz";
    public static char[] espacioBusqueda = caracteres.toCharArray();
    public static int numThreads;
    public static String codigoCriptografico;
    public static String sal;
    public static String algoritmo;

    public static void encontrarMensaje() {
        if(numThreads == 2) {
            // Iterar sobre todas las posibles longitudes de mensaje
            for (int longitud = 1; longitud <= 4; longitud++) {
                // Generar todas las posibles combinaciones de caracteres para la longitud actual
                ThreadBusqueda hilo1 = new ThreadBusqueda(espacioBusqueda, longitud, codigoCriptografico, sal, algoritmo);
                ThreadBusqueda hilo2 = new ThreadBusqueda(espacioBusqueda, longitud+3, codigoCriptografico, sal, algoritmo);

                hilo1.start();
                hilo2.start();

                try {
                    hilo1.join();
                    hilo2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Iterar sobre todas las posibles longitudes de mensaje
            for (int longitud = 1; longitud <= 7; longitud++) {
                // Generar todas las posibles combinaciones de caracteres para la longitud actual
                ThreadBusqueda hilo1 = new ThreadBusqueda(espacioBusqueda, longitud, codigoCriptografico, sal, algoritmo);
                hilo1.start();

                try {
                    hilo1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("No se encontró el mensaje");
    }

    public static void main(String[] args) {
        codigoCriptografico = "f5cc43628e040ee6acb2d1678488d1f4e0fa75488ab65e64d01587838baaac91";
        sal = "ki";
        algoritmo = "256";
        numThreads = 1;
        encontrarMensaje();
    }
}
