package com.serralheria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static List<Servico> servicos = new ArrayList<>();
    static List<Material> materiais = new ArrayList<>();
    static List<PrecoServico> tabelaPrecos = new ArrayList<>();

    public static final String RESET = "\u001B[0m";

    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String CIANO = "\u001B[36m";

    public static void main(String[] args) {
        carregarDadosIniciais();
        int opcao;
        do {
           opcao = lerOpcaoMenu();
           executarOpcaoMenu(opcao);
        } while (opcao != 6);
    }

    public static void carregarDadosIniciais() {
        Servico portao = new Servico("Portão", TipoCalculo.AREA);
        Servico grade = new Servico("Grade", TipoCalculo.AREA);
        Servico janela = new Servico("Janela", TipoCalculo.AREA);
        Servico corrimao = new Servico("Corrimão", TipoCalculo.LINEAR);

        servicos.add(portao);
        servicos.add(grade);
        servicos.add(janela);
        servicos.add(corrimao);

        Material ferro = new Material("Ferro");
        Material aluminio = new Material("Alumínio");

        materiais.add(ferro);
        materiais.add(aluminio);

        tabelaPrecos.add(new PrecoServico(portao, ferro, 450));
        tabelaPrecos.add(new PrecoServico(portao, aluminio, 650));
        tabelaPrecos.add(new PrecoServico(grade, ferro, 350));
        tabelaPrecos.add(new PrecoServico(grade, aluminio, 550));
        tabelaPrecos.add(new PrecoServico(janela, ferro, 400));
        tabelaPrecos.add(new PrecoServico(janela, aluminio, 600));
        tabelaPrecos.add(new PrecoServico(corrimao, ferro, 300));
        tabelaPrecos.add(new PrecoServico(corrimao, aluminio, 500));


    }

    public static int lerOpcaoMenu(){
        while(true){
            System.out.println(AMARELO + "==================================================");
            System.out.println("            SISTEMA SERRALHERIA VALENTE           ");
            System.out.println("==================================================\n" + RESET);
            System.out.println("[1] Criar orçamento");
            System.out.println("[2] Ver tabela de preços");
            System.out.println("[3] Cadastrar novo serviço");
            System.out.println("[4] Cadastrar novo material");
            System.out.println("[5] Cadastrar ou atualizar preço");
            System.out.println(VERMELHO + "[6] Sair\n" + RESET);
            System.out.println("--------------------------------------------------");
            System.out.println(CIANO + "➜ Selecione uma opção:" + RESET);

            if(sc.hasNextInt()) {

                int opcao = sc.nextInt();
                sc.nextLine();

                if(opcao >= 1 && opcao <= 6) {
                    return opcao;
                } else {
                    System.out.println(VERMELHO + "\n✖ Número inválido!\n" + RESET);
                }

            } else {
                System.out.println(VERMELHO + "\n✖ Número inválido!\n" + RESET);
                sc.nextLine();
            }
        }
    }

    public static void executarOpcaoMenu(int opcao){
        switch (opcao) {
            case 1:
                while(true) {
                    criarOrcamento();

                    String opcaoNovoOrcamento =
                            lerSimOuNao(CIANO + "\n➜ Deseja realizar outro orçamento? (S/N)" + RESET);

                    if(opcaoNovoOrcamento.equals("N")){
                        break;
                    }
                }
                break;

            case 2:
                verTabelaPrecos();
                System.out.println(CIANO + "➜ Pressione ENTER para voltar ao menu..." + RESET);
                String opcaoEnter = sc.nextLine();
                break;
            case 3:
                while(true) {
                    cadastrarServico();

                    String opcaoNovoCadastroServico =
                            lerSimOuNao(CIANO + "➜ Deseja realizar outro cadastro de serviço? (S/N)" + RESET);

                    if (opcaoNovoCadastroServico.equals("N")) {
                        break;
                    }
                }
                break;
            case 4:
                while(true) {
                    cadastrarMaterial();

                    String opcaoNovoCadastroMaterial =
                            lerSimOuNao(CIANO + "\n➜ Deseja realizar outro cadastro de material? (S/N)" + RESET);

                    if (opcaoNovoCadastroMaterial.equals("N")) {
                        break;
                    }
                }
                break;
            case 5:
                while(true) {
                    cadastrarPreco();

                    String opcaoCadastroPreco =
                            lerSimOuNao(CIANO + "\n➜ Deseja realizar outro cadastro de preço? (S/N)" + RESET);

                    if (opcaoCadastroPreco.equals("N")){
                        break;
                    }
                }
                break;

            case 6:
                System.out.println(VERMELHO + "⚠ Encerrando o sistema..." + RESET);
                break;

        }
    }

    public static void criarOrcamento(){


        Cliente cliente = cadastrarCliente();

        Servico servicoEscolhido = selecionarServico();

        Material materialEscolhido = selecionarMaterial();

        double precoBase = buscarPreco(servicoEscolhido, materialEscolhido);

        System.out.printf("\nPreço por metro - " + VERDE + "R$ %.2f%n" + RESET ,precoBase);

        double valorMaterial = calcularValorBase(servicoEscolhido, precoBase);

        double precoOrcamento = valorMaterial;

        System.out.printf("\nTotal parcial do orçamento: " + VERDE + "R$ %.2f%n" + RESET ,valorMaterial);

        double valorMaoDeObra = lerMaoDeObra();

        precoOrcamento += valorMaoDeObra;

        System.out.printf("\nTotal parcial do orçamento: " + VERDE + "R$ %.2f%n" + RESET ,precoOrcamento);


        List<Extra> extras = new ArrayList<>();
        double somaExtras = calcularExtras(extras);
        precoOrcamento += somaExtras;

        if (somaExtras > 0) {
            System.out.printf("\nTotal parcial do orçamento: " + VERDE + "R$ %.2f%n" + RESET ,precoOrcamento);
        }

        double valorDescontoAplicado = calcularDesconto(precoOrcamento);
        precoOrcamento -= valorDescontoAplicado;

        Orcamento orcamento = new Orcamento(
                cliente,
                servicoEscolhido,
                materialEscolhido,
                valorMaterial,
                valorMaoDeObra,
                somaExtras,
                valorDescontoAplicado
        );


        exibirResumoOrcamento(orcamento);
    }

    public static Cliente cadastrarCliente(){
        System.out.println(AMARELO + "\n\n==================================================");
        System.out.println("                   NOVO ORÇAMENTO                 ");
        System.out.println("==================================================\n" + RESET);

        System.out.println("---------------- DADOS DO CLIENTE ----------------\n");

        String nome = lerStringValida(CIANO + "Nome do cliente:" + RESET);

        String endereco = lerStringValida(CIANO + "\nEndereço do cliente:" + RESET);

        String celular = lerStringValida(CIANO + "\nCelular do cliente:" + RESET);

        Cliente cliente = new Cliente(nome, endereco, celular);

        System.out.println(VERDE + "\nCliente cadastrado com sucesso ✔\n" + RESET);

        return cliente;

    }

    public static Servico selecionarServico() {
        System.out.println("\n-------------------- SERVIÇOS --------------------");
        while(true) {
            for (int i = 0; i < servicos.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + servicos.get(i).getNome());
            }

            System.out.println("\n--------------------------------------------------");
            System.out.println(CIANO + "➜ Selecione o serviço:" + RESET);

            if (sc.hasNextInt()) {
                int opcaoServico = sc.nextInt();
                sc.nextLine();
                if(opcaoServico > 0 && opcaoServico <= servicos.size()) {
                    Servico servicoEscolhido = servicos.get(opcaoServico - 1);
                    System.out.println(VERDE + "\n➜ Serviço selecionado - " + servicoEscolhido.getNome() + RESET);
                    return servicoEscolhido;
                } else {
                    System.out.println(VERMELHO + "✖ Número inválido!\n" + RESET);

                }
            } else {
                System.out.println(VERMELHO + "✖ Número inválido!\n" + RESET);
                sc.nextLine();
            }
        }

    }

    public static Material selecionarMaterial() {
        System.out.println("\n--------------------- MATERIAIS -------------------");
        while(true) {
            for (int i = 0; i < materiais.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + materiais.get(i).getNome());
            }

            System.out.println("\n--------------------------------------------------");
            System.out.println(CIANO + "➜ Selecione o material:" + RESET);
            if(sc.hasNextInt()) {
                int opcaoMaterial = sc.nextInt();
                sc.nextLine();
                if(opcaoMaterial > 0 && opcaoMaterial <= materiais.size()){
                    Material materialEscolhido = materiais.get(opcaoMaterial - 1);
                    System.out.println(VERDE + "\n➜ Material selecionado - " + materialEscolhido.getNome() + RESET);
                    return materialEscolhido;
                } else {
                    System.out.println(VERMELHO + "\n✖ Número inválido!" + RESET);
                }
            } else {
                System.out.println(VERMELHO +"\n✖ Número inválido!" + RESET);
                sc.nextLine();
            }
        }
    }

    public static double calcularValorBase(Servico servicoEscolhido, double precoBase) {
        double medida1;
        double medida2 = 0;
        double precoOrcamento;

        System.out.println("\n--------------------- MEDIDAS --------------------");

        if (servicoEscolhido.getTipoCalculo() == TipoCalculo.AREA){


            medida1 = lerDoubleValido(CIANO + "\nDigite a altura em metros:" + RESET);
            medida2 = lerDoubleValido(CIANO + "\nDigite a largura em metros:" + RESET);

            precoOrcamento = medida1 * medida2 * precoBase;

        } else {
            medida1 = lerDoubleValido(CIANO + "\nDigite o comprimento em metros:" + RESET);

            precoOrcamento = medida1 * precoBase;

        }
        return precoOrcamento;
    }

    public static double lerMaoDeObra() {

        System.out.println("\n------------------- MÃO DE OBRA ------------------");

        double valorMaoDeObra = lerDoubleValido(CIANO + "\nDigite o preço da mão de obra do serviço:" + RESET);

        return valorMaoDeObra;
    }

    public static double calcularExtras(List<Extra> extras) {
        System.out.println("\n--------------------- EXTRAS ---------------------");
        while (true) {
            String opcaoExtra =
                    lerSimOuNao(CIANO + "\nDeseja adicionar um valor extra no orçamento? (S/N)" + RESET);

            if (opcaoExtra.equals("N")) {
                break;
            }

            if (opcaoExtra.equals("S")) {

                String nomeExtra = lerStringValida(CIANO + "\nDigite o motivo do valor extra:" + RESET);

                double valorExtra = lerDoubleValido(CIANO + "\nDigite o valor extra:" + RESET);

                extras.add(new Extra(nomeExtra, valorExtra));
            }
        }

        double somaExtras = 0;

        if (extras.isEmpty()) {
            System.out.println(VERMELHO + "\n⚠ Nenhum valor extra adicionado!" + RESET);
        } else {
            System.out.println("\n---------------- VALORES EXTRAS ------------------");

            for (int i = 0; i < extras.size(); i++) {
                Extra extra = extras.get(i);

                somaExtras += extra.getValor();

                System.out.printf("[%d] - %s - " + VERDE + "R$ %.2f%n" + RESET,
                        i + 1,
                        extra.getMotivo(),
                        extra.getValor()
                );
            }
        }

        return somaExtras;
    }

    public static void verTabelaPrecos(){
        System.out.println(AMARELO + "\n\n==================================================");
        System.out.println("                  TABELA DE PREÇOS                ");
        System.out.println("==================================================" + RESET);

        int i = 1;

        for (PrecoServico ps : tabelaPrecos) {

            System.out.printf("[%d]  %s | %s | " + VERDE + "R$ %.2f%n" + RESET,
                    i,
                    ps.getServico().getNome(),
                    ps.getMaterial().getNome(),
                    ps.getPreco());
            i++;

        }
    }

    public static double buscarPreco(Servico servico, Material material) {
        for (PrecoServico precoServico : tabelaPrecos) {
            if(precoServico.getServico() == servico &&
                precoServico.getMaterial() == material) {

                return precoServico.getPreco();
            }
        }

        return 0;
    }

    public static double calcularDesconto(double precoOrcamento){
        System.out.println("\n-------------------- DESCONTO --------------------");
        String opcaoDesconto =
                lerSimOuNao(CIANO + "\nDeseja adicionar um desconto no valor final? (S/N)" + RESET);

        double valorDescontoAplicado = 0;
        String valorDesconto;
        if (opcaoDesconto.equals("S")) {
            System.out.println(CIANO + "Digite o valor do desconto: (% ou R$)" + RESET);
            valorDesconto = sc.nextLine();

            if(valorDesconto.contains("%")) {
                valorDesconto = valorDesconto.replace("%", "").trim();
                double percentualDesconto = Double.parseDouble(valorDesconto);
                percentualDesconto = percentualDesconto / 100;
                valorDescontoAplicado = precoOrcamento * percentualDesconto;



            } else {
                valorDescontoAplicado  = Double.parseDouble(valorDesconto);
            }

            System.out.println(VERDE + "➜ Desconto aplicado com sucesso!" + RESET);
        }

        return valorDescontoAplicado;
    }

    public static void exibirResumoOrcamento(Orcamento orcamento) {
        System.out.println(AMARELO + "\n\n==================================================");
        System.out.println("                   ORÇAMENTO FINAL                ");
        System.out.println("==================================================\n" + RESET);

        System.out.println("Nome do cliente: " + orcamento.getCliente().getNome());
        System.out.println("Endereço do cliente: " + orcamento.getCliente().getEndereco());
        System.out.println("Celular do cliente: " + orcamento.getCliente().getCelular());

        System.out.printf("\n\n%-20s R$ %.2f%n", "Valor material:", orcamento.getValorMaterial());
        System.out.printf("%-20s R$ %.2f%n", "Mão de obra:", orcamento.getValorMaoDeObra());
        System.out.printf("%-20s R$ %.2f%n", "Desconto:", orcamento.getValorDesconto());
        System.out.printf("%-20s " + VERDE + "R$ %.2f" + RESET + "%n", "➜ VALOR TOTAL:", orcamento.getValorTotal());
    }

    public static void cadastrarServico() {
        System.out.println(AMARELO +"\n\n==================================================");
        System.out.println("             CADASTRAR NOVO SERVIÇO               ");
        System.out.println("==================================================" + RESET);

        String nomeServico = lerStringValida(CIANO + "\nNome do serviço: " + RESET);

        System.out.println("\nTipo de cálculo:");
        System.out.println("[1] Área");
        System.out.println("[2] Metro linear");
        System.out.println("\n--------------------------------------------------");
        System.out.println(CIANO + "➜ Selecione o tipo de cálculo:" + RESET);

        int opcaoTipoDeCalculo = sc.nextInt();
        sc.nextLine();

        TipoCalculo tipoCalculo;

        if (opcaoTipoDeCalculo == 1) {
            tipoCalculo = TipoCalculo.AREA;
        } else if (opcaoTipoDeCalculo == 2) {
            tipoCalculo = TipoCalculo.LINEAR;
        } else {
            System.out.println(VERMELHO + "✖ Opção inválida. Por favor digite um número válido!" + RESET);
            return;
        }


        Servico novoServico = new Servico(nomeServico, tipoCalculo);

        servicos.add(novoServico);

        System.out.println(VERDE + "\nServiço \"" + nomeServico + "\" cadastrado com sucesso ✔" + RESET);
    }

    public static void cadastrarMaterial() {
        System.out.println(AMARELO + "\n\n==================================================");
        System.out.println("             CADASTRAR NOVO MATERIAL              ");
        System.out.println("==================================================" + RESET);

        String nomeMaterial = lerStringValida(CIANO + "\nNome do material:" + RESET);


        Material novoMaterial = new Material(nomeMaterial);

        materiais.add(novoMaterial);

        System.out.println(VERDE + "\nMaterial \"" + nomeMaterial + "\" cadastrado com sucesso ✔" + RESET);


    }

    public static void cadastrarPreco() {
        System.out.println(AMARELO + "\n\n==================================================");
        System.out.println("                 CADASTRAR PREÇO                  ");
        System.out.println("==================================================" + RESET);

        System.out.println("------------------- SERVIÇOS ---------------------");
        for ( int i = 0; i < servicos.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + servicos.get(i).getNome());
        }
        System.out.println("\n--------------------------------------------------");
        System.out.println(CIANO + "➜ Selecione o serviço:" + RESET);
        int opcaoTipoServico = sc.nextInt();
        sc.nextLine();

        Servico servicoEscolhido = servicos.get(opcaoTipoServico - 1);

        System.out.println(VERDE + "Serviço \"" + servicoEscolhido.getNome() + "\" selecionado com sucesso ✔" + RESET);

        System.out.println("\n------------------- MATERIAIS --------------------");

        for (int i = 0; i < materiais.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + materiais.get(i).getNome());
        }

        System.out.println(CIANO + "➜ Selecione o material:" + RESET);

        int opcaoTipoMaterial = sc.nextInt();
        sc.nextLine();

        Material materialEscolhido = materiais.get(opcaoTipoMaterial - 1);

        System.out.println(VERDE + "Material \"" + materialEscolhido.getNome() + "\" selecionado com sucesso ✔" + RESET);

        System.out.println("\n------------------- CADASTRO PREÇO ---------------");

        double precoEscolhido = lerDoubleValido(CIANO + "➜ Digite o novo preço do " + servicoEscolhido.getNome() + " de " + materialEscolhido.getNome() + ":" + RESET);

        boolean verificacaoTabelaPrecos = false;

        for (PrecoServico ps: tabelaPrecos) {
            if (ps.getServico() == servicoEscolhido &&
                ps.getMaterial() == materialEscolhido) {
                ps.setPreco(precoEscolhido);
                verificacaoTabelaPrecos = true;
                System.out.printf("Preço do %s de %s atualizado com o valor  " + VERDE + "R$ %.2f%n" + RESET
                        , servicoEscolhido.getNome()
                        , materialEscolhido.getNome()
                        , precoEscolhido);
                break;

            }
        }

        if(!verificacaoTabelaPrecos){

            PrecoServico novoPreco = new PrecoServico(servicoEscolhido, materialEscolhido, precoEscolhido);

            tabelaPrecos.add(novoPreco);

            System.out.printf("Preço do %s de %s cadastrado com o valor de " + VERDE + "R$ %.2f%n" + RESET
                    , servicoEscolhido.getNome()
                    , materialEscolhido.getNome()
                    , precoEscolhido);
        }




    }

    public static double lerDoubleValido(String mensagem) {
        while(true) {
            System.out.println(mensagem);
            if (sc.hasNextDouble()) {
                double valorValido = sc.nextDouble();
                sc.nextLine();
                if(valorValido >= 0){
                    return valorValido;
                } else {
                    System.out.println(VERMELHO + "\n✖ Número inválido!" + RESET);
                }
            } else {
                System.out.println(VERMELHO + "\n✖ Número inválido!" + RESET);
                sc.nextLine();
            }
        }
    }

    public static int lerIntValido(String mensagem) {
        while(true){
            System.out.println(mensagem);
            if(sc.hasNextInt()){
                int valorValido = sc.nextInt();
                sc.nextLine();
                return valorValido;
            } else {
                System.out.println(VERMELHO + "\n✖ Número inválido!" + RESET);
                sc.nextLine();
            }
        }
    }

    public static String lerSimOuNao(String mensagem) {
        while(true){
            System.out.println(mensagem);
            String opcao = sc.nextLine().toUpperCase();
            if(opcao.equals("S") || opcao.equals("N")){
                return opcao;
            } else {
                System.out.println(VERMELHO + "✖ Caractere inválido, utilize S ou N!" + RESET);
            }

        }
    }

    public static String lerStringValida(String mensagem) {
        while(true){
            System.out.println(mensagem);
            String string = sc.nextLine().trim();

            if(string.isEmpty()){
                System.out.println(VERMELHO + "✖ Você não digitou nada!" + RESET);
            } else {
                return string;
            }
        }
    }
}
