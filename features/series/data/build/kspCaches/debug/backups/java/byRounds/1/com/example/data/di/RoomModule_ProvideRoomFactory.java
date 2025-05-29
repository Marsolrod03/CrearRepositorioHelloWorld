package com.example.data.di;

import android.content.Context;
import com.example.data.database.SeriesDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class RoomModule_ProvideRoomFactory implements Factory<SeriesDatabase> {
  private final RoomModule module;

  private final Provider<Context> contextProvider;

  public RoomModule_ProvideRoomFactory(RoomModule module, Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public SeriesDatabase get() {
    return provideRoom(module, contextProvider.get());
  }

  public static RoomModule_ProvideRoomFactory create(RoomModule module,
      Provider<Context> contextProvider) {
    return new RoomModule_ProvideRoomFactory(module, contextProvider);
  }

  public static SeriesDatabase provideRoom(RoomModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideRoom(context));
  }
}
