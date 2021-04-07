package net.jgarcia.internationalbusiness.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import net.jgarcia.internationalbusiness.R
import net.jgarcia.internationalbusiness.data.Rates
import net.jgarcia.internationalbusiness.databinding.FragmentDetailsBinding
import net.jgarcia.internationalbusiness.utils.Prefs

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val transaction = args.transaction

            Log.i("TRANSACTION", transaction.toString())

            if(!transaction.currency.equals("EUR")) {


                val rate = Prefs.get<Rates>(transaction.currency + "_to_EUR")
                Log.i("CURRENCY", transaction.currency)
                Log.i("RATE", rate!!.rate)

                val amountNumber: Double = transaction.amount.toDouble()
                Log.i("AMOUNT", transaction.amount)
                val rateNumber = rate!!.rate.toDouble()

                val total = amountNumber * rateNumber

                textViewAmount.text = total.toString()
            }
            else
                textViewAmount.text = transaction.amount

        }
    }
}