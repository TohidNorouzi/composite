package com.example.composite.validator

import com.example.composite.TicketRequest
import com.example.composite.ForeignTripAgeRangePolicyService
import org.springframework.stereotype.Component

@Component
class CheckTicketBuyerAgeValidForForeignTripe constructor(private val policyService: ForeignTripAgeRangePolicyService) :
    ITicketRequestValidator {

    companion object {
        const val ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP =
            "The ticket buyer must be within the age limit to use the ground travel service outside of the UK in accordance with Unicorn Company's internal rules!"
    }

    private fun isTheTripWithinTheUk(ticketRequest: TicketRequest) = ticketRequest.destinationCountry.uppercase() == "UK"

    private fun isBuyerAgeInRange(ticketRequest: TicketRequest): Boolean {
        val (fromRange, toRange) = policyService.getAllowAgeRange()
        return (ticketRequest.ticketBuyer.age in fromRange..toRange)
    }


    override fun validate(ticketRequest: TicketRequest) {
        if (isTheTripWithinTheUk(ticketRequest)) return
        if (isBuyerAgeInRange(ticketRequest)) return
        throw Exception(ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP)
    }

}