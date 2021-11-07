package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.GenericDialogInfo
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components.PositiveAction
import java.util.*

class DialogQueue {

    // FIFO
    val dialogQueue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    private fun removeMessage(){
        if (dialogQueue.value.isNotEmpty()) {
            val update = dialogQueue.value
            update.remove()
            dialogQueue.value = ArrayDeque() // force recompose (bug?)
            dialogQueue.value = update
        }
    }

    fun addDialogMessage(title: String, description: String){
        dialogQueue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeMessage)
                .description(description)
                .positive(
                    PositiveAction(
                        positiveBtnTxt = "Ok",
                        onPositiveAction = this::removeMessage,
                    )
                )
                .build()
        )
    }
}