package com.example.composite

import com.example.composite.validator.ITicketRequestValidator
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TicketPurchaseService(private val ticketRequestValidators: List<ITicketRequestValidator>) {

    private fun validateRequest(ticketRequest: TicketRequest) {
        ticketRequestValidators.forEach { validator -> validator.validate(ticketRequest) }
    }

    private fun issueTheTicket(ticketRequest: TicketRequest):Long {
        val ticketNumber= Random(1).nextLong()
        println("Ticket $ticketNumber was issued for ${ticketRequest.ticketBuyer.name} to the city of ${ticketRequest.destinationCity} ")
        return ticketNumber
    }

    fun purchase(ticketRequest: TicketRequest) : Long{
        validateRequest(ticketRequest)
        return issueTheTicket(ticketRequest)
    }

}