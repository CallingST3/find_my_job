package com.apps.findmyjob.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.apps.findmyjob.R
import com.apps.findmyjob.databinding.FragmentDetailBinding
import com.apps.findmyjob.util.APP_TAG
import com.apps.findmyjob.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list_of_results.loadingProgressBar
import kotlinx.android.synthetic.main.fragment_list_of_results.textViewLoadError

class DetailFragment : Fragment() {

    private var vacancyId = ""
    private var companyCode = ""

    private lateinit var dataBinding: FragmentDetailBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            vacancyId = DetailFragmentArgs.fromBundle(it).vacancyId
            companyCode = DetailFragmentArgs.fromBundle(it).companyCode
            Log.d(APP_TAG, "args id: $vacancyId, company code: $companyCode")
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.refresh(vacancyId, companyCode)

        detailRefreshLayout.setOnRefreshListener {
            rootLayout.visibility = View.GONE
            textViewLoadError.visibility = View.GONE
            loadingProgressBar.visibility = View.VISIBLE
            viewModel.refresh(vacancyId, companyCode)
            detailRefreshLayout.isRefreshing = false
        }

        observeViewModel()

        textViewCompany.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToListOfResultsFragment()
            action.typeOfRequest = 2
            action.companyCode = companyCode
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.vacancy.observe(this, Observer { vacancy ->
            vacancy?.let {
                dataBinding.vacancy = it.results?.vacancies?.get(0)?.vacancy
                Log.d(APP_TAG, "observeViewModel retrieve!")
                rootLayout.visibility = View.VISIBLE
            }
        })
        viewModel.loading.observe(this, Observer { loading ->
            loading?.let {
                loadingProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    rootLayout.visibility = View.GONE
                    textViewLoadError.visibility = View.GONE
                }
            }
        })
        viewModel.loadError.observe(this, Observer { loadError ->
            loadError?.let {
                textViewLoadError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

    }


}
