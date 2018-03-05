package com.mustafa.arif.fbapp.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.mustafa.arif.fbapp.backend.model.Data
import com.mustafa.arif.fbapp.recycler.RecyclerAdapter
import com.mustafa.arif.fbapp.MyApp
import com.mustafa.arif.fbapp.R
import com.mustafa.arif.fbapp.base.BaseActivity
import java.util.*
import android.view.Gravity
import android.view.WindowManager


class HomeActivity : BaseActivity<HomePresenter.View, HomePresenter>(), HomePresenter.View {

    private var callbackManager: CallbackManager? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var floatingPostBtn: FloatingActionButton? = null
    private val KEY_DATA: String = "DATA"
    private val KEY_PAGING: String = "PAGING"

    override fun setRecycleAdapter(recyclerAdapter: RecyclerAdapter, data: ArrayList<Data>?) {
        recyclerAdapter.addFbFeeds(data)
        recyclerView?.setAdapter(recyclerAdapter)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun updateRecyclerAdapter(recycleAdapter: RecyclerAdapter, data: ArrayList<Data>?) {
        recycleAdapter.addFbFeeds(data)
        recycleAdapter.notifyItemInserted(data?.size!! - 10)
        recycleAdapter.notifyDataSetChanged()
    }

    override fun showFloatingUpdateBtn(boolean: Boolean) {
        if (boolean) floatingPostBtn?.show()
        else floatingPostBtn?.hide()
    }


    private fun recyclerBuilder() {
        recyclerView = findViewById(R.id.recycleView)
        var mLayoutManager: LinearLayoutManager
        // set true if your RecyclerView is finite and has fixed size
        recyclerView?.setHasFixedSize(false)
        // Set the required LayoutManager
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false)
        val dividerItemDecoration = DividerItemDecoration(recyclerView!!.getContext(),
                mLayoutManager.getOrientation())
        recyclerView!!.addItemDecoration(dividerItemDecoration)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.addOnScrollListener(presenter.scrollListener)
        swipeRefreshLayout?.setOnRefreshListener(presenter.onRefreshListener)
    }


    override fun configureViewElements(savedInstanceState: Bundle?) {
        setContentView(R.layout.main_view)
        (application as MyApp).getActivityComponent()?.getSubComponent()?.inject(this)
        progressBar = findViewById(R.id.progress_bar)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        floatingPostBtn = findViewById(R.id.floatingPostBtn)
        floatingPostBtn?.setOnClickListener { fbPublishPermission() }
        showFloatingUpdateBtn(false)
        recyclerBuilder()
    }

    override fun showProgressBar(show: Boolean) {
        if (show) run { progressBar?.setVisibility(View.VISIBLE) }
        else {
            progressBar?.setVisibility(View.GONE)
            swipeRefreshLayout?.setRefreshing(false)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.setToken(AccessToken.getCurrentAccessToken()?.token)
        presenter.updateRecycler()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(KEY_DATA) && savedInstanceState.containsKey(KEY_PAGING)) {
            presenter.onRestore(savedInstanceState.getParcelableArrayList(KEY_DATA),
                    savedInstanceState.getParcelable(KEY_PAGING))
        }

    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        if (presenter.getData() != null && presenter.getPaging() != null) {
            savedInstanceState?.putParcelableArrayList(KEY_DATA, presenter.getData())
            savedInstanceState?.putParcelable(KEY_PAGING, presenter.getPaging())
        }

    }

    override fun fbLogin() {
        if (AccessToken.getCurrentAccessToken() != null) {
            if (AccessToken.getCurrentAccessToken().getPermissions().contains("user_posts")) {
                presenter?.setToken(AccessToken.getCurrentAccessToken().token)
                presenter?.getInitFeed()
                return
            }
        }
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
                "user_posts", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        presenter?.setToken(loginResult.accessToken.token)
                        presenter?.getInitFeed()
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                    }

                    override fun onCancel() {
                        toastMessage(R.string.toast_message_user_cancel)

                    }

                    override fun onError(error: FacebookException) {
                        toastMessage(R.string.toast_message_error)

                    }
                })
    }

    override fun fbPublishPermission() {
        if (AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions")) {
            customDialogBox()
            return
        }
        LoginManager.getInstance().logInWithPublishPermissions(
                this,
                Arrays.asList("publish_actions"))
    }

    override fun customDialogBox() {
        // custom dialog
        val dialog = Dialog(this)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        lp.windowAnimations = R.style.DialogAnimation
        dialog.window.attributes = lp
        dialog.setContentView(R.layout.dialog_box)
        val postText: EditText = dialog.findViewById(R.id.postEditText)
        val postButton: Button = dialog.findViewById(R.id.postButton)
        postButton.setOnClickListener({ view ->
            presenter.setToken(AccessToken.getCurrentAccessToken().token)
            presenter.postToFb(postText.text.toString())
            dialog.dismiss()
        })
        dialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}

