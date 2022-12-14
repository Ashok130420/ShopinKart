package com.app.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.classes.InAppUpdate
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityDashBoardBinding
import com.app.shopinkarts.fragments.AccountFragment
import com.app.shopinkarts.fragments.CategoriesFragment
import com.app.shopinkarts.fragments.HomeFragment
import com.app.shopinkarts.fragments.OrdersFragment
import com.app.shopinkarts.model.CartModel
import com.app.shopinkarts.response.Notification


class DashBoardActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashBoardBinding
    private val homeFragment = HomeFragment()
    private val categoriesFragment = CategoriesFragment()
    private val ordersFragment = OrdersFragment()
    private val accountFragment = AccountFragment()
    lateinit var sharedPreference: SharedPreference
    private lateinit var inAppUpdate: InAppUpdate

    private var backPressedTime: Long = 0

    companion object {
        var profile = ""
        var mInstance: DashBoardActivity = DashBoardActivity()
        var arrayListCart: ArrayList<CartModel> = ArrayList()
        var selectedVIDs: ArrayList<String> = ArrayList()

        var arrayListNotifications: ArrayList<Notification> = ArrayList()

        fun getInstance(): DashBoardActivity {
            return mInstance
        }
    }

    override fun onResume() {

        if (profile.isEmpty()) {
            binding.headerDashBoard.profileIV.setBackgroundResource(R.drawable.white_blue_logo)
        } else {
            Glide.with(this).load(profile).into(binding.headerDashBoard.profileIV)
        }

        arrayListCart
        Log.d("arrayListCart", arrayListCart.toString())
        if (arrayListCart.isNotEmpty()) {
            binding.headerDashBoard.cartItemTV.visibility = View.VISIBLE
            binding.headerDashBoard.cartItemTV.text = arrayListCart.size.toString()
        } else {
            binding.headerDashBoard.cartItemTV.visibility = View.GONE
        }

        super.onResume()
        inAppUpdate.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)

        sharedPreference = SharedPreference(this)

        sharedPreference.getArray()
        inAppUpdate = InAppUpdate(this)

        mInstance = this

//           val window: Window = this.window
//           window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//           window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//           window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        binding.headerDashBoard.notificationIV.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.headerDashBoard.cartIV.setOnClickListener {

            for (element in arrayListCart) {
                Log.d("arrayListCartDash", element.toString())
            }
            val i = Intent(this, ProductCartActivity::class.java)
            startActivity(i)
        }


        if (arrayListNotifications.isEmpty()) {
            binding.headerDashBoard.notificationItemTV.visibility = View.GONE
        } else {
            binding.headerDashBoard.notificationItemTV.visibility = View.VISIBLE
        }

        // default fragment loads
        replaceFragment(homeFragment)

        binding.headerDashBoard.profileIV.setOnClickListener {
            binding.navBottomMenu.selectedItemId = R.id.bottomAccount
        }

        // Bottom Navigation menus
        binding.headerDashBoard.nameTV.text = resources.getString(R.string.shopinkarts)
        binding.headerDashBoard.profileIV.visibility = View.VISIBLE
        binding.navBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottomHome -> {
                    replaceFragment(homeFragment)
                    binding.headerDashBoard.nameTV.text = resources.getString(R.string.shopinkarts)
                    binding.headerDashBoard.profileIV.visibility = View.VISIBLE
                }
                R.id.bottomCategories -> {
                    replaceFragment(categoriesFragment)
                    binding.headerDashBoard.nameTV.text = resources.getString(R.string.categories)
                    binding.headerDashBoard.profileIV.visibility = View.VISIBLE
                }
                R.id.bottomOrders -> {
                    replaceFragment(ordersFragment)
                    binding.headerDashBoard.nameTV.text = resources.getString(R.string.my_orders)
                    binding.headerDashBoard.profileIV.visibility = View.VISIBLE
                }
                R.id.bottomAccount -> {
                    replaceFragment(accountFragment)
                    binding.headerDashBoard.nameTV.text = resources.getString(R.string.profile)
                    binding.headerDashBoard.profileIV.visibility = View.GONE
                }
            }
            true
        }

        intent.extras?.let {
            if (it.getString("from") == "myOrder") {
                myOrders()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "categories") {
                categories()
            }
        }


    }

    // Replace Fragment on FrameLayout
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutDashBoard, fragment)
        transaction.commit()
    }

    private fun myOrders() {
        binding.navBottomMenu.selectedItemId = R.id.bottomOrders
    }

    private fun categories() {
        binding.navBottomMenu.selectedItemId = R.id.bottomCategories
    }

    // exit from app on double click
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.finish()
            finish()
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}