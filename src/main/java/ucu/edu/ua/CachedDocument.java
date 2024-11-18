package ucu.edu.ua;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// sqlite - embeded || android

public class CachedDocument extends DocumentDecorator {
    public CachedDocument(Document document) {
        super(document);
    }

    public String parse(String path) {
        String result = null;
        try (Connection CONN = DriverManager.getConnection(
            "jdbc:sqlite:db.sqlite"
            );

            PreparedStatement STMT = CONN.prepareStatement(
                "SELECT parsed_string FROM files WHERE path = ?"
            )) {
            
            STMT.setString(1, path);
            ResultSet rs = STMT.executeQuery();
            
            if (rs.next()) {
                System.out.println("Extraced from cache");
                result = rs.getString("parsed_string");
            } else {
                result = super.parse(path);

                try (PreparedStatement INSERT_STMT = CONN.prepareStatement(
                    "INSERT INTO files (path, parsed_string) VALUES (?, ?)"
                    )) {
                    INSERT_STMT.setString(1, path);
                    INSERT_STMT.setString(2, result);
                    INSERT_STMT.executeUpdate();
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}


