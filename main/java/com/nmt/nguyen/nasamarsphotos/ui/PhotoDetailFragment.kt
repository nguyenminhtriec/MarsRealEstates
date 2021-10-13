package com.nmt.nguyen.nasamarsphotos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.nmt.nguyen.nasamarsphotos.R
import com.nmt.nguyen.nasamarsphotos.databinding.FragmentPhotoDetailBinding
import com.nmt.nguyen.nasamarsphotos.model.PhotoViewModel
import com.nmt.nguyen.nasamarsphotos.network.MarsPhoto
import java.text.NumberFormat

class PhotoDetailFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: FragmentPhotoDetailBinding

    private val navigationArgs: PhotoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.photo = viewModel.selectedPhoto.value
        bindInfo() // call bindInfo.

    }
    // Show plot information on corresponding TextViews
    private fun bindInfo() {
        binding.apply {
            plotIdTextview.text = getString(R.string.plot_id, navigationArgs.plotId)
            plotTypeTextView.text = getString(R.string.plot_type, navigationArgs.plotType.uppercase())
            plotPriceTextview.text = getString(R.string.plot_price, getPriceRentOrBuy(navigationArgs.plotPrice))
        }
    }

    // Get price according to plot type (buy or rent), then format it before showing in [TextView]
    private fun getPriceRentOrBuy(price: String): String {
        val originalPrice: Double? = price.toDoubleOrNull()
        val priceByType = if (navigationArgs.plotType == "buy") originalPrice else  originalPrice?.div(100)
        return NumberFormat.getCurrencyInstance().format(priceByType)
    }

}