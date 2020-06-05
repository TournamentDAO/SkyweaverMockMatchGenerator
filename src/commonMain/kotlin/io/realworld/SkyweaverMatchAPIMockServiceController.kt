package io.realworld

import io.realworld.model.SkyWeaverAPIMock
import io.realworld.model.SkyWeaverRequestMock

expect class SkyweaverMatchAPIMockServiceController {
    suspend fun skyweaverMatchesByUserAddress(req: SkyWeaverRequestMock): SkyWeaverAPIMock
}
