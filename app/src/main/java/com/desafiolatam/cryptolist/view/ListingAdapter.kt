package com.desafiolatam.cryptolist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.desafiolatam.cryptolist.databinding.ItemAssetBinding
import com.desafiolatam.cryptolist.model.db.AssetEntity
import com.squareup.picasso.Picasso


class ListingAdapter : RecyclerView.Adapter<ListingAdapter.AssetVH>() {

    private val assets = mutableListOf<AssetEntity>()
    private val selected = MutableLiveData<AssetEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetVH {
        return AssetVH(ItemAssetBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AssetVH, position: Int) {
        holder.bind(assets[position])
        holder.itemView.setOnClickListener {
            selected.value = assets[position]
        }
    }
    override fun getItemCount() = assets.size
    fun update(newAssets: List<AssetEntity>) {
        assets.clear()
        assets.addAll(newAssets)
        notifyDataSetChanged()
    }

    fun selected(): LiveData<AssetEntity> = selected

    class AssetVH(val binding: ItemAssetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asset: AssetEntity) {
            binding.tvUsdPrice.text = formatDouble(asset.priceUsd.toDouble())
            binding.tvSymbol.text = asset.symbol
            Picasso.get()
                .load(asset.imgSrc)
                .into(binding.imageView)
        }

        fun formatDouble(value: Double): String = String.format("USD $ %.2f", value)
    }
}
