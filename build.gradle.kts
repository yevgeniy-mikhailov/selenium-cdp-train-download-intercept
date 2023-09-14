plugins {
    alias(libs.plugins.versionCatalogUpdate)
    alias(libs.plugins.pluginVersions)
    alias(libs.plugins.kotlin)
}

group = "org.example.selenium-cdp"
version = "0.0.1"

dependencies {
    implementation(libs.awaitility)
    implementation(libs.nettyHandler)
    implementation(libs.nettyTransportNativeEpoll)
    testImplementation(libs.slf4jSimple)
    implementation(libs.selenide)
    implementation(libs.owner)
    testImplementation(libs.junit5)
    testImplementation(libs.jupiterEngine)
}

tasks.test {
    useJUnitPlatform()
}
