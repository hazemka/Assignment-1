package com.example.assignment1

import com.google.firebase.firestore.DocumentId

data class Contact(@DocumentId var id:String,var name:String,var number:String,var address:String)
