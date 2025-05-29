package Database;

import java.io.FileWriter;
import java.io.IOException;

public class AuditService {
    private static AuditService instance;
    private final String fileName = "audit.csv";

    private AuditService() {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write("actiune, timestamp\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String action) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            String timestamp = java.time.LocalDateTime.now().toString();
            fw.write(action + " - " + timestamp + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
