package com.dineshdk.workoutapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.dineshdk.workoutapplication.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val itemList:ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = itemList[position]
        holder.bind(exercise)
    }

    class ViewHolder(val binding: ItemExerciseStatusBinding,):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ex: Exercise): Unit {
            binding.tvItem.text = ex.getId().toString()

            when{
                ex.getIsSelected() -> {
                   binding.tvItem.background = getDrawable(
                        itemView.context,R.drawable.item_circuler_thin_color_accent_border)
                }
                ex.getIsCompleted() ->{
                    binding.tvItem.background = getDrawable(
                            itemView.context, R.drawable.item_circuler_color_accent_background)
                    binding.tvItem.setTextColor(getColor(itemView.context,R.color.white))

                }
                else ->{
                    binding.tvItem.background = getDrawable(
                        itemView.context, R.drawable.item_circuler_color_gray_background)
                }
            }
        }

    }
}