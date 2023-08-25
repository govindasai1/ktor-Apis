package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Flight (var flightNumber:String,var source:String,var destination:String,var departureTime:String,var arrivalTime:String)
@Serializable
data class Passenger(var id:String, var name:String, var flights: MutableList<Flight>)
@Serializable
data class Passer(var id:String, var name:String)
@Serializable
data class FlightDet(var source:String,var destination:String,var departureTime:String)
@Serializable
data class PassFlight(var id:String,var flightNumber:String)

//var PassengersList: MutableList<Passenger> = mutableListOf()
var BookingSystemFlig: MutableList<Flight> = mutableListOf()
var BookingSystemPass: MutableList<Passenger> = mutableListOf()