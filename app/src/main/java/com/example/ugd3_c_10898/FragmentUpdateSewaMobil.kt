package com.example.ugd3_c_10898

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.ugd3_c_10898.room.mobil.SewaMobil
//import com.example.ugd3_c_10898.room.User.Use
import com.example.ugd3_c_10898.room.user.User
import com.example.ugd3_c_10898.room.user.UserDB


class FragmentUpdateSewaMobil : Fragment() {

    val db by lazy { UserDB(this.requireActivity()) }
    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_sewa_mobil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        val btn: Button = view.findViewById(R.id.btnUpdateSewa)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val btnDelete : Button = view.findViewById(R.id.btnDeleteSewa)

        val lokasiEdit : TextView =  view.findViewById(R.id.inputLokasi)
        val tanggalPinjamEdit : TextView = view.findViewById(R.id.inputTanggalPinjam)
        val tanggalKembaliEdit : TextView = view.findViewById(R.id.inputTanggalKembali)
        val modelKendaraanEdit : TextView = view.findViewById(R.id.inputModelKendaraan)

        val id: Int = requireArguments().getInt("id")
//        arguments.let {
//
//        }

        val sewaMobil = db.SewaMobilDao().getDataSewaMobil(id)

        lokasiEdit.setText(sewaMobil.lokasi)
        tanggalPinjamEdit.setText(sewaMobil.tanggalPinjam)
        tanggalKembaliEdit.setText(sewaMobil.tanggalKembali)
        modelKendaraanEdit.setText(sewaMobil.modelKendaraan)

        btn.setOnClickListener {
            db.SewaMobilDao().updateSewaMobil(SewaMobil(id,lokasiEdit.text.toString(),tanggalPinjamEdit.text.toString(),tanggalKembaliEdit.text.toString(),modelKendaraanEdit.text.toString()))
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

        btnBack.setOnClickListener {
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }

        btnDelete.setOnClickListener {
            db.SewaMobilDao().deleteSewaMobil(SewaMobil(id,lokasiEdit.text.toString(),tanggalPinjamEdit.text.toString(),tanggalKembaliEdit.text.toString(),modelKendaraanEdit.text.toString()))
            (activity as HomeActivity).changeFragment(rv_show_pemesanan())
        }
    }
}