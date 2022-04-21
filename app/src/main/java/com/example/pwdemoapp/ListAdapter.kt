package com.example.pwdemoapp
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*
import android.widget.TextView
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.concurrent.thread


open class ListAdapter(
    private val context: Context,
    private var list: ArrayList<ProfileModel>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.nameID.text = model.name
            val builder = SpannableStringBuilder()
            builder.append(model.subjects[0])
            val strx = SpannableString("  •  ")
            strx.setSpan(ForegroundColorSpan(Color.GRAY), 0, strx.length, 0)
            val len1 = model.subjects.size
            val len2 = model.qualification.size
            var i=1
            while(i<len1){
                strx.setSpan(ForegroundColorSpan(Color.GRAY), 0, strx.length, 0)
                builder.append(strx)
                builder.append(model.subjects[i])
                i++
            }
            i=0
            while(i<len2){
                strx.setSpan(ForegroundColorSpan(Color.GRAY), 0, strx.length, 0)
                builder.append(strx)
                builder.append(model.qualification[i])
                i++
            }

            holder.itemView.qualID.setText(builder, TextView.BufferType.SPANNABLE)
            thread {
                Glide.with(context)
                    .asBitmap()
                    .load(model.profileImage)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            holder.itemView.image.setImageBitmap(resource)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }
                    })
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}