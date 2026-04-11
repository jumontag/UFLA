import re
import math
import statistics
import time
import csv

def hash_soma_palavra_toda (palavra, tamanho_tabela):
    soma = 0
    for letra in palavra:
        soma += ord(letra)
    return soma % tamanho_tabela

def hash_soma_ponderada(palavra, tamanho_tabela):
    soma = 0
    for i, letra in enumerate(palavra):
        soma += (ord(letra) * (i + 1)) # Multiplica pelo índice da letra
    return soma % tamanho_tabela

def hash_polinomial(palavra, tamanho_tabela):
    R = 31
    h = 0
    for letra in palavra:
        h = (R * h + ord(letra)) % tamanho_tabela
    return h

def hash_simples (palavra, tamanho_tabela):
    if(len(palavra) == 1):
        letras_iniciais = ord(palavra[0])
    if(len(palavra) == 2):
        letras_iniciais = (ord(palavra[0]) + ord(palavra[1]))
    if(len(palavra) == 3):
        letras_iniciais = (ord(palavra[0]) + ord(palavra[1]) + ord(palavra[2]))
    if(len(palavra) > 3):
        letras_iniciais = (ord(palavra[0]) + ord(palavra[1]) + ord(palavra[2]) + ord(palavra[3]))
    return letras_iniciais % tamanho_tabela

def busca_hash(chave, tabela, tamanho_tabela):
    categoria = hashs[l](chave, tamanho_tabela)
    lista_palavras = tabela[categoria] 
    passos = 0
    tempo_inicial = time.time()
    for palavra in lista_palavras:
        passos += 1
        if chave == palavra:
            tempo_final = time.time()
            tempo_total = tempo_final - tempo_inicial
            return passos, tempo_total, chave
    tempo_final = time.time()
    tempo_total = tempo_final - tempo_inicial
    return passos, tempo_total, None

def busca_sequencial_na_lista(chave, lista_palavras):
    passos = 0
    tempo_inicial = time.time()
    for palavra in lista_palavras:
        passos += 1
        if chave == palavra:
            tempo_final = time.time()
            tempo_total = tempo_final - tempo_inicial
            return passos, tempo_total, chave
    tempo_final = time.time()
    tempo_total = tempo_final - tempo_inicial
    return passos, tempo_total, None

# criação do csv que armazenará os resultados
with open('resultados.csv', 'w', newline='', encoding='utf-8') as csvfile:
                writer = csv.writer(csvfile, delimiter=',')
                writer.writerow(['texto', 'M', 'hash_name', 'n', 'alpha', 'max_bucket', 'avg_bucket', 'total_comp_success', 'avg_comp_success', 'total_comp_fail', 'avg_comp_fail'])

okcsv = input("Deseja gerar arquivos .csv com dados dos histogramas? (sim/nao) ")
okterminal = input("Deseja imprimir no terminal os histogramas? (sim/nao) ")

# automatizando os testes
textos = ["tale.txt", "quincas_borba.txt"]
M = [97, 100, 997]
hashs = [hash_soma_palavra_toda, hash_soma_ponderada, hash_polinomial, hash_simples]
nomes_funcoes_hash = ["hash_soma_palavra_toda", "hash_soma_ponderada", "hash_polinomial", "hash_simples"]
num_experimento = 0
for j in range (2):
    for k in range(3):
        for l in range (4):
            num_experimento += 1
            print("----------------------------------------")
            print(f"Experimento {num_experimento}\n")
            print(f"Texto: {textos[j]}")
            print(f"Função hash: {nomes_funcoes_hash[l]}")

            # criação da lista sequencial e da tabela hash
            tamanho_M = M[k]
            lista_sequencial = []
            tabela_hash = [] * tamanho_M
            for i in range (tamanho_M):
                tabela_hash.append([]) # cada item do vetor recebe outro vetor

            # tratamento de texto
            arquivo_de_texto = textos[j]
            with open(arquivo_de_texto, "r", encoding = "utf-8") as arquivo:
                texto = arquivo.read()
                texto_minusculo = texto.lower()
                palavras = re.findall(r'\b[a-zA-ZÀ-ÿ]+\b', texto_minusculo)

            for i in range (len(palavras)):
                if (palavras[i] not in lista_sequencial):
                    lista_sequencial.append(palavras[i])

            # inserindo na tabela
            for palavra in lista_sequencial:
                categoria = hashs[l](palavra, tamanho_M)
                tabela_hash[categoria].append(palavra)

            # métricas
            numero_palavras = len(lista_sequencial)
            fator_carga = numero_palavras/tamanho_M
            print(f"Tamanho da tabela: {tamanho_M}")
            print(f"Número de palavras distintas: {numero_palavras}")
            print(f"Fator de carga: {fator_carga}")

            buckets = []
            
            if(okterminal == "sim"):
                print("\nHistograma:")
                for categoria, lista_na_tabela in enumerate(tabela_hash):
                    total = len(lista_na_tabela)
                    buckets.append(total)
                    print(f"Categoria: {categoria} - {total*'*'}")
            
            # csv para armazenar os dados e criar um histograma no excel
            if(okcsv == "sim"):
                nome_arquivo_csv = f'histograma_{num_experimento}.csv'
                with open(nome_arquivo_csv, 'w', newline='', encoding='utf-8') as csvfile:
                    writer = csv.writer(csvfile, delimiter=',')
                    writer.writerow(['Bucket', 'Quantidade'])
                    for categoria, lista_na_tabela in enumerate(tabela_hash):
                        total = len(lista_na_tabela)
                        buckets.append(total)
                        writer.writerow([categoria, total])

            max_bucket = max(buckets)
            media = statistics.mean(buckets)
            desvio_padrao = statistics.pstdev(buckets)
            print(f"\nMaior bucket: {max_bucket}")
            print(f"Media do tamanho dos buckets: {media}")
            print(f"Desvio padrão do tamanho dos buckets: {desvio_padrao}")

            # cálculos de custo e tempo

            # conjunto sucesso: palavras do mesmo texto, já filtradas em "lista_sequencial"
            comparacoes_hash_s = 0
            comparacoes_vetor_s = 0
            segundos_hash_s = 0
            segundos_vetor_s = 0
            palavras_encontradas_hash_s = []
            palavras_encontradas_vetor_s = []

            print("----------------------------------------")
            print("Buscando todas as palavras do conjunto sucesso...")
            for i in range(len(lista_sequencial)):
                passos_hash, tempo_hash, palavra_encontrada_h = busca_hash(lista_sequencial[i], tabela_hash, tamanho_M)
                comparacoes_hash_s += passos_hash
                segundos_hash_s += tempo_hash
                if palavra_encontrada_h is not None:
                    palavras_encontradas_hash_s.append(palavra_encontrada_h)

                passos_vetor, tempo_vetor, palavra_encontrada_v = busca_sequencial_na_lista(lista_sequencial[i], lista_sequencial)
                comparacoes_vetor_s += passos_vetor
                segundos_vetor_s += tempo_vetor
                if palavra_encontrada_v is not None:
                    palavras_encontradas_vetor_s.append(palavra_encontrada_v)

            print(f"\nComparações na tabela hash: {comparacoes_hash_s}.")
            print(f"Tempo total decorrido na busca na tabela hash: {segundos_hash_s} segundos.")
            qtd_encontrada_hash_s = len(palavras_encontradas_hash_s)
            print(f"Quantidade de palavras encontradas na tabela hash: {qtd_encontrada_hash_s}.")

            print(f"\nComparações na estrutura linear: {comparacoes_vetor_s}.")
            print(f"Tempo total decorrido na busca na estrutura linear: {segundos_vetor_s} segundos.")
            qtd_encontrada_vetor_s = len(palavras_encontradas_vetor_s)
            print(f"Quantidade de palavras encontradas na estrutura linear: {qtd_encontrada_vetor_s}.")
            avg_comp_s = comparacoes_hash_s/numero_palavras

            # conjunto falha: arquivo de nomes já filtrado por fora
            comparacoes_hash_f = 0
            comparacoes_vetor_f = 0
            segundos_hash_f = 0
            segundos_vetor_f = 0
            palavras_encontradas_hash_f = []
            palavras_encontradas_vetor_f = []

            lista_falha = []
            with open("nomes_filtrados.txt", "r", encoding="utf-8") as arquivo:
                conjunto_falha = arquivo.read()
                lista_falha = conjunto_falha.split()

            print("----------------------------------------")
            print("Buscando todas as palavras do conjunto falha...")
            for i in range(len(lista_falha)):
                passos_hash, tempo_hash, palavra_encontrada_h = busca_hash(lista_falha[i], tabela_hash, tamanho_M)
                comparacoes_hash_f += passos_hash
                segundos_hash_f += tempo_hash
                if palavra_encontrada_h is not None:
                    palavras_encontradas_hash_f.append(palavra_encontrada_h)

                passos_vetor, tempo_vetor, palavra_encontrada_v = busca_sequencial_na_lista(lista_falha[i], lista_sequencial)
                comparacoes_vetor_f += passos_vetor
                segundos_vetor_f += tempo_vetor
                if palavra_encontrada_v is not None:
                    palavras_encontradas_vetor_f.append(palavra_encontrada_v)

            print(f"\nComparações na tabela hash: {comparacoes_hash_f}.")
            print(f"Tempo total decorrido na busca na tabela hash: {segundos_hash_f} segundos.")
            qtd_encontrada_hash_f = len(palavras_encontradas_hash_f)
            print(f"Quantidade de palavras encontradas na tabela hash: {qtd_encontrada_hash_f}.")

            print(f"\nComparações na estrutura linear: {comparacoes_vetor_f}.")
            print(f"Tempo total decorrido na busca na estrutura linear: {segundos_vetor_f} segundos.")
            qtd_encontrada_vetor_f = len(palavras_encontradas_vetor_f)
            print(f"Quantidade de palavras encontradas na estrutura linear: {qtd_encontrada_vetor_f}.")
            avg_comp_f = comparacoes_hash_f/numero_palavras

            # armazenando os resultados em um arquivo csv
            with open('resultados.csv', 'a', newline='', encoding='utf-8') as csvfile:
                writer = csv.writer(csvfile, delimiter=',')
                writer.writerow([textos[j], tamanho_M, nomes_funcoes_hash[l], numero_palavras, fator_carga, max_bucket, media, comparacoes_hash_s, avg_comp_s, comparacoes_hash_f, avg_comp_f])
