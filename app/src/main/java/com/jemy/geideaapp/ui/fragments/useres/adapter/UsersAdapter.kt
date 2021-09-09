package com.jemy.geideaapp.ui.fragments.useres.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jemy.geideaapp.data.model.entities.UserEntity
import com.jemy.geideaapp.databinding.ItemUserBinding

class UsersAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private var itemCallback: ((UserEntity?) -> Unit)? = null
    var items = mutableListOf<UserEntity>()

    fun addItems(items: List<UserEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val itemBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding, itemCallback)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = items[position]
        holder.bind(user)
    }

    fun setItemCallBack(itemCallback: (UserEntity?) -> Unit) {
        this.itemCallback = itemCallback
    }

    override fun getItemCount(): Int = items.size
}

class UserViewHolder(
    itemUserBinding: ItemUserBinding,
    private val itemCallback: ((UserEntity?) -> Unit)?
) : RecyclerView.ViewHolder(itemUserBinding.root) {

    private var itemView = itemUserBinding.itemView
    private var name = itemUserBinding.userName
    private var id = itemUserBinding.userId

    fun bind(user: UserEntity?) {
        itemView.setOnClickListener { itemCallback?.invoke(user) }
        name.text = "${user?.firstName} ${user?.lastName}"
        id.text = user?.id.toString()
    }
}