package App;

import java.time.LocalDate;
import java.util.Scanner;

import Enums.Estado;
import Enums.Prioridad;
import Services.GestorTareas;
import Services.Validador;
import Entities.Tarea;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        GestorTareas gestorTareas = new GestorTareas();
        int opcion = 0;

        do {
            System.out.println("Menú: ");
            System.out.println("1) Crear tarea");
            System.out.println("2) Listar tareas");
            System.out.println("3) Editar tarea");
            System.out.println("4) Eliminar tarea");
            System.out.println("5) Reportes");
            System.out.println("6) Salir");
            System.out.println("Ingrese opcion: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    String titulo; // Variables
                do {
                    System.out.println("Ingrese el titulo de la tarea: ");// Pido la informacion
                    titulo = scanner.nextLine();
                    if (!Validador.validarStrings(titulo)) {
                        System.out.println("El titulo no puede estar vacio.");
                    }
                } while (!Validador.validarStrings(titulo));    
                
                String descripcion;
                do {
                   System.out.println("Ingrese la descripcion de la tarea: ");// Pido la informacion
                    descripcion = scanner.nextLine();
                    if (!Validador.validarStrings(descripcion)) {
                        System.out.println("La descripcion no puede estar vacia.");
                    } 
                } while (!Validador.validarStrings(descripcion));
                
                String fechaInicio;
                do {
                    System.out.println("Ingrese la fecha de inicio de la tarea (YYYY-MM-DD): ");// Pido la informacion
                    fechaInicio = scanner.nextLine();// Transformo el String a LocalDate

                } while (!Validador.validarStrings(fechaInicio));
                    
                    String fechaVencimiento;
                    
                    do {
                    System.out.println("Ingrese la fecha de vencimiento de la tarea (YYYY-MM-DD): ");// Pido la
                                                                                                     // informacion
                    fechaVencimiento = scanner.nextLine();// Transformo el String a LocalDate   
                    } while (!Validador.validarStrings(fechaVencimiento));
                    
                    Estado estado;
                    
                    do {
                    System.out.println("Ingrese el estado de la tarea (PENDIENTE, EN_PROGRESO, COMPLETADA): ");// Pido
                                                                                                                // la
                                                                                                               // informacion
                    estado = Estado.valueOf(scanner.nextLine().toUpperCase());// En mayusculas para evitar errores 
                    if (estado == null) {
                        System.out.println("El estado no puede estar vacio.");
                    }                    
                    } while (estado == null);

                    Prioridad prioridad;

                    do {
                    System.out.println("Ingrese la prioridad de la tarea (BAJA, MEDIA, ALTA): ");// Pido la informacion
                    prioridad = Prioridad.valueOf(scanner.nextLine().toUpperCase());// En mayusculas para evitar errores
                    if (prioridad==null) {
                        System.out.println("La prioridad no puede estar vacia.");
                    }                        
                    } while (prioridad == null);

                    gestorTareas.crearTarea(titulo, descripcion, fechaInicio, fechaVencimiento, estado, prioridad);
                    break;
                case 2:
                    gestorTareas.listarTareas();
                    break;
                case 3:
                    System.out.print("Ingrese el id de la tarea que desea buscar: ");
                    Tarea tarea;
                    try {
                        gestorTareas.buscarPorId(scanner.nextLong());
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

                        switch (op) {
                            case 1:

                                break;

                            default:
                                break;
                        }

                    } while (op != 6);
                    break;
                case 4:
                    try {
                        System.out.println("Ingrese el ID de la tarea que desea eliminar: ");
                        Long id = scanner.nextLong();
                        if (id) {
                            System.out.println("Error: el ID no puede estar vacio.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        } while (opcion != 6);
        scanner.close();
    }
}
