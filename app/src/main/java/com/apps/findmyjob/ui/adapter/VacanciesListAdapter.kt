package com.apps.findmyjob.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.apps.findmyjob.R
import com.apps.findmyjob.databinding.ItemVacancyBinding
import com.apps.findmyjob.model.VacanciesList
import com.apps.findmyjob.ui.ListFragmentDirections
import kotlinx.android.synthetic.main.item_vacancy.view.*

class VacanciesListAdapter(private val vacanciesList : ArrayList<VacanciesList?>) : RecyclerView.Adapter<VacanciesListAdapter.VacanciesViewHolder>(),
    VacancyClickListener {

    fun updateVacanciesList(newVacanciesList: List<VacanciesList?>) {
        vacanciesList.clear()
        vacanciesList.addAll(newVacanciesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesViewHolder {
        val view = DataBindingUtil.inflate<ItemVacancyBinding>(LayoutInflater.from(parent.context), R.layout.item_vacancy, parent, false)
        return VacanciesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vacanciesList.size
    }

    override fun onBindViewHolder(holder: VacanciesViewHolder, position: Int) {
        holder.view.vacancy = vacanciesList[position]?.vacancy
        holder.view.vacancyClickListener = this
    }

    class VacanciesViewHolder(var view: ItemVacancyBinding) : RecyclerView.ViewHolder(view.root)

    override fun onVacancyClicked(v: View) {
        val action = ListFragmentDirections.actionListOfResultsFragmentToDetailFragment()
        action.vacancyId = v.vacancyId.text.toString()
        action.companyCode = v.companyCode.text.toString()
        Navigation.findNavController(v).navigate(action)
    }

}