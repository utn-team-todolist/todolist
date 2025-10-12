package Services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Entities.Tarea;
import Enums.Estado;
import Enums.Prioridad;
import Repository.CSVManager;

public class GestorTareas {

    // Atributos
    // Mapa para almacenar las tareas con su ID como clave
    private Map<Long, Tarea> mapTareas = new HashMap<>();

    Scanner scanner = new Scanner(System.in);

    public GestorTareas() {
        List<Tarea> listaTareas = CSVManager.leerTareas();
        transformarListaAHashMap(listaTareas);
    }
    // Metodos

    // Transformarm de list a hashmap
    public void transformarListaAHashMap(List<Tarea> lista) {
        for (Tarea tarea : lista) {
            mapTareas.put(tarea.getId(), tarea);
        }
    }

    public void crearTarea(String titulo, String descripcion, LocalDate fechaInicial, LocalDate fechaFinal, Estado estado,
            Prioridad prioridad) throws Exception {
        if (fechaFinal != null && fechaInicial != null && fechaFinal.isBefore(fechaInicial)) {
            throw new Exception("Ya paso la fecha de vencimiento");
        }
        if (buscarDuplicacion(titulo)) {
            System.out.println("Tienes una tarea con el mismo titulo, confirmar o rechazar s/n ");
            String opcion = scanner.nextLine();
            if (opcion.equalsIgnoreCase("s")) {
                Tarea tarea = new Tarea(crearId(), titulo, descripcion, fechaInicial, fechaFinal, estado,
                        prioridad);
                mapTareas.put(tarea.getId(), tarea);
                CSVManager.agregarTarea(tarea);
                System.out.println("Tarea creada con exito");
            }
            if (opcion.equalsIgnoreCase("n")) {
                System.out.println("Tarea rechazada");

            }
        } else {
            Tarea tarea = new Tarea(crearId(), titulo, descripcion, fechaInicial, fechaFinal, estado, prioridad);
            mapTareas.put(tarea.getId(), tarea);
            CSVManager.agregarTarea(tarea);
            System.out.println("Tarea creada con exito");

        }
    }

    public Boolean buscarDuplicacion(String titulo) {
        for (Tarea tarea : mapTareas.values()) {
            if (tarea.getTitulo().equalsIgnoreCase(titulo)) {
                return true;
            }
        }
        return false;
    }

    public void listarTareas() {// Recibo el mapa de tareas
        if (mapTareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar");
            return;
        }
        for (Tarea i : mapTareas.values()) {// Uso values para obtener solo los valores del mapa
            System.out.println(i.toString());// Imprimo la tarea
            System.out.println("-----------");
        }
    }

    public Tarea buscarPorId(Long id) throws Exception {
        if (!mapTareas.containsKey(id)) { // si no contiene el id tira la excepcion
            throw new Exception("El id buscado no existe");
        }
        Tarea tareaBuscada = null; // variable auxiliar para retornar tarea
        if (mapTareas.containsKey(id)) { // si el id esta en el map
            tareaBuscada = mapTareas.get(id); // adsigna la tarea del id buscado a la auxiliar
        }
        return tareaBuscada;
    }

    public Long crearId() {
        if (mapTareas.isEmpty()) {
            return Long.parseLong("1"); // si el map esta vacio, devuelve el 1
        }

        Long nuevo = Long.parseLong("0");
        for (Long i : mapTareas.keySet()) { // compara y devuevle el mayor
            if (i > nuevo) {
                nuevo = i;
            }
        }
        return (nuevo + 1); // nuevo + 1 para que sea el siguente
    }

    public void eliminarTarea(Long id) throws Exception {
        if (!mapTareas.containsKey(id)) {
            throw new Exception("No se encontro ese ID");
        } else {
            mapTareas.remove(id);
            CSVManager.reescribirTareas(mapTareas);
        }
        System.out.println("Tarea eliminada con exito");
    }

    public void guardarCambios() {
        CSVManager.reescribirTareas(mapTareas);
    }

    public void listarTareasPorEstado(){
        if (mapTareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar");
            return;
        }
        for (Estado estado : Estado.values()) {
            Boolean hayTareas = false;
            System.out.println("Estado: " + estado);
            for (Tarea tarea : mapTareas.values()) {
                if (tarea.getEstado() == estado) {
                    System.out.println(tarea.toString());
                    hayTareas = true;
                }
            }
            if (!hayTareas) {
                System.out.println("No hay tares con ese estado.");
            }
        System.out.println();    
        }
    }

}
