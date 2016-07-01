package de.prttstft.materialmensa.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.prttstft.materialmensa.model.FirebaseMeal
import de.prttstft.materialmensa.model.Meal
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener
import timber.log.Timber

class FirebaseMeals() {

    companion object {
        val DOWNVOTES = "downvotes";
        val MEALS = "meals"
        val UPVOTES = "upvotes";

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val mealReference = firebaseDatabase.getReference(MEALS)
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        @JvmStatic fun addMealToDatabase(mealName: String) {
            mealReference.child(mealName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null && !dataSnapshot.exists()) {
                        addMeal(mealName);
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    if (databaseError != null) {
                        Timber.e(databaseError.message)
                    }
                }
            })
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


        private fun addMeal(mealName: String) {
            val newMeal: FirebaseMeal = FirebaseMeal(mealName)

            mealReference.child(mealName).setValue(newMeal, DatabaseReference.CompletionListener { databaseError, ref ->
                if (databaseError != null) {
                    Timber.e(databaseError.message)
                }
            })
        }

        private fun checkIfUserVoted(listener: MainFragmentListener, firebaseMeal: FirebaseMeal) {
            val meal: Meal = Meal()
            meal.nameEn = firebaseMeal.name

            if (firebaseMeal.downvotes != null) {
                if (firebaseMeal.downvotes[userId] == true) {
                    meal.isDownvoted = true;
                    meal.isUpvoted = false;
                } else {
                    meal.isDownvoted = false;
                }
            }

            if (firebaseMeal.upvotes != null) {
                if (firebaseMeal.upvotes[userId] == true) {
                    meal.isDownvoted = false;
                    meal.isUpvoted = true;
                } else {
                    meal.isUpvoted = false;
                }
            }

            //if (meal.isDownvoted || meal.isUpvoted) {
            listener.sendUserVote(meal)
            //}
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

                        meal.nameEn = firebaseMeal.name
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