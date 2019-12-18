package com.apps.findmyjob.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.apps.findmyjob.R
import com.apps.findmyjob.util.APP_TAG
import com.apps.findmyjob.util.pageLimit
import com.apps.findmyjob.util.pageOffset
import com.apps.findmyjob.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list_of_results.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private var userInputText = ""
    private var regionId = ""

    private val vacanciesListAdapter = VacanciesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_of_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            userInputText = ListFragmentArgs.fromBundle(it).userInputText
            regionId = ListFragmentArgs.fromBundle(it).regionId
        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        recyclerViewVacanciesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vacanciesListAdapter
        }

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recyclerViewVacanciesList.visibility = View.GONE
            textViewLoadError.visibility = View.GONE
            loadingProgressBar.visibility = View.VISIBLE
            viewModel.refresh(regionId, userInputText, pageOffset, pageLimit)
            refreshLayout.isRefreshing = false
        }

        // download data from endpoint
        viewModel.refresh(regionId, userInputText, pageOffset, pageLimit)

    }

    private fun observeViewModel() {
        viewModel.vacancies.observe(this, Observer { response ->
            response?.let {
                val list = it.results?.vacancies
                if (list?.get(0) != null) {
                    val vacanciesList = response.results?.vacancies
                    vacanciesList?.let {
                        Log.d(APP_TAG, "найдено: ${it.size} или более вакансий в городе $regionId")
                        recyclerViewVacanciesList.visibility = View.VISIBLE
                        textViewEmptyResponse.visibility = View.GONE
                        vacanciesListAdapter.updateVacanciesList(it)
                    }

                } else {
                    textViewEmptyResponse.visibility = View.VISIBLE
                    Log.d(APP_TAG, "не найдено записей")
                }
            }
        })
        viewModel.loading.observe(this, Observer { loading ->
            loading?.let {
                loadingProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    recyclerViewVacanciesList.visibility = View.GONE
                    textViewLoadError.visibility = View.GONE
                    textViewEmptyResponse.visibility = View.GONE
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
