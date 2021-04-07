package net.jgarcia.internationalbusiness.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import net.jgarcia.internationalbusiness.api.ProductApi
import javax.inject.Inject

class ProductRepository@Inject constructor(private val productApi: ProductApi) {

    fun getResults() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(productApi) }
        ).liveData

    suspend fun getRates() = productApi.getRates()
}