plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("org.testng:testng:6.14.3")


}

tasks.test {

    useTestNG() { //Tells Gradle to use TestNG
        useDefaultListeners = true // Tells TestNG to execute its default reporting structure
        suites("src/test/testng.xml") //location of our suite.xml
    }
}

