package com.nmt.nguyen.nasamarsphotos.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import com.nmt.nguyen.nasamarsphotos.R
import com.nmt.nguyen.nasamarsphotos.databinding.FragmentPhotoListBinding
import com.nmt.nguyen.nasamarsphotos.model.MarsApiStatus
import com.nmt.nguyen.nasamarsphotos.model.PhotoViewModel
import com.nmt.nguyen.nasamarsphotos.network.MarsApiFilter

class PhotoListFragment : Fragment() {

    private lateinit var binding: FragmentPhotoListBinding
    private val viewModel: PhotoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_photo_list, container, false)
        binding = FragmentPhotoListBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = PhotoListAdapter {

            viewModel.getSelecttedPhoto(it) // update value of [_selectedPhoto] in [PhotoViewModel]

            val action = PhotoListFragmentDirections
                .actionPhotoListFragmentToPhotoDetailFragment(it.id, it.type, it.price.toString())

            findNavController().navigate(action)
        }
        binding.photosGrid.adapter = adapter

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.status.observe(viewLifecycleOwner) {
            bindStatus(binding.statusImage, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_option, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.show_buy_menu -> viewModel.updateFilter(MarsApiFilter.BUY)
            R.id.show_rent_menu -> viewModel.updateFilter(MarsApiFilter.RENT)
            else -> viewModel.updateFilter(MarsApiFilter.ALL)
        }
        return true
    }

    //@BindingAdapter("marsApiStatus") -- this Annotation only needed when using data binding
    private fun bindStatus(statusImageView: ImageView, status: MarsApiStatus) {
        when(status) {
            MarsApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
            MarsApiStatus.SUCCESS -> {
                statusImageView.visibility = View.GONE
            }
            MarsApiStatus.FAILURE -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
            }
        }
    }


}

