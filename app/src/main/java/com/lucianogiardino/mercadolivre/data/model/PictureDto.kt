import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("secure_url") val secureUrl: String,
)
