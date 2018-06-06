package th.thebox.sayhichat.data.provider.friend

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import th.thebox.sayhichat.data.entity.response.friend.FriendListResponse

interface FriendProvider {
    fun loadFriendList(): Observable<FriendListResponse>
}

class FriendProviderImpl(private val firebaseDatabase: FirebaseDatabase) : FriendProvider {

    companion object {
        const val FRIENDS = "friends"
    }

    private val errorMessage = "Couldn't get friend list from repository"

    override fun loadFriendList(): Observable<FriendListResponse> =
            Observable.create<FriendListResponse> { emitter ->

                firebaseDatabase.reference.child(FRIENDS).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        if (!emitter.isDisposed) {
                            emitter.tryOnError(Throwable(message = errorMessage))
                        }
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.getValue(FriendListResponse::class.java) == null) {
                            if (!emitter.isDisposed) {
                                emitter.tryOnError(Throwable(message = errorMessage))
                            }
                        }

                        dataSnapshot.getValue(FriendListResponse::class.java)?.let {
                            emitter.onNext(it)
                            emitter.onComplete()
                        }
                    }
                })

            }
}