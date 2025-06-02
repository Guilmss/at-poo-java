import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        Scanner scanner = new Scanner(System.in);

       
        alunoDAO.criarTabela();

        System.out.println("Iniciando o Gerenciador de Alunos!");

        int opcao;
        do {
            System.out.println("\n--- Menu Gerenciador de Alunos ---");
            System.out.println("1. Inserir aluno");
            System.out.println("2. Listar todos os alunos");
            System.out.println("3. Buscar aluno por ID");
            System.out.println("4. Atualizar aluno");
            System.out.println("5. Deletar aluno");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Por favor, insira um número.");
                System.out.print("Escolha uma opção: ");
                scanner.next(); 
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Inserir Aluno ---");
                    System.out.print("ID do aluno: ");
                    int idInserir = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Nome do aluno: ");
                    String nomeInserir = scanner.nextLine();
                    System.out.print("Idade do aluno: ");
                    int idadeInserir = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Curso do aluno: ");
                    String cursoInserir = scanner.nextLine();
                    Aluno novoAluno = new Aluno(idInserir, nomeInserir, idadeInserir, cursoInserir);
                    alunoDAO.inserir(novoAluno);
                    break;
                case 2:
                    System.out.println("\n--- Listando Todos os Alunos ---");
                    List<Aluno> todosAlunos = alunoDAO.listarTodos();
                    if (todosAlunos.isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado.");
                    } else {
                        for (Aluno aluno : todosAlunos) {
                            System.out.println(aluno);
                        }
                    }
                    break;
                case 3:
                    System.out.println("\n--- Buscar Aluno por ID ---");
                    System.out.print("Digite o ID do aluno a ser buscado: ");
                    int idBuscar = scanner.nextInt();
                    scanner.nextLine(); 
                    Aluno alunoEncontrado = alunoDAO.buscarPorId(idBuscar);
                    if (alunoEncontrado != null) {
                        System.out.println("Aluno encontrado: " + alunoEncontrado);
                    } else {
                        System.out.println("Aluno com ID " + idBuscar + " não encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- Atualizar Aluno ---");
                    System.out.print("Digite o ID do aluno a ser atualizado: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); 

                    Aluno alunoParaAtualizar = alunoDAO.buscarPorId(idAtualizar);
                    if (alunoParaAtualizar != null) {
                        System.out.println("Dados atuais: " + alunoParaAtualizar);
                        System.out.print("Novo nome (deixe em branco para não alterar): ");
                        String novoNome = scanner.nextLine();
                        if (!novoNome.trim().isEmpty()) {
                            alunoParaAtualizar.setNome(novoNome);
                        }

                        System.out.print("Nova idade (digite 0 para não alterar): ");
                        String novaIdadeStr = scanner.nextLine();
                        if (!novaIdadeStr.trim().isEmpty()) {
                            try {
                                int novaIdade = Integer.parseInt(novaIdadeStr);
                                if (novaIdade > 0) {
                                    alunoParaAtualizar.setIdade(novaIdade);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Idade inválida, mantendo a original.");
                            }
                        }
                        
                        System.out.print("Novo curso (deixe em branco para não alterar): ");
                        String novoCurso = scanner.nextLine();
                        if (!novoCurso.trim().isEmpty()) {
                            alunoParaAtualizar.setCurso(novoCurso);
                        }
                        
                        alunoDAO.atualizar(alunoParaAtualizar);
                    } else {
                        System.out.println("Aluno com ID " + idAtualizar + " não encontrado para atualização.");
                    }
                    break;
                case 5:
                    System.out.println("\n--- Deletar Aluno ---");
                    System.out.print("Digite o ID do aluno a ser deletado: ");
                    int idDeletar = scanner.nextInt();
                    scanner.nextLine(); 
                    alunoDAO.deletar(idDeletar);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
        System.out.println("\n--- Fim da execução ---");
    }
}
