**CategoriaRequestDTO**

* nome
* descricao
* CategoriaResponseDTO
* idCategoria
* nome
* descrição



POST   /api/categorias

GET    /api/categorias

GET    /api/categorias/{id}

PUT    /api/categorias/{id}

DELETE /api/categorias/{id}



criar

listar

buscarPorId

atualizar

excluir





\----------------------------

&#x09;ProdutoRequestDTO

* nome
* descricao
* preco
* image
* disponivel
* categoriaId
* ingredientesIds   ← podemos manter para o MVP



&#x09;ProdutoResponseDTO

* idProduto
* nome
* descrição
* preco
* image
* disponível
* categoria
* ingredientes



&#x09;CategoriaResumoDTO

* id
* nome

*IngredienteResumoDTO*
   * id
   * nome
   * unidadeMedida



&#x09;ProdutoResponseDTO

&#x09;├── CategoriaResumoDTO

&#x09;└── List<IngredienteResumoDTO>



POST   /api/produtos

GET    /api/produtos

GET    /api/produtos/{id}

PUT    /api/produtos/{id}

DELETE /api/produtos/{id}



criar

listar

buscarPorId

atualizar

excluir





\----------------------------

**MesaRequestDTO**

\- numero

 **MesaResponseDTO**

\- idMesa

\- numero

\- statusMesa

\- qrToken



Endpoints administrativos:

POST   /api/mesas

GET    /api/mesas

GET    /api/mesas/{id}

PUT    /api/mesas/{id}

DELETE /api/mesas/{id}



E futuramente teremos operações específicas:

PATCH /api/mesas/{id}/status



criar

listar

buscarPorId

atualizar

excluir





\---------------------------- 4 - QR Code

O QR Code é uma representação do qrToken da Mesa.

Por exemplo:

&#x09;Mesa

&#x09;├── idMesa

&#x09;├── numero

&#x09;└── qrToken



O QR pode apontar para:

&#x09;/mesa/{qrToken}

Então inicialmente:

não precisa de QRCodeDTO.



Mais tarde podemos criar um endpoint para gerar/download do QR:

GET /api/mesas/{id}/qrcode

que poderia retornar uma imagem PNG.





\---------------------------- Cliente/Atendente

**PedidoRequestDTO**

* mesaId
* itens
* ItemPedidoRequestDTO
* produtoId
* quantidade



**PedidoRequestDTO**

├── mesaId

└── List<ItemPedidoRequestDTO>



**PedidoResponseDTO**

* idPedido
* dataHora
* statusPedido
* mesa
* itens
* valorTotal

subtotal = quantidade × precoUnitario

valorTotal = subtotais





\---------------------------- Pedido via QR Code

Cliente:

QR

&#x20;	  ↓

&#x09;/mesa/{qrToken}

&#x20;	  ↓

&#x09;Cardápio

&#x20;	  ↓

&#x09;POST /api/publico/mesas/{qrToken}/pedidos



Nesse caso, não confie em mesaId enviado pelo cliente.

O backend obtém:

&#x09;qrToken

&#x09; ↓

&#x09;Mesa

&#x09; ↓

&#x09;mesaId

Portanto, podemos ter um DTO específico:



**PedidoQRRequestDTO**

* itens



e não:

* mesaId



Porque a mesa vem do QR.

Então:

**PedidoQRRequestDTO**

└── List<ItemPedidoRequestDTO>





\---------------------------- Cozinha

trabalha com o **PedidoResponseDTO**



listarPedidosPendentes()

iniciarPreparacao(idPedido)

marcarComoPronto(idPedido)



GET   /api/cozinha/pedidos

PATCH /api/cozinha/pedidos/{id}/preparar

PATCH /api/cozinha/pedidos/{id}/pronto

PATCH /api/cozinha/pedidos/{id}/cancelar





\---------------------------- Atendente

atendente também utiliza PedidoRequestDTO



POST /api/atendente/mesas/{mesaId}/pedidos

GET  /api/atendente/pedidos

GET  /api/atendente/mesas



Aqui o mesaId pode vir da URL porque o atendente estará autenticado futuramente

&#x09;ATENDENTE

&#x09; ↓

&#x09;pertence à lanchonete?

&#x09; ↓

&#x09;mesa pertence à lanchonete?





\---------------------------- Pagamento

Deixar para depois , como futuras funcionalidades



**PagamentoRequestDTO**

* pedidoId
* valor
* formaPagamento



**PagamentoResponseDTO**

* id
* pedidoId
* valor
* formaPagamento
* dataHora



POST /api/pagamentos

GET  /api/pagamentos/{id}

GET  /api/pagamentos/pedido/{pedidoId}



registrar

buscarPorId

buscarPorPedido

estornar





\---------------------------- Ingrediente / Estoque

Não mexeria nesses DTOs agora. deixar na parte de ERP



Estas classes

&#x20;Ingrediente

&#x20;Estoque

&#x20;MovimentacaoEstoque

&#x20;TipoMovimentacao

&#x20;UnidadeMedida

ficarão para depois



\---------------------------- Lanchonete

Não precisa mexer agr, classe pensada no ERP

Lanchonete

├── Mesas

├── Produtos

└── futuramente Usuários



\---------------------------- Usuário

deixar para o final, junto com Security

UsuarioRequestDTO

UsuarioResponseDTO

LoginRequestDTO

LoginResponseDTO

PerfilUsuario



\---------------------------- Resumo DTOs para usar

dto/

│

├── categoria/

│   ├── CategoriaRequestDTO

│   ├── CategoriaResponseDTO

│   └── CategoriaResumoDTO

│

├── produto/

│   ├── ProdutoRequestDTO

│   └── ProdutoResponseDTO

│

├── mesa/

│   ├── MesaRequestDTO

│   └── MesaResponseDTO

│

├── pedido/

│   ├── PedidoRequestDTO

│   ├── PedidoQRRequestDTO

│   ├── PedidoResponseDTO

│   ├── ItemPedidoRequestDTO

│   └── ItemPedidoResponseDTO

│

└── pagamento/

├── PagamentoRequestDTO

└── PagamentoResponseDTO

Não criar por enquanto

&#x20;Usuário

&#x20;Ingrediente

&#x20;Estoque

&#x20;MovimentacaoEstoque

&#x20;Lanchonete

&#x20;QRCode


1. Categoria
   ↓
2. Produto
   ↓
3. Mesa
   ↓
4. Pedido + ItemPedido
   ↓
5. Fluxo QR
   ↓
6. Cozinha
   ↓
7. Atendente
   ↓
8. Pagamento
   ↓
9. Frontend
   ↓
10. Usuario + Spring Security
   ↓
11. Estoque / ERP

