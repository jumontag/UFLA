# Compilando:
No terminal, dentro da pasta src inserir:
"javac -d . br/ufla/gct052/encapsulamento/model/*.java br/ufla/gct052/encapsulamento/app/*.java"

# Executando:
Ainda dentro da pasta src, executar:
java br.ufla.gct052.encapsulamento.app.App

# O que foi corrigido:

1. Todos os atributos foram transformados em private.

2. Foram implementados construtores que exigem a passagem de parâmetros para serem criados, com uma validação interna para garantir que os dados do cliente e da conta não sejam nulos, e, no caso do cpf, que ele também não seja vazio. Caso um dado inválido seja inserido, é lançada uma exceção.

3. Para exibição dos dados foram criados getters, entretanto, para alterar os dados internos foram criados os métodos depositar, sacar e transferir. Todos eles passam por validações, para garantir que haja saldo para ser corretamente manipulado.

4. Foram implementados métodos de sobrescrita para fornecer informações sobre cada objeto, ao invés de seu endereço de memória.

5. Para preservar o endereço do cliente, ao tentar acessá-lo retorna-se uma cópia defensiva, que impede que o endereço armazenado internamente seja alterado fora da classe responsável. 

# Saídas no terminal:
----------------- PARTE A -----------------

Inicialmente, o cpf do titular da conta 123 foi definido como: 123.456.789-00

1 - Com "conta.saldo = 1000000" o saldo da conta foi definido diretamente fora da classe como R$1000000.0, algo que quebra a segurança da conta, afinal, nem todos devem poder alterar esse campo desse modo.
2 - Com "conta.numero = 124" o número da conta foi alterado para 124, algo que não deveria ser alterado depois de criado.
3 - O cpf de uma pessoa é único e imutável, logo, também não deveria ser permitido alterá-lo. Entretanto, ele foi modificado para 009.876.543-21 com o comando "conta.titular.cpf = "009.876.543-21"
4 - Com "conta.limite = -100" o limite da conta foi definido como R$-100.0, um valor inválido, pois o limite não pode ser negativo.
    Além disso, o meio como isso ocorreu também pode prejudicar a segurança do sistema.
5 - Definindo uma string nomeCliente e tentando armazenar nela "conta.titular.nome" é realizada uma tentativa de se obter o nome do cliente, porém, o mesmo ainda não foi definido, o que retorna apenas 'null'

----------------- PARTE B (CÓDIGO REFATORADO) -----------------

* Entre () estão os números que relacionam o antes e depois da refatoração.

Agora na criação dos objetos já há a passagem obrigatória dos parâmetros, com validação, incluindo também a vinculação conta-cliente. Isso evita erros como NullPointerException.

(1) - O saldo de cada cliente se inicia zerado. (R$0.0).
(1) - Com a operação de depósito, o saldo passou a ser R$1000.0
(1) - Com a operação de saque, o saldo passou a ser R$900.0
(1, 2 e 3) - Já não é mais possível alterar o número da conta, o cpf do cliente e, diretamente, o saldo, pois são atributos privados.
(4) - Ao tentar alterar o limite para um valor negativo, como por "contaR.setLimite(-100)" é lançada uma exceção, impedindo a operação.
(5) - Agora, com a obrigatoriedade de se fornecer o nome do cliente na criação do objeto, é possível retornar seu nome, que no caso da conta 123 é Cebolinha

----------------- PARTE C -----------------

Definindo uma nova cliente, com seu endereço:
Endereço da cliente Mônica: Rua dos Bobos, 0

Alterando o endereço da cliente, que é privado, mas tem a referência exposta:
Endereço da cliente Mônica alterado para: Rua dos limoeiros, 78

Utilizando o método de cópia defensiva e tentando alterar novamente o endereço:
O endereço da cliente Mônica é Rua dos limoeiros, 78

----- Aproveitando as duas contas criadas corretamente, é possível testar o método "transferir"

A conta 321, pertencente ao(à) cliente Mônica, CPF: 111.222.333-44 recebeu um depósito de R$1500.0
Foram transferidos R$100.00 da conta conta 321, pertencente ao(à) cliente Mônica, CPF: 111.222.333-44 para a conta 123, pertencente ao(à) cliente Cebolinha, CPF: 123.456.789-00

Saldo da conta conta 321, pertencente ao(à) cliente Mônica, CPF: 111.222.333-44 atualizado: R$1400.0
Saldo da conta conta 123, pertencente ao(à) cliente Cebolinha, CPF: 123.456.789-00 atualizado: R$1000.0
