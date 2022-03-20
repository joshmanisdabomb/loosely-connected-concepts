package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.render.model.json.Transformation
import net.minecraft.item.Item
import net.minecraft.util.math.Vec3f

open class DynamicItemAssetFactory(val transform: ModelTransformation = ModelTransformation.NONE, val light: String? = null) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        data.models[idh.loc(entry)] = { data.parser.parse(getTemplate(transform, light)) }
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

        val item = ModelTransformation(
            Transformation(Vec3f(0f, 0f, 0f), Vec3f(0f, 3f, 1f), Vec3f(0.55f, 0.55f, 0.55f)),
            Transformation(Vec3f(0f, 0f, 0f), Vec3f(0f, 3f, 1f), Vec3f(0.55f, 0.55f, 0.55f)),
            Transformation(Vec3f(0f, -90f, 25f), Vec3f(1.13f, 3.2f, 1.13f), Vec3f(0.68f, 0.68f, 0.68f)),
            Transformation(Vec3f(0f, -90f, 25f), Vec3f(1.13f, 3.2f, 1.13f), Vec3f(0.68f, 0.68f, 0.68f)),
            Transformation(Vec3f(0f, 180f, 0f), Vec3f(0f, 13f, 7f), Vec3f(1f, 1f, 1f)),
            Transformation.IDENTITY,
            Transformation(Vec3f(0f, 0f, 0f), Vec3f(0f, 2f, 0f), Vec3f(0.5f, 0.5f, 0.5f)),
            Transformation(Vec3f(0f, 180f, 0f), Vec3f(0f, 0f, 0f), Vec3f(1f, 1f, 1f))
        )

        private fun serializeTransform(transform: Transformation): String {
            return """"rotation": [${transform.rotation.x}, ${transform.rotation.y}, ${transform.rotation.z}],
      "translation": [${transform.translation.x}, ${transform.translation.y}, ${transform.translation.z}],
      "scale": [${transform.scale.x}, ${transform.scale.y}, ${transform.scale.z}]"""
        }

        private fun getTemplate(transform: ModelTransformation, light: String?): String {
            return """{
  "parent": "minecraft:builtin/entity",
  ${if (light != null) "\"gui_light\": \"$light\"," else ""}
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
