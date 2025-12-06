package com.example.chillout.presentation.screen.main.history


data class PurchaseHistory(
    val id: String,
    val name: String,
    val price: Int,
    val categoryName: String,
    val datalock: String
)
fun getInitialHistoryPurchases(): List<PurchaseHistory> {
    return listOf(
        PurchaseHistory(
            id = "p1",
            name = "Костюм",
            price = 15000,
            categoryName = "green",
            datalock = "—"
        ),
        PurchaseHistory(
            id = "p2",
            name = "Машина",
            price = 1_500_000,
            categoryName = "blue",
            datalock = "05.12.2025"
        ),
        PurchaseHistory(
            id = "p3",
            name = "Шкаф",
            price = 25000,
            categoryName = "red",
            datalock = "—"
        ),
        PurchaseHistory(
            id = "p4",
            name = "Ноутбук",
            price = 60000,
            categoryName = "green",
            datalock = "—"
        )
    )
}