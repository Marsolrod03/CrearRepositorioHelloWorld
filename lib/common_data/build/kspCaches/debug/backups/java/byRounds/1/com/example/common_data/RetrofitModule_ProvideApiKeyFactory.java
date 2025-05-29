package com.example.common_data;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class RetrofitModule_ProvideApiKeyFactory implements Factory<String> {
  @Override
  public String get() {
    return provideApiKey();
  }

  public static RetrofitModule_ProvideApiKeyFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provideApiKey() {
    return Preconditions.checkNotNullFromProvides(RetrofitModule.INSTANCE.provideApiKey());
  }

  private static final class InstanceHolder {
    static final RetrofitModule_ProvideApiKeyFactory INSTANCE = new RetrofitModule_ProvideApiKeyFactory();
  }
}
