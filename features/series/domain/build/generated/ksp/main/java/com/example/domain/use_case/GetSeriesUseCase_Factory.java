package com.example.domain.use_case;

import com.example.domain.repository.SeriesRepository;
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
public final class GetSeriesUseCase_Factory implements Factory<GetSeriesUseCase> {
  private final Provider<SeriesRepository> seriesRepositoryProvider;

  public GetSeriesUseCase_Factory(Provider<SeriesRepository> seriesRepositoryProvider) {
    this.seriesRepositoryProvider = seriesRepositoryProvider;
  }

  @Override
  public GetSeriesUseCase get() {
    return newInstance(seriesRepositoryProvider.get());
  }

  public static GetSeriesUseCase_Factory create(
      Provider<SeriesRepository> seriesRepositoryProvider) {
    return new GetSeriesUseCase_Factory(seriesRepositoryProvider);
  }

  public static GetSeriesUseCase newInstance(SeriesRepository seriesRepository) {
    return new GetSeriesUseCase(seriesRepository);
  }
}
