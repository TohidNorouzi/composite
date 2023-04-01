package com.example.composite

data class TicketRequest(
    val ticketBuyer : PassengerInfo,
    val destinationCountry:String ,
    val destinationCity:String,
    val participants: List<PassengerInfo> = emptyList()
)
{

}

data class PassengerInfo(
    val name:String,
    val age:Int
)
