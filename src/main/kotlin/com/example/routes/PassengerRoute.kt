package com.example.routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.PassengerRouting(){
    route("/PassengerBooking"){
        post {
            val pass = call.receive<Passer>()
            var fli: MutableList<Flight> = mutableListOf()
            var passenger:Passenger = Passenger(pass.id,pass.name,fli)
            val esp= BookingSystemPass.add(passenger)
            if (esp) call.respondText("INSERTED SUCCESSFULLY", status = HttpStatusCode.OK)
            else call.respondText("ENTER CORRECT DETAILS", status = HttpStatusCode.BadRequest)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?:return@delete call.respondText("ENTER ID ", status = HttpStatusCode.BadRequest)
            val esp= BookingSystemPass.removeIf { it.id==id }
            if(esp) call.respondText("REMOVED SUCCESSFULLY", status = HttpStatusCode.OK)
            else call.respondText("ENTER CORRECT FLIGHT NUMBER", status = HttpStatusCode.NotFound)
        }

        get("{id?}") {
            val id = call.parameters["id"] ?:return@get call.respondText("ENTER ID", status = HttpStatusCode.BadRequest)
            var pass=BookingSystemPass.filter { it.id == id }
            if(pass.isEmpty()) call.respondText ("NO FLIGHTS WITH ID : $id", status = HttpStatusCode.Accepted)
            else call.respond(pass)
        }

        get {
            call.respond(BookingSystemPass)
        }
    }
}