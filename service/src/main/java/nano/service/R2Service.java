package nano.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class R2Service {

    private final AmazonS3 s3Client;

    public R2Service() {
        this.s3Client = this.createClient();
    }

    public AmazonS3 createClient() {
        try {
            var accessKey = "";
            var secretKey = "";
            var credentials = new BasicAWSCredentials(accessKey, secretKey);
            var credentialsProvider = new AWSStaticCredentialsProvider(credentials);
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(credentialsProvider)
                    .build();
        } catch (Exception ex) {
            return null;
        }
    }

    public void putObject() {
        var request = new PutObjectRequest("nano", "", "");
        this.s3Client.putObject(request);
    }
}
