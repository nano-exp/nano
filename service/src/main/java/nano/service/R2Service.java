package nano.service;

import io.minio.*;
import lombok.SneakyThrows;
import nano.common.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class R2Service {

    private static final String BUCKET_NAME = "nano";
    private final MinioClient r2Client;

    public R2Service() {
        this.r2Client = this.createClient();
    }

    public MinioClient createClient() {
        try {
            return MinioClient.builder()
                    .region("auto")
                    .credentials(Env.R2_ACCESS_KEY, Env.R2_SECRET_KEY)
                    .endpoint(Env.R2_ENDPOINT)
                    .build();
        } catch (Exception ex) {
            return null;
        }
    }

    @SneakyThrows
    public ObjectWriteResponse putObject(@NotNull Resource resource, @NotNull String objectKey, String contentType) {
        var args = PutObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(objectKey)
                .contentType(contentType)
                .stream(resource.getInputStream(), resource.contentLength(), -1)
                .build();
        return this.r2Client.putObject(args);
    }

    @SneakyThrows
    public GetObjectResponse getObject(String objectKey) {
        var args = GetObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(objectKey)
                .build();
        return this.r2Client.getObject(args);
    }

    @SneakyThrows
    public void removeObject(String objectKey) {
        var args = RemoveObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(objectKey)
                .build();
        this.r2Client.removeObject(args);
    }

}
