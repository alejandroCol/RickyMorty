package vass.rickymorty.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import vass.rickymorty.R

@Composable
fun ErrorScreen(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 50.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier.padding(30.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                maxLines = 2,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onClickRetry) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}
