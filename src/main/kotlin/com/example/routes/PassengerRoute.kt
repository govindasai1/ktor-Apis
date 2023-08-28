package com.example.routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.passengerRouting(){
    route("/PassengerBooking"){
        post {
            val pass = call.receive<Passer>()
            var fli: MutableList<Flight> = mutableListOf()
            var passenger = Passenger(pass.id,pass.name,fli)
            val esp= BookingSystemPass.add(passenger)
            if (esp) call.respond(HttpStatusCode.OK,Message("INSERTED SUCCESSFULLY") )
             else call.respond(HttpStatusCode.BadRequest,Message("ENTER CORRECT DETAILS"))
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?:return@delete call.respond(HttpStatusCode.BadRequest,Message("ENTER ID "))
            val esp= BookingSystemPass.removeIf { it.id==id }
            if(esp) call.respond(HttpStatusCode.OK,Message("REMOVED SUCCESSFULLY"))
            else call.respond(HttpStatusCode.NotFound,Message("ENTER CORRECT FLIGHT NUMBER"))
        }

        get("{id?}") {
            val id = call.parameters["id"] ?:return@get call.respond(HttpStatusCode.BadRequest,Message("ENTER ID "))
            var pass=BookingSystemPass.filter { it.id == id }
            if(pass.isEmpty()) call.respond (HttpStatusCode.Accepted,Message("NO FLIGHTS WITH ID : $id"))
            else call.respond(pass)
        }

        get {
            call.respond(BookingSystemPass)
        }
    }
}