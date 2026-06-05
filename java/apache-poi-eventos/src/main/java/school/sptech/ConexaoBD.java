package school.sptech;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
    public static Connection conectar() {
        try {
            String host = System.getenv("DB_HOST");
            String port = System.getenv("DB_PORT");
            String db   = System.getenv("DB_NAME");
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASSWORD");

            return DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + db,
                    user,
                    pass
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco", e);
        }
    }
}