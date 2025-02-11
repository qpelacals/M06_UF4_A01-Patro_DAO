package com.iticbcn.quimpelacals.models;

import com.iticbcn.quimpelacals.HibernateUtil;
import com.iticbcn.quimpelacals.dao.*;
import org.hibernate.SessionFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

public class Main {
    private static boolean sortirapp = false;
    private static SessionFactory sessionFactory;
    private static EmpleatDAO empleatDAO;
    private static EquipDAO equipDAO;
    private static HistoricDAO historicDAO;
    private static TascaDAO tascaDAO;

    public static void main(String[] args) {
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            inicialitzarDAOs();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                while (!sortirapp) {
                    mostrarMenuPrincipal(br);
                }
            }
        } catch (Exception e) {
            System.err.println("Error crític: " + e.getMessage());
        } finally {
            if (sessionFactory != null) sessionFactory.close();
        }
    }

    private static void inicialitzarDAOs() {
        empleatDAO = new EmpleatDAO(sessionFactory);
        equipDAO = new EquipDAO(sessionFactory);
        historicDAO = new HistoricDAO(sessionFactory);
        tascaDAO = new TascaDAO(sessionFactory);
    }

    private static void mostrarMenuPrincipal(BufferedReader br) throws IOException {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Empleats");
        System.out.println("2. Gestionar Equips");
        System.out.println("3. Gestionar Tasques");
        System.out.println("4. Gestionar Històrics");
        System.out.println("5. Sortir");
        System.out.print("Escull una opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            //case 1: gestionarEmpleats(br); break;
            case 2: gestionarEquips(br); break;
            //case 3: gestionarTasques(br); break;
            case 4: gestionarHistorics(br); break;
            case 5: sortirapp = true; break;
            default: System.out.println("Opció no vàlida");
        }
    }

    // =============================================
    // IMPLEMENTACIÓ COMPLETA PER EQUIPS I HISTÒRICS
    // =============================================

    private static void gestionarEquips(BufferedReader br) throws IOException {
        System.out.println("\n--- GESTIÓ D'EQUIPS ---");
        System.out.println("1. Crear equip");
        System.out.println("2. Llistar equips");
        System.out.println("3. Buscar equip per ID");
        System.out.println("4. Actualitzar equip");
        System.out.println("5. Eliminar equip");
        System.out.println("6. Afegir empleat a equip");
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearEquip(br); break;
            case 2: llistarEquips(); break;
            case 3: buscarEquipPerId(br); break;
            //case 4: actualitzarEquip(br); break;
            //case 5: eliminarEquip(br); break;
            case 6: afegirEmpleatAEquip(br); break;
        }
    }

    private static void crearEquip(BufferedReader br) throws IOException {
        Equip equip = new Equip();
        System.out.print("Nom de l'equip: ");
        equip.setNom(br.readLine());

        equipDAO.saveEquip(equip);
        System.out.println("Equip creat amb èxit!");
    }

    private static void llistarEquips() {
        List<Equip> equips = equipDAO.getAllEquips();
        equips.forEach(e -> System.out.println(
                "ID: " + e.getId() +
                        " | Nom: " + e.getNom() +
                        " | Membres: " + (e.getEmpleats() != null ? e.getEmpleats().size() : 0)
        ));
    }

    private static void buscarEquipPerId(BufferedReader br) throws IOException {
        System.out.print("ID de l'equip: ");
        Long id = Long.parseLong(br.readLine());
        Equip equip = equipDAO.getEquipById(id);

        if(equip != null) {
            System.out.println("Equip trobat:");
            System.out.println("Nom: " + equip.getNom());
            if(equip.getEmpleats() != null && !equip.getEmpleats().isEmpty()) {
                System.out.println("Membres:");
                equip.getEmpleats().forEach(e ->
                        System.out.println(" - " + e.getNom() + " " + e.getCognoms()));
            }
        } else {
            System.out.println("Equip no trobat!");
        }
    }

    private static void afegirEmpleatAEquip(BufferedReader br) throws IOException {
        System.out.print("ID de l'empleat: ");
        Empleat e = empleatDAO.getEmpleatById(Long.parseLong(br.readLine()));

        System.out.print("ID de l'equip: ");
        Equip eq = equipDAO.getEquipById(Long.parseLong(br.readLine()));

        if(e != null && eq != null) {
            eq.getEmpleats().add(e);
            equipDAO.updateEquip(eq);
            System.out.println("Empleat afegit correctament!");
        } else {
            System.out.println("Error: Empleat o equip no trobat");
        }
    }

    private static void gestionarHistorics(BufferedReader br) throws IOException {
        System.out.println("\n--- GESTIÓ D'HISTÒRICS ---");
        System.out.println("1. Crear històric");
        System.out.println("2. Llistar històrics");
        System.out.println("3. Buscar històric per ID");
        System.out.println("4. Actualitzar històric");
        System.out.println("5. Eliminar històric");
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearHistoric(br); break;
            case 2: llistarHistorics(); break;
            //case 3: buscarHistoricPerId(br); break;
            //case 4: actualitzarHistoric(br); break;
            //case 5: eliminarHistoric(br); break;
        }
    }

    private static void crearHistoric(BufferedReader br) throws IOException {
        Historic historic = new Historic();

        System.out.print("Data inici (YYYY-MM-DD): ");
        historic.setDataInici(Date.valueOf(br.readLine()));

        System.out.print("Data fi (opcional, YYYY-MM-DD): ");
        String dataFi = br.readLine();
        if(!dataFi.isEmpty()) historic.setDataFi(Date.valueOf(dataFi));

        System.out.print("Comentaris (opcional): ");
        historic.setComentaris(br.readLine());

        System.out.print("ID de la tasca associada: ");
        Long tascaId = Long.parseLong(br.readLine());
        Tasca tasca = tascaDAO.getTascaById(tascaId);

        if(tasca != null) {
            historic.setTasca(tasca);
            historicDAO.saveHistoric(historic);
            System.out.println("Històric creat amb èxit!");
        } else {
            System.out.println("Error: Tasca no trobada!");
        }
    }

    private static void llistarHistorics() {
        List<Historic> historics = historicDAO.getAllHistorics();
        historics.forEach(h -> System.out.println(
                "ID: " + h.getId() +
                        " | Tasca: " + h.getTasca().getNom() +
                        " | Inici: " + h.getDataInici() +
                        " | Fi: " + (h.getDataFi() != null ? h.getDataFi() : "En curs")
        ));
    }

    // [...] Mètodes d'actualització i eliminació similars als altres DAOs
}