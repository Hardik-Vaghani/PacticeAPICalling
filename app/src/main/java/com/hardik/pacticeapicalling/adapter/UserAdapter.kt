package com.hardik.pacticeapicalling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hardik.pacticeapicalling.R
import com.hardik.pacticeapicalling.databinding.ItemUserPreviewBinding
import com.hardik.pacticeapicalling.domain.model.UserModel

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    val TAG = UserAdapter::class.java.simpleName
    inner class UserViewHolder(val binding: ItemUserPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(
            oldItem: UserModel,
            newItem: UserModel
                                    ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserModel,
            newItem: UserModel
                                       ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this@UserAdapter, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
                                          )
                             )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).setDuration(200).start()
        holder.itemView.apply {
            holder.binding.tvTitle.text = user.name
            holder.binding.tvEmail.text = user.email
            Glide
                .with(this)
                .load("https://100k-faces.glitch.me/random-image")
//                .load("https://ozgrozer.github.io/100k-faces/0/6/006722.jpg")
//                .centerCrop()
//                .apply(RequestOptions.circleCropTransform())
//                .apply(RequestOptions().transform(RoundedCorners(20)))
                .fitCenter()
                .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_launcher_foreground))
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.ivUser)
            setOnClickListener {
                onItemClickListener?.let {
                    it(
                        UserModel(
                            id = user.id ?: 1,
                            city=user.city,
                            companyName=user.companyName,
                            email=user.email,
                            lat=user.lat,
                            lng=user.lng,
                            name=user.name,
                            phone=user.phone,
                            street=user.street,
                            suite=user.suite,
                            username=user.username,
                            website=user.website,
                            zipcode=user.zipcode,
                            )
                      )
                }
            }
        }
    }

    private var onItemClickListener: ((UserModel) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: ((UserModel) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }
}