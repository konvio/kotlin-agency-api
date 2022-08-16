package tech.kononenko.agency.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech.kononenko.agency.model.Agency
import tech.kononenko.agency.service.AgencyService

@RestController
@RequestMapping("/api/v1/agencies")
class AgencyController {

    @Autowired
    lateinit var agencyService: AgencyService

    @GetMapping
    fun getAllAgencies() = agencyService.getAll()

    @GetMapping("/{id}")
    fun getAgencyById(@PathVariable id: String): ResponseEntity<Agency> {
        return if (agencyService.getById(id) != null) {
            ResponseEntity.ok(agencyService.getById(id))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping
    fun createOrUpdateAgency(@RequestBody agency: Agency) = agencyService.createOrUpdate(agency)

    @DeleteMapping("/{id}")
    fun deleteAgency(@PathVariable id: String) = agencyService.delete(id)
}
