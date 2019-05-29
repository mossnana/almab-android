package com.mossnana.loginactivity

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Matchs(
    var matchId: String? = null,
    var leftTeamPoint: String? = null,
    var leftTeamName: String? = null,
    var leftTeamPlayerA: String? = null,
    var leftTeamPlayerB: String? = null,
    var rightTeamPoint: String? = null,
    var rightTeamName: String? = null,
    var rightTeamPlayerA: String? = null,
    var rightTeamPlayerB: String? = null,
    var createBy: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "matchId" to matchId,
            "leftTeamPoint" to leftTeamPoint,
            "leftTeamName" to leftTeamName,
            "leftTeamPlayerA" to leftTeamPlayerA,
            "leftTeamPlayerB" to leftTeamPlayerB,
            "rightTeamPoint" to rightTeamPoint,
            "rightTeamName" to rightTeamName,
            "rightTeamPlayerA" to rightTeamPlayerA,
            "rightTeamPlayerB" to rightTeamPlayerB,
            "createBy" to createBy
        )
    }
}