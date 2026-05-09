# Lançador de Dados — Ponderada 1

Aplicação Android desenvolvida em Jetpack Compose que permite ao usuário escolher um tipo de dado e lançá-lo, exibindo um resultado aleatório válido.

---

## O que foi proposto

A aplicação original possuía dois problemas:

1. Erro lógico no D6: o código usava `Random.nextInt(6)`, que gera valores de 0 a 5 e não de 1 a 6 como esperado para um dado.
2. Apenas o D6 estava disponível: a lista de dados continha somente `"D6"` e não havia lógica para outros tipos.

---

## O que foi corrigido e implementado

### Correção do erro lógico
O intervalo do `Random.nextInt` foi ajustado para incluir corretamente o valor mínimo (1) e o máximo do D6:

```kotlin
"D6" -> Random.nextInt(1, 7)
```

`Random.nextInt(from, until)` gera um número aleatório no intervalo fechado no início e aberto no final, ou seja, inclui o `from` mas não inclui o `until`. Por isso, para um D6 que deve retornar valores de 1 a 6, passamos `nextInt(1, 7)` onde o 1 é incluído, o 7 não. O código original usava `nextInt(6)`, que gera valores de 0 a 5, errando tanto o limite inferior quanto o superior.

### Adição dos novos dados
A lista de dados foi expandida para incluir D10, D20 e D100, com RadioButtons para seleção na interface. Os intervalos corretos foram definidos para cada tipo:

```kotlin
"D6"   -> Random.nextInt(1, 7)
"D10"  -> Random.nextInt(1, 11)
"D20"  -> Random.nextInt(1, 21)
"D100" -> Random.nextInt(1, 101)
```

### Exibição do resultado via `when`
Um bloco `when` trata cada tipo de dado e gera o valor aleatório correto ao clicar no botão.

---

## Ir Além — Imagens dos dados

Para alcançar a nota máxima, foram adicionadas imagens representando cada face de dado, exibidas junto ao resultado.

### O que foi implementado

Estado separado para o valor sorteado:
```kotlin
var valorSorteado by remember { mutableStateOf(0) }
```

Isso permite que a imagem seja atualizada reativamente junto ao número sorteado.

Reset ao trocar de dado:
```kotlin
onClick = { dadoSelecionado = dado; valorSorteado = 0 }
```

Ao trocar o tipo de dado, o valor é zerado para evitar exibir um resultado de outro dado.

Mapeamento de dado para imagem com `mapOf`:
```kotlin
val dict = mapOf(
    "D6"   to R.drawable.d6,
    "D10"  to R.drawable.d10,
    "D20"  to R.drawable.d20,
    "D100" to R.drawable.d100
)
```

Esse dicionário transforma o nome do dado selecionado diretamente no recurso de imagem correspondente, sem precisar de um segundo bloco `when`.

Composable `DiceImage`:
Um componente separado sobrepõe o valor sorteado sobre a imagem do dado, usando um `Box` com `contentAlignment = Alignment.Center`.

---

## Referências e uso de IA

Para implementar as imagens, foi utilizado o codelab oficial do Android como referência principal:
[Adicionar imagens ao app Android — Android Developers](https://developer.android.com/codelabs/basic-android-kotlin-compose-add-images?hl=pt-br#2)

O material cobre como adicionar imagens à pasta `drawable`, acessá-las via `painterResource(R.drawable.*)`, e exibi-las com o elemento combinável `Image` dentro de um layout `Box`.

O uso de inteligência artificial ficou restrito a um ponto específico: entender como centralizar o texto do valor sorteado sobre o centro da imagem do dado. Antes de recorrer à IA, encontramos duas dificuldades: esquecer de adicionar o `remember` ao estado, o que fazia com que a interface não reagisse às mudanças, e um import incorreto do `R`, que impedia o acesso aos recursos de imagem. Tentamos também usar `Alignment.Center` diretamente no componente `Text`, o que não funcionou como esperado. A partir disso, pedimos ajuda à IA, o que nos levou ao uso de `contentAlignment = Alignment.Center` no `Box` do composable `DiceImage`.

---

## Critérios atendidos

| Critério | Status |
|---|---|
| D6 gera valores de 1 a 6 | ✅ |
| D10 gera valores de 1 a 10 | ✅ |
| D20 gera valores de 1 a 20 | ✅ |
| D100 gera valores de 1 a 100 | ✅ |
| Interface permite escolher o tipo de dado | ✅ |
| Resultado exibido corretamente após clique | ✅ |
| Faces diferentes por tipo de dado (ir além) | ✅ |