package com.example.common_data;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
public final class RetrofitModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<Interceptor> apiKeyInterceptorProvider;

  private final Provider<HttpLoggingInterceptor> loggingInterceptorProvider;

  public RetrofitModule_ProvideOkHttpClientFactory(Provider<Interceptor> apiKeyInterceptorProvider,
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
    this.apiKeyInterceptorProvider = apiKeyInterceptorProvider;
    this.loggingInterceptorProvider = loggingInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(apiKeyInterceptorProvider.get(), loggingInterceptorProvider.get());
  }

  public static RetrofitModule_ProvideOkHttpClientFactory create(
      Provider<Interceptor> apiKeyInterceptorProvider,
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
    return new RetrofitModule_ProvideOkHttpClientFactory(apiKeyInterceptorProvider, loggingInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(Interceptor apiKeyInterceptor,
      HttpLoggingInterceptor loggingInterceptor) {
    return Preconditions.checkNotNullFromProvides(RetrofitModule.INSTANCE.provideOkHttpClient(apiKeyInterceptor, loggingInterceptor));
  }
}
