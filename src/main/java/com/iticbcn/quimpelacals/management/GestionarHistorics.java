package com.iticbcn.quimpelacals.management;

import com.iticbcn.quimpelacals.dao.HistoricDAO;
import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Historic;
import com.iticbcn.quimpelacals.models.Tasca;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class GestionarHistorics {
    private final HistoricDAO historicDAO;
    private final BufferedReader br;

    public GestionarHistorics(HistoricDAO historicDAO, BufferedReader br) {
        this.historicDAO = historicDAO;
        this.br = br;
    }

    public void mostrarMenu() throws Exception {
        System.out.println("\n--- GESTIÓ D'HISTÒRICS ---");
        System.out.println("1. Crear registre històric");
        System.out.println("2. Llistar registres històrics");
        System.out.println("3. Buscar registre per ID");
        System.out.println("4. Actualitzar registre històric");
        System.out.println("5. Eliminar registre històric");
        System.out.print("Escull opció: ");

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1: crearHistoric(); break;
            case 2: llistarHistorics(); break;
            case 3: buscarPerId(); break;
            case 4: actualitzarHistoric(); break;
            case 5: eliminarHistoric(); break;
        }
    }

    private void crearHistoric() throws Exception {
        Historic historic = new Historic();

        System.out.print("Introdueix la data d'inici (YYYY-MM-DD): ");
        historic.setDataInici(java.sql.Date.valueOf(br.readLine()));

        System.out.print("Introdueix la data de finalització (opcional, YYYY-MM-DD o deixa en blanc): ");
        String dataFi = br.readLine();
        if (!dataFi.isEmpty()) {
            historic.setDataFi(java.sql.Date.valueOf(dataFi));
        }

        System.out.print("Comentaris (opcional): ");
        historic.setComentaris(br.readLine());

        System.out.print("ID de la tasca associada: ");
        Long tascaId = Long.parseLong(br.readLine());
        Tasca tasca = new Tasca(); // Això hauria de recuperar la tasca real de la BD si és necessari
        tasca.setId(tascaId);
        historic.setTasca(tasca);

        historicDAO.save(historic);
        System.out.println("Registre històric creat amb èxit!");
    }

    private void llistarHistorics() throws Exception {
        List<Historic> historics = historicDAO.getAll();
        if (historics != null && !historics.isEmpty()) {
            historics.forEach(h -> System.out.println(
                    "ID: " + h.getId() +
                            " | Inici: " + h.getDataInici() +
                            " | Fi: " + (h.getDataFi() != null ? h.getDataFi() : "Encara en curs") +
                            " | Tasca ID: " + h.getTasca().getId()
            ));
        } else {
            System.out.println("No hi ha registres històrics.");
        }
    }

    private void buscarPerId() throws Exception {
        System.out.print("Introdueix l'ID del registre històric: ");
        int id = Integer.parseInt(br.readLine());

        Historic historic = historicDAO.get(id);

        if (historic != null) {
            System.out.println("\n--- DADES DEL REGISTRE HISTÒRIC ---");
            System.out.println("ID: " + historic.getId());
            System.out.println("Data inici: " + historic.getDataInici());
            System.out.println("Data fi: " + (historic.getDataFi() != null ? historic.getDataFi() : "Encara en curs"));
            System.out.println("Comentaris: " + (historic.getComentaris() != null ? historic.getComentaris() : "Sense comentaris"));
            System.out.println("ID Tasca: " + historic.getTasca().getId());
        } else {
            System.out.println("No s'ha trobat cap registre històric amb ID " + id);
        }
    }

    private void actualitzarHistoric() throws Exception {
        System.out.print("Introdueix l'ID del registre històric a actualitzar: ");
        int id = Integer.parseInt(br.readLine());

        Historic historic = historicDAO.get(id);

        if (historic == null) {
            System.out.println("Registre històric no trobat!");
            return;
        }

        System.out.println("\nDeixa en blanc els camps que no vulguis modificar");

        System.out.print("Nova data d'inici (" + historic.getDataInici() + "): ");
        String novaDataInici = br.readLine();
        if (!novaDataInici.isEmpty()) {
            historic.setDataInici(java.sql.Date.valueOf(novaDataInici));
        }

        System.out.print("Nova data fi (" + (historic.getDataFi() != null ? historic.getDataFi() : "Encara en curs") + "): ");
        String novaDataFi = br.readLine();
        if (!novaDataFi.isEmpty()) {
            historic.setDataFi(java.sql.Date.valueOf(novaDataFi));
        }

        System.out.print("Nous comentaris (" + (historic.getComentaris() != null ? historic.getComentaris() : "Sense comentaris") + "): ");
        String nousComentaris = br.readLine();
        if (!nousComentaris.isEmpty()) {
            historic.setComentaris(nousComentaris);
        }

        historicDAO.update(historic);
        System.out.println("Registre històric actualitzat amb èxit!");
    }

    private void eliminarHistoric() throws Exception {
        System.out.print("Introdueix l'ID del registre històric a eliminar: ");
        int id = Integer.parseInt(br.readLine());

        Historic historic = historicDAO.get(id);

        if (historic != null) {
            System.out.println("Segur que vols eliminar aquest registre històric? (s/n)");
            String confirmacio = br.readLine();

            if (confirmacio.equalsIgnoreCase("s")) {
                historicDAO.delete(historic);
                System.out.println("Registre històric eliminat correctament!");
            } else {
                System.out.println("Operació cancel·lada.");
            }
        } else {
            System.out.println("No s'ha trobat cap registre històric amb aquest ID.");
        }
    }
}
