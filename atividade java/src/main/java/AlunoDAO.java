import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private String urlBanco = "jdbc:sqlite:alunos.db";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(urlBanco);
    }

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS alunos ("
                   + "id INTEGER PRIMARY KEY, "
                   + "nome TEXT NOT NULL, "
                   + "idade INTEGER, "
                   + "curso TEXT"
                   + ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sql);
            System.out.println("Tabela 'alunos' verificada/criada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela de alunos: " + e.getMessage());
        }
    }

    public void inserir(Aluno aluno) {
        String sql = "INSERT INTO alunos(id, nome, idade, curso) VALUES(?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, aluno.getId());
            pstmt.setString(2, aluno.getNome());
            pstmt.setInt(3, aluno.getIdade());
            pstmt.setString(4, aluno.getCurso());

            pstmt.executeUpdate();
            System.out.println("Aluno '" + aluno.getNome() + "' inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
            // Uma causa comum de erro aqui é tentar inserir um ID que já existe,
            // pois definimos 'id' como PRIMARY KEY.
        }
    }

    public List<Aluno> listarTodos() {
        List<Aluno> listaDeAlunos = new ArrayList<>();
        String sql = "SELECT id, nome, idade, curso FROM alunos";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setCurso(rs.getString("curso"));
                
                listaDeAlunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os alunos: " + e.getMessage());
        }
        return listaDeAlunos;
    }

    public Aluno buscarPorId(int idParaBuscar) {
        String sql = "SELECT id, nome, idade, curso FROM alunos WHERE id = ?";
        Aluno alunoEncontrado = null;

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idParaBuscar);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                alunoEncontrado = new Aluno();
                alunoEncontrado.setId(rs.getInt("id"));
                alunoEncontrado.setNome(rs.getString("nome"));
                alunoEncontrado.setIdade(rs.getInt("idade"));
                alunoEncontrado.setCurso(rs.getString("curso"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno pelo ID " + idParaBuscar + ": " + e.getMessage());
        }
        return alunoEncontrado;
    }

    public void atualizar(Aluno alunoParaAtualizar) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, curso = ? WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, alunoParaAtualizar.getNome());
            pstmt.setInt(2, alunoParaAtualizar.getIdade());
            pstmt.setString(3, alunoParaAtualizar.getCurso());
            pstmt.setInt(4, alunoParaAtualizar.getId());

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno com ID " + alunoParaAtualizar.getId() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com o ID " + alunoParaAtualizar.getId() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno com ID " + alunoParaAtualizar.getId() + ": " + e.getMessage());
        }
    }

    public void deletar(int idParaDeletar) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idParaDeletar);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno com ID " + idParaDeletar + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com o ID " + idParaDeletar + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar aluno com ID " + idParaDeletar + ": " + e.getMessage());
        }
    }
}
