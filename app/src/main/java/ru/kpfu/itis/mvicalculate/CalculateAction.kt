package ru.kpfu.itis.mvicalculate

sealed class CalculateAction {
    class ShowNumberByIndex(val number: Int?, val index: Int) : CalculateAction()
    class CompleteCalculateCalculateAction(
        val firstNumber: Int?,
        val secondNumber: Int?,
        val thirdNumber: Int?
    ) : CalculateAction()

    object LoadCalculateCalculateAction : CalculateAction()
}