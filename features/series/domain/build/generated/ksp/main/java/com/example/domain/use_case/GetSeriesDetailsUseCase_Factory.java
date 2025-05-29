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
public final class GetSeriesDetailsUseCase_Factory implements Factory<GetSeriesDetailsUseCase> {
  private final Provider<SeriesRepository> seriesRepositoryProvider;

  public GetSeriesDetailsUseCase_Factory(Provider<SeriesRepository> seriesRepositoryProvider) {
    this.seriesRepositoryProvider = seriesRepositoryProvider;
  }

  @Override
  public GetSeriesDetailsUseCase get() {
    return newInstance(seriesRepositoryProvider.get());
  }

  public static GetSeriesDetailsUseCase_Factory create(
      Provider<SeriesRepository> seriesRepositoryProvider) {
    return new GetSeriesDetailsUseCase_Factory(seriesRepositoryProvider);
  }

  public static GetSeriesDetailsUseCase newInstance(SeriesRepository seriesRepository) {
    return new GetSeriesDetailsUseCase(seriesRepository);
  }
}
