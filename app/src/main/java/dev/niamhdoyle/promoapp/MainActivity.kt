package dev.niamhdoyle.promoapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dev.niamhdoyle.promoapp.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    private var startDateViewIsVisible = true
    private var message: Message? = null
    private val spinnerValues = arrayOf("Android Developer", "Android Engineer", "Kotlin Developer", "Mobile Developer")

    override fun onCreate(savedInstanceState: Bundle?) {

        message = intent.getSerializableExtra("message", Message::class.java)

        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.buttonPreview.setOnClickListener { onPreviewClicked() }
        vb.checkBoxImmediateStart.setOnClickListener {toggleStartDateView()}

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues)
        vb.spinnerJobTitle.adapter = spinnerAdapter

        setFields()
    }

    private fun toggleStartDateView() {
        startDateViewIsVisible = !startDateViewIsVisible
        vb.editTextStartDate.isVisible = startDateViewIsVisible
    }

    private fun setFields() {
        if(message == null) return
        else {
            val jobTitle = message!!.jobTitle
            val spinnerIndex = if(jobTitle== null) 0 else spinnerValues.indexOf(jobTitle)

            vb.editTextContactName.setText(message!!.contactName)
            vb.editTextContactNumber.setText(message!!.contactNumber)
            vb.editTextMyDisplayName.setText(message!!.displayName)
            vb.checkBoxJunior.setChecked(message!!.includeJr)
            vb.spinnerJobTitle.setSelection(spinnerIndex)
            vb.checkBoxImmediateStart.setChecked(message!!.immediateStart)
            vb.editTextStartDate.setText(message!!.startDate)
        }
    }

    private fun onPreviewClicked() {
        val message = Message(
            vb.editTextContactName.text.toString(),
            vb.editTextContactNumber.text.toString(),
            vb.editTextMyDisplayName.text.toString(),
            vb.checkBoxJunior.isChecked,
            vb.spinnerJobTitle.selectedItem?.toString(),
            vb.checkBoxImmediateStart.isChecked,
            vb.editTextStartDate.text?.toString()
        )

        val previewActivityIntent = Intent(this, PreviewActivity::class.java)
        previewActivityIntent.putExtra("Message", message)
        startActivity(previewActivityIntent)
    }

}