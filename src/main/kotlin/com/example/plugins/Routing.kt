package com.example.plugins

import com.example.routes.PassengerRouting
import com.example.routes.bookingRouting
import com.example.routes.flightRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        bookingRouting()
        flightRouting()
        PassengerRouting()

//        get("/") {
//
//            call.respondText("Hello World!")
//        }
    }
}
