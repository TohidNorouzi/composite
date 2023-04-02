package com.example.composite.validator

import com.example.composite.TicketRequest
import org.springframework.stereotype.Component

@Component
class TicketBuyerAgeAfter16Validator : ITicketRequestValidator {

    companion object {
        const val ERROR_MSG_TICKET_BUYER_MUST_AT_LEAST_16 = "The ticket buyer must be at least 16 years old"
    }

    override fun validate(ticketRequest: TicketRequest) {
        if (ticketRequest.ticketBuyer.age >= 16) return
        throw Exception(ERROR_MSG_TICKET_BUYER_MUST_AT_LEAST_16)
    }

}