package com.example.demo

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import akka.pattern.Patterns
import org.springframework.web.bind.annotation.PathVariable
import java.time.Duration


@RestController
class HelloController {

    @Autowired
    lateinit var actorSystem: ActorSystem

    @GetMapping("/{name}")
    fun hello(@PathVariable("name") name: String ): Mono<String> = Mono.using(
            { actorSystem.actorOf(WorkerActor.props()) },
            { actor ->
                Mono.fromCompletionStage { Patterns.ask(actor, WorkerActor.RequestCommand(name), Duration.ofSeconds(1)) }
                        .map { it as WorkerActor.RequestResult }
                        .map { it.Body }
            },
            { actor -> actor.tell(PoisonPill.getInstance(), ActorRef.noSender()) })
}


