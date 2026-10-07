package com.example.bugtobiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.bugtobiz.databinding.ActivityOpportunityDetailBinding

class OpportunityDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpportunityDetailBinding
    private lateinit var opportunity: Opportunity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOpportunityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        val item = MockOpportunities.items.find { it.id == id }

        if (item == null) {
            Toast.makeText(this, R.string.opportunity_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val isInvestigating = savedInstanceState?.getBoolean(STATE_INVESTIGATING)
            ?: intent.getBooleanExtra(EXTRA_INVESTIGATING, false)
        opportunity = item.copy(isInvestigating = isInvestigating)

        binding.category.text = opportunity.category
        binding.title.text = opportunity.title
        binding.audience.text = opportunity.audience
        binding.problem.text = opportunity.problem
        binding.solution.text = opportunity.solution
        binding.firstVersion.text = opportunity.firstVersion

        val note = opportunity.note?.takeIf { it.isNotBlank() }
        binding.noteCard.isVisible = note != null
        binding.note.text = note.orEmpty()

        binding.backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.investigateButton.setOnClickListener {
            opportunity = opportunity.copy(isInvestigating = !opportunity.isInvestigating)
            updateInvestigationViews()
            updateResult()
            Toast.makeText(this,
                if (opportunity.isInvestigating) R.string.added_to_investigation
                else R.string.removed_from_investigation,
                Toast.LENGTH_SHORT).show()
        }
        updateInvestigationViews()
        updateResult()

        val padding = resources.getDimensionPixelSize(R.dimen.screen_padding)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(padding + bars.left, padding + bars.top,
                padding + bars.right, padding + bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun updateInvestigationViews() {
        binding.investigationStatus.setText(
            if (opportunity.isInvestigating) R.string.investigating else R.string.idea_to_explore
        )
        binding.investigateButton.setText(
            if (opportunity.isInvestigating) R.string.remove_investigation
            else R.string.start_investigation
        )
    }

    private fun updateResult() {
        val result = Intent()
            .putExtra(EXTRA_ID, opportunity.id)
            .putExtra(EXTRA_INVESTIGATING, opportunity.isInvestigating)
        setResult(Activity.RESULT_OK, result)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::opportunity.isInitialized) {
            outState.putBoolean(STATE_INVESTIGATING, opportunity.isInvestigating)
        }
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val EXTRA_ID = "opportunity_id"
        const val EXTRA_INVESTIGATING = "is_investigating"
        private const val STATE_INVESTIGATING = "investigating"
    }
}
