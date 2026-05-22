package school.sptech;

public class AmbienteConfig {

        public static Integer getFkChegadaLocalizacao() {
            return Integer.parseInt(System.getenv("FK_CHEGADA_LOCALIZACAO"));
        }

        public static Integer getFkLazerMotivo() {
            return Integer.parseInt(System.getenv("FK_LAZER_MOTIVO"));
        }

        public static Integer getFkPacotePerfil() {
            return Integer.parseInt(System.getenv("FK_PACOTE_PERFIL"));
        }

        public static Integer getFkPacoteLocalizacao() {
            return Integer.parseInt(System.getenv("FK_PACOTE_LOCALIZACAO"));
        }

        public static Integer getFkPacoteEvento() {
            return Integer.parseInt(System.getenv("FK_PACOTE_EVENTO"));
        }
}
