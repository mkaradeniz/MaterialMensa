@file:JvmName("Meal")
package de.prttstft.materialmensa.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.comparisons.compareValuesBy

@SuppressWarnings("unused")
class Meal : Comparable<Meal> {

    @SerializedName("allergens") val allergens: List<String> = ArrayList()
    @SerializedName("badges") val badges: List<String> = ArrayList()
    @SerializedName("category") val category: String = ""
    @SerializedName("category_de") val categoryDe: String = ""
    @SerializedName("category_en") val categoryEn: String = ""
    @SerializedName("date") val date: String = ""
    @SerializedName("description_de") val descriptionDe: String = ""
    @SerializedName("description_en") val descriptionEn: String = ""
    @SerializedName("image") val image: String = ""
    @SerializedName("name_de") var nameDe: String = ""
    @SerializedName("name_en") var nameEn: String = ""
    @SerializedName("order_info") val orderInfo: Int = 0
    @SerializedName("priceGuests") val priceGuests: Float = 0.toFloat()
    @SerializedName("priceStudents") val priceStudents: Float = 0.toFloat()
    @SerializedName("priceWorkers") val priceWorkers: Float = 0.toFloat()
    @SerializedName("pricetype") val pricetype: String = ""
    @SerializedName("restaurant") val restaurant: String = ""
    @SerializedName("subcategory") val subcategory: String = ""
    @SerializedName("subcategory_en") val subcategoryEn: String = ""
    @SerializedName("thumbnail") val thumbnail: String = ""
    var customDescription: String = ""
    var downvotes: HashMap<String, Any> = HashMap()
    var isDownvoted: Boolean = false
    var isFiltered: Boolean = false
    var isUpvoted: Boolean = false
    var orderNumber: Int = 0
    var priceString: String = ""
    var score: Int = 0
    var upvotes: HashMap<String, Any> = HashMap()
    var hasScores: Boolean = false


    val badge: String
        get() {
            if (badges.size > 0) {
                return badges[0]
            } else {
                return ""
            }
        }

    override fun compareTo(other: Meal): Int {
        return compareValuesBy(this, other, { it.customSort() })
    }

    private fun customSort(): Int {
        if (isUpvoted) {
            return -10000000 - score
        }

        if (isDownvoted) {
            return +10000000 - score
        }

        if (score > 0) {
            return score * (-1)
        }

        if (score < 0) {
            return score * (-1)
        }

        return orderNumber
    }
}