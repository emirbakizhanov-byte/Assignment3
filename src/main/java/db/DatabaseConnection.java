package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require";

    private static final String USER =
            "postgres.cyglbqrqwnuebwjjyuxz";

    private static final String PASSWORD =
            "my_password"; // из Supabase Database Settings

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to connect to Supabase DB", e);
            }
        }
    }
