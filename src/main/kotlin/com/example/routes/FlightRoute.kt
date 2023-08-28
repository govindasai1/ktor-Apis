@file:Suppress("UNUSED_VARIABLE")

package com.example.routes


import com.example.models.BookingSystemFlig
import com.example.models.Flight
import com.example.models.FlightDet
import com.example.models.Message
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.NumberFormatException

fun Route.flightRouting(){
    route("/FlightsBooking"){
        post{
            try {
                val flight = call.receive<Flight>()
                val esp = BookingSystemFlig.add(flight)
                if (esp) call.respond(HttpStatusCode.Created,Message("INSERTED SUCCESSFULLY"))
                else call.respond(HttpStatusCode.BadRequest,Message("ENTER CORRECT DETAILS"))
            }catch (e:ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest,Message("ENTER FLIGHT DETAILS"))
            }
        }


        delete("{id?}") {
            val id = call.parameters["id"] ?:return@delete call.respond(HttpStatusCode.BadRequest,Message("ENTER ID "))
            val esp= BookingSystemFlig.removeIf { it.flightNumber==id }
            if(esp) call.respond(HttpStatusCode.OK,Message("REMOVED SUCCESSFULLY"))
            else call.respond(HttpStatusCode.NotFound,Message("ENTER CORRECT FLIGHT NUMBER"))
        }

        get {
            val flightDet = call.receive<FlightDet>()
            val list = BookingSystemFlig.filter { it.source==flightDet.source && it.destination==flightDet.destination && it.departureTime==flightDet.departureTime }
            if(list.isEmpty()) call.respond (HttpStatusCode.Accepted,Message("NO FLIGHTS WITH $flightDet") )
            else call.respond(list)
        }

        get("{id?}"){
            try {
                val change:Int = call.parameters["id"]?.toInt() ?: return@get call.respond (HttpStatusCode.OK,Message("done"))
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, Message("ENTER ID "))
                val list = BookingSystemFlig.filter { it.flightNumber == id }
                if (list.isEmpty()) call.respond(HttpStatusCode.Accepted,Message("NO FLIGHTS WITH $id"))
                else call.respond(list)
            }catch (e:NumberFormatException){
                call.respond(HttpStatusCode.NotAcceptable,Message("ENTER ID IN NUMBER"))
            }
        }
        }
}
