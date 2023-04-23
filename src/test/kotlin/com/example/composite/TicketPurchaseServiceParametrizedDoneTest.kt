package com.example.composite

import com.example.composite.validator.SingleAdultWithKidsRestrictionValidator.Companion.ERROR_MSG_SINGLE_ADULT_RESTRICTION
import com.example.composite.validator.TicketBuyerAgeAfter16Validator.Companion.ERROR_MSG_TICKET_BUYER_MUST_AT_LEAST_16
import com.example.composite.validator.TicketBuyerAgeValidForForeignTripeValidator.Companion.ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP
import com.example.composite.validator.TicketBuyerBelow18RestrictionValidator.Companion.ERROR_MSG_TICKET_BUYER_18_RESTRICTION

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TicketPurchaseServiceParametrizedDoneTest {

    @Autowired
    private lateinit var service: TicketPurchaseService

    companion object {
        private val NANY_2 = PassengerInfo("nany", 2)
        private val TOM_5 = PassengerInfo("tom", 5)
        private val LARA_7 = PassengerInfo("lara", 7)
        private val BOB_15 = PassengerInfo("bob", 15)
        private val CARL_17 = PassengerInfo("carl", 17)
        private val SARA_19 = PassengerInfo("sara", 19)
        private val NANCY_24 = PassengerInfo("NANCY", 24)
        private val UK = "uk"
        private val SPAIN = "Spain"
        private val UK_LONDON = "london"
        private val ES_MADRID = "madrid"
    }

    enum class TestScenario(
        val givenTicketRequest: TicketRequest,
    ) {
        givenBuyerAt17AndDestinationIsInsideTheUk_whenPurchaseTicket_thenIssueTheTicket(
            TicketRequest(CARL_17, UK,UK_LONDON  )
        ),
        givenBuyerAt17AndPassengerAt15_whenPurchaseTicket_thenIssueTheTicket(
            TicketRequest(CARL_17, UK,UK_LONDON , listOf(BOB_15)  )
        ),
        givenBuyerAt24AndDestinationIsOutsideTheUK_whenPurchaseTicket_thenIssueTheTicket(
            TicketRequest(NANCY_24, SPAIN, ES_MADRID  )
        ),
        givenBuyerAtAge24AndPassengerIncludeAnAdultAnd3Kids_whenPurchaseTicket_thenIssueTheTicket(
            TicketRequest(NANCY_24, UK,UK_LONDON, listOf(SARA_19,NANY_2,TOM_5,LARA_7) )
        )
    }

    @EnumSource(TestScenario::class)
    @ParameterizedTest
    fun run(sc: TestScenario) {
        //Given
        //When
        val ticketNumber= service.purchase(sc.givenTicketRequest)
        //Then
        assertThat(ticketNumber).isPositive()
    }


}