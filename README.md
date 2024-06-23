# Projeto Sistemas Operacionais

Esse projeto trata-se de um **simulador de escalonamento
de processos**, proposto como trabalho final da disciplina **(5954016)
Sistemas Operacionais** do Departamento de Computação e Matemática
da USP-RP, aplicada pelo professor Clever Ricardo Guareis de Farias.

## Instruções básicas

1) Para inicilizar o programa basta abrir um projeto java e executar a classe principal
SchedulerSimulator ou apenas executar o arquivo SchedulerSimulator.jar.
2) Com o programa aberto, você precisa indicar em "Job file" o caminho absoluto
completo do "arquivo programa" definido pela BNF apresentada pelo professor.

```
<program> ::= <program_statement> <program_body>
<program_statement> ::= "program" <whitespace> <file_name> <EndOfLine>
<program_body> ::= <begin_statement> <program_behaviour> <end_statement>
<begin_statement> ::= "begin" <EndOfLine>
<end_statement> ::= "end" <EndOfLine>
<program_behaviour> ::= (<behaviour_statement>)+
<behaviour_statement> ::= "execute" <EndOfLine> | "block" <whitespace> <block_period> <EndOfLine>
<whitespace> ::= " "
<block_period> ::= "1" | "2" | "3" | "4" | "5"
```

3) Após indicar o caminho em "Job file", deve-se usar os botões para manipular
a execução do programa.

### Instruções para gerador

Para executar nosso gerador de programas aleatórios, 
é necessário ter o Python 3 instalado. 
A execução é simples: é necessário passar três parâmetros. 
Primeiro, o número de instruções por programa; 
segundo, o número de programas gerados; 
e, por último, o nome da pasta que irá guardar os arquivos. 
A pasta será criada na mesma pasta em que o script for executado.

```bash
    python3 generator.py n_instrucoes n_arquivos nome_pasta
```

Já adicionamos 15 arquivos testes na pasta `resources/arquivos_para_teste`.
Lembrando que o nome do arquivo não deve ser modificado, já que há um teste para verificar
se o nome real do arquivo é o mesmo especificado no programa.

### Autores
- Caio Uehara Martins (13672022) 
- Gustavo Bender (13725695)