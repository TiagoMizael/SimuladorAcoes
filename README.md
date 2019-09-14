**_SpringBoot + AngularJs + Bootstrap3 + Java 11+ REST_**

**Instruções**

O Projeto está pré configurado para conectar com um banco de dados
Postgres do nome 'SoftExpert'.
<br>**User**: 'postgres' 
<br>**Password**: 'postgres'

Em _application.properties_, é possível alterar esta conexão.
Existe uma pré configuração também para SQL Server em comentário.

**######## Importante #######**
<br>É necessário a criação de uma pasta chamada workspace na unidade C:\\,
("C:\\workspace")para que o arquivo .xls do relatório seja gerado.
<br> É possível alterar o local da pasta e o nome da mesma, modificando o váriavel
<br>**_String path_** no método _gerar()_ da classe **GeradorExcel**.


**Frameworks e bibliotecas**

Foram utilizados SpringBoot,AngularJs,Bootstrap 3.
<br>**Apache Poi** para gerar relatório em excel
<br> **JavaMail** para enviar o email com relatório

**Modo de uso** 

Após subir o ambiente
<br> Clique na aba **Contas** e em seguida **Registrar Conta.**
<br> Digite um **E-mail** real _(pois para este que será enviado o email
contendo o relatório posteriormente)_ e um valor fictício no campo
**Valor Disponível** e logo em seguida clique em **Cadastrar.**
Uma mensagem de sucesso deverá ser mostrada.

_Nesta versão, não foi utilizado máscaras monetárias e **foi
limitado o número de contas registradas para uma conta.**_

Logo em seguida clique na aba **Monitoramento** e 
**Registrar Monitoramento.**
<br> Digite o nome de uma empresa que deseja monitorar, o valor
de compra e venda, e clique em **Cadastrar**.

_**É recomendado adicionar várias empresas para monitoramento,
com valores de compra e venda entre 10.0 e 10.99.**_
<br>Para que tenha maior probabilidade de comprar e vender uma ação
quando iniciar o simulador, fazendo o processo ser mais rápido.

_O Uso de apenas uma empresa no monitoramento, pode levar o processo
de simulação demorar cerca de 20 minutos._ 

Após esse processo, será possível verificar a empresa para monitoramento
na aba **Consultar Monitoramentos.**
<br>Nesta aba é possível pesquisar, editar e excluir um monitoramento.

Na aba **Simulador Bolsa de Valores**, clique em **Iniciar Simulação.**
Esse processo pode demorar alguns minutos, podendo diminuir ou aumentar
conforme os dados utilizados no **Monitoramento.**

_O Processo irá gerar um valor no mercado de ações a cada **5 segundos.**_

Após o término da simulação será enviado um e-mail com anexo para o endereço cadastrado na _Conta_ contendo um _.xls_ nomeado _relatorio_ + o número de ID da primeira
e última transação simulada. EX: relatorio1-100.xls.

Agora será possível visualizar uma tabela contendo os dados de todas as simulações
que tiveram transação, sendo possível excluir estes dados conforme um filtro.
EX: _Deletar transações do ID **1** ao ID **100**_ e clicar em **Excluir**, será feito
um **Delete** no banco de dados "**Delete From tb_dados_negociacao where ID >= 1 and ID<= 100**"


