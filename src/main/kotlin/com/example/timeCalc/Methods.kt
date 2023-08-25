package com.example.timeCalc

import com.example.models.Flight
import com.example.models.Passenger
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun timeTaken(departureTime: String, arrivalTime: String):Float {
    val startTime = LocalTime.parse(departureTime, DateTimeFormatter.ofPattern("HH:mm")).toSecondOfDay()
    println(startTime)
    val endTime = LocalTime.parse(arrivalTime, DateTimeFormatter.ofPattern("HH:mm")).toSecondOfDay()
    var time: Float = ((endTime - startTime) / 3600f)
    return time
}

fun getShortestFlight(flights: List<Flight>){
    if(flights.isEmpty()) println("NO FLIGHTS PRESENT")
    else{
        var shortFlight: Flight?=null
        for(Flight in flights){
            var List: List<Flight> = flights.sortedBy {timeTaken(it.departureTime,it.arrivalTime) }
            shortFlight=List[0]
        }
        println(shortFlight)
    }
}
fun processBookings(passengers:List<Passenger>)= runBlocking {
    var asyn = async {
        for (passenger in passengers) {
            var passId = passenger.id
            var flight = passenger.flights
            println("< passengerId: $passId ,Flight: $flight>")
        }
    }
    println("processed bookings are")
    asyn.await()
}