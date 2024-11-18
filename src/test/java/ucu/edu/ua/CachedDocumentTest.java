package ucu.edu.ua;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CachedDocumentTest {

    @BeforeEach
    void setupDatabase() throws SQLException {
        try (Connection CONN = DriverManager
        .getConnection("jdbc:sqlite:db.sqlite")) {
            try (Statement STMT = CONN.createStatement()) {
                STMT.execute(
                    "CREATE TABLE files "
                    + "(path TEXT PRIMARY KEY, parsed_string TEXT);"
                );
            }
        }
    }

    @Test
    void testParseFromCache() throws SQLException {
        try (Connection CONN = DriverManager.getConnection(
            "jdbc:sqlite:db.sqlite"
            )) {
            try (PreparedStatement STMT = CONN.prepareStatement(
                "INSERT INTO files (path, parsed_string) VALUES (?, ?)"
                )) {
                STMT.setString(1, "test-path");
                STMT.setString(2, "Cached Result");
                STMT.executeUpdate();
            }
        }

        Document stubDocument = path -> "New Result";
        CachedDocument cachedDocument = new CachedDocument(stubDocument);

        String result = cachedDocument.parse("test-path");
        assertEquals("Cached Result", result);
    }

    @Test
    void testParseAndInsertIntoCache() throws SQLException {
        Document stubDocument = path -> "New Parsed Result";
        CachedDocument cachedDocument = new CachedDocument(stubDocument);

        String result = cachedDocument.parse("new-path");
        assertEquals("New Parsed Result", result);

        try (Connection CONN = DriverManager.getConnection(
            "jdbc:sqlite:db.sqlite"
            );
            PreparedStatement STMT = CONN.prepareStatement(
                "SELECT parsed_string FROM files WHERE path = ?"
            )) {
            STMT.setString(1, "new-path");
            ResultSet rs = STMT.executeQuery();
            assertTrue(rs.next());
            assertEquals("New Parsed Result", rs.getString("parsed_string"));
        }
    }
}
