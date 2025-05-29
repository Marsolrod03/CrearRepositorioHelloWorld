package com.example.data.di;

import com.example.data.database.SeriesDatabase;
import com.example.data.database.dao.PaginationSeriesDao;
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
public final class RoomModule_ProvidePaginationSeriesDaoFactory implements Factory<PaginationSeriesDao> {
  private final RoomModule module;

  private final Provider<SeriesDatabase> dbProvider;

  public RoomModule_ProvidePaginationSeriesDaoFactory(RoomModule module,
      Provider<SeriesDatabase> dbProvider) {
    this.module = module;
    this.dbProvider = dbProvider;
  }

  @Override
  public PaginationSeriesDao get() {
    return providePaginationSeriesDao(module, dbProvider.get());
  }

  public static RoomModule_ProvidePaginationSeriesDaoFactory create(RoomModule module,
      Provider<SeriesDatabase> dbProvider) {
    return new RoomModule_ProvidePaginationSeriesDaoFactory(module, dbProvider);
  }

  public static PaginationSeriesDao providePaginationSeriesDao(RoomModule instance,
      SeriesDatabase db) {
    return Preconditions.checkNotNullFromProvides(instance.providePaginationSeriesDao(db));
  }
}
