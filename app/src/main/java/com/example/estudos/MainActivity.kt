package com.example.estudos
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

data class Produto(val nome: String, val preco: Double)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val produtos = listOf<Produto>(Produto("Shampoo", 10.50), Produto("Sabonete", 2.50))
            var totalComprados by remember { mutableIntStateOf(0) }

            Column(
                modifier = Modifier.fillMaxSize().padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "Produtos Comprados: $totalComprados/${produtos.size}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                ProdutoCard(produtos){comprado ->
                    if (comprado) totalComprados++ else totalComprados--
                }
            }
        }
    }
}

@Composable
fun ProdutoCard(produtos: List<Produto>, onCompraChange: (Boolean) -> Unit) {
    Column(modifier = Modifier.padding(5.dp, 5.dp)) {
        for(produto in produtos) {
            var comprado by remember { mutableStateOf(false) } // estado do produto

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ){
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Produto: ${produto.nome}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Preço: R$ %.2f".format(produto.preco),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Button(onClick = {
                        comprado = !comprado
                        onCompraChange(comprado)
                    }) {
                        Text(if (comprado) "Comprado ✅" else "Comprar 🛒")
                    }
                }
            }
        }
    }
}