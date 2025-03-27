package Simulacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Kolmogorov {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ManipularCSV archivo = new ManipularCSV(); // Crear objeto
        archivo.leerArchivo("/workspaces/SimulacionEquipoPi-a/Simulacion/Datos.csv"); 
        int n = archivo.getContador();
        double error;

        System.out.println("Ingrese el error: ");
        error = input.nextDouble();
        input.close();
        ArrayList<Double> serie = archivo.ListaValores(); // Usar ArrayList directamente
        Collections.sort(serie); // Ordenar el ArrayList
  
        double[] fi = new double[n];
        double[] Ui = new double[n];

        recursivo(serie, fi, Ui, 0, n, 1.0 / n, (error / 100.00), Double.NEGATIVE_INFINITY); // Inicializar d1 como el valor mínimo posible
    }

    static void recursivo( ArrayList<Double> serie, double[] fi, double[] Ui, int index, int n, double fn, double a, double d1) {
        if (index < n) {
            fi[index] = (index + 1) * fn;
            Ui[index] = serie.get( index) - fi[index];
            d1 = Math.max(d1, Ui[index]); // Actualizar d1 si Ui[index] es mayor
            recursivo(serie, fi, Ui, index + 1, n, fn, a, d1);
        } else {
            double d2 = calcularH1(n, a);
            imprimirResultados(serie, fi, Ui, n, a, fn, d1, d2);
        }
    }

    static double calcularH1(double n, double a) {
        if (a == 0.01) return 1.63 / Math.sqrt(n);
        if (a == 0.05) return 1.36 / Math.sqrt(n);
        if (a == 0.1) return 1.22 / Math.sqrt(n);
        if (a == 0.15) return 1.14 / Math.sqrt(n);
        if (a == 0.2) return 1.07 / Math.sqrt(n);
        return 0; // Valor por defecto en caso de error
    }

    static void imprimirResultados(ArrayList<Double> serie, double[] fi, double[] Ui, int n, double a, double fn, double d1, double d2) {
        System.out.println("n=" + n);
        System.out.println("Error= " + (a * 100));
        System.out.println("Fn= " + fn);
        System.out.println("D1= " + d1);
        System.out.println("D2= " + d2);
        System.out.println("------------------------------------------------");
        System.out.println("| i     | Valor       | Fi(Ui)      | Ui - Fi(Ui)|");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < serie.size(); i++) {
            System.out.printf("| %-5d | %-10.5f | %-10.5f | %-10.5f |%n", (i + 1), serie.get(i), fi[i], Ui[i]);
        }
        System.out.println("------------------------------------------------");

        if (d1 < d2) {
            System.out.println("No hay evidencia suficiente para decir que estos numeros no estan distribuidos uniformemente");
        } else {
            System.out.println("Hay evidencia suficiente para decir que estos numeros no estan distribuidos uniformemente");
        }
    }
}