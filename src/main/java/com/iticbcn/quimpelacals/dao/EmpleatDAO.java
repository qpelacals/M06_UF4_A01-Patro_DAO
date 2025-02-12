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

public class EmpleatDAO {

    private final SessionFactory sessionFactory;

    public EmpleatDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveEmpleat(Empleat empleat) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(empleat);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al guardar empleat: " + e.getMessage());
        }
    }

    public Empleat getEmpleatById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Empleat.class, id);
        } catch (HibernateException e) {
            System.err.println("Error al obtenir empleat: " + e.getMessage());
            return null;
        }
    }

    public void updateEmpleat(Empleat empleat) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(empleat);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al actualitzar empleat: " + e.getMessage());
        }
    }

    public void deleteEmpleat(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Empleat empleat = session.find(Empleat.class, id);
            if (empleat != null) {
                session.remove(empleat);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al eliminar empleat: " + e.getMessage());
        }
    }

    public List<Empleat> getAllEmpleats() {
        try (Session session = sessionFactory.openSession()) {
            Query<Empleat> query = session.createQuery("FROM Empleat", Empleat.class);
            return query.list();
        } catch (HibernateException e) {
            System.err.println("Error al llistar empleats: " + e.getMessage());
            return null;
        }
    }

    public Map<String, Long> countTasksPerEmployee() {
        try (Session session = sessionFactory.openSession()) {
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