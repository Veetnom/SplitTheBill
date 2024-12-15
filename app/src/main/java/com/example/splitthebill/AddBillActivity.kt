package com.example.splitthebill

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.data.ProductData
import com.example.splitthebill.data.ProductsDao
import com.example.splitthebill.data.ProductsDataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBillActivity : AppCompatActivity() {
    private lateinit var adapter: ProductsAdapter
    private val product = mutableListOf<ProductData>()
    private lateinit var productsDb: ProductsDataBase
    private lateinit var productsDao: ProductsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_bill)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_products)
        val addRowButton: ImageView = findViewById(R.id.addProduct)
        val continueButton: Button = findViewById(R.id.continueButton)

        productsDb = ProductsDataBase.getDb(applicationContext)
        productsDao = productsDb.prouctsDao()
        // Настройка RecyclerView
        adapter = ProductsAdapter(product)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Добавление нового ряда
        addRowButton.setOnClickListener {
            adapter.addProduct()
        }
        continueButton.setOnClickListener {
            saveData()
        }
    }
    private fun saveData() {
        // Сохранение всех продуктов в базе данных
        CoroutineScope(Dispatchers.IO).launch {
            product.forEach {
                productsDao.insertProduct(it)
            }
        }
    }
}