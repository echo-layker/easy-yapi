package com.itangcent.idea.plugin.json

import com.google.inject.Inject
import com.itangcent.common.kit.KVUtils
import com.itangcent.intellij.context.ActionContext
import com.itangcent.intellij.extend.guice.with
import com.itangcent.mock.BaseContextTest
import org.junit.jupiter.api.BeforeEach
import kotlin.reflect.KClass

/**
 * Test case of [JsonFormatter]
 */
internal abstract class JsonFormatterTest : BaseContextTest() {

    @Inject
    lateinit var jsonFormatter: JsonFormatter

    @Inject
    lateinit var model: Any

    protected abstract val formatClass: KClass<out JsonFormatter>

    override fun bind(builder: ActionContext.ActionContextBuilder) {
        builder.bind(JsonFormatter::class) { it.with(formatClass) }
    }

    @BeforeEach
    fun buildModel() {
        model = mapOf<Any?, Any?>(
            "string" to "abc",
            "int" to 1,
            1 to "int",
            "null" to null,
            null to "null",
            "array" to arrayOf<Any?>("def", 2, emptyMap<Any?, Any?>()),
            "list" to listOf<Any?>("ghi", 3, mapOf("x" to 1, 2 to "y"), emptyArray<Any?>()),
            "map" to mapOf<Any?, Any?>("x" to 1, 2 to "y", "empty" to emptyList<Any?>()),
            "any" to Any()
        )
        KVUtils.addKeyComment(model, "string", "a string")
        KVUtils.addKeyComment(model, "int", "a int")
        KVUtils.addKeyOptions(
            model, "int", arrayListOf(
                hashMapOf("value" to 1, "desc" to "ONE"),
                hashMapOf("value" to 2, "desc" to "TWO")
            )
        )
        KVUtils.addKeyComment(model, "list", "list")
        KVUtils.addKeyComment(model, "list.x", "The value of the x axis")
        KVUtils.addKeyComment(model, "map", "map\nmap")
        KVUtils.addKeyComment(model, "map.x", "The value of the x axis\nin map")
    }
}