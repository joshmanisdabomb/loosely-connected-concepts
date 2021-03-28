package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import net.minecraft.data.client.model.Model
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier
import java.util.*

object LCCModelTemplates : BasicDirectory<Model, Unit>() {

    val aligned_cross by templateEntry("block", aligned_cross_json, TextureKey.CROSS)
    val generated1 by referenceEntry(Identifier("minecraft", "item/generated"), TextureKey.LAYER0, LCCModelTextureKeys.layer1)

    val cable4_center by templateEntry("block", cable4_center_json, TextureKey.END, TextureKey.PARTICLE)
    val cable4_connection by templateEntry("block", cable4_connection_json, TextureKey.SIDE, TextureKey.END, TextureKey.PARTICLE)
    val cable4_item by templateEntry("block", cable4_item_json, TextureKey.SIDE, TextureKey.END)

    val template_bounce_pad by templateEntry("block", template_bounce_pad_block_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_0 by templateEntry("block", template_bounce_pad_0_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_1 by templateEntry("block", template_bounce_pad_1_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_2 by templateEntry("block", template_bounce_pad_2_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_3 by templateEntry("block", template_bounce_pad_3_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_4 by templateEntry("block", template_bounce_pad_4_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_item by templateEntry("item", template_bounce_pad_item_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5)
    val template_spawner_table by templateEntry("block", template_spawner_table_json, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM, TextureKey.PARTICLE)
    val template_cog by templateEntry("block", template_cog_json, TextureKey.FRONT, TextureKey.BACK, TextureKey.PARTICLE)
    val template_solar_panel by templateEntry("item", template_solar_panel_json, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM)
    val template_turbine by templateEntry("block", template_turbine_json, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM, TextureKey.PARTICLE)
    val template_atomic_bomb_head by templateEntry("block", template_atomic_bomb_head_json, LCCModelTextureKeys.t2, LCCModelTextureKeys.t5, TextureKey.PARTICLE)
    val template_atomic_bomb_middle by templateEntry("block", template_atomic_bomb_middle_json, LCCModelTextureKeys.t2, LCCModelTextureKeys.t5, TextureKey.PARTICLE)
    val template_atomic_bomb_tail by templateEntry("block", template_atomic_bomb_tail_json, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, TextureKey.PARTICLE)
    val template_atomic_bomb_item by templateEntry("item", template_atomic_bomb_item_json, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5)
    val template_face_up_down by templateEntry("block", template_face_up_down_json, TextureKey.TEXTURE, TextureKey.PARTICLE)
    val template_face_north by templateEntry("block", template_face_north_json, TextureKey.TEXTURE)
    val template_face_east by templateEntry("block", template_face_east_json, TextureKey.TEXTURE)
    val template_face_south by templateEntry("block", template_face_south_json, TextureKey.TEXTURE)
    val template_face_west by templateEntry("block", template_face_west_json, TextureKey.TEXTURE)
    val template_treetap by templateEntry("block", template_treetap_json, LCCModelTextureKeys.t0)
    val template_treetap_overflow by templateEntry("block", template_treetap_overflow_json, LCCModelTextureKeys.t2)
    val template_treetap_bowl by templateEntry("block", template_treetap_bowl_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1)
    val template_treetap_bowl_1 by templateEntry("block", template_treetap_bowl_1_json, LCCModelTextureKeys.t2)
    val template_treetap_bowl_2 by templateEntry("block", template_treetap_bowl_2_json, LCCModelTextureKeys.t2)
    val template_treetap_bowl_3 by templateEntry("block", template_treetap_bowl_3_json, LCCModelTextureKeys.t2)
    val template_treetap_bowl_dried by templateEntry("block", template_treetap_bowl_dried_json, LCCModelTextureKeys.t2)
    val template_nuclear_generator by templateEntry("block", template_nuclear_generator_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5, LCCModelTextureKeys.t6, LCCModelTextureKeys.t7, TextureKey.PARTICLE)
    val template_nuclear_generator_full by templateEntry("block", template_nuclear_generator_full_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5, LCCModelTextureKeys.t6, LCCModelTextureKeys.t7, LCCModelTextureKeys.t8, LCCModelTextureKeys.t9, TextureKey.PARTICLE)
    val template_nuclear_generator_item by templateEntry("item", template_nuclear_generator_full_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5, LCCModelTextureKeys.t6, LCCModelTextureKeys.t7, LCCModelTextureKeys.t8, LCCModelTextureKeys.t9)

    fun initialiser(input: Model, context: DirectoryContext<Unit>, parameters: Unit) = input.also { if (context.tags.size >= 2) LCCData.accessor.handler.modelStates.addModel(Identifier(LCCData.accessor.modid, "${context.tags[0]}/${context.name}")) { DataUtils.parser.parse(context.tags[1]) } }

    fun referenceEntry(parent: Identifier, vararg keys: TextureKey) = entry(::initialiser) { Model(Optional.of(parent), Optional.empty(), *keys) }
    fun templateEntry(folder: String, json: String, vararg keys: TextureKey) = entry(::initialiser) { Model(Optional.of(Identifier(LCCData.accessor.modid, "${tags[0]}/$name")), Optional.empty(), *keys) }.addTags(folder, json)

    override fun defaultProperties(name: String) = Unit

}

private const val aligned_cross_json =
"""{
  "parent": "minecraft:block/block",
  "ambientocclusion": false,
  "textures": {
    "particle": "#cross"
  },
  "elements": [
    {
      "from": [
        0,
        0,
        8
      ],
      "to": [
        16,
        16,
        8
      ],
      "shade": false,
      "faces": {
        "north": {
          "texture": "#cross"
        },
        "south": {
          "texture": "#cross"
        }
      }
    },
    {
      "from": [
        8,
        0,
        0
      ],
      "to": [
        8,
        16,
        16
      ],
      "shade": false,
      "faces": {
        "west": {
          "texture": "#cross"
        },
        "east": {
          "texture": "#cross"
        }
      }
    }
  ]
}"""

private const val template_bounce_pad_block_json =
"""{
	"parent": "block/block",
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "bottom",
			"from": [0, 0, 0],
			"to": [16, 1, 16],
			"faces": {
				"north": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "north"},
				"east": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "east"},
				"south": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "south"},
				"west": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "west"},
				"up": {"uv": [0, 0, 16, 16], "texture": "#3"},
				"down": {"uv": [0, 0, 16, 16], "texture": "#3", "cullface": "down"}
			}
		},
		{
			"name": "corner1",
			"from": [0, 1, 0],
			"to": [7, 6, 7],
			"faces": {
				"north": {"uv": [9, 10, 16, 15], "texture": "#0"},
				"east": {"uv": [9, 10, 16, 15], "texture": "#0"},
				"south": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "south"},
				"west": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "west"}
			}
		},
		{
			"name": "corner2",
			"from": [9, 1, 0],
			"to": [16, 6, 7],
			"faces": {
				"north": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "north"},
				"east": {"uv": [9, 10, 16, 15], "texture": "#0", "cullface": "east"},
				"south": {"uv": [9, 10, 16, 15], "texture": "#0"},
				"west": {"uv": [0, 10, 7, 15], "texture": "#0"}
			}
		},
		{
			"name": "corner3",
			"from": [9, 1, 9],
			"to": [16, 6, 16],
			"faces": {
				"north": {"uv": [0, 10, 7, 15], "texture": "#0"},
				"east": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "east"},
				"south": {"uv": [9, 10, 16, 15], "texture": "#0", "cullface": "south"},
				"west": {"uv": [9, 10, 16, 15], "texture": "#0"}
			}
		},
		{
			"name": "corner4",
			"from": [0, 1, 9],
			"to": [7, 6, 16],
			"faces": {
				"north": {"uv": [9, 10, 16, 15], "texture": "#0"},
				"east": {"uv": [0, 10, 7, 15], "texture": "#0"},
				"south": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "south"},
				"west": {"uv": [9, 10, 16, 15], "texture": "#0", "cullface": "west"}
			}
		},
		{
			"name": "top",
			"from": [0, 6, 0],
			"to": [16, 7, 16],
			"faces": {
				"north": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "north"},
				"east": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "east"},
				"south": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "south"},
				"west": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "west"},
				"up": {"uv": [0, 0, 16, 16], "texture": "#3"},
				"down": {"uv": [0, 0, 16, 16], "texture": "#3"}
			}
		},
		{
			"name": "inner",
			"from": [1, 1, 1],
			"to": [15, 6, 15],
			"faces": {
				"north": {"uv": [1, 10, 15, 15], "texture": "#1"},
				"east": {"uv": [1, 10, 15, 15], "texture": "#1"},
				"south": {"uv": [1, 10, 15, 15], "texture": "#1"},
				"west": {"uv": [1, 10, 15, 15], "texture": "#1"}
			}
		}
	]
}"""

private const val template_bounce_pad_0_json =
    """{
	"parent": "lcc:block/bounce_pad",
	"elements": [
		{
			"name": "setting1",
			"from": [7, 1, 0],
			"to": [9, 2, 1],
			"faces": {
				"north": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "north"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting2",
			"from": [15, 1, 7],
			"to": [16, 2, 9],
			"faces": {
				"east": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		},
		{
			"name": "setting3",
			"from": [7, 1, 15],
			"to": [9, 2, 16],
			"faces": {
				"south": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "south"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting4",
			"from": [0, 1, 7],
			"to": [1, 2, 9],
			"faces": {
				"west": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		}
	]
}"""

private const val template_bounce_pad_1_json =
"""{
	"parent": "lcc:block/bounce_pad",
	"elements": [
		{
			"name": "setting1",
			"from": [7, 2, 0],
			"to": [9, 3, 1],
			"faces": {
				"north": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "north"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting2",
			"from": [15, 2, 7],
			"to": [16, 3, 9],
			"faces": {
				"east": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		},
		{
			"name": "setting3",
			"from": [7, 2, 15],
			"to": [9, 3, 16],
			"faces": {
				"south": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "south"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting4",
			"from": [0, 2, 7],
			"to": [1, 3, 9],
			"faces": {
				"west": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		}
	]
}"""

private const val template_bounce_pad_2_json =
"""{
	"parent": "lcc:block/bounce_pad",
	"elements": [
		{
			"name": "setting1",
			"from": [7, 3, 0],
			"to": [9, 4, 1],
			"faces": {
				"north": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "north"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting2",
			"from": [15, 3, 7],
			"to": [16, 4, 9],
			"faces": {
				"east": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		},
		{
			"name": "setting3",
			"from": [7, 3, 15],
			"to": [9, 4, 16],
			"faces": {
				"south": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "south"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting4",
			"from": [0, 3, 7],
			"to": [1, 4, 9],
			"faces": {
				"west": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		}
	]
}"""

private const val template_bounce_pad_3_json =
"""{
	"parent": "lcc:block/bounce_pad",
	"elements": [
		{
			"name": "setting1",
			"from": [7, 4, 0],
			"to": [9, 5, 1],
			"faces": {
				"north": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "north"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting2",
			"from": [15, 4, 7],
			"to": [16, 5, 9],
			"faces": {
				"east": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		},
		{
			"name": "setting3",
			"from": [7, 4, 15],
			"to": [9, 5, 16],
			"faces": {
				"south": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "south"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting4",
			"from": [0, 4, 7],
			"to": [1, 5, 9],
			"faces": {
				"west": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		}
	]
}"""

private const val template_bounce_pad_4_json =
"""{
	"parent": "lcc:block/bounce_pad",
	"elements": [
		{
			"name": "setting1",
			"from": [7, 5, 0],
			"to": [9, 6, 1],
			"faces": {
				"north": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "north"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting2",
			"from": [15, 5, 7],
			"to": [16, 6, 9],
			"faces": {
				"east": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		},
		{
			"name": "setting3",
			"from": [7, 5, 15],
			"to": [9, 6, 16],
			"faces": {
				"south": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "south"},
				"up": {"uv": [0, 0, 2, 1], "texture": "#2"},
				"down": {"uv": [0, 0, 2, 1], "texture": "#2"}
			}
		},
		{
			"name": "setting4",
			"from": [0, 5, 7],
			"to": [1, 6, 9],
			"faces": {
				"west": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 1, 2], "texture": "#2"},
				"down": {"uv": [0, 0, 1, 2], "texture": "#2"}
			}
		}
	]
}"""

private const val template_bounce_pad_item_json =
"""{
    "parent": "block/block",
    "credit": "Made with Blockbench",
    "elements": [
        {
            "name": "bottom",
            "from": [0, 0, 0],
            "to": [16, 1, 16],
            "faces": {
                "north": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "north"},
                "east": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "east"},
                "south": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "south"},
                "west": {"uv": [0, 15, 16, 16], "texture": "#0", "cullface": "west"},
                "up": {"uv": [0, 0, 16, 16], "texture": "#3"},
                "down": {"uv": [0, 0, 16, 16], "texture": "#3", "cullface": "down"}
            }
        },
        {
            "name": "corner1",
            "from": [0, 1, 0],
            "to": [7, 6, 7],
            "faces": {
                "north": {"uv": [9, 10, 16, 15], "texture": "#0"},
                "east": {"uv": [9, 10, 16, 15], "texture": "#0"},
                "south": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "south"},
                "west": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "west"}
            }
        },
        {
            "name": "corner2",
            "from": [9, 1, 0],
            "to": [16, 6, 7],
            "faces": {
                "north": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "north"},
                "east": {"uv": [9, 10, 16, 15], "texture": "#0", "cullface": "east"},
                "south": {"uv": [9, 10, 16, 15], "texture": "#0"},
                "west": {"uv": [0, 10, 7, 15], "texture": "#0"}
            }
        },
        {
            "name": "corner3",
            "from": [9, 1, 9],
            "to": [16, 6, 16],
            "faces": {
                "north": {"uv": [0, 10, 7, 15], "texture": "#0"},
                "east": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "east"},
                "south": {"uv": [9, 10, 16, 15], "texture": "#0", "cullface": "south"},
                "west": {"uv": [9, 10, 16, 15], "texture": "#0"}
            }
        },
        {
            "name": "corner4",
            "from": [0, 1, 9],
            "to": [7, 6, 16],
            "faces": {
                "north": {"uv": [9, 10, 16, 15], "texture": "#0"},
                "east": {"uv": [0, 10, 7, 15], "texture": "#0"},
                "south": {"uv": [0, 10, 7, 15], "texture": "#0", "cullface": "south"},
                "west": {"uv": [9, 10, 16, 15], "texture": "#0", "cullface": "west"}
            }
        },
        {
            "name": "top",
            "from": [0, 6, 0],
            "to": [16, 7, 16],
            "faces": {
                "north": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "north"},
                "east": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "east"},
                "south": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "south"},
                "west": {"uv": [0, 9, 16, 10], "texture": "#0", "cullface": "west"},
                "up": {"uv": [0, 0, 16, 16], "texture": "#3"},
                "down": {"uv": [0, 0, 16, 16], "texture": "#3"}
            }
        },
        {
            "name": "inner",
            "from": [1, 1, 1],
            "to": [15, 6, 15],
            "faces": {
                "north": {"uv": [1, 10, 15, 15], "texture": "#1"},
                "east": {"uv": [1, 10, 15, 15], "texture": "#1"},
                "south": {"uv": [1, 10, 15, 15], "texture": "#1"},
                "west": {"uv": [1, 10, 15, 15], "texture": "#1"}
            }
        },
        {
            "name": "setting1",
            "from": [7, 3, 0],
            "to": [9, 4, 1],
            "faces": {
                "north": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "north"},
                "up": {"uv": [0, 0, 2, 1], "texture": "#2"},
                "down": {"uv": [0, 0, 2, 1], "texture": "#2"}
            }
        },
        {
            "name": "setting2",
            "from": [15, 3, 7],
            "to": [16, 4, 9],
            "faces": {
                "east": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "east"},
                "up": {"uv": [0, 0, 1, 2], "texture": "#2"},
                "down": {"uv": [0, 0, 1, 2], "texture": "#2"}
            }
        },
        {
            "name": "setting3",
            "from": [7, 3, 15],
            "to": [9, 4, 16],
            "faces": {
                "south": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "south"},
                "up": {"uv": [0, 0, 2, 1], "texture": "#2"},
                "down": {"uv": [0, 0, 2, 1], "texture": "#2"}
            }
        },
        {
            "name": "setting4",
            "from": [0, 3, 7],
            "to": [1, 4, 9],
            "faces": {
                "west": {"uv": [0, 0, 2, 1], "texture": "#2", "cullface": "west"},
                "up": {"uv": [0, 0, 1, 2], "texture": "#2"},
                "down": {"uv": [0, 0, 1, 2], "texture": "#2"}
            }
        },
        {
            "name": "pad",
            "from": [1, 7, 1],
            "to": [15, 8, 15],
            "faces": {
                "north": {"uv": [0, 0, 14, 1], "texture": "#5"},
                "east": {"uv": [0, 0, 14, 1], "texture": "#5"},
                "south": {"uv": [0, 0, 14, 1], "texture": "#5"},
                "west": {"uv": [0, 0, 14, 1], "texture": "#5"},
                "up": {"uv": [1, 1, 15, 15], "texture": "#4"}
            }
        }
    ]
}"""

private const val template_spawner_table_json =
"""{
  "parent": "minecraft:block/block",
  "elements": [
    {
      "from": [
        0,
        0,
        0
      ],
      "to": [
        16,
        4,
        16
      ],
      "faces": {
        "down": {
          "texture": "#bottom",
          "cullface": "down"
        },
        "up": {
          "texture": "#top"
        },
        "north": {
          "texture": "#side",
          "cullface": "north"
        },
        "south": {
          "texture": "#side",
          "cullface": "south"
        },
        "west": {
          "texture": "#side",
          "cullface": "west"
        },
        "east": {
          "texture": "#side",
          "cullface": "east"
        }
      }
    }
  ]
}"""

private const val template_cog_json =
"""{
	"credit": "Made with Blockbench",
	"parent": "minecraft:block/block",
    "ambientocclusion": false,
	"textures": {
		"front": "lcc:block/cog_cw",
		"back": "lcc:block/cog_ccw",
		"particle": "lcc:block/cog"
	},
	"elements": [
		{
			"from": [-1, 15.5, -1],
			"to": [17, 15.5, 17],
            "shade": false,
			"faces": {
				"up": {"uv": [0, 0, 16, 16], "texture": "#back"},
				"down": {"uv": [0, 0, 16, 16], "texture": "#front"}
			}
		}
	]
}"""

private const val cable4_center_json =
"""{
	"credit": "Made with Blockbench",
	"parent": "minecraft:block/block",
	"elements": [
		{
			"from": [6, 6, 6],
			"to": [10, 10, 10],
			"faces": {
				"north": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"east": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"south": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"west": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"up": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"down": {"uv": [6, 6, 10, 10], "texture": "#end"}
			}
		}
	]
}"""

private const val cable4_connection_json =
"""{
	"credit": "Made with Blockbench",
	"parent": "minecraft:block/block",
	"elements": [
		{
			"from": [6, 10, 6],
			"to": [10, 16, 10],
			"faces": {
				"north": {"uv": [6, 0, 10, 6], "texture": "#side"},
				"east": {"uv": [6, 0, 10, 6], "texture": "#side"},
				"south": {"uv": [6, 0, 10, 6], "texture": "#side"},
				"west": {"uv": [6, 0, 10, 6], "texture": "#side"},
				"up": {"uv": [6, 6, 10, 10], "texture": "#end", "cullface": "up"}
			}
		}
	]
}"""

private const val cable4_item_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"from": [0, 4, 4],
			"to": [10, 8, 8],
			"faces": {
				"north": {"uv": [6, 6, 16, 10], "texture": "#side"},
				"east": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"south": {"uv": [0, 6, 10, 10], "texture": "#side"},
				"west": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"up": {"uv": [0, 6, 10, 10], "texture": "#side"},
				"down": {"uv": [0, 6, 10, 10], "texture": "#side"}
			}
		},
		{
			"from": [6, 4, 8],
			"to": [16, 8, 12],
			"faces": {
				"north": {"uv": [0, 6, 10, 10], "texture": "#side"},
				"east": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"south": {"uv": [6, 6, 16, 10], "texture": "#side"},
				"west": {"uv": [6, 6, 10, 10], "texture": "#end"},
				"up": {"uv": [6, 6, 16, 10], "texture": "#side"},
				"down": {"uv": [6, 6, 16, 10], "texture": "#side"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 32.75, 0],
			"translation": [0, 1.5, 0],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, -32.75, 0],
			"translation": [0, 1.5, 0],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [5, 0, 0],
			"translation": [0, 1.5, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [5, -180, 0],
			"translation": [0, 1.5, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"translation": [0, 1, 0],
			"scale": [0.625, 0.625, 0.625]
		},
		"fixed": {
			"rotation": [90, 0, 0],
			"scale": [0.5, 0.5, 0.5]
		}
	}
}"""

private const val template_solar_panel_json =
"""{
  "parent": "minecraft:block/block",
  "elements": [
    {
      "from": [
        0,
        0,
        0
      ],
      "to": [
        16,
        5,
        16
      ],
      "faces": {
        "down": {
          "texture": "#bottom",
          "cullface": "down"
        },
        "up": {
          "texture": "#top"
        },
        "north": {
          "texture": "#side",
          "cullface": "north"
        },
        "south": {
          "texture": "#side",
          "cullface": "south"
        },
        "west": {
          "texture": "#side",
          "cullface": "west"
        },
        "east": {
          "texture": "#side",
          "cullface": "east"
        }
      }
    }
  ]
}"""

private const val template_turbine_json =
"""{
  "parent": "minecraft:block/block",
  "elements": [
    {
      "from": [
        0,
        11,
        0
      ],
      "to": [
        16,
        16,
        16
      ],
      "faces": {
        "down": {
          "texture": "#bottom",
          "cullface": "down"
        },
        "up": {
          "texture": "#top"
        },
        "north": {
          "texture": "#side",
          "cullface": "north"
        },
        "south": {
          "texture": "#side",
          "cullface": "south"
        },
        "west": {
          "texture": "#side",
          "cullface": "west"
        },
        "east": {
          "texture": "#side",
          "cullface": "east"
        }
      }
    }
  ]
}"""

private const val template_atomic_bomb_head_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "octagon",
			"from": [0, 4.68629, 3.98],
			"to": [16, 11.31371, 16.02],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 0]},
			"faces": {
				"north": {"uv": [0, 1, 16, 7], "texture": "#2"},
				"east": {"uv": [0, 3, 6, 6], "rotation": 180, "texture": "#5", "cullface": "east"},
				"west": {"uv": [0, 3, 6, 6], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, 4],
			"to": [11.31371, 16, 16],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 0]},
			"faces": {
				"north": {"uv": [6, 0, 0, 16], "texture": "#2"},
				"up": {"uv": [0, 4.5, 6, 7.5], "rotation": 90, "texture": "#5"},
				"down": {"uv": [0, 4.5, 6, 7.5], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, 4],
			"to": [11.31371, 16, 16],
			"rotation": {"angle": -45, "axis": "z", "origin": [8, 8, 0]},
			"faces": {
				"north": {"uv": [16, 6, 0, 0], "rotation": 270, "texture": "#2"},
				"up": {"uv": [0, 1.5, 6, 4.5], "rotation": 90, "texture": "#5"},
				"down": {"uv": [0, 1.5, 6, 4.5], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, 3.99],
			"to": [11.31371, 16, 16.01],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 0]},
			"faces": {
				"north": {"uv": [5, 0, 11, 16], "texture": "#2"},
				"up": {"uv": [0, 0, 6, 3], "rotation": 90, "texture": "#5", "cullface": "up"},
				"down": {"uv": [0, 0, 6, 3], "rotation": 90, "texture": "#5", "cullface": "down"}
			}
		},
		{
			"name": "octagon",
			"from": [1, 5.10051, 3.01],
			"to": [15, 10.89949, 4.01],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 11]},
			"faces": {
				"north": {"uv": [15, 6, 1, 0], "texture": "#2"},
				"east": {"uv": [14, 6, 14.5, 9], "texture": "#5"},
				"west": {"uv": [14, 6, 14.5, 9], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [1, 5.10051, 3],
			"to": [15, 10.89949, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 11]},
			"faces": {
				"north": {"uv": [1, 5, 15, 11], "texture": "#2"},
				"east": {"uv": [14, 0, 14.5, 3], "texture": "#5"},
				"west": {"uv": [14, 0, 14.5, 3], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [5.10051, 1, 3.01],
			"to": [10.89949, 15, 4.01],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 11]},
			"faces": {
				"north": {"uv": [10, 1, 4, 15], "texture": "#2"},
				"up": {"uv": [14, 6, 14.5, 9], "rotation": 90, "texture": "#5"},
				"down": {"uv": [13.5, 6, 14, 9], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [5.10051, 1, 3],
			"to": [10.89949, 15, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 11]},
			"faces": {
				"north": {"uv": [4, 1, 10, 15], "texture": "#2"},
				"up": {"uv": [14, 3, 14.5, 6], "rotation": 90, "texture": "#5"},
				"down": {"uv": [15.5, 3, 16, 6], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [2, 5.51472, 2.01],
			"to": [14, 10.48528, 3.01],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 3]},
			"faces": {
				"north": {"uv": [14, 5, 2, 0], "texture": "#2"},
				"east": {"uv": [14.5, 6.5, 15, 9], "texture": "#5"},
				"west": {"uv": [14.5, 6.5, 15, 9], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [2, 5.51472, 2],
			"to": [14, 10.48528, 3],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 3]},
			"faces": {
				"north": {"uv": [2, 8, 14, 13], "texture": "#2"},
				"east": {"uv": [14.5, 0.5, 15, 3], "texture": "#5"},
				"west": {"uv": [14.5, 0.5, 15, 3], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [5.51472, 2, 2.01],
			"to": [10.48528, 14, 3.01],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 3]},
			"faces": {
				"north": {"uv": [12, 2, 6, 14], "texture": "#2"},
				"up": {"uv": [14.5, 6.5, 15, 9], "rotation": 90, "texture": "#5"},
				"down": {"uv": [14.5, 6.5, 15, 9], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [5.51472, 2, 2],
			"to": [10.48528, 14, 3],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 3]},
			"faces": {
				"north": {"uv": [4, 2, 9, 14], "texture": "#2"},
				"up": {"uv": [14.5, 3.5, 15, 6], "rotation": 90, "texture": "#5"},
				"down": {"uv": [15.5, 3.5, 16, 6], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [3, 5.92893, 1.02],
			"to": [13, 10.07107, 2.02],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [13, 11, 3, 7], "texture": "#2"},
				"east": {"uv": [15, 7, 15.5, 9], "texture": "#5"},
				"west": {"uv": [15, 7, 15.5, 9], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [3, 5.92893, 1],
			"to": [13, 10.07107, 2],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [3, 5, 13, 9], "texture": "#2"},
				"east": {"uv": [15, 1, 15.5, 3], "texture": "#5"},
				"west": {"uv": [15, 1, 15.5, 3], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [5.92893, 3, 1.02],
			"to": [10.07107, 13, 2.02],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [12, 3, 6, 13], "texture": "#2"},
				"up": {"uv": [15, 7, 15.5, 9], "rotation": 90, "texture": "#5"},
				"down": {"uv": [15, 7, 15.5, 9], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [5.92893, 3, 1],
			"to": [10.07107, 13, 2],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [7, 3, 11, 13], "texture": "#2"},
				"up": {"uv": [15, 4, 15.5, 6], "rotation": 90, "texture": "#5"},
				"down": {"uv": [15.5, 4, 16, 6], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4, 6.34315, 0.02],
			"to": [12, 9.65685, 1.02],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [12, 6, 4, 2], "texture": "#2"},
				"east": {"uv": [15.5, 7.5, 16, 9], "texture": "#5"},
				"west": {"uv": [15.5, 7.5, 16, 9], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4, 6.34315, 0.01],
			"to": [12, 9.65685, 1.01],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [4, 1, 12, 4], "texture": "#2"},
				"east": {"uv": [15.5, 1.5, 16, 3], "texture": "#5"},
				"west": {"uv": [15.5, 1.5, 16, 3], "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [6.34315, 4, 0.02],
			"to": [9.65685, 12, 1.02],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [10, 4, 4, 12], "texture": "#2"},
				"up": {"uv": [15.5, 7.5, 16, 9], "rotation": 90, "texture": "#5"},
				"down": {"uv": [15.5, 7.5, 16, 9], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [6.34315, 4, 0],
			"to": [9.65685, 12, 1],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, 1]},
			"faces": {
				"north": {"uv": [6, 4, 9, 12], "texture": "#2", "cullface": "north"},
				"up": {"uv": [15.5, 4.5, 16, 6], "rotation": 90, "texture": "#5"},
				"down": {"uv": [15.5, 4.5, 16, 6], "rotation": 270, "texture": "#5"}
			}
		}
	],
	"groups": [
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [0, 1, 2, 3]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [4, 5, 6, 7]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [8, 9, 10, 11]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [12, 13, 14, 15]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [16, 17, 18, 19]
		}
	]
}"""

private const val template_atomic_bomb_middle_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "octagon",
			"from": [0, 4.68629, -0.02],
			"to": [16, 11.31371, 16.02],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -16]},
			"faces": {
				"east": {"uv": [6, 3, 14, 6], "rotation": 180, "texture": "#5", "cullface": "east"},
				"south": {"uv": [0, 1, 16, 7], "texture": "#2", "cullface": "south"},
				"west": {"uv": [6, 3, 14, 6], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, 0],
			"to": [11.31371, 16, 16],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -16]},
			"faces": {
				"south": {"uv": [16, 6, 0, 0], "rotation": 270, "texture": "#2", "cullface": "south"},
				"up": {"uv": [6, 4.5, 14, 7.5], "rotation": 90, "texture": "#5"},
				"down": {"uv": [6, 4.5, 14, 7.5], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, 0],
			"to": [11.31371, 16, 16],
			"rotation": {"angle": -45, "axis": "z", "origin": [8, 8, -16]},
			"faces": {
				"south": {"uv": [6, 0, 0, 16], "texture": "#2", "cullface": "south"},
				"up": {"uv": [6, 1.5, 14, 4.5], "rotation": 90, "texture": "#5"},
				"down": {"uv": [6, 1.5, 14, 4.5], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, -0.01],
			"to": [11.31371, 16, 16.01],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -16]},
			"faces": {
				"south": {"uv": [5, 0, 11, 16], "texture": "#2", "cullface": "south"},
				"up": {"uv": [6, 0, 14, 3], "rotation": 90, "texture": "#5", "cullface": "up"},
				"down": {"uv": [6, 0, 14, 3], "rotation": 90, "texture": "#5", "cullface": "down"}
			}
		}
	],
	"groups": [
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [0, 1, 2, 3]
		}
	]
}"""

private const val template_atomic_bomb_tail_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "tail",
			"from": [0, 0, 8],
			"to": [16, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -8]},
			"faces": {
				"north": {"uv": [0, 0, 16, 16], "texture": "#2"},
				"east": {"uv": [4, 0, 12, 16], "texture": "#1", "cullface": "east"},
				"south": {"uv": [0, 0, 16, 16], "texture": "#2", "cullface": "south"},
				"west": {"uv": [4, 0, 12, 16], "texture": "#1", "cullface": "west"},
				"up": {"uv": [4, 0, 12, 16], "rotation": 90, "texture": "#1", "cullface": "up"},
				"down": {"uv": [4, 0, 12, 16], "rotation": 270, "texture": "#1", "cullface": "down"}
			}
		},
		{
			"name": "fin1",
			"from": [7.5, 1, 0],
			"to": [8.5, 15, 8],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -8]},
			"faces": {
				"east": {"uv": [4, 1, 12, 15], "texture": "#3"},
				"west": {"uv": [4, 1, 12, 15], "texture": "#3"},
				"up": {"uv": [4, 1, 12, 2], "rotation": 270, "texture": "#3"},
				"down": {"uv": [4, 14, 12, 15], "rotation": 90, "texture": "#3"}
			}
		},
		{
			"name": "fin2",
			"from": [1, 7.5, 0],
			"to": [15, 8.5, 8],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -8]},
			"faces": {
				"east": {"uv": [4, 1, 12, 2], "rotation": 180, "texture": "#3"},
				"west": {"uv": [4, 14, 12, 15], "rotation": 180, "texture": "#3"},
				"up": {"uv": [4, 1, 12, 15], "rotation": 270, "texture": "#3"},
				"down": {"uv": [4, 1, 12, 15], "rotation": 270, "texture": "#3"}
			}
		},
		{
			"name": "handle",
			"from": [5.25, 5.25, 0],
			"to": [10.75, 10.75, 8],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -8]},
			"faces": {
				"east": {"uv": [4, 5, 12, 10], "texture": "#4"},
				"west": {"uv": [4, 5, 12, 10], "texture": "#4"},
				"up": {"uv": [4, 5, 12, 10], "rotation": 90, "texture": "#4"},
				"down": {"uv": [4, 5, 12, 10], "rotation": 270, "texture": "#4"}
			}
		}
	]
}"""

private const val template_atomic_bomb_item_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "tail",
			"from": [0, 0, 24],
			"to": [16, 16, 32],
			"faces": {
				"north": {"uv": [0, 0, 16, 16], "texture": "#2", "cullface": "east"},
				"east": {"uv": [4, 0, 12, 16], "texture": "#1", "cullface": "north"},
				"south": {"uv": [0, 0, 16, 16], "texture": "#2", "cullface": "east"},
				"west": {"uv": [4, 0, 12, 16], "texture": "#1", "cullface": "east"},
				"up": {"uv": [4, 0, 12, 16], "rotation": 90, "texture": "#1", "cullface": "east"},
				"down": {"uv": [4, 0, 12, 16], "rotation": 270, "texture": "#1", "cullface": "down"}
			}
		},
		{
			"name": "fin1",
			"from": [7.5, 1, 16],
			"to": [8.5, 15, 24],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 8]},
			"faces": {
				"east": {"uv": [4, 1, 12, 15], "texture": "#3", "cullface": "east"},
				"west": {"uv": [4, 1, 12, 15], "texture": "#3", "cullface": "east"},
				"up": {"uv": [4, 1, 12, 2], "rotation": 270, "texture": "#3", "cullface": "east"},
				"down": {"uv": [4, 14, 12, 15], "rotation": 90, "texture": "#3", "cullface": "east"}
			}
		},
		{
			"name": "fin2",
			"from": [1, 7.5, 16],
			"to": [15, 8.5, 24],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, 8]},
			"faces": {
				"east": {"uv": [4, 1, 12, 2], "rotation": 180, "texture": "#3", "cullface": "east"},
				"west": {"uv": [4, 14, 12, 15], "rotation": 180, "texture": "#3", "cullface": "east"},
				"up": {"uv": [4, 1, 12, 15], "rotation": 270, "texture": "#3", "cullface": "east"},
				"down": {"uv": [4, 1, 12, 15], "rotation": 270, "texture": "#3", "cullface": "east"}
			}
		},
		{
			"name": "handle",
			"from": [5.25, 5.25, 16],
			"to": [10.75, 10.75, 24],
			"faces": {
				"east": {"uv": [4, 5, 12, 10], "texture": "#4", "cullface": "east"},
				"west": {"uv": [4, 5, 12, 10], "texture": "#4", "cullface": "east"},
				"up": {"uv": [4, 5, 12, 10], "rotation": 90, "texture": "#4", "cullface": "east"},
				"down": {"uv": [4, 5, 12, 10], "rotation": 270, "texture": "#4", "cullface": "east"}
			}
		},
		{
			"name": "octagon",
			"from": [0, 4.68629, -12.002],
			"to": [16, 11.31371, 16.002],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -16]},
			"faces": {
				"north": {"uv": [0, 1, 16, 7], "texture": "#2", "cullface": "east"},
				"east": {"uv": [0, 3, 14, 6], "rotation": 180, "texture": "#5", "cullface": "north"},
				"south": {"uv": [0, 1, 16, 7], "texture": "#2", "cullface": "east"},
				"west": {"uv": [0, 3, 14, 6], "texture": "#5", "cullface": "south"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, -12],
			"to": [11.31371, 16, 16],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -16]},
			"faces": {
				"north": {"uv": [6, 0, 0, 16], "texture": "#2", "cullface": "east"},
				"south": {"uv": [16, 6, 0, 0], "rotation": 270, "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 4.5, 14, 7.5], "rotation": 90, "texture": "#5"},
				"down": {"uv": [0, 4.5, 14, 7.5], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, -12],
			"to": [11.31371, 16, 16],
			"rotation": {"angle": -45, "axis": "z", "origin": [8, 8, -16]},
			"faces": {
				"north": {"uv": [16, 6, 0, 0], "rotation": 270, "texture": "#2", "cullface": "east"},
				"south": {"uv": [6, 0, 0, 16], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 1.5, 14, 4.5], "rotation": 90, "texture": "#5"},
				"down": {"uv": [0, 1.5, 14, 4.5], "rotation": 270, "texture": "#5"}
			}
		},
		{
			"name": "octagon",
			"from": [4.68629, 0, -12.001],
			"to": [11.31371, 16, 16.001],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -16]},
			"faces": {
				"north": {"uv": [5, 0, 11, 16], "texture": "#2", "cullface": "east"},
				"south": {"uv": [5, 0, 11, 16], "texture": "#2", "cullface": "east"},
				"up": {"uv": [0, 0, 14, 3], "rotation": 90, "texture": "#5", "cullface": "up"},
				"down": {"uv": [0, 0, 14, 3], "rotation": 90, "texture": "#5", "cullface": "down"}
			}
		},
		{
			"name": "octagon",
			"from": [1, 5.10051, -12.999],
			"to": [15, 10.89949, -11.999],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -5]},
			"faces": {
				"north": {"uv": [15, 6, 1, 0], "texture": "#2", "cullface": "east"},
				"east": {"uv": [14, 6, 14.5, 9], "texture": "#5", "cullface": "west"},
				"west": {"uv": [14, 6, 14.5, 9], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [1, 5.10051, -13],
			"to": [15, 10.89949, -12],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -5]},
			"faces": {
				"north": {"uv": [1, 5, 15, 11], "texture": "#2", "cullface": "east"},
				"east": {"uv": [14, 0, 14.5, 3], "texture": "#5", "cullface": "west"},
				"west": {"uv": [14, 0, 14.5, 3], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [5.10051, 1, -12.999],
			"to": [10.89949, 15, -11.999],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -5]},
			"faces": {
				"north": {"uv": [10, 1, 4, 15], "texture": "#2", "cullface": "east"},
				"up": {"uv": [14, 6, 14.5, 9], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [13.5, 6, 14, 9], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [5.10051, 1, -13],
			"to": [10.89949, 15, -12],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -5]},
			"faces": {
				"north": {"uv": [4, 1, 10, 15], "texture": "#2", "cullface": "east"},
				"up": {"uv": [14, 3, 14.5, 6], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [15.5, 3, 16, 6], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [2, 5.51472, -13.999],
			"to": [14, 10.48528, -12.999],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -13]},
			"faces": {
				"north": {"uv": [14, 5, 2, 0], "texture": "#2", "cullface": "east"},
				"east": {"uv": [14.5, 6.5, 15, 9], "texture": "#5", "cullface": "west"},
				"west": {"uv": [14.5, 6.5, 15, 9], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [2, 5.51472, -14],
			"to": [14, 10.48528, -13],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -13]},
			"faces": {
				"north": {"uv": [2, 8, 14, 13], "texture": "#2", "cullface": "east"},
				"east": {"uv": [14.5, 0.5, 15, 3], "texture": "#5", "cullface": "west"},
				"west": {"uv": [14.5, 0.5, 15, 3], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [5.51472, 2, -13.999],
			"to": [10.48528, 14, -12.999],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -13]},
			"faces": {
				"north": {"uv": [12, 2, 6, 14], "texture": "#2", "cullface": "east"},
				"up": {"uv": [14.5, 6.5, 15, 9], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [14.5, 6.5, 15, 9], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [5.51472, 2, -14],
			"to": [10.48528, 14, -13],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -13]},
			"faces": {
				"north": {"uv": [4, 2, 9, 14], "texture": "#2", "cullface": "east"},
				"up": {"uv": [14.5, 3.5, 15, 6], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [15.5, 3.5, 16, 6], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [3, 5.92893, -14.998],
			"to": [13, 10.07107, -13.998],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [13, 11, 3, 7], "texture": "#2", "cullface": "east"},
				"east": {"uv": [15, 7, 15.5, 9], "texture": "#5", "cullface": "west"},
				"west": {"uv": [15, 7, 15.5, 9], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [3, 5.92893, -15],
			"to": [13, 10.07107, -14],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [3, 5, 13, 9], "texture": "#2", "cullface": "east"},
				"east": {"uv": [15, 1, 15.5, 3], "texture": "#5", "cullface": "west"},
				"west": {"uv": [15, 1, 15.5, 3], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [5.92893, 3, -14.998],
			"to": [10.07107, 13, -13.998],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [12, 3, 6, 13], "texture": "#2", "cullface": "east"},
				"up": {"uv": [15, 7, 15.5, 9], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [15, 7, 15.5, 9], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [5.92893, 3, -15],
			"to": [10.07107, 13, -14],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [7, 3, 11, 13], "texture": "#2", "cullface": "east"},
				"up": {"uv": [15, 4, 15.5, 6], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [15.5, 4, 16, 6], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [4, 6.34315, -15.998],
			"to": [12, 9.65685, -14.998],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [12, 6, 4, 2], "texture": "#2", "cullface": "east"},
				"east": {"uv": [15.5, 7.5, 16, 9], "texture": "#5", "cullface": "west"},
				"west": {"uv": [15.5, 7.5, 16, 9], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [4, 6.34315, -15.999],
			"to": [12, 9.65685, -14.999],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [4, 1, 12, 4], "texture": "#2", "cullface": "east"},
				"east": {"uv": [15.5, 1.5, 16, 3], "texture": "#5", "cullface": "west"},
				"west": {"uv": [15.5, 1.5, 16, 3], "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [6.34315, 4, -15.998],
			"to": [9.65685, 12, -14.998],
			"rotation": {"angle": 45, "axis": "z", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [10, 4, 4, 12], "texture": "#2", "cullface": "east"},
				"up": {"uv": [15.5, 7.5, 16, 9], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [15.5, 7.5, 16, 9], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		},
		{
			"name": "octagon",
			"from": [6.34315, 4, -16],
			"to": [9.65685, 12, -15],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 8, -15]},
			"faces": {
				"north": {"uv": [6, 4, 9, 12], "texture": "#2", "cullface": "east"},
				"up": {"uv": [15.5, 4.5, 16, 6], "rotation": 90, "texture": "#5", "cullface": "west"},
				"down": {"uv": [15.5, 4.5, 16, 6], "rotation": 270, "texture": "#5", "cullface": "west"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 90, 0],
			"translation": [0, 2, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 90, 0],
			"translation": [0, 2, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"firstperson_righthand": {
			"rotation": [0, 80, 0],
			"translation": [1, 0, 0],
			"scale": [0.4, 0.35, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, 80, 0],
			"translation": [1, 0, 0],
			"scale": [0.4, 0.35, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.2, 0.2, 0.2]
		},
		"gui": {
			"rotation": [30, -100, 0],
			"scale": [0.3, 0.3, 0.3]
		},
		"head": {
			"rotation": [90, 0, 0],
			"translation": [0, 14.5, 0]
		},
		"fixed": {
			"rotation": [0, 90, 0],
			"scale": [0.33, 0.33, 0.33]
		}
	},
	"groups": [0, 1, 2, 3,
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [4, 5, 6, 7]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [8, 9, 10, 11]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [12, 13, 14, 15]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [16, 17, 18, 19]
		},
		{
			"name": "octagon",
			"origin": [8, 8, 8],
			"children": [20, 21, 22, 23]
		}
	]
}"""

private const val template_face_up_down_json =
"""{
    "parent": "block/block",
    "elements": [
        {   "from": [ 0, 0, 0 ],
            "to": [ 16, 16, 16 ],
            "faces": {
                "down":  { "texture": "#texture", "cullface": "down" },
                "up":    { "texture": "#texture", "cullface": "up" }
            }
        }
    ]
}"""

private const val template_face_north_json =
"""{
    "parent": "block/block",
    "elements": [
        {   "from": [ 0, 0, 0 ],
            "to": [ 16, 16, 16 ],
            "faces": {
                "north": { "texture": "#texture", "cullface": "north" }
            }
        }
    ]
}"""

private const val template_face_east_json =
"""{
    "parent": "block/block",
    "elements": [
        {   "from": [ 0, 0, 0 ],
            "to": [ 16, 16, 16 ],
            "faces": {
                "east":  { "texture": "#texture", "cullface": "east" }
            }
        }
    ]
}"""

private const val template_face_south_json =
"""{
    "parent": "block/block",
    "elements": [
        {   "from": [ 0, 0, 0 ],
            "to": [ 16, 16, 16 ],
            "faces": {
                "south": { "texture": "#texture", "cullface": "south" }
            }
        }
    ]
}"""

private const val template_face_west_json =
"""{
    "parent": "block/block",
    "elements": [
        {   "from": [ 0, 0, 0 ],
            "to": [ 16, 16, 16 ],
            "faces": {
                "west":  { "texture": "#texture", "cullface": "west" }
            }
        }
    ]
}"""

private const val template_treetap_json =
"""{
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#0"
	},
	"elements": [
		{
			"name": "tap1",
			"from": [7, 14, 10],
			"to": [9, 15, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [15, 22, 20]},
			"faces": {
				"north": {"uv": [7, 1, 9, 2], "texture": "#0"},
				"east": {"uv": [0, 1, 6, 2], "texture": "#0"},
				"south": {"uv": [7, 1, 9, 2], "texture": "#0", "cullface": "south"},
				"west": {"uv": [10, 1, 16, 2], "texture": "#0"},
				"up": {"uv": [7, 10, 9, 16], "texture": "#0"},
				"down": {"uv": [7, 0, 9, 6], "texture": "#0"}
			}
		},
		{
			"name": "tap2",
			"from": [6, 15, 10],
			"to": [7, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 23, 20]},
			"faces": {
				"north": {"uv": [9, 0, 10, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 4, 1], "texture": "#0"},
				"south": {"uv": [9, 0, 10, 1], "texture": "#0", "cullface": "south"},
				"west": {"uv": [12, 0, 16, 1], "texture": "#0"},
				"up": {"uv": [6, 12, 7, 16], "texture": "#0", "cullface": "up"},
				"down": {"uv": [6, 0, 7, 4], "texture": "#0"}
			}
		},
		{
			"name": "tap3",
			"from": [9, 15, 10],
			"to": [10, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 23, 20]},
			"faces": {
				"north": {"uv": [6, 0, 7, 1], "texture": "#0"},
				"east": {"uv": [0, 0, 4, 1], "texture": "#0"},
				"south": {"uv": [6, 0, 7, 1], "texture": "#0", "cullface": "south"},
				"west": {"uv": [12, 0, 16, 1], "texture": "#0"},
				"up": {"uv": [9, 12, 10, 16], "texture": "#0", "cullface": "up"},
				"down": {"uv": [9, 0, 10, 4], "texture": "#0"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 0, 0],
			"translation": [0, 2.25, -2.75],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 0, 0],
			"translation": [0, 2.25, -2.75],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [10, -10, 5],
			"translation": [0, -0.25, 0],
			"scale": [0.5, 0.5, 0.5]
		},
		"firstperson_lefthand": {
			"rotation": [10, -10, 5],
			"translation": [0, -0.25, 0],
			"scale": [0.5, 0.5, 0.5]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"translation": [3.5, -8, 0]
		},
		"head": {
			"translation": [0, -3.5, -14.25]
		},
		"fixed": {
			"translation": [0, -7, -8]
		}
	}
}"""

private const val template_treetap_overflow_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "overflow",
			"from": [7, 15, 10],
			"to": [9, 15.5, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [15, 23, 18]},
			"faces": {
				"north": {"uv": [7, 0.5, 9, 1], "texture": "#2"},
				"up": {"uv": [7, 10, 9, 16], "texture": "#2"}
			}
		}
	]
}"""

private const val template_treetap_bowl_json =
"""{
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#0"
	},
	"elements": [
		{
			"name": "tap1",
			"from": [7, 14, 10],
			"to": [9, 15, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [15, 22, 20]},
			"faces": {
				"north": {"uv": [7, 1, 9, 2], "texture": "#0"},
				"east": {"uv": [0, 1, 6, 2], "texture": "#0"},
				"west": {"uv": [10, 1, 16, 2], "texture": "#0"},
				"up": {"uv": [7, 10, 9, 16], "texture": "#0"},
				"down": {"uv": [7, 0, 9, 6], "texture": "#0"}
			}
		},
		{
			"name": "tap2",
			"from": [6, 15, 12],
			"to": [7, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 23, 20]},
			"faces": {
				"east": {"uv": [0, 0, 4, 1], "texture": "#0"},
				"west": {"uv": [12, 0, 16, 1], "texture": "#0"},
				"up": {"uv": [6, 12, 7, 16], "texture": "#0", "cullface": "up"},
				"down": {"uv": [6, 0, 7, 4], "texture": "#0"}
			}
		},
		{
			"name": "tap3",
			"from": [9, 15, 12],
			"to": [10, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 23, 20]},
			"faces": {
				"east": {"uv": [0, 0, 4, 1], "texture": "#0"},
				"west": {"uv": [12, 0, 16, 1], "texture": "#0"},
				"up": {"uv": [9, 12, 10, 16], "texture": "#0", "cullface": "up"},
				"down": {"uv": [9, 0, 10, 4], "texture": "#0"}
			}
		},
		{
			"name": "tap4",
			"from": [6, 15, 11],
			"to": [7, 16, 12],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 23, 19]},
			"faces": {
				"east": {"uv": [4, 0, 5, 1], "texture": "#0"},
				"west": {"uv": [11, 0, 12, 1], "texture": "#0"},
				"down": {"uv": [6, 4, 7, 5], "texture": "#0"}
			}
		},
		{
			"name": "tap5",
			"from": [9, 15, 11],
			"to": [10, 16, 12],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 23, 19]},
			"faces": {
				"east": {"uv": [4, 0, 5, 1], "texture": "#0"},
				"west": {"uv": [11, 0, 12, 1], "texture": "#0"},
				"down": {"uv": [9, 4, 10, 5], "texture": "#0"}
			}
		},
		{
			"name": "tap6",
			"from": [6, 15, 10],
			"to": [7, 16, 11],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 23, 16]},
			"faces": {
				"north": {"uv": [9, 0, 10, 1], "texture": "#0"},
				"east": {"uv": [5, 0, 6, 1], "texture": "#0"},
				"west": {"uv": [10, 0, 11, 1], "texture": "#0"},
				"up": {"uv": [6, 10, 7, 11], "texture": "#0", "cullface": "up"},
				"down": {"uv": [6, 5, 7, 6], "texture": "#0"}
			}
		},
		{
			"name": "tap7",
			"from": [9, 15, 10],
			"to": [10, 16, 11],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 23, 16]},
			"faces": {
				"north": {"uv": [6, 0, 7, 1], "texture": "#0"},
				"east": {"uv": [5, 0, 6, 1], "texture": "#0"},
				"west": {"uv": [10, 0, 11, 1], "texture": "#0"},
				"up": {"uv": [9, 10, 10, 11], "texture": "#0", "cullface": "up"},
				"down": {"uv": [9, 5, 10, 6], "texture": "#0"}
			}
		},
		{
			"name": "chain1",
			"from": [6, 16, 11],
			"to": [10, 16, 12],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 23, 19]},
			"faces": {
				"up": {"uv": [11, 8, 15, 9], "texture": "#1", "cullface": "up"},
				"down": {"uv": [9, 7, 13, 8], "texture": "#1", "cullface": "up"}
			}
		},
		{
			"name": "chain2",
			"from": [6, 11, 11],
			"to": [6, 16, 12],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [6.1, 16, 11]},
			"faces": {
				"east": {"uv": [9, 4, 10, 9], "texture": "#1"},
				"west": {"uv": [9, 0, 10, 5], "texture": "#1"}
			}
		},
		{
			"name": "chain3",
			"from": [10, 11, 11],
			"to": [10, 16, 12],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [10, 16, 11]},
			"faces": {
				"east": {"uv": [9, 3, 10, 8], "texture": "#1"},
				"west": {"uv": [9, 1, 10, 6], "texture": "#1"}
			}
		},
		{
			"name": "bowl1",
			"from": [5.5, 10, 15],
			"to": [10.5, 12, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [18.5, 19, 23]},
			"faces": {
				"north": {"uv": [2, 1, 7, 3], "texture": "#1"},
				"east": {"uv": [7, 9, 8, 11], "texture": "#1"},
				"west": {"uv": [1, 9, 2, 11], "texture": "#1"},
				"up": {"uv": [2, 8, 7, 9], "texture": "#1"},
				"down": {"uv": [2, 8, 7, 9], "texture": "#1"}
			}
		},
		{
			"name": "bowl2",
			"from": [4.5, 10, 14],
			"to": [5.5, 12, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [12.5, 19, 22]},
			"faces": {
				"north": {"uv": [2, 2, 3, 4], "texture": "#1"},
				"east": {"uv": [1, 4, 2, 6], "texture": "#1"},
				"south": {"uv": [3, 9, 4, 11], "texture": "#1"},
				"west": {"uv": [5, 9, 6, 11], "texture": "#1"},
				"up": {"uv": [7, 7, 8, 8], "texture": "#1"},
				"down": {"uv": [1, 1, 2, 2], "texture": "#1"}
			}
		},
		{
			"name": "bowl3",
			"from": [10.5, 10, 14],
			"to": [11.5, 12, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [18.5, 19, 22]},
			"faces": {
				"north": {"uv": [3, 6, 4, 8], "texture": "#1"},
				"east": {"uv": [5, 9, 6, 11], "texture": "#1"},
				"south": {"uv": [3, 9, 4, 11], "texture": "#1"},
				"west": {"uv": [7, 5, 8, 7], "texture": "#1"},
				"up": {"uv": [7, 1, 8, 2], "texture": "#1"},
				"down": {"uv": [7, 1, 8, 2], "texture": "#1"}
			}
		},
		{
			"name": "bowl4",
			"from": [3.5, 10, 9],
			"to": [4.5, 12, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [11.5, 19, 21]},
			"faces": {
				"north": {"uv": [8, 3, 9, 5], "texture": "#1"},
				"east": {"uv": [3, 5, 8, 7], "texture": "#1"},
				"south": {"uv": [2, 0, 3, 2], "texture": "#1"},
				"west": {"uv": [2, 9, 7, 11], "texture": "#1"},
				"up": {"uv": [0, 2, 1, 7], "texture": "#1"},
				"down": {"uv": [0, 2, 1, 7], "texture": "#1"}
			}
		},
		{
			"name": "bowl5",
			"from": [11.5, 10, 9],
			"to": [12.5, 12, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [19.5, 19, 21]},
			"faces": {
				"north": {"uv": [3, 9, 4, 11], "texture": "#1"},
				"east": {"uv": [2, 9, 7, 11], "texture": "#1"},
				"south": {"uv": [8, 3, 9, 5], "texture": "#1"},
				"west": {"uv": [1, 2, 6, 4], "texture": "#1"},
				"up": {"uv": [8, 2, 9, 7], "texture": "#1"},
				"down": {"uv": [8, 2, 9, 7], "texture": "#1"}
			}
		},
		{
			"name": "bowl6",
			"from": [4.5, 10, 8],
			"to": [5.5, 12, 9],
			"rotation": {"angle": 0, "axis": "y", "origin": [12.5, 19, 16]},
			"faces": {
				"north": {"uv": [3, 9, 4, 11], "texture": "#1"},
				"east": {"uv": [6, 5, 7, 7], "texture": "#1"},
				"south": {"uv": [6, 2, 7, 4], "texture": "#1"},
				"west": {"uv": [5, 9, 6, 11], "texture": "#1"},
				"up": {"uv": [7, 7, 8, 8], "texture": "#1"},
				"down": {"uv": [7, 7, 8, 8], "texture": "#1"}
			}
		},
		{
			"name": "bowl7",
			"from": [10.5, 10, 8],
			"to": [11.5, 12, 9],
			"rotation": {"angle": 0, "axis": "y", "origin": [18.5, 19, 16]},
			"faces": {
				"north": {"uv": [5, 9, 6, 11], "texture": "#1"},
				"east": {"uv": [3, 9, 4, 11], "texture": "#1"},
				"south": {"uv": [3, 5, 4, 7], "texture": "#1"},
				"west": {"uv": [5, 6, 6, 8], "texture": "#1"},
				"up": {"uv": [1, 7, 2, 8], "texture": "#1"},
				"down": {"uv": [1, 1, 2, 2], "texture": "#1"}
			}
		},
		{
			"name": "bowl8",
			"from": [5.5, 10, 7],
			"to": [10.5, 12, 8],
			"rotation": {"angle": 0, "axis": "y", "origin": [18.5, 19, 15]},
			"faces": {
				"north": {"uv": [2, 9, 7, 11], "texture": "#1"},
				"east": {"uv": [1, 9, 2, 11], "texture": "#1"},
				"south": {"uv": [2, 6, 7, 8], "texture": "#1"},
				"west": {"uv": [7, 9, 8, 11], "texture": "#1"},
				"up": {"uv": [2, 0, 7, 1], "texture": "#1"},
				"down": {"uv": [2, 0, 7, 1], "texture": "#1"}
			}
		},
		{
			"name": "bowl9",
			"from": [4.5, 9, 9],
			"to": [5.5, 10, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [12.5, 17, 17]},
			"faces": {
				"north": {"uv": [8, 9, 9, 10], "texture": "#1"},
				"east": {"uv": [2, 6, 7, 7], "texture": "#1"},
				"south": {"uv": [3, 9, 4, 10], "texture": "#1"},
				"west": {"uv": [4, 9, 9, 10], "texture": "#1"},
				"up": {"uv": [4, 2, 5, 7], "texture": "#1"},
				"down": {"uv": [15, 0, 16, 5], "texture": "#1"}
			}
		},
		{
			"name": "bowl10",
			"from": [5.5, 9, 8],
			"to": [10.5, 10, 9],
			"rotation": {"angle": 0, "axis": "y", "origin": [12.5, 17, 16]},
			"faces": {
				"north": {"uv": [2, 0, 7, 1], "texture": "#1"},
				"east": {"uv": [7, 6, 8, 7], "texture": "#1"},
				"south": {"uv": [1, 3, 6, 4], "texture": "#1"},
				"west": {"uv": [8, 6, 9, 7], "texture": "#1"},
				"up": {"uv": [3, 3, 8, 4], "texture": "#1"},
				"down": {"uv": [2, 0, 7, 1], "texture": "#1"}
			}
		},
		{
			"name": "bowl11",
			"from": [5.5, 9, 14],
			"to": [10.5, 10, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [12.5, 17, 22]},
			"faces": {
				"north": {"uv": [1, 6, 6, 7], "texture": "#1"},
				"east": {"uv": [1, 6, 2, 7], "texture": "#1"},
				"south": {"uv": [11, 2, 16, 3], "texture": "#1"},
				"west": {"uv": [8, 9, 9, 10], "texture": "#1"},
				"up": {"uv": [3, 3, 8, 4], "texture": "#1"},
				"down": {"uv": [2, 0, 7, 1], "texture": "#1"}
			}
		},
		{
			"name": "bowl12",
			"from": [10.5, 9, 9],
			"to": [11.5, 10, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 17, 21]},
			"faces": {
				"north": {"uv": [8, 6, 9, 7], "texture": "#1"},
				"east": {"uv": [2, 0, 7, 1], "texture": "#1"},
				"south": {"uv": [3, 0, 4, 1], "texture": "#1"},
				"west": {"uv": [2, 2, 7, 3], "texture": "#1"},
				"up": {"uv": [3, 2, 4, 7], "texture": "#1"},
				"down": {"uv": [0, 2, 1, 7], "texture": "#1"}
			}
		},
		{
			"name": "bowl13",
			"from": [5.5, 8, 9],
			"to": [10.5, 9, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 16, 17]},
			"faces": {
				"north": {"uv": [2, 9, 7, 10], "texture": "#1"},
				"east": {"uv": [11, 3, 16, 4], "texture": "#1"},
				"south": {"uv": [0, 9, 5, 10], "texture": "#1"},
				"west": {"uv": [2, 0, 7, 1], "texture": "#1"},
				"up": {"uv": [2, 2, 7, 7], "texture": "#1"},
				"down": {"uv": [11, 0, 16, 5], "texture": "#1"}
			}
		}
	]
}"""

private const val template_treetap_bowl_1_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "bowl_fill",
			"from": [5.5, 9, 9],
			"to": [10.5, 9.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 17, 17]},
			"faces": {
				"up": {"uv": [0, 0, 1, 1], "texture": "#2"}
			}
		}
	]
}"""

private const val template_treetap_bowl_2_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "bowl_fill1",
			"from": [5.5, 10, 8],
			"to": [10.5, 10.7, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 18, 17]},
			"faces": {
				"up": {"uv": [5.5, 8, 10.5, 15], "texture": "#2"}
			}
		},
		{
			"name": "bowl_fill2",
			"from": [10.5, 10, 9],
			"to": [11.5, 10.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 18, 16]},
			"faces": {
				"up": {"uv": [10.5, 9, 11.5, 14], "texture": "#2"}
			}
		},
		{
			"name": "bowl_fill3",
			"from": [4.5, 10, 9],
			"to": [5.5, 10.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [7.5, 18, 16]},
			"faces": {
				"up": {"uv": [4.5, 9, 5.5, 14], "texture": "#2"}
			}
		}
	]
}"""

private const val template_treetap_bowl_3_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "bowl_fill1",
			"from": [5.5, 11, 8],
			"to": [10.5, 11.7, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 19, 17]},
			"faces": {
				"up": {"uv": [5.5, 8, 10.5, 15], "texture": "#2"}
			}
		},
		{
			"name": "bowl_fill2",
			"from": [10.5, 11, 9],
			"to": [11.5, 11.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 19, 16]},
			"faces": {
				"up": {"uv": [10.5, 9, 11.5, 14], "texture": "#2"}
			}
		},
		{
			"name": "bowl_fill3",
			"from": [4.5, 11, 9],
			"to": [5.5, 11.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [7.5, 19, 16]},
			"faces": {
				"up": {"uv": [4.5, 9, 5.5, 14], "texture": "#2"}
			}
		}
	]
}"""

private const val template_treetap_bowl_dried_json =
"""{
	"credit": "Made with Blockbench",
    "elements": [
		{
			"name": "bowl_fill1",
			"from": [5.5, 11, 8],
			"to": [10.5, 11.7, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 19, 17]},
			"faces": {
				"up": {"uv": [5.5, 8, 10.5, 15], "texture": "#2"}
			}
		},
		{
			"name": "bowl_fill2",
			"from": [10.5, 11, 9],
			"to": [11.5, 11.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [13.5, 19, 16]},
			"faces": {
				"up": {"uv": [10.5, 9, 11.5, 14], "texture": "#2"}
			}
		},
		{
			"name": "bowl_fill3",
			"from": [4.5, 11, 9],
			"to": [5.5, 11.7, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [7.5, 19, 16]},
			"faces": {
				"up": {"uv": [4.5, 9, 5.5, 14], "texture": "#2"}
			}
		},
		{
			"name": "overflow1",
			"from": [8, 15, 15],
			"to": [9, 15.5, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [15, 23, 18]},
			"faces": {
				"north": {"uv": [7, 0.5, 8, 1], "texture": "#2"},
				"up": {"uv": [8, 15, 9, 16], "texture": "#2"}
			}
		},
		{
			"name": "overflow2",
			"from": [7, 15, 14],
			"to": [8, 15.5, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 23, 18]},
			"faces": {
				"north": {"uv": [8, 0.5, 9, 1], "texture": "#2"},
				"east": {"uv": [0, 0.5, 2, 1], "texture": "#2"},
				"up": {"uv": [7, 14, 8, 16], "texture": "#2"}
			}
		},
		{
			"name": "overflow3",
			"from": [7, 15, 12],
			"to": [8, 15.5, 13],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 23, 15]},
			"faces": {
				"north": {"uv": [8, 0.5, 9, 1], "texture": "#2"},
				"east": {"uv": [0, 0.5, 2, 1], "texture": "#2"},
				"south": {"uv": [7, 0.5, 8, 1], "texture": "#2"},
				"up": {"uv": [7, 12, 8, 13], "texture": "#2"}
			}
		}
	]
}"""

private const val template_nuclear_generator_full_json =
"""{
	"credit": "Made with Blockbench",
    "parent": "block/block",
	"elements": [
		{
			"name": "bottom1",
			"from": [0, 0, 3],
			"to": [16, 3, 16],
			"faces": {
				"north": {"uv": [0, 12, 16, 15], "texture": "#1"},
				"east": {"uv": [0, 13, 13, 16], "texture": "#1", "cullface": "east"},
				"south": {"uv": [0, 13, 16, 16], "texture": "#1", "cullface": "south"},
				"west": {"uv": [3, 13, 16, 16], "texture": "#1", "cullface": "west"},
				"up": {"uv": [0, 3, 16, 16], "texture": "#3"},
				"down": {"uv": [0, 0, 16, 13], "texture": "#6", "cullface": "down"}
			}
		},
		{
			"name": "bottom2",
			"from": [0, 0, 0],
			"to": [16, 2, 3],
			"faces": {
				"north": {"uv": [0, 14, 16, 16], "texture": "#1", "cullface": "north"},
				"east": {"uv": [13, 14, 16, 16], "texture": "#1", "cullface": "east"},
				"west": {"uv": [0, 14, 3, 16], "texture": "#1", "cullface": "west"},
				"up": {"uv": [0, 0, 16, 3], "texture": "#5"},
				"down": {"uv": [0, 13, 16, 16], "texture": "#6", "cullface": "down"}
			}
		},
		{
			"name": "bottom3",
			"from": [9, 2, 0],
			"to": [16, 3, 3],
			"faces": {
				"north": {"uv": [0, 13, 7, 14], "texture": "#1", "cullface": "north"},
				"east": {"uv": [13, 13, 16, 14], "texture": "#1", "cullface": "east"},
				"west": {"uv": [0, 14, 3, 15], "texture": "#1"},
				"up": {"uv": [9, 0, 16, 3], "texture": "#3"}
			}
		},
		{
			"name": "bottom4",
			"from": [0, 2, 0],
			"to": [7, 3, 3],
			"rotation": {"angle": 0, "axis": "y", "origin": [-1, 8, 8]},
			"faces": {
				"north": {"uv": [9, 13, 16, 14], "texture": "#1", "cullface": "north"},
				"east": {"uv": [13, 14, 16, 15], "texture": "#1"},
				"west": {"uv": [0, 13, 3, 14], "texture": "#1", "cullface": "west"},
				"up": {"uv": [0, 0, 7, 3], "texture": "#3"}
			}
		},
		{
			"name": "top",
			"from": [0, 13, 0],
			"to": [16, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 21, 8]},
			"faces": {
				"north": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "north"},
				"east": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "east"},
				"south": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "south"},
				"west": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 16, 16], "texture": "#0", "cullface": "up"},
				"down": {"uv": [0, 0, 16, 16], "texture": "#4"}
			}
		},
		{
			"name": "fuel",
			"from": [7, 3, 7],
			"to": [9, 13, 9],
			"rotation": {"angle": 0, "axis": "y", "origin": [15, 11, 15]},
			"faces": {
				"north": {"uv": [4, 3, 6, 13], "texture": "#8"},
				"east": {"uv": [10, 3, 12, 13], "texture": "#8"},
				"south": {"uv": [6, 3, 8, 13], "texture": "#8"},
				"west": {"uv": [8, 3, 10, 13], "texture": "#8"}
			}
		},
		{
			"name": "coolant1",
			"from": [6, 3, 6],
			"to": [7, 13, 7],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 11, 14]},
			"faces": {
				"north": {"uv": [1, 3, 2, 13], "texture": "#7"},
				"east": {"uv": [12, 3, 13, 13], "texture": "#7"},
				"south": {"uv": [14, 3, 15, 13], "texture": "#7"},
				"west": {"uv": [3, 3, 4, 13], "texture": "#7"}
			}
		},
		{
			"name": "coolant2",
			"from": [9, 3, 6],
			"to": [10, 13, 7],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 11, 14]},
			"faces": {
				"north": {"uv": [2, 3, 3, 13], "texture": "#7"},
				"east": {"uv": [13, 3, 14, 13], "texture": "#7"},
				"south": {"uv": [3, 3, 4, 13], "texture": "#7"},
				"west": {"uv": [12, 3, 13, 13], "texture": "#7"}
			}
		},
		{
			"name": "coolant3",
			"from": [9, 3, 9],
			"to": [10, 13, 10],
			"rotation": {"angle": 0, "axis": "y", "origin": [17, 11, 17]},
			"faces": {
				"north": {"uv": [3, 3, 4, 13], "texture": "#7"},
				"east": {"uv": [1, 3, 2, 13], "texture": "#7"},
				"south": {"uv": [12, 3, 13, 13], "texture": "#7"},
				"west": {"uv": [2, 3, 3, 13], "texture": "#7"}
			}
		},
		{
			"name": "coolant4",
			"from": [6, 3, 9],
			"to": [7, 13, 10],
			"rotation": {"angle": 0, "axis": "y", "origin": [14, 11, 17]},
			"faces": {
				"north": {"uv": [13, 3, 14, 13], "texture": "#7"},
				"east": {"uv": [13, 3, 14, 13], "texture": "#7"},
				"south": {"uv": [3, 3, 4, 13], "texture": "#7"},
				"west": {"uv": [2, 3, 3, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding1",
			"from": [1, 3, 1],
			"to": [4, 13, 2],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 9]},
			"faces": {
				"north": {"uv": [13, 3, 16, 13], "texture": "#9"},
				"east": {"uv": [12, 3, 13, 13], "texture": "#9"},
				"south": {"uv": [0, 3, 3, 13], "texture": "#9"},
				"west": {"uv": [0, 3, 1, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding2",
			"from": [1, 3, 2],
			"to": [2, 13, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 10]},
			"faces": {
				"east": {"uv": [13, 3, 15, 13], "texture": "#9"},
				"south": {"uv": [3, 3, 4, 13], "texture": "#9"},
				"west": {"uv": [1, 3, 3, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding3",
			"from": [12, 3, 1],
			"to": [15, 13, 2],
			"rotation": {"angle": 0, "axis": "y", "origin": [20, 11, 9]},
			"faces": {
				"north": {"uv": [0, 3, 3, 13], "texture": "#9"},
				"east": {"uv": [15, 3, 16, 13], "texture": "#9"},
				"south": {"uv": [13, 3, 16, 13], "texture": "#9"},
				"west": {"uv": [3, 3, 4, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding4",
			"from": [1, 3, 12],
			"to": [2, 13, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 20]},
			"faces": {
				"north": {"uv": [14, 3, 15, 13], "texture": "#9"},
				"east": {"uv": [1, 3, 3, 13], "texture": "#9"},
				"west": {"uv": [1, 3, 3, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding5",
			"from": [1, 3, 14],
			"to": [4, 13, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 22]},
			"faces": {
				"north": {"uv": [13, 3, 16, 13], "texture": "#9"},
				"east": {"uv": [2, 3, 3, 13], "texture": "#9"},
				"south": {"uv": [0, 3, 3, 13], "texture": "#9"},
				"west": {"uv": [15, 3, 16, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding6",
			"from": [14, 3, 12],
			"to": [15, 13, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [22, 11, 20]},
			"faces": {
				"north": {"uv": [12, 3, 13, 13], "texture": "#9"},
				"east": {"uv": [1, 3, 3, 13], "texture": "#9"},
				"west": {"uv": [13, 3, 15, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding7",
			"from": [12, 3, 14],
			"to": [15, 13, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [20, 11, 22]},
			"faces": {
				"north": {"uv": [0, 3, 3, 13], "texture": "#9"},
				"east": {"uv": [0, 3, 1, 13], "texture": "#9"},
				"south": {"uv": [13, 3, 16, 13], "texture": "#9"},
				"west": {"uv": [3, 3, 4, 13], "texture": "#9"}
			}
		},
		{
			"name": "shielding8",
			"from": [14, 3, 2],
			"to": [15, 13, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [22, 11, 10]},
			"faces": {
				"east": {"uv": [13, 3, 15, 13], "texture": "#9"},
				"south": {"uv": [12, 3, 13, 13], "texture": "#9"},
				"west": {"uv": [1, 3, 3, 13], "texture": "#9"}
			}
		}
	]
}"""

private const val template_nuclear_generator_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "bottom1",
			"from": [0, 0, 3],
			"to": [16, 3, 16],
			"faces": {
				"north": {"uv": [0, 12, 16, 15], "texture": "#1"},
				"east": {"uv": [0, 13, 13, 16], "texture": "#1", "cullface": "east"},
				"south": {"uv": [0, 13, 16, 16], "texture": "#1", "cullface": "south"},
				"west": {"uv": [3, 13, 16, 16], "texture": "#1", "cullface": "west"},
				"up": {"uv": [0, 3, 16, 16], "texture": "#3"},
				"down": {"uv": [0, 0, 16, 13], "texture": "#6", "cullface": "down"}
			}
		},
		{
			"name": "bottom2",
			"from": [0, 0, 0],
			"to": [16, 2, 3],
			"faces": {
				"north": {"uv": [0, 14, 16, 16], "texture": "#1", "cullface": "north"},
				"east": {"uv": [13, 14, 16, 16], "texture": "#1", "cullface": "east"},
				"west": {"uv": [0, 14, 3, 16], "texture": "#1", "cullface": "west"},
				"up": {"uv": [0, 0, 16, 3], "texture": "#5"},
				"down": {"uv": [0, 13, 16, 16], "texture": "#6", "cullface": "down"}
			}
		},
		{
			"name": "bottom3",
			"from": [9, 2, 0],
			"to": [16, 3, 3],
			"faces": {
				"north": {"uv": [0, 13, 7, 14], "texture": "#1", "cullface": "north"},
				"east": {"uv": [13, 13, 16, 14], "texture": "#1", "cullface": "east"},
				"west": {"uv": [0, 14, 3, 15], "texture": "#1"},
				"up": {"uv": [9, 0, 16, 3], "texture": "#3"}
			}
		},
		{
			"name": "bottom4",
			"from": [0, 2, 0],
			"to": [7, 3, 3],
			"rotation": {"angle": 0, "axis": "y", "origin": [-1, 8, 8]},
			"faces": {
				"north": {"uv": [9, 13, 16, 14], "texture": "#1", "cullface": "north"},
				"east": {"uv": [13, 14, 16, 15], "texture": "#1"},
				"west": {"uv": [0, 13, 3, 14], "texture": "#1", "cullface": "west"},
				"up": {"uv": [0, 0, 7, 3], "texture": "#3"}
			}
		},
		{
			"name": "top",
			"from": [0, 13, 0],
			"to": [16, 16, 16],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 21, 8]},
			"faces": {
				"north": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "north"},
				"east": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "east"},
				"south": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "south"},
				"west": {"uv": [0, 0, 16, 3], "texture": "#2", "cullface": "west"},
				"up": {"uv": [0, 0, 16, 16], "texture": "#0", "cullface": "up"},
				"down": {"uv": [0, 0, 16, 16], "texture": "#4"}
			}
		},
		{
			"name": "shielding1",
			"from": [1, 3, 1],
			"to": [4, 13, 2],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 9]},
			"faces": {
				"north": {"uv": [13, 3, 16, 13], "texture": "#7"},
				"east": {"uv": [12, 3, 13, 13], "texture": "#7"},
				"south": {"uv": [0, 3, 3, 13], "texture": "#7"},
				"west": {"uv": [0, 3, 1, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding2",
			"from": [1, 3, 2],
			"to": [2, 13, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 10]},
			"faces": {
				"east": {"uv": [13, 3, 15, 13], "texture": "#7"},
				"south": {"uv": [3, 3, 4, 13], "texture": "#7"},
				"west": {"uv": [1, 3, 3, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding3",
			"from": [12, 3, 1],
			"to": [15, 13, 2],
			"rotation": {"angle": 0, "axis": "y", "origin": [20, 11, 9]},
			"faces": {
				"north": {"uv": [0, 3, 3, 13], "texture": "#7"},
				"east": {"uv": [15, 3, 16, 13], "texture": "#7"},
				"south": {"uv": [13, 3, 16, 13], "texture": "#7"},
				"west": {"uv": [3, 3, 4, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding4",
			"from": [1, 3, 12],
			"to": [2, 13, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 20]},
			"faces": {
				"north": {"uv": [14, 3, 15, 13], "texture": "#7"},
				"east": {"uv": [1, 3, 3, 13], "texture": "#7"},
				"west": {"uv": [1, 3, 3, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding5",
			"from": [1, 3, 14],
			"to": [4, 13, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [9, 11, 22]},
			"faces": {
				"north": {"uv": [13, 3, 16, 13], "texture": "#7"},
				"east": {"uv": [2, 3, 3, 13], "texture": "#7"},
				"south": {"uv": [0, 3, 3, 13], "texture": "#7"},
				"west": {"uv": [15, 3, 16, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding6",
			"from": [14, 3, 12],
			"to": [15, 13, 14],
			"rotation": {"angle": 0, "axis": "y", "origin": [22, 11, 20]},
			"faces": {
				"north": {"uv": [12, 3, 13, 13], "texture": "#7"},
				"east": {"uv": [1, 3, 3, 13], "texture": "#7"},
				"west": {"uv": [13, 3, 15, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding7",
			"from": [12, 3, 14],
			"to": [15, 13, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [20, 11, 22]},
			"faces": {
				"north": {"uv": [0, 3, 3, 13], "texture": "#7"},
				"east": {"uv": [0, 3, 1, 13], "texture": "#7"},
				"south": {"uv": [13, 3, 16, 13], "texture": "#7"},
				"west": {"uv": [3, 3, 4, 13], "texture": "#7"}
			}
		},
		{
			"name": "shielding8",
			"from": [14, 3, 2],
			"to": [15, 13, 4],
			"rotation": {"angle": 0, "axis": "y", "origin": [22, 11, 10]},
			"faces": {
				"east": {"uv": [13, 3, 15, 13], "texture": "#7"},
				"south": {"uv": [12, 3, 13, 13], "texture": "#7"},
				"west": {"uv": [1, 3, 3, 13], "texture": "#7"}
			}
		}
	]
}"""