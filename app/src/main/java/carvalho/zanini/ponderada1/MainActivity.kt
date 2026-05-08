package carvalho.zanini.ponderada1

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LancadorDeDadosApp()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LancadorDeDadosApp() {
    var dadoSelecionado by remember { mutableStateOf("D6") }
    var valorSorteado  by remember { mutableStateOf(0) }
    val dados = listOf("D6", "D10", "D20", "D100")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Lançador de Dados",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Escolha o tipo de dado:")

        dados.forEach { dado ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = dadoSelecionado == dado,
                    onClick = { dadoSelecionado = dado; valorSorteado = 0 }
                )
                Text(text = dado)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                valorSorteado = when (dadoSelecionado) {
                    "D6" -> Random.nextInt(1, 7)
                    "D10" -> Random.nextInt(1, 11)
                    "D20" -> Random.nextInt(1, 21)
                    "D100" -> Random.nextInt(1, 101)
                    else -> 0
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Lançar dado")
        }

        Spacer(modifier = Modifier.height(24.dp))
        DiceImage(valorSorteado.toString(), dadoSelecionado)
    }
}

@Composable
fun DiceImage(value: String, dice: String, modifier: Modifier = Modifier) {
    val dict = mapOf("D10" to R.drawable.d10, "D6" to R.drawable.d6, "D20" to R.drawable.d20, "D100" to R.drawable.d100)
    val image = painterResource(dict.get(dice)!!)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image,
            contentDescription = null
        )
        Text(
            text = value,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

