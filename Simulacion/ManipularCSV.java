package Simulacion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class ManipularCSV {
	
	private BufferedReader lector; // Lector del archivo
	private String linea; // Almacena cada línea leída
	private String[] partes; // Array para almacenar las partes de la línea
	private int contador = 0; //saber cuantos valores tenemos en nuestro documento
	private ArrayList<Double> listaValores = new ArrayList<>();

	public void leerArchivo(String nombreArchivo) {
		try {
			lector = new BufferedReader(new FileReader(nombreArchivo)); //son clases que permiten leer archivos de texto
			while ((linea = lector.readLine()) != null) { //hasta que se cumpla esta condicion
				partes = linea.split(","); // para dividir una cadena en subcadenas
				 if (partes.length > 0) {  // Evitar errores con líneas vacías
	                    String valorLimpio = partes[0].replaceAll("[^0-9.]", "").trim(); //.trim eliminar los espacios en blanco que se encuentran al inicio y al final de una cadena

	                    try {
	                        double valor = Double.parseDouble(valorLimpio);//castear
	                        listaValores.add(valor);
	                        contador++;
	                    } catch (NumberFormatException e) {
	                        //System.out.println("Número inválido en la línea: " + linea);
	                    }
	                }
			}
			lector.close();
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	public void imprimirLinea() {
		if (partes != null) { // Validación para evitar NullPointerException
			for (String parte : partes) {
				System.out.print(parte + " ");
				contador++;
			}
		}
	}
	
	public int getContador() {
        return contador;
    }
	
	public ArrayList<Double> ListaValores(){
		return listaValores;
	}
	
}