package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Tasca;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class TascaDAO extends GenDAOImpl<Tasca> {

    private SessionFactory sessionFactory;

    public TascaDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Tasca.class);
    }

}