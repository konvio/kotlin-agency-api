package tech.kononenko.agency.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import tech.kononenko.agency.model.Agency

@Repository
interface AgencyRepository : MongoRepository<Agency, String>
