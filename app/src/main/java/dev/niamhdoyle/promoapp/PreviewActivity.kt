package dev.niamhdoyle.promoapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import dev.niamhdoyle.promoapp.databinding.ActivityPreviewBinding
import androidx.core.net.toUri

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class PreviewActivity : AppCompatActivity() {

    private lateinit var vb: ActivityPreviewBinding
    private lateinit var message: Message
    private lateinit var messageBody: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(vb.root)
        displayMessagePreview()
        setupEditButton()
        setupSendMessageButton()
    }

    private fun displayMessagePreview() {
        message = intent.getSerializableExtra("Message", Message::class.java)!!
        messageBody = message.generateMessage()
        vb.textViewMessage.text = messageBody
    }

    private fun setupEditButton() {
        vb.buttonBack.setOnClickListener {
            val editMessageIntent = Intent(this, MainActivity::class.java)
            editMessageIntent.putExtra("message", message)
            startActivity(editMessageIntent)
        }
    }

    private fun setupSendMessageButton() {
        vb.buttonSendMessage.setOnClickListener {
            val sendMessageIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "smsto: ${message.contactNumber}".toUri()
                putExtra("sms_body", messageBody)
            }
            startActivity(sendMessageIntent)
        }
    }
}