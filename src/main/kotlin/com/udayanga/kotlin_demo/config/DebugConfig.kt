package com.udayanga.kotlin_demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DebugConfig {

    @Value("\${spring.mongodb.uri:NOT_FOUND}")
    lateinit var mongoUri: String

    @Bean
    fun debugRunner() = CommandLineRunner {
        println("================================")
        println("Mongo URI = $mongoUri")
        println("================================")
    }
}
