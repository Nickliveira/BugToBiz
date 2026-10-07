package com.example.bugtobiz

data class Opportunity(
    val id: String,
    val title: String,
    val audience: String,
    val category: String,
    val summary: String,
    val problem: String,
    val solution: String,
    val firstVersion: String,
    val note: String? = null,
    val isInvestigating: Boolean = false
)
