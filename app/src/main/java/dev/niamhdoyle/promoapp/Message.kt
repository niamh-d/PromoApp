package dev.niamhdoyle.promoapp

import java.io.Serializable

data class Message(
    val contactName: String,
    val contactNumber: String,
    val displayName: String,
    val includeJr: Boolean,
    val jobTitle: String?,
    val immediateStart: Boolean,
    val startDate: String?
): Serializable {

    fun generateMessage(): String {
        return """
            Hi $contactName!,
            
            My name is $displayName and I am ${generateFullJobTitle()}.
            
            I have a portfolio of work to demonstrate my skills.
            
            I am available to start ${generateAvailability()}.
            
            Don't hesitate to get in touch!
            
            Thanks!
            
        """.trimIndent()
    }

    private fun generateAvailability() = if (immediateStart) "immediately" else "from $startDate"

    private fun generateFullJobTitle() = (if(includeJr) "a Junior " else if (jobTitle != null && jobTitle.contains("Android"))
        "an " else "a ") + jobTitle
}