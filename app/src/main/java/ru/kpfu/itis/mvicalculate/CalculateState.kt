package ru.kpfu.itis.mvicalculate

data class CalculateState(
    var isLoading: Boolean = false,
    var firstNumber: Int? = null,
    var secondNumber: Int? = null,
    var thirdNumber: Int? = null,
    var firstLastIndex: Int = 0,
    var secondLastIndex: Int = 0
) {
    fun reduce(calculateState: CalculateState, calculateAction: CalculateAction): CalculateState {
        return when (calculateAction) {
            is CalculateAction.LoadCalculateCalculateAction -> calculateState.copy(isLoading = true)
            is CalculateAction.CompleteCalculateCalculateAction -> calculateState.copy(
                isLoading = false,
                firstNumber = calculateState.firstNumber,
                secondNumber = calculateState.secondNumber,
                thirdNumber = calculateState.thirdNumber
            )
            is CalculateAction.ShowNumberByIndex -> {
                calculateState.apply {
                    when (calculateAction.index) {
                        1 -> firstNumber = calculateAction.number
                        2 -> secondNumber = calculateAction.number
                        3 -> thirdNumber = calculateAction.number
                    }
                    if (firstLastIndex != calculateAction.index) {
                        secondLastIndex = firstLastIndex
                        firstLastIndex = calculateAction.index
                    }
                    copy(
                        firstNumber = firstNumber,
                        secondNumber = secondNumber,
                        thirdNumber = thirdNumber,
                        firstLastIndex = firstLastIndex,
                        secondLastIndex = secondLastIndex
                    )
                }
            }
        }
    }
}