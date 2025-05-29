package com.example.data;

import com.example.data.data_source.DatabaseDataSource;
import com.example.data.data_source.SeriesNetworkDataSource;
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
public final class SeriesRepositoryImpl_Factory implements Factory<SeriesRepositoryImpl> {
  private final Provider<SeriesNetworkDataSource> networkDataSourceProvider;

  private final Provider<DatabaseDataSource> databaseDataSourceProvider;

  public SeriesRepositoryImpl_Factory(Provider<SeriesNetworkDataSource> networkDataSourceProvider,
      Provider<DatabaseDataSource> databaseDataSourceProvider) {
    this.networkDataSourceProvider = networkDataSourceProvider;
    this.databaseDataSourceProvider = databaseDataSourceProvider;
  }

  @Override
  public SeriesRepositoryImpl get() {
    return newInstance(networkDataSourceProvider.get(), databaseDataSourceProvider.get());
  }

  public static SeriesRepositoryImpl_Factory create(
      Provider<SeriesNetworkDataSource> networkDataSourceProvider,
      Provider<DatabaseDataSource> databaseDataSourceProvider) {
    return new SeriesRepositoryImpl_Factory(networkDataSourceProvider, databaseDataSourceProvider);
  }

  public static SeriesRepositoryImpl newInstance(SeriesNetworkDataSource networkDataSource,
      DatabaseDataSource databaseDataSource) {
    return new SeriesRepositoryImpl(networkDataSource, databaseDataSource);
  }
}
