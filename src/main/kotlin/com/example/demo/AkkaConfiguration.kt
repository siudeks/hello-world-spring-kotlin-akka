package com.example.demo

import akka.actor.ActorSystem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AkkaConfiguration {

    @Bean
    fun create() : ActorSystem = ActorSystem.create()
}