package de.prttstft.materialmensa.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.prttstft.materialmensa.model.FirebaseMeal
import de.prttstft.materialmensa.model.Meal
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener
import timber.log.Timber
import java.util.*

class FirebaseMeals() {

    companion object {
        val DOWNVOTES = "downvotes"
        val MEALS = "meals"
        val UPVOTES = "upvotes"

        var mealCount = 0;

        var socialMeals = ArrayList<Meal>()

        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val mealReference: DatabaseReference = firebaseDatabase.getReference(MEALS)

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        @JvmStatic fun addMealToDatabase(meal: Meal) {
            if (meal.nameEn != null) {
                mealReference.child(meal.nameEn).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        if (dataSnapshot != null && !dataSnapshot.exists()) {
                            addMeal(meal)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError?) {
                        if (databaseError != null) {
                            Timber.e(databaseError.message)
                        }
                    }
                })
            }

        }

        @JvmStatic fun downvoteMeal(mealName: String) {
            mealReference.child(mealName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        downvote(mealName)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
        }

        @JvmStatic fun getSocialData(listener: MainFragmentListener, meals: List<Meal>) {
            updateMealWithSocial(listener, meals)
        }

        @JvmStatic fun getSocialDataMeal(listener: MainFragmentListener, meal: Meal) {
            mealReference.child(meal.nameEn).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        val firebaseMeal: FirebaseMeal = dataSnapshot.getValue(FirebaseMeal::class.java)
                        val socialMeal = mergeMeals(meal, firebaseMeal)

                        socialMeal.score = socialMeal.upvotes.size - socialMeal.downvotes.size

                        if (socialMeal.downvotes[userId] == true) {
                            socialMeal.isDownvoted = true
                            socialMeal.isUpvoted = false
                        } else if (socialMeal.upvotes[userId] == true) {
                            socialMeal.isDownvoted = false
                            socialMeal.isUpvoted = true
                        } else {
                            socialMeal.isDownvoted = false
                            socialMeal.isUpvoted = false
                        }

                        listener.addMeal(socialMeal)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
        }

        @JvmStatic fun processMealList(listener: MainFragmentListener, meals: List<Meal>) {
            mealCount = meals.size

            for (meal in meals) {
                mealReference.child(meal.nameEn).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        if (dataSnapshot != null && dataSnapshot.exists()) {
                            val firebaseMeal: FirebaseMeal = dataSnapshot.getValue(FirebaseMeal::class.java)
                            val socialMeal = mergeMeals(meal, firebaseMeal)

                            socialMeal.score = socialMeal.upvotes.size - socialMeal.downvotes.size

                            if (socialMeal.downvotes[userId] == true) {
                                socialMeal.isDownvoted = true
                                socialMeal.isUpvoted = false
                            } else if (socialMeal.upvotes[userId] == true) {
                                socialMeal.isDownvoted = false
                                socialMeal.isUpvoted = true
                            } else {
                                socialMeal.isDownvoted = false
                                socialMeal.isUpvoted = false
                            }

                            addToSocialMeals(listener, socialMeal)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError?) {
                        if (databaseError != null) {
                            Timber.e(databaseError.message)
                        }
                    }
                })
            }
        }

        private fun addToSocialMeals(listener: MainFragmentListener, meal: Meal) {
            socialMeals.add(meal)

            if (socialMeals.size == mealCount) {
                Timber.d("Hi from addToSocialMeals")


            }

        }

        private fun mergeMeals(meal: Meal, firebaseMeal: FirebaseMeal): Meal {
            if (firebaseMeal.downvotes != null) {
                meal.downvotes = firebaseMeal.downvotes
            }

            if (firebaseMeal.upvotes != null) {
                meal.upvotes = firebaseMeal.upvotes
            }

            return meal
        }

        @JvmStatic fun upvoteMeal(mealName: String) {
            mealReference.child(mealName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        upvote(mealName)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
        }


        private fun addMeal(meal: Meal) {
            val newMeal: FirebaseMeal = FirebaseMeal(meal.nameDe, meal.nameEn)

            mealReference.child(meal.nameEn).setValue(newMeal, DatabaseReference.CompletionListener { databaseError, ref ->
                if (databaseError != null) {
                    Timber.e(databaseError.message)
                }
            })
        }

        private fun checkIfUserVoted(listener: MainFragmentListener, firebaseMeal: FirebaseMeal) {
            val meal: Meal = Meal()
            meal.nameEn = firebaseMeal.nameEn

            if (firebaseMeal.downvotes != null) {
                if (firebaseMeal.downvotes[userId] == true) {
                    meal.isDownvoted = true
                    meal.isUpvoted = false
                } else {
                    meal.isDownvoted = false
                }
            }

            if (firebaseMeal.upvotes != null) {
                if (firebaseMeal.upvotes[userId] == true) {
                    meal.isDownvoted = false
                    meal.isUpvoted = true
                } else {
                    meal.isUpvoted = false
                }
            }

            listener.sendUserVote(meal)
        }

        private fun downvote(mealName: String) {
            mealReference.child(mealName).child(DOWNVOTES).child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        unvote(DOWNVOTES, mealName)
                        unvote(UPVOTES, mealName)
                    } else {
                        vote(DOWNVOTES, mealName)
                        unvote(UPVOTES, mealName)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
        }

        private fun unvote(type: String, mealName: String) {
            mealReference.child(mealName).child(type).child(userId).setValue(null, DatabaseReference.CompletionListener { databaseError, ref ->
                if (databaseError != null) {
                    Timber.e(databaseError.message)
                }
            })
        }

        private fun updateMealWithSocial(listener: MainFragmentListener, meals: List<Meal>) {
            for (meal in meals) {
                val mealName = meal.nameEn

                mealReference.child(mealName).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        if (dataSnapshot != null && dataSnapshot.exists()) {
                            val firebaseMeal: FirebaseMeal = dataSnapshot.getValue(FirebaseMeal::class.java)

                            var downvotes: kotlin.Int = 0
                            var upvotes: kotlin.Int = 0

                            if (firebaseMeal.downvotes != null) {
                                downvotes = firebaseMeal.downvotes.size
                            }

                            if (firebaseMeal.upvotes != null) {
                                upvotes = firebaseMeal.upvotes.size
                            }

                            meal.score = upvotes - downvotes

                            checkIfUserVoted(listener, firebaseMeal)

                            listener.addMeal(meal)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError?) {
                        if (databaseError != null) {
                            Timber.e(databaseError.message)
                        }
                    }
                })
            }

            mealReference.addChildEventListener(object : ChildEventListener {
                override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {
                    if (dataSnapshot != null) {
                        val firebaseMeal: FirebaseMeal = dataSnapshot.getValue(FirebaseMeal::class.java)

                        var downvotes: kotlin.Int = 0
                        var upvotes: kotlin.Int = 0

                        if (firebaseMeal.downvotes != null) {
                            downvotes = firebaseMeal.downvotes.size
                        }

                        if (firebaseMeal.upvotes != null) {
                            upvotes = firebaseMeal.upvotes.size
                        }

                        val meal = Meal()

                        meal.nameEn = firebaseMeal.nameEn
                        meal.score = upvotes - downvotes

                        checkIfUserVoted(listener, firebaseMeal)

                        listener.sendMealScore(meal)
                    }
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot?, s: String?) {

                }

                override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot?) {

                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    Timber.e(databaseError?.message)
                }
            })
        }

        private fun upvote(mealName: String) {
            mealReference.child(mealName).child(UPVOTES).child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        unvote(DOWNVOTES, mealName)
                        unvote(UPVOTES, mealName)
                    } else {
                        vote(UPVOTES, mealName)
                        unvote(DOWNVOTES, mealName)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
        }

        private fun vote(type: String, mealName: String) {
            mealReference.child(mealName).child(type).child(userId).setValue(true, DatabaseReference.CompletionListener { databaseError, ref ->
                if (databaseError != null) {
                    Timber.e(databaseError.message)
                }
            })
        }
    }
}