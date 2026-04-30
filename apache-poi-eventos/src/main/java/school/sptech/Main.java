package school.sptech;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws IOException {
        String caminho = ".////eventos_2026.xlsx";

        LeitorExcel leitorExcel = new LeitorExcel();
        List<Eventos> eventosExtraidos = leitorExcel.extrairEventos(caminho);


        //
        AwsCredentialsProvider credentials = DefaultCredentialsProvider.create();

        S3Provider provider = new S3Provider(credentials);
        S3Client s3Client = provider.getS3Client();

        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket("bucket-new-way")
                .build();

        s3Client.createBucket(createBucketRequest);

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

        File file = new File("C:\\Users\\SPTech\\Aula-Linguagem-Programa-o\\apache-poi-eventos\\eventos_2026.xlsx");

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("bucket-new-way")
                .key(file.getName())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

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