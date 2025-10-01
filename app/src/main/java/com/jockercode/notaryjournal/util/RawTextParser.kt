package com.jockercode.notaryjournal.util

import com.jockercode.notaryjournal.model.DriverLicense

object RawTextParser {

    fun parse(rawText: String): DriverLicense {
        // Normalize text
        val cleaned = rawText.replace("\\s+".toRegex(), " ").trim()
        val lines = rawText.lines().map { it.trim() }.filter { it.isNotBlank() }

        // Extract DOB (MM/DD/YYYY)
        val dobRegex = Regex("""\b\d{2}/\d{2}/\d{4}\b""")
        val dob = dobRegex.find(cleaned)?.value

        // Extract address
        val addressLine = lines.firstOrNull { it.contains(Regex("""\d{3,}.*(ST|AVE|RD|BLVD)""", RegexOption.IGNORE_CASE)) }
        val cityLine = lines.firstOrNull { it.contains("NY") && it.contains(Regex("""\d{5}""")) }
        val address = listOfNotNull(addressLine, cityLine).joinToString(" ")

        // Extract name
        val nameLine = lines.firstOrNull { it.contains(",") }
        val fullName = nameLine?.replace(",", " ")?.trim()

        return DriverLicense(
            fullName = fullName,
            dob = dob,
            address = address.ifBlank { null }
        )
    }
}