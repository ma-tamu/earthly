package jp.co.project.planets.earthly.aws.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.aws.AwsProviderProperty;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

/**
 * mail client
 */
@Component
public class MailClient {

    private final AwsProviderProperty providerProperty;

    private final String from;
    private final String region;

    public MailClient(final AwsProviderProperty providerProperty, @Value("${aws.ses.from}") final String from,
            @Value("${aws.ses.region}") final String region) {
        this.providerProperty = providerProperty;
        this.from = from;
        this.region = region;
    }

    /**
     * post ses service
     * 
     * @param to
     *            to mail address
     * @param subject
     *            subject
     * @param body
     *            body
     */
    public void post(final String to, final String subject, final String body) {
        final var destination = Destination.builder().toAddresses(to).build();
        final var content = Content.builder().data(body).build();
        final var sub = Content.builder().data(subject).build();
        final var message = Message.builder().subject(sub).body(builder -> builder.text(content)).build();
        final var request = SendEmailRequest.builder().destination(destination).message(message).source(from).build();
        postSes(request);
    }

    /**
     * generate ses client
     * 
     * @return SesClient
     */
    private SesClient generateClient() {
        final var builder = SesClient.builder() //
                .region(Region.of(region)) //
                .credentialsProvider(DefaultCredentialsProvider.create());

        if (providerProperty.isLocalStack()) {
            builder.applyMutation(b -> b.endpointOverride(URI.create(providerProperty.getEndpoint())));
        }

        return builder.build();
    }

    /**
     * post ses
     * 
     * @param request
     *            mail request
     */
    private void postSes(final SendEmailRequest request) {
        try (final var sesClient = generateClient()) {
            sesClient.sendEmail(request);
        } catch (final SesException e) {
            throw new RuntimeException(e.awsErrorDetails().errorMessage(), e);
        }
    }

}
