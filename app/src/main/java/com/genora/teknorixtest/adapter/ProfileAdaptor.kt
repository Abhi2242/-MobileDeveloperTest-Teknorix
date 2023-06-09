package com.genora.teknorixtest.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.genora.teknorixtest.Models.Data
import com.genora.teknorixtest.R
import com.genora.teknorixtest.activities.DetailedProfile


class ProfileAdaptor(private val profileList: List<Data>): RecyclerView.Adapter<ProfileAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.profileData = profileList[position]

        val url = profileList[position].avatar
        Glide.with(holder.itemView.context)
            .load(url)
            .override(150,150)
            .transform(CenterInside(), RoundedCorners(24))
            .into(holder.profileAvatar)

        val fullName = "${profileList[position].first_name} ${profileList[position].last_name}"
        holder.profileFullName.text = fullName

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, DetailedProfile::class.java)
            intent.putExtra(DetailedProfile.ARG_PROFILE_ID, holder.profileData!!.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val profileFullName: TextView = itemView.findViewById(R.id.tv_profileName)
        var profileData: Data? = null
        var profileAvatar: ImageView = itemView.findViewById(R.id.iv_profileImage)

        override fun toString(): String {
            return """${super.toString()} '${profileFullName.text}'"""
        }
    }
}