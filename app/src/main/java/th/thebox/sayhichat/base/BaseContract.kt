package th.thebox.sayhichat.base

interface BaseContract {

    interface View {

        fun showProgress()

        fun hideProgress()

        fun showContent()

        fun showError(message: String)

    }

    interface Presenter {

        fun clear()

    }

}