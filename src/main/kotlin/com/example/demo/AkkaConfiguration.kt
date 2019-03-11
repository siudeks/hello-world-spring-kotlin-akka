package com.example.demo

import akka.actor.ActorSystem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * There are two schools of sharing instance of ActorSystem
 * static reference, of by dependency injection (DI)
 * In our example wi'll use DI, but in my practize static reference could be ok in most of cases.
 */
@Configuration
class AkkaConfiguration {

    @Bean
    fun create() : ActorSystem = ActorSystem.create()
}