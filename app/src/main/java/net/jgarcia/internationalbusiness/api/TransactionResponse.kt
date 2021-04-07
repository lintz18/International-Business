package net.jgarcia.internationalbusiness.api

import com.google.gson.annotations.SerializedName
import net.jgarcia.internationalbusiness.data.Transaction

data class TransactionResponse(
        val transactionResults: List<Transaction>
){

}
