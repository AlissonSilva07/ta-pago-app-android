package br.alisson.edu.tapago.data.remote.dto.user

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import br.alisson.edu.tapago.domain.model.User
import br.alisson.edu.tapago.core.utils.formatDateAdapter

data class UserResponse(
    val createdAt: String,
    val email: String,
    val id: String,
    val name: String,
    val profilePicture: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun UserResponse.toDomainModel(): User {
    val decodedProfilePicture = decodeBase64ToByteArray(this.profilePicture)
    val formattedDate = formatDateAdapter(this.createdAt)

    return User(
        createdAt = formattedDate,
        email = this.email,
        id = this.id,
        name = this.name,
        profilePicture = decodedProfilePicture
    )
}

fun decodeBase64ToByteArray(base64String: String): ByteArray {
    return Base64.decode(base64String, Base64.DEFAULT)
}