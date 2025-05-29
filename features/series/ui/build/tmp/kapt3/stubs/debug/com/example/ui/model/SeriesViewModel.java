package com.example.ui.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0011"}, d2 = {"Lcom/example/ui/model/SeriesViewModel;", "Landroidx/lifecycle/ViewModel;", "getSeriesUseCase", "Lcom/example/domain/use_case/GetSeriesUseCase;", "(Lcom/example/domain/use_case/GetSeriesUseCase;)V", "_seriesList", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/ui/model/SeriesState;", "hashMorePages", "", "seriesList", "Lkotlinx/coroutines/flow/StateFlow;", "getSeriesList", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "loadSeries", "ui_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SeriesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.domain.use_case.GetSeriesUseCase getSeriesUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.ui.model.SeriesState> _seriesList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.ui.model.SeriesState> seriesList = null;
    private boolean hashMorePages = true;
    
    @jakarta.inject.Inject()
    public SeriesViewModel(@org.jetbrains.annotations.NotNull()
    com.example.domain.use_case.GetSeriesUseCase getSeriesUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.ui.model.SeriesState> getSeriesList() {
        return null;
    }
    
    public final void loadSeries() {
    }
    
    public final void clearError() {
    }
}