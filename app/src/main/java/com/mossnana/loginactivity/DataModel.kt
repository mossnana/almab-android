package com.mossnana.loginactivity

class DataModel {
    var matchId: String? = null
    var leftTeamPoint: String? = null
    var leftTeamName: String? = null
    var leftTeamPlayerA: String? = null
    var leftTeamPlayerB: String? = null
    var rightTeamPoint: String? = null
    var rightTeamName: String? = null
    var rightTeamPlayerA: String? = null
    var rightTeamPlayerB: String? = null

    constructor()

    constructor(
        matchId: String?,
        leftTeamPoint: String?,
        leftTeamName: String?,
        leftTeamPlayerA: String?,
        leftTeamPlayerB: String?,
        rightTeamPoint: String?,
        rightTeamName: String?,
        rightTeamPlayerA: String?,
        rightTeamPlayerB: String?
    ) {
        this.matchId = matchId
        this.leftTeamPoint = leftTeamPoint
        this.leftTeamName = leftTeamName
        this.leftTeamPlayerA = leftTeamPlayerA
        this.leftTeamPlayerB = leftTeamPlayerB
        this.rightTeamPoint = rightTeamPoint
        this.rightTeamName = rightTeamName
        this.rightTeamPlayerA = rightTeamPlayerA
        this.rightTeamPlayerB = rightTeamPlayerB
    }


    // Map Received Data to HashMap
    // Hashmap is easy to input data than list
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("matchId", matchId!!)
        result.put("leftTeamPoint", leftTeamPoint!!)
        result.put("leftTeamName", leftTeamName!!)
        result.put("leftTeamPlayerA", leftTeamPlayerA!!)
        result.put("leftTeamPlayerB", leftTeamPlayerB!!)
        result.put("rightTeamPoint", rightTeamPoint!!)
        result.put("rightTeamName", rightTeamName!!)
        result.put("rightTeamPlayerA", rightTeamPlayerA!!)
        result.put("rightTeamPlayerB", rightTeamPlayerB!!)
        return result
    }
}