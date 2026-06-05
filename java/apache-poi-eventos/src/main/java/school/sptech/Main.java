package school.sptech;

import java.io.IOException;

public class Main {

        public static void main(String[] args) throws IOException {

                LeitorExcel leitorExcel = new LeitorExcel();
                leitorExcel.extrairEventos();
                leitorExcel.extrairDemandaTuristica();
                leitorExcel.extrairChegadas();

                System.out.println("Processo finalizado com sucesso!");
        }
}