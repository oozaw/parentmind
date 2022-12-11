package com.capstone.parentmind.model

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ForumMessage(
   val created_by: String? = null,
   val name: String? = null,
   val created_at: Long? = null,
   val photoUrl: String? = null,
   val message: String? = null,
   val child: String? = null,
   val tag: String? = null,
   val title: String? = null,
   val body: String? = null
): Parcelable{
   // Null default values create a no-argument default constructor, which is needed
   // for deserialization from a DataSnapshot.
}