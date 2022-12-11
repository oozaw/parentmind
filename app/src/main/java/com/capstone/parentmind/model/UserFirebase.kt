package com.capstone.parentmind.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserFirebase(
   val id_backend: String? = null,
   val email: String? = null,
   val name: String? = null,
   val profilePicture: String? = null
) {
   // Null default values create a no-argument default constructor, which is needed
   // for deserialization from a DataSnapshot.
}
