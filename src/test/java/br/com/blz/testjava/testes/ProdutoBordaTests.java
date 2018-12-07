package br.com.blz.testjava.testes;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.blz.testjava.MsgException;
import br.com.blz.testjava.bordas.ProdutoBorda;
import br.com.blz.testjava.controles.ProdutoControle;
import br.com.blz.testjava.entidades.db.Produto;
import lombok.extern.slf4j.Slf4j;


@ActiveProfiles("testes")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Tag("bordas")
@Slf4j
public class ProdutoBordaTests {
  
  @Autowired
  private ProdutoBorda borda;
  
  @MockBean
  private ProdutoControle controle;
  
  // --------------------------------
  
  @Test
  public void testarSucesso(){
    // preparar mock do controle para respostas de sucesso
    Produto produto = Produto.builder().sku(1).build() ;
    doReturn( Arrays.asList( produto ) ).when(controle).buscar();
    doReturn( produto ).when(controle).buscar(1);
    doReturn( 1 ).when(controle).atualizar(produto);
    doReturn( 1 ).when(controle).deletar(1);
    doNothing().when(controle).gravar(produto);
    
    // executar testes na borda
    List<Produto> respostaBusca = borda.buscar();
    assertTrue(respostaBusca.size() > 0, "A borda de Produtos não está retornando o valor de sucesso esperado para buscas!");
    assertEquals( borda.buscarId(1),  produto, "A borda de Produtos não está retornando o valor de sucesso esperado para buscas por ID/SKU!");
    assertDoesNotThrow( () -> borda.atualizar(produto) , "A borda de Produtos está disparando exceções inesperadas ao atualizar uma instância!");
    assertDoesNotThrow( () -> borda.deletar(1) , "A borda de Produtos está disparando exceções inesperadas ao deletar uma instância!");
    assertDoesNotThrow( () -> borda.criar(produto) , "A borda de Produtos está disparando exceções inesperadas ao criar uma instância!");
    
  }
  
  @Test
  public void testarErros(){
    // preparar mock do controle para respostas de sucesso
    Produto produto = Produto.builder().sku(1).inventory(null).build() ; // ao criar, deverá disparar exceção por não ter "inventory"
    doReturn( Arrays.asList(  ) ).when(controle).buscar();
    doReturn( null ).when(controle).buscar(1);
    doReturn( 0 ).when(controle).atualizar(produto);
    doReturn( 0 ).when(controle).deletar(1);
    doThrow( new MsgException("Exceção para testar criar produto sem 'inventory'", null) ).when(controle).gravar(produto);
    
    // executar testes na borda
    List<Produto> respostaBusca = borda.buscar();
    assertTrue(respostaBusca.size() < 1, "A borda de Produtos não está retornando lista vazia quado não existem registros de produtos!");
    assertThrows( MsgException.class, () -> borda.buscarId(1), "A borda de Produtos não está disparando uma 'MsgException' quando buscado item por ID/SKU!");
    assertThrows( MsgException.class, () -> borda.atualizar(produto) , "A borda de Produtos não está disparando uma 'MsgException' quando ao atualizar item por ID/SKU que não existe!");
    assertThrows( MsgException.class, () -> borda.deletar(1) , "A borda de Produtos não está disparando uma 'MsgException' quando ao deletar item por ID/SKU que não existe!");
    assertThrows( MsgException.class, () -> borda.criar(produto) , "A borda de Produtos não está disparando uma exceção quando ao criar item sem 'inventory'!");
    
  }
  
}
