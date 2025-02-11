package com.iticbcn.quimpelacals.management;

import com.iticbcn.quimpelacals.dao.EmpleatDAO;
import com.iticbcn.quimpelacals.dao.EquipDAO;
import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Equip;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class GestionarEquips {
    private final EquipDAO equipDAO;
    private final EmpleatDAO empleatDAO;
    private final BufferedReader br;

    public GestionarEquips(EquipDAO equipDAO, EmpleatDAO empleatDAO, BufferedReader br) {
        this.equipDAO = equipDAO;
        this.empleatDAO = empleatDAO;
        this.br = br;
    }

    public void mostrarMenu() throws IOException {
        System.out.println("\n--- GESTIÓ D'EQUIPS ---");
        System.out.println("1. Crear equip");
        System.out.println("2. Llistar equips");
        System.out.println("3. Afegir empleat a equip");
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearEquip(); break;
            case 2: llistarEquips(); break;
            case 3: afegirEmpleatAEquip(); break;
        }
    }

    private void crearEquip() throws IOException {
        Equip equip = new Equip();
        System.out.print("Nom de l'equip: ");
        equip.setNom(br.readLine());
        equipDAO.saveEquip(equip);
        System.out.println("Equip creat amb èxit!");
    }

    private void llistarEquips() {
        List<Equip> equips = equipDAO.getAllEquips();

        if(equips.isEmpty()) {
            System.out.println("\nNo hi ha equips registrats");
            return;
        }

        System.out.println("\n--- LLISTAT D'EQUIPS ---");
        for(Equip equip : equips) {
            System.out.println("\nID: " + equip.getId());
            System.out.println("Nom: " + equip.getNom());
            System.out.println("Nombre de membres: " + (equip.getEmpleats() != null ? equip.getEmpleats().size() : 0));

            if(equip.getEmpleats() != null && !equip.getEmpleats().isEmpty()) {
                System.out.println("Membres:");
                for(Empleat membre : equip.getEmpleats()) {
                    System.out.println("  - " + membre.getNom() + " " + membre.getCognoms() +
                            " (ID: " + membre.getEmpleat_id() + ")");
                }
            }
        }
    }

    private void afegirEmpleatAEquip() throws IOException {
        System.out.print("ID de l'empleat: ");
        Empleat empleat = empleatDAO.getEmpleatById(Long.parseLong(br.readLine()));

        System.out.print("ID de l'equip: ");
        Equip equip = equipDAO.getEquipById(Long.parseLong(br.readLine()));

        if(empleat != null && equip != null) {
            equip.getEmpleats().add(empleat);
            equipDAO.updateEquip(equip);
            System.out.println("Empleat afegit correctament!");
        }
    }
}