package ru.kpfu.itis.mvicalculate

import io.reactivex.Single
import java.util.concurrent.TimeUnit

private const val FIRST_INDEX = 1
private const val SECOND_INDEX = 2
private const val THIRD_INDEX = 3

class CalculateService {

    fun calculateValue(state: CalculateState): Single<MutableList<Int?>> {
        var result = mutableListOf<Int?>()
        state.apply {
            when (firstLastIndex) {
                FIRST_INDEX -> {
                    result = if (secondLastIndex == 2) {
                        val thirdNumber = firstNumber?.plus(secondNumber ?: 0)
                        mutableListOf(firstNumber, secondNumber, thirdNumber)
                    } else {
                        val secondNumber = thirdNumber?.minus(firstNumber ?: 0)
                        mutableListOf(firstNumber, secondNumber, thirdNumber)
                    }
                }
                SECOND_INDEX -> {
                    result = if (secondLastIndex == 1) {
                        val thirdNumber = firstNumber?.plus(secondNumber ?: 0)
                        mutableListOf(firstNumber, secondNumber, thirdNumber)
                    } else {
                        val firstNumber = thirdNumber?.minus(secondNumber ?: 0)
                        mutableListOf(firstNumber, secondNumber, thirdNumber)
                    }
                }
                THIRD_INDEX -> {
                    result = if (secondLastIndex == 1) {
                        val secondNumber = thirdNumber?.minus(firstNumber ?: 0)
                        mutableListOf(firstNumber, secondNumber, thirdNumber)
                    } else {
                        val firstNumber = thirdNumber?.minus(secondNumber ?: 0)
                        mutableListOf(firstNumber, secondNumber, thirdNumber)
                    }
                }
            }
        }
        return Single.just(result)
            .delay(5, TimeUnit.SECONDS)
    }
}