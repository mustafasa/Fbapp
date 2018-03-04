package com.mustafa.arif.fbapp.ui

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView.OnScrollListener
import com.mustafa.arif.fbapp.backend.CommunicationChecker
import com.mustafa.arif.fbapp.backend.FbCommunicator
import com.mustafa.arif.fbapp.backend.model.Data
import com.mustafa.arif.fbapp.backend.model.FbResponse
import com.mustafa.arif.fbapp.backend.model.Paging
import com.mustafa.arif.fbapp.backend.model.PostResponse
import com.mustafa.arif.fbapp.recycler.RecyclerAdapter
import com.mustafa.arif.fbapp.recycler.RecyclerViewListener
import com.mustafa.arif.fbapp.R
import com.mustafa.arif.fbapp.base.BasePresentableView
import com.mustafa.arif.fbapp.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by arifm2 on 3/2/2018.
 */
class HomePresenter @Inject constructor(communicationChecker: CommunicationChecker,
                                        fbCommunicator: FbCommunicator)
    : BasePresenter<HomePresenter.View>() {

    private var recyclerAdapter: RecyclerAdapter
    private var data: ArrayList<Data>? = null
    private var paging: Paging? = null
    private var communicationChecker: CommunicationChecker? = null
    private var fbCommunicator: FbCommunicator? = null


    init {
        this.communicationChecker = communicationChecker
        this.fbCommunicator = fbCommunicator
        recyclerAdapter = RecyclerAdapter()
    }

    private val recyclerViewListener = object : RecyclerViewListener {


        override fun onClick(v: android.view.View, position: Int) {
            if (v.id == R.id.imageView) {
                val thumbnail = data?.get(position)?.getFullPicture()
                if (thumbnail != null && !thumbnail.isEmpty() && isValid(thumbnail))
                    view?.openBrowser(thumbnail);
            } else {
                val urlLink = data?.get(position)?.getPermalinkUrl()
                if (urlLink != null && !urlLink.isEmpty() && isValid(urlLink))
                    view?.openBrowser(urlLink);
            }
        }

        override fun atBottom() {
            view?.showProgressBar(true)
            getOlderFeed()
        }
    }


    val scrollListener = object : OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> view?.showFloatingUpdateBtn(true)
                RecyclerView.SCROLL_STATE_DRAGGING -> view?.showFloatingUpdateBtn(false)
            }

        }
    }

    val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        view?.fbLogin()
    }

    fun onRestore(data: ArrayList<Data>,paging: Paging){
        this.data=data
        this.paging=paging
    }

    fun getData(): ArrayList<Data>?{
        return this.data
    }

    fun getPaging(): Paging?{
        return this.paging
    }

    fun updateRecycler(token: String?) {
        if (token == null) {
            view?.fbLogin()
            return
        }else if(data!=null){
            recyclerAdapter.addViewListener(recyclerViewListener)
            view?.setRecycleAdapter(recyclerAdapter, data)
            return
        }
        getInitFeed(token)
    }

    fun getInitFeed(token: String?) {
        view?.showProgressBar(true)
        if (token == null) {
            view?.fbLogin()
            return
        }
        recyclerAdapter.addViewListener(recyclerViewListener)
        if (communicationChecker!!.isNetworkAvailable!!) {
            fbCommunicator?.getFeed(10, token, "picture,created_time,story" +
                    ",message,name,full_picture,permalink_url")?.enqueue(object : Callback<FbResponse> {
                override fun onResponse(call: Call<FbResponse>, response: retrofit2.Response<FbResponse>?) {
                    if (response != null && response.body()?.data != null) {
                        data = response.body()?.data
                        paging = response.body()?.paging
                        view?.setRecycleAdapter(recyclerAdapter, data)
                        view?.showProgressBar(false)
                        view?.showFloatingUpdateBtn(true)
                    }
                }

                override fun onFailure(call: Call<FbResponse>, t: Throwable) {
                    initErrorHandler()
                }
            })
        } else {
            view?.showProgressBar(false)
            view?.toastMessage(R.string.toast_message_error_netwrok)

        }
    }

    fun getOlderFeed() {
        view?.showProgressBar(true)
        if (communicationChecker!!.isNetworkAvailable!!) {
            fbCommunicator?.getOlderFeed(paging?.getNext())?.enqueue(object : Callback<FbResponse> {
                override fun onResponse(call: Call<FbResponse>, response: retrofit2.Response<FbResponse>?) {
                    if (response != null && response.body()?.data != null) {
                        data?.addAll(response.body()?.data!!)
                        paging = response.body()?.paging
                        view?.updateRecyclerAdapter(recyclerAdapter, data)
                        view?.showProgressBar(false)
                    }
                }

                override fun onFailure(call: Call<FbResponse>, t: Throwable) {
                    noMoreFeedErrorHandler()
                }
            })
        } else {
            noNetworkErroHandler()
        }
    }

    fun postToFb(message: String?, token: String?) {
        view?.showProgressBar(true)
        if (token == null) {
            view?.fbLogin()
            return
        } else if (message == null) {
            view?.showProgressBar(false)
            view?.toastMessage(R.string.post_invalid)
            return
        }
        if (communicationChecker!!.isNetworkAvailable!!) {
            fbCommunicator?.addPost(token, message)?.enqueue(object : Callback<PostResponse> {
                override fun onResponse(call: Call<PostResponse>, response: retrofit2.Response<PostResponse>?) {
                    if (response?.body()?.getId() != null) {
                        view?.toastMessage(R.string.post_successfully)
                        getInitFeed(token)
                    } else {
                        noNetworkErroHandler()
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    noNetworkErroHandler()
                }
            })
        } else {
            noNetworkErroHandler()
        }
    }

    private fun noNetworkErroHandler() {
        view?.showProgressBar(false)
        view?.toastMessage(R.string.toast_message_error_netwrok)
    }

    private fun initErrorHandler() {
        view?.showProgressBar(false)
        if (data == null) run {
            data = ArrayList<Data>()
            val tempData = Data()
            tempData.setMessage("-1")
            data!!.add(tempData)
            view?.setRecycleAdapter(recyclerAdapter, data)
        }

    }

    private fun noMoreFeedErrorHandler() {
        if (paging?.getNext() == null) {
            view?.toastMessage(R.string.no_more_feeds)
        }
        view?.showProgressBar(false)
    }

    interface View : BasePresentableView {
        /**
         * This method is for initial setup and  attach recyclerAdapter and data array
         *
         * @param recycleAdapter adapter to attach
         * @param Data       data arraylist to attach recycler adapter.
         */
        fun setRecycleAdapter(recyclerAdapter: RecyclerAdapter, data: ArrayList<Data>?)

        /**
         * This method is utilized to update recyclerView adapter and data array.
         *
         * @param recycleAdapter adapter to update recyclerView
         * @param Data       arraylist to update  recyclerView
         */
        fun updateRecyclerAdapter(recycleAdapter: RecyclerAdapter, data: ArrayList<Data>?)


        /**
         * Control showing/hidden of progress bar
         *
         * @param show true if show,else false
         */
        fun showProgressBar(show: Boolean)

        fun fbLogin()

        fun showFloatingUpdateBtn(boolean: Boolean)
    }
}