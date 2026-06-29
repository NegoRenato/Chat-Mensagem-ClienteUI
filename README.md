# Chat-Mensagem-ClienteUI

Projeto da disciplina de Sistemas Distribuidos

TEMA

o Tema do projeto, é um aplicativo de troca de mensagens, onde possui um cliente que manda as requisições para o servidor e um servidor que processa as requsições do 
cliente, e devolve o que o cliente requisitou, a conexão e via sockets pelo modelo TCP(visto que o udp perde pacotes), no qual o cliente deve especificar o ip do servidor
e a porta do mesmo para se conectar, e o servidor deve especificar sua porta e aguardar a conexão de um cliente.

EPs

o projeto possui 3 EPs e cada uma das EPs pede os seguintes conteudos.

EP1:A EP1 pede para que o aluno desenvolva um CRUD de usuarios, no qual esse usuario deve ter, nome comum(nome da pessoa), nome de usuario(qual ele usa para logar),
senha e um token(para saber se aquele usuario esta logado). E tambem deve ter validações na hora de cadastrar/atualizar um usuario, e as validações são. O nome de usuario
não pode ter caraceteres especiais, espaços em brancos e o nome de usuario nao pode ser menor que 5 caracteres e maior que 20. A Senha tem que ser apenas numerica
e de ate 6 numeros. O token do usuario deve conter a string "usr_" + o nome do usuario.

EP2: A EP2 pede para que o aluno desenvolva um usuario adm, no qual o usuario adm pode Ler todos os usuarios cadastrados no servidor ou um usuario especifico, alterar
dados de um usuario especifico, e excluir um usuario especifico do sistema. E tambem deve conter uma validação que um usuario logado no sistema, não possa alterar
outro usuario do sistema.
