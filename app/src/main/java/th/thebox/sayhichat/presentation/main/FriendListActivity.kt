package th.thebox.sayhichat.presentation.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import th.thebox.sayhichat.R
import th.thebox.sayhichat.data.entity.model.friend.FriendModel
import th.thebox.sayhichat.data.provider.friend.FriendProviderImpl
import th.thebox.sayhichat.data.usecase.friend.FriendListUseCaseImpl
import th.thebox.sayhichat.presentation.adapter.friend.FriendAdapter

class FriendListActivity : AppCompatActivity(), FriendListContract.View {

    private lateinit var presenter: FriendListContract.Presenter
    private var friendAdapter: FriendAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        presenter = FriendListPresenter(view = this,
                friendListUseCase = FriendListUseCaseImpl(
                        provider = FriendProviderImpl(firebaseDatabase = FirebaseDatabase.getInstance())))

        initView()

        presenter.findFriend(gender = FriendModel.ALL)
    }

    private fun initView() {

        friendAdapter = FriendAdapter(context = this, listData = listOf())

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = friendAdapter

        friendsTextView.setOnClickListener {
            selectedTab(index = FriendModel.ALL)
            presenter.findFriend(gender = FriendModel.ALL)
        }

        boysTextView.setOnClickListener {
            selectedTab(index = FriendModel.BOY)
            presenter.findFriend(gender = FriendModel.BOY)
        }

        girlsTextView.setOnClickListener {
            selectedTab(index = FriendModel.GIRL)
            presenter.findFriend(gender = FriendModel.GIRL)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.clear()
    }

    override fun renderFriendList(list: List<FriendModel>) {
        friendAdapter?.let { adapter ->
            adapter.setListData(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun showContent() {
        contentView.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        errorView.visibility = View.VISIBLE
        errorTextView.text = message
    }

    override fun selectedTab(index: Int) {
        friendsTextView.setTextColor(ContextCompat.getColor(this,
                if (index == FriendModel.ALL) R.color.appBlackTextColor
                else R.color.white))

        friendsTextView.background = ContextCompat.getDrawable(this,
                if (index == FriendModel.ALL) R.drawable.selected_background
                else R.drawable.deselect_background)

        boysTextView.setTextColor(ContextCompat.getColor(this,
                if (index == FriendModel.BOY) R.color.appBlackTextColor
                else R.color.white))

        boysTextView.background = ContextCompat.getDrawable(this,
                if (index == FriendModel.BOY) R.drawable.selected_background
                else R.drawable.deselect_background)

        girlsTextView.setTextColor(ContextCompat.getColor(this,
                if (index == FriendModel.GIRL) R.color.appBlackTextColor
                else R.color.white))

        girlsTextView.background = ContextCompat.getDrawable(this,
                if (index == FriendModel.GIRL) R.drawable.selected_background
                else R.drawable.deselect_background)
    }
}
