package Services;

import java.time.LocalDate;
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
    private Map<Long, Tarea> mapTareas;

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

    public void crearTarea(String titulo, String descripcion, String fechaInicial, String fechaFinal, Estado estado,
            Prioridad prioridad) throws Exception {
        LocalDate fechaInicio = null;
        LocalDate fechaVencimiento = null;
        try {
            fechaInicio = LocalDate.parse(fechaInicial);
            fechaVencimiento = LocalDate.parse(fechaFinal);
        } catch (Exception e) {
            System.out.println("Formaro fecha no valida");
        }
        if (fechaVencimiento != null && fechaInicio != null && fechaVencimiento.isBefore(fechaInicio)) {
            throw new Exception("Ya paso la fecha de vencimiento");
        }
        if (buscarDuplicacion(titulo)) {
            System.out.println("Tienes una tarea con el mismo titulo, confirmar o rechazar s/n ");
            String opcion = scanner.nextLine();
            if (opcion.equalsIgnoreCase("s")) {
                Tarea tarea = new Tarea(titulo, descripcion, fechaInicio, fechaVencimiento, estado, prioridad);
                mapTareas.put(crearId(), tarea);
            }
            if (opcion.equalsIgnoreCase("n")) {
                System.out.println("Tarea rechazada");

            }
        } else {
            Tarea tarea = new Tarea(titulo, descripcion, fechaInicio, fechaVencimiento, estado, prioridad);
            mapTareas.put(crearId(), tarea);

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

}
