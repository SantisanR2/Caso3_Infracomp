import java.lang.*;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String encriptarSHA(String mensaje, String algoritmo){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-"+algoritmo);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(mensaje.getBytes());
        byte[] digest = md.digest();
        String result = new BigInteger(1, digest).toString(16).toLowerCase();
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresa el la cadena que representa el hash:");
        codigoCriptografico = sc.nextLine();
        System.out.println("Ingresa la sal a utilizar:");
        sal = sc.nextLine();
        System.out.println("Ingresa el tipo de SHA a usar, 256 o 512:");
        algoritmo = sc.nextLine();
        System.out.println("Ingresa el número de theads a usar:");
        numThreads = sc.nextInt();
        encontrarMensaje();
    }
}
