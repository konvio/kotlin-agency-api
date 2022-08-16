package tech.kononenko.agency.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.kononenko.agency.model.Agency
import tech.kononenko.agency.repository.AgencyRepository

@Service
class AgencyService {

    @Autowired
    lateinit var agencyRepository: AgencyRepository

    fun getAll(): List<Agency> = agencyRepository.findAll()

    fun getById(id: String): Agency? = agencyRepository.findById(id).orElse(null)

    fun createOrUpdate(agency: Agency): Agency = agencyRepository.save(agency)

    fun delete(id: String): Unit = agencyRepository.deleteById(id)
}
