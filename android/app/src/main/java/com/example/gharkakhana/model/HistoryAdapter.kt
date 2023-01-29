package com.example.gharkakhana.model

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.gharkakhana.databinding.ItemTravelHistoryBinding
import java.net.BindException

class HistoryAdapter(private val list:List<Transaction>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    val differ= AsyncListDiffer(this,object : DiffUtil.ItemCallback<Transaction>(){
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem._id==newItem._id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    })

    class ViewHolder(private val binding: ItemTravelHistoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction){
            Log.d("boarding",transaction.boarding)
            binding.sourceTxt.text = transaction.boarding
            binding.destText.text = transaction.destination
            binding.amountTxt.text = "-${transaction.amount}"
        }
        companion object{
            fun from (parent:ViewGroup):ViewHolder{
                return ViewHolder(ItemTravelHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = differ.currentList[position]
        holder.bind(transaction)
    }

    override fun getItemCount() = differ.currentList.size
}