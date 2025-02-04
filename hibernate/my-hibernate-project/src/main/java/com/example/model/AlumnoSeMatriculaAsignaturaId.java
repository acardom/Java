package com.example.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AlumnoSeMatriculaAsignaturaId implements Serializable {
    private int idAlumno;
    private int idAsignatura;
    private int idCursoEscolar;

    // Getters and setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public int getIdCursoEscolar() {
        return idCursoEscolar;
    }

    public void setIdCursoEscolar(int idCursoEscolar) {
        this.idCursoEscolar = idCursoEscolar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlumnoSeMatriculaAsignaturaId that = (AlumnoSeMatriculaAsignaturaId) o;
        return idAlumno == that.idAlumno && idAsignatura == that.idAsignatura && idCursoEscolar == that.idCursoEscolar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlumno, idAsignatura, idCursoEscolar);
    }
}