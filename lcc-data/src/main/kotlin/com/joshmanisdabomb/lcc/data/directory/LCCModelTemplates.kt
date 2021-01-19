package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.directory.ThingDirectory
import net.minecraft.data.client.model.Model
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier
import java.util.*

object ModelTemplates : ThingDirectory<Model, Pair<String, String>>() {

    val aligned_cross by createWithNameProperties("block" to aligned_cross_json, model(TextureKey.CROSS))
    val template_bounce_pad by createWithNameProperties("block" to template_bounce_pad_block_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE))
    val template_bounce_pad_0 by createWithNameProperties("block" to template_bounce_pad_0_block_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE))
    val template_bounce_pad_1 by createWithNameProperties("block" to template_bounce_pad_1_block_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE))
    val template_bounce_pad_2 by createWithNameProperties("block" to template_bounce_pad_2_block_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE))
    val template_bounce_pad_3 by createWithNameProperties("block" to template_bounce_pad_3_block_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE))
    val template_bounce_pad_4 by createWithNameProperties("block" to template_bounce_pad_4_block_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, TextureKey.PARTICLE))
    val template_bounce_pad_item by createWithNameProperties("item" to template_bounce_pad_item_json, model(LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4, LCCModelTextureKeys.t5))
    val template_spawner_table by createWithNameProperties("block" to template_spawner_table_json, model(TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM, TextureKey.PARTICLE))
    val template_cog by createWithNameProperties("block" to template_cog_json, model(TextureKey.FRONT, TextureKey.BACK, TextureKey.PARTICLE))
    val template_cable4_center by createWithNameProperties("block" to template_cable4_center_json, model(TextureKey.END, TextureKey.PARTICLE))
    val template_cable4_connection by createWithNameProperties("block" to template_cable4_connection_json, model(TextureKey.SIDE, TextureKey.END, TextureKey.PARTICLE))
    val template_cable4_item by createWithNameProperties("block" to template_cable4_item_json, model(TextureKey.SIDE, TextureKey.END))
    val template_solar_panel by createWithNameProperties("item" to template_solar_panel_json, model(TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM))
    val template_turbine by createWithNameProperties("block" to template_turbine_json, model(TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM, TextureKey.PARTICLE))

    override fun registerAll(things: Map<String, Model>, properties: Map<String, Pair<String, String>>) {
        things.forEach { (k, v) -> LCCData.accessor.handler.modelStates.addModel(Identifier(LCCData.accessor.modid, "${properties[k]!!.first}/$k")) { DataUtils.parser.parse(properties[k]!!.second) } }
    }

    fun model(vararg keys: TextureKey) = { n: String, p: Pair<String, String> -> Model(Optional.of(Identifier(LCCData.accessor.modid, "${p.first}/$n")), Optional.empty(), *keys) }

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

private const val template_bounce_pad_0_block_json =
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

private const val template_bounce_pad_1_block_json =
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

private const val template_bounce_pad_2_block_json =
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

private const val template_bounce_pad_3_block_json =
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

private const val template_bounce_pad_4_block_json =
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

private const val template_cable4_center_json =
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

private const val template_cable4_connection_json =
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

private const val template_cable4_item_json =
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