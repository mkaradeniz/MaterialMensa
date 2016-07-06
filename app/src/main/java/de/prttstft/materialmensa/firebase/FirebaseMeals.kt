package de.prttstft.materialmensa.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.prttstft.materialmensa.extras.Analytics
import de.prttstft.materialmensa.model.FirebaseMeal
import de.prttstft.materialmensa.model.Meal
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener
import timber.log.Timber


class FirebaseMeals() {

    companion object {
        val DOWNVOTES = "downvotes"
        val MEALS = "meals"
        val UPVOTES = "upvotes"

        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val mealReference: DatabaseReference = firebaseDatabase.getReference(MEALS)

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        @JvmStatic fun addMealToDatabase(meal: Meal) {
            if (userId != null) {
                mealReference.child(meal.nameEn).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        if (dataSnapshot != null && !dataSnapshot.exists()) {
                            val newMeal: FirebaseMeal = FirebaseMeal(meal)

                            mealReference.child(meal.nameEn).setValue(newMeal, DatabaseReference.CompletionListener { databaseError, ref ->
                                if (databaseError == null) {
                                    Analytics.mealAddedToDatabase()
                                } else {
                                    Timber.e(databaseError.message)
                                }
                            })
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
            if (userId != null) {
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
        }

        @JvmStatic fun getSocialDataMeal(listener: MainFragmentListener, meal: Meal) {
            if (userId != null) {
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

                            socialMeal.hasScores = true

                            listener.addMeal(socialMeal)

                            Analytics.socialMealServed()
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

        @JvmStatic fun setUpSocialListener(listener: MainFragmentListener) {
            if (userId != null) {
                mealReference.addChildEventListener(object : ChildEventListener {
                    override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {
                        if (dataSnapshot != null) {
                            val firebaseMeal: FirebaseMeal = dataSnapshot.getValue(FirebaseMeal::class.java)

                            val meal = mergeMeals(Meal(), firebaseMeal)

                            meal.hasScores = true

                            listener.sendSocialData(meal)
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
        }

        @JvmStatic fun upvoteMeal(mealName: String) {
            if (userId != null) {
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
        }


        private fun addVote(type: String, mealName: String) {
            mealReference.child(mealName).child(type).child(userId).setValue(true, DatabaseReference.CompletionListener { databaseError, ref ->
                if (type == DOWNVOTES) {
                    Analytics.mealdownvoted()
                } else if (type == UPVOTES) {
                    Analytics.mealUpvoted()
                }
            })
        }

        private fun downvote(mealName: String) {
            mealReference.child(mealName).child(DOWNVOTES).child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        removeVote(DOWNVOTES, mealName)
                        removeVote(UPVOTES, mealName)
                    } else {
                        addVote(DOWNVOTES, mealName)
                        removeVote(UPVOTES, mealName)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
        }

        private fun mergeMeals(meal: Meal, firebaseMeal: FirebaseMeal): Meal {
            meal.nameEn = firebaseMeal.nameEn

            if (firebaseMeal.downvotes != null) {
                meal.downvotes = firebaseMeal.downvotes
            }

            if (firebaseMeal.upvotes != null) {
                meal.upvotes = firebaseMeal.upvotes
            }

            meal.score = meal.upvotes.size - meal.downvotes.size

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

            return meal
        }

        private fun removeVote(type: String, mealName: String) {
            mealReference.child(mealName).child(type).child(userId).setValue(null, DatabaseReference.CompletionListener { databaseError, ref ->
                if (databaseError == null) {
                    if (type == DOWNVOTES) {
                        Analytics.mealdownvoteRemoved()
                    } else if (type == UPVOTES) {
                        Analytics.mealUpvoteRemoved()
                    }
                } else {
                    Timber.e(databaseError.message)
                }
            })
        }

        private fun upvote(mealName: String) {
            mealReference.child(mealName).child(UPVOTES).child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        removeVote(DOWNVOTES, mealName)
                        removeVote(UPVOTES, mealName)
                    } else {
                        addVote(UPVOTES, mealName)
                        removeVote(DOWNVOTES, mealName)
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
}