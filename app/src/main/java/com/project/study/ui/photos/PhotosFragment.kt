package com.project.study.ui.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.project.study.R
import com.project.study.databinding.FragmentPhotosBinding
import com.project.study.utils.PaginationScrollListener
import com.project.study.data.client.Status
import com.project.study.utils.showToast
import com.project.study.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private val TAG = "PhotosFragment"
    private lateinit var binding: FragmentPhotosBinding
    private val viewModel: SharedViewModel by viewModels()
//    private lateinit var photoAdapter: PhotosAdapter
    private lateinit var photosListAdapter: PhotosListAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotosBinding.bind(view)
        binding.viewModel = viewModel

//        photoAdapter = PhotosAdapter()
        photosListAdapter = PhotosListAdapter()

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object :PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                Log.d(TAG, "loadMoreItems: ")
                isLoading = true
                viewModel.insertPhotos(++viewModel.pageNo)
            }

        })
        binding.recyclerView.adapter = photosListAdapter

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.getPhotos().observe(viewLifecycleOwner, {events->
            when (events.status) {
                Status.SUCCESS -> {
                    events.data.let { list ->
                        Log.d(TAG, "photosList Size: ${list?.size}")
                        isLoading = false
//                        photoAdapter.submitList(list)
                        photosListAdapter.submitList(list)
                    }
                }
                Status.ERROR -> {
                    requireActivity().showToast(events.message.toString())
                }
            }
        })
    }

}