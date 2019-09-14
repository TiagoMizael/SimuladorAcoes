<!DOCTYPE html>
<html ng-app="app">
<script src="js/lib/angular.min.js"></script>
<script src="js/lib/angular-ui-router.min.js"></script>
<script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
</script>
<script src="js/bootstrap.min.js"></script>
<script src="js/lib/localforage.min.js"></script>
<script src="js/lib/ngStorage.min.js"></script>
<script src="js/app/bolsaValores.module.js"></script>
<script src="js/app/bolsaValores.controller.js"></script>
<link href="css/bootstrap.css" rel="stylesheet"/>
<link href="css/app.css" rel="stylesheet"/>
<link href="css/loading.css" rel="stylesheet"/>
<link href="css/bolsaValores.css" rel="stylesheet">
<head>
    <meta charset="UTF-8">
    <title>Bolsa de Valores</title>
</head>

<body ng-controller="BolvaValoresController">
<div class="panel-heading"><span class="lead" style="font-weight: bold">Bolsa de Valores</span></div>
<div class="panel-body">

    <ul class="nav nav-tabs" style="font-weight: bold">
        <li><a ng-click="select = true" href="#contas" data-toggle="tab">
            <span class="glyphicon glyphicon-user"></span>
            Contas</a></li>
        <li><a ng-click="select = true" href="#monitoramento" data-toggle="tab">
            <span class="glyphicon glyphicon-usd"></span>
            Monitoramento</a></li>
        <li><a ng-click="select = true" href="#simulador" data-toggle="tab">
            <span class="glyphicon glyphicon-stats"></span>
            Simulador Bolsa de Valores</a></li>
    </ul>
    <div ng-show="!select" class="panel-heading">
        <span class="lead" style="font-weight: bold">Por Favor,Selecione uma das opções acima!</span>
    </div>
    <div ng-show="select" class="tab-content col-sm-6">
        <div class="tab-pane fade in active" style="padding-top: 10px" id="contas">
            <div class="btn-group bot" role="group">
                <button ng-click="registra=false" type="button" class="btn btn-default">Consultar Conta
                </button>
                <button  ng-click="registra=true" type="button" class="btn btn-default">Registrar Conta
                </button>
            </div>
            <div ng-if="registra">
                <div class="form-group">
                    <label for="email" class="control-label">E-mail</label>
                    <input ng-model="conta.email" id="email" class="form-control" placeholder="Digite o E-mail"
                           type="text">
                </div>
                <div class="form-group">
                    <label for="valorDisponivel" class="control-label">Valor Disponível</label>
                    <input ng-model="conta.valor" id="valorDisponivel" class="form-control"
                           placeholder="Digite o valor disponível" type="number" min="0">
                </div>
                <button ng-click="registraConta()" type="button" id="buttonCad" class="btn btn-primary">Cadastrar</button>
            </div>
            <div class="bot" ng-if="!registra">
            </div>
            <div ng-if="!registra">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>E-mail</th>
                        <th>Valor Disponível</th>
                        <th>#</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="conta in contas | filter:search.filter ">
                        <td>{{ conta.id }}</td>
                        <td ng-if="!edit">{{conta.email}}</td>
                        <td ng-if="!edit">{{conta.valor}}</td>
                        <td ng-if="edit"><input ng-model="conta.email" id="email" type="text"></td>
                        <td ng-if="edit"><input ng-model="conta.valor" id="valorDisponivel" type="text"></td>
                        <td ng-show="!edit && !deleta" style="width: 69px">
                            <button ng-click="deleta = !deleta" type="button"
                                    class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span>
                            </button>
                            <button ng-click="edit = !edit" type="button" class="btn btn-default btn-xs"><span
                                    class="glyphicon glyphicon-pencil"></span></button>
                        </td>
                        <td ng-show="edit" style="width: 69px">
                            <button ng-click="editaConta(conta,edit)" type="button"
                                    class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span>
                            </button>
                            <button ng-click="edit = !edit" type="button" class="btn btn-danger btn-xs"><span
                                    class="glyphicon glyphicon-remove"></span></button>
                        </td>
                        <td ng-show="deleta" style="width: 69px">
                            <button ng-click="deletaConta(conta.id,deleta)" type="button"
                                    class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span>
                            </button>
                            <button ng-click="deleta = !deleta" type="button" class="btn btn-danger btn-xs"><span
                                    class="glyphicon glyphicon-remove"></span></button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <span style="font-weight: bold;width: 14px">Ações Compradas</span>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Empresa</th>
                        <th>Quantidade</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="acoes in listaAcoes">
                        <td>{{acoes.id}}</td>
                        <td>{{acoes.empresa}}</td>
                        <td>{{acoes.quantidade}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade in active" style="padding-top: 10px" id="monitoramento">
            <div class="btn-group bot" role="group">
                <button ng-click="registraMon = false;" type="button" class="btn btn-default">Consultar Monitoramentos
                </button>
                <button ng-click="registraMon = true" type="button" class="btn btn-default">Registrar Monitoramento
                </button>
            </div>
            <div ng-if="!registraMon">
                <label class="control-label">Buscar Monitoramentos</label>
                <div class="input-group bot" style="width: 50%">
                    <input ng-model="search.monitoramento" class="form-control"
                           placeholder="Digite um filtro de monitoramento">
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Empresa</th>
                        <th>Valor de Compra</th>
                        <th>Valor de Venda</th>
                        <th>#</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="monitoramento in monitoramentos | filter:search.monitoramento ">
                        <td>{{monitoramento.id}}</td>
                        <td>{{monitoramento.empresa}}</td>
                        <td ng-if="!editMon">{{monitoramento.valorCompra}}</td>
                        <td ng-if="!editMon">{{monitoramento.valorVenda}}</td>
                        <td ng-if="editMon"><input ng-model="monitoramento.valorCompra" id="valorCompra" type="number">
                        </td>
                        <td ng-if="editMon"><input ng-model="monitoramento.valorVenda" id="valorVenda" type="number">
                        </td>
                        <td ng-show="!editMon && !deletaMon" style="width: 69px">
                            <button ng-click="deletaMon = true" type="button"
                                    class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span>
                            </button>
                            <button ng-click="editMon = !editMon" type="button" class="btn btn-default btn-xs"><span
                                    class="glyphicon glyphicon-pencil"></span></button>
                        </td>
                        <td ng-show="editMon" style="width: 69px">
                            <button ng-click="editaMonitoramento(monitoramento,editMon)" type="button"
                                    class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span>
                            </button>
                            <button ng-click="editMon = !editMon" type="button" class="btn btn-danger btn-xs"><span
                                    class="glyphicon glyphicon-remove"></span></button>
                        </td>
                        <td ng-show="deletaMon" style="width: 69px">
                            <button ng-click="deletaMonitoramento(monitoramento.id,deletaMon)" type="button"
                                    class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span>
                            </button>
                            <button ng-click="deletaMon = !deletaMon" type="button" class="btn btn-danger btn-xs"><span
                                    class="glyphicon glyphicon-remove"></span></button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div ng-if="registraMon">
                <div class="form-group">
                    <label for="empresaMon" class="control-label">Empresa</label>
                    <input ng-model="monitoramento.empresa" id="empresaMon" class="form-control"
                           placeholder="Digite o nome da Empresa"
                           type="text">
                </div>
                <div class="form-group">
                    <label for="valorCompra" class="control-label">Valor de Compra</label>
                    <input ng-model="monitoramento.valorCompra" id="valorCompra" class="form-control"
                           placeholder="Digite o valor de compra" type="number">
                </div>
                <div class="form-group">
                    <label for="valorVenda" class="control-label">Valor de Venda</label>
                    <input ng-model="monitoramento.valorVenda" id="valorVenda" class="form-control"
                           placeholder="Digite o valor de venda" type="number">
                </div>
                <button ng-click="registraMonitoramento()" type="button" class="btn btn-primary">Cadastrar</button>
            </div>
        </div>
        <div class="tab-pane fade in active" style="padding-top: 10px" id="simulador">
            <div ng-show="loading" style="text-align: center;margin-top: 20px">
                <span class="lead" style="font-weight: bold">O Processo de Simulação pode levar alguns minutos!</span>
                <span class="lead" style="font-weight: bold"><br>Por Favor aguarde,</span>
                <span class="lead" style="font-weight: bold">seu relatório logo será gerado!</span>
                <div class="loader" id="loader-1"></div>
            </div>
            <button ng-if="!loading" ng-click="simular()" type="button" class="btn btn-success">Iniciar Simulação</button>
            </button>
            <div style="padding-top: 15px" ng-show="!loading">

                <label class="control-label">Buscar Transação</label>
                <div class="input-group bot" style="width: 50%">
                    <input ng-model="search.transacao" class="form-control"
                           placeholder="Digite um filtro de transação">
                </div>
                <div style="padding-bottom: 15px">
                    <span>Deletar Transações do ID:</span>
                    <input ng-model="deletaRelat.id1" type="number" min="0" style="width: 80px">
                    <span>ao ID:</span>
                    <input ng-model="deletaRelat.id2" type="number" min="0" style="width: 80px">
                    <button ng-click="deletaRelatorio()" type="button" class="btn btn-danger btn-xs">Excluir</button>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Empresa</th>
                        <th>Quantidade</th>
                        <th>Tipo</th>
                        <th>Valor</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="transacao in historico | filter:search.transacao ">
                        <td>{{transacao.id}}</td>
                        <td>{{transacao.data}}</td>
                        <td>{{transacao.empresa}}</td>
                        <td>{{transacao.quantidade}}</td>
                        <td>{{transacao.tipo}}</td>
                        <td>{{transacao.valor}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</nav>
</body>
</html>