package com.example.demo

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props

class WorkerActor : AbstractActor() {


    companion object {

        // Note about invoking Props.create where the last parameter is outside parentheses
        // In Kotlin, there is a convention that if the last parameter of a function accepts a function,
        // a lambda expression that is passed as the corresponding argument can be placed outside the parentheses:
        // more@ https://kotlinlang.org/docs/reference/lambdas.html
        fun props() = Props.create(WorkerActor::class.java) { WorkerActor() } !!
        // alternative 1 fun props() = Props.create(WorkerActor::class.java, ::WorkerActor ) !!
        // alternative 2 fun props() = Props.create(WorkerActor::class.java, fun() = WorkerActor())!!
        // alternative 3 fun props() = Props.create(WorkerActor::class.java, fun(): WorkerActor { return WorkerActor() })!!

    }

    override fun createReceive() = receiveBuilder()
            .matchAny(this::onMessage)
            .build()!!

    private fun onMessage(it: Any?): Unit = when (it) {
        is RequestCommand -> self().tell("Hello ${it.Body}", sender())
        is String -> sender().tell(RequestResult("$it !"), ActorRef.noSender())
        else -> Unit
    }

    class RequestCommand(val Body: String)

    class RequestResult(val Body: String)
}