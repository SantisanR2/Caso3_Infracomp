import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ThreadBusqueda extends Thread{

    private char[] espacioBusqueda;
    private int longitud;
    private String codigoCriptografico;
    private String sal;
    private String algoritmo;
    private StringBuilder sb = new StringBuilder();
    private long tiempoInicial;
    private long tiempoFinal;

    public ThreadBusqueda(char[] espacioBusqueda, int longitud, String codigoCriptografico, String sal, String algoritmo) {
        this.espacioBusqueda = espacioBusqueda;
        this.longitud = longitud;
        this.codigoCriptografico = codigoCriptografico;
        this.sal = sal;
        this.algoritmo = algoritmo;
        tiempoInicial = System.nanoTime();
    }

    public void run() {
        generarCombinaciones(espacioBusqueda, longitud, codigoCriptografico, sal, algoritmo);
    }

    public void generarCombinaciones(char[] espacioBusqueda, int longitud, String codigoCriptografico, String sal, String algoritmo) {
        if (longitud == 0) {
            // Calcular el código criptográfico de hash del mensaje con la sal y compararlo con el código dado
            String mensajeEncontrado = sb.toString();
            String codigoCriptograficoActual = encriptarSHA(mensajeEncontrado + sal, algoritmo);
            if (codigoCriptograficoActual.equals(codigoCriptografico)) {
                System.out.println("Mensaje encontrado: " + mensajeEncontrado);
                tiempoFinal = System.nanoTime();
                long tiempo = tiempoFinal - tiempoInicial;
                System.out.println("La sal utilizada fue: " + sal);
                System.out.println("El tiempo de decifrado fue de: " + tiempo/1e9 + "s");
                System.exit(0); // Salir del programa cuando se encuentra una coincidencia
            }
        } else {
            // Generar todas las posibles combinaciones de caracteres para la longitud actual
            for (char c : espacioBusqueda) {
                sb.append(c);
                generarCombinaciones(espacioBusqueda, longitud - 1, codigoCriptografico, sal, algoritmo);
                sb.setLength(sb.length() - 1); // Retroceder para probar la siguiente combinación
            }
        }
    }

    public String encriptarSHA(String mensaje, String algoritmo){
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
}
