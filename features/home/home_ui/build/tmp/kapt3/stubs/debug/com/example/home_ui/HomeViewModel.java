package com.example.home_ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u000f"}, d2 = {"Lcom/example/home_ui/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_stateHome", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/home_ui/HomeState;", "stateHome", "Lkotlinx/coroutines/flow/StateFlow;", "getStateHome", "()Lkotlinx/coroutines/flow/StateFlow;", "navigateTo", "", "target", "Lcom/example/home_ui/NavigationTarget;", "navigationCompleted", "home_ui_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.home_ui.HomeState> _stateHome = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.home_ui.HomeState> stateHome = null;
    
    @jakarta.inject.Inject()
    public HomeViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.home_ui.HomeState> getStateHome() {
        return null;
    }
    
    public final void navigateTo(@org.jetbrains.annotations.NotNull()
    com.example.home_ui.NavigationTarget target) {
    }
    
    public final void navigationCompleted() {
    }
}