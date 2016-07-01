package de.prttstft.materialmensa.extras

import de.prttstft.materialmensa.model.Meal
import kotlin.comparisons.compareBy


open class UtilitiesKt() {

    companion object {

        @JvmStatic fun sortMeals(meals: List<Meal>): List<Meal> {
            return meals.sortedWith(compareBy({ it.isUpvoted }, { it.score }, { it.orderNumber }))
        }
    }
}