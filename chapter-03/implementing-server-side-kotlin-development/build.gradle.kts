import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	/**
	 * Kotlin のバージョン
	 * Kotlin のバージョン更新の場合はここを更新する
	 * Kotlin のライブラリによっては、バージョンが合わないとエラーが発生するので、そちらも合わせて更新する
	 */
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"

	/**
	 * detekt
	 *
	 * URL
	 * - https://github.com/detekt/detekt
	 * GradlePlugins(plugins.gradle.org)
	 * - https://plugins.gradle.org/plugin/io.gitlab.arturbosch.detekt
	 * Main用途
	 * - Linter/Formatter
	 * Sub用途
	 * - 無し
	 * 概要
	 * KotlinのLinter/Formatter
	 */
	id("io.gitlab.arturbosch.detekt") version "1.23.5"

	/**
	 * dokka
	 *
	 * URL
	 * - https://github.com/Kotlin/dokka
	 * GradlePlugins(plugins.gradle.org)
	 * - https://plugins.gradle.org/plugin/org.jetbrains.dokka
	 * Main用途
	 * - ドキュメント生成
	 * Sub用途
	 * - 特になし
	 * 概要
	 * - JDocの代替(=KDoc)
	 */
	id("org.jetbrains.dokka") version "1.9.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	/**
	 * detektの拡張: detekt-formatting
	 *
	 * 概要
	 * - formattingのルール
	 * - 基本はktlintと同じ
	 * - format自動適用オプションの autoCorrect が使えるようになる
	 */
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")

	/**
	 * dokkaHtmlPlugin
	 *
	 * dokka Pluginを適用するのに必要
	 */
	dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.10")

	/**
	 * Arrow Core
	 *
	 * URL
	 * - https://arrow-kt.io/
	 * MavenCentral
	 * - https://mvnrepository.com/artifact/io.arrow-kt/arrow-core
	 * Main用途
	 * - Either/Validatedを使ったRailway Oriented Programming
	 * Sub用途
	 * - Optionを使ったletの代替
	 * 概要
	 * - Kotlinで関数型プログラミングをするときに便利なライブラリ
	 */
	implementation("io.arrow-kt:arrow-core:1.2.1")

	/**
	 * AssertJ
	 *
	 * URL
	 * - https://assertj.github.io/doc/
	 * MavenCentral
	 * - https://mvnrepository.com/artifact/org.assertj/assertj-core
	 * Main用途
	 * - JUnitでassertThat(xxx).isEqualTo(yyy)みたいな感じで比較時に使う
	 * Sub用途
	 * - 特になし
	 * 概要
	 * - JUnit等を直感的に利用するためのライブラリ
	 */
	testImplementation("org.assertj:assertj-core:3.25.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

/**
 * detektの設定
 *
 * 基本的に全て `detekt-override.yml` で設定する
 */
detekt {
	/**
	 * ./gradlew detektGenerateConfig でdetekt.ymlが生成される(バージョンが上がる度に再生成する)
	 */
	config = files(
		"$projectDir/config/detekt/detekt.yml",
		"$projectDir/config/detekt/detekt-override.yml",
	)
}
