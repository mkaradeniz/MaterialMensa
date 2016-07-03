package de.prttstft.materialmensa.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.comparisons.compareValuesBy

@SuppressWarnings("unused")
class Meal : Comparable<Meal> {

    @SerializedName("allergens") val allergens: List<String> = ArrayList()
    @SerializedName("badges") val badges: List<String> = ArrayList()
    @SerializedName("category") val category: String? = null
    @SerializedName("category_de") val categoryDe: String? = null
    @SerializedName("category_en") val categoryEn: String? = null
    @SerializedName("date") val date: String? = null
    @SerializedName("description_de") val descriptionDe: String? = null
    @SerializedName("description_en") val descriptionEn: String? = null
    @SerializedName("image") val image: String? = null
    @SerializedName("name_de") var nameDe: String? = null
    @SerializedName("name_en") var nameEn: String? = null
    @SerializedName("order_info") val orderInfo: Int = 0
    @SerializedName("priceGuests") val priceGuests: Float = 0.toFloat()
    @SerializedName("priceStudents") val priceStudents: Float = 0.toFloat()
    @SerializedName("priceWorkers") val priceWorkers: Float = 0.toFloat()
    @SerializedName("pricetype") val pricetype: String? = null
    @SerializedName("restaurant") val restaurant: String? = null
    @SerializedName("subcategory") val subcategory: String? = null
    @SerializedName("subcategory_en") val subcategoryEn: String? = null
    @SerializedName("thumbnail") val thumbnail: String? = null
    var customDescription: String? = null
    var isDownvoted: Boolean = false
    var isFiltered: Boolean = false
    var isUpvoted: Boolean = false
    var orderNumber: Int = 0
    var priceString: String? = null
    var score: Int = 0


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