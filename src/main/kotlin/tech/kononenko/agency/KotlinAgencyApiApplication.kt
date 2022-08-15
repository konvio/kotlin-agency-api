package tech.kononenko.agency

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinAgencyApiApplication

fun main(args: Array<String>) {
    runApplication<KotlinAgencyApiApplication>(*args)
}
