package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.adapters.QuestionAdapter
import com.example.madlevel2task2.classes.Question
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    /**
     * Method for setting up the views for the application
     */
    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        binding.rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter
        binding.rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        // Populate the places list and notify the data set has changed.
        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.QUESTION_ANSWERS[i]))
        }

        questionAdapter.notifyDataSetChanged()

        createItemTouchHelper().attachToRecyclerView(binding.rvQuestions)
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                // Check if the answer is correct by checking the swiped direction
                val correct = checkAnswerForQuestion(position, direction == ItemTouchHelper.RIGHT)

                // Remove the question when answered correctly, otherwise put it back on its place
                if (correct) {
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                    Snackbar.make(binding.rvQuestions, R.string.correct, Snackbar.LENGTH_SHORT).show()
                } else {
                    questionAdapter.notifyItemChanged(position)
                    Snackbar.make(binding.rvQuestions, R.string.incorrect, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        return ItemTouchHelper(callback)
    }

    /**
     * Check and return if the given answer is correct for the question on the provided position.
     */
    private fun checkAnswerForQuestion(questionPosition: Int, answer: Boolean): Boolean {
        return questions[questionPosition].answer == answer
    }
}