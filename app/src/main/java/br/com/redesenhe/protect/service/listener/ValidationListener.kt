package br.com.redesenhe.protect.service.listener

class ValidationListener(str: String = "") {

    private var mStatus : Boolean = true
    private var mMsg : String = ""

    init {
        if (str != ""){
            mStatus = false
            mMsg = str
        }
    }

    fun success() = mStatus
    fun falure() = mMsg
}