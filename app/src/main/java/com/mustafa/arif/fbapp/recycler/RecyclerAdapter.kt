package com.mustafa.arif.fbapp.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mustafa.arif.fbapp.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mustafa.arif.fbapp.backend.model.Data
import com.squareup.picasso.Picasso
import java.util.ArrayList
import javax.inject.Inject


class RecyclerAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var fbFeeds: ArrayList<Data>? = null
    private var recyclerViewListener: RecyclerViewListener? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        var message: TextView
        var story: TextView
        var name: TextView
        var created_time: TextView
        var imageView: ImageView

        init {
            message = view.findViewById(R.id.message)
            message.setOnClickListener(this)
            story = view.findViewById(R.id.story)
            story.setOnClickListener(this)
            name = view.findViewById(R.id.name)
            name.setOnClickListener(this)
            created_time = view.findViewById(R.id.created_time)
            created_time.setOnClickListener(this)
            imageView = view.findViewById(R.id.imageView)
            imageView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            recyclerViewListener?.onClick(view,getAdapterPosition())
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        if (fbFeeds?.get(position)?.getMessage()=="-1") {
            holder?.imageView?.visibility = View.INVISIBLE
            holder?.story?.setText("Pull to refresh")
            holder?.story?.setTextSize(25.0f)
            holder?.story?.setTextColor(holder.story.getResources().getColor(R.color.colorPrimaryDark))
            return
        }

        if (position == fbFeeds!!.size - 1) {
            recyclerViewListener?.atBottom()
        }

        holder?.message?.setText(fbFeeds?.get(position)?.getMessage())
        holder?.story?.setText(fbFeeds?.get(position)?.getStory())
        holder?.name?.setText(fbFeeds?.get(position)?.getName())
        holder?.created_time?.setText(fbFeeds?.get(position)?.getCreated_time())
        val thumbnail = fbFeeds?.get(position)?.getPicture()
        Picasso.with(holder?.imageView?.context)
                .load(thumbnail)
                .placeholder(holder?.imageView?.context?.resources?.getDrawable(R.mipmap.ic_launcher))
                .error(holder?.imageView?.context?.resources?.getDrawable(R.mipmap.ic_launcher))
                .into(holder?.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent!!.getContext())
                .inflate(R.layout.feed_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (fbFeeds == null) 0 else fbFeeds!!.size
    }

    /**
     * Add arraylist to recycle adapter.
     *
     * @param children ArrayList to attach
     */
    fun addFbFeeds(fbFeeds: ArrayList<Data>?) {
        this.fbFeeds = fbFeeds
    }

    /**
     * set listener to get event from adapter
     *
     * @param recyclerViewListener listener to attach
     */
    fun addViewListener(recyclerViewListener: RecyclerViewListener) {
        this.recyclerViewListener = recyclerViewListener

    }

}