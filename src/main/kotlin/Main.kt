import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import net.peanuuutz.tomlkt.Toml

typealias ConfigString = @Contextual String

class ConfigStringSerializer(
    private val envDecoder: UppercaseDecoder,
) : KSerializer<ConfigString> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ConfigString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ConfigString {
        val rawString = decoder.decodeString()
        return envDecoder.decode(rawString)
    }

    override fun serialize(encoder: Encoder, value: ConfigString) {
        error("Should not be serialized")
    }
}

class NormalStringSerializer(
    private val envDecoder: UppercaseDecoder
) : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NormalString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String {
        val rawString = decoder.decodeString()
        return envDecoder.decode(rawString)
    }

    override fun serialize(encoder: Encoder, value: String) {
        error("Should not be serialized")
    }

}

object UppercaseDecoder {
    fun decode(string: String): String {
        return string.uppercase()
    }
}

@Serializable
data class ContextualClass(
    val hello: ConfigString
)

@Serializable
data class NormalStringClass(
    @Contextual
    val hello: String
)

val configStringModule = SerializersModule {
    contextual(ConfigStringSerializer(UppercaseDecoder))
}

val normalStringModule = SerializersModule {
    contextual(NormalStringSerializer(UppercaseDecoder))
}


val configStringJsonSerializer = Json {
    serializersModule = configStringModule
}

val configStringTomlSerializer = Toml {
    serializersModule = configStringModule
}

val normalStringJsonSerializer = Json {
    serializersModule = normalStringModule
}

val normalStringTomlSerializer = Toml {
    serializersModule = normalStringModule
}


