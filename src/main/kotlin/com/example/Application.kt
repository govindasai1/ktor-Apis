package com.example

import com.example.exceptions.FlightNumber
import com.example.exceptions.FlightOrId
import com.example.exceptions.Id
import com.example.models.*
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.lang.NumberFormatException


fun main() {
    embeddedServer(Netty, port = 8081, host = "127.0.0.3", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(RequestValidation) {
        validate<Flight> {
            try {
                if (it.flightNumber.toInt() > 0)
                    ValidationResult.Valid
                else ValidationResult.Invalid("flight-number cant be negative")
            } catch (e: NumberFormatException) {
                throw FlightNumber()
            }
        }
        validate<PassFlight> {
            try {
                if (it.flightNumber.toInt() > 0 && it.id.toInt() > 0)
                    ValidationResult.Valid
                else ValidationResult.Invalid("flight-number or id cant be negative")
            } catch (e: NumberFormatException) {
                throw FlightOrId()
            }
        }
        validate<Passer> {
            try {
                if (it.id.toInt() > 0)
                    ValidationResult.Valid
                else ValidationResult.Invalid("id cant be negative")
            } catch (e: NumberFormatException) {
                throw Id()
            }
        }
        validate<Passenger> {
            try {
                if (it.id.toInt() > 0)
                    ValidationResult.Valid
                else ValidationResult.Invalid("flight-number cant be negative")
            } catch (e: NumberFormatException) {
                throw FlightNumber()
            }
        }

    }
    install(StatusPages) {
        exception<FlightNumber> { call, cause ->
            call.respond(HttpStatusCode.NotAcceptable,Message("Flight number cant be words"))
            throw cause
        }
        exception<FlightOrId>{
            call, cause -> call.respond(HttpStatusCode.NotAcceptable,"Flight number or id cant be in words")
            throw cause
        }
        exception<Id>{
            call, cause -> call.respond(HttpStatusCode.NotAcceptable,Message("Id cant be in words"))
            throw cause
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }

    }
    configureSerialization()
    configureRouting()
}





