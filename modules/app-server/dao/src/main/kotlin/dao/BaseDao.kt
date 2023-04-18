package dao

import org.jooq.DSLContext

interface BaseDao<POJO_TYPE, ID_TYPE> {
    val dslContext: DSLContext

    fun insert(data: POJO_TYPE): POJO_TYPE?
    fun update(data: POJO_TYPE): POJO_TYPE?
    fun delete(id: ID_TYPE)
}