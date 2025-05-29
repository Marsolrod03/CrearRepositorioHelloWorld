package com.example.data.data_source;

import com.example.data.database.dao.PaginationSeriesDao;
import com.example.data.database.dao.SeriesDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DatabaseDataSource_Factory implements Factory<DatabaseDataSource> {
  private final Provider<SeriesDao> seriesDaoProvider;

  private final Provider<PaginationSeriesDao> paginationSeriesDaoProvider;

  public DatabaseDataSource_Factory(Provider<SeriesDao> seriesDaoProvider,
      Provider<PaginationSeriesDao> paginationSeriesDaoProvider) {
    this.seriesDaoProvider = seriesDaoProvider;
    this.paginationSeriesDaoProvider = paginationSeriesDaoProvider;
  }

  @Override
  public DatabaseDataSource get() {
    return newInstance(seriesDaoProvider.get(), paginationSeriesDaoProvider.get());
  }

  public static DatabaseDataSource_Factory create(Provider<SeriesDao> seriesDaoProvider,
      Provider<PaginationSeriesDao> paginationSeriesDaoProvider) {
    return new DatabaseDataSource_Factory(seriesDaoProvider, paginationSeriesDaoProvider);
  }

  public static DatabaseDataSource newInstance(SeriesDao seriesDao,
      PaginationSeriesDao paginationSeriesDao) {
    return new DatabaseDataSource(seriesDao, paginationSeriesDao);
  }
}
