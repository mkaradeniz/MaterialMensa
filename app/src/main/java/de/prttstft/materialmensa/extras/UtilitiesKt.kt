package de.prttstft.materialmensa.extras

import de.prttstft.materialmensa.model.Meal


open class UtilitiesKt() {

    companion object {

        @JvmStatic fun sortMeals(meals: List<Meal>): MutableList<Meal> {
            return meals.sorted().toMutableList()
        }

    }
}