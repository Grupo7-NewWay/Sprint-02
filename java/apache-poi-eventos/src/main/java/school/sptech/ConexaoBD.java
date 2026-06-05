package school.sptech;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/newway",
                    "adminnewway",
                    "urubu100"
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco", e);
        }


    }
}
