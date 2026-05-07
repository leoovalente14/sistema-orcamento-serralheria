package com.serralheria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static List<Servico> servicos = new ArrayList<>();
    static List<Material> materiais = new ArrayList<>();
    static List<PrecoServico> tabelaPrecos = new ArrayList<>();

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
            System.out.println("\n====== SISTEMA DE ORÇAMENTO - SERRALHERIA VALENTE ======");
            System.out.println("1 - Criar orçamento");
            System.out.println("2 - Ver tabela de preços");
            System.out.println("3 - Cadastrar novo serviço");
            System.out.println("4 - Cadastrar novo material");
            System.out.println("5 - Cadastrar/atualizar preço");
            System.out.println("6 - Sair");
            System.out.println("\nEscolha uma opção:");

            if(sc.hasNextInt()) {

                int opcao = sc.nextInt();
                sc.nextLine();

                if(opcao >= 1 && opcao <= 6) {
                    return opcao;
                } else {
                    System.out.println("\nNúmero inválido!");
                }

            } else {
                System.out.println("\nNúmero inválido!");
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
                            lerSimOuNao("\nDeseja realizar outro orçamento? (S/N)");

                    if(opcaoNovoOrcamento.equals("N")){
                        break;
                    }
                }
                break;

            case 2:
                verTabelaPrecos();
                System.out.println("Pressione ENTER para voltar ao menu...");
                String opcaoEnter = sc.nextLine();
                break;
            case 3:
                while(true) {
                    cadastrarServico();

                    String opcaoNovoCadastroServico =
                            lerSimOuNao("Deseja realizar outro cadastro de serviço? (S/N)");

                    if (opcaoNovoCadastroServico.equals("N")) {
                        break;
                    }
                }
                break;
            case 4:
                while(true) {
                    cadastrarMaterial();

                    String opcaoNovoCadastroMaterial =
                            lerSimOuNao("\nDeseja realizar outro cadastro de material? (S/N)");

                    if (opcaoNovoCadastroMaterial.equals("N")) {
                        break;
                    }
                }
                break;
            case 5:
                while(true) {
                    cadastrarPreco();

                    String opcaoCadastroPreco =
                            lerSimOuNao("\nDeseja realizar outro cadastro de preço? (S/N)");

                    if (opcaoCadastroPreco.equals("N")){
                        break;
                    }
                }
                break;

            case 6:
                System.out.println("Encerrando o sistema...");
                break;

        }
    }

    public static void criarOrcamento(){


        Cliente cliente = cadastrarCliente();

        Servico servicoEscolhido = selecionarServico();

        Material materialEscolhido = selecionarMaterial();

        double precoBase = buscarPreco(servicoEscolhido, materialEscolhido);

        System.out.println("\nPreço por metro - R$ " + precoBase);

        double valorMaterial = calcularValorBase(servicoEscolhido, precoBase);

        double precoOrcamento = valorMaterial;

        System.out.println("Preço do serviço: R$ " + valorMaterial);

        double valorMaoDeObra = lerMaoDeObra();

        precoOrcamento += valorMaoDeObra;

        System.out.printf("Total parcial do orçamento = R$ %.2f%n",precoOrcamento);


        List<Extra> extras = new ArrayList<>();
        double somaExtras = calcularExtras(extras);
        precoOrcamento += somaExtras;

        if (somaExtras > 0) {
            System.out.printf("Total parcial do orçamento: R$ %.2f%n", precoOrcamento);
        }

        double valorDescontoAplicado = calcularDesconto(precoOrcamento);
        precoOrcamento -= valorDescontoAplicado;


        exibirResumoOrcamento(
                cliente,
                valorMaterial,
                valorMaoDeObra,
                valorDescontoAplicado,
                precoOrcamento
        );
    }

    public static Cliente cadastrarCliente(){
        System.out.println("\n====== NOVO ORÇAMENTO ======\n");

        System.out.println("------ DADOS DO CLIENTE ------\n");

        String nome = lerStringValida("Nome do cliente: ");

        System.out.println("Endereço do cliente: ");
        String endereco = lerStringValida("Endereço do cliente: ");

        String celular = lerStringValida("Celular do cliente: ");

        Cliente cliente = new Cliente(nome, endereco, celular);

        System.out.println("Cliente cadastrado com sucesso!");

        return cliente;

    }

    public static Servico selecionarServico() {
        System.out.println("\n------ SERVIÇO ------");
        while(true) {
            for (int i = 0; i < servicos.size(); i++) {
                System.out.println((i + 1) + " - " + servicos.get(i).getNome());
            }

            System.out.println("Digite o número correspondente ao serviço:");

            if (sc.hasNextInt()) {
                int opcaoServico = sc.nextInt();
                sc.nextLine();
                if(opcaoServico > 0 && opcaoServico <= servicos.size()) {
                    Servico servicoEscolhido = servicos.get(opcaoServico - 1);
                    System.out.println("\nServiço selecionado - " + servicoEscolhido.getNome());
                    return servicoEscolhido;
                } else {
                    System.out.println("Número inválido!\n");

                }
            } else {
                System.out.println("Número inválido!\n");
                sc.nextLine();
            }
        }

    }

    public static Material selecionarMaterial() {
        System.out.println("\n------ MATERIAL ------\n");
        while(true) {
            for (int i = 0; i < materiais.size(); i++) {
                System.out.println((i + 1) + " - " + materiais.get(i).getNome());
            }

            System.out.println("Digite o número correspondente ao material utilizado no serviço:");
            if(sc.hasNextInt()) {
                int opcaoMaterial = sc.nextInt();
                sc.nextLine();
                if(opcaoMaterial > 0 && opcaoMaterial <= materiais.size()){
                    Material materialEscolhido = materiais.get(opcaoMaterial - 1);
                    System.out.println("\nMaterial selecionado - " + materialEscolhido.getNome());
                    return materialEscolhido;
                } else {
                    System.out.println("\nNúmero inválido!");
                }
            } else {
                System.out.println("\nNúmero inválido!");
                sc.nextLine();
            }
        }
    }

    public static double calcularValorBase(Servico servicoEscolhido, double precoBase) {
        double medida1;
        double medida2 = 0;
        double precoOrcamento;
        if (servicoEscolhido.getTipoCalculo() == TipoCalculo.AREA){


            medida1 = lerDoubleValido("\nDigite a altura em metros:");
            medida2 = lerDoubleValido("\nDigite a largura em metros:");

            precoOrcamento = medida1 * medida2 * precoBase;

        } else {
            medida1 = lerDoubleValido("\nDigite o comprimento em metros:");

            precoOrcamento = medida1 * precoBase;

        }
        return precoOrcamento;
    }

    public static double lerMaoDeObra() {
        double valorMaoDeObra = lerDoubleValido("\nDigite o preço da mão de obra do serviço:");

        return valorMaoDeObra;
    }

    public static double calcularExtras(List<Extra> extras) {
        while (true) {
            String opcaoExtra =
                    lerSimOuNao("\nDeseja adicionar um valor extra no orçamento? (S/N)");

            if (opcaoExtra.equals("N")) {
                break;
            }

            if (opcaoExtra.equals("S")) {

                String nomeExtra = lerStringValida("Digite o motivo do valor extra:");

                double valorExtra = lerDoubleValido("Digite o valor extra:");

                extras.add(new Extra(nomeExtra, valorExtra));
            }
        }

        double somaExtras = 0;

        if (extras.isEmpty()) {
            System.out.println("\nNenhum valor extra adicionado");
        } else {
            System.out.println("\n====== VALORES EXTRAS ======");

            for (int i = 0; i < extras.size(); i++) {
                Extra extra = extras.get(i);

                somaExtras += extra.getValor();

                System.out.printf("%d - %s - R$ %.2f%n",
                        i + 1,
                        extra.getMotivo(),
                        extra.getValor()
                );
            }
        }

        return somaExtras;
    }

    public static void verTabelaPrecos(){
        System.out.println("\n====== TABELA DE PREÇOS ======\n");

        int i = 1;

        for (PrecoServico ps : tabelaPrecos) {

            System.out.printf("%d - %s | %s | R$ %.2f%n",
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
        String opcaoDesconto =
                lerSimOuNao("\nDeseja adicionar um desconto no valor final? (S/N)");

        double valorDescontoAplicado = 0;
        String valorDesconto;
        if (opcaoDesconto.equals("S")) {
            System.out.println("Digite o valor do desconto: (% ou R$)");
            valorDesconto = sc.nextLine();

            if(valorDesconto.contains("%")) {
                valorDesconto = valorDesconto.replace("%", "").trim();
                double percentualDesconto = Double.parseDouble(valorDesconto);
                percentualDesconto = percentualDesconto / 100;
                valorDescontoAplicado = precoOrcamento * percentualDesconto;



            } else {
                valorDescontoAplicado  = Double.parseDouble(valorDesconto);
            }

            System.out.println("Desconto aplicado com sucesso!");
        }

        return valorDescontoAplicado;
    }

    public static void exibirResumoOrcamento(
            Cliente cliente,
            double valorMaterial,
            double valorMaoDeObra,
            double valorDescontoAplicado,
            double precoOrcamento
    ) {
        System.out.println("\n====== ORÇAMENTO FINAL ======\n");

        System.out.println("Nome do cliente: " + cliente.getNome());
        System.out.println("Endereço do cliente: " + cliente.getEndereco());
        System.out.println("Celular do cliente: " + cliente.getCelular());

        System.out.printf("Valor do material - R$ %.2f%n",valorMaterial);
        System.out.printf("Valor da mão de obra - R$ %.2f%n",valorMaoDeObra);
        System.out.printf("Desconto - R$ %.2f%n", valorDescontoAplicado);

        System.out.printf("VALOR TOTAL DO ORÇAMENTO - R$ %.2f%n", precoOrcamento);
    }

    public static void cadastrarServico() {
        System.out.println("\n====== CADASTRAR NOVO SERVIÇO ======");

        String nomeServico = lerStringValida("Nome do serviço: ");

        System.out.println("\nTipo de cálculo:");
        System.out.println("1 - Área");
        System.out.println("2 - Metro linear");
        System.out.println("Digite o número correspondente ao tipo de cálculo:");

        int opcaoTipoDeCalculo = sc.nextInt();
        sc.nextLine();

        TipoCalculo tipoCalculo;

        if (opcaoTipoDeCalculo == 1) {
            tipoCalculo = TipoCalculo.AREA;
        } else if (opcaoTipoDeCalculo == 2) {
            tipoCalculo = TipoCalculo.LINEAR;
        } else {
            System.out.println("Opção inválida. Por favor digite um número válido!");
            return;
        }


        Servico novoServico = new Servico(nomeServico, tipoCalculo);

        servicos.add(novoServico);

        System.out.println("\nServiço \"" + nomeServico + "\" cadastrado com sucesso!");
    }

    public static void cadastrarMaterial() {
        System.out.println("\n====== CADASTRAR NOVO MATERIAL ======");

        String nomeMaterial = lerStringValida("Nome do material: ");


        Material novoMaterial = new Material(nomeMaterial);

        materiais.add(novoMaterial);

        System.out.println("\nMaterial \"" + nomeMaterial + "\" cadastrado com sucesso!");


    }

    public static void cadastrarPreco() {
        System.out.println("\n====== CADASTRAR PREÇO ======");

        System.out.println("------SERVIÇOS------");
        for ( int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + " - " + servicos.get(i).getNome());
        }

        System.out.println("Digite o número correspondente ao serviço:");
        int opcaoTipoServiço = sc.nextInt();
        sc.nextLine();

        Servico servicoEscolhido = servicos.get(opcaoTipoServiço - 1);

        System.out.println("Serviço \"" + servicoEscolhido.getNome() + "\" selecionado com sucesso!");

        System.out.println("------ MATERIAIS ------");

        for (int i = 0; i < materiais.size(); i++) {
            System.out.println((i + 1) + " - " + materiais.get(i).getNome());
        }

        System.out.println("Digite o número correspondente ao material a ser utilizado:");

        int opcaoTipoMaterial = sc.nextInt();
        sc.nextLine();

        Material materialEscolhido = materiais.get(opcaoTipoMaterial - 1);

        System.out.println("Material \"" + materialEscolhido.getNome() + "\" selecionado com sucesso!");

        System.out.println("------ CADASTRO PREÇO ------");

        double precoEscolhido = lerDoubleValido("Digite o novo preço do " + servicoEscolhido.getNome() + " de " + materialEscolhido.getNome() + ":");

        boolean verificacaoTabelaPrecos = false;

        for (PrecoServico ps: tabelaPrecos) {
            if (ps.getServico() == servicoEscolhido &&
                ps.getMaterial() == materialEscolhido) {
                ps.setPreco(precoEscolhido);
                verificacaoTabelaPrecos = true;
                System.out.printf("Preço do %s de %s atualizado com o valor de R$ %.2f%n"
                        , servicoEscolhido.getNome()
                        , materialEscolhido.getNome()
                        , precoEscolhido);
                break;

            }
        }

        if(!verificacaoTabelaPrecos){

            PrecoServico novoPreco = new PrecoServico(servicoEscolhido, materialEscolhido, precoEscolhido);

            tabelaPrecos.add(novoPreco);

            System.out.printf("Preço do %s de %s cadastrado com o valor de R$ %.2f%n"
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
                    System.out.println("\nNúmero inválido!");
                }
            } else {
                System.out.println("\nNúmero inválido!");
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
                System.out.println("\nNúmero inválido!");
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
                System.out.println("Caractere inválido, utilize S ou N!");
            }

        }
    }

    public static String lerStringValida(String mensagem) {
        while(true){
            System.out.println(mensagem);
            String string = sc.nextLine().trim();

            if(string.isEmpty()){
                System.out.println("Você não digitou nada!");
            } else {
                return string;
            }
        }
    }
}
