package nano.service;

import static nano.service.MetaDataService.Name.*;

import io.minio.*;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class R2Service {

  private static final String BUCKET_NAME = "nano";
  private final MinioClient r2Client;

  public R2Service(MetaDataService metaDataService) {
    this.r2Client = this.createClient(metaDataService);
  }

  public MinioClient createClient(MetaDataService metaDataService) {
    var metaData = metaDataService.getMetaData();
    var accessKey = metaData.get(R2_ACCESS_KEY);
    var secretKey = metaData.get(R2_SECRET_KEY);
    var endpoint = metaData.get(R2_ENDPOINT);
    return MinioClient.builder().region("auto").credentials(accessKey, secretKey).endpoint(endpoint).build();
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
    var args = GetObjectArgs.builder().bucket(BUCKET_NAME).object(objectKey).build();
    return this.r2Client.getObject(args);
  }

  @SneakyThrows
  public void removeObject(String objectKey) {
    var args = RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectKey).build();
    this.r2Client.removeObject(args);
  }
}
