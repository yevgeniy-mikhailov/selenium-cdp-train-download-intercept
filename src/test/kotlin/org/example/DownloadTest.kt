package org.example

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.open
import io.piano.ai.config.config
import io.piano.ai.utils.DownloadHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.logging.Level

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DownloadTest {
    val log: Logger = LoggerFactory.getLogger(DownloadTest::class.qualifiedName)
    @BeforeAll
    fun setUp() {
        setupDriver()
        try {
            open()
        } catch (e: WebDriverException) {
            log.warn(e.localizedMessage)
        }
    }

    @Test
    fun downloadTest() {
        open("https://the-internet.herokuapp.com/download")
        val filename = "username.csv"
        DownloadHelper.downloadFile(filename) {
            `$`(Selectors.byText(filename)).click()
        }
    }

    private fun setupDriver() {
        val options = ChromeOptions().apply {
            addArguments(
                "--lang=en-EN",
                "--font-render-hinting=none",
                "--force-color-profile=generic-rgb"
            )
            setExperimentalOption("prefs", hashMapOf("intl.accept_languages" to "en-EN"))
        }
        Configuration.browserCapabilities = options
        Configuration.remoteReadTimeout = 240_000
        Configuration.remoteConnectionTimeout = 30_000
        Configuration.remote = config.hubUrl().takeIf { config.hub() == "hub" }
        Configuration.browser = config.browserName()
        val logPrefs = LoggingPreferences()
        logPrefs.enable(LogType.BROWSER, Level.ALL)
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL)
        Configuration.browserCapabilities.setCapability("goog:loggingPrefs", logPrefs)
        Configuration.baseUrl = "/"
        Configuration.timeout = 15000
        Configuration.browserVersion = config.browserVersion()
        System.setProperty("selenide.browserVersion", config.browserVersion())
        Configuration.headless = config.isHeadless()
    }
}
