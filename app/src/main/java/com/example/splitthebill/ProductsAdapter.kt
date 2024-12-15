package com.example.splitthebill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.data.ProductData

class ProductsAdapter(private val dataSet: MutableList<ProductData>) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val editProductName: EditText = view.findViewById(R.id.editProductName)
        private val editProductCount: EditText = view.findViewById(R.id.editProductCount)
        private val editProductPrice: EditText = view.findViewById(R.id.editProductPrice)

        fun bind(productData : ProductData) {
            editProductName.setText(productData.name)
            editProductCount.setText(productData.count)
            editProductPrice.setText(productData.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.product_edit_item, parent, false)
        return ProductViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = dataSet[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun addProduct() {
        dataSet.add(ProductData())
        notifyItemInserted(dataSet.size-1) // Уведомляем адаптер о том, что данные обновились
    }
}
