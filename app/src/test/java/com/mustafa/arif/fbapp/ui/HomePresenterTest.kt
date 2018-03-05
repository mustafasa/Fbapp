package com.mustafa.arif.fbapp.ui

import com.mustafa.arif.fbapp.R
import com.mustafa.arif.fbapp.backend.CommunicationChecker
import com.mustafa.arif.fbapp.backend.FbCommunicator
import com.mustafa.arif.fbapp.recycler.RecyclerAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class HomePresenterTest {
    @Mock
    lateinit var viewMock: HomePresenter.View
    @Mock
    lateinit var communicationCheckerMock: CommunicationChecker
    @Mock
    lateinit var fbCommunicatorMock: FbCommunicator

    private var homePresenter: HomePresenter? = null


    @Before
    fun setup() {
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(true)
        homePresenter = HomePresenter(communicationCheckerMock, fbCommunicatorMock, RecyclerAdapter())
        homePresenter!!.bind(viewMock)
    }


    @Test
    fun test_emptyToken_updateRecycler() {
        homePresenter!!.updateRecycler()
        verify(viewMock).fbLogin()
    }

    @Test
    fun test_validToken_updateRecycler() {
        homePresenter!!.setToken("token")
        homePresenter!!.updateRecycler()
        verify(viewMock, never()).fbLogin()
    }

    @Test
    fun test_emptyToken_getInitFeed() {
        homePresenter!!.getInitFeed()
        verify(viewMock).showProgressBar(true)
        verify(viewMock).fbLogin()
        verify(communicationCheckerMock, never()).isNetworkAvailable
    }

    @Test
    fun test_validToken_getInitFeed() {
        homePresenter!!.setToken("token")
        homePresenter!!.getInitFeed()
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(communicationCheckerMock).isNetworkAvailable
    }

    @Test
    fun test_validToken_getInitFeed_notAvailableNetwork() {
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(false)
        homePresenter!!.setToken("token")
        homePresenter!!.getInitFeed()
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(communicationCheckerMock).isNetworkAvailable
        verify(fbCommunicatorMock, never()).getFeed(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())
        verify(viewMock, times(2)).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.toast_message_error_network)

    }

    @Test
    fun test_validToken_getInitFeed_availableNetwork() {
        homePresenter!!.setToken("token")
        homePresenter!!.getInitFeed()
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(communicationCheckerMock).isNetworkAvailable
        verify(fbCommunicatorMock).getFeed(10, "token",
                "picture,created_time,story,message,name,full_picture,permalink_url")
        verify(viewMock, never()).showProgressBar(false)
        verify(viewMock, never()).toastMessage(R.string.toast_message_error_network)

    }

    @Test
    fun test_getOlderFeed() {
        homePresenter!!.getOlderFeed()
        verify(viewMock).showProgressBar(true)
        verify(communicationCheckerMock).isNetworkAvailable

    }

    @Test
    fun test_getOlderFeed_notAvailableNetwork() {
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(false)
        homePresenter!!.getOlderFeed()
        verify(viewMock).showProgressBar(true)
        verify(communicationCheckerMock).isNetworkAvailable
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.toast_message_error_network)

    }

    @Test
    fun test_postToFb_nullMessage() {
        homePresenter!!.setToken("token")
        var message: String? = null
        homePresenter!!.postToFb(message)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.post_invalid)
    }

    @Test
    fun test_postToFb_nullToken() {
        var message: String? = "message"
        homePresenter!!.postToFb(message)
        verify(viewMock).showProgressBar(true)
        verify(viewMock).fbLogin()
        verify(viewMock, never()).toastMessage(R.string.post_invalid)
    }

    @Test
    fun test_postToFb_nullToken_test_nullMessage() {
        var message: String? = null
        homePresenter!!.postToFb(message)
        verify(viewMock).showProgressBar(true)
        verify(viewMock).fbLogin()
        verify(viewMock, never()).toastMessage(R.string.post_invalid)
    }

    @Test
    fun test_postToFb_validMessageAndToken() {
        var message: String? = "message"
        homePresenter!!.setToken("token")
        homePresenter!!.postToFb(message)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(viewMock, never()).toastMessage(R.string.post_invalid)
        verify(communicationCheckerMock).isNetworkAvailable
    }

    @Test
    fun test_postToFb_validMessageAndToken_notAvailableNetwork() {
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(false)
        var message: String? = "message"
        homePresenter!!.setToken("token")
        homePresenter!!.postToFb(message)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(viewMock, never()).toastMessage(R.string.post_invalid)
        verify(communicationCheckerMock).isNetworkAvailable
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.toast_message_error_network)
    }


}