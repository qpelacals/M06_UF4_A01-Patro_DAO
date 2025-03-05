package com.iticbcn.quimpelacals.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.iticbcn.quimpelacals.models.Empleat;
import com.iticbcn.quimpelacals.models.Departament;

public class EmpleatDAOimplBDOR {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public EmpleatDAOimplBDOR(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public void inserirEmpleat(Empleat empleat) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO empleats (dni, nom, cognoms, tel, sou, departament) VALUES (?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);

            try (PreparedStatement prep = conn.prepareStatement(query)) {
                prep.setString(1, empleat.getDni());
                prep.setString(2, empleat.getNom());
                prep.setString(3, empleat.getCognoms());
                prep.setString(4, empleat.getTel());
                prep.setDouble(5, empleat.getSou());
                prep.setString(6, empleat.getDepartament().name());

                prep.executeUpdate();
                conn.commit();
                System.out.println("Empleat inserit amb èxit");
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Error inserint empleat: " + ex.getMessage());
            }
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            System.err.println("Error de connexió: " + ex.getMessage());
        }
    }

    public List<Empleat> mostrarEmpleats() {
        List<Empleat> empleats = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT empleat_id, dni, nom, cognoms, tel, sou, departament FROM empleats";
            ResultSet rs = stmt.executeQuery(query);

            //obtenim numero de columnes i nom
            int colNum = getColumnNames(rs);

            //Si el nombre de columnes és >0 procedim a llegir i mostrar els registres
            if (colNum > 0) {
                recorrerRegistres(rs, colNum);
            }
        } catch (SQLException ex) {
            System.err.println("Error mostrant empleats: " + ex.getMessage());
        }
        return empleats;
    }

    public static int getColumnNames(ResultSet rs) throws SQLException {

        int numberOfColumns = 0;

        if (rs != null) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            numberOfColumns = rsMetaData.getColumnCount();

            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = rsMetaData.getColumnName(i);
                System.out.print(columnName + ", ");
            }
        }

        System.out.println();

        return numberOfColumns;

    }

    public void recorrerRegistres(ResultSet rs, int ColNum) throws SQLException {

        while (rs.next()) {
            for (int i = 0; i < ColNum; i++) {
                if (i + 1 == ColNum) {
                    System.out.println(rs.getString(i + 1));
                } else {

                    System.out.print(rs.getString(i + 1) + ", ");
                }
            }
        }
    }
}

class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        String[] cnnset = DemanarConnexio();

        EmpleatDAOimplBDOR eDAOimplBDOR= new EmpleatDAOimplBDOR (cnnset[0],cnnset[1],cnnset[2]);

        GestioOpcions(eDAOimplBDOR);
    }

    public static String[] DemanarConnexio() throws IOException {

        String[] connSettings = new String[3];

        System.err.print("A quina BD us voleu connectar? : ");
        String database = br.readLine();
        connSettings[0] = "jdbc:postgresql://localhost:54320/" + database;

        System.err.print("Usuari: ");
        connSettings[1] = br.readLine();
        System.err.print("Password: ");
        connSettings[2] = br.readLine();

        return connSettings;
    }

    public static void GestioOpcions(EmpleatDAOimplBDOR eDAOimplBDOR) throws IOException {

        String opcio;
        boolean validOpt = true;

        while (validOpt) {

            System.out.println("Què voleu fer?");
            System.out.println("A. Inserir nova persona");
            System.out.println("B. Mostrar totes les persones");

            opcio = br.readLine();

            if (opcio.equalsIgnoreCase("a")) {
                Empleat emp = DemanarDades();
                eDAOimplBDOR.inserirEmpleat(emp);
                validOpt = false;
            } else if (opcio.equalsIgnoreCase("b")) {
                eDAOimplBDOR.mostrarEmpleats();
                validOpt = false;

            } else {
                System.out.println("Opció no vàlida, introduir l'opció correcta");
            }
        }
    }

    public static Empleat DemanarDades() throws IOException {

        System.out.print("Nom: ");
        String nom = br.readLine();

        System.out.print("Cognoms: ");
        String cognoms = br.readLine();

        System.out.print("DNI: ");
        String dni = br.readLine();

        System.out.print("Telèfon (opcional, deixar en blanc si no en té): ");
        String tel = br.readLine();
        if (tel.isEmpty()) {
            tel = null;
        }

        System.out.print("Sou: ");
        double sou = Double.parseDouble(br.readLine());

        System.out.print("Departament (EXEMPLE: ADMINISTRACIO, VENDES, IT): ");
        Departament departament = Departament.valueOf(br.readLine().toUpperCase());

        Empleat empleat = new Empleat();
        empleat.setNom(nom);
        empleat.setCognoms(cognoms);
        empleat.setDni(dni);
        empleat.setTel(tel);
        empleat.setSou(sou);
        empleat.setDepartament(departament);

        System.out.println("Empleat creat amb èxit!");
        return empleat;
    }
}

