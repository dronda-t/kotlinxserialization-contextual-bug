import kotlinx.serialization.decodeFromString
import kotlin.test.Test
import kotlin.test.assertEquals

class DeserializeTest {
    private val json = """
    {
        "hello": "world"
    }
""".trimIndent()

    private val toml = """
    hello = "world"
""".trimIndent()

    @Test
    fun jsonDeserialization() {
        val test = configStringJsonSerializer.decodeFromString<ContextualClass>(json)
        assertEquals("WORLD", test.hello)
    }

    @Test
    fun tomlDeserialization() {
        val test = configStringTomlSerializer.decodeFromString<ContextualClass>(toml)
        assertEquals("WORLD", test.hello)
    }

    @Test
    fun `normal string json deserialization`() {
        val test = normalStringJsonSerializer.decodeFromString<NormalStringClass>(json)
        assertEquals("WORLD", test.hello)
    }

    @Test
    fun `normal string toml deserialization`() {
        val test = normalStringTomlSerializer.decodeFromString<NormalStringClass>(toml)
        assertEquals("WORLD", test.hello)
    }
}