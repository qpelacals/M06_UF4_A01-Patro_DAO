package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Historic;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class HistoricDAO extends GenDAOImpl<Historic> {

    private SessionFactory sessionFactory;

    public HistoricDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Historic.class);
    }

}