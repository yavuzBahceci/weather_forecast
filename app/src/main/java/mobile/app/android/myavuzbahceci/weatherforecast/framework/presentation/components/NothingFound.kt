package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NothingFound(){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.align(Alignment.Center)){
            Text(
                modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                text = "¯\\_(ツ)_/¯",
                style = TextStyle(fontSize = 50.sp)
            )
            Text(
                modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                text = "There's nothing here",
                style = MaterialTheme.typography.h4
            )
        }

    }
}