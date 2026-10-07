package com.example.bugtobiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bugtobiz.databinding.ItemOpportunityBinding

class OpportunityAdapter(
    private var items: List<Opportunity>,
    private val onItemClick: (Opportunity) -> Unit
) : RecyclerView.Adapter<OpportunityAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOpportunityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOpportunityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding

        binding.category.text = item.category
        binding.title.text = item.title
        binding.audience.text = item.audience
        binding.summary.text = item.summary
        binding.status.setText(
            if (item.isInvestigating) R.string.investigating else R.string.explore_opportunity
        )
        binding.root.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(updatedItems: List<Opportunity>) {
        val previousItems = items
        items = updatedItems
        // A lista tem os mesmos quatro itens; atualizamos só os cartões alterados.
        items.forEachIndexed { index, item ->
            if (item != previousItems[index]) notifyItemChanged(index)
        }
    }
}
