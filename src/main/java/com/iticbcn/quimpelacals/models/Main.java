package com.iticbcn.quimpelacals.models;

import com.iticbcn.quimpelacals.dao.EmpleatDAO;
import com.iticbcn.quimpelacals.dao.TascaDAO;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sesion = HibernateUtil.getSessionFactory();

        Session session = sesion.openSession();

        session.beginTransaction();

        System.out.println("Hola desde Hibernate");

        session.getTransaction().commit();

        session.close();

    }
}