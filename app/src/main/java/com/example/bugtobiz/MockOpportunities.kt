package com.example.bugtobiz
object MockOpportunities {
    val items = listOf(
        Opportunity(
            id = "encomendas",
            title = "Pedidos pelo WhatsApp",
            audience = "Confeitarias de bairro",
            category = "Vendas",
            summary = "É difícil acompanhar os pedidos no meio das mensagens.",
            problem = "Os pedidos chegam pelo WhatsApp e ficam misturados com as conversas. A pessoa precisa procurar as mensagens para lembrar o que deve entregar.",
            solution = "Um app para anotar os pedidos e consultar as datas de entrega.",
            firstVersion = "Cadastro com nome do cliente, produto e data. Uma lista para marcar os pedidos entregues.",
            note = "Pensado para quem trabalha por encomenda, como bolos e doces."
        ),
        Opportunity(
            id = "agenda",
            title = "Horários duplicados",
            audience = "Barbearias e salões",
            category = "Atendimento",
            summary = "Às vezes dois clientes são marcados no mesmo horário.",
            problem = "Os horários são anotados em um caderno. Se mais de uma pessoa faz os agendamentos, pode acontecer de marcar dois clientes no mesmo horário.",
            solution = "Uma agenda que mostra os horários ocupados antes de fazer uma marcação.",
            firstVersion = "Cadastrar cliente, serviço e horário. Mostrar os agendamentos do dia e avisar quando houver um conflito."
        ),
        Opportunity(
            id = "estoque",
            title = "Falta de produtos",
            audience = "Mercadinhos e lojas pequenas",
            category = "Estoque",
            summary = "O dono só percebe que um produto acabou quando alguém pede.",
            problem = "Nem sempre dá tempo de conferir todas as prateleiras. Alguns produtos acabam e não entram na lista de reposição.",
            solution = "Uma lista de estoque que mostra os produtos com poucas unidades.",
            firstVersion = "Cadastrar produtos e quantidades. Destacar os que precisam ser comprados.",
            note = "O próprio dono atualizaria as quantidades."
        ),
        Opportunity(
            id = "reparos",
            title = "Acompanhamento de consertos",
            audience = "Assistências técnicas",
            category = "Serviços",
            summary = "Os clientes perguntam várias vezes se o aparelho está pronto.",
            problem = "O técnico precisa parar o trabalho e procurar suas anotações para responder sobre cada conserto.",
            solution = "Uma lista com os aparelhos recebidos e o andamento de cada serviço.",
            firstVersion = "Cadastrar cliente e aparelho. Alterar o status entre recebido, em conserto e pronto."
        )
    )
}
