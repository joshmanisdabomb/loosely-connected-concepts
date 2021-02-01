package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.data.client.model.Model
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier
import java.util.*

object ModelTemplates : ThingDirectory<Model, Pair<String?, String?>>() {

    val aligned_cross by createFromTemplate("block", aligned_cross_json, TextureKey.CROSS)
    val generated1 by createFromReference(Identifier("minecraft", "item/generated"), TextureKey.LAYER0, LCCModelTextureKeys.layer1)

    val cable4_center by createFromTemplate("block", cable4_center_json, TextureKey.END, TextureKey.PARTICLE)
    val cable4_connection by createFromTemplate("block", cable4_connection_json, TextureKey.SIDE, TextureKey.END, TextureKey.PARTICLE)
    val cable4_item by createFromTemplate("block", cable4_item_json, TextureKey.SIDE, TextureKey.END)

    val template_bounce_pad by createFromTemplate("block", template_bounce_pad_block_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_0 by createFromTemplate("block", template_bounce_pad_0_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_1 by createFromTemplate("block", template_bounce_pad_1_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_2 by createFromTemplate("block", template_bounce_pad_2_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_3 by createFromTemplate("block", template_bounce_pad_3_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_4 by createFromTemplate("block", template_bounce_pad_4_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE)
    val template_bounce_pad_item by createFromTemplate("item", template_bounce_pad_item_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5)
    val template_spawner_table by createFromTemplate("block", template_spawner_table_json, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM, TextureKey.PARTICLE)
    val template_cog by createFromTemplate("block", template_cog_json, TextureKey.FRONT, TextureKey.BACK, TextureKey.PARTICLE)
    val template_solar_panel by createFromTemplate("item", template_solar_panel_json, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM)
    val template_turbine by createFromTemplate("block", template_turbine_json, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM, TextureKey.PARTICLE)
    val template_atomic_bomb_head by createFromTemplate("block", template_atomic_bomb_head_json, LCCModelTextureKeys.t2, LCCModelTextureKeys.t5, TextureKey.PARTICLE)
    val template_atomic_bomb_middle by createFromTemplate("block", template_atomic_bomb_middle_json, LCCModelTextureKeys.t2, LCCModelTextureKeys.t5, TextureKey.PARTICLE)
    val template_atomic_bomb_tail by createFromTemplate("block", template_atomic_bomb_tail_json, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, TextureKey.PARTICLE)
    val template_atomic_bomb_item by createFromTemplate("item", template_atomic_bomb_item_json, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5)

    override fun registerAll(things: Map<String, Model>, properties: Map<String, Pair<String?, String?>>) {
        things.forEach { (k, v) -> if (properties[k]!!.first != null && properties[k]!!.second != null) LCCData.accessor.handler.modelStates.addModel(Identifier(LCCData.accessor.modid, "${properties[k]!!.first}/$k")) { DataUtils.parser.parse(properties[k]!!.second) } }
    }

    fun createFromReference(parent: Identifier, vararg keys: TextureKey) = createWithNameProperties(Pair(null, null)) { n: String, p: Pair<String?, String?> -> Model(Optional.of(parent), Optional.empty(), *keys) }
    fun createFromTemplate(folder: String, json: String, vararg keys: TextureKey) = createWithNameProperties(Pair(folder, json)) { n: String, p: Pair<String?, String?> -> Model(Optional.of(Identifier(LCCData.accessor.modid, "${p.first}/$n")), Optional.empty(), *keys) }

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