package com.example.`data`.database.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.`data`.database.entities.PaginationSeriesEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PaginationSeriesDao_Impl(
  __db: RoomDatabase,
) : PaginationSeriesDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPaginationSeriesEntity: EntityInsertAdapter<PaginationSeriesEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfPaginationSeriesEntity = object :
        EntityInsertAdapter<PaginationSeriesEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `pagination_series` (`id`,`last_loaded_page`,`total_pages`,`last_database_deletion`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PaginationSeriesEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindLong(2, entity.lastLoadedPage.toLong())
        statement.bindLong(3, entity.total_pages.toLong())
        statement.bindLong(4, entity.lastDatabaseDeletion)
      }
    }
  }

  public override suspend
      fun insertPaginationSeries(paginationSeriesEntity: PaginationSeriesEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPaginationSeriesEntity.insert(_connection, paginationSeriesEntity)
  }

  public override suspend fun getPaginationSeries(): Int {
    val _sql: String = "SELECT last_loaded_page FROM pagination_series"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          _result = _stmt.getLong(0).toInt()
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLastDatabaseDeletion(): Long {
    val _sql: String = "SELECT last_database_deletion FROM pagination_series WHERE id = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Long
        if (_stmt.step()) {
          _result = _stmt.getLong(0)
        } else {
          _result = 0L
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalPages(): Int {
    val _sql: String = "SELECT total_pages FROM pagination_series WHERE id = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          _result = _stmt.getLong(0).toInt()
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearPaginationSeries() {
    val _sql: String = "DELETE FROM pagination_series"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePaginationSeries(newPage: Int) {
    val _sql: String = "UPDATE pagination_series SET last_loaded_page = ? WHERE id = 1"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, newPage.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLastDatabaseDeletion(newLastDatabaseDeletion: Long) {
    val _sql: String = "UPDATE pagination_series SET last_database_deletion = ? WHERE id = 1"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, newLastDatabaseDeletion)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateTotalPages(newTotalPages: Int) {
    val _sql: String = "UPDATE pagination_series SET total_pages = ? WHERE id = 1"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, newTotalPages.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
