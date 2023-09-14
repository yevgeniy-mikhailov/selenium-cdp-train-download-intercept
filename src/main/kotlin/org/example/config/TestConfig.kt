package io.piano.ai.config

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.*
import org.aeonbits.owner.ConfigFactory

@LoadPolicy(LoadType.MERGE)
@Sources(
    "system:env",
)
interface TestConfig : Config {
    @Key("HUB")
    fun hub(): String

    @Key("HUB_URL")
    fun hubUrl(): String

    @Key("BROWSER_NAME")
    fun browserName(): String

    @Key("BROWSER_VERSION")
    fun browserVersion(): String

    @Key("BROWSER_HEADLESS")
    fun isHeadless(): Boolean
}

val config: TestConfig = ConfigFactory.create(TestConfig::class.java)
