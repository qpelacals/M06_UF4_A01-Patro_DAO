package com.iticbcn.quimpelacals;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

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