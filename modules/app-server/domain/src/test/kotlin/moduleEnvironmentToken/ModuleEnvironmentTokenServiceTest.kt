package moduleEnvironmentToken

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isNull
import java.util.UUID

class ModuleEnvironmentTokenServiceTest {
    private val moduleEnvironmentTokenService = buildModuleEnvironmentTokenService()

    @Nested
    inner class TestRegenerate {
        @Test
        fun `regenerate should delete preexisting key for module ref and environment ref`() {
            // Given
            val moduleRef = UUID.randomUUID()
            val projectRef = UUID.randomUUID()

            // When
            val firstToken = moduleEnvironmentTokenService.regenerateToken(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef,
                    environmentRef = projectRef
                )
            ).tokenValue

            moduleEnvironmentTokenService.regenerateToken(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef,
                    environmentRef = projectRef
                )
            )

            // Then
            expectThat(moduleEnvironmentTokenService.getTokenByValue(firstToken)).isNull()
        }
    }
}

