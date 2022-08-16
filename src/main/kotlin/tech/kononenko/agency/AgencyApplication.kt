package tech.kononenko.agency

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AgencyApplication

fun main(args: Array<String>) {
    runApplication<AgencyApplication>(*args)
}
