package gm.servicedesk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public record AppProperties(
        String custom,
        String url,
        JobProperties job) {
}
