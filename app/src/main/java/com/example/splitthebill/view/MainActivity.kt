package com.example.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.splitthebill.AddBillActivity
import com.example.splitthebill.R
import com.example.splitthebill.fragments.BillsFragment
import com.example.splitthebill.fragments.PartiesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this, R.color.top)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        val addBillButton = findViewById<FloatingActionButton>(R.id.addBillButton)
        addBillButton.setOnClickListener{
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
        }
        if (savedInstanceState == null) {
            switchFragment(BillsFragment())
            Log.d("MainActivity", "URA")
        }
        bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_bills -> {
                    switchFragment(BillsFragment())
                    true
                }
                R.id.nav_parties -> {
                    switchFragment(PartiesFragment())
                    true
                }
                else -> false
            }
        }
    }
    fun switchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}