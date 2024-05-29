
plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.arr.nautaclear"
    compileSdk = 33
    buildToolsVersion = "34.0.0"

    defaultConfig {
        minSdk = 21
        
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = false
        
    }
    
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("javax.mail:mail:1.4.7")
}
android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.applifycu"
            artifactId = "nautaclear"
            version = "1.0.0-alpha2"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}
