package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.model.Grado;
import com.example.model.Asignatura;

public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = factory.openSession()) {

            Transaction tx = session.beginTransaction();

            try {
                // 1. Ficheros bien mapeados, probar creando métodos toString no circulares
                Grado nuevoGrado = new Grado();
                nuevoGrado.setNombre("Ingeniería Informática");
                session.persist(nuevoGrado);

                Asignatura nuevaAsignatura = new Asignatura();
                nuevaAsignatura.setNombre("Programación");
                nuevaAsignatura.setCreditos(6);
                nuevaAsignatura.setGrado(nuevoGrado);
                session.persist(nuevaAsignatura);

                // 2. Dos consultas en HQL que involucren más de una tabla o cosas más complejas como group by
                String hqlCompleja1 = "SELECT g.nombre, COUNT(a.id) FROM Grado g JOIN g.asignaturas a GROUP BY g.nombre";
                List<Object[]> resultadosHql1 = session.createQuery(hqlCompleja1).list();
                for (Object[] row : resultadosHql1) {
                    logger.info("Grado: " + row[0] + " - Número de asignaturas: " + row[1]);
                }

                String hqlCompleja2 = "SELECT a.nombre, g.nombre FROM Asignatura a JOIN a.grado g";
                List<Object[]> resultadosHql2 = session.createQuery(hqlCompleja2).list();
                for (Object[] row : resultadosHql2) {
                    logger.info("Asignatura: " + row[0] + " - Grado: " + row[1]);
                }

                // 3. Dos consultas en SQL que involucren más de una tabla
                String sqlCompleja1 = "SELECT g.nombre AS grado_nombre, COUNT(a.id) AS num_asignaturas FROM grado g JOIN asignatura a ON g.id = a.id_grado GROUP BY g.nombre";
                List<Object[]> resultadosSql1 = session.createNativeQuery(sqlCompleja1).list();
                for (Object[] row : resultadosSql1) {
                    logger.info("Grado: " + row[0] + " - Número de asignaturas: " + row[1]);
                }

                String sqlCompleja2 = "SELECT a.nombre AS asignatura_nombre, g.nombre AS grado_nombre FROM asignatura a JOIN grado g ON a.id_grado = g.id";
                List<Object[]> resultadosSql2 = session.createNativeQuery(sqlCompleja2).list();
                for (Object[] row : resultadosSql2) {
                    logger.info("Asignatura: " + row[0] + " - Grado: " + row[1]);
                }

                // 4. Guardar nueva instancia que a su vez tenga alguna instancia en su estructura
                Grado nuevoGrado2 = new Grado();
                nuevoGrado2.setNombre("Ingeniería Mecánica");
                Asignatura nuevaAsignatura2 = new Asignatura();
                nuevaAsignatura2.setNombre("Mecánica de Fluidos");
                nuevaAsignatura2.setCreditos(6);
                nuevaAsignatura2.setGrado(nuevoGrado2);
                session.persist(nuevoGrado2);
                session.persist(nuevaAsignatura2);

                // 5. Actualizar instancia que a su vez tenga alguna instancia en su estructura
                Grado gradoExistente = session.get(Grado.class, nuevoGrado.getId());
                if (gradoExistente != null) {
                    gradoExistente.setNombre("Ingeniería Informática Actualizada");
                    for (Asignatura asignatura : gradoExistente.getAsignaturas()) {
                        asignatura.setNombre(asignatura.getNombre() + " Actualizada");
                        session.update(asignatura);
                    }
                    session.update(gradoExistente);
                }

                // 6. Eliminar instancia que a su vez tenga alguna instancia en su estructura
                Grado gradoAEliminar = session.get(Grado.class, nuevoGrado2.getId());
                if (gradoAEliminar != null) {
                    for (Asignatura asignatura : gradoAEliminar.getAsignaturas()) {
                        session.delete(asignatura);
                    }
                    session.delete(gradoAEliminar);
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            }
        }
    }
}