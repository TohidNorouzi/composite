package com.example.composite.validator

import com.example.composite.TicketRequest
import org.springframework.stereotype.Component

@Component
class CheckTicketBuyerBelow18Restriction : ITicketRequestValidator {

    companion object {
        const val ERROR_MSG_TICKET_BUYER_18_RESTRICTION =
            "People under the age of 18 cannot buy tickets for people under the age of 12"
    }

    private fun isBuyerMature(ticketRequest: TicketRequest) = ticketRequest.ticketBuyer.age >= 18

    private fun isAnyRestrictedPassengerExist(ticketRequest: TicketRequest) =
        ticketRequest.participants.any { it.age <= 12 }

    override fun validate(ticketRequest: TicketRequest) {
        if (isBuyerMature(ticketRequest)) return
        if (isAnyRestrictedPassengerExist(ticketRequest)) throw Exception(ERROR_MSG_TICKET_BUYER_18_RESTRICTION)
    }

}