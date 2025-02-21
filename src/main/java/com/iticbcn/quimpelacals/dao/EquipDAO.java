package com.iticbcn.quimpelacals.dao;

import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Equip;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class EquipDAO extends GenDAOImpl<Equip> {

    public EquipDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Equip.class);
    }

}