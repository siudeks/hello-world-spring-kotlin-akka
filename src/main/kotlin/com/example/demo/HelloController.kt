package com.example.demo

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import akka.pattern.Patterns
import java.time.Duration


@RestController
class HelloController {

    @Autowired
    lateinit var actorSystem: ActorSystem

    companion object {
        val logger = LoggerFactory.getLogger(HelloController::class.java)!!
    }

    @GetMapping("/")
    fun hello(): Mono<String> = Mono.using(
            { actorSystem.actorOf(WorkerActor.props()) },
            { actor ->
                Mono.fromCompletionStage { Patterns.ask(actor, WorkerActor.RequestCommand("dupa"), Duration.ofSeconds(1)) }
                        .map { it as WorkerActor.RequestResult }
                        .map { it.Body }
            },
            { actor -> actor.tell(PoisonPill.getInstance(), ActorRef.noSender()) })
}


