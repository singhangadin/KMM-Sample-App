package com.github.angads25.kmmsampleapp.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.angads25.kmmsampleapp.android.databinding.ListItemImageBinding
import com.github.angads25.kmmsampleapp.data.Hits

class ImageListAdapter: RecyclerView.Adapter<ImageListAdapter.ImageItemViewHolder>() {
    val imageList = ArrayList<Hits>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        val binding = ListItemImageBinding.inflate(LayoutInflater.from(parent.context))
        return ImageItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(imageList[position].previewURL)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(holder.imageView)
            .clearOnDetach()

        holder.imageTitle.text = "Photo by ${imageList[position].user}"
    }

    fun addImages(items: List<Hits>) {
        val sizeBefore = imageList.size
        imageList.addAll(items)
        notifyItemRangeChanged(sizeBefore, items.size)
    }

    override fun getItemCount(): Int = imageList.size

    class ImageItemViewHolder(binding: ListItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        var imageView: AppCompatImageView = binding.imageView
        var imageTitle: AppCompatTextView = binding.imageTitle
    }
}