package ucu.edu.ua;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class CachedDocumentTest {

    @BeforeEach
    void setupDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite")) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE files (path TEXT PRIMARY KEY, parsed_string TEXT);");
            }
        }
    }

    @Test
    void testParseFromCache() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite")) {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO files (path, parsed_string) VALUES (?, ?)")) {
                stmt.setString(1, "test-path");
                stmt.setString(2, "Cached Result");
                stmt.executeUpdate();
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

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
             PreparedStatement stmt = conn.prepareStatement("SELECT parsed_string FROM files WHERE path = ?")) {
            stmt.setString(1, "new-path");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals("New Parsed Result", rs.getString("parsed_string"));
        }
    }
}
