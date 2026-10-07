package com.example.bugtobiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bugtobiz.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: OpportunityAdapter
    private var opportunities = MockOpportunities.items

    // Recebe da segunda tela o ID e o novo status da oportunidade.
    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val id = data?.getStringExtra(OpportunityDetailActivity.EXTRA_ID)
            val isInvestigating = data?.getBooleanExtra(
                OpportunityDetailActivity.EXTRA_INVESTIGATING, false
            ) ?: false

            opportunities = opportunities.map { item ->
                if (item.id == id) item.copy(isInvestigating = isInvestigating) else item
            }
            adapter.updateItems(opportunities)
            updateSummary()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Preserva as escolhas se o Android recriar esta tela, por exemplo ao girar.
        val selectedIds = savedInstanceState?.getStringArrayList(STATE_SELECTED_IDS).orEmpty()
        opportunities = MockOpportunities.items.map {
            it.copy(isInvestigating = it.id in selectedIds)
        }

        adapter = OpportunityAdapter(opportunities) { item ->
            val intent = Intent(this, OpportunityDetailActivity::class.java)
            intent.putExtra(OpportunityDetailActivity.EXTRA_ID, item.id)
            intent.putExtra(OpportunityDetailActivity.EXTRA_INVESTIGATING, item.isInvestigating)
            detailLauncher.launch(intent)
        }
        binding.opportunityList.layoutManager = LinearLayoutManager(this)
        binding.opportunityList.adapter = adapter
        updateSummary()

        val padding = resources.getDimensionPixelSize(R.dimen.screen_padding)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(padding + bars.left, padding + bars.top,
                padding + bars.right, padding + bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun updateSummary() {
        val count = opportunities.count { it.isInvestigating }
        binding.opportunityCount.text = resources.getQuantityString(
            R.plurals.opportunity_count, opportunities.size, opportunities.size
        )
        binding.investigationCount.text = getString(R.string.investigation_count, count)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(STATE_SELECTED_IDS, ArrayList(
            opportunities.filter { it.isInvestigating }.map { it.id }
        ))
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val STATE_SELECTED_IDS = "selected_ids"
    }
}
