package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Empleat;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpleatDAO extends GenDAOImpl<Empleat> {

    public EmpleatDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Empleat.class);
    }

    public Map<String, Long> countTasksPerEmployee() {
        try (Session session = super.getSessionFactory().openSession()) {
            // Consulta HQL per comptar tasques per empleat
            String hql = "SELECT CONCAT(e.nom, ' ', e.cognoms), COUNT(t.id) " +
                    "FROM Empleat e " +
                    "LEFT JOIN e.tasques t " + // Relació @OneToMany entre Empleat i Tasca
                    "GROUP BY e.nom, e.cognoms";

            // Crear i executar la consulta
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> results = query.list();

            // Processar els resultats i construir el mapa
            Map<String, Long> resultMap = new HashMap<>();
            for (Object[] result : results) {
                String nomComplet = (String) result[0]; // Nom i cognom de l'empleat
                Long taskCount = (Long) result[1];      // Nombre de tasques
                resultMap.put(nomComplet, taskCount);
            }

            return resultMap; // Retornar el mapa amb els resultats
        } catch (Exception e) {
            System.err.println("Error al executar countTasksPerEmployee: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyMap(); // Retornar un mapa buit en cas d'error
        }
    }
}