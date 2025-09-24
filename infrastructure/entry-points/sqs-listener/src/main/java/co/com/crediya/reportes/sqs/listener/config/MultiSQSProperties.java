package co.com.crediya.reportes.sqs.listener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "entrypoint.sqs")
public record MultiSQSProperties(
        String region,
        String endpoint,
        Map<String, Queue> queues
) {
    public record Queue(
            String queueUrl,
            int waitTimeSeconds,
            int visibilityTimeoutSeconds,
            int maxNumberOfMessages,
            int numberOfThreads,
            String processorBean
    ) {}
}
