package tech.kononenko.agency.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class MongoConfig {

    @Bean
    fun validatingMongoEventListener(factory: LocalValidatorFactoryBean): ValidatingMongoEventListener {
        return ValidatingMongoEventListener(factory)
    }
}
