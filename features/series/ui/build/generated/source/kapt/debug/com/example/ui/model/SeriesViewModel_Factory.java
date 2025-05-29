package com.example.ui.model;

import com.example.domain.use_case.GetSeriesUseCase;
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
public final class SeriesViewModel_Factory implements Factory<SeriesViewModel> {
  private final Provider<GetSeriesUseCase> getSeriesUseCaseProvider;

  public SeriesViewModel_Factory(Provider<GetSeriesUseCase> getSeriesUseCaseProvider) {
    this.getSeriesUseCaseProvider = getSeriesUseCaseProvider;
  }

  @Override
  public SeriesViewModel get() {
    return newInstance(getSeriesUseCaseProvider.get());
  }

  public static SeriesViewModel_Factory create(
      Provider<GetSeriesUseCase> getSeriesUseCaseProvider) {
    return new SeriesViewModel_Factory(getSeriesUseCaseProvider);
  }

  public static SeriesViewModel newInstance(GetSeriesUseCase getSeriesUseCase) {
    return new SeriesViewModel(getSeriesUseCase);
  }
}
