package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Historic;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class HistoricDAO {

    private final SessionFactory sessionFactory;

    public HistoricDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveHistoric(Historic historic) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(historic);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al guardar històric: " + e.getMessage());
        }
    }

    public Historic getHistoricById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Historic.class, id);
        } catch (HibernateException e) {
            System.err.println("Error al obtenir històric: " + e.getMessage());
            return null;
        }
    }

    public void updateHistoric(Historic historic) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(historic);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al actualitzar històric: " + e.getMessage());
        }
    }

    public void deleteHistoric(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Historic historic = session.find(Historic.class, id);
            if (historic != null) {
                session.remove(historic);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al eliminar històric: " + e.getMessage());
        }
    }

    public List<Historic> getAllHistorics() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Historic", Historic.class).getResultList();
        } catch (HibernateException e) {
            System.err.println("Error al llistar històrics: " + e.getMessage());
            return null;
        }
    }
}