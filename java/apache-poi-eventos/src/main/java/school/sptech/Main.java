package school.sptech;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

        public static void main(String[] args) throws IOException {

                String bucketName = System.getenv("S3_BUCKET");
                String localPath = "/app/";

                S3Provider provider = new S3Provider();
                S3Client s3Client = provider.getS3Client();

                // Baixa os 3 arquivos do S3
                s3Client.getObject(
                        GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key("eventos_2026.xlsx")
                                .build(),
                        Paths.get(localPath + "eventos_2026.xlsx")
                );

                s3Client.getObject(
                        GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key("demanda_turistica_2021.xlsx")
                                .build(),
                        Paths.get(localPath + "demanda_turistica_2021.xlsx")
                );

                s3Client.getObject(
                        GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key("chegada_turistas.xlsx")
                                .build(),
                        Paths.get(localPath + "chegada_turistas.xlsx")
                );

                System.out.println("Download finalizado!");

                LeitorExcel leitorExcel = new LeitorExcel();
                leitorExcel.extrairEventos();
                leitorExcel.extrairDemandaTuristica();
                leitorExcel.extrairChegadas();

                s3Client.close();

                System.out.println("Processo finalizado com sucesso!");
        }
}