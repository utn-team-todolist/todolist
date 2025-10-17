package App;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Enums.Estado;
import Enums.Prioridad;
import Repository.CSVManager;
import Services.GestorTareas;
import Services.Validador;
import Entities.Tarea;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static GestorTareas gestorTareas = new GestorTareas();

    public static void main(String[] args) throws Exception {
        CSVManager.crearArchivoTareas();
        int opcion;
        do {
            opcion = mostrarMenu();
            switch (opcion) {
                case 1:
                    menuCrearTarea();
                    System.out.println("Tarea creada con exito.");
                    break;
                case 2:
                    gestorTareas.listarTareas();
                    break;
                case 3:
                    menuModificarTarea();
                    break;
                case 4:
                    menuEliminarTarea();
                    break;
                case 5:
                    gestorTareas.listarTareasPorEstado();
                    break;
                case 6:
                    gestorTareas.listarPorSemana();
                    break;
                default:
                    break;
            }
        } while (opcion != 7);
        scanner.close();
    }

    public static Integer mostrarMenu() {
        while (true) {
            System.out.println("----- MENU -----");
            System.out.println("1. Crear tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Modificar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Listar tareas por estado");
            System.out.println("6. Listar tareas de la semana");
            System.out.println("7. Salir");
            System.out.print("Ingrese una opcion: ");

            String opStr = scanner.nextLine().trim();
            if (opStr.isEmpty()) {
                System.out.println("Error: opcion vacia. Ingrese un numero entre 1 y 7.");
                continue;
            }
            try {
                int op = Integer.parseInt(opStr);
                if (op < 1 || op > 7) {
                    System.out.println("Error: la opcion debe ser un numero entre 1 y 7.");
                    continue;
                }
                return op;
            } catch (NumberFormatException e) {
                System.out.println("Error: opcion invalida. Ingrese un numero entre 1 y 7.");
            }
        }
    }

    public static void menuCrearTarea() throws Exception {
        String titulo;
        while (true) {
            System.out.println("Ingrese el titulo de la tarea: ");
            titulo = scanner.nextLine().trim();
            if (!Validador.validarStrings(titulo)) {
                System.out.println("El titulo no puede estar vacio.");
                continue;
            }

            // Si ya existe un título igual, pedir confirmación
            if (gestorTareas.buscarDuplicacion(titulo)) {
                System.out.println("Tienes una tarea con el mismo titulo, confirmar o rechazar (s/n): ");
                String opcion = scanner.nextLine().trim();
                if (opcion.equalsIgnoreCase("s")) {
                    break;
                } else if (opcion.equalsIgnoreCase("n")) {
                    //volver a pedir título
                    System.out.println("Ingrese otro titulo.");
                    continue;
                } else {
                    System.out.println("Opcion invalida. Ingrese 's' o 'n'.");
                    continue;
                }
            } else {
                // Título no duplicado, salir del bucle
                break;
            }
        }

        String descripcion;
        do {
            System.out.println("Ingrese la descripcion de la tarea: ");
            descripcion = scanner.nextLine();
            if (!Validador.validarStrings(descripcion)) {
                System.out.println("La descripcion no puede estar vacia.");
            }
        } while (!Validador.validarStrings(descripcion));

        String fechaInicioString;
        LocalDate fechaInicio = null;
        while (true) {
            System.out.println("Ingrese la fecha de inicio de la tarea (YYYY-MM-DD): ");
            fechaInicioString = scanner.nextLine();
            try {
                fechaInicio = LocalDate.parse(fechaInicioString); // Intenta parsear la fecha
                break; // Si no lanza excepción, sale del bucle
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }
        }

        String fechaVenciString;
        LocalDate fechaVencimiento = null;
        while (true) {
            System.out.println("Ingrese la fecha de vencimiento de la tarea (YYYY-MM-DD): ");
            fechaVenciString = scanner.nextLine().trim();
            if (!Validador.validarStrings(fechaVenciString)) {
                System.out.println("Error: la fecha no puede estar vacia.");
                continue;
            }
            try {
                fechaVencimiento = LocalDate.parse(fechaVenciString);
                if (fechaInicio != null && fechaVencimiento.isBefore(fechaInicio)) {
                    System.out.println("Error: la fecha de vencimiento no puede ser anterior a la fecha de inicio (" + fechaInicio + ").");
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }
        }

        Estado estado;
        do {
            System.out.println("Ingrese el estado de la tarea (PENDIENTE, EN PROGRESO, COMPLETADA): ");
            String estadoInput = scanner.nextLine().toUpperCase().replace(" ", "_");
            try {
                estado = Estado.valueOf(estadoInput);
            } catch (IllegalArgumentException e) {
                estado = null;
                System.out.println("Estado invalido. Intente nuevamente.");
            }
            if (estado == null) {
                System.out.println("El estado no puede estar vacio.");
            }
        } while (estado == null);

        Prioridad prioridad;
        do {
            System.out.println("Ingrese la prioridad de la tarea (BAJA, MEDIA, ALTA): ");
            try {
                prioridad = Prioridad.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                prioridad = null;
                System.out.println("Prioridad invalida. Intente nuevamente.");
            }
            if (prioridad == null) {
                System.out.println("La prioridad no puede estar vacia.");
            }
        } while (prioridad == null);

        gestorTareas.crearTarea(titulo, descripcion, fechaInicio, fechaVencimiento, estado, prioridad);
    }

    public static void menuModificarTarea() {
        System.out.print("Ingrese el id de la tarea que desea buscar: ");
        Tarea tarea = new Tarea();
        try {
            tarea = gestorTareas.buscarPorId(scanner.nextLong());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int op = 0;
        do {
            System.out.println("Ingrese la opcion que desea modificar.");
            System.out.println("1. Titulo");
            System.out.println("2. Descripcion");
            System.out.println("3. Fecha vencimiento");
            System.out.println("4. Estado");
            System.out.println("5. Prioridad");
            System.out.println("6. Volver al menu principal");
            op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    System.out.println("Ingrese el nuevo titulo: ");
                    String nuevoTitulo = scanner.nextLine();
                    if (Validador.validarStrings(nuevoTitulo)) {
                        tarea.setTitulo(nuevoTitulo);
                    } else {
                        System.out.println("Error: el titulo no puede estar vacio.");
                    }
                    break;
                case 2:
                    System.out.println("Ingrese la nueva descripcion: ");
                    String nuevaDescripcion = scanner.nextLine();
                    if (Validador.validarStrings(nuevaDescripcion)) {
                        tarea.setDescripcion(nuevaDescripcion);
                    } else {
                        System.out.println("Error: la descripcion no puede estar vacia.");
                    }
                    break;
                case 3:
                    System.out.println("Ingrese la nueva fecha de vencimiento (YYYY-MM-DD): ");
                    String nuevaFecha = scanner.nextLine();
                    if (Validador.validarStrings(nuevaFecha)) {
                        try {
                            tarea.setFechaVencimiento(LocalDate.parse(nuevaFecha));
                        } catch (DateTimeParseException e) {
                            System.out.println("Error: formato de fecha invalido.");
                        }
                    } else {
                        System.out.println("Error: la fecha no puede estar vacia.");
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el nuevo estado (PENDIENTE, EN_PROGRESO, COMPLETADA): ");
                    String nuevoEstado = scanner.nextLine().toUpperCase();
                    if (Validador.validarStrings(nuevoEstado)) {
                        try {
                            tarea.setEstado(Estado.valueOf(nuevoEstado));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: estado invalido.");
                        }
                    } else {
                        System.out.println("Error: el estado no puede estar vacio.");
                    }
                    break;
                case 5:
                    System.out.println("Ingrese la nueva prioridad (BAJA, MEDIA, ALTA): ");
                    String nuevaPrioridad = scanner.nextLine().toUpperCase();
                    if (Validador.validarStrings(nuevaPrioridad)) {
                        try {
                            tarea.setPrioridad(Prioridad.valueOf(nuevaPrioridad));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: prioridad invalida.");
                        }
                    } else {
                        System.out.println("Error: la prioridad no puede estar vacia.");
                    }
                    break;
                default:
                    break;
            }

        } while (op != 6);
        gestorTareas.guardarCambios();
        System.out.println("Cambios guardados con exito.");
    }

    public static void menuEliminarTarea() {
        try {
            System.out.println("Ingrese el ID de la tarea que desea eliminar: ");
            String id = scanner.nextLine();
            Long idLong = Long.parseLong(id);
            gestorTareas.eliminarTarea(idLong);
            System.out.println("Tarea eliminada con exito.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
