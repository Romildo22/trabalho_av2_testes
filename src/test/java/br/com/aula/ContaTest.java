package br.com.aula;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import br.com.aula.exception.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class ContaTest {

	@Test
	public void deveEfetuarTransferenciaContasCorrentesPoupanca() throws ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, ContaInvalidaException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}

	@Test(expected = ContaNaoExistenteException.class)
	public void verificarContaOrigemExiste() throws ContaNaoExistenteException, ContaSemSaldoException, ContaJaExistenteException, ContaInvalidaException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Maria");
		Conta contaOrigem = new Conta(cliente, 456, 0, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Joao");
		Conta contaDestino = new Conta(cliente2, 123, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Collections.singletonList(contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		Assert.fail();
	}

	@Test(expected = ContaSemSaldoException.class)
	public void naoDevePermitirTransferenciaPoupancaSaldoNegativo() throws ContaNaoExistenteException, ContaSemSaldoException, ContaJaExistenteException, ContaInvalidaException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Maria");
		Conta contaOrigem = new Conta(cliente, 456, 0, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Joao");
		Conta contaDestino = new Conta(cliente2, 123, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(456, 123, 100);

		Assert.fail();
	}

	@Test(expected = ContaNaoExistenteException.class)
	public void verificarContaDestinoExiste() throws ContaNaoExistenteException, ContaSemSaldoException, ContaJaExistenteException, ContaInvalidaException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Maria");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Joao");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Collections.singletonList(contaOrigem));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		Assert.fail();
	}

	@Test(expected = ValorNegativoException.class)
	public void verificarValorNegativo() throws ContaNaoExistenteException, ContaSemSaldoException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Maria");
		Conta contaOrigem = new Conta(cliente, 456, 100, TipoConta.POUPANCA);

		Cliente cliente2 = new Cliente("Joao");
		Conta contaDestino = new Conta(cliente2, 123, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, -10);

		Assert.fail();
	}


}
