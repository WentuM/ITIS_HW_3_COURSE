package ru.kpfu.itis.mvicalculate

import com.freeletics.rxredux.StateAccessor
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType

class CountHelper(
    private val service: CalculateService,
) : CalculateSideEffect {

    override fun invoke(
        actions: Observable<CalculateAction>,
        calculateState: StateAccessor<CalculateState>
    ): Observable<out CalculateAction> {
        return actions.ofType<CalculateAction.ShowNumberByIndex>()
            .switchMap { action ->
                calculateAndUpdate(action.number, action.index, calculateState())
                    .map<CalculateAction> { calculatedValues ->
                        CalculateAction.CompleteCalculateCalculateAction(
                            calculatedValues[0],
                            calculatedValues[1],
                            calculatedValues[2]
                        )
                    }
                    .toObservable()
                    .startWith(CalculateAction.LoadCalculateCalculateAction)
            }
    }

    private fun calculateAndUpdate(
        newCount: Int?,
        newIndex: Int,
        state: CalculateState
    ): Single<MutableList<Int?>> {
        when (newIndex) {
            1 -> state.firstNumber = newCount
            2 -> state.secondNumber = newCount
            3 -> state.thirdNumber = newCount
        }
        if (newIndex == state.firstLastIndex) {
            return service.calculateValue(state)
        }
        state.secondLastIndex = state.firstLastIndex
        state.firstLastIndex = newIndex
        return service.calculateValue(state)
    }
}