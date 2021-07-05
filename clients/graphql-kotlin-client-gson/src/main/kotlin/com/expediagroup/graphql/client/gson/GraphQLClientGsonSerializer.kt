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

import com.expediagroup.graphql.client.serializer.GraphQLClientSerializer
import com.expediagroup.graphql.client.types.GraphQLClientError
import com.expediagroup.graphql.client.types.GraphQLClientRequest
import com.expediagroup.graphql.client.types.GraphQLClientResponse
import com.expediagroup.graphql.client.types.GraphQLClientSourceLocation
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser.parseString
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

data class GsonGraphQLSourceLocation(
    override val line: Int,
    override val column: Int
) : GraphQLClientSourceLocation

data class GsonGraphQLError(
    override val message: String,
    override val locations: List<GsonGraphQLSourceLocation>? = null,
    override val path: List<Any>? = null,
    override val extensions: Map<String, Any?>? = null,
) : GraphQLClientError

data class GsonGraphQLResponse<T>(
    override val data: T? = null,
    override val errors: List<GsonGraphQLError>? = null,
    override val extensions: Map<String, Any?>? = null
) : GraphQLClientResponse<T>

/**
 * Gson based GraphQL request/response serializer.
 */
class GraphQLClientGsonSerializer(
    private val gson: Gson = Gson()
) : GraphQLClientSerializer {

    private val typeCache = ConcurrentHashMap<KClass<*>, Type>()

    private fun <T : Any> parameterizedType(type: KClass<T>): Type =
        typeCache.computeIfAbsent(type) {
            TypeToken.getParameterized(GsonGraphQLResponse::class.java, type.java).type
        }

    override fun serialize(request: GraphQLClientRequest<*>): String = gson.toJson(request)

    override fun serialize(requests: List<GraphQLClientRequest<*>>): String = gson.toJson(requests)

    override fun <T : Any> deserialize(rawResponse: String, responseType: KClass<T>): GraphQLClientResponse<T> =
        gson.fromJson(rawResponse, parameterizedType(responseType))

    override fun deserialize(rawResponses: String, responseTypes: List<KClass<*>>): List<GraphQLClientResponse<*>> {
        val response = parseString(rawResponses)
        return if (response is JsonArray) {
            response.mapIndexed { index, element ->
                val type = parameterizedType(responseTypes[index])
                gson.fromJson(element, type)
            }
        } else {
            // should never be the case
            val type = parameterizedType(responseTypes.first())
            listOf(gson.fromJson(response, type))
        }
    }
}
