package th.thebox.sayhichat.presentation.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import th.thebox.sayhichat.data.entity.model.friend.FriendModel
import th.thebox.sayhichat.data.usecase.friend.FriendListUseCase
import th.thebox.sayhichat.extensions.addTo

class FriendListPresenter(private var view: FriendListContract.View?,
                          private val friendListUseCase: FriendListUseCase): FriendListContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private var currentIndex = 2

    override fun findFriend(gender: Int) {

        if (currentIndex == gender) {
            return
        }

        view?.showProgress()
        friendListUseCase.getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    view?.hideProgress()

                    currentIndex = gender

                    response?.let { listOfFriend: List<FriendModel> ->

                        if (gender != FriendModel.ALL) {
                            view?.renderFriendList(listOfFriend.filter { it.gender == gender })
                        } else {
                            view?.renderFriendList(listOfFriend)
                        }

                    }

                }, { e ->

                    view?.hideProgress()

                    e.message?.let { errorMessage ->

                        view?.showError(errorMessage)
                        view?.selectedTab(currentIndex)

                    }

                }).addTo(compositeDisposable)
    }

    override fun clear() {
        view = null
        compositeDisposable.clear()
    }
}