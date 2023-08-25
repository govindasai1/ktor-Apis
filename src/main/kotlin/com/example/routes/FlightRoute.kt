package com.example.routes


import com.example.models.BookingSystemFlig
import com.example.models.Flight
import com.example.models.FlightDet
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.flightRouting(){
    route("/FlightsBooking"){
        post{
            val flight = call.receive<Flight>()
            val esp = BookingSystemFlig.add(flight)
            if(esp) call.respondText("INSERTED SUCCESSFULLY", status = HttpStatusCode.Created)
            else call.respondText("ENTER CORRECT DETAILS", status = HttpStatusCode.BadRequest)
        }


        delete("{id?}") {
            val id = call.parameters["id"] ?:return@delete call.respondText("ENTER ID ", status = HttpStatusCode.BadRequest)
            val esp= BookingSystemFlig.removeIf { it.flightNumber==id }
            if(esp) call.respondText("REMOVED SUCCESSFULLY", status = HttpStatusCode.OK)
            else call.respondText("ENTER CORRECT FLIGHT NUMBER", status = HttpStatusCode.NotFound)
        }

        get {
            val flightDet = call.receive<FlightDet>()
            val list = BookingSystemFlig.filter { it.source==flightDet.source && it.destination==flightDet.destination && it.departureTime==flightDet.departureTime }
            if(list.isEmpty()) call.respondText ("NO FLIGHTS WITH $flightDet", status = HttpStatusCode.Accepted)
            else call.respond(list)
        }

        get("{id?}"){
            val id = call.parameters["id"] ?:return@get call.respondText("ENTER ID ", status = HttpStatusCode.BadRequest)
            val list = BookingSystemFlig.filter { it.flightNumber==id }
            if(list.isEmpty()) call.respondText ("NO FLIGHTS WITH $id", status = HttpStatusCode.Accepted)
            else call.respond(list)
        }
        }
}
