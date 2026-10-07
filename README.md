# BugToBiz

Aplicativo Android para explorar problemas fictícios de pequenos negócios e imaginar soluções digitais. Desenvolvido para a **parcial de Android Views (XML)**.

O usuário consulta um mural, abre uma oportunidade e marca ou desmarca seu interesse em investigá-la. Ao voltar, o mural mostra o novo status e a quantidade de oportunidades selecionadas.

## Como executar no Android Studio

1. Abra o Android Studio e escolha **Open**.
2. Selecione a pasta **BugToBiz**, que contém `settings.gradle.kts`. Não abra apenas a pasta `app`.
3. Aguarde a sincronização do Gradle. Na primeira abertura, é necessário acesso à internet para baixar dependências que não estejam no cache.
4. Se o Android Studio solicitar o SDK do projeto, aceite a instalação do **Android SDK Platform 37** pelo SDK Manager.
5. Em **Settings > Build, Execution, Deployment > Build Tools > Gradle**, use o JDK fornecido pelo Android Studio, compatível com o Gradle 9.5 (o ambiente usado na criação possui JDK 25).
6. Escolha um emulador ou celular com **Android 13 / API 33 ou superior**.
7. Selecione a configuração **app** e clique em **Run ▶**.

A configuração principal acompanha o MediaTracker fornecido como referência: **AGP 9.3.1**, **Gradle 9.5.0**, **compileSdk/targetSdk 37**. O Gradle Wrapper está incluído, portanto não é preciso instalar o Gradle separadamente.

`local.properties` é uma configuração local do SDK. O Android Studio cria esse arquivo para cada computador; ele não deve ser publicado no GitHub.

## Telas e interação

- **Mural:** quatro problemas de confeitarias, barbearias, mercadinhos e assistências técnicas.
- **Detalhes:** problema, solução proposta, primeira versão possível e uma dica opcional.
- **Quero investigar:** altera o status na tela de detalhes. É possível desfazer a escolha. O mural recebe o resultado e atualiza o cartão e o contador.

Os exemplos são fictícios. A seleção permanece durante a navegação e a recriação das telas, inclusive ao girar o aparelho. Não há persistência em banco ou arquivo: ao iniciar uma sessão nova, o mural volta à seleção inicial.

## Organização do código

| Arquivo | Responsabilidade |
|---|---|
| `Opportunity.kt` | Modelo imutável com campos `val` e uma observação opcional (`String?`). |
| `MockOpportunities.kt` | Lista de dados simulados. |
| `HomeActivity.kt` | Exibe o mural, abre os detalhes e recebe o status atualizado. |
| `OpportunityAdapter.kt` | Preenche os cartões do `RecyclerView` e trata o clique. |
| `OpportunityDetailActivity.kt` | Busca a oportunidade pelo ID e trata a interação de investigação. |
| `res/layout/activity_home.xml` | Layout da primeira tela. |
| `res/layout/activity_opportunity_detail.xml` | Layout da segunda tela. |
| `res/layout/item_opportunity.xml` | Componente XML reutilizado para cada cartão. |
| `res/values/` | Textos, cores, dimensões, estilos e tema. |

## Requisitos da parcial

| Requisito | Implementação |
|---|---|
| Duas telas em Views/XML | Duas Activities com seus layouts XML. |
| Views e ViewGroups | TextView, ImageView, ImageButton, LinearLayout, NestedScrollView, RecyclerView e componentes Material. |
| Intent explícita com dados | `Intent(this, OpportunityDetailActivity::class.java)`, passando ID e status. |
| Views conectadas ao Kotlin | ViewBinding em telas e cartões. |
| Interação que atualiza a interface | Marcar/desmarcar investigação, atualização de texto, cartão e contador. |
| Modelos imutáveis | `data class Opportunity` com `val`; mudanças criam cópias com `copy()`. |
| Tratamento de valores opcionais | ID inválido encerra os detalhes com mensagem; observação ausente oculta o cartão. |
| Dados simulados | Quatro oportunidades locais, sem API ou banco de dados. |
| Componente XML reutilizável (opcional) | `item_opportunity.xml`, inflado pelo Adapter com ViewBinding. |

O projeto usa XML e Kotlin, sem Compose. Não inclui login, servidor, API, chaves ou senhas.

## Como explicar o funcionamento

1. **Dados:** `MockOpportunities` contém quatro objetos `Opportunity`. Os campos são `val`; mudar o status significa criar uma cópia com `copy()`.
2. **Layout:** as telas são desenhadas nos arquivos XML. O ViewBinding gera classes que permitem acessar as Views pelos seus IDs, por exemplo `binding.title.text`.
3. **Lista:** o `RecyclerView` pede ao Adapter para criar um cartão (`onCreateViewHolder`) e preencher os textos do item (`onBindViewHolder`).
4. **Navegação:** ao tocar no cartão, a primeira Activity abre a segunda com uma `Intent` explícita. Ela envia o ID e o status atual com `putExtra()`.
5. **Detalhes:** a segunda Activity recebe o ID com `getStringExtra()` e procura o objeto. A observação usa `String?`: se ela não existe, o cartão é ocultado.
6. **Interação:** o botão inverte o status, atualiza a tela e prepara o resultado com `setResult()`. Ao voltar, o callback registrado na primeira Activity recebe o resultado e atualiza a lista.
7. **Rotação:** `onSaveInstanceState()` guarda as escolhas em um `Bundle`, para restaurá-las quando a tela for recriada. Isso não é armazenamento permanente.

## Bibliotecas utilizadas

- **AndroidX Core KTX 1.10.1:** funções auxiliares de Kotlin para Views e tratamento das barras do sistema.
- **AndroidX AppCompat 1.6.1:** base das Activities e suporte ao tema.
- **AndroidX Activity KTX 1.8.0:** Activity Result API, retorno da segunda tela e configuração edge-to-edge.
- **AndroidX RecyclerView 1.1.0:** lista de oportunidades com Adapter e ViewHolder.
- **Material Components 1.10.0:** cartões arredondados, botão e tema de interface.

ViewBinding é um recurso do plugin Android, sem biblioteca adicional. Ícones são vetores XML incluídos no projeto; não há carregamento externo de imagens ou fontes.

## Roteiro de verificação manual

1. Abra o aplicativo: devem aparecer quatro oportunidades e nenhuma em investigação.
2. Abra uma oportunidade: seus detalhes devem corresponder ao cartão escolhido.
3. Toque em **Quero investigar**: status e texto do botão devem mudar.
4. Volte ao mural: o cartão deve indicar **Em investigação** e o contador deve aumentar.
5. Abra novamente e remova a seleção: o contador deve diminuir ao voltar.
6. Abra a oportunidade de barbearias: não deve haver cartão de dica, pois sua observação é opcional.
7. Gire o aparelho durante a navegação: a seleção deve ser preservada.

## Verificação realizada

Verificado em 07/10/2026 no ambiente local do Android Studio:

- `assembleDebug`: compilação concluída.
- `lintDebug`: concluído, sem erros; há avisos sobre versões mais recentes das dependências e configuração de backup.
- Execução no emulador Pixel 10 Pro: lista, detalhes por ID, seleção, retorno ao mural, desfazer seleção e observação ausente.
- Recriação por rotação nas duas telas: seleção preservada.
- Texto ampliado a 150%: conteúdo acessível por rolagem e botão funcional.

## Entrega

Publique os arquivos de código deste projeto em seu próprio repositório no GitHub e entregue o link. O `.gitignore` exclui configurações locais, caches e arquivos gerados pelo build. O APK e as prévias que acompanham a pasta de entrega não são necessários no repositório.

Referência de estudo: [MediaTracker das aulas](https://github.com/betopompolo/AC322A_MediaTracker). O BugToBiz usa outro tema, outros layouts e dados próprios.
