package co.com.crediya.reportes.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "adapter.sqs")
public record SQSSenderProperties(
        String region,
        String queueUrl,
        String endpoint,
        List<String> to){
}
