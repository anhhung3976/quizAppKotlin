package com.example.quizapptonghopvabutton

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapptonghopvabutton.DBhelper.DBHelperOther
import com.example.quizapptonghopvabutton.Model.Question
import com.example.quizapptonghopvabutton.common.Common1
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import java.util.ArrayList

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var clickedButton: Button? = null
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var btn4: Button? = null
    private var buttonConfirmNext: Button? = null

    private var textViewQuestion: TextView? = null//text view câu hỏi
    private var textViewScore: TextView? = null//text view  điểm
    private var textViewQuestionCount: TextView? = null//text view số câu hiện tại
    private var textViewCountDown: TextView? = null//text view tổng số câu hỏi
    private var textViewCorrect: TextView? = null//text view câu đúng
    private var textViewWrong: TextView? = null//text view câu sai

    private var questionsList: ArrayList<Question>? = null//danh sách câu hỏi
    private var questionCounter: Int = 0//số câu đã trả lời
    private var questionTotalCount: Int = 0//tổng số câu hỏi
    private var currentQuestion: Question? = null
    private var answer: Boolean = false

    private val handler = Handler()
    private var correctAns : Int  = 0
    private var wrongAns : Int  = 0
    var score: Int =0
    private var index : Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //ánh xạ
        setupUI()

        //hàm lấy danh sách câu hỏi theo lớp đã chọn
        genQuestion()

    }

    private fun setupUI() {
        textViewQuestion = findViewById(R.id.txtQuestionContainer)
        textViewCorrect = findViewById(R.id.txtCorrect)
        textViewWrong = findViewById(R.id.txtWrong)
        textViewCountDown = findViewById(R.id.txtViewTimer)
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion)
        textViewScore = findViewById(R.id.txtScore)

        buttonConfirmNext = findViewById(R.id.button)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)


    }

    //Lúc khởi chạy lần đầu: genQuestion->startQuiz->showQuestion->quizOperation->onClick->showQuestion
    //Sau khởi chạy lần đầu và chọn đúng showQuestion->quizOperation->onClick->showQuestion
    //Sau khởi chạy lần đầu và chọn sai  showQuestion->quizOperation->onClick->changeIncorrectColor-> showQuestion
    //tiếp tục như thế cho tới khi hết tổng câu hỏi thì dừng


    //khi đến hết câu hỏi trong mục
//    genQuestion->startQuiz->showQuestion->quizOperation->onClick->showQuestion->finalResult

    private fun genQuestion() {

        //lấy danh sách câu hỏi dựa theo lớp đã chọn
        questionsList = DBHelperOther.getInstance(this).getAllQuestionByGrade(Common1.selectedCategoryId!!.id)

        //nếu không có câu hỏi nào của mục được chọn thì
        if(questionsList!!.size==0) {
            MaterialStyledDialog.Builder(this)
                .setTitle("Oops")
                .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                .setDescription("Don't have any Question in this ${Common1.selectedCategoryId!!.name} category")
                .setPositiveText("Ok")
                .onPositive{dialog, which ->
                    dialog.dismiss()
                    finish()

                }.show()
        }

        //bắt đầu chương trình
        if(questionsList!!.size !=0)
        startQuiz()
    }

    private fun startQuiz() {
        questionTotalCount = questionsList!!.size
//        Collections.shuffle(questionsList)//random câu hỏi

        showQuestions(index)
        if (!answer) {
            quizOperation()
        }


    }

    private fun showQuestions(index : Int) {
        btn1!!.setEnabled(true)
        btn2!!.setEnabled(true)
        btn3!!.setEnabled(true)
        btn4!!.setEnabled(true)

        btn1!!.setTextColor(Color.BLACK)
        btn2!!.setTextColor(Color.BLACK)
        btn3!!.setTextColor(Color.BLACK)
        btn4!!.setTextColor(Color.BLACK)

        btn1!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
        btn2!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
        btn3!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
        btn4!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))


        //nếu số câu hỏi hiện tại < tổng số câu hỏi(VD 2(số câu đã trả lời)<6(tổng số câu hỏi))
        if (index < questionTotalCount) {
            currentQuestion = questionsList!!.get(questionCounter)
            textViewQuestion!!.setText(currentQuestion!!.getQuestion())
            btn1!!.setText(currentQuestion!!.getOption1())
            btn2!!.setText(currentQuestion!!.getOption2())
            btn3!!.setText(currentQuestion!!.getOption3())
            btn4!!.setText(currentQuestion!!.getOption4())

            questionCounter++
            answer = false //chưa chọn câu trả lời
            buttonConfirmNext!!.setText("Confirm")
            textViewQuestionCount!!.setText("Question :$questionCounter/$questionTotalCount")

        } else {
            // không cho chương trình chạy quá nhanh mà ko thấy rõ hiệu ứng
            handler.postDelayed({
                finalResult()
            }, 1000)
        }
    }

    private fun quizOperation() {
        answer = true

        //xử lý tập trung cho các nút
        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
        btn3!!.setOnClickListener(this)
        btn4!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clickedButton = v as Button //nút được click(nếu button 1 được click thì clickedButton là button1,tương tự cho button khác

       //sau khi chọn thì không cho chọn nữa để tránh lỗi chương trình
        btn1!!.setEnabled(false)
        btn2!!.setEnabled(false)
        btn3!!.setEnabled(false)
        btn4!!.setEnabled(false)

        //so sánh text của button được chọn với text của button chứa câu trả lời đúng
        if (clickedButton!!.getText() == questionsList!!.get(index).getAnswerNr()) {
            clickedButton!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            correctAns++
            score += 10
            textViewScore!!.setText("Score: $score")
            textViewCorrect!!.setText("Correct: $correctAns")


            handler.postDelayed({
                clickedButton!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.button_background))
                showQuestions(++index)
            }, 2000)

        } else {

            //nếu đáp án 1 là đáp án đúng
            if (btn1!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn1!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            }

            //nếu đáp án 2 là đáp án đúng
            if (btn2!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn2!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            }

            //nếu đáp án 3 là đáp án đúng
            if (btn3!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn3!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))

            }

            //nếu đáp án 4 là đáp án đúng
            if (btn4!!.getText() == questionsList!!.get(index).getAnswerNr()) {
                btn4!!.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.when_answer_correct))
            }

            //đổi màu nút được chọn nếu sai
            changeIncorrectColor(clickedButton!!)

            wrongAns++
            textViewWrong!!.setText("Wrong:$wrongAns")
            clickedButton!!.setEnabled(false)

            handler.postDelayed({
                clickedButton!!.setBackground(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.button_background
                    )
                )
                showQuestions(++index)
            }, 2000)

        }
    }

    //nếu trả lời sai thì app dừng ngay và hiển thị điểm đã đạt được
    private fun changeIncorrectColor(btnSelected: Button) {
        btnSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_wrong))
        btnSelected.setTextColor(Color.WHITE)
    }

    //kết quả cuối cùng
    private fun finalResult() {
        val resultData = Intent(this@QuestionActivity, Result::class.java)
        resultData.putExtra("UserScore", score)
        resultData.putExtra("CorrectQues", correctAns)
        startActivity(resultData)
    }
}