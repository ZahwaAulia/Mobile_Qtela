
package com.example.zwa_qtela.Booking

import android.Manifest
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.R
import com.example.zwa_qtela.utils.AlarmScheduler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class BookingFragment : Fragment() {

    private lateinit var textStatusAlarm: TextView
    private lateinit var spinnerLapangan: Spinner
    private lateinit var spinnerJam: Spinner
    private lateinit var btnPilihTanggal: Button
    private lateinit var inputNama: EditText
    private lateinit var inputNomorHP: EditText

    private var tanggalTerpilih = ""

    private val daftarLapangan = listOf(
        "LAPANGAN 1",
        "LAPANGAN 2"
    )

    private val daftarJam = listOf(
        "13:00 - 14:00",
        "14:00 - 15:00",
        "15:00 - 16:00",
        "16:00 - 17:00",
        "17:00 - 18:00",
        "19:00 - 20:00",
        "20:00 - 21:00"
    )

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            val pesan = if (isGranted) {
                "Izin notifikasi diberikan"
            } else {
                "Izin notifikasi ditolak"
            }

            Toast.makeText(
                requireContext(),
                pesan,
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_booking,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        inputNama = view.findViewById(R.id.inputNama)
        inputNomorHP = view.findViewById(R.id.inputNomorHP)
        spinnerLapangan = view.findViewById(R.id.spinnerLapangan)
        spinnerJam = view.findViewById(R.id.spinnerJam)
        btnPilihTanggal = view.findViewById(R.id.btnPilihTanggal)
        textStatusAlarm = view.findViewById(R.id.textStatusAlarm)

        val btnSetelAlarm =
            view.findViewById<Button>(R.id.btnSetelAlarm)

        val btnBatalkanAlarm =
            view.findViewById<Button>(R.id.btnBatalkanAlarm)

        val btnSimpanBooking =
            view.findViewById<Button>(R.id.btnSimpanBooking)

        checkNotificationPermission()

        // Pilihan lapangan
        spinnerLapangan.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            daftarLapangan
        ).apply {
            setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
        }

        val selectedLapangan =
            arguments?.getString("lapangan_nama")

        val index = daftarLapangan.indexOf(selectedLapangan)
        if (index >= 0) {
            spinnerLapangan.setSelection(index)
        }

        // Pilihan jam booking
        spinnerJam.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            daftarJam
        ).apply {
            setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
        }

        // Pilih tanggal booking
        btnPilihTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    tanggalTerpilih = String.format(
                        Locale.getDefault(),
                        "%02d/%02d/%04d",
                        dayOfMonth,
                        month + 1,
                        year
                    )

                    btnPilihTanggal.text = tanggalTerpilih
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Tombol atur alarm
        btnSetelAlarm.setOnClickListener {
            if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                !hasExactAlarmPermission()
            ) {
                requestExactAlarmPermission()
            } else {
                showTimePicker()
            }
        }

        // Tombol batalkan alarm
        btnBatalkanAlarm.setOnClickListener {
            AlarmScheduler.cancelAlarm(requireContext())

            textStatusAlarm.text =
                getString(R.string.alarm_not_set)

            Toast.makeText(
                requireContext(),
                "Alarm berhasil dibatalkan",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Tombol simpan booking
        btnSimpanBooking.setOnClickListener {
            val nama = inputNama.text.toString().trim()
            val nomorHP = inputNomorHP.text.toString().trim()
            val lapangan = spinnerLapangan.selectedItem.toString()
            val jam = spinnerJam.selectedItem.toString()

            if (
                nama.isEmpty() ||
                nomorHP.isEmpty() ||
                tanggalTerpilih.isEmpty()
            ) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Data Belum Lengkap")
                    .setMessage(
                        "Harap isi Nama, Nomor HP, dan pilih tanggal booking!"
                    )
                    .setPositiveButton("OK", null)
                    .show()

                return@setOnClickListener
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Booking Berhasil")
                .setMessage(
                    "Booking atas nama $nama untuk $lapangan " +
                            "pada $tanggalTerpilih ($jam) berhasil disimpan!"
                )
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    private fun hasExactAlarmPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true
        }

        val alarmManager = requireContext()
            .getSystemService(android.content.Context.ALARM_SERVICE)
                as AlarmManager

        return alarmManager.canScheduleExactAlarms()
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return
        }

        try {
            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.parse("package:${requireContext().packageName}")
            )

            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Buka Pengaturan aplikasi dan aktifkan izin alarm tepat.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Pilih Jam Alarm Pengingat")
            .build()

        // Jalankan logika alarm setelah pengguna menekan OK.
        picker.addOnPositiveButtonClickListener {
            if (!isAdded) return@addOnPositiveButtonClickListener

            val alarmCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, picker.hour)
                set(Calendar.MINUTE, picker.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (
                alarmCalendar.timeInMillis <=
                System.currentTimeMillis()
            ) {
                alarmCalendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            val lapanganTerpilih =
                spinnerLapangan.selectedItem?.toString()
                    ?: "Badminton"

            val title = "Pengingat Booking $lapanganTerpilih"
            val message =
                "Jadwal main $lapanganTerpilih kamu sudah dekat! " +
                        "Siapkan raket dan peralatanmu."

            try {
                val alarmBerhasil = AlarmScheduler.scheduleAlarm(
                    requireContext(),
                    alarmCalendar.timeInMillis,
                    title,
                    message
                )

                val timeFormatted = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    picker.hour,
                    picker.minute
                )

                if (alarmBerhasil) {
                    textStatusAlarm.text =
                        "Alarm diatur untuk jam $timeFormatted"

                    Toast.makeText(
                        requireContext(),
                        "Alarm berhasil diatur pada $timeFormatted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    textStatusAlarm.text =
                        "Alarm belum aktif. Periksa izin alarm."

                    Toast.makeText(
                        requireContext(),
                        "Alarm gagal diatur. Periksa izin alarm tepat.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Gagal mengatur alarm: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // PENTING: tampilkan dialog sebelum pengguna menekan OK.
        picker.show(childFragmentManager, "ALARM_TIME_PICKER")
    }
}
