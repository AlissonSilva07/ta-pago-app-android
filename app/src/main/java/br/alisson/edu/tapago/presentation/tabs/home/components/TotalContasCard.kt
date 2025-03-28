package br.alisson.edu.tapago.presentation.tabs.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.R
import br.alisson.edu.tapago.domain.model.TotalExpense
import br.alisson.edu.tapago.presentation.ui.theme.TaPagoTheme
import br.alisson.edu.tapago.utils.getMonthInPortuguese
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TotalContasCard(
    totalExpense: List<TotalExpense>
) {
    val currentIndex = rememberSaveable { mutableIntStateOf(totalExpense.size - 1) }

    val currentMonth = totalExpense[currentIndex.intValue]

    fun goToPreviousMonth() {
        if (currentIndex.intValue > 0) {
            currentIndex.intValue -= 1
        }
    }

    fun goToNextMonth() {
        if (currentIndex.intValue < totalExpense.size - 1) {
            currentIndex.intValue += 1
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(0.8f)
                .height(120.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ad1),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "R$ ${currentMonth.total}",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Total de contas em ${getMonthInPortuguese(currentMonth.month)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .weight(0.2f)
                .height(120.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val previousButtonTint = if (currentIndex.intValue > 0) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.inverseSurface
                }

                val nextButtonTint = if (currentIndex.intValue < totalExpense.size - 1) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.inverseSurface
                }

                IconButton(
                    onClick = { goToPreviousMonth() },
                    enabled = currentIndex.intValue > 0,
                    content = {
                        Icon(
                            imageVector = Lucide.ChevronUp,
                            contentDescription = null,
                            tint = previousButtonTint
                        )
                    }
                )
                Text(
                    text = getMonthInPortuguese(currentMonth.month).take(3),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { goToNextMonth() },
                    enabled = currentIndex.intValue < totalExpense.size - 1,
                    content = {
                        Icon(
                            imageVector = Lucide.ChevronDown,
                            contentDescription = null,
                            tint = nextButtonTint
                        )
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun TotalContasPrev() {
    TaPagoTheme {
        TotalContasCard(
            totalExpense = mutableListOf(
                TotalExpense(
                    month = "2025-02",
                    total = 800
                ),
                TotalExpense(
                    month = "2025-03",
                    total = 1000
                ),
                TotalExpense(
                    month = "2025-04",
                    total = 1200
                )
            )
        )
    }
}