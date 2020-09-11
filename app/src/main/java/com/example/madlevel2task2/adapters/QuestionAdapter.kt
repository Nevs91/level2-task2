package com.example.madlevel2task2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.R
import com.example.madlevel2task2.classes.Question
import com.example.madlevel2task2.databinding.ItemQuestionBinding

class QuestionAdapter(private val questions: List<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemQuestionBinding.bind(itemView)

        fun databind(question: Question) {
            binding.tvQuestion.text = question.question
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}