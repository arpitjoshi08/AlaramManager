package com.example.alarammanager

import android.app.*
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    //region - private properties
    private val calendar = Calendar.getInstance()
    private var hour: Int = 0
    private var minute: Int = 0
    private var selectedday = 0
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0
    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0
    private var message: String = ""
    //endregion

    //region - override functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setonClickListener()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectedYear = year
        selectedday = dayOfMonth
        selectedMonth = monthOfYear

        val timePickerDialog = TimePickerDialog(
            this@MainActivity,
            this@MainActivity,
            hour,
            minute,
            DateFormat.is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, p2: Int) {
        selectedHour = hourOfDay;
        selectedMinute = p2;
        calendar.set(selectedYear, selectedMonth, selectedday, selectedHour, selectedMinute, 0)

        message = "Year: " + selectedYear + " " +
                "Month: " + selectedMonth + " " +
                "Day: " + selectedday + " " +
                "Hour: " + selectedHour + " " +
                "Minute: " + selectedMinute
        var date = calendar?.time

        //method which need message and time in milisecond to show notification
        NotificationGenerate.setEventAlarm(this, message, date!!.time)
        timeDetail.setText(message)
    }
    //endregion

    //region - private function
    private fun setonClickListener() {
        selectButton.setOnClickListener({
            selectDateClick()
        })
    }

    private fun selectDateClick() {
        val calendar = Calendar.getInstance()
        var year = calendar[Calendar.YEAR]
        var month = calendar[Calendar.MONTH]

        var day = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog =
            DatePickerDialog(this@MainActivity, this@MainActivity, year, month, day)
        datePickerDialog.show()
    }
    //endregion

}