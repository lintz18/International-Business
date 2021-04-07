package net.jgarcia.internationalbusiness.data

import android.util.Log
import androidx.paging.PagingSource
import com.google.gson.GsonBuilder
import net.jgarcia.internationalbusiness.api.ProductApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ProductPagingSource (
    private val productApi: ProductApi
) : PagingSource<Int, Transaction>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {

            val response = productApi.getTransactions()
            val transactions =  response.toList()

            LoadResult.Page(
                data = transactions,
                prevKey = if(position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(transactions.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }


}


