package com.example.zadanie1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zadanie1.databinding.ActivityPromptBinding

class PromptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPromptBinding
    private var correctAnswer = false
    private var answerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPromptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        correctAnswer = intent.getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, false)

        if (savedInstanceState != null) {
            answerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false)
            if (answerShown) {
                showAnswerText()
                setAnswerShownResult(true)
            }
        }

        binding.showAnswerButton.setOnClickListener {
            answerShown = true
            showAnswerText()
            setAnswerShownResult(true)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun showAnswerText() {
        val answerId = if (correctAnswer) R.string.button_true else R.string.button_false
        binding.answerTextView.setText(answerId)
    }

    private fun setAnswerShownResult(answerShown: Boolean) {
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerShown)
        setResult(RESULT_OK, resultIntent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, answerShown)
    }

    companion object {
        const val KEY_EXTRA_ANSWER_SHOWN = "answerShown"
        private const val KEY_ANSWER_SHOWN = "answer_shown"
    }
}
