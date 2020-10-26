package org.example.third.tasks.part.two.config;

import io.sentry.spring.EnableSentry;
import org.springframework.context.annotation.Configuration;

@EnableSentry(dsn = "https://d2070778548d47359b660cf8af8c83b7@o467159.ingest.sentry.io/5493132", sendDefaultPii = true)
@Configuration
public class SentryConfiguration {
}
