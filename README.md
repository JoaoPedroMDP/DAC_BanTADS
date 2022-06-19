# Como rodar o BanTADS

## Instalação
Precisa apenas do Docker instalado e dos arquivos do projeto

## Execução
Para rodar o projeto em sua máquina:
1. Verifique se a porta 4200 está disponível (geralmente está)

2. Crie um atalho para a pasta do projeto do front com o nome `front`. Precisa desse nome pois é com ele que o docker-compose monta o volume    
    `ln -s nome-da-pasta-do-front/ front`
    > Se a pasta já se chamar front...bom, não precisa desse passo :)

3. Agora, para poder rodar, precisamos criar um atalho para o docker-compose certo. A diferença entre os dois é que o de produção **copia os arquivos do projeto** para dentro dele, enquanto que o de desenvolvimento **apenas cria um volume**. Isso é necessário para não tornar o processo de desenvolvimento demorado.
    
    **a**. Produção: `ln -s docker-compose-prod.yml docker-compose.yml` 
    
    **b**. Desenvolvimento: `ln -s docker-compose-dev.yml docker-compose.yml`

4. Agora, é só rodar `docker-compose up -d`
    > Lembrando que não é necessário rebuildar o projeto a cada alteração. Como no ambiente de desenvolvimento criamos apenas um volume, toda alteração feita na máquina host é vista pelo container, e o próprio Angular já faz o *reload* da página :)