package com.example.zwa_qtela.Booking

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.R
import com.example.zwa_qtela.utils.AlarmScheduler
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class BookingFragment : Fragment() {

    private lateinit var textStatusAlarm: TextView

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        textStatusAlarm = view.findViewById(R.id.textStatusAlarm)
        val btnSetelAlarm = view.findViewById<Button>(R.id.btnSetelAlarm)
        val btnBatalkanAlarm = view.findViewById<Button>(R.id.btnBatalkanAlarm)
        val btnSimpanBooking = view.findViewById<Button>(R.id.btnSimpanBooking)

        checkNotificationPermission()

        btnSetelAlarm?.setOnClickListener {
            showTimePicker()
        }

        btnBatalkanAlarm?.setOnClickListener {
            AlarmScheduler.cancelAlarm(requireContext())
            textStatusAlarm.text = getString(R.string.alarm_not_set)
            Toast.makeText(requireContext(), "Alarm berhasil dibatalkan", Toast.LENGTH_SHORT).show()
        }

        btnSimpanBooking?.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Form booking siap diproses!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar[Calendar.HOUR_OF_DAY])
            .setMinute(calendar[Calendar.MINUTE])
            .setTitleText("Pilih Jam Alarm Pengingat")
            .build()

        picker.addOnPositiveButtonClickListener {
            val alarmCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, picker.hour)
                set(Calendar.MINUTE, picker.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (alarmCalendar.timeInMillis <= System.currentTimeMillis()) {
                alarmCalendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            val title = "Pengingat Booking Badminton"
            val message = "Jadwal main badminton kamu sudah dekat! Jangan lupa persiapkan dirimu."

            AlarmScheduler.scheduleAlarm(
                requireContext(),
                alarmCalendar.timeInMillis,
                title,
                message
            )

            val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", picker.hour, picker.minute)
            val statusText = "Alarm diatur untuk jam $timeFormatted"
            textStatusAlarm.text = statusText
            Toast.makeText(requireContext(), "Alarm berhasil diatur pada $timeFormatted", Toast.LENGTH_SHORT).show()
        }

        picker.show(childFragmentManager, "ALARM_TIME_PICKER")
    }
}