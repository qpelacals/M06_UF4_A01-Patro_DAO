package com.iticbcn.quimpelacals.management;

import com.iticbcn.quimpelacals.dao.EmpleatDAO;
import com.iticbcn.quimpelacals.models.Departament;
import com.iticbcn.quimpelacals.models.Empleat;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class GestionarEmpleats {
    private final EmpleatDAO empleatDAO;
    private final BufferedReader br;

    public GestionarEmpleats(EmpleatDAO empleatDAO, BufferedReader br) {
        this.empleatDAO = empleatDAO;
        this.br = br;
    }

    public void mostrarMenu() throws IOException {
        System.out.println("\n--- GESTIÓ D'EMPLEATS ---");
        System.out.println("1. Crear empleat");
        System.out.println("2. Llistar empleats");
        System.out.println("3. Buscar empleat per ID");
        System.out.println("4. Actualitzar empleat");
        System.out.println("5. Eliminar empleat");
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearEmpleat(); break;
            case 2: llistarEmpleats(); break;
            case 3: buscarPerId(); break;
            case 4: actualitzarEmpleat(); break;
            case 5: eliminarEmpleat(); break;
        }
    }

    private void crearEmpleat() throws IOException {
        Empleat empleat = new Empleat();
        System.out.print("DNI: ");
        empleat.setDni(br.readLine());

        System.out.print("Nom: ");
        empleat.setNom(br.readLine());

        System.out.print("Cognoms: ");
        empleat.setCognoms(br.readLine());

        System.out.print("Telèfon (opcional): ");
        empleat.setTel(br.readLine());

        System.out.print("Sou: ");
        empleat.setSou(Double.parseDouble(br.readLine()));

        System.out.print("Departament (SALES/MARKETING): ");
        empleat.setDepartament(Departament.valueOf(br.readLine().toUpperCase()));

        empleatDAO.saveEmpleat(empleat);
        System.out.println("Empleat creat amb èxit!");
    }

    private void llistarEmpleats() {
        List<Empleat> empleats = empleatDAO.getAllEmpleats();
        empleats.forEach(e -> System.out.println(
                "ID: " + e.getId() +
                        " | Nom: " + e.getNom() +
                        " " + e.getCognoms() +
                        " | DNI: " + e.getDni()
        ));
    }

    private void buscarPerId() throws IOException {
        System.out.print("Introdueix l'ID de l'empleat: ");
        Long id = Long.parseLong(br.readLine());

        Empleat empleat = empleatDAO.getEmpleatById(id);

        if(empleat != null) {
            System.out.println("\n--- DADES DE L'EMPLEAT ---");
            System.out.println("ID: " + empleat.getId());
            System.out.println("DNI: " + empleat.getDni());
            System.out.println("Nom complet: " + empleat.getNom() + " " + empleat.getCognoms());
            System.out.println("Telèfon: " + (empleat.getTel() != null ? empleat.getTel() : "No té"));
            System.out.println("Sou: " + empleat.getSou() + "€");
            System.out.println("Departament: " + empleat.getDepartament());
        } else {
            System.out.println("No s'ha trobat cap empleat amb ID " + id);
        }
    }

    private void actualitzarEmpleat() throws IOException {
        System.out.print("Introdueix l'ID de l'empleat a actualitzar: ");
        Long id = Long.parseLong(br.readLine());

        Empleat empleat = empleatDAO.getEmpleatById(id);

        if(empleat == null) {
            System.out.println("Empleat no trobat!");
            return;
        }

        System.out.println("\nDeixa en blanc els camps que no vulguis modificar");

        System.out.print("Nou DNI (" + empleat.getDni() + "): ");
        String nouDni = br.readLine();
        if(!nouDni.isEmpty()) empleat.setDni(nouDni);

        System.out.print("Nou nom (" + empleat.getNom() + "): ");
        String nouNom = br.readLine();
        if(!nouNom.isEmpty()) empleat.setNom(nouNom);

        System.out.print("Nous cognoms (" + empleat.getCognoms() + "): ");
        String nousCognoms = br.readLine();
        if(!nousCognoms.isEmpty()) empleat.setCognoms(nousCognoms);

        System.out.print("Nou telèfon (" + (empleat.getTel() != null ? empleat.getTel() : "") + "): ");
        String nouTel = br.readLine();
        if(!nouTel.isEmpty()) empleat.setTel(nouTel);

        System.out.print("Nou sou (" + empleat.getSou() + "): ");
        String nouSou = br.readLine();
        if(!nouSou.isEmpty()) empleat.setSou(Double.parseDouble(nouSou));

        System.out.print("Nou departament (" + empleat.getDepartament() + "): ");
        String nouDept = br.readLine();
        if(!nouDept.isEmpty()) {
            try {
                empleat.setDepartament(Departament.valueOf(nouDept.toUpperCase()));
            } catch(IllegalArgumentException e) {
                System.out.println("Departament no vàlid! Mantenint valor anterior.");
            }
        }

        empleatDAO.updateEmpleat(empleat);
        System.out.println("Empleat actualitzat amb èxit!");
    }

    private void eliminarEmpleat() throws IOException {
        System.out.print("Introdueix l'ID de l'empleat a eliminar: ");
        Long id = Long.parseLong(br.readLine());

        Empleat empleat = empleatDAO.getEmpleatById(id);

        if(empleat != null) {
            System.out.println("Segur que vols eliminar a " + empleat.getNom() + " " + empleat.getCognoms() + "? (s/n)");
            String confirmacio = br.readLine();

            if(confirmacio.equalsIgnoreCase("s")) {
                empleatDAO.deleteEmpleat(id);
                System.out.println("Empleat eliminat correctament!");
            } else {
                System.out.println("Operació cancel·lada");
            }
        } else {
            System.out.println("No s'ha trobat cap empleat amb aquest ID");
        }
    }
}