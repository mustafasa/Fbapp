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
    @Mock
    lateinit var recyclerAdapterMock: RecyclerAdapter

    private var homepresenter: HomePresenter? = null

    @Before
    fun setup() {
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(true)
        homepresenter=HomePresenter(communicationCheckerMock,fbCommunicatorMock,recyclerAdapterMock)
        homepresenter!!.bind(viewMock)
    }


    @Test
    fun test_emptyToken_updateRecycler() {
        var toke:String?=null
        homepresenter!!.updateRecycler(toke)
        verify(viewMock).fbLogin()
    }

    @Test
    fun test_validToken_updateRecycler() {
        var toke ="token"
        homepresenter!!.updateRecycler(toke)
        verify(viewMock, never()).fbLogin()
    }

    @Test
    fun test_emptyToken_getInitFeed(){
        var toke:String?=null
        homepresenter!!.getInitFeed(toke)
        verify(viewMock).showProgressBar(true)
        verify(viewMock).fbLogin()
        verify(communicationCheckerMock, never()).isNetworkAvailable
    }

    @Test
    fun test_validToken_getInitFeed(){
        var toke:String?="token"
        homepresenter!!.getInitFeed(toke)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(communicationCheckerMock).isNetworkAvailable
    }

    @Test
    fun test_validToken_getInitFeed_notAvailableNetwork(){
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(false)
        var toke:String?="token"
        homepresenter!!.getInitFeed(toke)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(communicationCheckerMock).isNetworkAvailable
        verify(fbCommunicatorMock, never()).getFeed(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.toast_message_error_netwrok)

    }

    @Test
    fun test_validToken_getInitFeed_availableNetwork(){
        var toke:String?="token"
        homepresenter!!.getInitFeed(toke)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(communicationCheckerMock).isNetworkAvailable
        verify(fbCommunicatorMock).getFeed(10, toke,
                "picture,created_time,story,message,name")
        verify(viewMock, never()).showProgressBar(false)
        verify(viewMock, never()).toastMessage(R.string.toast_message_error_netwrok)

    }
    @Test
    fun test_getOlderFeed(){
        homepresenter!!.getOlderFeed()
        verify(viewMock).showProgressBar(true)
        verify(communicationCheckerMock).isNetworkAvailable

    }
    @Test
    fun test_getOlderFeed_notAvailableNetwork(){
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(false)
        homepresenter!!.getOlderFeed()
        verify(viewMock).showProgressBar(true)
        verify(communicationCheckerMock).isNetworkAvailable
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.toast_message_error_netwrok)

    }
    @Test
    fun test_postToFb_nullMessage(){
        var token:String?="token"
        var message:String?=null
        homepresenter!!.postToFb(message,token)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.post_invalid)
    }

    @Test
    fun test_postToFb_nullToken(){
        var token:String?=null
        var message:String?="message"
        homepresenter!!.postToFb(message,token)
        verify(viewMock).showProgressBar(true)
        verify(viewMock).fbLogin()
        verify(viewMock,never()).toastMessage(R.string.post_invalid)
    }

    @Test
    fun test_postToFb_nullToken_test_nullMessage(){
        var token:String?=null
        var message:String?=null
        homepresenter!!.postToFb(message,token)
        verify(viewMock).showProgressBar(true)
        verify(viewMock).fbLogin()
        verify(viewMock,never()).toastMessage(R.string.post_invalid)
    }

    @Test
    fun test_postToFb_validMessageAndToken(){
        var token:String?="token"
        var message:String?="message"
        homepresenter!!.postToFb(message,token)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(viewMock,never()).toastMessage(R.string.post_invalid)
        verify(communicationCheckerMock).isNetworkAvailable
    }
    @Test
    fun test_postToFb_validMessageAndToken_notAvailableNetwork(){
        `when`(communicationCheckerMock.isNetworkAvailable).thenReturn(false)
        var token:String?="token"
        var message:String?="message"
        homepresenter!!.postToFb(message,token)
        verify(viewMock).showProgressBar(true)
        verify(viewMock, never()).fbLogin()
        verify(viewMock,never()).toastMessage(R.string.post_invalid)
        verify(communicationCheckerMock).isNetworkAvailable
        verify(viewMock).showProgressBar(false)
        verify(viewMock).toastMessage(R.string.toast_message_error_netwrok)
    }


}