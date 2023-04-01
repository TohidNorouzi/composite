package com.example.composite

import org.springframework.stereotype.Service

@Service
class ForeignTripAgeRangePolicyService {
    fun getAllowAgeRange()= Pair(20,27)
}