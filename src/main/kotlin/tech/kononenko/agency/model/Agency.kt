package tech.kononenko.agency.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "agency")
data class Agency(
    var id: String? = null,
    val name: String,
    val country: String,
    val countryCode: String,
    val city: String,
    val street: String,
    val settlementCurrency: String,
    val contactPerson: String
)
