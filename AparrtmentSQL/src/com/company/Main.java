package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/apartment?serverTimezone=Europe/Kiev";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "features1996";
    static Connection conn;

    public Main() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            try {
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                initDB();

                while (true) {
                    System.out.println("1: add apartment");
                    System.out.println("2: choose apartment");
                    System.out.println("3: view apartments");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addApartment(sc);
                            break;
                        case "2":
                            chooseApartment(sc);
                            break;
                        case "3":
                            viewApartment();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void initDB() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS Apartment ");
            st.execute("CREATE TABLE Apartment (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "city VARCHAR(32) DEFAULT NULL, areaApartment VARCHAR(32) DEFAULT NULL, " +
                    "adressApartment VARCHAR(100) DEFAULT NULL, " +
                    "square VARCHAR(12) NOT NULL, num VARCHAR(12) NOT NULL, " +
                    "price VARCHAR(12) NOT NULL)");
        }
    }

    private static void addApartment(Scanner sc) throws SQLException {
        System.out.print("Enter city: ");
        String city = sc.nextLine();
        System.out.print("Enter the area of the apartment: ");
        String areaApartment = sc.nextLine();
        System.out.print("Enter the adress of the apartment: ");
        String adressApartment = sc.nextLine();
        System.out.print("Enter the apartment square: ");
        String squareApartment = sc.nextLine();
        int square = Integer.parseInt(squareApartment);
        System.out.print("Enter the number of rooms: ");
        String numberRooms = sc.nextLine();
        int numberOfRooms = Integer.parseInt(numberRooms);
        System.out.print("Enter the price of the apartment: ");
        String priceApartment = sc.nextLine();
        int price = Integer.parseInt(priceApartment);

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Apartment (city, areaApartment, adressApartment, square, numberOfRooms, price) " +
                        "VALUES(?, ?, ?, ?, ?, ?)");
        try {
            ps.setString(1, city);
            ps.setString(2, areaApartment);
            ps.setString(3, adressApartment);
            ps.setInt(4, square);
            ps.setInt(5, numberOfRooms);
            ps.setInt(6, price);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    private static void chooseApartment(Scanner sc) throws SQLException {
        System.out.print("Enter number of rooms: ");
        String numbApartment = sc.nextLine();
        int numApart = Integer.parseInt(numbApartment);
        System.out.println("Enter max price: ");
        String maximumPrice = sc.nextLine();
        int maxPrice = Integer.parseInt(maximumPrice);
        System.out.println("Enter lower price: ");
        String lowerPrice = sc.nextLine();
        int lowPrice = Integer.parseInt(lowerPrice);


        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM Apartment WHERE numberOfRooms = ? AND price>? AND price<?");
        try {
            ps.setInt(1, numApart);
            ps.setInt(2, lowPrice);
            ps.setInt(3, maxPrice);

            ResultSet rs = ps.executeQuery();

            try {

                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    private static void viewApartment() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartment");
        try {
            // table of data representing a database result set,
            ResultSet rs = ps.executeQuery();

            try {
                // can be used to get information about the types and properties of the columns in a ResultSet object
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close(); // rs can't be null according to the docs
            }
        } finally {
            ps.close();
        }
    }
}

