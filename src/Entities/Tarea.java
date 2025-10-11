package Entities;

import java.time.LocalDate;
import Enums.Estado;
import Enums.Prioridad;

public class Tarea {
  private Long id;
  private String titulo;
  private String descripcion;
  private LocalDate fechaInicio;
  private LocalDate fechaVencimiento;
  private Estado estado;// Enum
  private Prioridad prioridad;// Enum

  public Tarea(Long id, String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaVencimiento,
      Estado estado,
      Prioridad prioridad) {
    this.id = id;
    this.descripcion = descripcion;
    this.estado = estado;
    this.fechaInicio = fechaInicio;
    this.fechaVencimiento = fechaVencimiento;
    this.prioridad = prioridad;
    this.titulo = titulo;

  }

  // Getters y setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public LocalDate getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDate fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public LocalDate getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(LocalDate fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Prioridad getPrioridad() {
    return prioridad;
  }

  public void setPrioridad(Prioridad prioridad) {
    this.prioridad = prioridad;
  }

  // toString y toCSV
  @Override
  public String toString() {
    return "ID: " + this.id + " - Titulo: " + this.titulo +
        "\nDescripcion: " + this.descripcion +
        "\nFecha Inicio: " + this.fechaInicio + " - Fecha Vencimiento: " + this.fechaVencimiento +
        "\nnEstado: " + this.estado + " - Prioridad: " + this.prioridad;
  }

  public String toCSV() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getId())
        .append(",")
        .append(this.getTitulo())
        .append(",")
        .append(this.getDescripcion())
        .append(",")
        .append(this.getFechaInicio())
        .append(",")
        .append(this.getFechaVencimiento())
        .append(",")
        .append(this.getPrioridad())
        .append(",")
        .append(this.getEstado());
    return sb.toString();
  }

}