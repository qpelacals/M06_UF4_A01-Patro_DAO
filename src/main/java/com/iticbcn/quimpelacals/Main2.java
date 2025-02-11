package com.iticbcn.quimpelacals;

import com.iticbcn.quimpelacals.management.*;
import com.iticbcn.quimpelacals.dao.*;
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
        HistoricDAO historicDAO = new HistoricDAO(sessionFactory);

        // Configurar gestors
        GestionarEmpleats gestioEmpleats = new GestionarEmpleats(empleatDAO, br);
        GestionarEquips gestioEquips = new GestionarEquips(equipDAO, empleatDAO, br);
        GestionarTasques gestioTasques = new GestionarTasques(tascaDAO, br);
        GestionarHistorics gestioHistorics = new GestionarHistorics(historicDAO, br);

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
                    case 3: gestioTasques.mostrarMenu(); break;
                    case 4: gestioHistorics.mostrarMenu(); break;
                    case 5: sortir = true; break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error: " + e.getMessage());
            }
        }

        sessionFactory.close();
    }
}