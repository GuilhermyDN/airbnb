import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Objects;

class Util {
    public static String lerInput(String mensagem) {
        System.out.print(mensagem);
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine();
        }
    }
}

class Usuario {
    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean verificarSeUsuarioAlugou(Propriedade propriedade, String dataFim) {
        for (Reserva reserva : propriedade.reservas) {
            if (reserva.getUsuario().equals(this) && reserva.getDataFim().equals(dataFim)) {
                return true;
            }
        }
        return false;
    }

}

class Propriedade {
    private String titulo;
    private String descricao;
    private String localizacao;
    private int capacidade;
    private double precoPorNoite;
    List<Reserva> reservas;
    private List<Avaliacao> avaliacoes;

    public Propriedade(String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.capacidade = capacidade;
        this.precoPorNoite = precoPorNoite;
        this.reservas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    public double calcularMediaAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            return 0;
        }

        double somaAvaliacoes = 0;
        for (Avaliacao avaliacao : avaliacoes) {
            somaAvaliacoes += avaliacao.getNota();
        }

        return somaAvaliacoes / avaliacoes.size();
    }

    public boolean verificarDisponibilidade(String dataInicio, String dataFim) {
        for (Reserva reserva : reservas) {
            if (reserva.intersectsWith(dataInicio, dataFim)) {
                return false; // Já reservada para as datas especificadas
            }
        }
        return true; // Disponível para reserva
    }

    public String getTitulo() {
        return titulo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPrecoPorNoite() {
        return precoPorNoite;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getDataFim() {
        if (!reservas.isEmpty()) {
            Reserva reservaMaisRecente = reservas.get(0);

            for (Reserva reserva : reservas) {
                if (reserva.getDataFim().compareTo(reservaMaisRecente.getDataFim()) > 0) {
                    reservaMaisRecente = reserva;
                }
            }

            return reservaMaisRecente.getDataFim();
        }
        return null;
    }

    public String getDataInicioAluguel() {
        if (!reservas.isEmpty()) {
            Reserva reservaMaisRecente = reservas.get(0);

            for (Reserva reserva : reservas) {
                if (reserva.getDataInicio().compareTo(reservaMaisRecente.getDataInicio()) > 0) {
                    reservaMaisRecente = reserva;
                }
            }

            return reservaMaisRecente.getDataInicio();
        }
        return null;
    }

    public void exibirReservas() {
        System.out.println("Reservas da Propriedade " + titulo);
        for (Reserva reserva : reservas) {
            System.out.println("Data de Início: " + reserva.getDataInicio());
            System.out.println("Data de Fim: " + reserva.getDataFim());
            System.out.println("Usuário: " + reserva.getUsuario().getNome());
            System.out.println("----------------------------");
        }
    }

    // Exemplo de um método que pode ser adicionado
    public void exibirDetalhes() {
        System.out.println("Detalhes da Propriedade " + titulo);
        System.out.println("Título: " + titulo);
        System.out.println("Descrição: " + descricao);
        System.out.println("Localização: " + localizacao);
        System.out.println("Capacidade: " + capacidade);
        System.out.println("Preço por Noite: " + precoPorNoite);
        System.out.println("Média de Avaliações: " + calcularMediaAvaliacoes());
        System.out.println("----------------------------");

        if (!reservas.isEmpty()) {
            exibirReservas();
        } else {
            System.out.println("Nenhuma reserva realizada ainda.");
        }
    }

    public boolean verificarSeUsuarioAlugou(Usuario usuarioLogado, String tituloPropriedadeAvaliacao) {
        return false;
    }
}

class Reserva {
    private String dataInicio;
    private String dataFim;
    private Usuario usuario;

    public Reserva(String dataInicio, String dataFim, Usuario usuario) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public boolean intersectsWith(String otherDataInicio, String otherDataFim) {
        // Verifica se as datas da reserva atual intersectam com as datas especificadas
        // Pode ser necessário utilizar uma lógica mais avançada dependendo do formato
        // das datas
        return dataInicio.equals(otherDataInicio) || dataFim.equals(otherDataFim)
                || (dataInicio.compareTo(otherDataInicio) < 0 && dataFim.compareTo(otherDataInicio) > 0)
                || (dataInicio.compareTo(otherDataFim) < 0 && dataFim.compareTo(otherDataFim) > 0);
    }
}

class Avaliacao {
    private String comentario;
    private double nota;

    public Avaliacao(String comentario, double nota) {
        this.comentario = comentario;
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Avaliacao avaliacao = (Avaliacao) o;
        return Double.compare(avaliacao.nota, nota) == 0 &&
                Objects.equals(comentario, avaliacao.comentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comentario, nota);
    }
}

class CadastroUsuarios {
    private List<Usuario> usuarios;

    public CadastroUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public void cadastrarUsuario(Usuario usuario1) {
        usuarios.add(usuario1);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    public void cadastrarUsuario(Proprietario proprietario1) {
    }
}

class CadastroPropriedades {
    private List<Propriedade> propriedades;

    public CadastroPropriedades() {
        this.propriedades = new ArrayList<>();
    }

    public void cadastrarPropriedade(Propriedade propriedade) {
        propriedades.add(propriedade);
    }

    public void exibirDetalhesPropriedades() {
        for (Propriedade propriedade : propriedades) {
            System.out.println("Título: " + propriedade.getTitulo());
            System.out.println("Descrição: " + propriedade.getDescricao());
            System.out.println("Localização: " + propriedade.getLocalizacao());
            System.out.println("Capacidade: " + propriedade.getCapacidade());
            System.out.println("Preço por Noite: " + propriedade.getPrecoPorNoite());
            System.out.println("Média de Avaliações: " + propriedade.calcularMediaAvaliacoes());
            System.out.println("----------------------------");
        }
    }

    public Propriedade buscarPropriedadePorTitulo(String titulo) {
        for (Propriedade propriedade : propriedades) {
            if (propriedade.getTitulo().equals(titulo)) {
                return propriedade;
            }
        }
        return null;
    }
}

class Menu {
    private CadastroUsuarios cadastroUsuarios;
    private CadastroPropriedades cadastroPropriedades;
    private Usuario usuarioLogado;

    public Menu(CadastroUsuarios cadastroUsuarios, CadastroPropriedades cadastroPropriedades) {
        this.cadastroUsuarios = cadastroUsuarios;
        this.cadastroPropriedades = cadastroPropriedades;
        this.usuarioLogado = null;
    }

    public void exibirMenuInicial() {
        int opcao;

        do {
            System.out.println("Bem-vindo à plataforma de Aluguel de Propriedades!");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Login como Proprietário");
            System.out.println("2. Login como Usuário");
            System.out.println("3. Sair");

            opcao = Integer.parseInt(Util.lerInput("Digite o número da opção desejada: "));

            switch (opcao) {
                case 1:
                    loginProprietario();
                    break;
                case 2:
                    loginUsuario();
                    break;
                case 3:
                    System.out.println("Obrigado por usar a plataforma. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 3);
    }

    public class Proprietario extends Usuario {
        // Outros atributos e métodos da classe Proprietario

        public Proprietario(String nome, String email, String senha) {
            super(nome, email, senha);
            // Inicialização específica do Proprietario, se necessário
        }

        // Método para verificar a senha
        public boolean verificarSenha(String senha) {
            // Lógica para verificar se a senha fornecida corresponde à senha do
            // Proprietario
            // Aqui você pode comparar a senha fornecida com a senha armazenada no
            // Proprietario
            return this.getSenha().equals(senha);
        }

        // Outros métodos específicos do Proprietario, se necessário
    }

    private void loginProprietario() {
        System.out.println("Login como Proprietário");
        String email = Util.lerInput("Informe seu email: ");
        String senha = Util.lerInput("Informe sua senha: ");

        // Buscar o usuário pelo email
        Usuario usuario = cadastroUsuarios.buscarUsuarioPorEmail(email);

        // Verificar se o usuário é um proprietário e se a senha está correta
        if (usuario != null && usuario instanceof Proprietario) {
            Proprietario proprietario = (Proprietario) usuario; // Cast para Proprietario
            if (proprietario.verificarSenha(senha)) {
                // Login bem-sucedido, usuário é um proprietário e a senha está correta
                System.out.println("Login bem-sucedido como Proprietário!");
                exibirMenuProprietario();
                return; // Retornar para evitar a execução do bloco else
            }
        }

        // Credenciais inválidas
        System.out.println("Credenciais inválidas. Tente novamente.");
        exibirMenuInicial();
    }

    private void loginUsuario() {
        System.out.println("Login como Usuário");
        String email = Util.lerInput("Informe seu email: ");
        String senha = Util.lerInput("Informe sua senha: ");

        // Buscar o usuário pelo email
        Usuario usuario = cadastroUsuarios.buscarUsuarioPorEmail(email);

        // Verificar se o usuário foi encontrado e se a senha está correta
        if (usuario != null && usuario instanceof Usuario) {
            if (usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                exibirMenuUsuario();
            } else {
                System.out.println("Senha incorreta. Tente novamente.");
                exibirMenuInicial();
            }
        } else {
            System.out.println("Credenciais inválidas. Tente novamente.");
            exibirMenuInicial();
        }
    }

    private void exibirMenuProprietario() {
        while (true) {
            System.out.println("\nMenu do Proprietário");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar Propriedade");
            System.out.println("2. Exibir Propriedades e Avaliações");
            System.out.println("3. Verificar Propriedades Alugadas");
            System.out.println("4. Logout");

            int opcao = Integer.parseInt(Util.lerInput("Digite o número da opção desejada: "));

            switch (opcao) {
                case 1:
                    cadastrarPropriedade();
                    break;
                case 2:
                    exibirDetalhesPropriedades();
                    break;
                case 3:
                    verificarPropriedadesAlugadas();
                    break;
                case 4:
                    usuarioLogado = null;
                    exibirMenuInicial();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void cadastrarPropriedade() {
        System.out.println("\nCadastro de Propriedade");

        String titulo = Util.lerInput("Informe o título da propriedade: ");
        String descricao = Util.lerInput("Informe a descrição da propriedade: ");
        String localizacao = Util.lerInput("Informe a localização da propriedade: ");
        int capacidade = Integer.parseInt(Util.lerInput("Informe a capacidade da propriedade: "));
        double precoPorNoite = Double.parseDouble(Util.lerInput("Informe o preço por noite da propriedade: "));

        Propriedade novaPropriedade = new Propriedade(titulo, descricao, localizacao, capacidade, precoPorNoite);
        cadastroPropriedades.cadastrarPropriedade(novaPropriedade);

        System.out.println("Propriedade cadastrada com sucesso!");
    }

    private void exibirDetalhesPropriedades() {
        System.out.println("\nDetalhes das Propriedades");
        cadastroPropriedades.exibirDetalhesPropriedades();
    }

    private void verificarPropriedadesAlugadas() {
        // Implemente a lógica para verificar quais propriedades estão alugadas pelo
        // proprietário
        // Essa funcionalidade depende de como você está mantendo o rastreamento das
        // reservas e propriedades
        // Aqui, estou apenas simulando algumas propriedades alugadas
        System.out.println("\nPropriedades Alugadas pelo Proprietário");
        List<Propriedade> propriedadesAlugadas = new ArrayList<>(); // Substitua por sua lógica real
        for (Propriedade propriedade : propriedadesAlugadas) {
            System.out.println("Título: " + propriedade.getTitulo());
            System.out.println("Data de Início: " + propriedade.getDataInicioAluguel()); // Substitua por sua lógica
                                                                                         // real
            System.out.println("Data de Fim: " + propriedade.getDataInicioAluguel()); // Substitua por sua lógica real
            System.out.println("----------------------------");
        }
    }

    private void exibirMenuUsuario() {
        while (true) {
            System.out.println("\nMenu do Usuário");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Consultar Propriedades Disponíveis");
            System.out.println("2. Alugar Propriedade");
            System.out.println("3. Avaliar Propriedade Alugada");
            System.out.println("4. Logout");

            int opcao = Integer.parseInt(Util.lerInput("Digite o número da opção desejada: "));

            switch (opcao) {
                case 1:
                    consultarPropriedadesDisponiveis();
                    break;
                case 2:
                    alugarPropriedade();
                    break;
                case 3:
                    avaliarPropriedadeAlugada();
                    break;
                case 4:
                    usuarioLogado = null;
                    exibirMenuInicial();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void consultarPropriedadesDisponiveis() {
        System.out.println("\nPropriedades Disponíveis para Aluguel");
        cadastroPropriedades.exibirDetalhesPropriedades();
    }

    private void alugarPropriedade() {
        System.out.println("\nAluguel de Propriedade");

        String tituloPropriedade = Util.lerInput("Informe o título da propriedade desejada: ");
        Propriedade propriedadeAluguel = cadastroPropriedades.buscarPropriedadePorTitulo(tituloPropriedade);

        if (propriedadeAluguel != null) {
            String dataInicioAluguel = Util.lerInput("Informe a data de início do aluguel (formato: YYYY-MM-DD): ");
            String dataFimAluguel = Util.lerInput("Informe a data de fim do aluguel (formato: YYYY-MM-DD): ");

            if (propriedadeAluguel.verificarDisponibilidade(dataInicioAluguel, dataFimAluguel)) {
                // Realizar o aluguel
                Reserva novaReserva = new Reserva(dataInicioAluguel, dataFimAluguel, (Usuario) usuarioLogado);
                propriedadeAluguel.adicionarReserva(novaReserva);

                System.out.println("Aluguel realizado com sucesso!");
            } else {
                System.out.println("Desculpe, a propriedade não está disponível para as datas selecionadas.");
            }
        } else {
            System.out.println("Propriedade não encontrada.");
        }
    }

    private void avaliarPropriedadeAlugada() {
        System.out.println("\nAvaliação de Propriedade Alugada");

        // Implemente a lógica para listar as propriedades alugadas pelo usuário
        // Essa funcionalidade depende de como você está mantendo o rastreamento das
        // reservas e propriedades
        // Aqui, estou apenas simulando algumas propriedades alugadas
        List<Propriedade> propriedadesAlugadas = new ArrayList<>(); // Substitua por sua lógica real

        if (!propriedadesAlugadas.isEmpty()) {
            System.out.println("Propriedades Alugadas por Você:");
            for (Propriedade propriedade : propriedadesAlugadas) {
                System.out.println("Título: " + propriedade.getTitulo());
                System.out.println("----------------------------");
            }

            String tituloPropriedadeAvaliacao = Util.lerInput("Informe o título da propriedade que deseja avaliar: ");
            Propriedade propriedadeAvaliacao = cadastroPropriedades
                    .buscarPropriedadePorTitulo(tituloPropriedadeAvaliacao);

            if (propriedadeAvaliacao != null) {
                if ((boolean) usuarioLogado.verificarSeUsuarioAlugou(propriedadeAvaliacao,
                        tituloPropriedadeAvaliacao)) {
                    String comentarioAvaliacao = Util.lerInput("Deixe seu comentário sobre a propriedade: ");
                    double notaAvaliacao = Double
                            .parseDouble(Util.lerInput("Dê uma nota de 1 a 5 para a propriedade: "));

                    Avaliacao novaAvaliacao = new Avaliacao(comentarioAvaliacao, notaAvaliacao);
                    propriedadeAvaliacao.adicionarAvaliacao(novaAvaliacao);

                    System.out.println("Avaliação realizada com sucesso!");
                } else {
                    System.out.println("Você não alugou esta propriedade. Avaliação não permitida.");
                }
            } else {
                System.out.println("Propriedade não encontrada.");
            }
        } else {
            System.out.println("Você ainda não alugou nenhuma propriedade para avaliar.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CadastroUsuarios cadastroUsuarios = new CadastroUsuarios();
        CadastroPropriedades cadastroPropriedades = new CadastroPropriedades();
        Menu menu = new Menu(cadastroUsuarios, cadastroPropriedades);

        // Criação de alguns usuários fictícios
        Usuario usuario1 = new Usuario("João", "joao@email.com", "senha123");
        UsuarioComum usuario2 = new UsuarioComum("Maria", "maria@email.com", "senha456");
        Proprietario proprietario1 = new Proprietario("Carlos", "carlos@email.com", "senha789");

        // Cadastrar usuários no cadastroUsuarios
        cadastroUsuarios.cadastrarUsuario(usuario1);
        cadastroUsuarios.cadastrarUsuario(usuario2);
        cadastroUsuarios.cadastrarUsuario(proprietario1);

        // Criação de algumas propriedades fictícias
        Propriedade propriedade1 = new Propriedade("Casa na Praia", "Linda casa à beira-mar", "Praia do Sol", 5, 200.0);
        Propriedade propriedade2 = new Propriedade("Apartamento no Centro",
                "Confortável apartamento no coração da cidade", "Centro", 3, 150.0);

        cadastroPropriedades.cadastrarPropriedade(propriedade1);
        cadastroPropriedades.cadastrarPropriedade(propriedade2);

        // Simulação de reservas
        Reserva reserva1 = new Reserva("2023-12-01", "2023-12-10", usuario1);
        Reserva reserva2 = new Reserva("2023-11-15", "2023-11-20", usuario2);

        // Adiciona as reservas às propriedades
        propriedade1.adicionarReserva(reserva1);
        propriedade2.adicionarReserva(reserva2);

        // Exibir o menu inicial
        menu.exibirMenuInicial();

        scanner.close();
    }
}