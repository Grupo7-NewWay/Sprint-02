package school.sptech;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/newway",
                    "root",
                    "Th@18012903Lari#lari"
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco", e);
        }


    }
}
