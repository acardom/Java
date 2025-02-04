package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "curso_escolar")
public class CursoEscolar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CursoEscolar{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}