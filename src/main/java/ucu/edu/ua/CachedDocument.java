package ucu.edu.ua;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// sqlite - embeded || android

public class CachedDocument extends DocumentDecorator{
    public CachedDocument (Document document) {
        super(document);
    }

    public String parse(String path) {
        String result = null;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            PreparedStatement stmt = conn.prepareStatement("SELECT parsed_string FROM files WHERE path = ?")) {
            
            stmt.setString(1, path);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("Extraced from cache");
                result = rs.getString("parsed_string");
            } else {
                result = super.parse(path);

                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO files (path, parsed_string) VALUES (?, ?)")) {
                    insertStmt.setString(1, path);
                    insertStmt.setString(2, result);
                    insertStmt.executeUpdate();
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}


