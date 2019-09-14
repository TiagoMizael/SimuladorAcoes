app.controller("BolvaValoresController",function ($scope,$http) {

    $scope.registra = false;
    $scope.registraMon = false;
    $scope.loading = false;
    $scope.edit = false;
    $scope.deleta = false;
    $scope.editMon = false;
    $scope.deletaMon = false;
    $scope.cargo = true;
    $scope.select = false;
    $scope.deletaRelat = {};
    $scope.search={};
    $scope.transacao = {};
    $scope.conta={};
    $scope.contas=[];
    $scope.listaAcoes = [];
    $scope.historico = [];
    $scope.monitoramentos = [];
    $scope.monitoramento = {};


    $scope.getContas = function(){
        $http({method : 'GET', url : 'http://localhost:8080/getContas'})
            .then(function sucessCallBack(response) {
            $scope.contas = response.data;
        }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao buscar contas!')
        });
    };
    $scope.getAcoes = function(){
        $http({method : 'GET', url : 'http://localhost:8080/getAcoes'})
            .then(function sucessCallBack(response) {
                $scope.listaAcoes = response.data;
            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao buscar Ações!')
            });
    };

    $scope.registraConta = function() {
        if($scope.conta.valor != null || $scope.conta.email != null) {
            if(!$scope.contas.length > 0) {
                $http({method: 'POST', url: 'http://localhost:8080/novaConta', data: $scope.conta})
                    .then(function sucessCallBack(response) {
                        alert('Conta criada com sucesso!');
                        $scope.getContas();
                    }, function errorCallBack(response) {
                        console.log('Error:  ' + response);
                        alert('Erro ao registrar conta!')
                    });
            }else{alert('Não é permitido registrar mais de uma conta!')}
        }else{
            alert('Favor preencher todos os campos!')
        }
    };

    $scope.deletaConta = function (id,deleta) {
        $http({method : 'DELETE', url : 'http://localhost:8080/deletaConta/'+id})
            .then(function sucessCallBack(response) {
                alert('Conta deletada com sucesso!');
                deleta = !deleta;
                $scope.getContas();
            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao deletar conta!')
            });
    };

    $scope.editaConta = function (conta,edit) {
        $http({method : 'PUT', url : 'http://localhost:8080/atualizaConta/'+conta.id,data: conta})
            .then(function sucessCallBack(response) {
                alert('Conta editada com sucesso!');
                edit= !edit;
                $scope.getContas();
            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao editar conta!')
            });
    };

    $scope.getHistorico = function () {
        $http({method : 'GET', url : 'http://localhost:8080/getHistorico'})
            .then(function sucessCallBack(response) {
                $scope.historico = response.data;
            }, function errorCallBack(response) {
                console.log('Error:  ' + response)
                alert('Erro ao buscar relatório!')
            });
    };

    $scope.getMonitoramentos = function () {
        $http({method : 'GET', url : 'http://localhost:8080/getMonitoramentos'})
            .then(function sucessCallBack(response) {
                $scope.monitoramentos = response.data;
                $scope.registraMon = false;
            }, function errorCallBack(response) {
                console.log('Error:  ' + response)
                alert('Erro ao buscar despesas!')
            });
    };

    $scope.registraMonitoramento = function() {
        $http({method : 'POST', url : 'http://localhost:8080/novoMonitoramento',data: $scope.monitoramento})
            .then(function sucessCallBack(response) {
                alert('Monitoramento criado com sucesso!');
                $scope.getMonitoramentos();
            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao registrar monitoramento!')
            });
    };

    $scope.deletaMonitoramento = function (id,deletaMon) {
        $http({method : 'DELETE', url : 'http://localhost:8080/deletaMonitoramento/'+id})
            .then(function sucessCallBack(response) {
                alert('Monitoramento deletado com sucesso!');
                deletaMon = !deletaMon;
                $scope.getContas();
            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao deletar monitoramento!')
            });
    };

    $scope.editaMonitoramento = function (monitoramento,editMon) {
        $http({method : 'PUT', url : 'http://localhost:8080/atualizaMonitoramento/'+monitoramento.id,data: monitoramento})
            .then(function sucessCallBack(response) {
                alert('Monitoramento editado com sucesso!');
                editMon= !editMon;
                $scope.getMonitoramentos();
            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao editar monitoramento!')
            });
    };
    $scope.simular = function(){
        $scope.loading = true;
        $http({method : 'GET', url : 'http://localhost:8080/simular'})
            .then(function sucessCallBack(response) {
                $scope.getHistorico();
                $scope.getContas();
                $scope.getAcoes();
                $scope.loading = false;
                alert('Simulação concluída')
            }, function errorCallBack(response) {
                console.log('Error:  ' + response)
                $scope.loading = false;
                alert('Erro ao iniciar simulação!')
            });
    };
    $scope.deletaRelatorio = function() {
        if($scope.deletaRelat.id1 != null || $scope.deletaRelat.id1 !=null){
        $http({method : 'POST', url : 'http://localhost:8080/deletaRelatorio',data: $scope.deletaRelat})
            .then(function sucessCallBack(response) {
                $scope.getHistorico();
                alert('Transações excluidas conforme filtro!');

            }, function errorCallBack(response) {
                console.log('Error:  ' + response);
                alert('Erro ao deletar transações')
            });
        }else{
            alert('Preencha os filtros corretamente para excluir as transações')
        }
    };

    $scope.getContas();
    $scope.getAcoes();
    $scope.getMonitoramentos();
    $scope.getHistorico();
})