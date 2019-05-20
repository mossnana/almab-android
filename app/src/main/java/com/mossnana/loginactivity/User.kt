package com.mossnana.loginactivity

class User(val uid: String, val username: String, val profileImageUrl: String, val name: String) {
    constructor() : this("", "", "", "")
}