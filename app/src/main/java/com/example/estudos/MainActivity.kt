package com.example.estudos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.estudos.ui.theme.EstudosTheme

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

                // Usando SnapshotStateMap (estado reativo para mapas)
                val quantidades = remember { mutableStateMapOf<String, Int>() }

                val totalComprados = quantidades.values.sum()
                val totalPreco = produtos.sumOf { produto ->
                    (quantidades[produto.nome] ?: 0) * produto.preco
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier.padding(bottom = 20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        Text(
                            text = "Produtos Comprados: $totalComprados",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = "Valor total: R$ %.2f".format(totalPreco),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    ProdutoCard(
                        produtos = produtos,
                        quantidades = quantidades,
                        onQuantidadeChange = { produto, novaQtd ->
                            quantidades[produto.nome] = novaQtd
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProdutoCard(
    produtos: List<Produto>,
    quantidades: Map<String, Int>,
    onQuantidadeChange: (Produto, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(produtos, key = { produto -> produto.nome }) { produto ->
            val quantidadeComprados = quantidades[produto.nome] ?: 0

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = produto.nome, style = MaterialTheme.typography.titleLarge)
                        Text(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(12.dp))
                                .padding(5.dp),
                            text = "$quantidadeComprados",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    Text(
                        text = "Preço: R$ %.2f".format(produto.preco),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            shape = CircleShape,
                            onClick = {
                                if (quantidadeComprados > 0) {
                                    onQuantidadeChange(produto, quantidadeComprados - 1)
                                }
                            }
                        ) { Text("-") }

                        Button(
                            shape = CircleShape,
                            onClick = { onQuantidadeChange(produto, quantidadeComprados + 1) }
                        ) { Text("+") }
                    }
                }
            }
        }
    }
}
