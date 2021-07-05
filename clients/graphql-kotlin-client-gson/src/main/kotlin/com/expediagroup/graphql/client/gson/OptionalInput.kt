package com.expediagroup.graphql.client.gson

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

@JsonAdapter(OptionalInputTypeAdapter::class)
sealed class OptionalInput<out T> {

    object Undefined : OptionalInput<Nothing>() {
        override fun toString(): String = "Undefined"
    }

    data class Defined<out U>(val value: U?) : OptionalInput<U>()
}

internal class OptionalInputTypeAdapter : TypeAdapter<OptionalInput<*>>() {
    override fun write(out: JsonWriter, value: OptionalInput<*>?) {
        when (value) {
            null -> out.nullValue()
            is OptionalInput.Defined -> out
        }
    }

    override fun read(`in`: JsonReader?): OptionalInput<*> {
        TODO("Not yet implemented")
    }
}
