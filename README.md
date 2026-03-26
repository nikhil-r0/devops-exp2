# DevOps Experiment 2 (devops-exp2)

This repository demonstrates how to set up, build, test, and execute a Java application using both **Maven** and **Gradle**, and automate the workflow using a **Jenkins CI/CD pipeline**.

---

## 📌 Prerequisites

* Java Development Kit (JDK) 8 or 17
* Apache Maven (3.x)
* Gradle (8.x)
* Jenkins (for CI/CD automation)
* Chrome Browser and ChromeDriver (for Selenium tests)

---

## 🧩 Part 1: Application Code & Tests

### 1. Java Application

`src/main/java/org/example/App.java`

A simple Java program that prints a greeting and calculates a sum.

```java
package org.example;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, Maven");
        System.out.println("This is the simple realworld example....");

        int a = 5;
        int b = 10;
        System.out.println("Sum of " + a + " and " + b + " is " + sum(a, b));
    }

    public static int sum(int x, int y) {
        return x + y;
    }
}
```

---

### 2. Unit Test Script

`src/test/java/org/example/AppTest.java`

A TestNG script to verify the `sum` method.

```java
package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void testSum() {
        int expected = 15;
        int actual = App.sum(5, 10);
        Assert.assertEquals(actual, expected, "The sum method did not return the expected result!");
    }
}
```

---

### 3. Selenium Web UI Test

`src/test/java/org/test/Webpage/WebpageTest.java`

A Selenium + TestNG script to validate a webpage title.

```java
package org.test.Webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WebpageTest {
    private static WebDriver driver;

    @BeforeTest
    public void openBrowser() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Thread.sleep(2000);
        driver.get("https://nikhil-r0.github.io/devops-exp2/");
    }

    @Test
    public void titleValidationTest(){
        String actualTitle = driver.getTitle();
        String expectedTitle = "Ecommerce Demo";
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @AfterTest
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
```

---

## ⚙️ Part 2: Maven Build System

### 1. Maven Configuration

`pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>exp2</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.5.0</version>
                <executions>
                    <execution>
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.basedir}/docs</outputDirectory>
                            <resources>
                                <resource>
                                    <directory>src/main/resources</directory>
                                    <includes>
                                        <include>**/*</include>
                                    </includes>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

---

### 2. Maven Commands

```bash
# Clean and build
mvn clean install

# Compile and run application
mvn compile exec:java -Dexec.mainClass="org.example.App"
```

---

## ⚙️ Part 3: Gradle Build System

### 1. Groovy DSL (`build.gradle`)

```groovy
plugins {
    id 'java-library'
    id 'maven-publish'
    id 'application'
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api 'org.seleniumhq.selenium:selenium-java:4.15.0'
    testImplementation 'org.testng:testng:7.7.0'
}

application {
    mainClass = 'org.example.App'
}

group = 'org.example'
version = '1.0-SNAPSHOT'
sourceCompatibility = '1.8'

test {
    useTestNG()
}
```

---

### 2. Kotlin DSL (`build.gradle.kts`)

```kotlin
plugins {
    `java-library`
    `maven-publish`
    application
}

application {
    mainClass.set("org.example.App")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.seleniumhq.selenium:selenium-java:4.15.0")
    testImplementation("org.testng:testng:7.7.0")
}

group = "org.example"
version = "1.0-SNAPSHOT"
description = "exp2"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.named<Test>("test") {
    useTestNG()
}
```

---

### 3. Gradle Commands

```bash
# Run tests
gradle test

# Run application
gradle run
```

---

## 🚀 Part 4: Jenkins CI/CD Automation

### Jenkins Pipeline (`Jenkinsfile`)

```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building Project...'
            }
        }

        stage('Test') {
            steps {
                echo 'Running Tests...'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying Application...'
            }
        }
    }
}
```

---

## 🛠️ Troubleshooting Notes

* **Maven invalid target release**
  Ensure `<maven.compiler.target>` matches your installed JDK (`mvn -v`).

* **Maven Resources Plugin Error**
  `<includes>` must be inside the `<resource>` block.

* **Gradle Kotlin vs Groovy DSL**
  Use correct syntax for each (e.g., `mainClass.set()` in Kotlin).

* **ClassNotFoundException (Maven Exec)**
  Always run:

  ```bash
  mvn compile exec:java
  ```

---

## ✅ Summary

This experiment demonstrates:

* Java application development
* Unit testing with TestNG
* UI testing with Selenium
* Build automation using Maven & Gradle
* CI/CD pipeline setup using Jenkins

---
