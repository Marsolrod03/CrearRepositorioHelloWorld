package com.example.data.data_source;

import com.example.data.network.SeriesService;
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
public final class SeriesNetworkDataSource_Factory implements Factory<SeriesNetworkDataSource> {
  private final Provider<SeriesService> seriesServiceProvider;

  public SeriesNetworkDataSource_Factory(Provider<SeriesService> seriesServiceProvider) {
    this.seriesServiceProvider = seriesServiceProvider;
  }

  @Override
  public SeriesNetworkDataSource get() {
    return newInstance(seriesServiceProvider.get());
  }

  public static SeriesNetworkDataSource_Factory create(
      Provider<SeriesService> seriesServiceProvider) {
    return new SeriesNetworkDataSource_Factory(seriesServiceProvider);
  }

  public static SeriesNetworkDataSource newInstance(SeriesService seriesService) {
    return new SeriesNetworkDataSource(seriesService);
  }
}
