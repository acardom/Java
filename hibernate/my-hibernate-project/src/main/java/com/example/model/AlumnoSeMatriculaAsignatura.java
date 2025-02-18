package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "alumno_se_matricula_asignatura")
public class AlumnoSeMatriculaAsignatura {
    @EmbeddedId
    private AlumnoSeMatriculaAsignaturaId id;

    @ManyToOne
    @MapsId("idAlumno")
    @JoinColumn(name = "id_alumno")
    private Persona alumno;

    @ManyToOne
    @MapsId("idAsignatura")
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

    @ManyToOne
    @MapsId("idCursoEscolar")
    @JoinColumn(name = "id_curso_escolar")
    private CursoEscolar cursoEscolar;

    // Getters and setters
    public AlumnoSeMatriculaAsignaturaId getId() {
        return id;
    }

    public void setId(AlumnoSeMatriculaAsignaturaId id) {
        this.id = id;
    }

    public Persona getAlumno() {
        return alumno;
    }

    public void setAlumno(Persona alumno) {
        this.alumno = alumno;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public CursoEscolar getCursoEscolar() {
        return cursoEscolar;
    }

    public void setCursoEscolar(CursoEscolar cursoEscolar) {
        this.cursoEscolar = cursoEscolar;
    }

    @Override
    public String toString() {
        return "AlumnoSeMatriculaAsignatura{" +
                "id=" + id +
                ", alumno=" + alumno +
                ", asignatura=" + asignatura +
                ", cursoEscolar=" + cursoEscolar +
                '}';
    }
}