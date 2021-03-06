plugins {
    id("org.jetbrains.intellij") version "0.4.20"
    java
}

group = "io.github.cdgeass"
version = "1.2.9"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    implementation("com.github.jsqlparser:jsqlparser:3.1")
    compileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    type = "IU"
    version = "IU-2020.3"
    setPlugins("java", "com.intellij.database")
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
    1.2.9</br>
    <ul>
        <li>fix issues(<a href="https://github.com/cdgeass/my-mybatis-plugin/issues/6">#6</a>)</li>
        <li>fix item in foreach</li>
    </ul>
      """)
}
tasks.publishPlugin {
    channels("stable")
    token(System.getenv("ORG_GRADLE_PROJECT_intellijPublishToken"))
}
