package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.render.model.json.Transformation
import net.minecraft.item.Item
import net.minecraft.util.math.Vec3f

open class DynamicItemAssetFactory(val transform: ModelTransformation = ModelTransformation.NONE) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        data.modelStates.addModel(loc(entry)) { DataUtils.parser.parse(getTemplate(transform)) }
    }

    companion object : DynamicItemAssetFactory() {
        val block = ModelTransformation(
            Transformation(Vec3f(75f, 45f, 0f), Vec3f(0f, 2.5f, 0f), Vec3f(0.375f, 0.375f, 0.375f)),
            Transformation(Vec3f(75f, 45f, 0f), Vec3f(0f, 2.5f, 0f), Vec3f(0.375f, 0.375f, 0.375f)),
            Transformation(Vec3f(0f, 45f, 0f), Vec3f(0f, 0f, 0f), Vec3f(0.4f, 0.4f, 0.4f)),
            Transformation(Vec3f(0f, 225f, 0f), Vec3f(0f, 0f, 0f), Vec3f(0.4f, 0.4f, 0.4f)),
            Transformation(Vec3f(0f, 0f, 0f), Vec3f(0f, 0f, 0f), Vec3f(1f, 1f, 1f)),
            Transformation(Vec3f(30f, 225f, 0f), Vec3f(0f, 0f, 0f), Vec3f(0.625f, 0.625f, 0.625f)),
            Transformation(Vec3f(0f, 0f, 0f), Vec3f(0f, 3f, 0f), Vec3f(0.25f, 0.25f, 0.25f)),
            Transformation(Vec3f(0f, 0f, 0f), Vec3f(0f, 0f, 0f), Vec3f(0.5f, 0.5f, 0.5f))
        )

        private fun serializeTransform(transform: Transformation): String {
            return """"rotation": [${transform.rotation.x}, ${transform.rotation.y}, ${transform.rotation.z}],
      "translation": [${transform.translation.x}, ${transform.translation.y}, ${transform.translation.z}],
      "scale": [${transform.scale.x}, ${transform.scale.y}, ${transform.scale.z}]"""
        }

        private fun getTemplate(transform: ModelTransformation): String {
            return """{
  "parent": "minecraft:builtin/entity",
  "display": {
    "thirdperson_righthand": {
      ${serializeTransform(transform.thirdPersonRightHand)}
    },
    "thirdperson_lefthand": {
      ${serializeTransform(transform.thirdPersonLeftHand)}
    },
    "firstperson_righthand": {
      ${serializeTransform(transform.firstPersonRightHand)}
    },
    "firstperson_lefthand": {
      ${serializeTransform(transform.firstPersonLeftHand)}
    },
    "ground": {
      ${serializeTransform(transform.ground)}
    },
    "fixed": {
      ${serializeTransform(transform.fixed)}
    },
    "gui": {
      ${serializeTransform(transform.gui)}
    },
    "head": {
      ${serializeTransform(transform.head)}
    }
  }
}"""
        }
    }

}
