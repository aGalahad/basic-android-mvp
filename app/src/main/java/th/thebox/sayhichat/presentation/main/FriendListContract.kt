package th.thebox.sayhichat.presentation.main

import th.thebox.sayhichat.base.BaseContract
import th.thebox.sayhichat.data.entity.model.friend.FriendModel

interface FriendListContract {

    interface View: BaseContract.View {
        fun renderFriendList(list: List<FriendModel>)
        fun selectedTab(index: Int)
    }

    interface Presenter: BaseContract.Presenter {
        fun findFriend(gender: Int)
    }

}