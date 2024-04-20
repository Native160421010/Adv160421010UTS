package com.wildfire.adv160421010uts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wildfire.adv160421010uts.databinding.FragmentDetailBinding
import com.wildfire.adv160421010uts.viewmodel.DetailViewModel

class detailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private var newsId: Int = 0

    private var curPage: Int = 0
    private var ttlPage: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsId = detailFragmentArgs.fromBundle(requireArguments()).newsId
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.fetchData(newsId)

        observeViewModel()

        binding.btnPrev.setOnClickListener {
            curPage = curPage - 1
            observeViewModel()
        }
        binding.btnNext.setOnClickListener {
            curPage = curPage + 1
            observeViewModel()
        }
    }

    fun observeViewModel() {
        detailViewModel.detailLD.observe(viewLifecycleOwner, Observer {
            // Picasso.get().load(student.photoUrl).into(binding.imgView)

            ttlPage = it.content?.size ?: 0
            if(curPage > ttlPage - 1){
                curPage = 0
            }

            if(curPage < 0){
                curPage = ttlPage-1
            }

            binding.txtTitleDetail.setText(it.title)
            binding.txtCreatorDetail.setText(it.creator)
            binding.txtTitlePage.text = it.content?.getOrNull(curPage)?.getOrNull(0) ?: "No content available"
            binding.txtContent.text = it.content?.getOrNull(curPage)?.getOrNull(1) ?: "No content available"
            binding.txtPage.text = "${curPage + 1} / $ttlPage"
        })
    }
}