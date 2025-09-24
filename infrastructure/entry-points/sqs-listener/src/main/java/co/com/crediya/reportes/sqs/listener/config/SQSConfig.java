package co.com.crediya.reportes.sqs.listener.config;

import co.com.crediya.reportes.sqs.listener.helper.SQSListener;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Configuration
@EnableConfigurationProperties(MultiSQSProperties.class)
public class SQSConfig {

    private final List<SQSListener> startedListeners = new ArrayList<>();

    @Bean
    public SqsAsyncClient sqsAsyncClient(MultiSQSProperties props, MetricPublisher publisher) {
        var builder = SqsAsyncClient.builder()
                .region(Region.of(props.region()))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
                .credentialsProvider(providerChain());
        if (props.endpoint() != null) builder.endpointOverride(URI.create(props.endpoint()));
        return builder.build();
    }

    @Bean
    public List<SQSListener> sqsListeners(
            @Qualifier("sqsAsyncClient") SqsAsyncClient client,
            MultiSQSProperties props,
            ApplicationContext ctx
    ) {
        props.queues().forEach((name, q) -> {
            @SuppressWarnings("unchecked")
            Function<Message, Mono<Void>> processor =
                     ctx.getBean(q.processorBean(), Function.class);

            var perQueue = new SQSProperties(
                    props.region(),
                    props.endpoint(),
                    q.queueUrl(),
                    q.waitTimeSeconds(),
                    q.visibilityTimeoutSeconds(),
                    q.maxNumberOfMessages(),
                    q.numberOfThreads()
            );

            var listener = SQSListener.builder()
                    .client(client)
                    .properties(perQueue)
                    .processor(processor)
                    .build()
                    .start();

            startedListeners.add(listener);
        });
        return startedListeners;
    }

    private AwsCredentialsProviderChain providerChain() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }

    @PreDestroy
    public void shutdown() {
        startedListeners.forEach(l -> {
            try { l.close(); } catch (Exception ignored) {}
        });
    }
}
