import java.time.Instant
import kotlin.random.Random

private val charactersAvailableForRandom = listOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
    'y', 'z'
)

fun randomString(length: Int = 10): String {
    return (1..length)
        .map { Random.nextInt(0, charactersAvailableForRandom.size).let { charactersAvailableForRandom[it] } }
        .joinToString("")
}