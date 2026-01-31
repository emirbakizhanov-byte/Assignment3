package db;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DatabaseConnectionManager {

    private static final String URL =
            "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require";


    private static final String USER =
            "postgres.cyglbqrqwnuebwjjyuxz";

    private static final String PASSWORD =
            "EmirSupabase2026!";

    private DatabaseConnectionManager() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed", e);
        }
    }
}
