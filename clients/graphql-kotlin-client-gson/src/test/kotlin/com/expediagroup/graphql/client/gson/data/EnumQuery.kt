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

package com.expediagroup.graphql.client.gson.data

import com.expediagroup.graphql.client.gson.data.enums.TestEnum
import com.expediagroup.graphql.client.types.GraphQLClientRequest
import kotlin.reflect.KClass

class EnumQuery(
    override val variables: Variables
) : GraphQLClientRequest<EnumQuery.Result> {
    override val query: String = "ENUM_QUERY"

    override val operationName: String = "EnumQuery"

    override fun responseType(): KClass<Result> = Result::class

    data class Variables(
        val enum: TestEnum? = null
    )

    data class Result(
        val enumResult: TestEnum = TestEnum.__UNKNOWN
    )
}
