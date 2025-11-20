package com.example.tipscalculator

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipscalculator.ui.theme.TipsCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipsCalculatorTheme {
                TipCalculatorPreview()
            }
        }
    }
}



@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.bill),
                contentDescription = null,
            )
        },
        label = { Text(stringResource(R.string.bill_amount)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
    Spacer(modifier = Modifier.height(20.dp))
}
@Composable
fun EnterTipPercentage(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.percent),
                contentDescription = null,
            )
        },
        label = { Text(stringResource(R.string.how_was_the_service)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )

}

@Composable
fun TipCalculator() {
    var amountInput by remember { mutableStateOf("") }
    var percentageInput by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val percent = percentageInput.toDoubleOrNull() ?: 15.0
    val tip = calculateTip(amount,percent,isChecked)




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.calculate_tip),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        EditNumberField(
            value = amountInput,
            onValueChange = { amountInput = it },

        )

        EnterTipPercentage(
            value = percentageInput,
            onValueChange = {percentageInput = it},
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(130.dp),
            modifier = Modifier
                .clickable { isChecked = !isChecked }
                .padding(10.dp)
        ) {
            Text(
                stringResource(R.string.round_up_tip)
            )
            Switch(
                checked = isChecked,
                onCheckedChange = {isChecked = it },
            )
        }

        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            fontSize = 16.sp,
            color = Color(0xFF212425)
        )


    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0, isChecked: Boolean): String {
    var tip = tipPercent / 100 * amount
    when(isChecked ){
        false -> return NumberFormat.getCurrencyInstance().format(tip)
        else -> {
            tip = tip + amount
            return NumberFormat.getCurrencyInstance().format(tip)
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipCalculatorPreview() {
    TipsCalculatorTheme {
        TipCalculator()
    }
}