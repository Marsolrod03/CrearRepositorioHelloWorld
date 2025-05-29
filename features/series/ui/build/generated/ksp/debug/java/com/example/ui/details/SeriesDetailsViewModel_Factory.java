package com.example.ui.details;

import com.example.domain.use_case.GetSeriesDetailsUseCase;
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
public final class SeriesDetailsViewModel_Factory implements Factory<SeriesDetailsViewModel> {
  private final Provider<GetSeriesDetailsUseCase> getSeriesDetailsUseCaseProvider;

  public SeriesDetailsViewModel_Factory(
      Provider<GetSeriesDetailsUseCase> getSeriesDetailsUseCaseProvider) {
    this.getSeriesDetailsUseCaseProvider = getSeriesDetailsUseCaseProvider;
  }

  @Override
  public SeriesDetailsViewModel get() {
    return newInstance(getSeriesDetailsUseCaseProvider.get());
  }

  public static SeriesDetailsViewModel_Factory create(
      Provider<GetSeriesDetailsUseCase> getSeriesDetailsUseCaseProvider) {
    return new SeriesDetailsViewModel_Factory(getSeriesDetailsUseCaseProvider);
  }

  public static SeriesDetailsViewModel newInstance(
      GetSeriesDetailsUseCase getSeriesDetailsUseCase) {
    return new SeriesDetailsViewModel(getSeriesDetailsUseCase);
  }
}
