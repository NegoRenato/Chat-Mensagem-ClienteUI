# Chat-Mensagem-ClienteUI

Visão Geral (Tema)
O projeto consiste em um aplicativo de troca de mensagens baseado na arquitetura cliente-servidor. O cliente envia requisições para o servidor, que processa as chamadas e retorna a resposta adequada. A comunicação é realizada via sockets utilizando o protocolo TCP, o que garante a confiabilidade e a entrega ordenada dos pacotes (diferentemente do UDP).

Para estabelecer a conexão, o cliente deve especificar o endereço IP e a porta do servidor. O servidor, por sua vez, deve configurar sua porta de escuta e aguardar a conexão simultânea de um ou mais clientes.

Funcionalidades
1. Conexão ao Servidor
O cliente conecta-se ao servidor informando o IP e a porta. Caso o servidor não seja encontrado ou esteja offline, o sistema exibirá uma mensagem de erro na tela do usuário informando que não foi possível estabelecer a conexão.

2. Ações de Conta e Sessão
Após conectar-se ao servidor, o usuário tem acesso às seguintes funcionalidades:

Cadastrar: O usuário pode registrar uma nova conta no servidor fornecendo as seguintes informações:

Nome de usuário: Entre 5 e 20 caracteres. Não pode conter espaços nem caracteres especiais.

Nome comum: Segue a mesma regra de validação do nome de usuário.

Senha: Deve ser estritamente numérica e conter no máximo 6 dígitos.

Logar: Após o cadastro, o usuário pode autenticar-se inserindo seu nome de usuário e senha. O sistema retornará um erro caso as credenciais sejam inválidas ou se o usuário já estiver logado em outra sessão.

Consultar: O usuário autenticado pode visualizar os dados do seu próprio perfil.

Atualizar: O usuário pode alterar seu nome comum e sua senha (as mesmas regras de validação do cadastro são aplicadas na atualização).

Excluir: O usuário pode deletar permanentemente o seu cadastro do servidor.

Deslogar (Logout): Encerra a sessão atual do usuário, mantendo-o conectado ao servidor, mas desautenticado.

Sair: Desconecta o cliente do servidor e encerra a aplicação a qualquer momento.

3. Comunicação (Chat)
Com o usuário devidamente logado, os recursos de mensagens são liberados:

Listar usuários logados: O sistema apresenta uma tabela lateral listando todos os usuários que estão online e disponíveis para comunicação no momento.

Enviar Mensagem: É possível enviar mensagens em tempo real para os usuários. O cliente pode selecionar um destinatário específico na tabela de usuários online e enviar a mensagem diretamente para ele.

