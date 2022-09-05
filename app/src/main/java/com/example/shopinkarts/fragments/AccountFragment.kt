package com.example.shopinkarts.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.*
import com.example.shopinkarts.databinding.FragmentAccountBinding

class   AccountFragment : Fragment() {
    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        binding.myTransactionTV.setOnClickListener {
            val intent = Intent(context, MyTransactionActivity::class.java)
            this.startActivity(intent)
        }

       /* binding.accountHeader.nameTV.text = resources.getString(R.string.profile)
        binding.accountHeader.headerCL.setBackgroundResource(R.drawable.background_white)
*/
        binding.refundPolicyTV.setOnClickListener {
            val intent = Intent(context, RefundPolicyActivity::class.java)
            this.startActivity(intent)
        }

        binding.addressesTV.setOnClickListener {
            val intent = Intent(context, com.example.shopinkarts.activity.AddressesActivity::class.java)
            this.startActivity(intent)
        }
        binding.customerSupportTV.setOnClickListener {
            val intent = Intent(context, com.example.shopinkarts.activity.CustomerSupportActivity::class.java)
            this.startActivity(intent)
        }
        binding.aboutTV.setOnClickListener {
            val intent = Intent(context, com.example.shopinkarts.activity.AboutUsActivity::class.java)
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

        return binding.root
    }

}