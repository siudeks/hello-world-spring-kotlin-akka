package com.example.demo

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props

class WorkerActor : AbstractActor() {

    companion object {
        fun props() = Props.create(WorkerActor::class.java, { WorkerActor() })
    }

    override fun createReceive() = receiveBuilder()
            .matchAny {
                onMessage(it)
            }
            .build()

    private fun onMessage(it: Any?): Unit = when (it) {
        is RequestCommand -> self().tell(it.Body + "1", sender())
        is String -> sender().tell(RequestResult(it + "2"), ActorRef.noSender())
        else -> {}
    }

    class RequestCommand(val Body: String)

    class RequestResult(val Body: String)
}