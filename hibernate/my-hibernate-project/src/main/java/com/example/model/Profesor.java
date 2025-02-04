package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    private int idProfesor;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    private String nombre;

    // Getters and setters
    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", departamento=" + departamento +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}