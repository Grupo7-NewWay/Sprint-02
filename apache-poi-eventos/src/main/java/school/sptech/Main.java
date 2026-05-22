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

            if (args.length == 0) {
                throw new RuntimeException("Informe o caminho do Excel");
            }

            String caminho1= args[0];
            String caminho2= args[0];

            LeitorExcel leitorExcel = new LeitorExcel();
            List<Eventos> eventosExtraidos = leitorExcel.extrairEventos(caminho1);
            List<Chegadas> chegadasExtraidos = leitorExcel.extrairChegadas(caminho2);
            List<Fonte> fonteExtraidos = leitorExcel.extrairFonte(caminho2);
            List<Gasto> gastoExtraidos = leitorExcel.extrairGasto(caminho2);
            List<GrupoTuristico> grupoTuristicoTuristicoExtraidos = leitorExcel.extrairGrupoTuristico(caminho2);
            List<GrupoIdade> grupoIdadeExtraidos = leitorExcel.extrairGrupoIdade(caminho2);
            List<Hospedagem> hospedagemExtraidos = leitorExcel.extrairHospedagem(caminho2);
            List<MotivoLazer> lazerExtraidos = leitorExcel.extrairLazer(caminho2);
            List<Localizacao> localizacaoExtraidos = leitorExcel.extrairLocalizacao(caminho2);
            List<Motivo> motivoExtraidos = leitorExcel.extrairMotivo(caminho2);
            List<Pacotes> pacotesExtraidos = leitorExcel.extrairPacotes(caminho2);
            List<Permanencia> permanenciaExtraidos = leitorExcel.extrairPermanencia(caminho2);
            List<ServicoAgencia> servicoAgenciaExtraidos = leitorExcel.extrairServicoAgencia(caminho2);

            AwsCredentialsProvider credentials = DefaultCredentialsProvider.create();

            S3Provider provider = new S3Provider();
            S3Client s3Client = provider.getS3Client();

            String bucketName = "bucket-new-way";

            try {
                s3Client.createBucket(CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build());
            } catch (Exception e) {
                System.out.println("Bucket já existe");
            }

            Path filePath1 = Paths.get(caminho1);
            Path filePath2 = Paths.get(caminho2);

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key("eventos_2026.xlsx")
                            .build(),
                    RequestBody.fromFile(filePath1)
            );

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key("demanda_turistica_2021.xlsx")
                            .build(),
                    RequestBody.fromFile(filePath2)
            );

            System.out.println("Upload finalizado");

            s3Client.close();

        /*String caminho = ".////eventos_2026.xlsx";

        LeitorExcel leitorExcel = new LeitorExcel();
        List<Eventos> eventosExtraidos = leitorExcel.extrairEventos(caminho);

        AwsCredentialsProvider credentials = DefaultCredentialsProvider.create();

        S3Provider provider = new S3Provider(credentials);
        S3Client s3Client = provider.getS3Client();

        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket("bucket-new-way")
                .build();

        try {
            s3Client.createBucket(createBucketRequest);
        } catch (BucketAlreadyOwnedByYouException e) {
            System.out.println("Bucket já existe. Continuando Execução");
        }

        List<Bucket> buckets = s3Client.listBuckets().buckets();
        for (Bucket bucket : buckets) {
            System.out.println("Bucket: " + bucket.name());
        }

        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket("bucket-new-way")
                .build();

        List<S3Object> objects = s3Client.listObjects(listObjects).contents();
        for (S3Object object : objects) {
            System.out.println("Objeto: " + object.key());
        }

        Path filePath = Paths.get(System.getProperty("user.dir"), "eventos_2026.xlsx");

        if (!Files.exists(filePath)) {
            throw new RuntimeException("Arquivo não encontrado: " + filePath);
        }

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket("bucket-new-way")
                        .key("eventos_2026.xlsx")
                        .build(),
                RequestBody.fromFile(filePath)
        );

        List<S3Object> objects2 = s3Client.listObjects(listObjects).contents();
        for (S3Object object : objects2) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("bucket-new-way")
                    .key(object.key())
                    .build();

            try (InputStream objectContent = s3Client.getObject(
                    getObjectRequest,
                    ResponseTransformer.toInputStream())) {

                Files.copy(objectContent, new File(object.key()).toPath());

            } catch (java.nio.file.FileAlreadyExistsException e) {
                System.out.println("Arquivo já existe: " + object.key());

            } catch (IOException e) {
                System.out.println("Erro ao baixar arquivo: " + object.key());
                e.printStackTrace();
            }
        }

         */

        /*
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket("bucket-new-way")
                .key("eventos_2026.xlsx")
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        System.out.println("Objeto deletado: " + "eventos_2026");

         */

    }
}