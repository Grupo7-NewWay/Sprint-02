package school.sptech;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

                    public static void main(String[] args) throws IOException {
                            if (args.length < 3) {
                                    throw new RuntimeException(
                                            "Informe os três arquivos de Excel"
                                    );
                            }

                            String caminhoEventos = args[0];
                            String caminhoDemanda = args[1];
                            String caminhoChegadas = args[2];


                            S3Provider provider = new S3Provider();

                            S3Client s3Client =
                                    provider.getS3Client();

                            String bucketName = "bucket-new-way";


                            try {
                                    s3Client.createBucket(
                                            CreateBucketRequest.builder()
                                                    .bucket(bucketName)
                                                    .build()
                                    );

                            } catch (Exception e) {

                                    System.out.println(
                                            "Bucket já existe"
                                    );
                            }

                            Path filePathEventos =
                                    Paths.get(caminhoEventos);

                            s3Client.putObject(

                                    PutObjectRequest.builder()
                                            .bucket(bucketName)
                                            .key("eventos_2026.xlsx")
                                            .build(),

                                    RequestBody.fromFile(
                                            filePathEventos
                                    )
                            );

                            Path filePathDemanda =
                                    Paths.get(caminhoDemanda);

                            s3Client.putObject(

                                    PutObjectRequest.builder()
                                            .bucket(bucketName)
                                            .key("demanda_turistica_2021.xlsx")
                                            .build(),

                                    RequestBody.fromFile(
                                            filePathDemanda
                                    )
                            );


                            Path filePathChegadas =
                                    Paths.get(caminhoChegadas);

                            s3Client.putObject(

                                    PutObjectRequest.builder()
                                            .bucket(bucketName)
                                            .key("chegada_turistas.xlsx")
                                            .build(),

                                    RequestBody.fromFile(
                                            filePathChegadas
                                    )
                            );

                            System.out.println(
                                    "Upload finalizado com sucesso!"
                            );

                            LeitorExcel leitorExcel =
                                    new LeitorExcel();

                            try {
                                    List<Eventos> eventos = leitorExcel.extrairEventos();
                                    NotificadorSlack.arquivoLido(AmbienteConfig.EVENTOS, eventos.size(), 0);
                            } catch (Exception e) {
                                    NotificadorSlack.notificarErro(AmbienteConfig.EVENTOS, e.getMessage());
                            }

                            try {
                                    leitorExcel.extrairDemandaTuristica();
                                    NotificadorSlack.arquivoLido(AmbienteConfig.DEMANDA);
                            } catch (Exception e) {
                                    NotificadorSlack.notificarErro(AmbienteConfig.DEMANDA, e.getMessage());
                            }

                            try {
                                    leitorExcel.extrairChegadas();
                                    NotificadorSlack.arquivoLido(AmbienteConfig.CHEGADASTURISTAS);
                            } catch (Exception e) {
                                    NotificadorSlack.notificarErro(AmbienteConfig.CHEGADASTURISTAS, e.getMessage());
                            }

                            s3Client.close();

                            NotificadorSlack.processoFinalizado();

                            System.out.println(
                                    "Processo finalizado com sucesso!"
                            );
                    }
}