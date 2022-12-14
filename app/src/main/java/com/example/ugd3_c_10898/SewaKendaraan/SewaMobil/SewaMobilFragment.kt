package com.example.ugd3_c_10898.SewaKendaraan.SewaMobil

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_c_10898.HomeActivity
import com.example.ugd3_c_10898.R
import com.example.ugd3_c_10898.api.TubesApi
import com.example.ugd3_c_10898.databinding.FragmentSewaMobilBinding


import com.example.ugd3_c_10898.room.user.UserDB

import com.example.ugd3_c_10898.models.SewaMobil
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class SewaMobilFragment : Fragment() {
    private  val Jenis_Mobil_list = arrayOf("Matic", "coupling", "listrik")

    // Code Room untuk Users
    val db by lazy { UserDB(this.requireActivity()) }
    private var _binding: FragmentSewaMobilBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val noticationId1 = 101
    private val noticationId2 = 102
    private var queue: RequestQueue? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSewaMobilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(requireContext())
        val btn: Button = view.findViewById(R.id.btnTambah)
        val cek: Button = view.findViewById(R.id.CekPesanan)
        setExposedDropdownMenu()
        btn.setOnClickListener {
            CreateSewa()
        }
        cek.setOnClickListener{
            (activity as HomeActivity).changeFragment(RVShowPemesananMobil())
        }

    }

//  Create Sewa Mobil
    private fun CreateSewa(){

        val sewa = SewaMobil(
            binding.inputLokasi.text.toString(), binding.inputTanggalPinjam.text.toString(),
            binding.inputTanggalKembali.text.toString(), binding.inputMerkMobil.text.toString(),
            binding.inputJenisMobil.text.toString(),
            binding.inputJumlahKursi.text.toString()

        )

    val stringRequest: StringRequest =
        object : StringRequest(Method.POST, TubesApi.createSewa, Response.Listener { response ->

                Toast.makeText(context, JSONObject(response).getString("message"), Toast.LENGTH_SHORT).show()
            createNotificationChanel()
            sendNotification()
            (activity as HomeActivity).changeFragment(RVShowPemesananMobil())
        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors = JSONObject(responseBody)
                Toast.makeText(
                    requireContext(),
                    errors.getString("message"),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(sewa)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
    queue!!.add(stringRequest)
    }

    fun setExposedDropdownMenu(){
        val adapterJenis: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            R.layout.item_list, Jenis_Mobil_list)
        binding.inputJenisMobil.setAdapter(adapterJenis)
    }


    private fun createNotificationChanel(){
        val name = "Notification Title"
        val descriptionText = "Notification Description"

        val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }
        val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }

        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel1)
        notificationManager.createNotificationChannel(channel2)
    }

    private fun sendNotification() {
        val SUMMARY_ID = 0
        val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"

        val newMessageNotification1 = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_mail_24)
            .setContentTitle("Jasa Cycle Fast")
            .setContentText("Selamat Anda Berhasil Melakukan Pemesanan")
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val newMessageNotification2 = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_mail_24)
            .setContentTitle("Deni Sumargo")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "Untuk Pemesanan ini segera menyertakan identitas lewat nomor 084443766577 ya agar segera diverivikasi"
                    )
            )
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()
        val summaryNotification = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID_1)
            .setContentTitle("Pesan Penting")
            //set content text to support devices running API level < 24
            .setContentText("Two new messages")
            .setSmallIcon(R.drawable.ic_mail_24)
            //build summary info into InboxStyle template
            .setStyle(NotificationCompat.InboxStyle()
                .setBigContentTitle("2 new messages")
                .setSummaryText("JasaFast@gmail.com"))
            //specify which group this notification belongs to
            .setGroup(GROUP_KEY_WORK_EMAIL)
            //set this notification as the summary for the group
            .setGroupSummary(true)
            .setColor(Color.RED)
            .build()

        NotificationManagerCompat.from(this.requireContext()).apply {
            notify(noticationId1, newMessageNotification1)
            notify(noticationId2, newMessageNotification2)
            notify(SUMMARY_ID, summaryNotification)
        }
    }
}