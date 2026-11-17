package com.example.zadanie1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zadanie1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions = arrayOf(
        Question(R.string.q1, true),
        Question(R.string.q2, false),
        Question(R.string.q3, false),
        Question(R.string.q4, true),
        Question(R.string.q5, true)
    )

    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var currentQuestionAnswered = false
    private var answerWasShown = false

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            answerWasShown = result.data
                ?.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false) ?: false
            Log.d(TAG, "answer was shown: $answerWasShown")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Wywołanie metody onCreate")
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.trueButton.setOnClickListener {
            checkAnswerCorrectness(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswerCorrectness(false)
        }

        binding.hintButton.setOnClickListener {
            val intent = Intent(this, PromptActivity::class.java)
            intent.putExtra(KEY_EXTRA_ANSWER, questions[currentQuestionIndex].trueAnswer)
            startForResult.launch(intent)
        }

        binding.nextButton.setOnClickListener {
            if (currentQuestionIndex == questions.lastIndex) {
                resetQuiz()
            } else {
                currentQuestionIndex++
                currentQuestionAnswered = false
                answerWasShown = false
                setNextQuestion()
            }
        }

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION)
            correctAnswersCount = savedInstanceState.getInt(KEY_CORRECT_ANSWERS)
            currentQuestionAnswered = savedInstanceState.getBoolean(KEY_ANSWERED)
            answerWasShown = savedInstanceState.getBoolean(KEY_HINT_SHOWN)
        }

        setNextQuestion()
    }

    private fun checkAnswerCorrectness(userAnswer: Boolean) {
        if (currentQuestionAnswered) {
            Toast.makeText(this, R.string.already_answered, Toast.LENGTH_SHORT).show()
            return
        }
        currentQuestionAnswered = true

        val isCorrect = userAnswer == questions[currentQuestionIndex].trueAnswer
        val messageId = when {
            answerWasShown -> R.string.answer_was_shown
            isCorrect -> {
                correctAnswersCount++
                R.string.correct_answer
            }
            else -> R.string.incorrect_answer
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()

        if (currentQuestionIndex == questions.lastIndex) {
            showResult()
        }
    }

    private fun showResult() {
        AlertDialog.Builder(this)
            .setTitle(R.string.result_title)
            .setMessage(getString(R.string.result_message, correctAnswersCount, questions.size))
            .setPositiveButton(android.R.string.ok, null)
            .setOnDismissListener { resetQuiz() }
            .show()
    }

    private fun resetQuiz() {
        correctAnswersCount = 0
        currentQuestionAnswered = false
        answerWasShown = false
        currentQuestionIndex = 0
        setNextQuestion()
    }

    private fun setNextQuestion() {
        binding.questionTextView.setText(questions[currentQuestionIndex].questionId)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Wywołanie metody onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Wywołanie metody onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Wywołanie metody onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Wywołanie metody onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Wywołanie metody onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Wywołanie metody onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "Wywołanie metody onSaveInstanceState, current question $currentQuestionIndex")
        outState.putInt(KEY_CURRENT_QUESTION, currentQuestionIndex)
        outState.putInt(KEY_CORRECT_ANSWERS, correctAnswersCount)
        outState.putBoolean(KEY_ANSWERED, currentQuestionAnswered)
        outState.putBoolean(KEY_HINT_SHOWN, answerWasShown)
    }

    companion object {
        const val KEY_EXTRA_ANSWER = "correctAnswer"
        private const val TAG = "MainActivity"
        private const val KEY_CURRENT_QUESTION = "currentQuestion"
        private const val KEY_CORRECT_ANSWERS = "correctAnswers"
        private const val KEY_ANSWERED = "currentQuestionAnswered"
        private const val KEY_HINT_SHOWN = "answerWasShown"
    }
}
