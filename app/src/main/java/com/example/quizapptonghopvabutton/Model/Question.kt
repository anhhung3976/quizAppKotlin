package com.example.quizapptonghopvabutton.Model

class Question {
    private var id: Int = 0
    private var question: String? = null
    private var option1: String? = null
    private var option2: String? = null
    private var option3: String? = null
    private var option4: String? = null
    private var answerNr: String? = null
    private var option5: Int = 0
    private var option6: String? = null

    constructor()

    constructor(id: Int, question: String, option1: String, option2: String, option3: String, option4: String, answerNr: String, option5: Int, option6: String) {
        this.id = id
        this.question = question
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.option4 = option4
        this.answerNr = answerNr
        this.option5 = option5
        this.option6 = option6
    }
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getQuestion(): String {
        return question!!
    }

    fun setQuestion(question: String) {
        this.question = question
    }

    fun getOption1(): String {
        return option1!!
    }

    fun setOption1(option1: String) {
        this.option1 = option1
    }

    fun getOption2(): String {
        return option2!!
    }

    fun setOption2(option2: String) {
        this.option2 = option2
    }

    fun getOption3(): String {
        return option3!!
    }

    fun setOption3(option3: String) {
        this.option3 = option3
    }

    fun getOption4(): String {
        return option4!!
    }

    fun setOption4(option4: String) {
        this.option4 = option4
    }

    fun getAnswerNr(): String {
        return answerNr!!
    }

    fun setAnswerNr(answerNr: String) {
        this.answerNr = answerNr
    }

    fun getOption5(): Int {
        return option5
    }

    fun setOption5(option5: Int) {
        this.option5 = option5
    }

    fun getOption6(): String {
        return option6!!
    }

    fun setOption6(option6: String) {
        this.option6 = option6
    }

}