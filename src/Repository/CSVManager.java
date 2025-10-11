package Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Entities.Tarea;
import Enums.Estado;
import Enums.Prioridad;

public class CSVManager {

  private static String rutaTareas = "./src/Archives/tareas.csv";// Ruta del archivo CSV

  public static void crearArchivoTareas() {
    File archivoTareas = new File(rutaTareas);// Creo un objeto File con la ruta del archivo
    if (!archivoTareas.exists()) {// Si no existe el archivo lo creo
      try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaTareas))) {// Creo un BufferedWriter para
                                                                                      // escribir en el archivo
        escritor.write("id,titulo,descripcion,fecha_inicio,fecha_vencimiento,estado,prioridad");// Escribo la cabecera
                                                                                                // del archivo
        escritor.newLine();
      } catch (Exception e) {
        System.out.println("Error al crear el archivo: " + e.getMessage());
      }
    }
  }

  public static List<Tarea> leerTareas() {// Leo las tareas del archivo CSV y las retorno en una lista

    List<Tarea> tareas = new ArrayList<>();// Lista para almacenar las tareas leídas

    try (BufferedReader lector = new BufferedReader(new FileReader(rutaTareas))) {
      lector.readLine();
      String linea;
      while ((linea = lector.readLine()) != null) {
        String[] tarea = linea.split(",");
        LocalDate fecha_inicio = LocalDate.parse(tarea[3]);
        LocalDate fecha_vencimiento = LocalDate.parse(tarea[4]);
        tareas.add(new Tarea(Long.parseLong(tarea[0]), tarea[1], tarea[2], fecha_inicio, fecha_vencimiento,
            Estado.valueOf(tarea[5]),
            Prioridad.valueOf(tarea[6])));
      }
    } catch (Exception e) {
      System.out.println("Error al leer las tareas: " + e.getMessage());
    }

    return tareas;// Todavia no esta guardando nada en la lista
  }

  public static void agregarTarea(Tarea tarea) {
    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaTareas, true))) {
      escritor.write(tarea.toCSV());
      escritor.newLine();
    } catch (Exception e) {
      System.out.println("Error al escribir la tarea " + e.getMessage());
    }
  }

  public static void reescribirTareas(Map<Long, Tarea> tareas) {
    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaTareas))) {
      escritor.write("id,titulo,descripcion,fecha_inicio,fecha_vencimiento,estado,prioridad");
      escritor.newLine();
      for (Tarea t : tareas.values()) {
        escritor.write(t.toCSV());
        escritor.newLine();
      }
    } catch (Exception e) {
      System.out.println("Error al reescribir el CSV de tareas");
    }
  }
}
