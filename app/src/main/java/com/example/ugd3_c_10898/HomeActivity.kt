package com.example.ugd3_c_10898

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ugd3_c_10898.Profil.ProfileFragment
import com.example.ugd3_c_10898.SewaKendaraan.SewaKendaraanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class   HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getSupportActionBar()?.hide()

        val nav : BottomNavigationView = findViewById(R.id.bottom_navigation)


        changeFragment(HomeFragment())

        nav.setOnItemSelectedListener{item -> when(item.itemId){
                R.id.home->{
                    changeFragment(HomeFragment())
                    true
                }
                R.id.shopping->{
                    changeFragment(SewaKendaraanFragment())
                    true
                }
                R.id.qrCode->{
                    changeFragment(ScannerFragment())
                    true
                }
                R.id.Location->{
                    changeFragment(LocationFragment())
                    true
                }
                R.id.profile->{
                    changeFragment(ProfileFragment())
                    true
                }
                else->false
            }
        }
    }

    fun changeFragment(fragment: Fragment){
        if(fragment !=null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@HomeActivity)
            builder.setMessage("Are you sure want to exit?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface, i: Int) {
                        finishAndRemoveTask()
                    }
                })
                .show()


        }
        return super.onOptionsItemSelected(item)
    }
}