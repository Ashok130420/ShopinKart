package com.example.shopinkarts.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.*
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var sharedPreference: SharedPreference

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        sharedPreference = SharedPreference(requireContext())

        binding.myTransactionTV.setOnClickListener {
            val intent = Intent(context, MyTransactionActivity::class.java)
            this.startActivity(intent)
        }

        binding.refundPolicyTV.setOnClickListener {
            val intent = Intent(context, RefundPolicyActivity::class.java)
            this.startActivity(intent)
        }

        binding.addressesTV.setOnClickListener {
            val intent = Intent(context, AddressesActivity::class.java)
            this.startActivity(intent)
        }
        binding.customerSupportTV.setOnClickListener {
            val intent = Intent(context, CustomerSupportActivity::class.java)
            this.startActivity(intent)
        }
        binding.aboutTV.setOnClickListener {
            val intent = Intent(context, AboutUsActivity::class.java)
            this.startActivity(intent)
        }
        binding.termsConditionsTV.setOnClickListener {
            val intent = Intent(context, TermsConditionsActivity::class.java)
            this.startActivity(intent)
        }
        binding.privacyPolicyTV.setOnClickListener {
            val intent = Intent(context, PrivacyPolicyActivity::class.java)
            this.startActivity(intent)
        }

        val phone = sharedPreference.getPhoneNo()

        Log.d("TAG_phone", "onCreateView: $phone")

        binding.phoneNumberTV.text = "+91-${sharedPreference.getPhoneNo()}"

        binding.logOutTV.setOnClickListener {
            sharedPreference.isLoginSet(false)
            sharedPreference.clear()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.profileIV.setOnClickListener {
            launchGallery()
        }


        return binding.root
    }

    //  profile image
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            Glide.with(this).load(filePath).into(binding.profileIV)

        }

    }
}