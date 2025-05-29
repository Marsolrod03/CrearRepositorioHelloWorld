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
public final class DeleteDatabaseUseCase_Factory implements Factory<DeleteDatabaseUseCase> {
  private final Provider<SeriesRepository> seriesRepositoryProvider;

  public DeleteDatabaseUseCase_Factory(Provider<SeriesRepository> seriesRepositoryProvider) {
    this.seriesRepositoryProvider = seriesRepositoryProvider;
  }

  @Override
  public DeleteDatabaseUseCase get() {
    return newInstance(seriesRepositoryProvider.get());
  }

  public static DeleteDatabaseUseCase_Factory create(
      Provider<SeriesRepository> seriesRepositoryProvider) {
    return new DeleteDatabaseUseCase_Factory(seriesRepositoryProvider);
  }

  public static DeleteDatabaseUseCase newInstance(SeriesRepository seriesRepository) {
    return new DeleteDatabaseUseCase(seriesRepository);
  }
}
