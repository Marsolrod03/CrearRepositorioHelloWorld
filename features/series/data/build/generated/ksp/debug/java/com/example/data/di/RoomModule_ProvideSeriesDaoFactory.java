package com.example.data.di;

import com.example.data.database.SeriesDatabase;
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
public final class RoomModule_ProvideSeriesDaoFactory implements Factory<SeriesDao> {
  private final RoomModule module;

  private final Provider<SeriesDatabase> dbProvider;

  public RoomModule_ProvideSeriesDaoFactory(RoomModule module,
      Provider<SeriesDatabase> dbProvider) {
    this.module = module;
    this.dbProvider = dbProvider;
  }

  @Override
  public SeriesDao get() {
    return provideSeriesDao(module, dbProvider.get());
  }

  public static RoomModule_ProvideSeriesDaoFactory create(RoomModule module,
      Provider<SeriesDatabase> dbProvider) {
    return new RoomModule_ProvideSeriesDaoFactory(module, dbProvider);
  }

  public static SeriesDao provideSeriesDao(RoomModule instance, SeriesDatabase db) {
    return Preconditions.checkNotNullFromProvides(instance.provideSeriesDao(db));
  }
}
