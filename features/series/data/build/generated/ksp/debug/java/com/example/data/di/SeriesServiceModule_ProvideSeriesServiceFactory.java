package com.example.data.di;

import com.example.data.network.SeriesService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

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
public final class SeriesServiceModule_ProvideSeriesServiceFactory implements Factory<SeriesService> {
  private final Provider<Retrofit> retrofitProvider;

  public SeriesServiceModule_ProvideSeriesServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public SeriesService get() {
    return provideSeriesService(retrofitProvider.get());
  }

  public static SeriesServiceModule_ProvideSeriesServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new SeriesServiceModule_ProvideSeriesServiceFactory(retrofitProvider);
  }

  public static SeriesService provideSeriesService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(SeriesServiceModule.INSTANCE.provideSeriesService(retrofit));
  }
}
