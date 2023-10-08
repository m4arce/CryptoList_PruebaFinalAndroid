package com.desafiolatam.cryptolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.desafiolatam.cryptolist.MyViewModel
import com.desafiolatam.cryptolist.databinding.FragmentDetailBinding
import com.desafiolatam.cryptolist.model.db.AssetEntity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date

private const val ARG_PARAM1 = "param1"
/**
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DetailFragment: Fragment() {

    private var assetId: String? = null
    private lateinit var binding : FragmentDetailBinding
    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            assetId = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        assetId?.apply {
            binding.tvName.text = this
            viewModel.retrieveAssetDetail(this)
            viewModel.getAsset(this).observe(viewLifecycleOwner) {
                // Actualiza la UI con el detalle del activo
                showDetail(it)
            }
        }
        return binding.root
    }

    private fun showDetail(asset: AssetEntity) {
        binding.tvName.text = asset.name
        binding.tvSymbol.text = asset.symbol
        binding.tvTimestamp.text = convertFromEpoch(asset.timestamp)
        Picasso.get()
            .load(asset.imgSrc)
            .into(binding.ivAssetDetail)
        //binding.ivAssetDetail.load(asset.imgSrc)
        binding.tvPrice.text = formatDouble(asset.priceUsd.toDouble())
        binding.tvSupply.text = asset.supply
        binding.tvMarketCap.text = asset.marketCapUsd
    }

    private fun convertFromEpoch(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss.SS")
        return sdf.format(Date(timestamp))
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Asset ID.
         * @return A new instance of fragment DetailFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
    fun formatDouble(value: Double): String = String.format("USD $ %.2f", value)
}
