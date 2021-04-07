package net.jgarcia.internationalbusiness.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.jgarcia.internationalbusiness.R
import net.jgarcia.internationalbusiness.api.ProductApi
import net.jgarcia.internationalbusiness.api.ProductApi.Companion.BASE_URL
import net.jgarcia.internationalbusiness.data.Rates
import net.jgarcia.internationalbusiness.data.Transaction
import net.jgarcia.internationalbusiness.databinding.FragmentGalleryBinding
import net.jgarcia.internationalbusiness.ui.gallery.adapter.ProductAdapter
import net.jgarcia.internationalbusiness.utils.Prefs
import net.jgarcia.internationalbusiness.utils.Prefs.RATES_KEY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment: Fragment(R.layout.fragment_gallery), ProductAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = ProductAdapter(this)

        getRates()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null //
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = GalleryLoadStateAdapter {
                        adapter.retry()
                    },
                    footer = GalleryLoadStateAdapter {
                        adapter.retry()
                    },
            )

            buttonRetry.setOnClickListener{
                adapter.retry()
            }
        }

        viewModel.transactions.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.onRatesStartDownloaded()

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //Si los datos en la vista está vacía
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Interface Adapter
    override fun OnItemClick(transaction: Transaction) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(transaction)
        findNavController().navigate(action)
    }


    private fun getRates(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ProductApi::class.java).getRates()
            val rates = call.body()
            if(call.isSuccessful){
                //Save SP
                    for(rate: Rates in rates!!) {
                        if (rate.to.equals("EUR")) {
                            Prefs.put(rate, rate.from + "_to_" + rate.to)
                            Log.i("PREFS", "Rates: " + rate.toString())
                        }
                        else{
                            //TODO
                        }
                    }

            }else{
                //show error TOAST
                Toast.makeText(context, "No se han recuperado los datos", Toast.LENGTH_LONG)
            }
        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

//    private fun transformAudToEur(rate:Rates){

//    }

}