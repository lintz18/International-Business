package net.jgarcia.internationalbusiness.ui.gallery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import net.jgarcia.internationalbusiness.data.ProductRepository
import net.jgarcia.internationalbusiness.data.Rates

class GalleryViewModel @ViewModelInject constructor(
    private val repository: ProductRepository,
    @Assisted state: SavedStateHandle
): ViewModel() {

    val transactions = repository.getResults().cachedIn(viewModelScope)


    fun onRatesStartDownloaded() = viewModelScope.launch {
        //SAVE IN SP
        repository.getRates()
    }
}