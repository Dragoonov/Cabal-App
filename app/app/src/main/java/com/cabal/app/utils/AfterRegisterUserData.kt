package com.cabal.app.utils

data class AfterRegisterUserData(val avatar: String, val nickname: String, val interestIds: IntArray, val searchRadius: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AfterRegisterUserData

        if (avatar != other.avatar) return false
        if (nickname != other.nickname) return false
        if (searchRadius != other.searchRadius) return false
        if (!interestIds.contentEquals(other.interestIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatar.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + searchRadius
        result = 31 * result + interestIds.contentHashCode()
        return result
    }
}