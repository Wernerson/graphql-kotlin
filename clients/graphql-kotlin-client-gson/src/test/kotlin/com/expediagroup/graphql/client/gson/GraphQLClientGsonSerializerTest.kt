/*
 * Copyright 2021 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expediagroup.graphql.client.gson

import com.expediagroup.graphql.client.gson.data.*
import com.expediagroup.graphql.client.gson.data.enums.TestEnum
import com.expediagroup.graphql.client.gson.data.polymorphicquery.SecondInterfaceImplementation
import com.google.gson.JsonParser
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class GraphQLClientGsonSerializerTest {

    private val serializer = GraphQLClientGsonSerializer()

    @Test
    fun `verify we can serialize GraphQLClientRequest`() {
        val testQuery = FirstQuery(FirstQuery.Variables(input = 1.0f))
        val expected =
            """{
            |  "query": "FIRST_QUERY",
            |  "operationName": "FirstQuery",
            |  "variables": { "input": 1.0 }
            |}
        """.trimMargin()

        val result = serializer.serialize(testQuery)
        assertEquals(JsonParser.parseString(expected), JsonParser.parseString(result))
    }

    @Test
    fun `verify we can serialize batch GraphQLClientRequest`() {
        val queries = listOf(FirstQuery(FirstQuery.Variables(input = 1.0f)), OtherQuery())
        val expected =
            """[{
            |  "query": "FIRST_QUERY",
            |  "operationName": "FirstQuery",
            |  "variables": { "input": 1.0 }
            |},{
            |  "query": "OTHER_QUERY",
            |  "operationName": "OtherQuery"
            |}]
        """.trimMargin()

        val result = serializer.serialize(queries)
        assertEquals(JsonParser.parseString(expected), JsonParser.parseString(result))
    }

    @Test
    fun `verify we can deserialize GsonGraphQLResponse`() {
        val testQuery = FirstQuery(variables = FirstQuery.Variables())
        val expected = GsonGraphQLResponse(
            data = FirstQuery.Result("hello world"),
            errors = listOf(
                GsonGraphQLError(
                    message = "test error message",
                    locations = listOf(GsonGraphQLSourceLocation(1, 1)),
                    path = listOf("firstQuery", 0),
                    extensions = mapOf("errorExt" to 123)
                )
            ),
            extensions = mapOf(
                "extBool" to true,
                "extDouble" to 1.5,
                "extInt" to 123,
                "extList" to listOf("ext1", "ext2"),
                "extMap" to mapOf("1" to 1, "2" to 2.0),
                "extNull" to null,
                "extString" to "extra"
            )
        )
        val rawResponse =
            """{
            |  "data": { "stringResult" : "hello world" },
            |  "errors": [{
            |    "message": "test error message",
            |    "locations": [{ "line": 1, "column": 1 }],
            |    "path": [ "firstQuery", 0 ],
            |    "extensions": {
            |      "errorExt": 123
            |    }
            |  }],
            |  "extensions" : {
            |    "extBool": true,
            |    "extDouble": 1.5,
            |    "extInt": 123,
            |    "extList": ["ext1", "ext2"],
            |    "extMap": { "1" : 1, "2" : 2.0 },
            |    "extNull": null,
            |    "extString": "extra"
            |  }
            |}
        """.trimMargin()

        val result = serializer.deserialize(rawResponse, testQuery.responseType())
        assertEquals(expected, result)
    }

    @Test
    fun `verify we can deserialize batch GsonGraphQLResponse`() {
        val testQuery = FirstQuery(variables = FirstQuery.Variables())
        val otherQuery = OtherQuery()
        val expected = listOf(
            GsonGraphQLResponse(
                data = FirstQuery.Result("hello world"),
                errors = listOf(GsonGraphQLError(message = "test error message")),
                extensions = mapOf("extVal" to 123, "extList" to listOf("ext1", "ext2"), "extMap" to mapOf("1" to 1, "2" to 2))
            ),
            GsonGraphQLResponse(
                data = OtherQuery.Result(stringResult = "goodbye world", integerResult = 42)
            )
        )
        val rawResponses =
            """[{
            |  "data": { "stringResult" : "hello world" },
            |  "errors": [{ "message" : "test error message" }],
            |  "extensions" : { "extVal" : 123, "extList" : ["ext1", "ext2"], "extMap" : { "1" : 1, "2" : 2} }
            |}, {
            |  "data": { "stringResult" : "goodbye world", "integerResult" : 42 }
            |}]
        """.trimMargin()

        val result = serializer.deserialize(rawResponses, listOf(testQuery.responseType(), otherQuery.responseType()))
        assertEquals(expected, result)
    }

    @Test
    fun `verify we can deserialize polymorphic response`() {
        val polymorphicResponse =
            """{
            |  "data": {
            |    "polymorphicResult": {
            |      "__typename": "SecondInterfaceImplementation",
            |      "id": 123,
            |      "floatValue": 1.2
            |    }
            |  }
            |}
        """.trimMargin()

        val result = serializer.deserialize(polymorphicResponse, PolymorphicQuery().responseType())
        assertEquals(SecondInterfaceImplementation(123, 1.2f), result.data?.polymorphicResult)
    }

    @Test
    fun `verify we can serialize custom scalars`() {
        val randomUUID = UUID.randomUUID()
        val scalarQuery = ScalarQuery(variables = ScalarQuery.Variables(alias = "1234", custom = com.expediagroup.graphql.client.gson.data.scalars.UUID(randomUUID)))
        val rawQuery =
            """{
            |  "query": "SCALAR_QUERY",
            |  "operationName": "ScalarQuery",
            |  "variables": { "alias": "1234", "custom": "$randomUUID" }
            |}
        """.trimMargin()

        val serialized = serializer.serialize(scalarQuery)
        assertEquals(JsonParser.parseString(rawQuery), JsonParser.parseString(serialized))
    }

    @Test
    fun `verify we can deserialize custom scalars`() {
        val expectedUUID = UUID.randomUUID()
        val scalarResponse =
            """{
            |  "data": {
            |    "scalarAlias": "1234",
            |    "customScalar": "$expectedUUID"
            |  }
            |}
        """.trimMargin()

        val result = serializer.deserialize(scalarResponse, ScalarQuery(ScalarQuery.Variables()).responseType())
        assertEquals("1234", result.data?.scalarAlias)
        assertEquals(expectedUUID, result.data?.customScalar?.value)
    }

    @Test
    fun `verify we can deserialize unknown enums`() {
        val unknownResponse =
            """{
            |  "data": { "enumResult": "INVALID" }
            |}
        """.trimMargin()

        val result = serializer.deserialize(unknownResponse, EnumQuery(EnumQuery.Variables()).responseType())
        assertEquals(TestEnum.__UNKNOWN, result.data?.enumResult)
    }

    @Test
    fun `verify we can serialize enums with custom names`() {
        val query = EnumQuery(variables = EnumQuery.Variables(enum = TestEnum.THREE))
        val rawQuery =
            """{
            |  "query": "ENUM_QUERY",
            |  "operationName": "EnumQuery",
            |  "variables": { "enum": "three" }
            |}
        """.trimMargin()

        val serialized = serializer.serialize(query)
        assertEquals(JsonParser.parseString(rawQuery), JsonParser.parseString(serialized))
    }

    @Test
    fun `verify we can deserialize enums with custom names`() {
        val rawResponse =
            """{
            |  "data": { "enumResult": "three" }
            |}
        """.trimMargin()
        val deserialized = serializer.deserialize(rawResponse, EnumQuery(EnumQuery.Variables()).responseType())
        assertEquals(TestEnum.THREE, deserialized.data?.enumResult)
    }

    @Test
    fun `verify we can serialize optional inputs`() {
        val query = InputQuery(
            variables = InputQuery.Variables(
                requiredInput = 123,
                optionalIntInput = OptionalInput.Defined(123),
                optionalStringInput = OptionalInput.Defined(null)
            )
        )
        val rawQuery =
            """{
            |  "query": "INPUT_QUERY",
            |  "operationName": "InputQuery",
            |  "variables": {
            |    "requiredInput": 123,
            |    "optionalIntInput": 123,
            |    "optionalStringInput": null
            |  }
            |}
        """.trimMargin()

        val serialized = serializer.serialize(query)
        assertEquals(JsonParser.parseString(rawQuery), JsonParser.parseString(serialized))
    }
}
