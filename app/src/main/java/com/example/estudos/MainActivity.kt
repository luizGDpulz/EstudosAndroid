package com.example.estudos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.estudos.ui.theme.EstudosTheme
import com.example.estudos.ui.theme.surfaceCardHighlightsDark
import com.example.estudos.ui.theme.surfaceContainerDark

data class Produto(val nome: String, val preco: Double)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstudosTheme {
                val produtos = listOf(
                    Produto("Shampoo", 10.50),
                    Produto("Sabonete", 2.50),
                    Produto("Condicionador", 12.00),
                    Produto("Escova de Dentes", 5.00),
                    Produto("Pasta de Dentes", 4.50),
                    Produto("Creme Hidratante", 15.00),
                    Produto("Perfume", 50.00),
                    Produto("Desodorante", 8.00),
                    Produto("Sabonete Líquido", 6.50),
                    Produto("Loção Corporal", 18.00),
                    Produto("Máscara Facial", 20.00),
                    Produto("Protetor Solar", 25.00),
                    Produto("Gel de Banho", 7.50),
                    Produto("Cotonete", 3.00),
                    Produto("Algodão", 2.00),
                    Produto("Esponja de Banho", 4.00),
                    Produto("Sabonete Esfoliante", 9.50),
                    Produto("Loção Pós-Barba", 14.00),
                    Produto("Sabonete Infantil", 5.50),
                    Produto("Óleo Corporal", 22.00)
                )
                var totalComprados: Int by remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .padding(bottom = 20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        Text(
                            text = "Produtos Comprados: $totalComprados/${produtos.size}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    ProdutoCard(produtos) { comprado ->
                        if (comprado) totalComprados++ else totalComprados--
                    }
                }
            }
        }
    }
}

@Composable
fun ProdutoCard(produtos: List<Produto>, onCompraChange: (Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(bottom = 16.dp), // espaço no fim da lista
        verticalArrangement = Arrangement.spacedBy(10.dp) // espaço entre itens
    ) {
        items(produtos, key = {produto -> produto.nome}) { produto ->
            var comprado by remember { mutableStateOf(false) } // estado do produto
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (comprado)
                        surfaceCardHighlightsDark.copy(alpha = 0.5f)
                    else
                        MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
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