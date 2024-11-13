<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crash Catcher SDK for Android</title>
</head>
<body>

<h1 style="text-align: center;">Crash Catcher SDK for Android</h1>
<p style="text-align: center;">Easily capture, log, and report crashes in your Android app using Kotlin and Jetpack Compose.</p>

<h2>Features</h2>
<ul>
    <li><b>Crash Logging</b>: Automatically captures and logs application crashes.</li>
    <li><b>Customizable Reports</b>: Allows you to include custom metadata and user feedback with crash reports.</li>
    <li><b>User-Friendly Interface</b>: Includes a pre-built Compose UI for collecting crash reports from users.</li>
</ul>

<h2>Getting Started</h2>

<h3>Prerequisites</h3>
<ul>
    <li><b>Android Studio</b> 4.2 or higher</li>
    <li><b>Kotlin</b> 1.7 or higher</li>
    <li><b>Jetpack Compose</b> 1.0 or higher</li>
</ul>

<h3>Installation</h3>
<p>Add the following dependency to your project’s <code>build.gradle</code> file:</p>

<pre><code>dependencies {
    implementation("com.github.krissirk0906:CrashCatcher:Tag")
}</code></pre>

<p>Ensure that your app’s <code>build.gradle</code> includes <code>mavenCentral</code>:</p>

<pre><code>repositories {
    mavenCentral()
}</code></pre>

<h3>Initialization</h3>
<p>Initialize Crash Catcher in your <code>Activity</code> class:</p>

<pre><code>class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CrashHandler.initialize(this) }</code></pre>

<h3>Basic Usage</h3>
<p>Once initialized, Crash Catcher automatically captures and logs uncaught exceptions in your app. Optionally, you can trigger a custom crash report programmatically:</p>

<h3>Jetpack Compose Integration</h3>
<p>Crash Catcher provides a pre-built Compose UI to display a crash report dialog. You can launch it when a crash occurs:</p>

<h2>License</h2>
<p>Crash Catcher SDK is released under the <a href="LICENSE">MIT License</a>.</p>

<h2>Contributing</h2>
<p>Contributions are welcome! Please open an issue or submit a pull request.</p>

</body>
</html>
