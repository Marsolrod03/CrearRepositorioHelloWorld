package com.example.`data`.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.example.`data`.database.dao.PaginationSeriesDao
import com.example.`data`.database.dao.PaginationSeriesDao_Impl
import com.example.`data`.database.dao.SeriesDao
import com.example.`data`.database.dao.SeriesDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SeriesDatabase_Impl : SeriesDatabase() {
  private val _seriesDao: Lazy<SeriesDao> = lazy {
    SeriesDao_Impl(this)
  }

  private val _paginationSeriesDao: Lazy<PaginationSeriesDao> = lazy {
    PaginationSeriesDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(3,
        "4a69bb9f196b29c84dbc5bb83cc5fceb", "a6c3df38810df4cf641cc1dff866f26e") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `series` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `poster_path` TEXT NOT NULL, `overview` TEXT NOT NULL, `vote_average` REAL NOT NULL, `vote_count` INTEGER NOT NULL, `first_air_date` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `pagination_series` (`id` INTEGER NOT NULL, `last_loaded_page` INTEGER NOT NULL, `total_pages` INTEGER NOT NULL, `last_database_deletion` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4a69bb9f196b29c84dbc5bb83cc5fceb')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `series`")
        connection.execSQL("DROP TABLE IF EXISTS `pagination_series`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsSeries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSeries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSeries.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSeries.put("poster_path", TableInfo.Column("poster_path", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSeries.put("overview", TableInfo.Column("overview", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSeries.put("vote_average", TableInfo.Column("vote_average", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSeries.put("vote_count", TableInfo.Column("vote_count", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSeries.put("first_air_date", TableInfo.Column("first_air_date", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSeries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSeries: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSeries: TableInfo = TableInfo("series", _columnsSeries, _foreignKeysSeries,
            _indicesSeries)
        val _existingSeries: TableInfo = read(connection, "series")
        if (!_infoSeries.equals(_existingSeries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |series(com.example.data.database.entities.SeriesEntity).
              | Expected:
              |""".trimMargin() + _infoSeries + """
              |
              | Found:
              |""".trimMargin() + _existingSeries)
        }
        val _columnsPaginationSeries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPaginationSeries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPaginationSeries.put("last_loaded_page", TableInfo.Column("last_loaded_page",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPaginationSeries.put("total_pages", TableInfo.Column("total_pages", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPaginationSeries.put("last_database_deletion",
            TableInfo.Column("last_database_deletion", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPaginationSeries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPaginationSeries: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPaginationSeries: TableInfo = TableInfo("pagination_series",
            _columnsPaginationSeries, _foreignKeysPaginationSeries, _indicesPaginationSeries)
        val _existingPaginationSeries: TableInfo = read(connection, "pagination_series")
        if (!_infoPaginationSeries.equals(_existingPaginationSeries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |pagination_series(com.example.data.database.entities.PaginationSeriesEntity).
              | Expected:
              |""".trimMargin() + _infoPaginationSeries + """
              |
              | Found:
              |""".trimMargin() + _existingPaginationSeries)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "series", "pagination_series")
  }

  public override fun clearAllTables() {
    super.performClear(false, "series", "pagination_series")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(SeriesDao::class, SeriesDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(PaginationSeriesDao::class,
        PaginationSeriesDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun getSeriesDao(): SeriesDao = _seriesDao.value

  public override fun getPaginationSeriesDao(): PaginationSeriesDao = _paginationSeriesDao.value
}
