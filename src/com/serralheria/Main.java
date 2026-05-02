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
            System.out.println("\n====== SISTEMA DE ORÇAMENTO - SERRALHERIA VALENTE ======\n");
            System.out.println("1 - Criar orçamento");
            System.out.println("2 - Ver tabela de preços");
            System.out.println("3 - Cadastrar novo serviço");
            System.out.println("4 - Cadastrar novo material");
            System.out.println("5 - Cadastrar/atualizar preço");
            System.out.println("6 - Sair");
            System.out.println("\nEscolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    while(true) {
                        criarOrcamento();

                        System.out.println("Deseja realizar outro orçamento? (S/N)");
                        String opcaoNovoOrcamento = sc.nextLine();

                        if(opcaoNovoOrcamento.equalsIgnoreCase("N")){
                            break;
                        }
                    }
                    break;

                case 2:
                    verTabelaPrecos();
                    break;
                case 3:
                    System.out.println("Cadastrar novo serviço selecionado.");
                    break;
                case 4:
                    System.out.println("Cadastrar novo material selecionado.");
                    break;
                case 5:
                    System.out.println("Cadastrar/atualizar preço selecionado.");
                    break;
                case 6:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }

        } while (opcao !=6);
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

    public static void criarOrcamento(){

        List<Extra> extras = new ArrayList<>();

        System.out.println("\n====== NOVO ORÇAMENTO ======\n");

        System.out.println("------ DADOS DO CLIENTE ------\n");

        System.out.println("Nome do cliente: ");
        String nome = sc.nextLine();

        System.out.println("Endereço do cliente: ");
        String endereco = sc.nextLine();

        System.out.println("Celular do cliente: ");
        String celular = sc.nextLine();

        System.out.println("\nCliente cadastrado!");

        Cliente cliente = new Cliente(nome, endereco, celular);

        System.out.println("\n------ SERVIÇO ------");

        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + " - " + servicos.get(i).getNome());
        }

        System.out.println("Digite o número correspondente ao serviço:");
        int opcaoServico = sc.nextInt();
        sc.nextLine();

        Servico servicoEscolhido = servicos.get(opcaoServico - 1);

        System.out.println("\nServiço selecionado - " + servicoEscolhido.getNome());

        System.out.println("\n------ MATERIAL ------\n");

        for (int i = 0; i < materiais.size(); i++) {
            System.out.println((i + 1) + " - " + materiais.get(i).getNome());
        }

        System.out.println("Digite o número correspondente ao material utilizado no serviço:");
        int opcaoMaterial = sc.nextInt();
        sc.nextLine();

        Material materialEscolhido = materiais.get(opcaoMaterial - 1);

        System.out.println("\nMaterial selecionado - " + materialEscolhido.getNome());

        double precoBase = buscarPreco(servicoEscolhido, materialEscolhido);

        System.out.println("\nPreço por metro - R$ " + precoBase);

        double medida1;
        double medida2 = 0;
        double precoOrcamento;

        if (servicoEscolhido.getTipoCalculo() == TipoCalculo.AREA){

            System.out.println("\nDigite a altura em metros:");
            medida1 = sc.nextDouble();
            sc.nextLine();

            System.out.println("Digite a largura em metros:");
            medida2 = sc.nextDouble();
            sc.nextLine();

            precoOrcamento = medida1 * medida2 * precoBase;

        } else {
            System.out.println("Digite o comprimento em metros:");
            medida1 = sc.nextDouble();
            sc.nextLine();

            precoOrcamento = medida1 * precoBase;

        }

        double valorMaterial = precoOrcamento;

        System.out.println("Preço do serviço: R$ " + precoOrcamento);

        System.out.println("\nDigite o preço da mão de obra do serviço:");

        double valorMaoDeObra = sc.nextDouble();
        sc.nextLine();

        precoOrcamento += valorMaoDeObra;

        System.out.printf("Total parcial do orçamento = R$ %.2f%n",precoOrcamento);


        while(true) {
            System.out.println("\nDeseja adicionar um valor extra no orçamento? (S/N)");
            String opcaoExtra = sc.nextLine();

            if (opcaoExtra.equalsIgnoreCase("N")){
                break;
            }

            if (opcaoExtra.equalsIgnoreCase("S")){
                System.out.println("Digite o motivo do valor extra:");
                String nomeExtra = sc.nextLine();
                System.out.println("Digite o valor extra:");
                double valorExtra = sc.nextDouble();
                sc.nextLine();
                extras.add(new Extra(nomeExtra, valorExtra));
            }
        }

        double somaExtras = 0;

        if (extras.isEmpty()){
            System.out.println("\nNenhum valor extra adicionado");
        } else {
            System.out.println("\n ====== VALORES EXTRAS ======");




            for (int i = 0; i < extras.size(); i++) {

                Extra extra = extras.get(i);

                somaExtras += extra.getValor();

                System.out.printf((i + 1) + " - %s - R$ %.2f%n",
                        extra.getMotivo(),
                        extra.getValor()
                );
            }

            precoOrcamento += somaExtras;

            System.out.printf("Total parcial do orçamento: R$ %.2f%n", precoOrcamento);
        }


        System.out.println("\nDeseja adicionar um desconto no valor final? (S/N)");
        String opcaoDesconto = sc.nextLine();

        double valorDescontoAplicado = 0;
        String valorDesconto;
        double desconto = 0;
        if (opcaoDesconto.equalsIgnoreCase("S")) {
            System.out.println("Digite o valor do desconto: (% ou R$)");
            valorDesconto = sc.nextLine();

            if(valorDesconto.contains("%")) {
                valorDesconto = valorDesconto.replace("%", "").trim();
                desconto = Double.parseDouble(valorDesconto);
                desconto = desconto / 100;
                valorDescontoAplicado = precoOrcamento * desconto;
                precoOrcamento = precoOrcamento - (valorDescontoAplicado);


            } else {
                desconto = Double.parseDouble(valorDesconto);
                precoOrcamento -= desconto;
                valorDescontoAplicado = desconto;
            }
        }

        System.out.println("\n====== ORÇAMENTO FINAL ======\n");

        System.out.println("Nome do cliente: " + cliente.getNome());
        System.out.println("Endereço do cliente: " + cliente.getEndereco());
        System.out.println("Celular do cliente: " + cliente.getCelular());

        System.out.printf("Valor do material - R$ %.2f%n",valorMaterial);
        System.out.printf("Valor da mão de obra - R$ %.2f%n",valorMaoDeObra);
        System.out.printf("Desconto - R$ %.2f%n", valorDescontoAplicado);

        System.out.printf("VALOR TOTAL DO ORÇAMENTO - R$ %.2f%n", precoOrcamento);








    }

    public static void verTabelaPrecos(){
        System.out.println("\n ====== TABELA DE PREÇOS ======");
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
}
