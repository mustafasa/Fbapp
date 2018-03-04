package com.mustafa.arif.fbapp.recycler

import android.view.View


interface RecyclerViewListener {



    /**
     * when user click on item on recyclerView item, then this method is called passing two
     * param.
     *
     * @param view
     * @param position
     */
    fun onClick(view: View, position: Int)

    /**
     * When the user scroll recyclerView to bottom of the list, this method is called.
     */
     fun atBottom()
}