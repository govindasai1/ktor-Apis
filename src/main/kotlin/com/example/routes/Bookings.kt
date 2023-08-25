package com.example.routes

import com.example.models.*
import com.example.timeCalc.getShortestFlight
import com.example.timeCalc.processBookings
import com.example.timeCalc.timeTaken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bookingRouting(){
    route("/bookings"){
        post {
            val pass = call.receive<PassFlight>()
            var list = BookingSystemFlig.filter { it.flightNumber == pass.flightNumber }
            if (list.isNotEmpty()){
                BookingSystemPass.map {
                    if (it.id == pass.id) {
                        it.flights.addAll(list)
                    }
                }
            call.respondText("FLIGHT BOOKED FOR PASSSENGER ID : ${pass.id}", status = HttpStatusCode.OK)
        }
            else
                call.respondText("FLIGHT NOT FOUND", status = HttpStatusCode.BadRequest)
        }

        delete {
            val pass = call.receive<PassFlight>()
            BookingSystemPass.map { if(it.id == pass.id){
                it.flights.removeIf { it.flightNumber == pass.flightNumber }
            }
            }
            call.respond(BookingSystemPass)
        }

        get("totaltime/{id?}") {
            var id=call.parameters["id"] ?:return@get call.respondText("ENTER ID", status = HttpStatusCode.OK)
            var totalTime:Float=0f
            var list: List<Passenger> =BookingSystemPass.filter { it.id == id }
            if(list.isNotEmpty()){
                list.map { it.flights.map { totalTime+=timeTaken(it.departureTime,it.arrivalTime) } }
                call.respondText("TOTAL TRAVEL TIME IS $totalTime", status = HttpStatusCode.Accepted)
            }
        }

        get("/shortestFlight"){
            var flight =getShortestFlight(BookingSystemFlig)
            call.respond(flight)
        }

        get("/bookings"){
            call.respond(processBookings(BookingSystemPass))
        }
    }
}