package com.desafiolatam.cryptolist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.desafiolatam.cryptolist.MyViewModel
import com.desafiolatam.cryptolist.R
import com.desafiolatam.cryptolist.databinding.FragmentListingBinding


class ListingFragment: Fragment() {

    private val viewModel: MyViewModel by activityViewModels()
    lateinit var binding: FragmentListingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListingBinding.inflate(inflater)
        val adapter = ListingAdapter()
        binding.assetsList.layoutManager = GridLayoutManager(context, 1)
        binding.assetsList.adapter = adapter
        viewModel.getAssets().observe(viewLifecycleOwner) {
            it?.let { adapter.update(it) }
        }
        adapter.selected().observe(viewLifecycleOwner) {
            activity?.apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(it.id))
                    .addToBackStack("detail").commit()
            }
        }
        binding.editTextTextPersonName.doOnTextChanged { text, _, _, _ ->
            saveInPreferences(text.toString())
        }
        getFromPreferences().let {
            binding.editTextTextPersonName.setText(it)
        }
        return binding.root
    }
    private fun saveInPreferences(text: String) {
        context?.let {
            val editor = it.getSharedPreferences("userPrefs", Context.MODE_PRIVATE).edit()
            editor.putString("username", text)
            editor.commit()
        }

    }
    private fun getFromPreferences(): String {
        val prefs = context?.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        return prefs?.getString("username", "Usuario") ?: ""
    }
}

