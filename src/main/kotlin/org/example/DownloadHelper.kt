package io.piano.ai.utils

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.webdriver
import io.piano.ai.config.config
import org.awaitility.kotlin.await
import org.openqa.selenium.devtools.HasDevTools
import org.openqa.selenium.devtools.v115.network.Network
import org.openqa.selenium.devtools.v115.network.model.Response
import org.openqa.selenium.remote.Augmenter
import java.lang.IllegalArgumentException
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

class DownloadHelper {
    companion object {
        fun downloadFile(filename: String? = null, actions: () -> Unit): String {
            if (config.hub() == "local") {
                val driver = webdriver().`object`()
                val devTools = (Augmenter().augment(driver) as HasDevTools).devTools
                devTools.createSession()
                devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()))
                val capturedResponses = Collections.synchronizedList(ArrayList<Response>())
                devTools.addListener(
                    Network.responseReceived()
                ) { responseReceived ->
                    if (responseReceived.response.mimeType == "text/csv") {
                        capturedResponses.add(
                            responseReceived.response
                        )
                    }
                }
                actions()
                await.alias("Export").atMost(1, TimeUnit.SECONDS).with().pollInSameThread()
                    .pollInterval(Duration.ofMillis(50)).until {
                        capturedResponses.size > 0
                    }
                val export = capturedResponses.first()
                require(export.status == 200)
                return export.headers.get("content-disposition").toString().substringAfter("filename=")
            } else if (config.hub() == "hub") {
                actions()
                val sessionId = webdriver().driver().sessionId
                val download = Selenide.download(
                    "${
                        config.hubUrl().substringBefore("/wd/hub")
                    }/download/$sessionId/$filename"
                )
                return download.name
            } else throw IllegalArgumentException("config.hub() expected to have `local` or `hub` value")
        }
    }
}
