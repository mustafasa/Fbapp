package com.mustafa.arif.fbapp.backend

/**
 * Class to check all sort of commuication
 */
interface CommunicationChecker {

    /**
     * Returns the status of network connection. Provides the current status immediately.
     *
     * @return `true` if connection is available, `false`.
     */
    val isNetworkAvailable: Boolean
}
