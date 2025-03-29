package br.alisson.edu.tapago.presentation.tabs.pay

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.alisson.edu.tapago.core.components.ButtonVariant
import br.alisson.edu.tapago.core.components.CustomButton
import br.alisson.edu.tapago.core.components.CustomTextField
import br.alisson.edu.tapago.core.components.TextFieldType
import br.alisson.edu.tapago.utils.Constants.categories
import br.alisson.edu.tapago.utils.formatDateAdapter
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar

data class CategoryType(
    val id: Number,
    val label: String
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayCreateScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    var customCategoryLabel by remember {
        mutableStateOf(categories.first().label)
    }
    var isOpenCategoryModal by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val lazyVerticalGridState = rememberLazyGridState()

    var isOpenDateModal by remember {
        mutableStateOf(false)
    }
    val today = Calendar.getInstance().timeInMillis
    var dateLabel by remember {
        mutableStateOf(
            Instant.ofEpochMilli(today)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT)
        )
    }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today
            }
        }
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(
                    label = "Nome:",
                    type = TextFieldType.DEFAULT,
                    value = "",
                    onValueChange = {},
                )
                CustomTextField(
                    label = "Valor:",
                    type = TextFieldType.DEFAULT,
                    value = "",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {},
                )
                CustomTextField(
                    label = "Descrição:",
                    type = TextFieldType.DEFAULT,
                    value = "",
                    onValueChange = {},
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Categoria:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    OutlinedTextField(
                        value = customCategoryLabel,
                        onValueChange = {},
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.inverseOnSurface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            disabledBorderColor = MaterialTheme.colorScheme.inverseOnSurface
                        ),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Lucide.ChevronRight,
                                contentDescription = "Toggle Password Visibility",
                                tint = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isOpenCategoryModal = true },
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Data de Vencimento:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    OutlinedTextField(
                        value = formatDateAdapter(dateLabel),
                        onValueChange = {},
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.inverseOnSurface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            disabledBorderColor = MaterialTheme.colorScheme.inverseOnSurface
                        ),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Lucide.ChevronRight,
                                contentDescription = "Toggle Password Visibility",
                                tint = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isOpenDateModal = true },
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButton(
                    title = "Registrar",
                    variant = ButtonVariant.DEFAULT,
                    disabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                )
                CustomButton(
                    title = "Limpar",
                    variant = ButtonVariant.MUTED,
                    disabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                )
            }
        }
    }

    if (isOpenCategoryModal) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isOpenCategoryModal = false },
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Categoria",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Selecione a categoria do seu gasto:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontWeight = FontWeight.Normal
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = lazyVerticalGridState,
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categories) { category ->
                        CustomButton(
                            title = category.label,
                            variant = ButtonVariant.MUTED,
                            disabled = false,
                            onClick = {
                                customCategoryLabel = category.label
                                isOpenCategoryModal = false
                            }
                        )
                    }
                }
            }
        }
    }

    if (isOpenDateModal) {
        DatePickerDialog(
            colors = DatePickerColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                headlineContentColor = MaterialTheme.colorScheme.onSurface,
                weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                subheadContentColor = MaterialTheme.colorScheme.onSurface,
                navigationContentColor = MaterialTheme.colorScheme.onSurface,
                yearContentColor = MaterialTheme.colorScheme.onSurface,
                disabledYearContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                currentYearContentColor = MaterialTheme.colorScheme.onSurface,
                selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                disabledSelectedYearContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                selectedYearContainerColor = MaterialTheme.colorScheme.surface,
                disabledSelectedYearContainerColor = MaterialTheme.colorScheme.inverseSurface,
                dayContentColor = MaterialTheme.colorScheme.surface,
                disabledDayContentColor = MaterialTheme.colorScheme.secondary,
                selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                disabledSelectedDayContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                disabledSelectedDayContainerColor = MaterialTheme.colorScheme.surface,
                todayContentColor = MaterialTheme.colorScheme.primary,
                todayDateBorderColor = MaterialTheme.colorScheme.primary,
                dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                dayInSelectionRangeContentColor = MaterialTheme.colorScheme.surface,
                dividerColor = MaterialTheme.colorScheme.inverseOnSurface,
                dateTextFieldColors = TextFieldColors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorTextColor = MaterialTheme.colorScheme.error,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    errorContainerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    errorCursorColor = MaterialTheme.colorScheme.error,
                    textSelectionColors = TextSelectionColors(
                        handleColor = MaterialTheme.colorScheme.primary,
                        backgroundColor = MaterialTheme.colorScheme.surface
                    ),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledIndicatorColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorIndicatorColor = MaterialTheme.colorScheme.error,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorTrailingIconColor = MaterialTheme.colorScheme.error,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorPlaceholderColor = MaterialTheme.colorScheme.error,
                    focusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedSupportingTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledSupportingTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorSupportingTextColor = MaterialTheme.colorScheme.error,
                    focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedPrefixColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledPrefixColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorPrefixColor = MaterialTheme.colorScheme.error,
                    focusedSuffixColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedSuffixColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledSuffixColor = MaterialTheme.colorScheme.inverseOnSurface,
                    errorSuffixColor = MaterialTheme.colorScheme.error
                )
            ),
            onDismissRequest = { isOpenDateModal = false },
            confirmButton = {
                TextButton(onClick = { isOpenDateModal = false }) {
                    Text(
                        text = "Confirmar",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Normal
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isOpenDateModal = false
                }) {
                    Text(
                        text = "Cancelar",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    headlineContentColor = MaterialTheme.colorScheme.tertiary,
                    weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                    subheadContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationContentColor = MaterialTheme.colorScheme.onSurface,
                    yearContentColor = MaterialTheme.colorScheme.onSurface,
                    disabledYearContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    currentYearContentColor = MaterialTheme.colorScheme.onSurface,
                    selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                    disabledSelectedYearContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    selectedYearContainerColor = MaterialTheme.colorScheme.surface,
                    disabledSelectedYearContainerColor = MaterialTheme.colorScheme.inverseSurface,
                    dayContentColor = MaterialTheme.colorScheme.onSurface,
                    disabledDayContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    selectedDayContentColor = MaterialTheme.colorScheme.surface,
                    disabledSelectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                    selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
                    disabledSelectedDayContainerColor = MaterialTheme.colorScheme.surface,
                    todayContentColor = MaterialTheme.colorScheme.primary,
                    todayDateBorderColor = MaterialTheme.colorScheme.primary,
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.surface,
                    dividerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    dateTextFieldColors = TextFieldColors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorTextColor = MaterialTheme.colorScheme.error,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        errorContainerColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        errorCursorColor = MaterialTheme.colorScheme.error,
                        textSelectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.primary,
                            backgroundColor = MaterialTheme.colorScheme.surface
                        ),
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledIndicatorColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorIndicatorColor = MaterialTheme.colorScheme.error,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorLeadingIconColor = MaterialTheme.colorScheme.error,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorTrailingIconColor = MaterialTheme.colorScheme.error,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                        errorLabelColor = MaterialTheme.colorScheme.error,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorPlaceholderColor = MaterialTheme.colorScheme.error,
                        focusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedSupportingTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledSupportingTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorSupportingTextColor = MaterialTheme.colorScheme.error,
                        focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedPrefixColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledPrefixColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorPrefixColor = MaterialTheme.colorScheme.error,
                        focusedSuffixColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedSuffixColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledSuffixColor = MaterialTheme.colorScheme.inverseOnSurface,
                        errorSuffixColor = MaterialTheme.colorScheme.error
                    )
                )
            )

            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let { millis ->
                    dateLabel = Instant.ofEpochMilli(millis)
                        .atOffset(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_INSTANT)
                }
            }
        }
    }

}