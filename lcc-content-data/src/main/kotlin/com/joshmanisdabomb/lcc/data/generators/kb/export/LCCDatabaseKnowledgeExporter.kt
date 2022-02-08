package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersionGroup
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime

class LCCDatabaseKnowledgeExporter(db: Database, da: DataAccessor, articles: Iterable<KnowledgeArticleBuilder>) : DatabaseKnowledgeExporter(db, da, articles) {

    override fun transaction(db: Transaction) {
        super.transaction(db)

        Versions.deleteAll()
        VersionGroups.deleteAll()

        LCCVersionGroup.values().forEach { vg ->
            val groupId = VersionGroups.insert {
                it[name] = vg.title
                it[code] = vg.code
                it[branch] = vg.branch
                it[sources] = vg.sources
                it[tags] = vg.tags
                it[order] = vg.order
            } get VersionGroups.id

            LCCVersion.values().filter { it.group == vg }.forEach { v ->
                Versions.insert {
                    it[mod_version] = v.modVersion
                    it[mc_version] = v.mcVersion
                    it[code] = v.code
                    it[group_id] = groupId
                    it[title] = v.title
                    it[changelog] = v.description
                    it[order] = v.order
                    it[released_at] = v.released
                }
            }
        }
    }

    protected object VersionGroups : Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 255)
        val code = varchar("code", 255)
        val branch = varchar("branch", 255)
        val sources = bool("sources")
        val tags = bool("tags")
        val order = short("order")

        override val tableName = "version_groups"

        override val primaryKey = PrimaryKey(id, name = "PRIMARY")
    }

    protected object Versions : Table() {
        val id = integer("id").autoIncrement()
        val mod_version = varchar("mod_version", 255)
        val mc_version = varchar("mc_version", 255)
        val code = varchar("code", 255)
        val group_id = integer("group_id").references(VersionGroups.id)
        val title = varchar("title", 255).nullable()
        val changelog = text("changelog")
        val order = short("order")
        val released_at = datetime("released_at").nullable()

        override val tableName = "versions"

        override val primaryKey = PrimaryKey(id, name = "PRIMARY")
    }

}