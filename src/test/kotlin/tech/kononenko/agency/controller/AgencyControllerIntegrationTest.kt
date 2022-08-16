package tech.kononenko.agency.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import tech.kononenko.agency.AgencyApplication
import tech.kononenko.agency.model.Agency
import tech.kononenko.agency.repository.AgencyRepository

@ContextConfiguration(classes = [AgencyApplication::class])
@ExtendWith(SpringExtension::class)
@WebAppConfiguration
internal class AgencyControllerIntegrationTest {

    private val baseUrl = "/api/v1/agencies"

    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var agencyRepository: AgencyRepository

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        agencyRepository.deleteAll()
    }

    @Test
    fun getAllAgencies_noData_returnsOkStatus() {
        mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk)
    }

    @Test
    fun createAgencyAndGetById_returnsTheSameName() {
        val agency = getSampleAgency()

        val createdAgencyJson = mockMvc.perform(
            put(baseUrl).content(agency.toJson()).contentType("application/json")
        ).andExpect(status().isOk)
            .andReturn()
            .response.contentAsString

        val createdAgency = readAgencyFromJson(createdAgencyJson)

        val foundAgencyJson = mockMvc.perform(get(baseUrl + "/" + createdAgency.id))
            .andExpect(status().is2xxSuccessful)
            .andReturn().response.contentAsString

        val foundAgency = readAgencyFromJson(foundAgencyJson)

        assertThat(foundAgency.name).isEqualTo(createdAgency.name)
    }

    @Test
    fun create100Agencies_findAll_returnsAll() {
        repeat(100) {
            mockMvc.perform(
                put(baseUrl).content(getSampleAgency().toJson()).contentType("application/json")
            ).andExpect(status().is2xxSuccessful)
        }

        val allAgenciesJson = mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val allAgencies = readAgenciesFromJson(allAgenciesJson)
        assertThat(allAgencies.size).isEqualTo(100)
    }

    @Test
    fun createAgencyAndDelete_returnsNotFound() {
        val agency = getSampleAgency()
        agency.id = "agencyid"

        mockMvc.perform(
            put(baseUrl).content(agency.toJson()).contentType("application/json")
        ).andExpect(status().is2xxSuccessful)

        mockMvc.perform(delete(baseUrl + "/" + agency.id))
            .andExpect(status().is2xxSuccessful)

        mockMvc.perform(get(baseUrl + "/" + agency.id)).andExpect(status().isNotFound)
    }

    private fun getSampleAgency(name: String = "Test Agency"): Agency {
        return Agency(
            name = name,
            country = "Italy",
            countryCode = "IT",
            city = "Rome",
            street = "Via Test, 1",
            contactPerson = "John Doe",
            settlementCurrency = "EUR"
        )
    }

    private fun Agency.toJson(): String {
        return objectMapper.writeValueAsString(this)
    }

    private fun readAgenciesFromJson(json: String): List<Agency> {
        val type = objectMapper.typeFactory.constructCollectionType(
            List::class.java, Agency::class.java
        )
        return objectMapper.readValue(json, type)
    }

    private fun readAgencyFromJson(json: String): Agency {
        return objectMapper.readValue(json, Agency::class.java)
    }
}
