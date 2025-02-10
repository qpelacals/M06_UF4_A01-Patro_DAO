package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Equip;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class EquipDAO {

    private final SessionFactory sessionFactory;

    public EquipDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveEquip(Equip equip) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(equip);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al guardar equip: " + e.getMessage());
        }
    }

    public Equip getEquipById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Equip.class, id);
        } catch (HibernateException e) {
            System.err.println("Error al obtenir equip: " + e.getMessage());
            return null;
        }
    }

    public void updateEquip(Equip equip) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(equip);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al actualitzar equip: " + e.getMessage());
        }
    }

    public void deleteEquip(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Equip equip = session.find(Equip.class, id);
            if (equip != null) {
                session.remove(equip);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Error al eliminar equip: " + e.getMessage());
        }
    }

    public List<Equip> getAllEquips() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Equip", Equip.class).getResultList();
        } catch (HibernateException e) {
            System.err.println("Error al llistar equips: " + e.getMessage());
            return null;
        }
    }
}