package com.sil.ecohero

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sil.ecohero.databinding.ItemRowJenisBinding

class ListTrashAdapter(private val listTrash: ArrayList<Trash>): RecyclerView.Adapter<ListTrashAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemRowJenisBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowJenisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo, description) = listTrash[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.binding.trashPhoto)
        holder.binding.trashName.text = name
        holder.binding.trashDescription.text = description
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailTrashActivity::class.java)
            intentDetail.putExtra("key_trash", listTrash[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }

    }

    override fun getItemCount(): Int = listTrash.size

}