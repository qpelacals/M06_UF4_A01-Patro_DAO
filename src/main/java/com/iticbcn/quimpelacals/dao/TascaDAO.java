package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Tasca;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class TascaDAO {

    private SessionFactory sessionFactory;

    public TascaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveTasca(Tasca tasca) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(tasca);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al guardar tasca: " + e.getMessage());
        }
    }

    public Tasca getTascaById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Tasca.class, id);
        } catch (HibernateException e) {
            System.err.println("Error al obtenir tasca: " + e.getMessage());
            return null;
        }
    }

    public void updateTasca(Tasca tasca) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(tasca);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al actualitzar tasca: " + e.getMessage());
        }
    }

    public void deleteTasca(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Tasca tasca = session.find(Tasca.class, id);
            if (tasca != null) {
                session.remove(tasca);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al eliminar tasca: " + e.getMessage());
        }
    }

    public List<Tasca> getAllTasques() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tasca", Tasca.class).getResultList();
        } catch (HibernateException e) {
            System.err.println("Error al llistar tasques: " + e.getMessage());
            return null;
        }
    }
}