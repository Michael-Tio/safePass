package com.tiogroup.safepass.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.databinding.CardItemBinding
import com.tiogroup.safepass.ui.detail.DetailActivity

class HomeAdapter(private val items: List<Password>) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class HomeViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pass: Password) {
        binding.txtTitle.text = pass.title
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, DetailActivity::class.java)
            intent.putExtra("PASSWORD_ID", pass.id)
            binding.root.context.startActivity(intent)
        }
    }
}
