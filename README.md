# Sistema de Gestión de Tareas "To Do"
## 1. Objetivo
- Desarrollar una aplicación en Java que permite gestionar tareas, persistiendo los datos en un archivo .csv. Implementar operaciones CRUD (Alta, Baja, Modificación y Listado) con validaciones esenciales y restricciones de datos.
---

## 2. Entidades y Formatos
### 2.1 Entidad: Tarea (Campos/CSV)
- Se almacenará en un archivo tareas.csv.
- Cabecera: id,titulo,descripcion,fecha_inicio,fecha_vencimiento,estado,prioridad
- Tipos y Ejemplos:
  - **id** -> Numerico long unico y auto-incremental.
  - **titulo** -> Texto (Ej: Llamar proveedor)
  - **descripcion** -> Texto largo (campos con comas deben ir entre comillas)
  - **fecha_inicio** -> Formato YYYY-MM-DD (Ej: 2025-10-10)
  - **fecha_vencimiento** -> Formato YYYY-MM-DD
  - **estado** -> Enum: Pendiendte, en_progreso, completada
  - **prioridad** -> Enum: alta,media,baja
- Ejemplo de fila en el CSV: 1, "Comprar cemento", "Comprar 10 bolsas de 50kg", "2025-10-01", "pendiende", "alta"
### 2.2 Enumeraciones
- **Estado**: pendiente, en_progreso, completada
- **Prioridad**: alta,media,baja
---

## 3 Requerimientos funcionales (CRUD)
### 3.1 Operaciones minimas
- Crear: Ingresar datos. El id debe generarse automaticamente.
- Listar: Listar todas las tareas.
- Actualizar: Modificar cualquier campo excepto **id**. La búsqueda de la tarea a modificar debe ser por su ID.
- Eliminar: Eliminar la tarea por su **ID**.
### 3.2 Reglas y validaciones
1. Menús: Mostrar opciones enumeradas para estados y prioridades, y validar que la entrada sea una opción válida.
2. Detección de duplicados: Antes de crear una tarea, verificar si ya existe otra con el título exactamente igual (puede ser case-insensitive). Si existe, preguntar confirmación o rechazar la creación.
3. Formato de fechas: Validar formato YYYY-MM-DD. Validar que fecha_vencimiento >= fecha_inicio (si ambas existen).
4. Entrada robusta: Manejar errores de parsing del CSV, campos vacíos obligatorios, y opciones numéricas inválidas del usuario.
---

## 4 Persistencia y Eficiencia (Requisito Clave)
### 4.1 Uso del Map para la Gestión de Memoria
- Para garantizar la eficiencia en la gestion de tareas la aplicación debe utilizar la interfaz Map de Java para mantener los datos en memoria.
  - Al cargar el archivo tareas.csv, todos los datos deben ser leídos y almacenados en una coleccion Map<Long, Tarea> donde la clave sea el **id** de la tarea.
  - Todas las operaciones CRUD deben hacerse utilizando esta estructura, garantizando un rendimiento optimo (O(1)).
### 4.2 Generación de ID
- Debe implementarse un mecanismo para asegurarse que el nuevo id asignado a cada tarea creada se unico y consecutivo.
---

## 5 Features 1.1.0 -> Reportes
- Implementar las siguientes funcionalidades de listado avanzadas:
  1. Listar todas las tareas: Imprimir lista completa de tareas.
  2. Tareas por Estado: Listar y agrupar las tareas por su **estado**.
  3. Tareas de la semana actual: Listar todas las tareas cuya **fecha_vencimiento** caiga dentro de la semana actual del sistema. (Desde día actual a 7 días en adelante)
---

## 6 Ejemplo de la interfaz CLI (flujo)
### Al ejecutar mostrar:
Menu:
1) Crear tarea
2) Listar tareas
3) Editar tarea
4) Eliminar tarea
5) Reportes
6) Salir
-Ingrese opción:
---

## 7 Recomendaciones y buenas practicas
- Estructura: Organizar en paquetes (entidades, enums, servicios -> incluirá la logica del map y el acceso a CSV).
- Persitencia: Separar la logica acceso a csv del resto del sistema.
- Manejo de fechas: Usar la API java.time para validación y el reporte de la semana actual.
- Git/Github: Realizar commits frecuentes en ramas.
