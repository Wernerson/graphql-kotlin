description = "GraphQL client serializer based on Gson"

val gsonVersion: String by project

dependencies {
    api(project(path = ":graphql-kotlin-client"))
    api("com.google.code.gson:gson:$gsonVersion")
}

tasks {
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    counter = "INSTRUCTION"
                    value = "COVEREDRATIO"
                    minimum = "0.89".toBigDecimal()
                }
            }
        }
    }
}
