package th.thebox.sayhichat.data.usecase.friend

import io.reactivex.Observable
import th.thebox.sayhichat.data.entity.model.friend.FriendModel
import th.thebox.sayhichat.data.provider.friend.FriendProvider

interface FriendListUseCase {
    fun getFriendList(): Observable<List<FriendModel>>
}

class FriendListUseCaseImpl(private val provider: FriendProvider) : FriendListUseCase {

    override fun getFriendList(): Observable<List<FriendModel>> {
        return provider.loadFriendList()
                .map { response ->
                    response.items ?: listOf()
                }
    }
}