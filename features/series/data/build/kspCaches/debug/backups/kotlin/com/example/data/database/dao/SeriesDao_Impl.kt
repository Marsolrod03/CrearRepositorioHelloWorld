package com.example.`data`.database.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.`data`.database.entities.SeriesEntity
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SeriesDao_Impl(
  __db: RoomDatabase,
) : SeriesDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSeriesEntity: EntityInsertAdapter<SeriesEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSeriesEntity = object : EntityInsertAdapter<SeriesEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `series` (`id`,`name`,`poster_path`,`overview`,`vote_average`,`vote_count`,`first_air_date`) VALUES (?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SeriesEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.poster_path)
        statement.bindText(4, entity.overview)
        statement.bindDouble(5, entity.vote_average)
        statement.bindLong(6, entity.vote_count.toLong())
        statement.bindText(7, entity.first_air_date)
      }
    }
  }

  public override suspend fun insertAll(series: List<SeriesEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfSeriesEntity.insert(_connection, series)
  }

  public override suspend fun getAllSeries(): List<SeriesEntity> {
    val _sql: String = "SELECT * FROM series"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfPosterPath: Int = getColumnIndexOrThrow(_stmt, "poster_path")
        val _columnIndexOfOverview: Int = getColumnIndexOrThrow(_stmt, "overview")
        val _columnIndexOfVoteAverage: Int = getColumnIndexOrThrow(_stmt, "vote_average")
        val _columnIndexOfVoteCount: Int = getColumnIndexOrThrow(_stmt, "vote_count")
        val _columnIndexOfFirstAirDate: Int = getColumnIndexOrThrow(_stmt, "first_air_date")
        val _result: MutableList<SeriesEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SeriesEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpPoster_path: String
          _tmpPoster_path = _stmt.getText(_columnIndexOfPosterPath)
          val _tmpOverview: String
          _tmpOverview = _stmt.getText(_columnIndexOfOverview)
          val _tmpVote_average: Double
          _tmpVote_average = _stmt.getDouble(_columnIndexOfVoteAverage)
          val _tmpVote_count: Int
          _tmpVote_count = _stmt.getLong(_columnIndexOfVoteCount).toInt()
          val _tmpFirst_air_date: String
          _tmpFirst_air_date = _stmt.getText(_columnIndexOfFirstAirDate)
          _item =
              SeriesEntity(_tmpId,_tmpName,_tmpPoster_path,_tmpOverview,_tmpVote_average,_tmpVote_count,_tmpFirst_air_date)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSerieById(id: Int): SeriesEntity {
    val _sql: String = "SELECT * FROM series WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfPosterPath: Int = getColumnIndexOrThrow(_stmt, "poster_path")
        val _columnIndexOfOverview: Int = getColumnIndexOrThrow(_stmt, "overview")
        val _columnIndexOfVoteAverage: Int = getColumnIndexOrThrow(_stmt, "vote_average")
        val _columnIndexOfVoteCount: Int = getColumnIndexOrThrow(_stmt, "vote_count")
        val _columnIndexOfFirstAirDate: Int = getColumnIndexOrThrow(_stmt, "first_air_date")
        val _result: SeriesEntity
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpPoster_path: String
          _tmpPoster_path = _stmt.getText(_columnIndexOfPosterPath)
          val _tmpOverview: String
          _tmpOverview = _stmt.getText(_columnIndexOfOverview)
          val _tmpVote_average: Double
          _tmpVote_average = _stmt.getDouble(_columnIndexOfVoteAverage)
          val _tmpVote_count: Int
          _tmpVote_count = _stmt.getLong(_columnIndexOfVoteCount).toInt()
          val _tmpFirst_air_date: String
          _tmpFirst_air_date = _stmt.getText(_columnIndexOfFirstAirDate)
          _result =
              SeriesEntity(_tmpId,_tmpName,_tmpPoster_path,_tmpOverview,_tmpVote_average,_tmpVote_count,_tmpFirst_air_date)
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type <com.example.`data`.database.entities.SeriesEntity>.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearSeries() {
    val _sql: String = "DELETE FROM series"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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
