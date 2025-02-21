package com.iticbcn.quimpelacals.management;

import com.iticbcn.quimpelacals.dao.EmpleatDAO;
import com.iticbcn.quimpelacals.dao.TascaDAO;
import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Tasca;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class GestionarTasques {
    private final TascaDAO tascaDAO;
    private final EmpleatDAO empleatDAO; // Afegir EmpleatDAO
    private final BufferedReader br;

    public GestionarTasques(TascaDAO tascaDAO, EmpleatDAO empleatDAO, BufferedReader br) {
        this.tascaDAO = tascaDAO;
        this.empleatDAO = empleatDAO; // Inicialitzar EmpleatDAO
        this.br = br;
    }

    public void mostrarMenu() throws Exception {
        System.out.println("\n--- GESTIÓ DE TASQUES ---");
        System.out.println("1. Crear tasca");
        System.out.println("2. Llistar tasques");
        System.out.println("3. Buscar tasca per ID");
        System.out.println("4. Actualitzar tasca");
        System.out.println("5. Eliminar tasca");
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearTasca(); break;
            case 2: llistarTasques(); break;
            case 3: buscarPerId(); break;
            case 4: actualitzarTasca(); break;
            case 5: eliminarTasca(); break;
        }
    }

    private void crearTasca() throws Exception {
        Tasca tasca = new Tasca();

        System.out.print("Nom de la tasca: ");
        tasca.setNom(br.readLine());

        System.out.print("Descripció (opcional): ");
        tasca.setDescripcio(br.readLine());

        System.out.print("Prioritat (ALTA/MITJANA/BAIXA): ");
        tasca.setPrioritat(br.readLine().toUpperCase());

        System.out.print("Data límit (YYYY-MM-DD): ");
        tasca.setDataLim(java.sql.Date.valueOf(br.readLine()));

        // Obtenir l'ID de l'empleat i assignar-lo a la tasca
        System.out.print("ID del empleat: ");
        int id = Integer.parseInt(br.readLine());
        Empleat empleat = empleatDAO.get(id);

        if (empleat != null) {
            tasca.setEmpleat(empleat); // Assignar l'objecte Empleat a la tasca
            tascaDAO.save(tasca);
            System.out.println("Tasca creada amb èxit!");
        } else {
            System.out.println("Error: No s'ha trobat cap empleat amb ID " + id);
        }
    }

    private void llistarTasques() throws Exception {
        List<Tasca> tasques = tascaDAO.getAll();
        if (tasques != null && !tasques.isEmpty()) {
            tasques.forEach(t -> System.out.println(
                    "ID: " + t.getId() +
                            " | Nom: " + t.getNom() +
                            " | Prioritat: " + t.getPrioritat() +
                            " | Data límit: " + t.getDataLim()
            ));
        } else {
            System.out.println("No hi ha tasques registrades.");
        }
    }

    private void buscarPerId() throws Exception {
        System.out.print("Introdueix l'ID de la tasca: ");
        int id = Integer.parseInt(br.readLine());

        Tasca tasca = tascaDAO.get(id);

        if (tasca != null) {
            System.out.println("\n--- DADES DE LA TASCA ---");
            System.out.println("ID: " + tasca.getId());
            System.out.println("Nom: " + tasca.getNom());
            System.out.println("Descripció: " + (tasca.getDescripcio() != null ? tasca.getDescripcio() : "Sense descripció"));
            System.out.println("Prioritat: " + tasca.getPrioritat());
            System.out.println("Data límit: " + tasca.getDataLim());
        } else {
            System.out.println("No s'ha trobat cap tasca amb ID " + id);
        }
    }

    private void actualitzarTasca() throws Exception {
        System.out.print("Introdueix l'ID de la tasca a actualitzar: ");
        int id = Integer.parseInt(br.readLine());

        Tasca tasca = tascaDAO.get(id);

        if (tasca == null) {
            System.out.println("Tasca no trobada!");
            return;
        }

        System.out.println("\nDeixa en blanc els camps que no vulguis modificar");

        System.out.print("Nou nom (" + tasca.getNom() + "): ");
        String nouNom = br.readLine();
        if (!nouNom.isEmpty()) tasca.setNom(nouNom);

        System.out.print("Nova descripció (" + (tasca.getDescripcio() != null ? tasca.getDescripcio() : "Sense descripció") + "): ");
        String novaDescripcio = br.readLine();
        if (!novaDescripcio.isEmpty()) tasca.setDescripcio(novaDescripcio);

        System.out.print("Nova prioritat (" + tasca.getPrioritat() + "): ");
        String novaPrioritat = br.readLine();
        if (!novaPrioritat.isEmpty()) tasca.setPrioritat(novaPrioritat.toUpperCase());

        System.out.print("Nova data límit (" + tasca.getDataLim() + "): ");
        String novaDataLim = br.readLine();
        if (!novaDataLim.isEmpty()) tasca.setDataLim(java.sql.Date.valueOf(novaDataLim));

        tascaDAO.update(tasca);
        System.out.println("Tasca actualitzada amb èxit!");
    }

    private void eliminarTasca() throws Exception {
        System.out.print("Introdueix l'ID de la tasca a eliminar: ");
        int id = Integer.parseInt(br.readLine());

        Tasca tasca = tascaDAO.get(id);

        if (tasca != null) {
            System.out.println("Segur que vols eliminar aquesta tasca? (s/n)");
            String confirmacio = br.readLine();

            if (confirmacio.equalsIgnoreCase("s")) {
                tascaDAO.delete(tasca);
                System.out.println("Tasca eliminada correctament!");
            } else {
                System.out.println("Operació cancel·lada.");
            }
        } else {
            System.out.println("No s'ha trobat cap tasca amb aquest ID.");
        }
    }
}
