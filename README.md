# selenium-cdp-train-download-intercept
An exercise to obtain info for a file downloaded through POST request by clicking on export button

To run this example you need to provide following ENV variables:
* HUB = 
  * local (for local run)
  * hub (when you want to run on Selenoid/Moon/Selenosis
* HUB_URL = Selenoid url when you set HUB=hub
* BROWSER_NAME = chrome
* BROWSER_VERSION = 115.0
* BROWSER_HEADLESS = true

Used tools:
* Kotlin
* Junit5
* Gradle (using version catalog)
* Owner
* Awaitility
* Selenide