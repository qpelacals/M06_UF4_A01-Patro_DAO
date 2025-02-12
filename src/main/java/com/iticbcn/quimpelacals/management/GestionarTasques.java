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

    public void mostrarMenu() throws IOException {
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

    private void crearTasca() throws IOException {
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
        Long empleatId = Long.parseLong(br.readLine());
        Empleat empleat = empleatDAO.getEmpleatById(empleatId);

        if (empleat != null) {
            tasca.setEmpleat(empleat); // Assignar l'objecte Empleat a la tasca
            tascaDAO.saveTasca(tasca);
            System.out.println("Tasca creada amb èxit!");
        } else {
            System.out.println("Error: No s'ha trobat cap empleat amb ID " + empleatId);
        }
    }

    private void llistarTasques() {
        List<Tasca> tasques = tascaDAO.getAllTasques();
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

    private void buscarPerId() throws IOException {
        System.out.print("Introdueix l'ID de la tasca: ");
        Long id = Long.parseLong(br.readLine());

        Tasca tasca = tascaDAO.getTascaById(id);

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

    private void actualitzarTasca() throws IOException {
        System.out.print("Introdueix l'ID de la tasca a actualitzar: ");
        Long id = Long.parseLong(br.readLine());

        Tasca tasca = tascaDAO.getTascaById(id);

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

        tascaDAO.updateTasca(tasca);
        System.out.println("Tasca actualitzada amb èxit!");
    }

    private void eliminarTasca() throws IOException {
        System.out.print("Introdueix l'ID de la tasca a eliminar: ");
        Long id = Long.parseLong(br.readLine());

        Tasca tasca = tascaDAO.getTascaById(id);

        if (tasca != null) {
            System.out.println("Segur que vols eliminar aquesta tasca? (s/n)");
            String confirmacio = br.readLine();

            if (confirmacio.equalsIgnoreCase("s")) {
                tascaDAO.deleteTasca(id);
                System.out.println("Tasca eliminada correctament!");
            } else {
                System.out.println("Operació cancel·lada.");
            }
        } else {
            System.out.println("No s'ha trobat cap tasca amb aquest ID.");
        }
    }
}
