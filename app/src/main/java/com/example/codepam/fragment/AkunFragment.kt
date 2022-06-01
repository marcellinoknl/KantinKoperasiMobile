package com.example.codepam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.codepam.R
import com.example.codepam.helper.SharedPref
import org.w3c.dom.Text

class AkunFragment : Fragment() {
    lateinit var s:SharedPref
    lateinit var btnLogout:Button
    lateinit var tvNama:TextView
    lateinit var tvEmail:TextView
    lateinit var tvKTP:TextView
    lateinit var tvNoHp:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_akun,container,false)
        init(view)

        s = SharedPref(requireActivity())

        btnLogout.setOnClickListener{
            s.setStatusLogin(false)
        }
        setData()

        return view
    }

    fun setData() {

        if (s.getUser() == null) {

            return
        }

        val user = s.getUser()!!

        tvNama.text = user.nama_lengkap
        tvEmail.text = user.email
        tvKTP.text = user.ktp
        tvNoHp.text =user.nohp

    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        tvKTP = view.findViewById(R.id.tv_ktp)
        tvNoHp = view.findViewById(R.id.tv_hp)
        tvEmail = view.findViewById(R.id.tv_email)

    }
}