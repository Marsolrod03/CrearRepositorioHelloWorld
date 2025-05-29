package com.example.common_data;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.Interceptor;

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
public final class RetrofitModule_ProvideApiKeyInterceptorFactory implements Factory<Interceptor> {
  private final Provider<String> apiKeyProvider;

  public RetrofitModule_ProvideApiKeyInterceptorFactory(Provider<String> apiKeyProvider) {
    this.apiKeyProvider = apiKeyProvider;
  }

  @Override
  public Interceptor get() {
    return provideApiKeyInterceptor(apiKeyProvider.get());
  }

  public static RetrofitModule_ProvideApiKeyInterceptorFactory create(
      Provider<String> apiKeyProvider) {
    return new RetrofitModule_ProvideApiKeyInterceptorFactory(apiKeyProvider);
  }

  public static Interceptor provideApiKeyInterceptor(String apiKey) {
    return Preconditions.checkNotNullFromProvides(RetrofitModule.INSTANCE.provideApiKeyInterceptor(apiKey));
  }
}
