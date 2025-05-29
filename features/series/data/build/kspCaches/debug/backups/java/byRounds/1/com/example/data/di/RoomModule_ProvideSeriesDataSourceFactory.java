package com.example.data.di;

import com.example.data.data_source.DatabaseDataSource;
import com.example.data.database.dao.PaginationSeriesDao;
import com.example.data.database.dao.SeriesDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RoomModule_ProvideSeriesDataSourceFactory implements Factory<DatabaseDataSource> {
  private final RoomModule module;

  private final Provider<SeriesDao> seriesDaoProvider;

  private final Provider<PaginationSeriesDao> paginationSeriesDaoProvider;

  public RoomModule_ProvideSeriesDataSourceFactory(RoomModule module,
      Provider<SeriesDao> seriesDaoProvider,
      Provider<PaginationSeriesDao> paginationSeriesDaoProvider) {
    this.module = module;
    this.seriesDaoProvider = seriesDaoProvider;
    this.paginationSeriesDaoProvider = paginationSeriesDaoProvider;
  }

  @Override
  public DatabaseDataSource get() {
    return provideSeriesDataSource(module, seriesDaoProvider.get(), paginationSeriesDaoProvider.get());
  }

  public static RoomModule_ProvideSeriesDataSourceFactory create(RoomModule module,
      Provider<SeriesDao> seriesDaoProvider,
      Provider<PaginationSeriesDao> paginationSeriesDaoProvider) {
    return new RoomModule_ProvideSeriesDataSourceFactory(module, seriesDaoProvider, paginationSeriesDaoProvider);
  }

  public static DatabaseDataSource provideSeriesDataSource(RoomModule instance, SeriesDao seriesDao,
      PaginationSeriesDao paginationSeriesDao) {
    return Preconditions.checkNotNullFromProvides(instance.provideSeriesDataSource(seriesDao, paginationSeriesDao));
  }
}
