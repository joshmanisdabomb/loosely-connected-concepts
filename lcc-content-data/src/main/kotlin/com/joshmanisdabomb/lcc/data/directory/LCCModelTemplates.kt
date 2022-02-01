package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.LCCData
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier

object LCCModelTemplates : ModelTemplateDirectory() {

    override val data = LCCData

    val aligned_cross by templateEntry("block", aligned_cross_json, TextureKey.CROSS)
    val textured_cross by templateEntry("block", textured_cross_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1)
    val flower_pot_textured_cross by templateEntry("block", flower_pot_textured_cross_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1)

    val generated1 by referenceEntry(Identifier("minecraft", "item/generated"), TextureKey.LAYER0, LCCModelTextureKeys.layer1)
    val generated2 by referenceEntry(Identifier("minecraft", "item/generated"), TextureKey.LAYER0, LCCModelTextureKeys.layer1, LCCModelTextureKeys.layer2)

    val cube_column_with_particle by templateEntry("block", cube_column_with_particle_json, TextureKey.END, TextureKey.SIDE, TextureKey.PARTICLE)
    val cube_column_horizontal_with_particle by templateEntry("block", cube_column_horizontal_with_particle_json, TextureKey.END, TextureKey.SIDE, TextureKey.PARTICLE)

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
    val template_salt_1_1 by templateEntry("block", template_salt_1_1_json, TextureKey.TEXTURE)
    val template_salt_1_2 by templateEntry("block", template_salt_1_2_json, TextureKey.TEXTURE)
    val template_salt_1_3 by templateEntry("block", template_salt_1_3_json, TextureKey.TEXTURE)
    val template_salt_2_1 by templateEntry("block", template_salt_2_1_json, TextureKey.TEXTURE)
    val template_salt_2_2 by templateEntry("block", template_salt_2_2_json, TextureKey.TEXTURE)
    val template_salt_2_3 by templateEntry("block", template_salt_2_3_json, TextureKey.TEXTURE)
    val template_salt_3_1 by templateEntry("block", template_salt_3_1_json, TextureKey.TEXTURE)
    val template_salt_3_2 by templateEntry("block", template_salt_3_2_json, TextureKey.TEXTURE)
    val template_salt_item by templateEntry("item", template_salt_item_json, TextureKey.TEXTURE)
    val template_alarm by templateEntry("block", template_alarm_json, TextureKey.TEXTURE)
    val template_alarm_item by templateEntry("item", template_alarm_item_json, TextureKey.TEXTURE)
    val template_radar by templateEntry("block", template_radar_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1, LCCModelTextureKeys.t2, LCCModelTextureKeys.t3, LCCModelTextureKeys.t4)
    val template_radiation_detector by templateEntry("item", template_radiation_detector_json, LCCModelTextureKeys.t0, LCCModelTextureKeys.t1)
    val template_deposit by templateEntry("block", template_deposit_json, TextureKey.TEXTURE)
    val template_redstone_dust_dot by templateEntry("block", template_redstone_dust_dot_json, TextureKey.TEXTURE, TextureKey.PARTICLE, LCCModelTextureKeys.overlay)
    val template_redstone_dust_side by templateEntry("block", template_redstone_dust_side_json, TextureKey.TEXTURE, TextureKey.PARTICLE, LCCModelTextureKeys.overlay)
    val template_redstone_dust_side_alt by templateEntry("block", template_redstone_dust_side_alt_json, TextureKey.TEXTURE, TextureKey.PARTICLE, LCCModelTextureKeys.overlay)
    val template_redstone_dust_up by templateEntry("block", template_redstone_dust_up_json, TextureKey.TEXTURE, TextureKey.PARTICLE, LCCModelTextureKeys.overlay)
    val template_iron_bars_post by templateEntry("block", template_iron_bars_post_json, LCCModelTextureKeys.bars)
    val template_iron_bars_post_ends by templateEntry("block", template_iron_bars_post_ends_json, TextureKey.EDGE)
    val template_iron_bars_cap by templateEntry("block", template_iron_bars_cap_json, LCCModelTextureKeys.bars)
    val template_iron_bars_cap_alt by templateEntry("block", template_iron_bars_cap_alt_json, LCCModelTextureKeys.bars)
    val template_iron_bars_side by templateEntry("block", template_iron_bars_side_json, LCCModelTextureKeys.bars, TextureKey.EDGE)
    val template_iron_bars_side_alt by templateEntry("block", template_iron_bars_side_alt_json, LCCModelTextureKeys.bars, TextureKey.EDGE)
    val template_spikes by templateEntry("block", template_spikes_json, TextureKey.TEXTURE)
    val template_sapphire_altar by templateEntry("block", template_sapphire_altar_json, TextureKey.FRONT, TextureKey.SIDE, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.PARTICLE)
    val template_sapphire_altar_tl by templateEntry("block", template_sapphire_altar_tl_json, TextureKey.TEXTURE)
    val template_sapphire_altar_tr by templateEntry("block", template_sapphire_altar_tr_json, TextureKey.TEXTURE)
    val template_sapphire_altar_middle by templateEntry("block", template_sapphire_altar_middle_json, TextureKey.TEXTURE)
    val template_sapphire_altar_bl by templateEntry("block", template_sapphire_altar_bl_json, TextureKey.TEXTURE)
    val template_sapphire_altar_br by templateEntry("block", template_sapphire_altar_br_json, TextureKey.TEXTURE)
    val template_sapphire_altar_key by templateEntry("block", template_sapphire_altar_key_json, TextureKey.TEXTURE)
    val template_obelisk_bottom by templateEntry("block", template_obelisk_bottom_json, TextureKey.TEXTURE, TextureKey.INSIDE, TextureKey.PARTICLE)
    val template_obelisk_top by templateEntry("block", template_obelisk_top_json, TextureKey.TEXTURE, TextureKey.LANTERN, TextureKey.INSIDE, TextureKey.PARTICLE)
    val template_obelisk_item by templateEntry("item", template_obelisk_item_json, TextureKey.TEXTURE, TextureKey.LANTERN, TextureKey.INSIDE)
    val template_enhancing_chamber by templateEntry("block", template_enhancing_chamber_json, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE)

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

private const val template_salt_1_1_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"from": [3, 0, 2],
			"to": [4, 1, 3],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [3, 2, 4, 3], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 1],
			"to": [9, 1, 2],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"up": {"uv": [8, 1, 9, 2], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 5],
			"to": [6, 1, 6],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [5, 5, 6, 6], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 8],
			"to": [2, 1, 9],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [1, 8, 2, 9], "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 2],
			"to": [14, 1, 3],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [13, 2, 14, 3], "texture": "#texture"}
			}
		},
		{
			"from": [10, 0, 4],
			"to": [11, 1, 5],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [10, 4, 11, 5], "texture": "#texture"}
			}
		},
		{
			"from": [14, 0, 6],
			"to": [15, 1, 7],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"up": {"uv": [14, 6, 15, 7], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 7],
			"to": [9, 1, 8],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [8, 7, 9, 8], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 10],
			"to": [5, 1, 11],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 5, 11], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 9],
			"to": [12, 1, 10],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [11, 9, 12, 10], "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 12],
			"to": [14, 1, 13],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [13, 12, 14, 13], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 4],
			"to": [1, 1, 5],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 4, 1, 5], "texture": "#texture"}
			}
		},
		{
			"from": [15, 0, 10],
			"to": [16, 1, 11],
			"faces": {
				"north": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture", "cullface": "east"},
				"south": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [15, 10, 16, 11], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 11],
			"to": [8, 1, 12],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"}
			}
		},
		{
			"from": [3, 0, 15],
			"to": [4, 1, 16],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [3, 15, 4, 16], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 13],
			"to": [1, 1, 14],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 13, 1, 14], "texture": "#texture"}
			}
		},
		{
			"from": [10, 0, 13],
			"to": [11, 1, 14],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [10, 13, 11, 14], "texture": "#texture"}
			}
		},
		{
			"from": [14, 0, 15],
			"to": [15, 1, 16],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [14, 15, 15, 16], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 14],
			"to": [8, 1, 15],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [7, 14, 8, 15], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_1_2_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"from": [1, 0, 0],
			"to": [2, 1, 1],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [3, 2, 4, 3], "texture": "#texture"}
			}
		},
		{
			"from": [6, 0, 1],
			"to": [7, 1, 2],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"up": {"uv": [8, 1, 9, 2], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 6],
			"to": [6, 1, 7],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [5, 5, 6, 6], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 6],
			"to": [2, 1, 7],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [1, 8, 2, 9], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 2],
			"to": [12, 1, 3],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [13, 2, 14, 3], "texture": "#texture"}
			}
		},
		{
			"from": [14, 0, 0],
			"to": [15, 1, 1],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [10, 4, 11, 5], "texture": "#texture"}
			}
		},
		{
			"from": [14, 0, 6],
			"to": [15, 1, 7],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"up": {"uv": [14, 6, 15, 7], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 3],
			"to": [9, 1, 4],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [8, 7, 9, 8], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 9],
			"to": [1, 1, 10],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 5, 11], "texture": "#texture"}
			}
		},
		{
			"from": [10, 0, 7],
			"to": [11, 1, 8],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [11, 9, 12, 10], "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 13],
			"to": [10, 1, 14],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [13, 12, 14, 13], "texture": "#texture"}
			}
		},
		{
			"from": [3, 0, 3],
			"to": [4, 1, 4],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 4, 1, 5], "texture": "#texture"}
			}
		},
		{
			"from": [15, 0, 12],
			"to": [16, 1, 13],
			"faces": {
				"north": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture", "cullface": "east"},
				"south": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [15, 10, 16, 11], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 9],
			"to": [8, 1, 10],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 11],
			"to": [5, 1, 12],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [3, 15, 4, 16], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 14],
			"to": [1, 1, 15],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 13, 1, 14], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 10],
			"to": [12, 1, 11],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [10, 13, 11, 14], "texture": "#texture"}
			}
		},
		{
			"from": [12, 0, 15],
			"to": [13, 1, 16],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [14, 15, 15, 16], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 14],
			"to": [6, 1, 15],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [7, 14, 8, 15], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_1_3_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"from": [1, 0, 3],
			"to": [2, 1, 4],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [3, 2, 4, 3], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 0],
			"to": [6, 1, 1],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"up": {"uv": [8, 1, 9, 2], "texture": "#texture"}
			}
		},
		{
			"from": [6, 0, 6],
			"to": [7, 1, 7],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [5, 5, 6, 6], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 15],
			"to": [2, 1, 16],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [1, 8, 2, 9], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 0],
			"to": [12, 1, 1],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [13, 2, 14, 3], "texture": "#texture"}
			}
		},
		{
			"from": [15, 0, 1],
			"to": [16, 1, 2],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [10, 4, 11, 5], "texture": "#texture"}
			}
		},
		{
			"from": [12, 0, 4],
			"to": [13, 1, 5],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"up": {"uv": [14, 6, 15, 7], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 3],
			"to": [9, 1, 4],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [8, 7, 9, 8], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 8],
			"to": [2, 1, 9],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 5, 11], "texture": "#texture"}
			}
		},
		{
			"from": [15, 0, 7],
			"to": [16, 1, 8],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [11, 9, 12, 10], "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 11],
			"to": [10, 1, 12],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [13, 12, 14, 13], "texture": "#texture"}
			}
		},
		{
			"from": [3, 0, 6],
			"to": [4, 1, 7],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 4, 1, 5], "texture": "#texture"}
			}
		},
		{
			"from": [15, 0, 15],
			"to": [16, 1, 16],
			"faces": {
				"north": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture", "cullface": "east"},
				"south": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [15, 10, 16, 11], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 9],
			"to": [8, 1, 10],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 11],
			"to": [5, 1, 12],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [3, 15, 4, 16], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 11],
			"to": [1, 1, 12],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 13, 1, 14], "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 10],
			"to": [14, 1, 11],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [10, 13, 11, 14], "texture": "#texture"}
			}
		},
		{
			"from": [12, 0, 13],
			"to": [13, 1, 14],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [14, 15, 15, 16], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 14],
			"to": [6, 1, 15],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [7, 14, 8, 15], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_2_1_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"from": [5, 0, 6],
			"to": [11, 1, 10],
			"faces": {
				"north": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [5, 6, 11, 10], "texture": "#texture"}
			}
		},
		{
			"from": [6, 0, 10],
			"to": [10, 1, 11],
			"faces": {
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [6, 10, 10, 11], "texture": "#texture"}
			}
		},
		{
			"from": [6, 0, 5],
			"to": [10, 1, 6],
			"faces": {
				"north": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [6, 5, 10, 6], "texture": "#texture"}
			}
		},
		{
			"from": [6, 1, 6],
			"to": [7, 2, 7],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [6, 6, 7, 7], "texture": "#texture"}
			}
		},
		{
			"from": [7, 1, 5],
			"to": [9, 2, 6],
			"faces": {
				"north": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [7, 5, 9, 6], "texture": "#texture"}
			}
		},
		{
			"from": [9, 1, 6],
			"to": [10, 2, 7],
			"faces": {
				"north": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [9, 6, 10, 7], "texture": "#texture"}
			}
		},
		{
			"from": [10, 1, 7],
			"to": [11, 2, 8],
			"faces": {
				"north": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [10, 7, 11, 8], "texture": "#texture"}
			}
		},
		{
			"from": [8, 1, 7],
			"to": [9, 2, 8],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [8, 7, 9, 8], "texture": "#texture"}
			}
		},
		{
			"from": [9, 1, 10],
			"to": [10, 2, 11],
			"faces": {
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [9, 10, 10, 11], "texture": "#texture"}
			}
		},
		{
			"from": [8, 1, 9],
			"to": [11, 2, 10],
			"faces": {
				"north": {"uv": [5, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [8, 9, 11, 10], "texture": "#texture"}
			}
		},
		{
			"from": [7, 1, 10],
			"to": [8, 2, 11],
			"faces": {
				"north": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [7, 10, 8, 11], "texture": "#texture"}
			}
		},
		{
			"from": [6, 1, 9],
			"to": [7, 2, 10],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [6, 9, 7, 10], "texture": "#texture"}
			}
		},
		{
			"from": [5, 1, 8],
			"to": [6, 2, 9],
			"faces": {
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [5, 8, 6, 9], "texture": "#texture"}
			}
		},
		{
			"from": [5, 1, 7],
			"to": [6, 2, 8],
			"faces": {
				"north": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [5, 7, 6, 8], "texture": "#texture"}
			}
		},
		{
			"from": [7, 1, 7],
			"to": [8, 2, 9],
			"faces": {
				"north": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [7, 7, 8, 9], "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 4],
			"to": [10, 2, 5],
			"faces": {
				"north": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"up": {"uv": [9, 4, 10, 5], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 4],
			"to": [6, 1, 5],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [5, 4, 6, 5], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 6],
			"to": [5, 2, 7],
			"faces": {
				"north": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"up": {"uv": [4, 6, 5, 7], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 8],
			"to": [5, 1, 9],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [4, 8, 5, 9], "texture": "#texture"}
			}
		},
		{
			"from": [6, 0, 11],
			"to": [7, 2, 12],
			"faces": {
				"north": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"east": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"up": {"uv": [6, 11, 7, 12], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 11],
			"to": [9, 1, 12],
			"faces": {
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [8, 11, 9, 12], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 9],
			"to": [12, 1, 10],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [11, 9, 12, 10], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 6],
			"to": [12, 1, 7],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [11, 6, 12, 7], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 4],
			"to": [12, 1, 5],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [11, 4, 12, 5], "texture": "#texture"}
			}
		},
		{
			"from": [10, 0, 3],
			"to": [11, 1, 4],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [10, 3, 11, 4], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 3],
			"to": [8, 2, 4],
			"faces": {
				"north": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"up": {"uv": [7, 3, 8, 4], "texture": "#texture"}
			}
		},
		{
			"from": [3, 0, 5],
			"to": [4, 1, 6],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [3, 5, 4, 6], "texture": "#texture"}
			}
		},
		{
			"from": [3, 0, 9],
			"to": [4, 2, 10],
			"faces": {
				"north": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"south": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"up": {"uv": [3, 9, 4, 10], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 10],
			"to": [5, 1, 11],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 5, 11], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 11],
			"to": [12, 1, 12],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [11, 11, 12, 12], "texture": "#texture"}
			}
		},
		{
			"from": [12, 0, 10],
			"to": [13, 2, 11],
			"faces": {
				"north": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"east": {"uv": [5, 14, 6, 16], "texture": "#texture"},
				"south": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 16], "texture": "#texture"},
				"up": {"uv": [12, 10, 13, 11], "texture": "#texture"}
			}
		},
		{
			"from": [12, 0, 7],
			"to": [13, 1, 8],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [12, 7, 13, 8], "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 12],
			"to": [10, 1, 13],
			"faces": {
				"north": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [9, 12, 10, 13], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 12],
			"to": [6, 1, 13],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [5, 12, 6, 13], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_2_2_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"name": "base1",
			"from": [5, 0, 6],
			"to": [11, 1, 10],
			"faces": {
				"north": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [5, 6, 11, 10], "texture": "#texture"}
			}
		},
		{
			"name": "base2",
			"from": [6, 0, 10],
			"to": [10, 1, 11],
			"faces": {
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [6, 10, 10, 11], "texture": "#texture"}
			}
		},
		{
			"name": "base3",
			"from": [6, 0, 5],
			"to": [10, 1, 6],
			"faces": {
				"north": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [6, 5, 10, 6], "texture": "#texture"}
			}
		},
		{
			"name": "tall1",
			"from": [4, 0, 8],
			"to": [5, 2, 9],
			"faces": {
				"north": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"up": {"uv": [4, 8, 5, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall2",
			"from": [7, 0, 4],
			"to": [8, 2, 5],
			"faces": {
				"north": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"up": {"uv": [7, 4, 8, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall3",
			"from": [11, 0, 6],
			"to": [12, 2, 7],
			"faces": {
				"north": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"up": {"uv": [11, 6, 12, 7], "texture": "#texture"}
			}
		},
		{
			"name": "tall4",
			"from": [11, 0, 9],
			"to": [12, 2, 10],
			"faces": {
				"north": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"up": {"uv": [11, 9, 12, 10], "texture": "#texture"}
			}
		},
		{
			"name": "tall5",
			"from": [8, 0, 11],
			"to": [9, 2, 12],
			"faces": {
				"north": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"east": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"up": {"uv": [8, 11, 9, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short1",
			"from": [5, 0, 4],
			"to": [6, 1, 5],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [5, 4, 6, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short2",
			"from": [8, 0, 4],
			"to": [9, 1, 5],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [8, 4, 9, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short3",
			"from": [11, 0, 5],
			"to": [12, 1, 6],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [11, 5, 12, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short4",
			"from": [10, 0, 3],
			"to": [11, 1, 4],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [10, 3, 11, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short5",
			"from": [3, 0, 3],
			"to": [4, 1, 4],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [3, 3, 4, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short6",
			"from": [3, 0, 7],
			"to": [4, 1, 8],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [3, 7, 4, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short7",
			"from": [3, 0, 10],
			"to": [4, 1, 11],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [3, 10, 4, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short8",
			"from": [4, 0, 6],
			"to": [5, 1, 7],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"up": {"uv": [4, 6, 5, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short9",
			"from": [12, 0, 6],
			"to": [13, 1, 7],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [12, 6, 13, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short10",
			"from": [11, 0, 8],
			"to": [12, 1, 9],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [11, 8, 12, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short11",
			"from": [11, 0, 10],
			"to": [12, 1, 11],
			"faces": {
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [11, 10, 12, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short12",
			"from": [12, 0, 9],
			"to": [13, 1, 10],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [12, 9, 13, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short13",
			"from": [12, 0, 11],
			"to": [13, 1, 12],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [12, 11, 13, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short14",
			"from": [10, 0, 12],
			"to": [11, 1, 13],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [10, 12, 11, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short15",
			"from": [6, 0, 12],
			"to": [7, 1, 13],
			"faces": {
				"north": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [6, 12, 7, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short16",
			"from": [7, 0, 11],
			"to": [8, 1, 12],
			"faces": {
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short17",
			"from": [9, 0, 11],
			"to": [10, 1, 12],
			"faces": {
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [9, 11, 10, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short18",
			"from": [5, 0, 11],
			"to": [6, 1, 12],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [5, 11, 6, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short19",
			"from": [4, 0, 12],
			"to": [5, 1, 13],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [4, 12, 5, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short20",
			"from": [4, 0, 9],
			"to": [5, 1, 10],
			"faces": {
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [4, 9, 5, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short21",
			"from": [6, 1, 9],
			"to": [7, 2, 10],
			"faces": {
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [6, 9, 7, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short22",
			"from": [8, 1, 9],
			"to": [9, 2, 10],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [8, 9, 9, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short23",
			"from": [8, 1, 6],
			"to": [10, 2, 7],
			"faces": {
				"north": {"uv": [6, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [8, 6, 10, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short23",
			"from": [10, 1, 9],
			"to": [11, 2, 10],
			"faces": {
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [10, 9, 11, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short24",
			"from": [6, 1, 6],
			"to": [7, 2, 7],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [6, 6, 7, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short25",
			"from": [8, 1, 5],
			"to": [9, 2, 6],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [8, 5, 9, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short26",
			"from": [9, 1, 7],
			"to": [10, 2, 8],
			"faces": {
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [9, 7, 10, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short27",
			"from": [10, 1, 8],
			"to": [11, 2, 9],
			"faces": {
				"north": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [10, 8, 11, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short27",
			"from": [7, 1, 7],
			"to": [8, 2, 8],
			"faces": {
				"north": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [7, 7, 8, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short28",
			"from": [5, 1, 7],
			"to": [6, 2, 8],
			"faces": {
				"north": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [5, 7, 6, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short29",
			"from": [5, 1, 8],
			"to": [7, 2, 9],
			"faces": {
				"north": {"uv": [9, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [5, 8, 7, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short30",
			"from": [9, 1, 9],
			"to": [10, 2, 11],
			"faces": {
				"north": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [5, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [9, 9, 10, 11], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_2_3_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"name": "base1",
			"from": [5, 0, 6],
			"to": [11, 1, 10],
			"faces": {
				"north": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [5, 6, 11, 10], "texture": "#texture"}
			}
		},
		{
			"name": "base2",
			"from": [6, 0, 10],
			"to": [10, 1, 11],
			"faces": {
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [6, 10, 10, 11], "texture": "#texture"}
			}
		},
		{
			"name": "base3",
			"from": [6, 0, 5],
			"to": [10, 1, 6],
			"faces": {
				"north": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [6, 5, 10, 6], "texture": "#texture"}
			}
		},
		{
			"name": "tall1",
			"from": [11, 0, 8],
			"to": [12, 2, 9],
			"faces": {
				"north": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"up": {"uv": [11, 8, 12, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall2",
			"from": [8, 0, 11],
			"to": [9, 2, 12],
			"faces": {
				"north": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"east": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"up": {"uv": [8, 11, 9, 12], "texture": "#texture"}
			}
		},
		{
			"name": "tall3",
			"from": [4, 0, 6],
			"to": [5, 2, 7],
			"faces": {
				"north": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"up": {"uv": [4, 6, 5, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short1",
			"from": [11, 0, 7],
			"to": [12, 1, 8],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [11, 7, 12, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short2",
			"from": [12, 0, 9],
			"to": [13, 1, 10],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [12, 9, 13, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short3",
			"from": [12, 0, 6],
			"to": [13, 1, 7],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"up": {"uv": [12, 6, 13, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short4",
			"from": [12, 0, 4],
			"to": [13, 1, 5],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [12, 4, 13, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short5",
			"from": [10, 0, 5],
			"to": [11, 1, 6],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [10, 5, 11, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short6",
			"from": [10, 0, 3],
			"to": [11, 1, 4],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [10, 3, 11, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short7",
			"from": [8, 0, 4],
			"to": [9, 1, 5],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [8, 4, 9, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short8",
			"from": [6, 0, 4],
			"to": [7, 1, 5],
			"faces": {
				"north": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short9",
			"from": [7, 0, 3],
			"to": [8, 1, 4],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [7, 3, 8, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short10",
			"from": [4, 0, 3],
			"to": [5, 1, 4],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [4, 3, 5, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short11",
			"from": [3, 0, 4],
			"to": [4, 1, 5],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [3, 4, 4, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short12",
			"from": [3, 0, 7],
			"to": [4, 1, 8],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [3, 7, 4, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short13",
			"from": [4, 0, 9],
			"to": [5, 1, 10],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [4, 9, 5, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short14",
			"from": [2, 0, 9],
			"to": [3, 1, 10],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [2, 9, 3, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short15",
			"from": [3, 0, 10],
			"to": [4, 1, 11],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [3, 10, 4, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short16",
			"from": [5, 0, 12],
			"to": [6, 1, 13],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [5, 12, 6, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short17",
			"from": [9, 0, 12],
			"to": [10, 1, 13],
			"faces": {
				"north": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [9, 12, 10, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short18",
			"from": [7, 0, 11],
			"to": [8, 1, 12],
			"faces": {
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short19",
			"from": [7, 0, 13],
			"to": [8, 1, 14],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [7, 13, 8, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short20",
			"from": [10, 0, 11],
			"to": [11, 1, 12],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [10, 11, 11, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short21",
			"from": [11, 0, 12],
			"to": [12, 1, 13],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [11, 12, 12, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short22",
			"from": [10, 1, 9],
			"to": [11, 2, 10],
			"faces": {
				"north": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [10, 9, 11, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short23",
			"from": [8, 1, 9],
			"to": [9, 2, 10],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [8, 9, 9, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short24",
			"from": [9, 1, 8],
			"to": [10, 2, 9],
			"faces": {
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [9, 8, 10, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short25",
			"from": [6, 1, 8],
			"to": [7, 2, 9],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [6, 8, 7, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short26",
			"from": [5, 1, 9],
			"to": [6, 2, 10],
			"faces": {
				"north": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [5, 9, 6, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short27",
			"from": [6, 1, 6],
			"to": [7, 2, 7],
			"faces": {
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [6, 6, 7, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short28",
			"from": [7, 1, 7],
			"to": [8, 2, 8],
			"faces": {
				"north": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [7, 7, 8, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short29",
			"from": [10, 1, 6],
			"to": [11, 2, 7],
			"faces": {
				"north": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [10, 6, 11, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short30",
			"from": [9, 1, 5],
			"to": [10, 2, 6],
			"faces": {
				"north": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [9, 5, 10, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short31",
			"from": [8, 1, 6],
			"to": [9, 2, 7],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [8, 6, 9, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short32",
			"from": [9, 1, 7],
			"to": [11, 2, 8],
			"faces": {
				"north": {"uv": [5, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [9, 7, 11, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short33",
			"from": [6, 1, 10],
			"to": [8, 2, 11],
			"faces": {
				"north": {"uv": [8, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [6, 10, 8, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short34",
			"from": [6, 1, 5],
			"to": [8, 2, 6],
			"faces": {
				"north": {"uv": [8, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [6, 5, 8, 6], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_3_1_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"name": "base1",
			"from": [3, 0, 6],
			"to": [13, 1, 10],
			"faces": {
				"north": {"uv": [3, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [3, 6, 13, 10], "texture": "#texture"}
			}
		},
		{
			"name": "base2",
			"from": [4, 0, 10],
			"to": [12, 1, 11],
			"faces": {
				"south": {"uv": [4, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 12, 11], "texture": "#texture"}
			}
		},
		{
			"name": "base3",
			"from": [4, 0, 5],
			"to": [12, 1, 6],
			"faces": {
				"north": {"uv": [4, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 5, 12, 6], "texture": "#texture"}
			}
		},
		{
			"name": "base4",
			"from": [5, 0, 11],
			"to": [11, 1, 12],
			"faces": {
				"south": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [5, 11, 11, 12], "texture": "#texture"}
			}
		},
		{
			"name": "base5",
			"from": [5, 0, 4],
			"to": [11, 1, 5],
			"faces": {
				"north": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [5, 4, 11, 5], "texture": "#texture"}
			}
		},
		{
			"name": "base6",
			"from": [6, 0, 3],
			"to": [10, 1, 4],
			"faces": {
				"north": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [6, 3, 10, 4], "texture": "#texture"}
			}
		},
		{
			"name": "base7",
			"from": [6, 0, 12],
			"to": [10, 1, 13],
			"faces": {
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [6, 12, 10, 13], "texture": "#texture"}
			}
		},
		{
			"name": "base8",
			"from": [5, 1, 7],
			"to": [11, 2, 9],
			"faces": {
				"north": {"uv": [5, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [5, 7, 11, 9], "texture": "#texture"}
			}
		},
		{
			"name": "base9",
			"from": [6, 1, 6],
			"to": [10, 2, 7],
			"faces": {
				"north": {"uv": [6, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [6, 6, 10, 7], "texture": "#texture"}
			}
		},
		{
			"name": "base10",
			"from": [7, 1, 5],
			"to": [9, 2, 6],
			"faces": {
				"north": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [7, 5, 9, 6], "texture": "#texture"}
			}
		},
		{
			"name": "base11",
			"from": [6, 1, 9],
			"to": [10, 2, 10],
			"faces": {
				"south": {"uv": [6, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [6, 9, 10, 10], "texture": "#texture"}
			}
		},
		{
			"name": "base12",
			"from": [7, 1, 10],
			"to": [9, 2, 11],
			"faces": {
				"south": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [7, 10, 9, 11], "texture": "#texture"}
			}
		},
		{
			"name": "base13",
			"from": [7, 2, 7],
			"to": [9, 3, 9],
			"faces": {
				"north": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"east": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"south": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"west": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"up": {"uv": [7, 7, 9, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall1",
			"from": [13, 0, 8],
			"to": [14, 2, 9],
			"faces": {
				"north": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"south": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"up": {"uv": [13, 8, 14, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall2",
			"from": [2, 0, 6],
			"to": [3, 2, 7],
			"faces": {
				"north": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"south": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"up": {"uv": [2, 6, 3, 7], "texture": "#texture"}
			}
		},
		{
			"name": "tall3",
			"from": [3, 0, 10],
			"to": [4, 2, 11],
			"faces": {
				"north": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"east": {"uv": [5, 14, 6, 16], "texture": "#texture"},
				"south": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 16], "texture": "#texture"},
				"up": {"uv": [3, 10, 4, 11], "texture": "#texture"}
			}
		},
		{
			"name": "tall4",
			"from": [4, 0, 12],
			"to": [5, 2, 13],
			"faces": {
				"north": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"east": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"up": {"uv": [4, 12, 5, 13], "texture": "#texture"}
			}
		},
		{
			"name": "tall5",
			"from": [11, 0, 12],
			"to": [12, 2, 13],
			"faces": {
				"north": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"east": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"up": {"uv": [11, 12, 12, 13], "texture": "#texture"}
			}
		},
		{
			"name": "tall6",
			"from": [12, 0, 4],
			"to": [13, 2, 5],
			"faces": {
				"north": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"up": {"uv": [12, 4, 13, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall7",
			"from": [9, 0, 2],
			"to": [10, 2, 3],
			"faces": {
				"north": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"east": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"west": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"up": {"uv": [9, 2, 10, 3], "texture": "#texture"}
			}
		},
		{
			"name": "tall8",
			"from": [4, 0, 4],
			"to": [5, 2, 5],
			"faces": {
				"north": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"up": {"uv": [4, 4, 5, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall9",
			"from": [2, 0, 3],
			"to": [3, 2, 4],
			"faces": {
				"north": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"south": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"up": {"uv": [2, 3, 3, 4], "texture": "#texture"}
			}
		},
		{
			"name": "tall10",
			"from": [14, 0, 3],
			"to": [15, 2, 4],
			"faces": {
				"north": {"uv": [1, 14, 2, 16], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"south": {"uv": [14, 14, 15, 16], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"up": {"uv": [14, 3, 15, 4], "texture": "#texture"}
			}
		},
		{
			"name": "tall11",
			"from": [13, 0, 14],
			"to": [14, 2, 15],
			"faces": {
				"north": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"east": {"uv": [1, 14, 2, 16], "texture": "#texture"},
				"south": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"west": {"uv": [14, 14, 15, 16], "texture": "#texture"},
				"up": {"uv": [13, 14, 14, 15], "texture": "#texture"}
			}
		},
		{
			"name": "tall12",
			"from": [3, 0, 1],
			"to": [4, 2, 2],
			"faces": {
				"north": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"east": {"uv": [14, 14, 15, 16], "texture": "#texture"},
				"south": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"west": {"uv": [1, 14, 2, 16], "texture": "#texture"},
				"up": {"uv": [3, 1, 4, 2], "texture": "#texture"}
			}
		},
		{
			"name": "tall13",
			"from": [8, 1, 4],
			"to": [9, 3, 5],
			"faces": {
				"north": {"uv": [7, 13, 8, 15], "texture": "#texture"},
				"east": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"south": {"uv": [8, 13, 9, 15], "texture": "#texture"},
				"west": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"up": {"uv": [8, 4, 9, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall14",
			"from": [5, 1, 6],
			"to": [6, 3, 7],
			"faces": {
				"north": {"uv": [10, 13, 11, 15], "texture": "#texture"},
				"east": {"uv": [9, 13, 10, 15], "texture": "#texture"},
				"south": {"uv": [5, 13, 6, 15], "texture": "#texture"},
				"west": {"uv": [6, 13, 7, 15], "texture": "#texture"},
				"up": {"uv": [5, 6, 6, 7], "texture": "#texture"}
			}
		},
		{
			"name": "tall15",
			"from": [4, 1, 8],
			"to": [5, 3, 9],
			"faces": {
				"north": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"east": {"uv": [7, 13, 8, 15], "texture": "#texture"},
				"south": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"west": {"uv": [8, 13, 9, 15], "texture": "#texture"},
				"up": {"uv": [4, 8, 5, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall16",
			"from": [9, 1, 10],
			"to": [10, 3, 11],
			"faces": {
				"north": {"uv": [6, 13, 7, 15], "texture": "#texture"},
				"east": {"uv": [5, 13, 6, 15], "texture": "#texture"},
				"south": {"uv": [9, 13, 10, 15], "texture": "#texture"},
				"west": {"uv": [10, 13, 11, 15], "texture": "#texture"},
				"up": {"uv": [9, 10, 10, 11], "texture": "#texture"}
			}
		},
		{
			"name": "tall17",
			"from": [11, 1, 6],
			"to": [12, 3, 7],
			"faces": {
				"north": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"east": {"uv": [9, 13, 10, 15], "texture": "#texture"},
				"south": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"west": {"uv": [6, 13, 7, 15], "texture": "#texture"},
				"up": {"uv": [11, 6, 12, 7], "texture": "#texture"}
			}
		},
		{
			"name": "tall18",
			"from": [6, 2, 8],
			"to": [7, 4, 9],
			"faces": {
				"north": {"uv": [9, 12, 10, 14], "texture": "#texture"},
				"east": {"uv": [7, 12, 8, 14], "texture": "#texture"},
				"south": {"uv": [6, 12, 7, 14], "texture": "#texture"},
				"west": {"uv": [8, 12, 9, 14], "texture": "#texture"},
				"up": {"uv": [6, 8, 7, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short1",
			"from": [13, 0, 5],
			"to": [14, 1, 6],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [13, 5, 14, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short2",
			"from": [11, 0, 2],
			"to": [12, 1, 3],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [11, 2, 12, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short3",
			"from": [8, 0, 2],
			"to": [9, 1, 3],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [8, 2, 9, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short4",
			"from": [6, 0, 2],
			"to": [7, 1, 3],
			"faces": {
				"north": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short5",
			"from": [4, 0, 3],
			"to": [5, 1, 4],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [4, 3, 5, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short6",
			"from": [2, 0, 2],
			"to": [3, 1, 3],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [2, 2, 3, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short7",
			"from": [3, 0, 5],
			"to": [4, 1, 6],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"}
			}
		},
		{
			"name": "short8",
			"from": [1, 0, 8],
			"to": [2, 1, 9],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [1, 8, 2, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short9",
			"from": [2, 0, 7],
			"to": [3, 1, 8],
			"faces": {
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [2, 7, 3, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short10",
			"from": [2, 0, 9],
			"to": [3, 1, 10],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [2, 9, 3, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short11",
			"from": [2, 0, 11],
			"to": [3, 1, 12],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [2, 11, 3, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short12",
			"from": [1, 0, 13],
			"to": [2, 1, 14],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [1, 13, 2, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short13",
			"from": [2, 0, 14],
			"to": [3, 1, 15],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [2, 14, 3, 15], "texture": "#texture"}
			}
		},
		{
			"name": "short14",
			"from": [5, 0, 14],
			"to": [6, 1, 15],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [5, 14, 6, 15], "texture": "#texture"}
			}
		},
		{
			"name": "short15",
			"from": [6, 0, 13],
			"to": [7, 1, 14],
			"faces": {
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [6, 13, 7, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short16",
			"from": [8, 0, 13],
			"to": [9, 1, 14],
			"faces": {
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [8, 13, 9, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short17",
			"from": [10, 0, 13],
			"to": [11, 1, 14],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [10, 13, 11, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short18",
			"from": [12, 0, 12],
			"to": [13, 1, 13],
			"faces": {
				"north": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [12, 12, 13, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short19",
			"from": [14, 0, 12],
			"to": [15, 1, 13],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [14, 12, 15, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short20",
			"from": [13, 0, 11],
			"to": [14, 1, 12],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [13, 11, 14, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short21",
			"from": [11, 0, 11],
			"to": [12, 1, 12],
			"faces": {
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [11, 11, 12, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short22",
			"from": [12, 0, 10],
			"to": [13, 1, 11],
			"faces": {
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [12, 10, 13, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short23",
			"from": [11, 1, 10],
			"to": [12, 2, 11],
			"faces": {
				"north": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [11, 10, 12, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short24",
			"from": [9, 1, 11],
			"to": [11, 2, 12],
			"faces": {
				"north": {"uv": [5, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [9, 11, 11, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short25",
			"from": [10, 1, 9],
			"to": [11, 2, 10],
			"faces": {
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"}
			}
		},
		{
			"name": "short26",
			"from": [12, 1, 7],
			"to": [13, 2, 8],
			"faces": {
				"north": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [12, 7, 13, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short27",
			"from": [11, 1, 8],
			"to": [12, 2, 9],
			"faces": {
				"north": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [11, 8, 12, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short28",
			"from": [13, 0, 9],
			"to": [14, 1, 10],
			"faces": {
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [13, 9, 14, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short29",
			"from": [10, 2, 9],
			"to": [11, 3, 10],
			"faces": {
				"north": {"uv": [5, 13, 6, 14], "texture": "#texture"},
				"east": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"south": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"west": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"up": {"uv": [10, 9, 11, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short30",
			"from": [10, 2, 7],
			"to": [11, 3, 8],
			"faces": {
				"north": {"uv": [5, 13, 6, 14], "texture": "#texture"},
				"east": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"south": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"west": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"up": {"uv": [10, 7, 11, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short31",
			"from": [10, 1, 5],
			"to": [11, 2, 6],
			"faces": {
				"north": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [10, 5, 11, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short32",
			"from": [9, 1, 4],
			"to": [10, 2, 5],
			"faces": {
				"north": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [9, 4, 10, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short33",
			"from": [8, 1, 3],
			"to": [9, 2, 4],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"up": {"uv": [8, 3, 9, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short34",
			"from": [8, 1, 12],
			"to": [9, 2, 13],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"south": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"up": {"uv": [8, 12, 9, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short35",
			"from": [6, 1, 12],
			"to": [7, 2, 13],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"up": {"uv": [6, 12, 7, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short36",
			"from": [5, 1, 11],
			"to": [6, 2, 12],
			"faces": {
				"north": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [5, 11, 6, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short37",
			"from": [4, 1, 9],
			"to": [5, 2, 10],
			"faces": {
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [4, 9, 5, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short38",
			"from": [7, 1, 11],
			"to": [8, 2, 12],
			"faces": {
				"east": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short39",
			"from": [3, 1, 8],
			"to": [4, 2, 9],
			"faces": {
				"north": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"south": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [3, 8, 4, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short40",
			"from": [4, 1, 6],
			"to": [5, 2, 8],
			"faces": {
				"north": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [4, 6, 5, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short41",
			"from": [3, 1, 5],
			"to": [4, 2, 6],
			"faces": {
				"north": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"south": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [3, 5, 4, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short42",
			"from": [5, 1, 5],
			"to": [6, 2, 6],
			"faces": {
				"north": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [5, 5, 6, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short43",
			"from": [6, 1, 3],
			"to": [7, 2, 4],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"up": {"uv": [6, 3, 7, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short44",
			"from": [7, 2, 6],
			"to": [8, 3, 7],
			"faces": {
				"north": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"east": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"west": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"up": {"uv": [7, 6, 8, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short45",
			"from": [9, 2, 6],
			"to": [10, 3, 7],
			"faces": {
				"north": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"east": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"south": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"west": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"up": {"uv": [9, 6, 10, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short46",
			"from": [9, 2, 8],
			"to": [10, 3, 9],
			"faces": {
				"north": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"east": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"south": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"up": {"uv": [9, 8, 10, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short47",
			"from": [7, 2, 10],
			"to": [8, 3, 11],
			"faces": {
				"north": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"east": {"uv": [5, 13, 6, 14], "texture": "#texture"},
				"south": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"west": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"up": {"uv": [7, 10, 8, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short48",
			"from": [8, 3, 8],
			"to": [9, 4, 9],
			"faces": {
				"north": {"uv": [7, 12, 8, 13], "texture": "#texture"},
				"east": {"uv": [7, 12, 8, 13], "texture": "#texture"},
				"south": {"uv": [8, 12, 9, 13], "texture": "#texture"},
				"west": {"uv": [8, 12, 9, 13], "texture": "#texture"},
				"up": {"uv": [8, 8, 9, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short49",
			"from": [7, 3, 7],
			"to": [8, 4, 8],
			"faces": {
				"north": {"uv": [8, 12, 9, 13], "texture": "#texture"},
				"east": {"uv": [8, 12, 9, 13], "texture": "#texture"},
				"south": {"uv": [7, 12, 8, 13], "texture": "#texture"},
				"west": {"uv": [7, 12, 8, 13], "texture": "#texture"},
				"up": {"uv": [7, 7, 8, 8], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_3_2_json =
"""{
    "ambientocclusion": false,
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"name": "base1",
			"from": [3, 0, 6],
			"to": [13, 1, 10],
			"faces": {
				"north": {"uv": [3, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [3, 6, 13, 10], "texture": "#texture"}
			}
		},
		{
			"name": "base2",
			"from": [4, 0, 10],
			"to": [12, 1, 11],
			"faces": {
				"south": {"uv": [4, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 12, 11], "texture": "#texture"}
			}
		},
		{
			"name": "base3",
			"from": [4, 0, 5],
			"to": [12, 1, 6],
			"faces": {
				"north": {"uv": [4, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [4, 5, 12, 6], "texture": "#texture"}
			}
		},
		{
			"name": "base4",
			"from": [5, 0, 11],
			"to": [11, 1, 12],
			"faces": {
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [5, 11, 11, 12], "texture": "#texture"}
			}
		},
		{
			"name": "base5",
			"from": [5, 0, 4],
			"to": [11, 1, 5],
			"faces": {
				"north": {"uv": [5, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [5, 4, 11, 5], "texture": "#texture"}
			}
		},
		{
			"name": "base6",
			"from": [6, 0, 3],
			"to": [10, 1, 4],
			"faces": {
				"north": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [6, 3, 10, 4], "texture": "#texture"}
			}
		},
		{
			"name": "base7",
			"from": [6, 0, 12],
			"to": [10, 1, 13],
			"faces": {
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [6, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [6, 12, 10, 13], "texture": "#texture"}
			}
		},
		{
			"name": "base8",
			"from": [5, 1, 7],
			"to": [11, 2, 9],
			"faces": {
				"north": {"uv": [5, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [5, 7, 11, 9], "texture": "#texture"}
			}
		},
		{
			"name": "base9",
			"from": [6, 1, 6],
			"to": [10, 2, 7],
			"faces": {
				"north": {"uv": [6, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [6, 6, 10, 7], "texture": "#texture"}
			}
		},
		{
			"name": "base10",
			"from": [7, 1, 5],
			"to": [9, 2, 6],
			"faces": {
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [7, 5, 9, 6], "texture": "#texture"}
			}
		},
		{
			"name": "base11",
			"from": [6, 1, 9],
			"to": [10, 2, 10],
			"faces": {
				"south": {"uv": [6, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [6, 9, 10, 10], "texture": "#texture"}
			}
		},
		{
			"name": "base12",
			"from": [7, 1, 10],
			"to": [9, 2, 11],
			"faces": {
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [7, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"}
			}
		},
		{
			"name": "base13",
			"from": [7, 2, 7],
			"to": [9, 3, 9],
			"faces": {
				"north": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"east": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"south": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"west": {"uv": [7, 13, 9, 14], "texture": "#texture"},
				"up": {"uv": [7, 7, 9, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall1",
			"from": [2, 0, 7],
			"to": [3, 2, 8],
			"faces": {
				"north": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"south": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"up": {"uv": [2, 7, 3, 8], "texture": "#texture"}
			}
		},
		{
			"name": "tall2",
			"from": [1, 0, 9],
			"to": [2, 2, 10],
			"faces": {
				"north": {"uv": [14, 14, 15, 16], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"south": {"uv": [1, 14, 2, 16], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"up": {"uv": [1, 9, 2, 10], "texture": "#texture"}
			}
		},
		{
			"name": "tall3",
			"from": [2, 0, 4],
			"to": [3, 2, 5],
			"faces": {
				"north": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"up": {"uv": [2, 4, 3, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall4",
			"from": [4, 0, 3],
			"to": [5, 2, 4],
			"faces": {
				"north": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"up": {"uv": [4, 3, 5, 4], "texture": "#texture"}
			}
		},
		{
			"name": "tall5",
			"from": [6, 0, 1],
			"to": [7, 2, 2],
			"faces": {
				"north": {"uv": [9, 14, 10, 16], "texture": "#texture"},
				"east": {"uv": [14, 14, 15, 16], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 16], "texture": "#texture"},
				"west": {"uv": [1, 14, 2, 16], "texture": "#texture"},
				"up": {"uv": [6, 1, 7, 2], "texture": "#texture"}
			}
		},
		{
			"name": "tall6",
			"from": [10, 0, 3],
			"to": [11, 2, 4],
			"faces": {
				"north": {"uv": [5, 14, 6, 16], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 16], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"up": {"uv": [10, 3, 11, 4], "texture": "#texture"}
			}
		},
		{
			"name": "tall7",
			"from": [13, 0, 4],
			"to": [14, 2, 5],
			"faces": {
				"north": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 16], "texture": "#texture"},
				"south": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"up": {"uv": [13, 4, 14, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall8",
			"from": [13, 0, 7],
			"to": [14, 2, 8],
			"faces": {
				"north": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"east": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"south": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"up": {"uv": [13, 7, 14, 8], "texture": "#texture"}
			}
		},
		{
			"name": "tall9",
			"from": [12, 0, 10],
			"to": [13, 2, 11],
			"faces": {
				"east": {"uv": [5, 14, 6, 16], "texture": "#texture"},
				"south": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 16], "texture": "#texture"},
				"up": {"uv": [12, 10, 13, 11], "texture": "#texture"}
			}
		},
		{
			"name": "tall10",
			"from": [13, 0, 13],
			"to": [14, 2, 14],
			"faces": {
				"north": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"east": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"south": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"west": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"up": {"uv": [13, 13, 14, 14], "texture": "#texture"}
			}
		},
		{
			"name": "tall11",
			"from": [7, 0, 13],
			"to": [8, 2, 14],
			"faces": {
				"north": {"uv": [8, 14, 9, 16], "texture": "#texture"},
				"east": {"uv": [2, 14, 3, 16], "texture": "#texture"},
				"south": {"uv": [7, 14, 8, 16], "texture": "#texture"},
				"west": {"uv": [13, 14, 14, 16], "texture": "#texture"},
				"up": {"uv": [7, 13, 8, 14], "texture": "#texture"}
			}
		},
		{
			"name": "tall12",
			"from": [4, 0, 12],
			"to": [5, 2, 13],
			"faces": {
				"east": {"uv": [3, 14, 4, 16], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 16], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 16], "texture": "#texture"},
				"up": {"uv": [4, 12, 5, 13], "texture": "#texture"}
			}
		},
		{
			"name": "tall13",
			"from": [3, 1, 8],
			"to": [4, 3, 9],
			"faces": {
				"north": {"uv": [12, 13, 13, 15], "texture": "#texture"},
				"east": {"uv": [7, 13, 8, 15], "texture": "#texture"},
				"south": {"uv": [3, 13, 4, 15], "texture": "#texture"},
				"west": {"uv": [8, 13, 9, 15], "texture": "#texture"},
				"up": {"uv": [3, 8, 4, 9], "texture": "#texture"}
			}
		},
		{
			"name": "tall14",
			"from": [4, 1, 11],
			"to": [5, 3, 12],
			"faces": {
				"north": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"east": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"south": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"west": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"up": {"uv": [4, 11, 5, 12], "texture": "#texture"}
			}
		},
		{
			"name": "tall15",
			"from": [9, 1, 11],
			"to": [10, 3, 12],
			"faces": {
				"north": {"uv": [6, 13, 7, 15], "texture": "#texture"},
				"east": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"south": {"uv": [9, 13, 10, 15], "texture": "#texture"},
				"west": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"up": {"uv": [9, 11, 10, 12], "texture": "#texture"}
			}
		},
		{
			"name": "tall16",
			"from": [12, 1, 9],
			"to": [13, 3, 10],
			"faces": {
				"north": {"uv": [3, 13, 4, 15], "texture": "#texture"},
				"east": {"uv": [6, 13, 7, 15], "texture": "#texture"},
				"south": {"uv": [12, 13, 13, 15], "texture": "#texture"},
				"west": {"uv": [9, 13, 10, 15], "texture": "#texture"},
				"up": {"uv": [12, 9, 13, 10], "texture": "#texture"}
			}
		},
		{
			"name": "tall17",
			"from": [11, 1, 6],
			"to": [12, 3, 7],
			"faces": {
				"north": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"east": {"uv": [9, 13, 10, 15], "texture": "#texture"},
				"south": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"west": {"uv": [6, 13, 7, 15], "texture": "#texture"},
				"up": {"uv": [11, 6, 12, 7], "texture": "#texture"}
			}
		},
		{
			"name": "tall18",
			"from": [8, 1, 4],
			"to": [9, 3, 5],
			"faces": {
				"north": {"uv": [7, 13, 8, 15], "texture": "#texture"},
				"east": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"south": {"uv": [8, 13, 9, 15], "texture": "#texture"},
				"west": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"up": {"uv": [8, 4, 9, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall19",
			"from": [5, 1, 4],
			"to": [6, 3, 5],
			"faces": {
				"north": {"uv": [10, 13, 11, 15], "texture": "#texture"},
				"east": {"uv": [11, 13, 12, 15], "texture": "#texture"},
				"south": {"uv": [5, 13, 6, 15], "texture": "#texture"},
				"west": {"uv": [4, 13, 5, 15], "texture": "#texture"},
				"up": {"uv": [5, 4, 6, 5], "texture": "#texture"}
			}
		},
		{
			"name": "tall20",
			"from": [5, 2, 7],
			"to": [6, 4, 8],
			"faces": {
				"north": {"uv": [10, 12, 11, 14], "texture": "#texture"},
				"east": {"uv": [8, 12, 9, 14], "texture": "#texture"},
				"south": {"uv": [5, 12, 6, 14], "texture": "#texture"},
				"west": {"uv": [7, 12, 8, 14], "texture": "#texture"},
				"up": {"uv": [5, 7, 6, 8], "texture": "#texture"}
			}
		},
		{
			"name": "tall21",
			"from": [8, 2, 6],
			"to": [9, 4, 7],
			"faces": {
				"north": {"uv": [7, 12, 8, 14], "texture": "#texture"},
				"east": {"uv": [9, 12, 10, 14], "texture": "#texture"},
				"south": {"uv": [8, 12, 9, 14], "texture": "#texture"},
				"west": {"uv": [6, 12, 7, 14], "texture": "#texture"},
				"up": {"uv": [8, 6, 9, 7], "texture": "#texture"}
			}
		},
		{
			"name": "tall22",
			"from": [8, 2, 10],
			"to": [9, 4, 11],
			"faces": {
				"north": {"uv": [7, 12, 8, 14], "texture": "#texture"},
				"east": {"uv": [5, 12, 6, 14], "texture": "#texture"},
				"south": {"uv": [8, 12, 9, 14], "texture": "#texture"},
				"west": {"uv": [10, 12, 11, 14], "texture": "#texture"},
				"up": {"uv": [8, 10, 9, 11], "texture": "#texture"}
			}
		},
		{
			"name": "tall23",
			"from": [8, 3, 8],
			"to": [9, 5, 9],
			"faces": {
				"north": {"uv": [7, 11, 8, 13], "texture": "#texture"},
				"east": {"uv": [7, 11, 8, 13], "texture": "#texture"},
				"south": {"uv": [8, 11, 9, 13], "texture": "#texture"},
				"west": {"uv": [8, 11, 9, 13], "texture": "#texture"},
				"up": {"uv": [8, 8, 9, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short1",
			"from": [11, 0, 3],
			"to": [12, 1, 4],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [11, 3, 12, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short2",
			"from": [13, 0, 3],
			"to": [14, 1, 4],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [13, 3, 14, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short3",
			"from": [9, 0, 2],
			"to": [10, 1, 3],
			"faces": {
				"north": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [9, 2, 10, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short4",
			"from": [5, 0, 2],
			"to": [6, 1, 3],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [5, 2, 6, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short5",
			"from": [2, 0, 1],
			"to": [3, 1, 2],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"east": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"up": {"uv": [2, 1, 3, 2], "texture": "#texture"}
			}
		},
		{
			"name": "short6",
			"from": [3, 0, 3],
			"to": [4, 1, 4],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"up": {"uv": [3, 3, 4, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short7",
			"from": [2, 0, 5],
			"to": [3, 1, 6],
			"faces": {
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [2, 5, 3, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short8",
			"from": [1, 0, 4],
			"to": [2, 1, 5],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [1, 4, 2, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short9",
			"from": [14, 0, 4],
			"to": [15, 1, 5],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [14, 4, 15, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short10",
			"from": [13, 0, 6],
			"to": [14, 1, 7],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [13, 6, 14, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short11",
			"from": [14, 0, 8],
			"to": [15, 1, 9],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [14, 8, 15, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short12",
			"from": [13, 0, 9],
			"to": [14, 1, 10],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [13, 9, 14, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short13",
			"from": [14, 0, 12],
			"to": [15, 1, 13],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [14, 12, 15, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short14",
			"from": [12, 0, 11],
			"to": [13, 1, 12],
			"faces": {
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [12, 11, 13, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short15",
			"from": [11, 0, 12],
			"to": [12, 1, 13],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [11, 12, 12, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short16",
			"from": [9, 0, 14],
			"to": [10, 1, 15],
			"faces": {
				"north": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [9, 14, 10, 15], "texture": "#texture"}
			}
		},
		{
			"name": "short17",
			"from": [8, 0, 13],
			"to": [9, 1, 14],
			"faces": {
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [8, 13, 9, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short18",
			"from": [5, 0, 13],
			"to": [6, 1, 14],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [5, 13, 6, 14], "texture": "#texture"}
			}
		},
		{
			"name": "short19",
			"from": [4, 0, 11],
			"to": [5, 1, 12],
			"faces": {
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"}
			}
		},
		{
			"name": "short20",
			"from": [3, 0, 12],
			"to": [4, 1, 13],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [3, 12, 4, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short21",
			"from": [2, 0, 10],
			"to": [3, 1, 11],
			"faces": {
				"north": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [2, 10, 3, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short22",
			"from": [2, 0, 8],
			"to": [3, 1, 9],
			"faces": {
				"south": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [2, 8, 3, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short23",
			"from": [4, 1, 9],
			"to": [5, 2, 10],
			"faces": {
				"north": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"east": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"west": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [4, 9, 5, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short24",
			"from": [4, 1, 7],
			"to": [5, 2, 8],
			"faces": {
				"north": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"west": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"up": {"uv": [4, 7, 5, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short25",
			"from": [3, 1, 6],
			"to": [4, 2, 7],
			"faces": {
				"north": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"west": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"up": {"uv": [3, 6, 4, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short26",
			"from": [4, 1, 5],
			"to": [5, 2, 6],
			"faces": {
				"north": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"south": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [4, 5, 5, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short27",
			"from": [6, 1, 5],
			"to": [7, 2, 6],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [6, 5, 7, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short28",
			"from": [6, 1, 3],
			"to": [7, 2, 4],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"up": {"uv": [6, 3, 7, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short29",
			"from": [8, 1, 3],
			"to": [9, 2, 4],
			"faces": {
				"north": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"west": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"up": {"uv": [8, 3, 9, 4], "texture": "#texture"}
			}
		},
		{
			"name": "short30",
			"from": [7, 0, 2],
			"to": [8, 1, 3],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [7, 2, 8, 3], "texture": "#texture"}
			}
		},
		{
			"name": "short31",
			"from": [7, 1, 4],
			"to": [8, 2, 5],
			"faces": {
				"north": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"up": {"uv": [7, 4, 8, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short32",
			"from": [9, 1, 4],
			"to": [10, 2, 5],
			"faces": {
				"north": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"east": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"up": {"uv": [9, 4, 10, 5], "texture": "#texture"}
			}
		},
		{
			"name": "short33",
			"from": [11, 1, 5],
			"to": [12, 2, 6],
			"faces": {
				"north": {"uv": [4, 14, 5, 15], "texture": "#texture"},
				"east": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"up": {"uv": [11, 5, 12, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short34",
			"from": [11, 1, 7],
			"to": [12, 2, 8],
			"faces": {
				"east": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"south": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [11, 7, 12, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short35",
			"from": [12, 1, 6],
			"to": [13, 2, 7],
			"faces": {
				"north": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"east": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"south": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"up": {"uv": [12, 6, 13, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short36",
			"from": [12, 1, 8],
			"to": [13, 2, 9],
			"faces": {
				"north": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"east": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"west": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"up": {"uv": [12, 8, 13, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short37",
			"from": [10, 1, 9],
			"to": [12, 2, 10],
			"faces": {
				"north": {"uv": [4, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [10, 9, 12, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short38",
			"from": [10, 1, 10],
			"to": [11, 2, 11],
			"faces": {
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [10, 10, 11, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short39",
			"from": [9, 1, 12],
			"to": [10, 2, 13],
			"faces": {
				"east": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"south": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"up": {"uv": [9, 12, 10, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short40",
			"from": [8, 1, 11],
			"to": [9, 2, 12],
			"faces": {
				"south": {"uv": [8, 14, 9, 15], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 15], "texture": "#texture"},
				"up": {"uv": [8, 11, 9, 12], "texture": "#texture"}
			}
		},
		{
			"name": "short41",
			"from": [6, 1, 12],
			"to": [7, 2, 13],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [3, 14, 4, 15], "texture": "#texture"},
				"south": {"uv": [6, 14, 7, 15], "texture": "#texture"},
				"west": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"up": {"uv": [6, 12, 7, 13], "texture": "#texture"}
			}
		},
		{
			"name": "short42",
			"from": [5, 1, 10],
			"to": [6, 2, 11],
			"faces": {
				"north": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"east": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"south": {"uv": [5, 14, 6, 15], "texture": "#texture"},
				"west": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"up": {"uv": [5, 10, 6, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short43",
			"from": [7, 2, 10],
			"to": [8, 3, 11],
			"faces": {
				"north": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"south": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"west": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"up": {"uv": [7, 10, 8, 11], "texture": "#texture"}
			}
		},
		{
			"name": "short44",
			"from": [8, 2, 9],
			"to": [9, 3, 10],
			"faces": {
				"east": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"west": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"up": {"uv": [8, 9, 9, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short45",
			"from": [10, 2, 9],
			"to": [11, 3, 10],
			"faces": {
				"north": {"uv": [5, 13, 6, 14], "texture": "#texture"},
				"east": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"south": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"west": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"up": {"uv": [10, 9, 11, 10], "texture": "#texture"}
			}
		},
		{
			"name": "short46",
			"from": [9, 2, 8],
			"to": [10, 3, 9],
			"faces": {
				"north": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"east": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"south": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"up": {"uv": [9, 8, 10, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short47",
			"from": [10, 2, 7],
			"to": [11, 3, 8],
			"faces": {
				"north": {"uv": [5, 13, 6, 14], "texture": "#texture"},
				"east": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"south": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"west": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"up": {"uv": [10, 7, 11, 8], "texture": "#texture"}
			}
		},
		{
			"name": "short48",
			"from": [7, 2, 5],
			"to": [8, 3, 6],
			"faces": {
				"north": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"east": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"south": {"uv": [7, 13, 8, 14], "texture": "#texture"},
				"west": {"uv": [5, 13, 6, 14], "texture": "#texture"},
				"up": {"uv": [7, 5, 8, 6], "texture": "#texture"}
			}
		},
		{
			"name": "short49",
			"from": [6, 2, 6],
			"to": [7, 3, 7],
			"faces": {
				"north": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"east": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"south": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"west": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"up": {"uv": [6, 6, 7, 7], "texture": "#texture"}
			}
		},
		{
			"name": "short50",
			"from": [6, 2, 8],
			"to": [7, 3, 9],
			"faces": {
				"north": {"uv": [9, 13, 10, 14], "texture": "#texture"},
				"south": {"uv": [6, 13, 7, 14], "texture": "#texture"},
				"west": {"uv": [8, 13, 9, 14], "texture": "#texture"},
				"up": {"uv": [6, 8, 7, 9], "texture": "#texture"}
			}
		},
		{
			"name": "short51",
			"from": [7, 3, 7],
			"to": [8, 4, 8],
			"faces": {
				"north": {"uv": [8, 12, 9, 13], "texture": "#texture"},
				"east": {"uv": [8, 12, 9, 13], "texture": "#texture"},
				"south": {"uv": [7, 12, 8, 13], "texture": "#texture"},
				"west": {"uv": [7, 12, 8, 13], "texture": "#texture"},
				"up": {"uv": [7, 7, 8, 8], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_salt_item_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"from": [3, 0, 2],
			"to": [4, 1, 3],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [3, 2, 4, 3], "texture": "#texture"},
				"down": {"uv": [3, 13, 4, 14], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 1],
			"to": [9, 1, 2],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"up": {"uv": [8, 1, 9, 2], "texture": "#texture"},
				"down": {"uv": [8, 14, 9, 15], "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 5],
			"to": [6, 1, 6],
			"faces": {
				"north": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"east": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"south": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"west": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"up": {"uv": [5, 5, 6, 6], "texture": "#texture"},
				"down": {"uv": [5, 10, 6, 11], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 8],
			"to": [2, 1, 9],
			"faces": {
				"north": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"west": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"up": {"uv": [1, 8, 2, 9], "texture": "#texture"},
				"down": {"uv": [1, 7, 2, 8], "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 2],
			"to": [14, 1, 3],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"up": {"uv": [13, 2, 14, 3], "texture": "#texture"},
				"down": {"uv": [13, 13, 14, 14], "texture": "#texture"}
			}
		},
		{
			"from": [10, 0, 4],
			"to": [11, 1, 5],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"up": {"uv": [10, 4, 11, 5], "texture": "#texture"},
				"down": {"uv": [10, 11, 11, 12], "texture": "#texture"}
			}
		},
		{
			"from": [14, 0, 6],
			"to": [15, 1, 7],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"up": {"uv": [14, 6, 15, 7], "texture": "#texture"},
				"down": {"uv": [14, 9, 15, 10], "texture": "#texture"}
			}
		},
		{
			"from": [8, 0, 7],
			"to": [9, 1, 8],
			"faces": {
				"north": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"east": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"south": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"west": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"up": {"uv": [8, 7, 9, 8], "texture": "#texture"},
				"down": {"uv": [8, 8, 9, 9], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 10],
			"to": [5, 1, 11],
			"faces": {
				"north": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"south": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [4, 10, 5, 11], "texture": "#texture"},
				"down": {"uv": [4, 5, 5, 6], "texture": "#texture"}
			}
		},
		{
			"from": [11, 0, 9],
			"to": [12, 1, 10],
			"faces": {
				"north": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"east": {"uv": [6, 15, 7, 16], "texture": "#texture"},
				"south": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"west": {"uv": [9, 15, 10, 16], "texture": "#texture"},
				"up": {"uv": [11, 9, 12, 10], "texture": "#texture"},
				"down": {"uv": [11, 6, 12, 7], "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 12],
			"to": [14, 1, 13],
			"faces": {
				"north": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"east": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"south": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"west": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"up": {"uv": [13, 12, 14, 13], "texture": "#texture"},
				"down": {"uv": [13, 3, 14, 4], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 4],
			"to": [1, 1, 5],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [4, 15, 5, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 4, 1, 5], "texture": "#texture"},
				"down": {"uv": [0, 11, 1, 12], "texture": "#texture"}
			}
		},
		{
			"from": [15, 0, 10],
			"to": [16, 1, 11],
			"faces": {
				"north": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"east": {"uv": [5, 15, 6, 16], "texture": "#texture", "cullface": "east"},
				"south": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"west": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"up": {"uv": [15, 10, 16, 11], "texture": "#texture"},
				"down": {"uv": [15, 5, 16, 6], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 11],
			"to": [8, 1, 12],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [4, 15, 5, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [11, 15, 12, 16], "texture": "#texture"},
				"up": {"uv": [7, 11, 8, 12], "texture": "#texture"},
				"down": {"uv": [7, 4, 8, 5], "texture": "#texture"}
			}
		},
		{
			"from": [3, 0, 15],
			"to": [4, 1, 16],
			"faces": {
				"north": {"uv": [12, 15, 13, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [3, 15, 4, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [3, 15, 4, 16], "texture": "#texture"},
				"down": {"uv": [3, 0, 4, 1], "texture": "#texture"}
			}
		},
		{
			"from": [0, 0, 13],
			"to": [1, 1, 14],
			"faces": {
				"north": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture", "cullface": "west"},
				"up": {"uv": [0, 13, 1, 14], "texture": "#texture"},
				"down": {"uv": [0, 2, 1, 3], "texture": "#texture"}
			}
		},
		{
			"from": [10, 0, 13],
			"to": [11, 1, 14],
			"faces": {
				"north": {"uv": [5, 15, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 15, 3, 16], "texture": "#texture"},
				"south": {"uv": [10, 15, 11, 16], "texture": "#texture"},
				"west": {"uv": [13, 15, 14, 16], "texture": "#texture"},
				"up": {"uv": [10, 13, 11, 14], "texture": "#texture"},
				"down": {"uv": [10, 2, 11, 3], "texture": "#texture"}
			}
		},
		{
			"from": [14, 0, 15],
			"to": [15, 1, 16],
			"faces": {
				"north": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"east": {"uv": [0, 15, 1, 16], "texture": "#texture"},
				"south": {"uv": [14, 15, 15, 16], "texture": "#texture", "cullface": "south"},
				"west": {"uv": [15, 15, 16, 16], "texture": "#texture"},
				"up": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"down": {"uv": [14, 0, 15, 1], "texture": "#texture"}
			}
		},
		{
			"from": [7, 0, 14],
			"to": [8, 1, 15],
			"faces": {
				"north": {"uv": [8, 15, 9, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 2, 16], "texture": "#texture"},
				"south": {"uv": [7, 15, 8, 16], "texture": "#texture"},
				"west": {"uv": [14, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [7, 14, 8, 15], "texture": "#texture"},
				"down": {"uv": [7, 1, 8, 2], "texture": "#texture"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.5, 2],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.5, 2],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [0, 45, 0],
			"translation": [0, 3.5, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, 225, 0],
			"translation": [0, 3.5, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"scale": [0.625, 0.625, 0.625]
		},
		"head": {
			"translation": [0, 12, 0],
			"scale": [0.7, 0.7, 0.7]
		},
		"fixed": {
			"rotation": [-90, 0, 0],
			"translation": [0, 0, -4],
			"scale": [0.5, 0.5, 0.5]
		}
	}
}"""

private const val template_alarm_json =
"""{
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"name": "top",
			"from": [3, 0, 6],
			"to": [13, 4, 10],
			"faces": {
				"north": {"uv": [3, 6, 13, 7], "texture": "#texture"},
				"east": {"uv": [3, 6, 7, 7], "texture": "#texture"},
				"south": {"uv": [3, 11, 13, 12], "texture": "#texture"},
				"west": {"uv": [9, 10, 13, 11], "texture": "#texture"},
				"up": {"uv": [3, 6, 13, 10], "texture": "#texture"},
				"down": {"uv": [3, 6, 13, 10], "rotation": 180, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [3, 0, 10],
			"to": [13, 3, 11],
			"faces": {
				"east": {"uv": [9, 10, 10, 13], "texture": "#texture"},
				"south": {"uv": [3, 10, 13, 13], "texture": "#texture"},
				"west": {"uv": [6, 10, 7, 13], "texture": "#texture"},
				"up": {"uv": [3, 13, 13, 14], "texture": "#texture"},
				"down": {"uv": [3, 15, 13, 16], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [3, 0, 5],
			"to": [13, 3, 6],
			"faces": {
				"north": {"uv": [3, 13, 13, 16], "texture": "#texture"},
				"east": {"uv": [7, 13, 8, 16], "texture": "#texture"},
				"west": {"uv": [8, 13, 9, 16], "texture": "#texture"},
				"up": {"uv": [3, 6, 13, 7], "texture": "#texture"},
				"down": {"uv": [3, 12, 13, 13], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [13, 0, 6],
			"to": [14, 3, 10],
			"faces": {
				"north": {"uv": [11, 10, 12, 13], "texture": "#texture"},
				"east": {"uv": [3, 10, 7, 13], "texture": "#texture"},
				"south": {"uv": [4, 10, 5, 13], "texture": "#texture"},
				"up": {"uv": [3, 13, 7, 14], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [3, 12, 7, 13], "rotation": 90, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [2, 0, 6],
			"to": [3, 3, 10],
			"faces": {
				"north": {"uv": [8, 13, 9, 16], "texture": "#texture"},
				"south": {"uv": [11, 10, 12, 13], "texture": "#texture"},
				"west": {"uv": [9, 13, 13, 16], "texture": "#texture"},
				"up": {"uv": [8, 13, 12, 14], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [9, 12, 13, 13], "rotation": 270, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [10, 7.3, 6],
			"to": [12, 10, 7],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [3, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [10, 0, 12, 3], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [10, 7.3, 9],
			"to": [12, 10, 10],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [10, 3, 12, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [1, 3, 3, 6], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [12, 7.3, 7],
			"to": [13, 10, 9],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [14, 3, 16, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [9, 7.3, 7],
			"to": [10, 10, 9],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [14, 0, 16, 3], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [10, 10, 7],
			"to": [12, 11, 9],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [8, 2, 10, 3], "texture": "#texture"},
				"east": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"south": {"uv": [3, 3, 5, 4], "texture": "#texture"},
				"west": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"up": {"uv": [12, 2, 14, 4], "texture": "#texture"},
				"down": {"uv": [12, 2, 14, 4], "texture": "#texture"}
			}
		},
		{
			"name": "stick",
			"from": [10.5, 5, 7.5],
			"to": [11.5, 10, 8.5],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [0, 11, 1, 16], "texture": "#texture"},
				"east": {"uv": [13, 11, 14, 16], "texture": "#texture"},
				"south": {"uv": [1, 11, 2, 16], "texture": "#texture"},
				"west": {"uv": [2, 11, 3, 16], "texture": "#texture"}
			}
		},
		{
			"from": [4, 7.3, 6],
			"to": [6, 10, 7],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [3, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [10, 0, 12, 3], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [4, 7.3, 9],
			"to": [6, 10, 10],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [10, 3, 12, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [1, 3, 3, 6], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [6, 7.3, 7],
			"to": [7, 10, 9],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [14, 3, 16, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [3, 7.3, 7],
			"to": [4, 10, 9],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [14, 0, 16, 3], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [4, 10, 7],
			"to": [6, 11, 9],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [8, 2, 10, 3], "texture": "#texture"},
				"east": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"south": {"uv": [3, 3, 5, 4], "texture": "#texture"},
				"west": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"up": {"uv": [12, 2, 14, 4], "texture": "#texture"},
				"down": {"uv": [12, 2, 14, 4], "texture": "#texture"}
			}
		},
		{
			"name": "stick",
			"from": [4.5, 5, 7.5],
			"to": [5.5, 10, 8.5],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [2, 11, 3, 16], "texture": "#texture"},
				"east": {"uv": [15, 10, 16, 16], "texture": "#texture"},
				"south": {"uv": [13, 11, 14, 16], "texture": "#texture"},
				"west": {"uv": [14, 11, 15, 16], "texture": "#texture"}
			}
		}
	],
	"groups": [
		{
			"name": "base",
			"origin": [0, 0, 0],
			"children": [0, 1, 2, 3, 4]
		},
		{
			"name": "bell1",
			"origin": [0, 0, 0],
			"children": [5, 6, 7, 8, 9, 10]
		},
		{
			"name": "bell2",
			"origin": [0, 0, 0],
			"children": [11, 12, 13, 14, 15, 16]
		}
	]
}"""

private const val template_alarm_item_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"name": "top",
			"from": [3, 0, 6],
			"to": [13, 4, 10],
			"faces": {
				"north": {"uv": [3, 6, 13, 7], "texture": "#texture"},
				"east": {"uv": [3, 6, 7, 7], "texture": "#texture"},
				"south": {"uv": [3, 11, 13, 12], "texture": "#texture"},
				"west": {"uv": [9, 10, 13, 11], "texture": "#texture"},
				"up": {"uv": [3, 6, 13, 10], "texture": "#texture"},
				"down": {"uv": [3, 6, 13, 10], "rotation": 180, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [3, 0, 10],
			"to": [13, 3, 11],
			"faces": {
				"east": {"uv": [9, 10, 10, 13], "texture": "#texture"},
				"south": {"uv": [3, 10, 13, 13], "texture": "#texture"},
				"west": {"uv": [6, 10, 7, 13], "texture": "#texture"},
				"up": {"uv": [3, 13, 13, 14], "texture": "#texture"},
				"down": {"uv": [3, 15, 13, 16], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [3, 0, 5],
			"to": [13, 3, 6],
			"faces": {
				"north": {"uv": [3, 13, 13, 16], "texture": "#texture"},
				"east": {"uv": [7, 13, 8, 16], "texture": "#texture"},
				"west": {"uv": [8, 13, 9, 16], "texture": "#texture"},
				"up": {"uv": [3, 6, 13, 7], "texture": "#texture"},
				"down": {"uv": [3, 12, 13, 13], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [13, 0, 6],
			"to": [14, 3, 10],
			"faces": {
				"north": {"uv": [11, 10, 12, 13], "texture": "#texture"},
				"east": {"uv": [3, 10, 7, 13], "texture": "#texture"},
				"south": {"uv": [4, 10, 5, 13], "texture": "#texture"},
				"up": {"uv": [3, 13, 7, 14], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [3, 12, 7, 13], "rotation": 90, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [2, 0, 6],
			"to": [3, 3, 10],
			"faces": {
				"north": {"uv": [8, 13, 9, 16], "texture": "#texture"},
				"south": {"uv": [11, 10, 12, 13], "texture": "#texture"},
				"west": {"uv": [9, 13, 13, 16], "texture": "#texture"},
				"up": {"uv": [8, 13, 12, 14], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [9, 12, 13, 13], "rotation": 270, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [10, 7.3, 6],
			"to": [12, 10, 7],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [3, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [10, 0, 12, 3], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [10, 7.3, 9],
			"to": [12, 10, 10],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [10, 3, 12, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [1, 3, 3, 6], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [12, 7.3, 7],
			"to": [13, 10, 9],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [14, 3, 16, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [9, 7.3, 7],
			"to": [10, 10, 9],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [14, 0, 16, 3], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [10, 10, 7],
			"to": [12, 11, 9],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [8, 2, 10, 3], "texture": "#texture"},
				"east": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"south": {"uv": [3, 3, 5, 4], "texture": "#texture"},
				"west": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"up": {"uv": [12, 2, 14, 4], "texture": "#texture"},
				"down": {"uv": [12, 2, 14, 4], "texture": "#texture"}
			}
		},
		{
			"name": "stick",
			"from": [10.5, 5, 7.5],
			"to": [11.5, 10, 8.5],
			"rotation": {"angle": -22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [0, 11, 1, 16], "texture": "#texture"},
				"east": {"uv": [13, 11, 14, 16], "texture": "#texture"},
				"south": {"uv": [1, 11, 2, 16], "texture": "#texture"},
				"west": {"uv": [2, 11, 3, 16], "texture": "#texture"}
			}
		},
		{
			"from": [4, 7.3, 6],
			"to": [6, 10, 7],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [3, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [10, 0, 12, 3], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [4, 7.3, 9],
			"to": [6, 10, 10],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [10, 3, 12, 6], "texture": "#texture"},
				"east": {"uv": [0, 3, 1, 6], "texture": "#texture"},
				"south": {"uv": [1, 3, 3, 6], "texture": "#texture"},
				"west": {"uv": [9, 2, 10, 5], "texture": "#texture"},
				"up": {"uv": [3, 2, 5, 3], "texture": "#texture"},
				"down": {"uv": [11, 5, 13, 6], "texture": "#texture"}
			}
		},
		{
			"from": [6, 7.3, 7],
			"to": [7, 10, 9],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [14, 3, 16, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [3, 7.3, 7],
			"to": [4, 10, 9],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"east": {"uv": [14, 0, 16, 3], "texture": "#texture"},
				"south": {"uv": [4, 3, 5, 6], "texture": "#texture"},
				"west": {"uv": [2, 3, 4, 6], "texture": "#texture"},
				"up": {"uv": [6, 2, 7, 4], "texture": "#texture"},
				"down": {"uv": [14, 4, 15, 6], "texture": "#texture"}
			}
		},
		{
			"from": [4, 10, 7],
			"to": [6, 11, 9],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [8, 2, 10, 3], "texture": "#texture"},
				"east": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"south": {"uv": [3, 3, 5, 4], "texture": "#texture"},
				"west": {"uv": [1, 2, 3, 3], "texture": "#texture"},
				"up": {"uv": [12, 2, 14, 4], "texture": "#texture"},
				"down": {"uv": [12, 2, 14, 4], "texture": "#texture"}
			}
		},
		{
			"name": "stick",
			"from": [4.5, 5, 7.5],
			"to": [5.5, 10, 8.5],
			"rotation": {"angle": 22.5, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [2, 11, 3, 16], "texture": "#texture"},
				"east": {"uv": [15, 10, 16, 16], "texture": "#texture"},
				"south": {"uv": [13, 11, 14, 16], "texture": "#texture"},
				"west": {"uv": [14, 11, 15, 16], "texture": "#texture"}
			}
		},
		{
			"from": [7.7, 3.3, 7.7],
			"to": [8.3, 8.3, 8.3],
			"rotation": {"angle": 0, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [1, 9, 2, 16], "texture": "#texture"},
				"east": {"uv": [14, 9, 15, 16], "texture": "#texture"},
				"south": {"uv": [13, 9, 14, 16], "texture": "#texture"},
				"west": {"uv": [2, 9, 3, 16], "texture": "#texture"}
			}
		},
		{
			"from": [7.1, 8.3, 7.6],
			"to": [8.9, 9.1, 8.4],
			"rotation": {"angle": 0, "axis": "z", "origin": [8, 4, 8]},
			"faces": {
				"north": {"uv": [1, 11, 3, 12], "texture": "#texture"},
				"east": {"uv": [14, 9, 15, 10], "texture": "#texture"},
				"south": {"uv": [1, 11, 3, 12], "texture": "#texture"},
				"west": {"uv": [14, 8, 15, 9], "texture": "#texture"},
				"up": {"uv": [13, 12, 15, 13], "texture": "#texture"},
				"down": {"uv": [1, 14, 3, 15], "texture": "#texture"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 0, 0],
			"translation": [0, 1.75, 2.5],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 0, 0],
			"translation": [0, 1.75, 2.5],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [0, 135, 0],
			"translation": [0, 3, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, 135, 0],
			"translation": [0, 3, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 135, 0],
			"scale": [0.625, 0.625, 0.625]
		},
		"head": {
			"translation": [0, 11.25, 0]
		},
		"fixed": {
			"scale": [0.5, 0.5, 0.5]
		}
	},
	"groups": [
		{
			"name": "base",
			"origin": [0, 0, 0],
			"children": [0, 1, 2, 3, 4]
		},
		{
			"name": "bell1",
			"origin": [0, 0, 0],
			"children": [5, 6, 7, 8, 9, 10]
		},
		{
			"name": "bell2",
			"origin": [0, 0, 0],
			"children": [11, 12, 13, 14, 15, 16]
		},
		{
			"name": "ringer",
			"origin": [0, 0, 0],
			"children": [17, 18]
		}
	]
}"""

private const val template_radar_json =
"""{
	"parent": "block/block",
	"textures": {
		"particle": "#0"
	},
	"elements": [
		{
			"from": [3, 0, 1],
			"to": [13, 1, 15],
			"faces": {
				"north": {"uv": [3, 10, 13, 11], "texture": "#1"},
				"east": {"uv": [1, 10, 15, 11], "texture": "#1"},
				"south": {"uv": [3, 10, 13, 11], "texture": "#1"},
				"west": {"uv": [1, 10, 15, 11], "texture": "#1"},
				"up": {"uv": [3, 1, 13, 15], "texture": "#2"},
				"down": {"uv": [3, 1, 13, 15], "texture": "#0", "cullface": "down"}
			}
		},
		{
			"from": [13, 1, 2],
			"to": [14, 2, 14],
			"faces": {
				"north": {"uv": [2, 9, 3, 10], "texture": "#1"},
				"east": {"uv": [2, 9, 14, 10], "texture": "#1"},
				"south": {"uv": [13, 9, 14, 10], "texture": "#1"},
				"west": {"uv": [2, 9, 14, 10], "texture": "#1"},
				"up": {"uv": [13, 2, 14, 14], "texture": "#2"},
				"down": {"uv": [13, 2, 14, 14], "texture": "#0"}
			}
		},
		{
			"from": [2, 1, 2],
			"to": [3, 2, 14],
			"faces": {
				"north": {"uv": [13, 9, 14, 10], "texture": "#1"},
				"east": {"uv": [2, 9, 14, 10], "texture": "#1"},
				"south": {"uv": [2, 9, 3, 10], "texture": "#1"},
				"west": {"uv": [2, 9, 14, 10], "texture": "#1"},
				"up": {"uv": [2, 2, 3, 14], "texture": "#2"},
				"down": {"uv": [2, 2, 3, 14], "texture": "#0"}
			}
		},
		{
			"from": [14, 2, 3],
			"to": [15, 3, 13],
			"faces": {
				"north": {"uv": [1, 8, 2, 9], "texture": "#1"},
				"east": {"uv": [3, 8, 13, 9], "texture": "#1"},
				"south": {"uv": [14, 8, 15, 9], "texture": "#1"},
				"west": {"uv": [3, 8, 13, 9], "texture": "#1"},
				"up": {"uv": [14, 3, 15, 13], "texture": "#2"},
				"down": {"uv": [14, 3, 15, 13], "texture": "#0"}
			}
		},
		{
			"from": [1, 2, 3],
			"to": [2, 3, 13],
			"faces": {
				"north": {"uv": [14, 8, 15, 9], "texture": "#1"},
				"east": {"uv": [3, 8, 13, 9], "texture": "#1"},
				"south": {"uv": [1, 8, 2, 9], "texture": "#1"},
				"west": {"uv": [3, 8, 13, 9], "texture": "#1"},
				"up": {"uv": [1, 3, 2, 13], "texture": "#2"},
				"down": {"uv": [1, 3, 2, 13], "texture": "#0"}
			}
		},
		{
			"from": [0, 1, 6],
			"to": [6, 7, 6],
			"rotation": {"angle": 45, "axis": "y", "origin": [3, 12, 3]},
			"faces": {
				"north": {"uv": [4, 8, 12, 16], "texture": "#3"},
				"south": {"uv": [12, 8, 4, 16], "texture": "#3"}
			}
		},
		{
			"from": [10, 1, 6],
			"to": [16, 7, 6],
			"rotation": {"angle": -45, "axis": "y", "origin": [13, 12, 3]},
			"faces": {
				"north": {"uv": [12, 8, 4, 16], "texture": "#3"},
				"south": {"uv": [4, 8, 12, 16], "texture": "#3"}
			}
		},
		{
			"from": [10, 1, 10],
			"to": [16, 7, 10],
			"rotation": {"angle": 45, "axis": "y", "origin": [13, 12, 13]},
			"faces": {
				"north": {"uv": [12, 8, 4, 16], "texture": "#3"},
				"south": {"uv": [4, 8, 12, 16], "texture": "#3"}
			}
		},
		{
			"from": [0, 1, 10],
			"to": [6, 7, 10],
			"rotation": {"angle": -45, "axis": "y", "origin": [3, 12, 13]},
			"faces": {
				"north": {"uv": [4, 8, 12, 16], "texture": "#3"},
				"south": {"uv": [12, 8, 4, 16], "texture": "#3"}
			}
		},
		{
			"from": [7, 0, 0],
			"to": [9, 16, 16],
			"shade": false,
			"faces": {
				"east": {"uv": [0, 0, 16, 16], "texture": "#4"},
				"west": {"uv": [0, 0, 16, 16], "texture": "#4"}
			}
		},
		{
			"from": [0, 0, 7],
			"to": [16, 16, 9],
			"shade": false,
			"faces": {
				"north": {"uv": [0, 0, 16, 16], "texture": "#4"},
				"south": {"uv": [0, 0, 16, 16], "texture": "#4"}
			}
		},
		{
			"from": [7.5, 1, 7.5],
			"to": [8.5, 13, 8.5],
			"faces": {
				"north": {"uv": [7, 2, 8, 15], "texture": "#0"},
				"east": {"uv": [3, 2, 4, 15], "texture": "#0"},
				"south": {"uv": [8, 2, 9, 15], "texture": "#0"},
				"west": {"uv": [10, 2, 11, 15], "texture": "#0"}
			}
		},
		{
			"from": [7, 13, 7],
			"to": [9, 15, 9],
			"faces": {
				"up": {"uv": [7, 1, 9, 3], "texture": "#4"},
				"down": {"uv": [7, 1, 9, 3], "texture": "#4"}
			}
		}
	]
}"""

private const val template_radiation_detector_json =
"""{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"from": [6, 0, 10],
			"to": [10, 1, 11],
			"faces": {
				"north": {"uv": [5, 10, 9, 11], "texture": "#0"},
				"east": {"uv": [9, 11, 10, 12], "texture": "#0"},
				"south": {"uv": [5, 8, 9, 9], "texture": "#0"},
				"west": {"uv": [9, 9, 10, 10], "texture": "#0"},
				"up": {"uv": [5, 9, 9, 10], "texture": "#0"},
				"down": {"uv": [5, 11, 9, 12], "texture": "#0"}
			}
		},
		{
			"from": [6, 1, 9],
			"to": [7, 2, 10],
			"faces": {
				"north": {"uv": [1, 4, 2, 5], "texture": "#0"},
				"east": {"uv": [3, 4, 4, 5], "texture": "#0"},
				"south": {"uv": [2, 4, 3, 5], "texture": "#0"},
				"west": {"uv": [0, 4, 1, 5], "texture": "#0"},
				"up": {"uv": [4, 4, 5, 5], "texture": "#0"},
				"down": {"uv": [0, 5, 1, 6], "texture": "#0"}
			}
		},
		{
			"from": [6, 2, 8],
			"to": [7, 3, 9],
			"faces": {
				"north": {"uv": [1, 0, 2, 1], "texture": "#0"},
				"east": {"uv": [3, 0, 4, 1], "texture": "#0"},
				"south": {"uv": [2, 0, 3, 1], "texture": "#0"},
				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
				"down": {"uv": [0, 1, 1, 2], "texture": "#0"}
			}
		},
		{
			"from": [9, 1, 9],
			"to": [10, 2, 10],
			"faces": {
				"north": {"uv": [6, 4, 7, 5], "texture": "#0"},
				"east": {"uv": [8, 4, 9, 5], "texture": "#0"},
				"south": {"uv": [5, 4, 6, 5], "texture": "#0"},
				"west": {"uv": [7, 4, 8, 5], "texture": "#0"},
				"up": {"uv": [9, 4, 10, 5], "texture": "#0"},
				"down": {"uv": [5, 5, 6, 6], "texture": "#0"}
			}
		},
		{
			"from": [9, 2, 8],
			"to": [10, 3, 9],
			"faces": {
				"north": {"uv": [5, 0, 6, 1], "texture": "#0"},
				"east": {"uv": [8, 0, 9, 1], "texture": "#0"},
				"south": {"uv": [6, 0, 7, 1], "texture": "#0"},
				"west": {"uv": [7, 0, 8, 1], "texture": "#0"},
				"down": {"uv": [5, 1, 6, 2], "texture": "#0"}
			}
		},
		{
			"from": [6, 3, 5],
			"to": [10, 7, 10],
			"faces": {
				"north": {"uv": [10, 4, 14, 8], "texture": "#0"},
				"east": {"uv": [5, 4, 10, 8], "texture": "#0"},
				"south": {"uv": [10, 0, 14, 4], "texture": "#0"},
				"west": {"uv": [5, 0, 10, 4], "texture": "#0"},
				"up": {"uv": [0, 0, 5, 4], "rotation": 90, "texture": "#0"},
				"down": {"uv": [0, 4, 5, 8], "rotation": 90, "texture": "#0"}
			}
		},
		{
			"from": [7, 3, 10],
			"to": [8, 6, 12],
			"faces": {
				"east": {"uv": [14, 3, 16, 6], "texture": "#0"},
				"south": {"uv": [14, 6, 15, 9], "texture": "#0"},
				"west": {"uv": [14, 0, 16, 3], "texture": "#0"},
				"up": {"uv": [15, 6, 16, 8], "texture": "#0"},
				"down": {"uv": [15, 8, 16, 10], "texture": "#0"}
			}
		},
		{
			"from": [5, 4.5, 10.5],
			"to": [7, 5.5, 11.5],
			"faces": {
				"north": {"uv": [1, 12, 3, 13], "rotation": 180, "texture": "#0"},
				"south": {"uv": [1, 12, 3, 13], "texture": "#0"},
				"west": {"uv": [1, 12, 2, 13], "texture": "#0"},
				"up": {"uv": [1, 12, 3, 13], "texture": "#0"},
				"down": {"uv": [1, 12, 3, 13], "texture": "#0"}
			}
		},
		{
			"from": [5, 4.5, 7.5],
			"to": [6, 5.5, 8.5],
			"faces": {
				"north": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"south": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"west": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"up": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"down": {"uv": [0, 12, 1, 13], "texture": "#0"}
			}
		},
		{
			"from": [8, 3, 10],
			"to": [13, 6, 11],
			"faces": {
				"north": {"uv": [5, 9, 10, 12], "texture": "#0"},
				"east": {"uv": [0, 9, 1, 12], "texture": "#0"},
				"south": {"uv": [0, 13, 5, 16], "texture": "#1"},
				"up": {"uv": [4, 8, 9, 9], "texture": "#0"},
				"down": {"uv": [0, 8, 5, 9], "texture": "#0"}
			}
		},
		{
			"from": [11, 4, 8],
			"to": [12, 5, 10],
			"faces": {
				"north": {"uv": [1, 12, 2, 13], "texture": "#0"},
				"east": {"uv": [0, 12, 2, 13], "texture": "#0"},
				"west": {"uv": [0, 12, 2, 13], "rotation": 180, "texture": "#0"},
				"up": {"uv": [0, 12, 2, 13], "rotation": 270, "texture": "#0"},
				"down": {"uv": [0, 12, 2, 13], "rotation": 90, "texture": "#0"}
			}
		},
		{
			"from": [4, 4.5, 8.5],
			"to": [5, 5.5, 10.5],
			"faces": {
				"north": {"uv": [1, 12, 2, 13], "texture": "#0"},
				"east": {"uv": [0, 12, 2, 13], "texture": "#0"},
				"south": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"west": {"uv": [0, 12, 2, 13], "rotation": 180, "texture": "#0"},
				"up": {"uv": [0, 12, 2, 13], "rotation": 270, "texture": "#0"},
				"down": {"uv": [0, 12, 2, 13], "rotation": 90, "texture": "#0"}
			}
		},
		{
			"from": [10, 4, 7],
			"to": [11, 5, 8],
			"faces": {
				"north": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"east": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"south": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"up": {"uv": [0, 12, 1, 13], "texture": "#0"},
				"down": {"uv": [0, 12, 1, 13], "texture": "#0"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"translation": [0, 7.75, -2.75],
			"scale": [0.98, 0.98, 0.98]
		},
		"thirdperson_lefthand": {
			"translation": [0, 7.75, -2.75],
			"scale": [0.98, 0.98, 0.98]
		},
		"firstperson_righthand": {
			"rotation": [-10, -5, 0],
			"translation": [-2, 8.5, 2],
            "scale": [0.9, 0.9, 0.5]
		},
		"firstperson_lefthand": {
			"rotation": [-10, -5, 0],
			"translation": [0, 8.5, 2],
            "scale": [0.9, 0.9, 0.5]
		},
		"ground": {
			"translation": [0, 2, 0],
			"scale": [0.5, 0.5, 0.5]
		},
		"gui": {
			"rotation": [30, 30, 0],
			"translation": [-1.5, 5, 0],
			"scale": [1.3, 1.3, 1.3]
		},
		"head": {
			"translation": [8.5, 5.25, 5.25]
		},
		"fixed": {
			"rotation": [0, -180, 0],
			"translation": [1, 10, 5.25],
			"scale": [2, 2, 2]
		}
	}
}"""

private const val template_deposit_json =
"""{
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"from": [5, 3, 5],
			"to": [11, 5, 11],
			"rotation": {"angle": 22.5, "axis": "y", "origin": [8, 0, 8]},
			"faces": {
				"north": {"uv": [5, 11, 11, 13], "texture": "#texture"},
				"east": {"uv": [5, 11, 11, 13], "texture": "#texture"},
				"south": {"uv": [5, 11, 11, 13], "texture": "#texture"},
				"west": {"uv": [5, 11, 11, 13], "texture": "#texture"},
				"up": {"uv": [5, 5, 11, 11], "texture": "#texture"}
			}
		},
		{
			"from": [6, 5, 6],
			"to": [10, 7, 10],
			"rotation": {"angle": -22.5, "axis": "y", "origin": [8, 0, 8]},
			"faces": {
				"north": {"uv": [6, 9, 10, 11], "texture": "#texture"},
				"east": {"uv": [6, 9, 10, 11], "texture": "#texture"},
				"south": {"uv": [6, 9, 10, 11], "texture": "#texture"},
				"west": {"uv": [6, 9, 10, 11], "texture": "#texture"},
				"up": {"uv": [6, 6, 10, 10], "texture": "#texture"}
			}
		},
		{
			"from": [4, 0, 4],
			"to": [12, 3, 12],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 0, 8]},
			"faces": {
				"north": {"uv": [4, 13, 12, 16], "texture": "#texture"},
				"east": {"uv": [4, 13, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 13, 12, 16], "texture": "#texture"},
				"west": {"uv": [4, 13, 12, 16], "texture": "#texture"},
				"up": {"uv": [4, 4, 12, 12], "texture": "#texture"},
				"down": {"uv": [4, 4, 12, 12], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [6.5, 7, 8],
			"to": [9.5, 9, 8],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 0, 8]},
			"faces": {
				"north": {"uv": [0, 0, 3, 2], "texture": "#texture"},
				"south": {"uv": [0, 0, 3, 2], "texture": "#texture"}
			}
		},
		{
			"from": [8, 7, 6.5],
			"to": [8, 9, 9.5],
			"rotation": {"angle": 0, "axis": "y", "origin": [8, 0, 8]},
			"faces": {
				"east": {"uv": [0, 0, 3, 2], "texture": "#texture"},
				"west": {"uv": [0, 0, 3, 2], "texture": "#texture"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.75, 2],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.75, 2],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [0, 45, 0],
			"translation": [0, 1.25, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, 225, 0],
			"translation": [0, 1.25, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"translation": [0, 4, 0]
		},
		"head": {
			"translation": [0, 14.5, 0]
		},
		"fixed": {
			"translation": [0, 4, -0.5]
		}
	}
}"""

private const val template_redstone_dust_dot_json =
"""{
    "ambientocclusion": false,
    "elements": [
        {   
            "from": [ 0, 0.25, 0 ],
            "to": [ 16, 0.25, 16 ],
            "shade": false,
            "faces": {
                "up": { "uv": [ 0, 0, 16, 16 ], "texture": "#texture", "tintindex": 0 },
                "down": { "uv": [ 0, 16, 16, 0 ], "texture": "#texture", "tintindex": 0 }
            }
        },
        {   
            "from": [ 0, 0.25, 0 ],
            "to": [ 16, 0.25, 16 ],
            "shade": false,
            "faces": {
                "up": { "uv": [ 0, 0, 16, 16 ], "texture": "#overlay" },
                "down": { "uv": [ 0, 16, 16, 0 ], "texture": "#overlay" }
            }
        }
    ]
}"""

private const val template_redstone_dust_side_json =
"""{
    "ambientocclusion": false,
    "elements": [
        {   
            "from": [ 0, 0.25, 0 ],
            "to": [ 16, 0.25, 8 ],
            "shade": false,
            "faces": {
                "up": { "uv": [ 0, 0, 16, 8 ], "texture": "#texture", "tintindex": 0 },
                "down": { "uv": [ 0, 8, 16, 0 ], "texture": "#texture", "tintindex": 0 }
            }
        },
        {   
            "from": [ 0, 0.25, 0 ],
            "to": [ 16, 0.25, 8 ],
            "shade": false,
            "faces": {
                "up": { "uv": [ 0, 0, 16, 8 ], "texture": "#overlay" },
                "down": { "uv": [ 0, 8, 16, 0 ], "texture": "#overlay" }
            }
        }
    ]
}"""

private const val template_redstone_dust_side_alt_json =
"""{
    "ambientocclusion": false,
    "elements": [
        {   
            "from": [ 0, 0.25, 8 ],
            "to": [ 16, 0.25, 16 ],
            "shade": false,
            "faces": {
                "up": { "uv": [ 0, 8, 16, 16 ], "texture": "#texture", "tintindex": 0 },
                "down": { "uv": [ 0, 16, 16, 8 ], "texture": "#texture", "tintindex": 0 }
            }
        },
        {   
            "from": [ 0, 0.25, 8 ],
            "to": [ 16, 0.25, 16 ],
            "shade": false,
            "faces": {
                "up": { "uv": [ 0, 8, 16, 16 ], "texture": "#overlay" },
                "down": { "uv": [ 0, 16, 16, 8 ], "texture": "#overlay" }
            }
        }
    ]
}"""

private const val template_redstone_dust_up_json =
"""{
    "ambientocclusion": false,
    "elements": [
        {   
            "from": [ 0, 0, 0.25 ],
            "to": [ 16, 16, 0.25 ],
            "shade": false,
            "faces": {
                "south": { "uv": [ 0, 0, 16, 16 ], "texture": "#texture", "tintindex": 0 },
                "north": { "uv": [ 16, 0, 0, 16 ], "texture": "#texture", "tintindex": 0 }
            }
        },
        {   
            "from": [ 0, 0, 0.25 ],
            "to": [ 16, 16, 0.25 ],
            "shade": false,
            "faces": {
                "south": { "uv": [ 0, 0, 16, 16 ], "texture": "#overlay" },
                "north": { "uv": [ 16, 0, 0, 16 ], "texture": "#overlay" }
            }
        }
    ]
}"""

private const val template_iron_bars_post_json =
"""{
    "ambientocclusion": false,
    "textures": {
        "particle": "#bars"
    },
    "elements": [
        {   
            "from": [ 8, 0, 7 ],
            "to": [ 8, 16, 9 ],
            "faces": {
                "west": { "uv": [ 7, 0, 9, 16 ], "texture": "#bars" },
                "east": { "uv": [ 9, 0, 7, 16 ], "texture": "#bars" }
            }
        },
        {   
            "from": [ 7, 0, 8 ],
            "to": [ 9, 16, 8 ],
            "faces": {
                "north": { "uv": [ 7, 0, 9, 16 ], "texture": "#bars" },
                "south": { "uv": [ 9, 0, 7, 16 ], "texture": "#bars" }
            }
        }
    ]
}"""

private const val template_iron_bars_post_ends_json =
"""{
    "ambientocclusion": false,
    "textures": {
        "particle": "#edge"
    },
    "elements": [
        {   
            "from": [ 7, 0.001, 7 ],
            "to": [ 9, 0.001, 9 ],
            "faces": {
                "down":  { "uv": [  7, 7,  9,  9 ], "texture": "#edge" },
                "up":    { "uv": [  7, 7,  9,  9 ], "texture": "#edge" }
            }
        },
        {   
            "from": [ 7, 15.999, 7 ],
            "to": [ 9, 15.999, 9 ],
            "faces": {
                "down":  { "uv": [  7, 7,  9,  9 ], "texture": "#edge" },
                "up":    { "uv": [  7, 7,  9,  9 ], "texture": "#edge" }
            }
        }
    ]
}"""

private const val template_iron_bars_cap_json =
"""{
    "ambientocclusion": false,
    "textures": {
        "particle": "#bars"
    },
    "elements": [
        {   
            "from": [ 8, 0, 8 ],
            "to": [ 8, 16, 9 ],
            "faces": {
                "west": { "uv": [ 8, 0, 7, 16 ], "texture": "#bars" },
                "east": { "uv": [ 7, 0, 8, 16 ], "texture": "#bars" }
            }
        },
        {   
            "from": [ 7, 0, 9 ],
            "to": [ 9, 16, 9 ],
            "faces": {
                "north": { "uv": [ 9, 0, 7, 16 ], "texture": "#bars" },
                "south": { "uv": [ 7, 0, 9, 16 ], "texture": "#bars" }
            }
        }
    ]
}"""

private const val template_iron_bars_cap_alt_json =
"""{
    "ambientocclusion": false,
    "textures": {
        "particle": "#bars"
    },
    "elements": [
        {   
            "from": [ 8, 0, 7 ],
            "to": [ 8, 16, 8 ],
            "faces": {
                "west": { "uv": [ 8, 0, 9, 16 ], "texture": "#bars" },
                "east": { "uv": [ 9, 0, 8, 16 ], "texture": "#bars" }
            }
        },
        {   
            "from": [ 7, 0, 7 ],
            "to": [ 9, 16, 7 ],
            "faces": {
                "north": { "uv": [ 7, 0, 9, 16 ], "texture": "#bars" },
                "south": { "uv": [ 9, 0, 7, 16 ], "texture": "#bars" }
            }
        }
    ]
}"""

private const val template_iron_bars_side_json =
"""{
    "ambientocclusion": false,
    "textures": {
        "particle": "#bars"
    },
    "elements": [
        {   
            "from": [ 8, 0, 0 ],
            "to": [ 8, 16, 8 ],
            "faces": {
                "west": { "uv": [ 16, 0,  8, 16 ], "texture": "#bars" },
                "east": { "uv": [  8, 0, 16, 16 ], "texture": "#bars" }
            }
        },
        {   
            "from": [ 7, 0, 0 ],
            "to": [ 9, 16, 7 ],
            "faces": {
                "north": { "uv": [ 7, 0, 9, 16 ], "texture": "#edge", "cullface": "north" }
            }
        },
        {   
            "from": [ 7, 0.001, 0 ],
            "to": [ 9, 0.001, 7 ],
            "faces": {
                "down":  { "uv": [ 9, 0, 7, 7 ], "texture": "#edge" },
                "up":    { "uv": [ 7, 0, 9, 7 ], "texture": "#edge" }
            }
        },
        {   
            "from": [ 7, 15.999, 0 ],
            "to": [ 9, 15.999, 7 ],
            "faces": {
                "down":  { "uv": [ 9, 0, 7, 7 ], "texture": "#edge" },
                "up":    { "uv": [ 7, 0, 9, 7 ], "texture": "#edge" }
            }
        }
    ]
}"""

private const val template_iron_bars_side_alt_json =
"""{
    "ambientocclusion": false,
    "textures": {
        "particle": "#bars"
    },
    "elements": [
        {   
            "from": [ 8, 0, 8 ],
            "to": [ 8, 16, 16 ],
            "faces": {
                "west": { "uv": [ 8, 0, 0, 16 ], "texture": "#bars" },
                "east": { "uv": [ 0, 0, 8, 16 ], "texture": "#bars" }
            }
        },
        {   
            "from": [ 7, 0, 9 ],
            "to": [ 9, 16, 16 ],
            "faces": {
                "south": { "uv": [ 7, 0, 9, 16 ], "texture": "#edge", "cullface": "south" },
                "down":  { "uv": [ 9, 9, 7, 16 ], "texture": "#edge" },
                "up":    { "uv": [ 7, 9, 9, 16 ], "texture": "#edge" }
            }
        },
        {   
            "from": [ 7, 0.001, 9 ],
            "to": [ 9, 0.001, 16 ],
            "faces": {
                "down":  { "uv": [ 9, 9, 7, 16 ], "texture": "#edge" },
                "up":    { "uv": [ 7, 9, 9, 16 ], "texture": "#edge" }
            }
        },
        {   
            "from": [ 7, 15.999, 9 ],
            "to": [ 9, 15.999, 16 ],
            "faces": {
                "down":  { "uv": [ 9, 9, 7, 16 ], "texture": "#edge" },
                "up":    { "uv": [ 7, 9, 9, 16 ], "texture": "#edge" }
            }
        }
    ]
}"""

private const val template_spikes_json =
"""{
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "#texture"
	},
	"elements": [
		{
			"from": [13, 0, 1],
			"to": [15, 4, 3],
			"faces": {
				"north": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"east": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"south": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"west": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [13, 4, 1.5],
			"to": [15, 8, 2.5],
			"faces": {
				"north": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"east": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"south": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"west": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 4, 1],
			"to": [14.5, 8, 3],
			"faces": {
				"north": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"east": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"south": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"west": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 8, 1.5],
			"to": [14.5, 12, 2.5],
			"faces": {
				"north": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"east": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"south": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"west": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 5],
			"to": [15, 4, 7],
			"faces": {
				"north": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"south": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"west": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 180, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 180, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [13, 4, 5.5],
			"to": [15, 8, 6.5],
			"faces": {
				"north": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"east": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"south": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"west": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 4, 5],
			"to": [14.5, 8, 7],
			"faces": {
				"north": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"east": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"south": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"west": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 8, 5.5],
			"to": [14.5, 12, 6.5],
			"faces": {
				"north": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"east": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"south": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"west": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 5],
			"to": [7, 4, 7],
			"faces": {
				"north": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"east": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"south": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"west": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 270, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [5.5, 4, 5],
			"to": [6.5, 8, 7],
			"faces": {
				"north": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"east": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"south": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"west": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [5, 4, 5.5],
			"to": [7, 8, 6.5],
			"faces": {
				"north": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"east": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"south": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"west": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [5.5, 8, 5.5],
			"to": [6.5, 12, 6.5],
			"faces": {
				"north": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"east": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"south": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"west": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [5.5, 8, 1.5],
			"to": [6.5, 12, 2.5],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"east": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"south": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"west": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 1],
			"to": [7, 4, 3],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"east": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"south": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"west": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 270, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 90, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [5.5, 4, 1],
			"to": [6.5, 8, 3],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"east": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"south": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"west": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [5, 4, 1.5],
			"to": [7, 8, 2.5],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"east": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"south": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"west": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 5],
			"to": [11, 4, 7],
			"faces": {
				"north": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"east": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"south": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"west": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [9, 4, 5.5],
			"to": [11, 8, 6.5],
			"faces": {
				"north": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"east": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"south": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"west": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "texture": "#texture"}
			}
		},
		{
			"from": [9.5, 4, 5],
			"to": [10.5, 8, 7],
			"faces": {
				"north": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"east": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"south": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"west": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "texture": "#texture"}
			}
		},
		{
			"from": [9.5, 8, 5.5],
			"to": [10.5, 12, 6.5],
			"faces": {
				"north": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"east": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"south": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"west": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 5],
			"to": [3, 4, 7],
			"faces": {
				"north": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"east": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"south": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"west": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 270, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 90, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [1.5, 4, 5],
			"to": [2.5, 8, 7],
			"faces": {
				"north": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"east": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"south": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"west": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [1, 4, 5.5],
			"to": [3, 8, 6.5],
			"faces": {
				"north": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"east": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"south": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"west": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 8, 5.5],
			"to": [2.5, 12, 6.5],
			"faces": {
				"north": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"east": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"south": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"west": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 1],
			"to": [11, 4, 3],
			"faces": {
				"north": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"east": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"south": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"west": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 270, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [9.5, 4, 1],
			"to": [10.5, 8, 3],
			"faces": {
				"north": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"east": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"south": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"west": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [9, 4, 1.5],
			"to": [11, 8, 2.5],
			"faces": {
				"north": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"east": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"south": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"west": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [9.5, 8, 1.5],
			"to": [10.5, 12, 2.5],
			"faces": {
				"north": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"east": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"south": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"west": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 1],
			"to": [3, 4, 3],
			"faces": {
				"north": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"east": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"south": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"west": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 180, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 180, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [1, 4, 1.5],
			"to": [3, 8, 2.5],
			"faces": {
				"north": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"east": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"south": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"west": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 4, 1],
			"to": [2.5, 8, 3],
			"faces": {
				"north": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"east": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"south": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"west": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 8, 1.5],
			"to": [2.5, 12, 2.5],
			"faces": {
				"north": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"east": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"south": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"west": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 13],
			"to": [3, 4, 15],
			"faces": {
				"north": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"east": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"south": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"west": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 180, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 180, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [1, 4, 13.5],
			"to": [3, 8, 14.5],
			"faces": {
				"north": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"east": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"south": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"west": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 4, 13],
			"to": [2.5, 8, 15],
			"faces": {
				"north": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"east": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"south": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"west": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 8, 13.5],
			"to": [2.5, 12, 14.5],
			"faces": {
				"north": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"east": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"south": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"west": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 9],
			"to": [3, 4, 11],
			"faces": {
				"north": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"east": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"south": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"west": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [1, 4, 9.5],
			"to": [3, 8, 10.5],
			"faces": {
				"north": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"east": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"south": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"west": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 4, 9],
			"to": [2.5, 8, 11],
			"faces": {
				"north": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"east": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"south": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"west": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "texture": "#texture"}
			}
		},
		{
			"from": [1.5, 8, 9.5],
			"to": [2.5, 12, 10.5],
			"faces": {
				"north": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"east": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"south": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"west": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 9],
			"to": [11, 4, 11],
			"faces": {
				"north": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"east": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"south": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"west": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 270, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 90, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [9.5, 4, 9],
			"to": [10.5, 8, 11],
			"faces": {
				"north": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"east": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"south": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"west": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [9, 4, 9.5],
			"to": [11, 8, 10.5],
			"faces": {
				"north": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"east": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"south": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"west": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [9.5, 8, 9.5],
			"to": [10.5, 12, 10.5],
			"faces": {
				"north": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"east": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"south": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"west": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [9.5, 8, 13.5],
			"to": [10.5, 12, 14.5],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [1, 0, 2, 4], "texture": "#texture"},
				"east": {"uv": [0, 0, 1, 4], "texture": "#texture"},
				"south": {"uv": [3, 0, 4, 4], "texture": "#texture"},
				"west": {"uv": [2, 0, 3, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [9, 0, 13],
			"to": [11, 4, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [2, 12, 4, 16], "texture": "#texture"},
				"east": {"uv": [0, 12, 2, 16], "texture": "#texture"},
				"south": {"uv": [6, 12, 8, 16], "texture": "#texture"},
				"west": {"uv": [4, 12, 6, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 270, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [9.5, 4, 13],
			"to": [10.5, 8, 15],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [2, 4, 3, 8], "texture": "#texture"},
				"east": {"uv": [0, 4, 2, 8], "texture": "#texture"},
				"south": {"uv": [5, 4, 6, 8], "texture": "#texture"},
				"west": {"uv": [3, 4, 5, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [9, 4, 13.5],
			"to": [11, 8, 14.5],
			"rotation": {"angle": 0, "axis": "y", "origin": [10, 0, 2]},
			"faces": {
				"north": {"uv": [1, 8, 3, 12], "texture": "#texture"},
				"east": {"uv": [0, 8, 1, 12], "texture": "#texture"},
				"south": {"uv": [4, 8, 6, 12], "texture": "#texture"},
				"west": {"uv": [3, 8, 4, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 9],
			"to": [7, 4, 11],
			"faces": {
				"north": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"east": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"south": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"west": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 180, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 180, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [5, 4, 9.5],
			"to": [7, 8, 10.5],
			"faces": {
				"north": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"east": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"south": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"west": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [5.5, 4, 9],
			"to": [6.5, 8, 11],
			"faces": {
				"north": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"east": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"south": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"west": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [5.5, 8, 9.5],
			"to": [6.5, 12, 10.5],
			"faces": {
				"north": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"east": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"south": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"west": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "rotation": 180, "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 9],
			"to": [15, 4, 11],
			"faces": {
				"north": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"east": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"south": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"west": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 90, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 270, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [13.5, 4, 9],
			"to": [14.5, 8, 11],
			"faces": {
				"north": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"east": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"south": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"west": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [13, 4, 9.5],
			"to": [15, 8, 10.5],
			"faces": {
				"north": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"east": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"south": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"west": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 8, 9.5],
			"to": [14.5, 12, 10.5],
			"faces": {
				"north": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"east": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"south": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"west": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"up": {"uv": [4, 0, 5, 1], "rotation": 90, "texture": "#texture"}
			}
		},
		{
			"from": [5, 0, 13],
			"to": [7, 4, 15],
			"faces": {
				"north": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"east": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"south": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"west": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "rotation": 270, "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "rotation": 90, "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [5.5, 4, 13],
			"to": [6.5, 8, 15],
			"faces": {
				"north": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"east": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"south": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"west": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [5, 4, 13.5],
			"to": [7, 8, 14.5],
			"faces": {
				"north": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"east": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"south": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"west": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [5.5, 8, 13.5],
			"to": [6.5, 12, 14.5],
			"faces": {
				"north": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"east": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"south": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"west": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "rotation": 270, "texture": "#texture"}
			}
		},
		{
			"from": [13, 0, 13],
			"to": [15, 4, 15],
			"faces": {
				"north": {"uv": [8, 12, 10, 16], "texture": "#texture"},
				"east": {"uv": [14, 12, 16, 16], "texture": "#texture"},
				"south": {"uv": [12, 12, 14, 16], "texture": "#texture"},
				"west": {"uv": [10, 12, 12, 16], "texture": "#texture"},
				"up": {"uv": [13, 1, 15, 3], "texture": "#texture"},
				"down": {"uv": [13, 13, 15, 15], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [13, 4, 13.5],
			"to": [15, 8, 14.5],
			"faces": {
				"north": {"uv": [8, 4, 10, 8], "texture": "#texture"},
				"east": {"uv": [13, 4, 14, 8], "texture": "#texture"},
				"south": {"uv": [11, 4, 13, 8], "texture": "#texture"},
				"west": {"uv": [10, 4, 11, 8], "texture": "#texture"},
				"up": {"uv": [6, 6, 8, 7], "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 4, 13],
			"to": [14.5, 8, 15],
			"faces": {
				"north": {"uv": [8, 8, 9, 12], "texture": "#texture"},
				"east": {"uv": [12, 8, 14, 12], "texture": "#texture"},
				"south": {"uv": [11, 8, 12, 12], "texture": "#texture"},
				"west": {"uv": [9, 8, 11, 12], "texture": "#texture"},
				"up": {"uv": [6, 4, 7, 6], "texture": "#texture"}
			}
		},
		{
			"from": [13.5, 8, 13.5],
			"to": [14.5, 12, 14.5],
			"faces": {
				"north": {"uv": [8, 0, 9, 4], "texture": "#texture"},
				"east": {"uv": [11, 0, 12, 4], "texture": "#texture"},
				"south": {"uv": [10, 0, 11, 4], "texture": "#texture"},
				"west": {"uv": [9, 0, 10, 4], "texture": "#texture"},
				"up": {"uv": [12, 0, 13, 1], "texture": "#texture"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.25, 1.5],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.25, 1.5],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [0, 75, 0],
			"translation": [0, 2, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, -105, 0],
			"translation": [0, 2, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"scale": [0.625, 0.625, 0.625]
		},
		"head": {
			"translation": [0, 11, 0]
		},
		"fixed": {
			"scale": [0.5, 0.5, 0.5]
		}
	}
}"""

private const val template_sapphire_altar_json =
"""{
	"credit": "Made with Blockbench",
	"textures": {
		"particle": "lcc:block/sapphire_altar",
		"side": "lcc:block/sapphire_altar",
		"front": "lcc:block/sapphire_altar_front",
		"bottom": "lcc:block/sapphire_altar_gems",
		"inside": "lcc:block/sapphire_altar_top"
	},
	"elements": [
		{
			"from": [1, 1, 1],
			"to": [15, 8, 15],
			"faces": {
				"north": {"uv": [1, 8, 15, 15], "texture": "#front"},
				"east": {"uv": [1, 8, 15, 15], "texture": "#side"},
				"south": {"uv": [1, 8, 15, 15], "texture": "#side"},
				"west": {"uv": [1, 8, 15, 15], "texture": "#side"},
				"up": {"uv": [1, 1, 15, 15], "rotation": 180, "texture": "#inside"}
			}
		},
		{
			"from": [2, 0, 1],
			"to": [14, 1, 15],
			"faces": {
				"north": {"uv": [2, 15, 14, 16], "texture": "#front"},
				"south": {"uv": [2, 15, 14, 16], "texture": "#side"},
				"down": {"uv": [2, 1, 14, 15], "texture": "#side", "cullface": "down"}
			}
		},
		{
			"from": [1, 0, 2],
			"to": [2, 1, 14],
			"faces": {
				"west": {"uv": [2, 15, 14, 16], "texture": "#side"},
				"down": {"uv": [1, 2, 2, 14], "texture": "#side", "cullface": "down"}
			}
		},
		{
			"from": [14, 0, 2],
			"to": [15, 1, 14],
			"faces": {
				"east": {"uv": [2, 15, 14, 16], "texture": "#side"},
				"down": {"uv": [14, 2, 15, 14], "texture": "#side", "cullface": "down"}
			}
		},
		{
			"from": [1, 8, 1],
			"to": [15, 10, 2],
			"faces": {
				"north": {"uv": [1, 6, 15, 8], "texture": "#front"},
				"east": {"uv": [14, 6, 15, 8], "texture": "#side"},
				"south": {"uv": [1, 6, 15, 8], "texture": "#inside"},
				"west": {"uv": [1, 6, 2, 8], "texture": "#side"},
				"up": {"uv": [1, 1, 15, 2], "texture": "#side"}
			}
		},
		{
			"from": [13, 8, 2],
			"to": [15, 10, 5],
			"faces": {
				"east": {"uv": [11, 6, 14, 8], "texture": "#side"},
				"west": {"uv": [2, 6, 5, 8], "texture": "#inside"},
				"up": {"uv": [13, 2, 15, 5], "texture": "#side"}
			}
		},
		{
			"from": [1, 8, 2],
			"to": [3, 10, 5],
			"faces": {
				"east": {"uv": [11, 6, 14, 8], "texture": "#inside"},
				"west": {"uv": [2, 6, 5, 8], "texture": "#side"},
				"up": {"uv": [1, 2, 3, 5], "texture": "#side"}
			}
		},
		{
			"from": [11, 8, 4],
			"to": [13, 10, 5],
			"faces": {
				"north": {"uv": [3, 6, 5, 8], "texture": "#inside"},
				"up": {"uv": [11, 4, 13, 5], "texture": "#side"}
			}
		},
		{
			"from": [3, 8, 4],
			"to": [5, 10, 5],
			"faces": {
				"north": {"uv": [11, 6, 13, 8], "texture": "#inside"},
				"up": {"uv": [3, 4, 5, 5], "texture": "#side"}
			}
		},
		{
			"from": [5, 8, 2],
			"to": [11, 10, 5],
			"faces": {
				"east": {"uv": [11, 6, 14, 8], "texture": "#inside"},
				"west": {"uv": [2, 6, 5, 8], "texture": "#inside"},
				"up": {"uv": [5, 2, 11, 5], "texture": "#side"}
			}
		},
		{
			"from": [1, 9, 5],
			"to": [15, 11, 6],
			"faces": {
				"north": {"uv": [1, 5, 15, 7], "texture": "#side"},
				"east": {"uv": [10, 5, 11, 7], "texture": "#side"},
				"south": {"uv": [1, 5, 15, 7], "texture": "#inside"},
				"west": {"uv": [5, 5, 6, 7], "texture": "#side"},
				"up": {"uv": [1, 5, 15, 6], "texture": "#side"}
			}
		},
		{
			"from": [9, 9, 6],
			"to": [15, 11, 9],
			"faces": {
				"east": {"uv": [7, 5, 10, 7], "texture": "#side"},
				"west": {"uv": [6, 5, 9, 7], "texture": "#inside"},
				"up": {"uv": [9, 6, 15, 9], "texture": "#side"}
			}
		},
		{
			"from": [1, 9, 6],
			"to": [7, 11, 9],
			"faces": {
				"east": {"uv": [7, 5, 10, 7], "texture": "#inside"},
				"west": {"uv": [6, 5, 9, 7], "texture": "#side"},
				"up": {"uv": [1, 6, 7, 9], "texture": "#side"}
			}
		},
		{
			"from": [7, 9, 8],
			"to": [9, 11, 9],
			"faces": {
				"north": {"uv": [7, 5, 9, 7], "texture": "#inside"},
				"up": {"uv": [7, 8, 9, 9], "texture": "#side"}
			}
		},
		{
			"from": [1, 8, 5],
			"to": [15, 9, 9],
			"faces": {
				"east": {"uv": [7, 7, 11, 8], "texture": "#side"},
				"west": {"uv": [5, 7, 9, 8], "texture": "#side"},
				"up": {"uv": [1, 7, 15, 11], "texture": "#inside"}
			}
		},
		{
			"from": [1, 10, 9],
			"to": [15, 12, 10],
			"faces": {
				"north": {"uv": [1, 4, 15, 6], "texture": "#side"},
				"east": {"uv": [6, 4, 7, 6], "texture": "#side"},
				"south": {"uv": [1, 4, 15, 6], "texture": "#inside"},
				"west": {"uv": [9, 4, 10, 6], "texture": "#side"},
				"up": {"uv": [1, 9, 15, 10], "texture": "#side"}
			}
		},
		{
			"from": [13, 10, 10],
			"to": [15, 12, 15],
			"faces": {
				"east": {"uv": [1, 4, 6, 6], "texture": "#side"},
				"south": {"uv": [13, 4, 15, 6], "texture": "#side"},
				"west": {"uv": [10, 4, 15, 6], "texture": "#inside"},
				"up": {"uv": [13, 10, 15, 15], "texture": "#side"}
			}
		},
		{
			"from": [1, 10, 10],
			"to": [3, 12, 15],
			"faces": {
				"east": {"uv": [1, 4, 6, 6], "texture": "#inside"},
				"south": {"uv": [1, 4, 3, 6], "texture": "#side"},
				"west": {"uv": [10, 4, 15, 6], "texture": "#side"},
				"up": {"uv": [1, 10, 3, 15], "texture": "#side"}
			}
		},
		{
			"from": [11, 10, 12],
			"to": [13, 12, 15],
			"faces": {
				"north": {"uv": [3, 4, 5, 6], "texture": "#inside"},
				"south": {"uv": [11, 4, 13, 6], "texture": "#side"},
				"up": {"uv": [11, 12, 13, 15], "texture": "#side"}
			}
		},
		{
			"from": [3, 10, 12],
			"to": [5, 12, 15],
			"faces": {
				"north": {"uv": [11, 4, 13, 6], "texture": "#inside"},
				"south": {"uv": [3, 4, 5, 6], "texture": "#side"},
				"up": {"uv": [3, 12, 5, 15], "texture": "#side"}
			}
		},
		{
			"from": [5, 10, 10],
			"to": [11, 12, 13],
			"faces": {
				"east": {"uv": [3, 4, 6, 6], "texture": "#inside"},
				"south": {"uv": [5, 4, 11, 6], "texture": "#inside"},
				"west": {"uv": [10, 4, 13, 6], "texture": "#inside"},
				"up": {"uv": [5, 10, 11, 13], "texture": "#side"}
			}
		},
		{
			"from": [7.5, 10, 14],
			"to": [8.5, 12, 15],
			"faces": {
				"north": {"uv": [7.5, 4, 8.5, 6], "texture": "#inside"},
				"south": {"uv": [7.5, 4, 8.5, 6], "texture": "#side"},
				"up": {"uv": [7.5, 14, 8.5, 15], "texture": "#side"}
			}
		},
		{
			"from": [8.5, 10, 13],
			"to": [11, 12, 15],
			"faces": {
				"south": {"uv": [8.5, 4, 11, 6], "texture": "#side"},
				"west": {"uv": [13, 4, 15, 6], "texture": "#inside"},
				"up": {"uv": [8.5, 13, 11, 15], "texture": "#side"}
			}
		},
		{
			"from": [5, 10, 13],
			"to": [7.5, 12, 15],
			"faces": {
				"east": {"uv": [1, 4, 3, 6], "texture": "#inside"},
				"south": {"uv": [5, 4, 7.5, 6], "texture": "#side"},
				"up": {"uv": [5, 13, 7.5, 15], "texture": "#side"}
			}
		},
		{
			"from": [1, 8, 9],
			"to": [15, 10, 15],
			"faces": {
				"east": {"uv": [1, 6, 7, 8], "texture": "#side"},
				"south": {"uv": [1, 6, 15, 8], "texture": "#side"},
				"west": {"uv": [9, 6, 15, 8], "texture": "#side"},
				"up": {"uv": [1, 1, 15, 7], "rotation": 180, "texture": "#inside"}
			}
		},
		{
			"from": [0, 0, 0],
			"to": [2, 6, 2],
			"faces": {
				"north": {"uv": [14, 10, 16, 16], "texture": "#bottom", "cullface": "north"},
				"east": {"uv": [14, 10, 16, 16], "texture": "#bottom"},
				"south": {"uv": [0, 10, 2, 16], "texture": "#bottom"},
				"west": {"uv": [0, 10, 2, 16], "texture": "#bottom", "cullface": "west"},
				"up": {"uv": [0, 0, 2, 2], "texture": "#bottom"},
				"down": {"uv": [12, 14, 14, 16], "texture": "#bottom", "cullface": "down"}
			}
		},
		{
			"from": [14, 0, 0],
			"to": [16, 6, 2],
			"faces": {
				"north": {"uv": [0, 10, 2, 16], "texture": "#bottom", "cullface": "north"},
				"east": {"uv": [14, 10, 16, 16], "texture": "#bottom", "cullface": "east"},
				"south": {"uv": [14, 10, 16, 16], "texture": "#bottom"},
				"west": {"uv": [0, 10, 2, 16], "texture": "#bottom"},
				"up": {"uv": [14, 0, 16, 2], "texture": "#bottom"},
				"down": {"uv": [2, 14, 4, 16], "texture": "#bottom", "cullface": "down"}
			}
		},
		{
			"from": [14, 0, 14],
			"to": [16, 8, 16],
			"faces": {
				"north": {"uv": [0, 8, 2, 16], "texture": "#bottom"},
				"east": {"uv": [0, 8, 2, 16], "texture": "#bottom", "cullface": "east"},
				"south": {"uv": [14, 8, 16, 16], "texture": "#bottom", "cullface": "south"},
				"west": {"uv": [14, 8, 16, 16], "texture": "#bottom"},
				"up": {"uv": [10, 8, 12, 10], "texture": "#bottom"},
				"down": {"uv": [14, 6, 16, 8], "texture": "#bottom", "cullface": "down"}
			}
		},
		{
			"from": [0, 0, 14],
			"to": [2, 8, 16],
			"faces": {
				"north": {"uv": [14, 8, 16, 16], "texture": "#bottom"},
				"east": {"uv": [0, 8, 2, 16], "texture": "#bottom"},
				"south": {"uv": [0, 8, 2, 16], "texture": "#bottom", "cullface": "south"},
				"west": {"uv": [14, 8, 16, 16], "texture": "#bottom", "cullface": "west"},
				"up": {"uv": [4, 8, 6, 10], "texture": "#bottom"},
				"down": {"uv": [0, 6, 2, 8], "texture": "#bottom", "cullface": "down"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.5, 0],
			"scale": [0.375, 0.375, 0.375]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2.5, 0],
			"scale": [0.375, 0.375, 0.375]
		},
		"firstperson_righthand": {
			"rotation": [0, 45, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, 225, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"scale": [0.625, 0.625, 0.625]
		},
		"fixed": {
			"scale": [0.5, 0.5, 0.5]
		}
	}
}"""

private const val template_sapphire_altar_tr_json = """{
	"elements": [
		{
			"from": [3, 12, 10],
			"to": [5, 16, 12],
			"faces": {
				"north": {"uv": [0, 2, 2, 6], "texture": "#texture"},
				"east": {"uv": [6, 2, 8, 6], "texture": "#texture"},
				"south": {"uv": [2, 2, 4, 6], "texture": "#texture"},
				"west": {"uv": [4, 2, 6, 6], "texture": "#texture"},
				"up": {"uv": [0, 8, 2, 10], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_sapphire_altar_tl_json = """{
	"elements": [
		{
			"from": [11, 12, 10],
			"to": [13, 16, 12],
			"faces": {
				"north": {"uv": [8, 2, 10, 6], "texture": "#texture"},
				"east": {"uv": [14, 2, 16, 6], "texture": "#texture"},
				"south": {"uv": [10, 2, 12, 6], "texture": "#texture"},
				"west": {"uv": [12, 2, 14, 6], "texture": "#texture"},
				"up": {"uv": [12, 8, 14, 10], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_sapphire_altar_middle_json = """{
	"elements": [
		{
			"from": [7, 11, 6],
			"to": [9, 15, 8],
			"faces": {
				"north": {"uv": [2, 2, 4, 6], "texture": "#texture"},
				"east": {"uv": [11, 2, 13, 6], "texture": "#texture"},
				"south": {"uv": [5, 2, 7, 6], "texture": "#texture"},
				"west": {"uv": [8, 2, 10, 6], "texture": "#texture"},
				"up": {"uv": [14, 8, 16, 10], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_sapphire_altar_br_json = """{
	"elements": [
		{
			"from": [3, 10, 2],
			"to": [5, 14, 4],
			"faces": {
				"north": {"uv": [14, 2, 16, 6], "texture": "#texture"},
				"east": {"uv": [8, 2, 10, 6], "texture": "#texture"},
				"south": {"uv": [12, 2, 14, 6], "texture": "#texture"},
				"west": {"uv": [10, 2, 12, 6], "texture": "#texture"},
				"up": {"uv": [7, 8, 9, 10], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_sapphire_altar_bl_json = """{
	"elements": [
		{
			"from": [11, 10, 2],
			"to": [13, 14, 4],
			"faces": {
				"north": {"uv": [6, 2, 8, 6], "texture": "#texture"},
				"east": {"uv": [0, 2, 2, 6], "texture": "#texture"},
				"south": {"uv": [4, 2, 6, 6], "texture": "#texture"},
				"west": {"uv": [2, 2, 4, 6], "texture": "#texture"},
				"up": {"uv": [9, 0, 11, 2], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_sapphire_altar_key_json = """{
	"elements": [
		{
			"from": [7.5, 12, 13],
			"to": [8.5, 13, 14],
			"faces": {
				"north": {"uv": [9, 14, 10, 15], "texture": "#texture"},
				"east": {"uv": [12, 14, 13, 15], "texture": "#texture"},
				"south": {"uv": [10, 14, 11, 15], "texture": "#texture"},
				"west": {"uv": [11, 14, 12, 15], "texture": "#texture"}
			}
		},
		{
			"from": [7, 13, 13],
			"to": [9, 15, 14],
			"faces": {
				"north": {"uv": [2, 1, 4, 3], "texture": "#texture"},
				"east": {"uv": [5, 1, 6, 3], "texture": "#texture"},
				"south": {"uv": [9, 1, 11, 3], "texture": "#texture"},
				"west": {"uv": [13, 1, 14, 3], "texture": "#texture"},
				"up": {"uv": [5, 13, 7, 14], "texture": "#texture"}
			}
		}
	]
}"""

private const val cube_column_with_particle_json = """{
    "parent": "block/cube",
    "textures": {
        "down": "#end",
        "up": "#end",
        "north": "#side",
        "east": "#side",
        "south": "#side",
        "west": "#side"
    }
}"""

private const val cube_column_horizontal_with_particle_json = """{
    "parent": "block/block",
    "elements": [
        {   "from": [ 0, 0, 0 ],
            "to": [ 16, 16, 16 ],
            "faces": {
                "down":  { "texture": "#down", "cullface": "down" },
                "up":    { "texture": "#up", "rotation": 180, "cullface": "up" },
                "north": { "texture": "#north", "cullface": "north" },
                "south": { "texture": "#south", "cullface": "south" },
                "west":  { "texture": "#west", "cullface": "west" },
                "east":  { "texture": "#east", "cullface": "east" }
            }
        }
    ],
    "textures": {
        "down": "#end",
        "up": "#end",
        "north": "#side",
        "east": "#side",
        "south": "#side",
        "west": "#side"
    }
}"""

private const val template_obelisk_bottom_json = """{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"from": [1, 2, 1],
			"to": [15, 3, 15],
			"faces": {
				"north": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"east": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"south": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"west": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"up": {"uv": [1, 1, 15, 15], "texture": "#texture"},
				"down": {"uv": [1, 1, 15, 15], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 1],
			"to": [15, 1, 15],
			"faces": {
				"north": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [1, 1, 15, 15], "texture": "#texture"},
				"down": {"uv": [1, 1, 15, 15], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [2, 1, 2],
			"to": [14, 2, 14],
			"faces": {
				"north": {"uv": [2, 14, 14, 15], "texture": "#inside"},
				"east": {"uv": [2, 14, 14, 15], "texture": "#inside"},
				"south": {"uv": [2, 14, 14, 15], "texture": "#inside"},
				"west": {"uv": [2, 14, 14, 15], "texture": "#inside"}
			}
		},
		{
			"from": [3, 3, 3],
			"to": [13, 4, 13],
			"faces": {
				"north": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"east": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"south": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"west": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"up": {"uv": [3, 3, 13, 13], "texture": "#texture"}
			}
		},
		{
			"from": [4, 4, 4],
			"to": [12, 16, 12],
			"faces": {
				"north": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"east": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"south": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"west": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"up": {"uv": [4, 4, 12, 12], "texture": "#texture", "cullface": "up"}
			}
		}
	]
}"""

private const val template_obelisk_top_json = """{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"from": [4, 0, 4],
			"to": [12, 4, 12],
			"faces": {
				"north": {"uv": [4, 12, 12, 16], "texture": "#texture"},
				"east": {"uv": [4, 12, 12, 16], "texture": "#texture"},
				"south": {"uv": [4, 12, 12, 16], "texture": "#texture"},
				"west": {"uv": [4, 12, 12, 16], "texture": "#texture"},
				"down": {"uv": [4, 4, 12, 12], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [5, 8, 5],
			"to": [11, 12, 11],
			"faces": {
				"north": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"east": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"south": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"west": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"up": {"uv": [5, 5, 11, 11], "texture": "#lantern"},
				"down": {"uv": [5, 5, 11, 11], "texture": "#lantern"}
			}
		},
		{
			"from": [6, 7, 6],
			"to": [10, 13, 10],
			"faces": {
				"north": {"uv": [6, 5, 10, 11], "texture": "#lantern"},
				"east": {"uv": [6, 5, 10, 11], "texture": "#lantern"},
				"south": {"uv": [6, 5, 10, 11], "texture": "#lantern"},
				"west": {"uv": [6, 5, 10, 11], "texture": "#lantern"}
			}
		},
		{
			"from": [7, 15, 7],
			"to": [9, 16, 9],
			"faces": {
				"north": {"uv": [7, 0, 9, 1], "texture": "#texture"},
				"east": {"uv": [7, 0, 9, 1], "texture": "#texture"},
				"south": {"uv": [7, 0, 9, 1], "texture": "#texture"},
				"west": {"uv": [7, 0, 9, 1], "texture": "#texture"},
				"up": {"uv": [7, 7, 9, 9], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [5, 14, 5],
			"to": [11, 15, 11],
			"faces": {
				"north": {"uv": [5, 1, 11, 2], "texture": "#texture"},
				"east": {"uv": [5, 1, 11, 2], "texture": "#texture"},
				"south": {"uv": [5, 1, 11, 2], "texture": "#texture"},
				"west": {"uv": [5, 1, 11, 2], "texture": "#texture"},
				"up": {"uv": [5, 5, 11, 11], "texture": "#texture"}
			}
		},
		{
			"from": [3, 4, 3],
			"to": [13, 5, 13],
			"faces": {
				"north": {"uv": [3, 11, 13, 12], "texture": "#texture"},
				"east": {"uv": [3, 11, 13, 12], "texture": "#texture"},
				"south": {"uv": [3, 11, 13, 12], "texture": "#texture"},
				"west": {"uv": [3, 11, 13, 12], "texture": "#texture"},
				"up": {"uv": [3, 3, 13, 13], "texture": "#texture"},
				"down": {"uv": [3, 3, 13, 13], "texture": "#texture"}
			}
		},
		{
			"from": [4, 5, 4],
			"to": [12, 6, 12],
			"faces": {
				"north": {"uv": [4, 10, 12, 11], "texture": "#inside"},
				"east": {"uv": [4, 10, 12, 11], "texture": "#inside"},
				"south": {"uv": [4, 10, 12, 11], "texture": "#inside"},
				"west": {"uv": [4, 10, 12, 11], "texture": "#inside"}
			}
		},
		{
			"from": [2, 6, 2],
			"to": [14, 7, 14],
			"faces": {
				"north": {"uv": [2, 9, 14, 10], "texture": "#texture"},
				"east": {"uv": [2, 9, 14, 10], "texture": "#texture"},
				"south": {"uv": [2, 9, 14, 10], "texture": "#texture"},
				"west": {"uv": [2, 9, 14, 10], "texture": "#texture"},
				"up": {"uv": [2, 2, 14, 14], "texture": "#texture"},
				"down": {"uv": [2, 2, 14, 14], "texture": "#texture"}
			}
		},
		{
			"from": [3, 13, 3],
			"to": [13, 14, 13],
			"faces": {
				"north": {"uv": [3, 2, 13, 3], "texture": "#texture"},
				"east": {"uv": [3, 2, 13, 3], "texture": "#texture"},
				"south": {"uv": [3, 2, 13, 3], "texture": "#texture"},
				"west": {"uv": [3, 2, 13, 3], "texture": "#texture"},
				"up": {"uv": [3, 3, 13, 13], "texture": "#texture"},
				"down": {"uv": [3, 3, 13, 13], "texture": "#texture"}
			}
		}
	]
}"""

private const val template_obelisk_item_json = """{
	"credit": "Made with Blockbench",
	"elements": [
		{
			"from": [1, 2, 1],
			"to": [15, 3, 15],
			"faces": {
				"north": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"east": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"south": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"west": {"uv": [1, 13, 15, 14], "texture": "#texture"},
				"up": {"uv": [1, 1, 15, 15], "texture": "#texture"},
				"down": {"uv": [1, 1, 15, 15], "texture": "#texture"}
			}
		},
		{
			"from": [1, 0, 1],
			"to": [15, 1, 15],
			"faces": {
				"north": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"east": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"south": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"west": {"uv": [1, 15, 15, 16], "texture": "#texture"},
				"up": {"uv": [1, 1, 15, 15], "texture": "#texture"},
				"down": {"uv": [1, 1, 15, 15], "texture": "#texture", "cullface": "down"}
			}
		},
		{
			"from": [2, 1, 2],
			"to": [14, 2, 14],
			"faces": {
				"north": {"uv": [2, 14, 14, 15], "texture": "#inside"},
				"east": {"uv": [2, 14, 14, 15], "texture": "#inside"},
				"south": {"uv": [2, 14, 14, 15], "texture": "#inside"},
				"west": {"uv": [2, 14, 14, 15], "texture": "#inside"}
			}
		},
		{
			"from": [3, 3, 3],
			"to": [13, 4, 13],
			"faces": {
				"north": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"east": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"south": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"west": {"uv": [3, 12, 13, 13], "texture": "#texture"},
				"up": {"uv": [3, 3, 13, 13], "texture": "#texture"}
			}
		},
		{
			"from": [4, 4, 4],
			"to": [12, 16, 12],
			"faces": {
				"north": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"east": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"south": {"uv": [4, 0, 12, 12], "texture": "#texture"},
				"west": {"uv": [4, 0, 12, 12], "texture": "#texture"}
			}
		},
		{
			"from": [3, 29, 3],
			"to": [13, 30, 13],
			"faces": {
				"north": {"uv": [3, 2, 13, 3], "texture": "#texture", "cullface": "up"},
				"east": {"uv": [3, 2, 13, 3], "texture": "#texture", "cullface": "up"},
				"south": {"uv": [3, 2, 13, 3], "texture": "#texture", "cullface": "up"},
				"west": {"uv": [3, 2, 13, 3], "texture": "#texture", "cullface": "up"},
				"up": {"uv": [3, 3, 13, 13], "texture": "#texture", "cullface": "up"},
				"down": {"uv": [3, 3, 13, 13], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [2, 22, 2],
			"to": [14, 23, 14],
			"faces": {
				"north": {"uv": [2, 9, 14, 10], "texture": "#texture", "cullface": "up"},
				"east": {"uv": [2, 9, 14, 10], "texture": "#texture", "cullface": "up"},
				"south": {"uv": [2, 9, 14, 10], "texture": "#texture", "cullface": "up"},
				"west": {"uv": [2, 9, 14, 10], "texture": "#texture", "cullface": "up"},
				"up": {"uv": [2, 2, 14, 14], "texture": "#texture", "cullface": "up"},
				"down": {"uv": [2, 2, 14, 14], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [4, 21, 4],
			"to": [12, 22, 12],
			"faces": {
				"north": {"uv": [4, 10, 12, 11], "texture": "#inside", "cullface": "up"},
				"east": {"uv": [4, 10, 12, 11], "texture": "#inside", "cullface": "up"},
				"south": {"uv": [4, 10, 12, 11], "texture": "#inside", "cullface": "up"},
				"west": {"uv": [4, 10, 12, 11], "texture": "#inside", "cullface": "up"}
			}
		},
		{
			"from": [3, 20, 3],
			"to": [13, 21, 13],
			"faces": {
				"north": {"uv": [3, 11, 13, 12], "texture": "#texture", "cullface": "up"},
				"east": {"uv": [3, 11, 13, 12], "texture": "#texture", "cullface": "up"},
				"south": {"uv": [3, 11, 13, 12], "texture": "#texture", "cullface": "up"},
				"west": {"uv": [3, 11, 13, 12], "texture": "#texture", "cullface": "up"},
				"up": {"uv": [3, 3, 13, 13], "texture": "#texture", "cullface": "up"},
				"down": {"uv": [3, 3, 13, 13], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [5, 30, 5],
			"to": [11, 31, 11],
			"faces": {
				"north": {"uv": [5, 1, 11, 2], "texture": "#texture", "cullface": "up"},
				"east": {"uv": [5, 1, 11, 2], "texture": "#texture", "cullface": "up"},
				"south": {"uv": [5, 1, 11, 2], "texture": "#texture", "cullface": "up"},
				"west": {"uv": [5, 1, 11, 2], "texture": "#texture", "cullface": "up"},
				"up": {"uv": [5, 5, 11, 11], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [7, 31, 7],
			"to": [9, 32, 9],
			"faces": {
				"north": {"uv": [7, 0, 9, 1], "texture": "#texture", "cullface": "up"},
				"east": {"uv": [7, 0, 9, 1], "texture": "#texture", "cullface": "up"},
				"south": {"uv": [7, 0, 9, 1], "texture": "#texture", "cullface": "up"},
				"west": {"uv": [7, 0, 9, 1], "texture": "#texture", "cullface": "up"},
				"up": {"uv": [7, 7, 9, 9], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [5, 24, 5],
			"to": [11, 28, 11],
			"faces": {
				"north": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"east": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"south": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"west": {"uv": [5, 6, 11, 10], "texture": "#lantern"},
				"up": {"uv": [5, 5, 11, 11], "texture": "#lantern"},
				"down": {"uv": [5, 5, 11, 11], "texture": "#lantern"}
			}
		},
		{
			"from": [4, 16, 4],
			"to": [12, 20, 12],
			"faces": {
				"north": {"uv": [4, 12, 12, 16], "texture": "#texture", "cullface": "up"},
				"east": {"uv": [4, 12, 12, 16], "texture": "#texture", "cullface": "up"},
				"south": {"uv": [4, 12, 12, 16], "texture": "#texture", "cullface": "up"},
				"west": {"uv": [4, 12, 12, 16], "texture": "#texture", "cullface": "up"}
			}
		},
		{
			"from": [6, 23, 6],
			"to": [10, 29, 10],
			"faces": {
				"north": {"uv": [6, 5, 10, 11], "texture": "#lantern"},
				"east": {"uv": [6, 5, 10, 11], "texture": "#lantern"},
				"south": {"uv": [6, 5, 10, 11], "texture": "#lantern"},
				"west": {"uv": [6, 5, 10, 11], "texture": "#lantern"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2, 0.75],
			"scale": [0.29, 0.29, 0.29]
		},
		"thirdperson_lefthand": {
			"rotation": [75, 45, 0],
			"translation": [0, 2, 0.75],
			"scale": [0.29, 0.29, 0.29]
		},
		"firstperson_righthand": {
			"rotation": [0, 45, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"firstperson_lefthand": {
			"rotation": [0, 225, 0],
			"scale": [0.4, 0.4, 0.4]
		},
		"ground": {
			"translation": [0, 3, 0],
			"scale": [0.25, 0.25, 0.25]
		},
		"gui": {
			"rotation": [30, 225, 0],
			"translation": [0, -2, 0],
			"scale": [0.42, 0.42, 0.42]
		},
		"fixed": {
			"translation": [0, -4, 0],
			"scale": [0.5, 0.5, 0.5]
		}
	}
}"""

private const val textured_cross_json = """{
  "ambientocclusion": false,
  "textures": {
    "particle": "#0"
  },
  "elements": [
    {
      "from": [ 0.8, 0, 8 ],
      "to": [ 15.2, 16, 8 ],
      "rotation": { "origin": [ 8, 8, 8 ], "axis": "y", "angle": 45, "rescale": true },
      "shade": false,
      "faces": {
        "north": {
          "uv": [ 0, 0, 16, 16 ], 
          "texture": "#0"
        },
        "south": {
          "uv": [ 16, 0, 0, 16 ], 
          "texture": "#0"
        }
      }
    },
    {
      "from": [ 8, 0, 0.8 ],
      "to": [ 8, 16, 15.2 ],
      "rotation": { "origin": [ 8, 8, 8 ], "axis": "y", "angle": 45, "rescale": true },
      "shade": false,
      "faces": {
        "west": {
          "uv": [ 0, 0, 16, 16 ], 
          "texture": "#1"
        },
        "east": {
          "uv": [ 16, 0, 0, 16 ], 
          "texture": "#1"
        }
      }
    }
  ]
}"""

private const val flower_pot_textured_cross_json = """{
  "ambientocclusion": false,
    "textures": {
        "particle": "block/flower_pot",
        "flowerpot": "block/flower_pot",
        "dirt": "block/dirt"
    },
    "elements": [
        {   "from": [ 5, 0, 5 ],
            "to": [ 6, 6, 11 ],
            "faces": {
                "down":  { "uv": [ 5, 5, 6, 11 ], "texture": "#flowerpot", "cullface": "down" },
                "up":    { "uv": [ 5, 5, 6, 11 ], "texture": "#flowerpot" },
                "north": { "uv": [ 10, 10, 11, 16 ], "texture": "#flowerpot" },
                "south": { "uv": [ 5, 10, 6, 16 ], "texture": "#flowerpot" },
                "west":  { "uv": [ 5, 10, 11, 16 ], "texture": "#flowerpot" },
                "east":  { "uv": [ 5, 10, 11, 16 ], "texture": "#flowerpot" }
            }
        },
        {   "from": [ 10, 0, 5 ],
            "to": [ 11, 6, 11 ],
            "faces": {
                "down":  { "uv": [ 10, 5, 11, 11 ], "texture": "#flowerpot", "cullface": "down" },
                "up":    { "uv": [ 10, 5, 11, 11 ], "texture": "#flowerpot" },
                "north": { "uv": [ 5, 10, 6, 16 ], "texture": "#flowerpot" },
                "south": { "uv": [ 10, 10, 11, 16 ], "texture": "#flowerpot" },
                "west":  { "uv": [ 5, 10, 11, 16 ], "texture": "#flowerpot" },
                "east":  { "uv": [ 5, 10, 11, 16 ], "texture": "#flowerpot" }
            }
        },
        {   "from": [ 6, 0, 5 ],
            "to": [ 10, 6, 6 ],
            "faces": {
                "down":  { "uv": [ 6, 10, 10, 11 ], "texture": "#flowerpot", "cullface": "down" },
                "up":    { "uv": [ 6, 5, 10, 6 ], "texture": "#flowerpot" },
                "north": { "uv": [ 6, 10, 10, 16 ], "texture": "#flowerpot" },
                "south": { "uv": [ 6, 10, 10, 16 ], "texture": "#flowerpot" }
            }
        },
        {   "from": [ 6, 0, 10 ],
            "to": [ 10, 6, 11 ],
            "faces": {
                "down":  { "uv": [ 6, 5, 10, 6 ], "texture": "#flowerpot", "cullface": "down" },
                "up":    { "uv": [ 6, 10, 10, 11 ], "texture": "#flowerpot" },
                "north": { "uv": [ 6, 10, 10, 16 ], "texture": "#flowerpot" },
                "south": { "uv": [ 6, 10, 10, 16 ], "texture": "#flowerpot" }
            }
        },
        {   "from": [ 6, 0, 6 ],
            "to": [ 10, 4, 10 ],
            "faces": {
                "down": { "uv": [ 6, 12, 10, 16 ], "texture": "#flowerpot", "cullface": "down" },
                "up":   { "uv": [ 6, 6, 10, 10 ], "texture": "#dirt" }
            }
        },
        {   "from": [ 2.6, 4, 8 ],
            "to": [ 13.4, 16, 8 ],
            "rotation": { "origin": [ 8, 8, 8 ], "axis": "y", "angle": 45, "rescale": true },
            "faces": {
                "north": { "uv": [ 0, 0, 16, 16 ], "texture": "#0" },
                "south": { "uv": [ 16, 0, 0, 16 ], "texture": "#0" }
            }
        },
        {   "from": [ 8, 4, 2.6 ],
            "to": [ 8, 16, 13.4 ],
            "rotation": { "origin": [ 8, 8, 8 ], "axis": "y", "angle": 45, "rescale": true },
            "faces": {
                "west": { "uv": [ 0, 0, 16, 16 ], "texture": "#1" },
                "east": { "uv": [ 16, 0, 0, 16 ], "texture": "#1" }
            }
        }
    ]
}"""

private const val template_enhancing_chamber_json = """{
	"credit": "Made with Blockbench",
    "parent": "block/block",
	"elements": [
		{
			"from": [1, 0, 0],
			"to": [4, 16, 1],
			"faces": {
				"north": {"uv": [12, 0, 15, 16], "texture": "#side"},
				"east": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"south": {"uv": [1, 0, 4, 16], "texture": "#side"},
				"west": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"up": {"uv": [1, 0, 4, 1], "texture": "#top"},
				"down": {"uv": [1, 15, 4, 16], "texture": "#bottom"}
			}
		},
		{
			"from": [4, 0, 1],
			"to": [9, 16, 2],
			"faces": {
				"north": {"uv": [7, 0, 12, 16], "texture": "#side"},
				"east": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"south": {"uv": [4, 0, 9, 16], "texture": "#side"},
				"west": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"up": {"uv": [4, 1, 9, 2], "texture": "#top"}
			}
		},
		{
			"from": [9, 0, 0],
			"to": [13, 16, 1],
			"faces": {
				"north": {"uv": [3, 0, 7, 16], "texture": "#side"},
				"east": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"south": {"uv": [9, 0, 13, 16], "texture": "#side"},
				"west": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"up": {"uv": [9, 0, 13, 1], "texture": "#top"},
				"down": {"uv": [9, 15, 13, 16], "texture": "#bottom"}
			}
		},
		{
			"from": [13, 0, 1],
			"to": [15, 16, 2],
			"faces": {
				"north": {"uv": [1, 0, 3, 16], "texture": "#side"},
				"east": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"south": {"uv": [13, 0, 15, 16], "texture": "#side"},
				"west": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"up": {"uv": [13, 1, 15, 2], "texture": "#top"}
			}
		},
		{
			"from": [15, 0, 2],
			"to": [16, 16, 7],
			"faces": {
				"north": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"east": {"uv": [9, 0, 14, 16], "texture": "#side"},
				"south": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"west": {"uv": [2, 0, 7, 16], "texture": "#side"},
				"up": {"uv": [15, 2, 16, 7], "texture": "#top"},
				"down": {"uv": [15, 9, 16, 14], "texture": "#bottom"}
			}
		},
		{
			"from": [14, 0, 7],
			"to": [15, 16, 11],
			"faces": {
				"north": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"east": {"uv": [5, 0, 9, 16], "texture": "#side"},
				"south": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"west": {"uv": [7, 0, 11, 16], "texture": "#side"},
				"up": {"uv": [14, 7, 15, 11], "texture": "#top"}
			}
		},
		{
			"from": [15, 0, 11],
			"to": [16, 16, 15],
			"faces": {
				"north": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"east": {"uv": [1, 0, 5, 16], "texture": "#side"},
				"south": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"west": {"uv": [11, 0, 15, 16], "texture": "#side"},
				"up": {"uv": [15, 11, 16, 15], "texture": "#top"},
				"down": {"uv": [15, 1, 16, 5], "texture": "#bottom"}
			}
		},
		{
			"from": [12, 0, 15],
			"to": [15, 16, 16],
			"faces": {
				"north": {"uv": [1, 0, 4, 16], "texture": "#side"},
				"east": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"south": {"uv": [12, 0, 15, 16], "texture": "#side"},
				"west": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"up": {"uv": [12, 15, 15, 16], "texture": "#top"},
				"down": {"uv": [12, 0, 15, 1], "texture": "#bottom"}
			}
		},
		{
			"from": [6, 0, 14],
			"to": [12, 16, 15],
			"faces": {
				"north": {"uv": [4, 0, 10, 16], "texture": "#side"},
				"east": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"south": {"uv": [6, 0, 12, 16], "texture": "#side"},
				"west": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"up": {"uv": [6, 14, 12, 15], "texture": "#top"}
			}
		},
		{
			"from": [2, 0, 15],
			"to": [6, 16, 16],
			"faces": {
				"north": {"uv": [10, 0, 14, 16], "texture": "#side"},
				"east": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"south": {"uv": [2, 0, 6, 16], "texture": "#side"},
				"west": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"up": {"uv": [2, 15, 6, 16], "texture": "#top"},
				"down": {"uv": [2, 0, 6, 1], "texture": "#bottom"}
			}
		},
		{
			"from": [1, 0, 14],
			"to": [2, 16, 15],
			"faces": {
				"north": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"east": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"south": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"west": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"up": {"uv": [1, 14, 2, 15], "texture": "#top"}
			}
		},
		{
			"from": [0, 0, 10],
			"to": [1, 16, 14],
			"faces": {
				"north": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"east": {"uv": [2, 0, 6, 16], "texture": "#side"},
				"south": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"west": {"uv": [10, 0, 14, 16], "texture": "#side"},
				"up": {"uv": [0, 10, 1, 14], "texture": "#top"},
				"down": {"uv": [0, 2, 1, 6], "texture": "#bottom"}
			}
		},
		{
			"from": [1, 0, 5],
			"to": [2, 16, 10],
			"faces": {
				"north": {"uv": [14, 0, 15, 16], "texture": "#side"},
				"east": {"uv": [6, 0, 11, 16], "texture": "#side"},
				"south": {"uv": [1, 0, 2, 16], "texture": "#side"},
				"west": {"uv": [5, 0, 10, 16], "texture": "#side"},
				"up": {"uv": [1, 5, 2, 10], "texture": "#top"}
			}
		},
		{
			"from": [0, 0, 1],
			"to": [1, 16, 5],
			"faces": {
				"north": {"uv": [15, 0, 16, 16], "texture": "#side"},
				"east": {"uv": [11, 0, 15, 16], "texture": "#side"},
				"south": {"uv": [0, 0, 1, 16], "texture": "#side"},
				"west": {"uv": [1, 0, 5, 16], "texture": "#side"},
				"up": {"uv": [0, 1, 1, 5], "texture": "#top"},
				"down": {"uv": [0, 11, 1, 15], "texture": "#bottom"}
			}
		},
		{
			"from": [1, 0, 1],
			"to": [15, 1, 15],
			"faces": {
				"up": {"uv": [1, 1, 15, 15], "texture": "#bottom"},
				"down": {"uv": [1, 1, 15, 15], "texture": "#bottom"}
			}
		}
	]
}"""