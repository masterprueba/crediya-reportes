package co.com.crediya.reportes.sqs.listener.config;

public record SQSProperties(
        String region,
        String endpoint,
        String queueUrl,
        int waitTimeSeconds,
        int visibilityTimeoutSeconds,
        int maxNumberOfMessages,
        int numberOfThreads) {
}
