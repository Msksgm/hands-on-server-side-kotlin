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

	/**
	 * springdoc
	 *
	 * URL
	 * - https://springdoc.org/
	 * Main 用途
	 * - OpenAPI 仕様に基づいたドキュメントを生成する
	 * Sub 用途
	 * - なし
	 * 概要
	 * コードから OpenAPI 仕様に基づいたドキュメントの生成ライブラリ
	 */
	id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
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

	/**
	 * jqwik
	 *
	 * URL
	 * - https://jqwik.net/
	 * MavenCentral
	 * - https://mvnrepository.com/artifact/net.jqwik/jqwik
	 * - https://mvnrepository.com/artifact/net.jqwik/jqwik-kotlin
	 * Main用途
	 * - Property Based Testing(pbt)
	 * 概要
	 * - Property Based Testingをするのに便利なライブラリ
	 * 参考
	 * - https://medium.com/criteo-engineering/introduction-to-property-based-testing-f5236229d237
	 * - https://johanneslink.net/property-based-testing-in-kotlin/#jqwiks-kotlin-support
	 */
	testImplementation("net.jqwik:jqwik:1.8.2")
	testImplementation("net.jqwik:jqwik-kotlin:1.8.2")

	/**
	 * Spring Boot Starter Validation
	 *
	 * MavenCentral
	 * - https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
	 * Main用途
	 * - コントローラーのバリデーションのために利用する
	 * Sub用途
	 * - 無し
	 * 概要
	 * - Validation を実装した際に、本ライブラリがなければ、バリデーションが動作しない
	 */
	implementation("org.springframework.boot:spring-boot-starter-validation")

	/**
	 * springdoc の gradle 拡張
	 *
	 * 概要
	 * - CLI から springdoc を利用して OpenAPI を 生成する
	 */
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	/**
	 * Spring JDBC
	 *
	 * URL
	 * - https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/package-summary.html
	 * MavenCentral
	 * - https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc
	 * Main用途
	 * - DBへ保存
	 * 概要
	 * - 特になし
	 *
	 * これを入れるだけで、application.properties/yamlや@ConfigurationによるDB接続設定が必要になる
	 */
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	/**
	 * postgresql
	 *
	 * URL
	 * - https://jdbc.postgresql.org/
	 * MavenCentral
	 * - https://mvnrepository.com/artifact/org.postgresql/postgresql
	 * Main用途
	 * - DBつなぐ時のドライバ
	 * 概要
	 * - 特になし
	 */
	implementation("org.postgresql:postgresql")
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
