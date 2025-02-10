package com.iticbcn.quimpelacals.models;

import com.iticbcn.quimpelacals.management.*;
import com.iticbcn.quimpelacals.dao.*;
import com.iticbcn.quimpelacals.models.HibernateUtil;
import org.hibernate.SessionFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main2 {
    private static boolean sortir = false;

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Inicialitzar DAOs
        EmpleatDAO empleatDAO = new EmpleatDAO(sessionFactory);
        EquipDAO equipDAO = new EquipDAO(sessionFactory);
        TascaDAO tascaDAO = new TascaDAO(sessionFactory);

        // Configurar gestors
        GestionarEmpleats gestioEmpleats = new GestionarEmpleats(empleatDAO, br);
        GestionarEquips gestioEquips = new GestionarEquips(equipDAO, empleatDAO, br);

        while (!sortir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestionar Empleats");
            System.out.println("2. Gestionar Equips");
            System.out.println("3. Gestionar Tasques");
            System.out.println("4. Gestionar Històrics");
            System.out.println("5. Sortir");
            System.out.print("Escull una opció: ");

            try {
                int opcio = Integer.parseInt(br.readLine());

                switch (opcio) {
                    case 1: gestioEmpleats.mostrarMenu(); break;
                    case 2: gestioEquips.mostrarMenu(); break;
                    case 3: sortir = true; break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        sessionFactory.close();
    }
}