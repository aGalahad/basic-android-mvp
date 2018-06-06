package th.thebox.sayhichat.presentation.adapter.friend

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_friend.view.*
import th.thebox.sayhichat.R
import th.thebox.sayhichat.data.entity.model.friend.FriendModel

/**
 * Created by armtimus on 10/21/2017 AD.
 */

class FriendAdapter(private var context: Context,
                    private var listData: List<FriendModel>) :
        RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView = itemView.profileImageView!!
        val nameTextView = itemView.nameTextView!!
    }

    override fun getItemCount() = listData.size

    fun setListData(list: List<FriendModel>) {
        listData = list
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]

        holder.nameTextView.text = item.name

        holder.profileImageView.setImageDrawable(
                ContextCompat.getDrawable(context,
                if (item.gender == FriendModel.BOY) {
                    R.drawable.boy
                } else {
                    R.drawable.girl
                }))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_friend,
                parent,
                false)
        return ViewHolder(itemView)
    }
}