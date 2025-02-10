package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Empleat;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

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
            return session.createQuery("FROM Empleat", Empleat.class).getResultList();
        } catch (HibernateException e) {
            System.err.println("Error al llistar empleats: " + e.getMessage());
            return null;
        }
    }
}