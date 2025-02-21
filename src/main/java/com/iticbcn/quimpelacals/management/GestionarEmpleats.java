package com.iticbcn.quimpelacals.management;

import com.iticbcn.quimpelacals.dao.EmpleatDAO;
import com.iticbcn.quimpelacals.models.Departament;
import com.iticbcn.quimpelacals.models.Empleat;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GestionarEmpleats {
    private final EmpleatDAO empleatDAO;
    private final BufferedReader br;

    public GestionarEmpleats(EmpleatDAO empleatDAO, BufferedReader br) {
        this.empleatDAO = empleatDAO;
        this.br = br;
    }

    public void mostrarMenu() throws Exception {
        System.out.println("\n--- GESTIÓ D'EMPLEATS ---");
        System.out.println("1. Crear empleat");
        System.out.println("2. Llistar empleats");
        System.out.println("3. Buscar empleat per ID");
        System.out.println("4. Actualitzar empleat");
        System.out.println("5. Eliminar empleat");
        System.out.println("6. Mostrar nombre de tasques per empleat"); // Nova opció
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearEmpleat(); break;
            case 2: llistarEmpleats(); break;
            case 3: buscarPerId(); break;
            case 4: actualitzarEmpleat(); break;
            case 5: eliminarEmpleat(); break;
            case 6: mostrarTasquesPerEmpleat(); break; // Nova opció
        }
    }

    private void crearEmpleat() throws Exception {
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

        empleatDAO.save(empleat);
        System.out.println("Empleat creat amb èxit!");
    }

    private void llistarEmpleats() throws Exception {
        List<Empleat> empleats = empleatDAO.getAll();
        empleats.forEach(e -> System.out.println(e.toString()));
    }

    private void buscarPerId() throws Exception {
        System.out.print("Introdueix l'ID de l'empleat: ");
        int id = Integer.parseInt(br.readLine());

        Empleat empleat = empleatDAO.get(id);

        if(empleat != null) {
            System.out.println("\n--- DADES DE L'EMPLEAT ---");
            System.out.println(empleat.toString());
        } else {
            System.out.println("No s'ha trobat cap empleat amb ID " + id);
        }
    }

    private void actualitzarEmpleat() throws Exception {
        System.out.print("Introdueix l'ID de l'empleat a actualitzar: ");
        int id = Integer.parseInt(br.readLine());

        Empleat empleat = empleatDAO.get(id);

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

        empleatDAO.update(empleat);
        System.out.println("Empleat actualitzat amb èxit!");
    }

    private void eliminarEmpleat() throws Exception {
        System.out.print("Introdueix l'ID de l'empleat a eliminar: ");
        int id = Integer.parseInt(br.readLine());

        Empleat empleat = empleatDAO.get(id);

        if(empleat != null) {
            System.out.println("Segur que vols eliminar a " + empleat.getNom() + " " + empleat.getCognoms() + "? (s/n)");
            String confirmacio = br.readLine();

            if(confirmacio.equalsIgnoreCase("s")) {
                empleatDAO.delete(empleat);
                System.out.println("Empleat eliminat correctament!");
            } else {
                System.out.println("Operació cancel·lada");
            }
        } else {
            System.out.println("No s'ha trobat cap empleat amb aquest ID");
        }
    }

    private void mostrarTasquesPerEmpleat() {
        Map<String, Long> tasquesPerEmpleat = empleatDAO.countTasksPerEmployee();

        if (tasquesPerEmpleat.isEmpty()) {
            System.out.println("No hi ha dades disponibles.");
            return;
        }

        System.out.println("\n--- NOMBRE DE TASQUES PER EMPLEAT ---");
        System.out.println("+---------------------+-------------------+");
        System.out.println("| Empleat             | Nombre de tasques |");
        System.out.println("+---------------------+-------------------+");
        for (Map.Entry<String, Long> entry : tasquesPerEmpleat.entrySet()) {
            System.out.printf("| %-20s| %-18d|\n", entry.getKey(), entry.getValue());
        }
        System.out.println("+---------------------+-------------------+");
    }
}