package com.example.composite.validator

import com.example.composite.TicketRequest

interface ITicketRequestValidator {
    fun validate (ticketRequest: TicketRequest)
}