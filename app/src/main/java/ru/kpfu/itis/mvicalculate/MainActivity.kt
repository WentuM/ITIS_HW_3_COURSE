package ru.kpfu.itis.mvicalculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.freeletics.rxredux.reduxStore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import ru.kpfu.itis.mvicalculate.databinding.ActivityMainBinding

private const val FIRST_INDEX = 1
private const val SECOND_INDEX = 2
private const val THIRD_INDEX = 3

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val actionRelay = PublishSubject.create<CalculateAction>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        init(listOf(CountHelper(CalculateService())))
        initTextChanged()
    }

    private fun init(sideEffects: List<CalculateSideEffect>) {
        val state = actionRelay.reduxStore(
            CalculateState(),
            sideEffects,
            CalculateState()::reduce
        )
        subscribe(state)
    }

    private fun subscribe(state: Observable<CalculateState>) {
        state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::render)
    }

    private fun calculateNumber(inputNumber: String, index: Int) {
        actionRelay.onNext(CalculateAction.ShowNumberByIndex(inputNumber.toInt(), index))
    }

    private fun initTextChanged() {
        binding.firstNumberEditText.doAfterTextChanged {
            calculateNumber(it.toString(), FIRST_INDEX)
        }
        binding.secondNumberEditText.doAfterTextChanged {
            calculateNumber(it.toString(), SECOND_INDEX)
        }
        binding.thirdNumberEditText.doAfterTextChanged {
            calculateNumber(it.toString(), THIRD_INDEX)
        }
    }

    private fun render(state: CalculateState) {
        with(binding) {
            state.firstNumber?.let {
                firstNumberEditText.setText(it.toString())
            }
            state.secondNumber?.let {
                secondNumberEditText.setText(it.toString())
            }
            state.thirdNumber?.let {
                thirdNumberEditText.setText(it.toString())
            }
            progressBar.isVisible = state.isLoading
        }
    }
}