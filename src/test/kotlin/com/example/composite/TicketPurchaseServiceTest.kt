package com.example.composite

import com.example.composite.validator.CheckSingleAdultWithKidsRestriction
import com.example.composite.validator.CheckSingleAdultWithKidsRestriction.Companion.ERROR_MSG_SINGLE_ADULT_RESTRICTION
import com.example.composite.validator.CheckTicketBuyerAgeAfter16.Companion.ERROR_MSG_TICKET_BUYER_MUST_AT_LEAST_16
import com.example.composite.validator.CheckTicketBuyerAgeValidForForeignTripe
import com.example.composite.validator.CheckTicketBuyerAgeValidForForeignTripe.Companion.ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP
import com.example.composite.validator.CheckTicketBuyerBelow18Restriction.Companion.ERROR_MSG_TICKET_BUYER_18_RESTRICTION
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TicketPurchaseServiceTest {

    @Autowired
    private lateinit var service: TicketPurchaseService
    private val NANY_2 = PassengerInfo("nany", 2)
    private val TOM_5 = PassengerInfo("tom", 5)
    private val LARA_7 = PassengerInfo("lara", 7)
    private val ANA_11 = PassengerInfo("ana", 11)
    private val BOB_15 = PassengerInfo("bob", 15)
    private val CARL_17 = PassengerInfo("carl", 17)
    private val SARA_19 = PassengerInfo("sara", 19)
    private val NANCY_24 = PassengerInfo("NANCY", 24)
    private val KATE_29 = PassengerInfo("NANCY", 29)

    private val UK = "uk"
    private val SPAIN = "Spain"
    private val UK_LONDON = "london"
    private val ES_MADRID = "madrid"


    @Test
    fun givenBuyerAt15_whenPurchaseTicket_thenThrowError(){
        // Given
        val request = TicketRequest(BOB_15, UK, UK_LONDON)
        // When
        val exception = assertThrows { service.purchase(request) } as Exception
        // Then
        assertThat(exception).hasMessage(ERROR_MSG_TICKET_BUYER_MUST_AT_LEAST_16)
    }

    @Test
    fun givenBuyerAt17AndOneKids_whenPurchaseTicket_thenThrowError(){
        // Given
        val request = TicketRequest(CARL_17, UK, UK_LONDON , listOf(LARA_7))
        // When
        val exception = assertThrows { service.purchase(request) } as Exception
        // Then
        assertThat(exception).hasMessage(ERROR_MSG_TICKET_BUYER_18_RESTRICTION)
    }

    @Test
    fun givenBuyerAt17AndDestinationIsOutsideOfTheUK_whenPurchaseTicket_thenThrowError(){
        // Given
        val request = TicketRequest(CARL_17, SPAIN, ES_MADRID )
        // When
        val exception = assertThrows { service.purchase(request) } as Exception
        // Then
        assertThat(exception).hasMessage(ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP)
    }

    @Test
    fun givenBuyerAt29AndDestinationIsOutsideOfTheUK_whenPurchaseTicket_thenThrowError(){
        // Given
        val request = TicketRequest(SARA_19,SPAIN, ES_MADRID  )
        // WhenSPAIN, ES_MADRID
        val exception = assertThrows { service.purchase(request) } as Exception
        // Then
        assertThat(exception).hasMessage(ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP)
    }

    @Test
    fun givenOnlyOnePassengerIsAdultAnd3Kids_whenPurchaseTicket_thenThrowError(){
        // Given
        val request = TicketRequest(SARA_19, UK,UK_LONDON , listOf(NANY_2,TOM_5,LARA_7) )
        // When
        val exception = assertThrows { service.purchase(request) } as Exception
        // Then
        assertThat(exception).hasMessage(ERROR_MSG_SINGLE_ADULT_RESTRICTION)
    }

    @Test
    fun givenBuyerAt17AndDestinationIsInsideTheUk_whenPurchaseTicket_thenIssueTheTicket(){
        // Given
        val request = TicketRequest(CARL_17, UK,UK_LONDON  )
        // When
        val ticketNumber= service.purchase(request)
        // Then
        assertThat(ticketNumber).isPositive()
    }

    @Test
    fun givenBuyerAt17AndPassengerAt15_whenPurchaseTicket_thenIssueTheTicket(){
        // Given
        val request = TicketRequest(CARL_17, UK,UK_LONDON , listOf(BOB_15)  )
        // When
        val ticketNumber= service.purchase(request)
        // Then
        assertThat(ticketNumber).isPositive()
    }


    @Test
    fun givenBuyerAt24AndDestinationIsOutsideTheUK_whenPurchaseTicket_thenIssueTheTicket(){
        // Given
        val request = TicketRequest(NANCY_24, SPAIN, ES_MADRID  )
        // When
        val ticketNumber= service.purchase(request)
        // Then
        assertThat(ticketNumber).isPositive()
    }

    @Test
    fun  givenBuyerAtAge24AndPassengerIncludeAnAdultAnd3Kids_whenPurchaseTicket_thenIssueTheTicket(){
        // Given
        val request = TicketRequest(NANCY_24, UK,UK_LONDON, listOf(SARA_19,NANY_2,TOM_5,LARA_7) )
        // When
        val ticketNumber= service.purchase(request)
        // Then
        assertThat(ticketNumber).isPositive()
    }


}