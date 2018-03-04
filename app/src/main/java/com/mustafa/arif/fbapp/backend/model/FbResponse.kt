package com.mustafa.arif.fbapp.backend.model

import java.util.ArrayList


class FbResponse {
    var data: ArrayList<Data>? = null

    var paging: Paging? = null


    override fun toString(): String {
        return "ClassPojo [data = $data, paging = $paging]"
    }
}