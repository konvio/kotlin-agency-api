package tech.kononenko.agency.model

import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Document(collection = "agency")
data class Agency(
    @get:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Invalid agency id")
    var id: String? = null,

    @get:NotEmpty(message = "Agency name is required")
    val name: String,

    @get:Size(max = 255, message = "Country must be at most 255 characters")
    val country: String? = null,

    @get:Pattern(regexp = "^[A-Z]+$", message = "Country code must be uppercase")
    @get:Size(max = 5, message = "Country code must be at most 6 characters")
    val countryCode: String? = null,

    @get:Size(max = 255, message = "City must be at most 255 characters")
    val city: String? = null,

    @get:Size(max = 255, message = "Agency description must be at most 256 characters")
    val street: String? = null,

    val settlementCurrency: CurrencyType? = null,

    @get:Size(max = 255, message = "Contact person description must be at most 256 characters")
    val contactPerson: String? = null,
)
