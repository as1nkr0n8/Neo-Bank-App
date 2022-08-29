package com.as1nkr0n8.pii_verification.domain.pii

import org.junit.Test

class PANTest {

    @Test
    fun `isValid success`() {
        val panString = "ABCDE1234F"
        assert(PAN(panString).isValid())
    }

    @Test
    fun `isValid fail`() {
        val panString = "ABCDE12345F12"
        assert(PAN(panString).isValid().not())
    }
}