package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import net.minecraft.data.DataCache
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class DatabaseKnowledgeExporter(private val db: Database, da: DataAccessor, articles: Iterable<KnowledgeArticleBuilder>) : KnowledgeExporter(da, articles) {

    override fun hasTranslations() = true

    override fun run(cache: DataCache) {
        transaction(db) {
            addLogger(StdOutSqlLogger)

            ArticleFragments.deleteAll()
            ArticleSections.deleteAll()
            Articles.update({ Articles.deleted_at.isNull() }) { it[deleted_at] = LocalDateTime.now() }

            articles.forEach { a ->
                a.onExport(this@DatabaseKnowledgeExporter)

                val existing: Int? = Articles.select { (Articles.registry eq a.location.registry.toString()) and (Articles.key eq a.location.key.toString()) }.singleOrNull()?.getOrNull(Articles.id)
                val articleId: Int
                if (existing == null) {
                    articleId = Articles.insert {
                        it[registry] = a.location.registry.toString()
                        it[key] = a.location.key.toString()
                        it[name] = a.finalName
                        it[slug1] = a.location.registry.path
                        it[slug2] = a.location.key.path
                        it[created_at] = LocalDateTime.now()
                        it[updated_at] = null
                        it[deleted_at] = null
                    } get Articles.id
                } else {
                    articleId = existing
                    Articles.update({ Articles.id eq articleId }) {
                        it[name] = a.finalName
                        it[slug1] = a.location.registry.path
                        it[slug2] = a.location.key.path
                        it[updated_at] = LocalDateTime.now()
                        it[deleted_at] = null
                    }
                }

                a.sections.forEachIndexed { k, s ->
                    s.onExport(a, this@DatabaseKnowledgeExporter)
                    val sectionId = ArticleSections.insert {
                        it[article_id] = articleId
                        it[name] = s.finalName
                        it[type] = s.type
                        it[order] = k.toShort()
                        it[created_at] = LocalDateTime.now()
                        it[updated_at] = null
                        it[deleted_at] = null
                    } get ArticleSections.id

                    s.fragments.forEachIndexed { k2, f ->
                        f.onExport(a, s, this@DatabaseKnowledgeExporter)
                        ArticleFragments.insert {
                            it[section_id] = sectionId
                            it[type] = f.type
                            it[markup] = f.toJson(a, s, this@DatabaseKnowledgeExporter).toString()
                            it[order] = k2.toShort()
                            it[created_at] = LocalDateTime.now()
                            it[updated_at] = null
                            it[deleted_at] = null
                        }
                    }
                }
            }
        }
    }

    override fun getName() = "${da.modid} Knowledge Database Exporter"

    protected object Articles : Table() {
        val id = integer("id").autoIncrement()
        val registry = varchar("registry", 255)
        val key = varchar("key", 255)
        val name = varchar("name", 255)
        val slug1 = varchar("slug1", 255)
        val slug2 = varchar("slug2", 255)
        val created_at = datetime("created_at").nullable()
        val updated_at = datetime("updated_at").nullable()
        val deleted_at = datetime("deleted_at").nullable()

        override val primaryKey = PrimaryKey(id, name = "PRIMARY")
    }

    protected object ArticleSections : Table() {
        val id = integer("id").autoIncrement()
        val article_id = integer("article_id").references(Articles.id)
        val name = varchar("name", 255)
        val type = varchar("type", 255)
        val order = short("order")
        val created_at = datetime("created_at").nullable()
        val updated_at = datetime("updated_at").nullable()
        val deleted_at = datetime("deleted_at").nullable()

        override val tableName = "article_sections"

        override val primaryKey = PrimaryKey(id, name = "PRIMARY")
    }

    protected object ArticleFragments : Table() {
        val id = integer("id").autoIncrement()
        val section_id = integer("section_id").references(ArticleSections.id)
        val type = varchar("type", 255)
        val markup = text("markup")
        val order = short("order")
        val created_at = datetime("created_at").nullable()
        val updated_at = datetime("updated_at").nullable()
        val deleted_at = datetime("deleted_at").nullable()

        override val tableName = "article_fragments"

        override val primaryKey = PrimaryKey(id, name = "PRIMARY")
    }

}