#buildKonfig
https://github.com/yshrsmz/BuildKonfig
```toml
[versions]
buildkonfigGradlePlugin = "0.15.1"

[libraries]
buildkonfig-gradle-plugin = { module = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin", version.ref = "buildkonfigGradlePlugin" }
```
project-level build.gradle

```kotlin
buildscript {
    dependencies{
        classpath (libs.buildkonfig.gradle.plugin)
    }
}
```
app-or-module-level build.gradle
```kotlin
plugins {
    id("com.codingfeline.buildkonfig")
}
...
buildkonfig {

    packageName = "com.sample.kmm"
    exposeObjectWithName = "KmmNetwork"

    defaultConfigs {

    }
}
```
-------------------
#Ktor <br>
https://ktor.io/docs/getting-started-ktor-client-multiplatform-mobile.html#ktor-dependencies
```toml
[versions]
ktor = "2.3.7"
coroutines = "1.7.3"

[libraries]
#Ktor
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
#coroutines
kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "coroutines" }
```
androidMain
```kotlin
actual fun getClient(
    config:HttpClientConfig<HttpClientEngineConfig>.()-> Unit
):HttpClient{
    return HttpClient(OkHttp, config)
}
```

iosMain
```kotlin
actual fun getClient(
    config:HttpClientConfig<HttpClientEngineConfig>.()-> Unit
):HttpClient{
    return HttpClient(Darwin, config)
}
```
commonMian
```kotlin
expect fun getClient(
    config:HttpClientConfig<HttpClientEngineConfig>.()-> Unit
):HttpClient

object Network {
    private val client: HttpClient by lazy {
        getClient(config = {
            install(Logging)
            defaultRequest {
                host = ""
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        })
    }
}

```

------------
