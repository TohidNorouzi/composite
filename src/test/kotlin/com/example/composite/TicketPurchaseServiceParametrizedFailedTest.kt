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
class TicketPurchaseServiceParametrizedFailedTest {

    @Autowired
    private lateinit var service: TicketPurchaseService

    companion object {
        private val NANY_2 = PassengerInfo("nany", 2)
        private val TOM_5 = PassengerInfo("tom", 5)
        private val LARA_7 = PassengerInfo("lara", 7)
        private val BOB_15 = PassengerInfo("bob", 15)
        private val CARL_17 = PassengerInfo("carl", 17)
        private val SARA_19 = PassengerInfo("sara", 19)
        private val UK = "uk"
        private val SPAIN = "Spain"
        private val UK_LONDON = "london"
        private val ES_MADRID = "madrid"
    }

    enum class TestScenario(
        val givenTicketRequest: TicketRequest,
        val thenExceptionMessage: String
    ) {
        givenBuyerAt15_whenPurchaseTicket_thenThrowError(
            TicketRequest(BOB_15, UK, UK_LONDON),
            ERROR_MSG_TICKET_BUYER_MUST_AT_LEAST_16
        ),
        givenBuyerAt17AndOneKids_whenPurchaseTicket_thenThrowError(
            TicketRequest(CARL_17, UK, UK_LONDON, listOf(LARA_7)),
            ERROR_MSG_TICKET_BUYER_18_RESTRICTION
        ),
        givenBuyerAt17AndDestinationIsOutsideOfTheUK_whenPurchaseTicket_thenThrowError(
            TicketRequest(CARL_17, SPAIN, ES_MADRID),
            ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP
        ),
        givenBuyerAt29AndDestinationIsOutsideOfTheUK_whenPurchaseTicket_thenThrowError(
            TicketRequest(SARA_19, SPAIN, ES_MADRID),
            ERROR_MSG_TICKET_BUYER_MUST_20_TO_27_FOR_FOREIGN_TRIP
        ),
        givenOnlyOnePassengerIsAdultAnd3Kids_whenPurchaseTicket_thenThrowError(
            TicketRequest(SARA_19, UK, UK_LONDON, listOf(NANY_2, TOM_5, LARA_7)),
            ERROR_MSG_SINGLE_ADULT_RESTRICTION
        )


    }

    @EnumSource(TestScenario::class)
    @ParameterizedTest
    fun run(sc: TestScenario) {
        //Given
        //When
        val exception = assertThrows { service.purchase(sc.givenTicketRequest) } as Exception
        //Then
        assertThat(exception).hasMessage(sc.thenExceptionMessage)
    }


}