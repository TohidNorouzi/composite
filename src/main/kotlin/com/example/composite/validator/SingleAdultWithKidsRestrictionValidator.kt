package com.example.composite.validator

import com.example.composite.PassengerInfo
import com.example.composite.TicketRequest
import org.springframework.stereotype.Component

@Component
class SingleAdultWithKidsRestrictionValidator : ITicketRequestValidator {

    companion object {
        const val ERROR_MSG_SINGLE_ADULT_RESTRICTION =
            "You are only allowed to accompany 2 people under the age of 8 years old"
    }

    private fun areThereMoreThan2ChildrenPresent(allPassengers:List<PassengerInfo>)=
        allPassengers.filter { it.age <= 8 }.size > 2

    private fun isThereAtMostOneAdultPresent(allPassengers:List<PassengerInfo>)=
        allPassengers.filter { it.age >=18 }.size <= 1



    override fun validate(ticketRequest: TicketRequest) {
        val allPassengers = mutableListOf(*ticketRequest.participants.toTypedArray())
        allPassengers.add(ticketRequest.ticketBuyer)
        if (areThereMoreThan2ChildrenPresent(allPassengers) && isThereAtMostOneAdultPresent(allPassengers) )
            throw Exception(ERROR_MSG_SINGLE_ADULT_RESTRICTION)

    }

}