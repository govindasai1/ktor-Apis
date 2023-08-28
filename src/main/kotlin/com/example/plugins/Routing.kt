package com.example.plugins

import com.example.models.Message
import com.example.routes.passengerRouting
import com.example.routes.bookingRouting
import com.example.routes.flightRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        bookingRouting()
        flightRouting()
        passengerRouting()

        get("/") {
            call.respond(HttpStatusCode.NotFound,Message("hi there who is dhis"))
        }

    }
}
